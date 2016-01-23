package com.dcl.blog.alipay;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class TestMain {
	public static void main(String args[]){
		HashMap map=new HashMap<String, String>();
		int a=1;
		map.put(a, "aaa");
		map.put(a, "222");
		Set set=map.entrySet();
		Iterator i=set.iterator();
		while(i.hasNext()){
			System.out.print(i.next());
		}
	}
}
