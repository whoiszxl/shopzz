package com.alipay.demo.trade;

public class Hello {
	
	public Hello() {
		System.out.println("boom");
		new Hello();
	}
	
	public static void main(String[] args) {
		
		Hello hello = new Hello();
	}
	
}
