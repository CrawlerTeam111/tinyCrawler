package analysis.js.sites;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;

import org.jsoup.Connection.Response;

import analysisi.js.util.RegexUtil;
import net.sf.json.JSONObject;

public class ifengNews {

	public int cmtCount(String url) throws IOException { // 获取凤凰网新闻评论数,URL是要爬取的新闻的网址

		// 返回评论网页请求的链接的共同的前半部分
		String basicFront = "http://comment.ifeng.com/get.php?doc_url=";
		// 返回评论网页请求的链接的共同的后半部分
		String basicBehind = "&job=1&callback=callbackGetFastCommentCount&callback=callbackGetFastCommentCount";

		// 下载目标网站
		Document doc = Jsoup.connect(url).get();
		// 获得指定JavaScript内容
		Elements ele = doc.select("head > script");
		String rejs = ele.html();


		// 对获取的数据进行正则匹配，得到返回的目标url的中间部分
		RegexUtil re = new RegexUtil();
		String regexStr = "\"commentUrl\":\"(.*?)\"";
		String plainURL = re.getFirstString(rejs, regexStr, 1);

		// 对返回的目标URL的中间部分进行编码
		String docURL = URLEncoder.encode(plainURL, "utf-8");

		// 整合前中后三部分，得到目标URL(返回评论的网页请求的精确链接)
		String reurl = basicFront + docURL + basicBehind;

		//下载返回页面，得到json数据
		Response res = Jsoup.connect(reurl).ignoreContentType(true).execute();
		String body = res.body();

		JSONObject jsonObj = JSONObject.fromObject(body);

		int join_count = jsonObj.getInt("join_count");
	

		return join_count;

	}

}
