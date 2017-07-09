package com.ns.simple.iface.crawl;

import java.util.ArrayList;

import com.ns.simple.pojos.*;
/**
 * ≈¿≥Ê¿‡Ω”ø⁄
 */
public interface ICrawler {
	public NewsContent crawl(URLpojo url) throws Exception;
	public ArrayList<URLpojo> godeep(URLpojo url);
}
