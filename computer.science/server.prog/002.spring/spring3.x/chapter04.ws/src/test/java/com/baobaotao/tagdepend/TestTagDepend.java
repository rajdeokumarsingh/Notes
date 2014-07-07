package com.baobaotao.tagdepend;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestTagDepend  extends TestCase {
	public ApplicationContext applicationContext = null;

	private static String[] CONFIG_FILES = {"tagdp-beans.xml"};
	public void setUp() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(CONFIG_FILES);
		
	}
	public void testInheritTag(){
		Car car3 = (Car) applicationContext.getBean("car3");
		Car car4 = (Car) applicationContext.getBean("car4");
		assertNotNull(car3);
		assertNotNull(car4);	
	}

	public void testReferenceTag(){
		Boss boss = (Boss) applicationContext.getBean("boss");
		assertNotNull(boss);
		System.out.println(boss);
	}
}
