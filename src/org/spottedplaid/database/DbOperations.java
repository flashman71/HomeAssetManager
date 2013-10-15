package org.spottedplaid.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import org.spottedplaid.database.DbConstants;

///**
//* This software has NO WARRANTY.  It is available ÄS-IS, use at your own risk.
//* 
//* @author gary
//* @version 1.0
//* 
//* DbOperations.java
//* (c) 2013 - Spotted Plaid Productions.
//* 
//* License - Can be copied, modified, and distributed with no fees and/or royalties.  If this is used it would be appreciated if
//*           credit were given, but it is not necessary.
//*
//*/

//
///* ***************************************************************
//Class:    DbOperations
//Purpose:  Database operations - create, replace, update, delete
//***************************************************************  */
public class DbOperations {
	
	/// Class variables
		private Connection dbConn  = null;
		private String sDbFilename = null;
		private File fileDb        = null;
		private int iNumRecords    = 0;
	
		/**
		 * Constructor - Checks for existing database file, if not found creates a new database
		 * @param _sDbFilename
		 */
	public DbOperations(String _sDbFilename)
	{
		sDbFilename = _sDbFilename;
		try {
			Class.forName("org.sqlite.JDBC");
			fileDb = new File(sDbFilename);
			if (!fileDb.exists())
			{
				System.out.println("INFO->Database does not exist...creating");
				initDb();
			}
		} catch (ClassNotFoundException e) {
			System.out.println("FAILED->Class not found [" + e.getMessage() + "]");
			e.printStackTrace();
		}

	}
	
	/**
	 * initDb - Creates new database structure
	 * @return - 0 for success, -1 for failure
	 */
	private int initDb()
	{
      	connectToDb();	
      	Statement stmtInit;

			  try {
				stmtInit = dbConn.createStatement();
				   System.out.println("DEBUG->initDb, Creating table [" + DbConstants.S_UDP + "]");
		           stmtInit.executeUpdate(DbConstants.S_UDP_TABLE);
		           System.out.println("DEBUG->initDb, Creating table [" + DbConstants.S_SITE + "]");
		           stmtInit.executeUpdate(DbConstants.S_SITE_TABLE);
		           System.out.println("DEBUG->initDb, Creating table [" + DbConstants.S_BUILDING + "]");
		           stmtInit.executeUpdate(DbConstants.S_BUILDING_TABLE);
		           System.out.println("DEBUG->initDb, Creating table [" + DbConstants.S_ROOM + "]");
		           stmtInit.executeUpdate(DbConstants.S_ROOM_TABLE);
		           System.out.println("DEBUG->initDb, Creating table [" + DbConstants.S_ITEMS + "]");
		           stmtInit.executeUpdate(DbConstants.S_ITEMS_TABLE);
		           System.out.println("DEBUG->initDb, Creating table [" + DbConstants.S_ITEM_ATTR + "]");
		           stmtInit.executeUpdate(DbConstants.S_ATTR_TABLE);
		           System.out.println("DEBUG->initDb, Creating table [" + DbConstants.S_CONTACT + "]");
		           stmtInit.executeUpdate(DbConstants.S_CONTACT_TABLE);
		           System.out.println("DEBUG->initDb, Creating table [" + DbConstants.S_CONTACT_ATTR + "]");
		           stmtInit.executeUpdate(DbConstants.S_CONTACT_ATTR_TABLE);
		           System.out.println("DEBUG->initDb, Creating index");
		           stmtInit.executeUpdate(DbConstants.S_UDP_IDX1);
		           System.out.println("DEBUG->initDb, Creating index");
		           stmtInit.executeUpdate(DbConstants.S_SITE_IDX1);
		           System.out.println("DEBUG->initDb, Creating index");
		           stmtInit.executeUpdate(DbConstants.S_SITE_IDX2);
		           System.out.println("DEBUG->initDb, Creating index");
		           stmtInit.executeUpdate(DbConstants.S_BUILDING_IDX1);
		           System.out.println("DEBUG->initDb, Creating index");
		           stmtInit.executeUpdate(DbConstants.S_BUILDING_IDX2);
		           System.out.println("DEBUG->initDb, Creating index");
		           stmtInit.executeUpdate(DbConstants.S_ROOM_IDX1);
		           System.out.println("DEBUG->initDb, Creating index");
		           stmtInit.executeUpdate(DbConstants.S_ROOM_IDX2);
		           System.out.println("DEBUG->initDb, Creating index");
		           stmtInit.executeUpdate(DbConstants.S_ITEMS_IDX1);
		           System.out.println("DEBUG->initDb, Creating index");
		           stmtInit.executeUpdate(DbConstants.S_ITEMS_IDX2);
		           System.out.println("DEBUG->initDb, Creating index");
		           stmtInit.executeUpdate(DbConstants.S_ATTR_IDX1);
		           
		           stmtInit.clearBatch();
		           String[] sUdp = DbConstants.S_INS;
		           for (int i=0;i<sUdp.length;i++)
		           {
		        	   stmtInit.addBatch(sUdp[i]);
		           }
		           
		           String[] sCTypes = DbConstants.S_INS_CONT;
		           for (int i=0;i<sCTypes.length;i++)
		           {
		        	   stmtInit.addBatch(sCTypes[i]);
		           }
		           
		           String[] sBTypes = DbConstants.S_INS_BTYPE;
		           for (int i=0; i < sBTypes.length;i++)
		           {
		        	   stmtInit.addBatch(sBTypes[i]);
		           }
		           
		           System.out.println("DEBUG->Inserting into UDP");
		           stmtInit.executeBatch();

			} catch (SQLException e) {
				System.out.println("FAILED->SQLException creating database [" + e.getMessage() + "]");
				e.printStackTrace();
				 return -1;
			}

		return 0;
	}
	
