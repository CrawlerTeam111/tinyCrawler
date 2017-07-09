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

	public int cmtCount(String url) throws IOException { // ��ȡ���������������,URL��Ҫ��ȡ�����ŵ���ַ

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

		//���ط���ҳ�棬�õ�json����
		Response res = Jsoup.connect(reurl).ignoreContentType(true).execute();
		String body = res.body();

		JSONObject jsonObj = JSONObject.fromObject(body);

		int join_count = jsonObj.getInt("join_count");
	

		return join_count;

	}

}
