package com.apartment.management.services;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{ 
    public static void main( String[] args ) throws SQLException
    {
    	ApplicationContext ctx = new ClassPathXmlApplicationContext("application-context.xml");
    	
    	LoginService ds = (LoginService)ctx.getBean("logService");
    	String paylaod = "{emailId:msgshd@gmail.com,password:ds}";
    	System.out.println(ds.ValidateUser(paylaod).getEntity());
    }
}
