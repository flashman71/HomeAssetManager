package org.spottedplaid.homeinv.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

///**
//* This software has NO WARRANTY.  It is available ÄS-IS, use at your own risk.
//* 
//* @author gary
//* @version 1.0
//* 
//* UIConstants.java
//* (c) 2013 - Spotted Plaid Productions.
//* 
//* License - Can be copied, modified, and distributed with no fees and/or royalties.  If this is used it would be appreciated if
//*           credit were given, but it is not necessary.
//*
//*/

//
///* ***************************************************************
//Class:    UIConstants
//Purpose:  UI UIConstants
//***************************************************************  */
public class UIConstants {
	
	public final static String S_DB_PROP = "DATABASE_FILE";
	public final static String S_KEYSTORE_PROP = "KEYSTORE";
	public final static String S_ENC_METHOD = "Blowfish";
	
	private final static String S_PROPERTIES_FILE = "homeinv.properties";
	public final static String S_BASE_DB_FILE = "homeinv.sqt";
	public final static String S_BASE_KEYFILE = "homeinv.kst";
	
	public final static String S_DEFAULT_DB = "Create New Database";
	public static String S_DB_FILE = "";
	public static String S_KEYFILE = "";
	
	private final static String S_LIST_DEFAULT = "Select";

	public static Properties getProperties()
	{
		Properties props = new Properties();
		File fileProps = new File(S_PROPERTIES_FILE);
		S_DB_FILE = S_BASE_DB_FILE;
		S_KEYFILE = S_BASE_KEYFILE;
		
		
		if (fileProps.exists())
		{
			InputStream inStreamProps = null;
			try {
			inStreamProps = new FileInputStream(S_PROPERTIES_FILE);
			props.load(inStreamProps);
			inStreamProps.close();
			S_DB_FILE = props.getProperty("DATABASE_FILE");
			S_KEYFILE = props.getProperty("KEYSTORE");
			}
			catch (IOException e)
			{
				System.out.println("WARNING - Exception opening properties file, defaults will be used [" + e.getMessage() + "]");
				S_DB_FILE = props.getProperty(S_DB_PROP);
				S_KEYFILE = props.getProperty(S_KEYSTORE_PROP);
			}
		}
		else
		{
           saveProps(props);		
		}
		
         return props;		
		
	}
	
	public static void saveProps(Properties _props)
	{
		try {
			OutputStream outStreamProps = new FileOutputStream(S_PROPERTIES_FILE);
			
			_props.setProperty(S_KEYSTORE_PROP, S_KEYFILE);
			_props.store(outStreamProps, null);
		}
		catch (IOException e)
		{
		  System.out.println("WARNING - Exception saving new properties file, defaults will be used [" + e.getMessage() + "]");	
		}
		
	}

	public static String getListDefault() {
		return S_LIST_DEFAULT;
	}
	
}
