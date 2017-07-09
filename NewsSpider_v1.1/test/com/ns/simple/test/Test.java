package com.ns.simple.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.ns.simple.enumeration.Ctg;
import com.ns.simple.impl.crawl.IfengSpider;
import com.ns.simple.pojos.URLpojo;


public class Test {
	public static void writeFile(String filePath, String value, String encoding) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(filePath));
			fos.write(value.getBytes(encoding));
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	class T{
		public T(int a, String b) {
			// TODO Auto-generated constructor stub
			A = a;
			B = b;
		}
		int A;
		String B;
	}
	public void Test1(){
		ArrayList<T> arr = new ArrayList<T>();
		Queue<T> q = new LinkedList<T>();
		for(int i = 0;i < 100; i++){
			arr.add(new T(i,"hehe"));
		}
		System.out.println("1111");
		q.addAll(arr);
		arr.clear();
		for(int i = 101;i < 200; i++){
			arr.add(new T(i,"hehe"));
		}
		q.addAll(arr);
		System.out.println("1111");
		T tmp;
		while(!q.isEmpty()){
			tmp = q.poll();
			System.out.println(tmp.A+" "+tmp.B);
		}
	}
	public static void main(String[] args) {
		String a = "asdad";
		String b = "asdd";
//		new Test().Test1();
		System.out.println(a==b);
		System.out.println(a.equals(b));
	}

}
