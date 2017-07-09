package com.ns.simple.util.crawl;

import java.io.IOException;

import javax.lang.model.element.Element;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.ns.simple.pojos.URLpojo;
/**
 * ʹ��httpclient��url��ȡ��ҳhtml����
 * 
 * */
public class URLGetter {
	public URLGetter(){
	}
	/*
	 * ץȡURL�е�HTML
	 * */
	private HttpClient httpclient;
	private HttpGet httpget;
	private HttpResponse httpresponse;
	public String CrawlURL(URLpojo url){
		try{
			httpclient = new DefaultHttpClient();
			HttpClientParams.setCookiePolicy(httpclient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
		
			httpget = new HttpGet(url.getURL());
			httpresponse = httpclient.execute(httpget);
			HttpEntity httpentity = httpresponse.getEntity();
			if(httpentity != null){
				//System.out.println("Response Content Len" + httpentity.getContentLength());
				String Html = EntityUtils.toString(httpentity,"UTF-8");
				return Html;
			}
			//httpget.abort();
		} catch (Exception e){
			System.out.println("ERROR,����ҳHTTP�����쳣");
		}finally{
			httpget.abort();
		}
		return null;	
	}
	
	
	public static void main(String args[]){
		//new CrawlerAnalyser();
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("http://js.ifeng.com/a/20170705/5795050_0.shtml");
		
	}
}
