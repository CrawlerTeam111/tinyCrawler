package com.ns.simple.pojos;

import java.util.ArrayList;

import com.ns.simple.enumeration.Ctg;
/**
 * 
 * 将URL对象进行封装
 * */
public class URLpojo {
	public URLpojo(){
		URL = null;
		category = null;
	}
	public URLpojo(String uRL, Ctg category) {
		URL = RepairURL(uRL);
		this.category = category;
	}
	
	private String URL;
	private Ctg category;
	
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = RepairURL(uRL);
	}
	public Ctg getCategory() {
		return category;
	}
	public void setCategory(Ctg category) {
		this.category = category;
	}
	private String RepairURL(String uRL){
		String tmp = uRL;
		tmp = tmp.replaceAll(" ","");
		tmp = tmp .replaceAll("\r|\n", "");
		return tmp;
	}
}
