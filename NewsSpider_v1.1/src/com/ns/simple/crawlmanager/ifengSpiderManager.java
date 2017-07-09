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
/**
 * �������Ҫ�Ƕ��ڷ���������һЩ���ƹ��������ƣ�
 * ����չ�����Ƕ��߳�֮���
 * */
public class ifengSpiderManager {
	public ifengSpiderManager(){//���췽��������ʼ��
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
	}
	//Ŀǰֻ��һ�����棬�Ժ������չ����������ɶ��
	IfengSpider ispider = new IfengSpider();
	//��ʱ����
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
	int threadCount = 5; // �߳�����
	int count = 0;//�����������������Ŀ
	int countOfWait = 0; // ��ʾ�ж��ٸ��̴߳���wait״̬
	public void start(){
		URLpojo homepage = new URLpojo("http://www.ifeng.com",Ctg.HOME);
		Cache = ispider.godeep(homepage);
		URLpojoQ.addAll(Cache);
		Cache.clear();


		final Object signal = new Object(); // �̼߳�ͨ�ű���
		// ������ȡʱ��
		long start = System.currentTimeMillis();
		for(int ii = 0; ii < threadCount; ii++){
			new Thread(new Runnable() {
				public void run() {

			while(!URLpojoQ.isEmpty() && count < 30000){
				synchronized (this){
					tmp = URLpojoQ.poll();}

				if(tmp!=null){
					if(vstedURL.contains(tmp.getURL())){//ȥ��
						continue;
					}else{
						vstedURL.add(tmp.getURL());
					}
					if(tmp.getCategory() == Ctg.CONTENT){
						//ContentQ.add(ispider.crawl(tmp));
						try {
							nc = ispider.crawl(tmp);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(nc != null){
//							String filePath = "test.txt";
//							String value ="title:"+ nc.getTitle() +"��,href: "+nc.getHref()+"content:\n"+nc.getContent()+"\n****************\n";
//							String encoding = "UTF-8";
							System.out.println(nc.getTitle());
//							IOUtil.writeFile(filePath, value, encoding);
							System.out.println("����ҳ�ɹ��������߳�"
									+ Thread.currentThread()
											.getName() + "����");

							count++;
							System.out.println(count);
						}
					}else  {
						if(tmp.getCategory() != Ctg.CONTENT){
							Cache = ispider.godeep(tmp);
							URLpojoQ.addAll(Cache);
							Cache.clear();
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

				}}
			}
			
		}},"thread-" + ii).start();}
		
		while (true) {
			if (URLpojoQ.isEmpty() && Thread.activeCount() == 1
					|| countOfWait == threadCount) {
				long end = System.currentTimeMillis();
				System.out.println("�ܹ�����" + count + "����ҳ");
				System.out.println("�ܹ���ʱ" + (end - start) / 1000 + "��");
				System.exit(1);
			}

		}
				
	}
	public static void main(String args[]){
		new ifengSpiderManager().start();;
	}
	
}
