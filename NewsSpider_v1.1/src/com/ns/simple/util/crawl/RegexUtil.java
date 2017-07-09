package com.ns.simple.util.crawl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	public String getFirstString(String dealStr, String regexStr, int n){
		if(dealStr == null || regexStr == null || n < 1){
			return "";
		}
		Pattern pattern = Pattern.compile(regexStr,Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
		Matcher matcher = pattern.matcher(dealStr);
		while (matcher.find()){
			return matcher.group(n).trim();
		}
		return "";
	}
	
	
	public List<String> getList(String dealStr, String regexStr, int n){
		List<String> list = new ArrayList<String>();
		if(dealStr == null || regexStr == null || n < 1){
			return list;
		}
		Pattern pattern = Pattern.compile(regexStr,Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
		Matcher matcher = pattern.matcher(dealStr);
		while (matcher.find()){
			list.add(matcher.group(n).trim());
		}
		return list;
	}
	
	
	public List<String[]> getList(String dealStr, String regexStr, int[] array){
		List<String[]> list = new ArrayList<String[]>();
		if(dealStr == null || regexStr == null || array == null){
			return list;
		}
		Pattern pattern = Pattern.compile(regexStr,Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
		Matcher matcher = pattern.matcher(dealStr);
		while (matcher.find()){
			String[] str = new String[array.length];
			for(int i=0; i < array.length; i++){
				str[i] = matcher.group(array[i]).trim();
			}
			list.add(str);
		}
		return list;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