	/**
	 * connectToDb - Establishes connection to database defined by sDbFilename
	 * @return - 0 for success, -1 for failure
	 */
	public int connectToDb()
	{
		System.out.println("DEBUG->connectToDb, Trying filename [" + sDbFilename + "]");
		String sConn = "jdbc:sqlite:" + sDbFilename;
		
		try {
		if (dbConn==null || dbConn.isClosed())
		{
		
			dbConn = DriverManager.getConnection(sConn);
		}
		} catch (SQLException e) {
			System.out.println("DEBUG->connectToDb SQLException [" + e.getMessage() + "]");
			 return -1;
		}
		
		return 0;
	}
	
    /**
     * insertRecord
     * @param _dbRec
     * @return
     */
	public int insertRecord(DbRecord _dbRec)
    {
    	return 0;
    }
	
	public int insertRecord(String _sTable, Hashtable<Object, ?> _htData)
	{
		String sSql = "insert into " + _sTable;
		String sCols = "";
		String sValues = "values(";
		String sTempCol = "";
		String sTempData = "";
		Statement stmtAdd = null;
		int iReturn = 0;
		
		Enumeration<Object> enKeys = _htData.keys();
		while (enKeys.hasMoreElements())
		{
		   sTempCol = enKeys.nextElement().toString();
		   sTempData = (String) _htData.get(sTempCol);
		   sCols = sCols + sTempCol + ",";
		   sValues = sValues + "\"" + sTempData + "\",";
		}
		sCols = sCols.substring(0, sCols.length()-1);
		sValues = sValues.substring(0,sValues.length()-1);

		sSql = sSql + "(" + sCols + ") " + sValues + ")";
		System.out.println("DEBUG->insertRecord, sSql [" + sSql + "]");
		connectToDb();
		try {
			stmtAdd = dbConn.createStatement();
			stmtAdd.executeUpdate(sSql);
			ResultSet rs = stmtAdd.getGeneratedKeys();
			 while (rs.next())
			 {
				 iReturn = rs.getInt("last_insert_rowid()");
				 System.out.println("DEBUG->insertRecord, found rs [" + iReturn + "]");
			 }
		} 
		catch (SQLException se)
		{
			System.out.println("EXCEPTION->insertRecord [" + se.getMessage() + "]");
			se.printStackTrace();
			 iReturn = -1;
		}
		
		return iReturn;
	}
    
