package com.dcl.blog.util;

import java.util.Random;

public class RandomNum {
	public static String getSuiji(int num){
		String numstr="";
		Random r=new Random();
		for(int i=0;i<=num;i++){
			numstr=numstr+r.nextInt(9);
		}
		return numstr;
	}
}
