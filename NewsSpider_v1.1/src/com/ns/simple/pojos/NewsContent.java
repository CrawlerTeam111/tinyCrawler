package com.ns.simple.pojos;
/**
 * 
 * 将网页内容对象进行封装，数据库应提供存储NewsContent的接口
 * */
public class NewsContent {
	public NewsContent(){
		title=content=null;
		comments=-1;
	}
	public NewsContent(String title, String content, int comments,String time,String href,String source) {
		this.title = title;
		this.content = content;
		this.comments = comments;
		this.time = time;
		this.href = href;
		this.source = source;
	}
	
	private String title;
	private String content;
	private int comments;
	private String time;
	private String source;
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

	private String href;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getComments() {
		return comments;
	}
	public void setComments(int comments) {
		this.comments = comments;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
}