    public int updateRecord(String _sTable, int _sMainId, Hashtable<Object,?> _htUpd)
    {
    	String sSql = "update " + _sTable + " set ";
		String sStatement = "";
		String sTempCol = "";
		String sTempData = "";
		Statement stmtAdd = null;
		int iReturn = _sMainId;
		
		Enumeration<Object> enKeys = _htUpd.keys();
		while (enKeys.hasMoreElements())
		{
		   sTempCol = enKeys.nextElement().toString();
		   sTempData = (String) _htUpd.get(sTempCol);
		   
		   sStatement = sStatement + sTempCol + "=\"" + sTempData + "\",";
		}

		sStatement = sStatement.substring(0,sStatement.length()-1);

		sSql = sSql + sStatement + " where id = " + _sMainId;
		System.out.println("DEBUG->updateRecord, sSql [" + sSql + "]");
		connectToDb();
		try {
			stmtAdd = dbConn.createStatement();
			stmtAdd.executeUpdate(sSql);
		} 
		catch (SQLException se)
		{
			System.out.println("EXCEPTION->updateRecord [" + se.getMessage() + "]");
			se.printStackTrace();
			 iReturn = -1;
		}
		
		return iReturn;
    }
    
    public int deleteRecord(String _sTable, int _Id)
    {
    	String sSql = "delete from " + _sTable + " where id = " + _Id;
    	Statement stmtDel = null;
    	int iReturn = 0;
    	
    	connectToDb();
		try {
			stmtDel = dbConn.createStatement();
			stmtDel.executeUpdate(sSql);
		} 
		catch (SQLException se)
		{
			System.out.println("EXCEPTION->deleteRecord [" + se.getMessage() + "]");
			se.printStackTrace();
			 iReturn = -1;
		}
		
    	return iReturn;
    }
    
   public ResultSet getRecords(DbRecord _dbRec)
    {
       String sSql = null;
      
       Statement stmtOut = null;
       ResultSet rs = null;
       
    	switch (_dbRec.getRecordType())
    	{
    	   case DbConstants.S_UDP:
              switch (_dbRec.getRecordName())
              {
                case DbConstants.S_SITE:
    		     sSql = "select udp_value from udp where module_name = \"SITE\"";
    		      break;
    		      
    		    case DbConstants.S_CONTACT:
    			 sSql = "select udp_value from udp where module_name = \"CONTACT_TYPE\"";
    		      break;
    		      
    		    case DbConstants.S_BUILDING:
    		     sSql = "select udp_value from udp where module_name = \"BUILDING_TYPE\"";
    		      break;
              }
              
              break;
    		   
    	   case DbConstants.S_SITE:
    	      if (_dbRec.getRecordName().equals(DbConstants.S_INIT))
    	      {
    	    	  sSql = "select * from site";
    	      }
    	      break;
    	      
    	   case DbConstants.S_CONTACT:
    		   if (_dbRec.getRecordName().equals(DbConstants.S_INIT))
    		   {
    			   sSql = "select * from contact where site_id = " + _dbRec.getInt1();
    		   }
    		   break;

    		   
    	}
    	
    	System.out.println("DEBUG->getRecords, sSql ["+ sSql  +"]");
    	try {
    		  connectToDb();
    		  stmtOut = dbConn.createStatement();
    		  stmtOut.execute(sSql);
    		  rs = stmtOut.getResultSet();
    	}
    	catch (SQLException e)
    	{
    		System.out.println("DEBUG->getRecords, SQLExc ["+ e.getMessage() + "]");
    		  e.printStackTrace();
    	}
    	
    	return rs;
    }    
    
}
