package get.ruturn.json;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import analysis.js.sites.ifengNews;

public class getReturnJson {

	public static void main(String[] args) throws Exception {

		String url = "http://book.ifeng.com/a/20170703/78401_0.shtml";
		// String url = "http://news.ifeng.com/a/20170706/51387861_0.shtml";

		 ifengNews ifn = new ifengNews();
		 int num = ifn.cmtCount(url);
		 System.out.println("the num is : "+ num);


	}

}
