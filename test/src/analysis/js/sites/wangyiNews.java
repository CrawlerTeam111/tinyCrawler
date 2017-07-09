package analysis.js.sites;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;

import analysisi.js.util.RegexUtil;
import net.sf.json.JSONObject;

public class wangyiNews {
	
	
	public int cmtCount(String url) throws IOException { //��ȡ��������������,URL��Ҫ��ȡ�����ŵ���ַ
		
		// ����������ҳ��������ӵĹ�ͬ��ǰ�벿��
		String basic = "http://sdk.comment.163.com/api/v1/products/a2869674571f77b5a0867c3d71db5856/threads/";

		RegexUtil re = new RegexUtil();
		String regexStr = "(.*/)";
		String adID = re.getFirstString(url, regexStr, 1);
		String docID = url.substring(adID.length(), adID.length() + 16);

		// �õ��������۵���ҳ����ľ�ȷ����
		String reurl = basic + docID;

		Response res = Jsoup.connect(reurl).ignoreContentType(true).execute();
		String body = res.body();

		JSONObject jsonObj = JSONObject.fromObject(body);

		int cmtAgainst = jsonObj.getInt("cmtAgainst");
		int cmtVote = jsonObj.getInt("cmtVote");
		int rcount = jsonObj.getInt("rcount");
		int cmtNum = cmtAgainst + cmtVote + rcount;

		return cmtNum;
	}
}
