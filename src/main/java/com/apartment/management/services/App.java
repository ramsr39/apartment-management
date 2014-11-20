package com.apartment.management.services;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
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
    	ApplicationContext ctx = new ClassPathXmlApplicationContext("dataSource-context.xml");
    	DataSource ds = (BasicDataSource)ctx.getBean("dataSource");
    	System.out.println(ds.getConnection());
    }
}
