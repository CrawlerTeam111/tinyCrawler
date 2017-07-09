package com.ns.simple.crawlmanager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import com.ns.simple.enumeration.Ctg;
import com.ns.simple.impl.crawl.IfengSpider;
import com.ns.simple.pojos.NewsContent;
import com.ns.simple.pojos.URLpojo;
import com.ns.simple.util.crawl.IOUtil;

public class MultiThifengSpiderManager {
	public MultiThifengSpiderManager() {
		super();
		vstedURL = new HashSet<String>();
		URLpojoQ = new LinkedList<URLpojo>();
		LinkedURLs = new ArrayList<String>();
		LinkedURLs.add("http://news.ifeng.com");LinkedURLs.add("http://finance.ifeng.com");
		LinkedURLs.add("http://ent.ifeng.com");LinkedURLs.add("http://sports.ifeng.com");
		LinkedURLs.add("http://culture.ifeng.com");LinkedURLs.add("http://fashion.ifeng.com");
		LinkedURLs.add("http://auto.ifeng.com");LinkedURLs.add("http://tech.ifeng.com");
		LinkedURLs.add("http://games.ifeng.com");LinkedURLs.add("http://hlj.ifeng.com");
		LinkedURLs.add("http://sd.ifeng.com");LinkedURLs.add("http://js.ifeng.com");
		LinkedURLs.add("http://zj.ifeng.com");LinkedURLs.add("http://ah.ifeng.com");
		LinkedURLs.add("http://hb.ifeng.com");LinkedURLs.add("http://hn.ifeng.com");
		LinkedURLs.add("http://gd.ifeng.com");LinkedURLs.add("http://hebei.ifeng.com");
		LinkedURLs.add("http://jx.ifeng.com");
		for(int i = 0;i < LinkedURLs.size();i++){
			URLpojoQ.add(new URLpojo(LinkedURLs.get(i),Ctg.LINK_TO_LINK));
		}
		//new Thread(new ifengCrawler(""),"");
	}
	
	class ifengCrawler implements Runnable{
		public ifengCrawler(String Id) {
			// TODO Auto-generated constructor stub
			id = Id;
		}
		String id;
		IfengSpider ispider = new IfengSpider();
		final Object signal = new Object(); 
		@Override
		public void run() {
			// TODO Auto-generated method stub
			{
				while(!URLpojoQ.isEmpty() && count < 30000){
					
					synchronized (this){
						tmp = URLpojoQ.poll();
					}
					if(tmp!=null){
						
						if(tmp.getCategory() == Ctg.CONTENT){
							if(vstedURL.contains(tmp.getURL())){//ȥ��
								continue;
							}else{
								vstedURL.add(tmp.getURL());
							}
							//ContentQ.add(ispider.crawl(tmp));
							try {
								nc = ispider.crawl(tmp);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(nc != null){
								String filePath = "test.txt";
								String value ="href: "+nc.getHref()+"\n";
								String encoding = "UTF-8";
								System.out.println(nc.getTitle());
								System.out.println(nc.getHref());
								IOUtil.writeFile(filePath, value, encoding);
								System.out.println("����ҳ�ɹ��������߳�"
										+ Thread.currentThread()
												.getName() + "����");

								count++;
								System.out.println(count);
							}
						}else{
							if(tmp.getCategory() != Ctg.CONTENT){
								Cache = ispider.godeep(tmp);
								URLpojoQ.addAll(Cache);
								Cache.clear();
								synchronized(signal) {  //---------------------��2��  
	                                countOfWait--;  
	                                signal.notify();  
	                            }  
							}
						}
					}else{
						//count++;
						System.out.println("tmpΪ��");
						synchronized (signal) { // ------------------��2��
							try {
								countOfWait++;
								System.out.println("��ǰ��" + countOfWait
										+ "���߳��ڵȴ�");
								signal.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
				}
				
			}
		}	
	}
	public void start(){
		URLpojo homepage = new URLpojo("http://www.ifeng.com",Ctg.HOME);
		Cache = FU.godeep(homepage);
		URLpojoQ.addAll(Cache);//initial
		Cache.clear();
		for(int ii = 0; ii < threadCount; ii++){
			new Thread(new ifengCrawler("thread"+ii),"thread"+ii).start();
		}
	}
	IfengSpider FU = new IfengSpider();
	URLpojo tmp;
	NewsContent nc;
	//һ��������ʱ�洢�ӷ�����ҳ�����������ӣ��ӵ��ܵ�URL����֮�����
	ArrayList<URLpojo> Cache;
	//һ��hash��洢�ѷ���URL����ȥ��
	static HashSet<String> vstedURL;
	//һ����ʱ�洢�������ݵ����飬���ڿ��Բ�Ҫ
	ArrayList<NewsContent> ContentQ = new ArrayList<NewsContent>();
	//URL���У����ڱ��漴�����ʵ�URL��ַ���ù�����ȸ���
	static Queue<URLpojo> URLpojoQ;
	//����������
	ArrayList<String> LinkedURLs;
	// HashMap<String, Integer> depth = new HashMap<String, Integer>();//
	// ������ҳ��url���
	//int crawDepth = 2; // �������
	private int threadCount = 5; // �߳�����
	private int count = 0;//�����������������Ŀ
	private int countOfWait = 0; // ��ʾ�ж��ٸ��̴߳���wait״̬
	public static void main(String args[]){
		new MultiThifengSpiderManager().start();
	}
}
