package com.ns.simple.impl.crawl;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ns.simple.enumeration.Ctg;
import com.ns.simple.iface.crawl.ICrawler;
import com.ns.simple.pojos.NewsContent;
import com.ns.simple.pojos.URLpojo;
import com.ns.simple.util.crawl.URLGetter;
import com.ns.simple.util.crawl.RegexUtil;

import net.sf.json.JSONObject;

import com.ns.simple.util.crawl.IOUtil;

/**
 * ������
 * 
 * ��Ҫ���������� 1.��һ��URL���������ݣ�crawl���� 2.��һ��URL���������������ӵ�����
 * 
 */
public class IfengSpider implements ICrawler {

	URLGetter UG;

	public IfengSpider() {// ���췽��
		UG = new URLGetter();
	}

	private int Classify(String urls) {// ���������������ڿɸ���������ʽ�Ż�����Ҫ��ɸѡ����Ҫ���URL
		ArrayList<String> LS = new ArrayList<String>();
		LS.add("");

		if (urls.charAt(0) == 'h' && urls.charAt(1) == 't' && urls.charAt(2) == 't' && urls.charAt(3) == 'p'
				&& urls.contains("ifeng") && !urls.contains("search") && !urls.contains("video")
				&& !urls.contains("v.ifeng") && !urls.contains(".jpg") && !urls.contains("yc.ifeng")
				&& !urls.contains("imall") && !urls.contains("dm.ifeng")) {
			if (urls.contains("_0.shtml") && !urls.contains("#p")) {
				return 0;// ����ҳ
			} else {
				String patten = "";
				return 1;// ����ҳ��������ʽ��չ����д��
			}
		}
		return 2;// �Ƿ�ҳ
	}

	/*
	 * ��ȡ���������������,URL��Ҫ��ȡ�����ŵ���ַ
	 */
	public int cmtCount(String url) throws IOException {

		// ����������ҳ��������ӵĹ�ͬ��ǰ�벿��
		String basicFront = "http://comment.ifeng.com/get.php?doc_url=";
		// ����������ҳ��������ӵĹ�ͬ�ĺ�벿��
		String basicBehind = "&job=1&callback=callbackGetFastCommentCount&callback=callbackGetFastCommentCount";

		// ����Ŀ����վ
		Document doc = Jsoup.connect(url).get();
		// ���ָ��JavaScript����
		Elements ele = doc.select("head > script");
		String rejs = ele.html();

		// �Ի�ȡ�����ݽ�������ƥ�䣬�õ����ص�Ŀ��url���м䲿��
		RegexUtil re = new RegexUtil();
		String regexStr = "\"commentUrl\":\"(.*?)\"";
		String plainURL = re.getFirstString(rejs, regexStr, 1);

		// �Է��ص�Ŀ��URL���м䲿�ֽ��б���
		String docURL = URLEncoder.encode(plainURL, "utf-8");

		// ����ǰ�к������֣��õ�Ŀ��URL(�������۵���ҳ����ľ�ȷ����)
		String reurl = basicFront + docURL + basicBehind;

		// ���ط���ҳ�棬�õ�json����
		Response res = Jsoup.connect(reurl).ignoreContentType(true).execute();
		String body = res.body();

		JSONObject jsonObj = JSONObject.fromObject(body);
		int join_count = jsonObj.getInt("join_count");

		return join_count;

	}

	/*
	 * �������������ݵ���ҳ
	 */
	@Override
	public NewsContent crawl(URLpojo url) throws Exception {

		String Html = UG.CrawlURL(url);
		if (Html != null) {
			Document doc = Jsoup.parse(Html);// ������ҳ���õ��ĵ�����

			// ������ű���
			Elements title = doc.select("#artical_topic");
			// ������ŷ���ʱ��
			Elements time = doc.select("#artical_sth > p > span.ss01");
			// ���������Դ
			Elements source = doc.select("#artical_sth > p > span:nth-child(4) > span > a");
			// ���������������
			Elements mainContent = doc.select("#main_content>p");

			// ������Ŷ�Ӧ�������
			int cmtCount = cmtCount(url.getURL());

			NewsContent newsContent = new NewsContent();
			if (title.text().length() > 4) {
				newsContent.setTitle(title.text());
				newsContent.setTime(time.text());
				newsContent.setSource(source.text());
				newsContent.setComments(cmtCount);
				newsContent.setHref(url.getURL());
				String content = "";
				for (Element para : mainContent) {
					content += para.text();
				}
				newsContent.setContent(content);
				return newsContent;
			}

//System.out.println("The title is : " + title.text());
//System.out.println("The time is : " + time.text());
//System.out.println("The source is : " + source.text());
//System.out.println("The num of comments is : " + cmtCount);
//System.out.println("The content is : ");
//for (Element para : mainContent) {
//	System.out.println(para.text());
//}

		}
		return null;
	}

	@Override
	/*
	 * ��ȡ����������������
	 * 
	 */
	public ArrayList<URLpojo> godeep(URLpojo url) {
		// TODO Auto-generated method stub
		ArrayList<URLpojo> URLS = new ArrayList<URLpojo>();
		String Html = UG.CrawlURL(url);
		// System.out.println(Html);
		// String filePath = "test.txt";
		// String value = Html;
		// String encoding = "UTF-8";
		//
		// IOUtil.writeFile(filePath, value, encoding);
		if (Html != null) {
			Document doc = Jsoup.parse(Html);// ������ҳ���õ��ĵ�����
			Elements Hrefs = doc.getElementsByTag("a");
			String tmpurl = "";
			for (Element href : Hrefs) {
				tmpurl = href.attr("href");
				if (tmpurl != "" && tmpurl != null) {
					// System.out.println(tmpurl);
					switch (Classify(tmpurl)) {
					case 1:
						if (url.getCategory() == Ctg.LINK_TO_LINK || url.getCategory() == Ctg.HOME) {
							if (tmpurl.contains(url.getURL()) && tmpurl != url.getURL()) {
								URLS.add(new URLpojo(tmpurl, Ctg.LINK_TO_CONTENT));
								String filePath = "test.txt";
								String value = tmpurl + "    ctg:LINK\n";
								String encoding = "UTF-8";
								IOUtil.writeFile(filePath, value, encoding);
							}
						}
						break;
					case 2:
						break;
					case 0:
						URLS.add(new URLpojo(tmpurl, Ctg.CONTENT));
						// String filePath2 = "test.txt";
						// String value2 = tmpurl + " ctg: CONTENT\n";
						// String encoding2 = "UTF-8";
						// IOUtil.writeFile(filePath2, value2, encoding2);
						break;
					}
				}

			}
		}
		return URLS;
	}

	public static void main(String args[]) throws Exception {
		// ArrayList<URLpojo> tmpA;
		// tmpA = new IfengSpider().godeep(new URLpojo("http://www.ifeng.com",
		// Ctg.HOME));
		// for (int i = 0; i < tmpA.size(); i++) {
		// System.out.println("url: " + tmpA.get(i).getURL() + " category: " +
		// tmpA.get(i).getCategory());
		// }
		
		String url = "http://news.ifeng.com/a/20170707/51390978_0.shtml";
		IfengSpider ifp = new IfengSpider();
		URLpojo urlc = new URLpojo(url, Ctg.CONTENT);
		NewsContent nc = ifp.crawl(urlc);
		System.out.println(nc.getTitle());
		System.out.println(nc.getComments());		
	
	}
}
