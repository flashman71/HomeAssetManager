package org.spottedplaid.database;

/**
 * This software has NO WARRANTY.  It is available ÄS-IS, use at your own risk.
 * 
 * @author gary
 * @version 1.0
 * 
 * DbConstants.java
 * (c) 2013 - Spotted Plaid Productions.
 * 
 * License - Can be copied, modified, and distributed with no fees and/or royalties.  If this is used it would be appreciated if
 *           credit were given, but it is not necessary.
 *
 */


/* ***************************************************************
Class:    DbConstants
Purpose:  DbConstants used by database operations
***************************************************************  */
public final class DbConstants {

	/// DbConstants defining table names
	public final static String S_UDP = "udp";
	public final static String S_SITE = "site";
	public final static String S_BUILDING = "building";
	public final static String S_ROOM = "room";
	public final static String S_ITEMS = "items";
	public final static String S_ITEM_ATTR = "item_attributes";
	public final static String S_MEDIA = "media";
	public final static String S_CONTACT = "contact";
	public final static String S_CONTACT_ATTR = "contact_attr";
	public final static String S_INIT = "INIT";
	
	
	/// Constant - table definition for udp table
	public final static String S_UDP_TABLE = "CREATE TABLE `udp`" 
            + "( id INTEGER PRIMARY KEY AUTOINCREMENT," 
            + "module_name VARCHAR(255) NOT NULL,"
            + "udp_value VARCHAR(500) NOT NULL )";
	
	
	public final static String S_UDP_IDX1 = "CREATE INDEX IDX_UDP_MODULENAME ON udp(module_name)";
	
	/// Constant - table definition for site table
	public final static String S_SITE_TABLE = "CREATE TABLE `site`" 
            + "( id INTEGER PRIMARY KEY AUTOINCREMENT," 
            + "site_name VARCHAR(255) NOT NULL,"
            + "description VARCHAR(500) NOT NULL,"
            + "acreage VARCHAR(10),"
            + "address VARCHAR(100) NOT NULL,"
            + "address2 VARCHAR(100)," 
            + "city VARCHAR(50) NOT NULL," 
            + "state VARCHAR(10) NOT NULL," 
            + "zipcode VARCHAR(15) NOT NULL)";
	
	public final static String S_SITE_IDX1 = "CREATE INDEX IDX_SITE_ID ON site(id)";
	public final static String S_SITE_IDX2 = "CREATE INDEX IDX_SITE_NAME ON site(site_name)";
	
	/// Constant - table definition for building table
	public final static String S_BUILDING_TABLE = "CREATE TABLE `building`" 
            + "( id INTEGER PRIMARY KEY AUTOINCREMENT," 
            + "building_name VARCHAR(255) NOT NULL,"
            + "description VARCHAR(500) NOT NULL,"
            + "square_ft NUMBER NOT NULL,"
            + "num_rooms NUMBER NOT NULL,"
            + "building_type VARCHAR(30) NOT NULL," 
            + "site_id NUMBER NOT NULL)";
	
	public final static String S_BUILDING_IDX1 = "CREATE INDEX IDX_BUILDING_ID ON building(id)";
	public final static String S_BUILDING_IDX2 = "CREATE INDEX IDX_BUILDING_NAME ON building(building_name)";
	
	/// Constant - table definition for room table
	public final static String S_ROOM_TABLE = "CREATE TABLE `room`" 
            + "( id INTEGER PRIMARY KEY AUTOINCREMENT," 
            + "room_name VARCHAR(255) NOT NULL,"
            + "description VARCHAR(500) NOT NULL,"
            + "square_ft NUMBER,"
            + "dims VARCHAR(50),"
            + "room_location VARCHAR(100),"
            + "room_type VARCHAR(30) NOT NULL," 
            + "building_id NUMBER NOT NULL)";
	
	public final static String S_ROOM_IDX1 = "CREATE INDEX IDX_ROOM_ID ON room(id)";
	public final static String S_ROOM_IDX2 = "CREATE INDEX IDX_ROOM_NAME ON room(room_name)";
	
	/// Constant - table definition for items table
	public final static String S_ITEMS_TABLE = "CREATE TABLE `items`" 
            + "( id INTEGER PRIMARY KEY AUTOINCREMENT," 
            + "item_name VARCHAR(255) NOT NULL,"
            + "description VARCHAR(500) NOT NULL,"
            + "purch_from VARCHAR(100),"
            + "purch_type VARCHAR(100),"
            + "purch_date VARCHAR(30),"
            + "purch_price VARCHAR(25),"
            + "estimated_value VARCHAR(25),"
            + "room_id NUMBER NOT NULL)";
	
	public final static String S_ITEMS_IDX1 = "CREATE INDEX IDX_ITEMS_ID ON items(id)";
	public final static String S_ITEMS_IDX2 = "CREATE INDEX IDX_ITEMS_PURCHFROM ON items(purch_from)";
	
	/// Constant - table definition for item_attributes table
	public final static String S_ATTR_TABLE = "CREATE TABLE `item_attributes`" 
            + "( id INTEGER PRIMARY KEY AUTOINCREMENT," 
            + "attrib_name VARCHAR(255) NOT NULL,"
            + "attrib_value VARCHAR(500) NOT NULL,"
            + "item_id NUMBER NOT NULL)";
	
	public final static String S_ATTR_IDX1 = "CREATE INDEX IDX_ATTR_ID ON item_attributes(id)";
	
	/// Constant - table definition for media table
	public final static String S_MEDIA_TABLE = "CREATE TABLE `media`" 
            + "( id INTEGER PRIMARY KEY AUTOINCREMENT," 
            + "media_name VARCHAR(255) NOT NULL,"
            + "description VARCHAR(500) NOT NULL,"
            + "media_type VARCHAR(50),"
            + "media BLOB,"
            + "item_id NUMBER,"
            + "site_id NUMBER,"
            + "building_id NUMBER)";
	
	public final static String S_MEDIA_IDX1 = "CREATE INDEX IDX_MEDIA_ID ON media(id)";
	public final static String S_MEDIA_IDX2 = "CREATE INDEX IDX_MEDIA_ID ON media(media_name)";
	
	/// Constant - table definition for contact table
	public final static String S_CONTACT_TABLE = "CREATE TABLE `contact`" 
            + "( id INTEGER PRIMARY KEY AUTOINCREMENT," 
            + "first_name VARCHAR(50) NOT NULL,"
            + "last_name VARCHAR(50) NOT NULL,"
            + "contact_type VARCHAR(100),"
            + "home_phone   VARCHAR(25),"
            + "mobile_phone   VARCHAR(25),"
            + "email   VARCHAR(100),"
            + "site_id NUMBER NOT NULL)";
	
	/// Constant - table definition for contact_attr table
	public final static String S_CONTACT_ATTR_TABLE = "CREATE TABLE `contact_attr`" 
            + "( id INTEGER PRIMARY KEY AUTOINCREMENT," 
            + "contact_type VARCHAR(50) NOT NULL,"
            + "contact_value VARCHAR(255) NOT NULL," 
            + "contact_id NUMBER NOT NULL)";
	
	public final static String[] S_INS = new String[]{"insert into udp(module_name,udp_value) values(\"SITE\",\"AL\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"AK\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"AZ\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"AR\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"CA\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"CO\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"CT\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"DE\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"FL\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"GA\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"HI\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"ID\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"IL\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"IN\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"IA\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"KS\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"KY\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"LA\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"ME\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"MD\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"MA\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"MI\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"MN\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"MS\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"MO\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"MT\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"NE\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"NV\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"NH\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"NJ\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"NM\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"NY\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"NC\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"ND\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"OH\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"OK\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"OR\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"PA\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"RI\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"SC\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"SD\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"TN\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"TX\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"UT\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"VA\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"VT\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"WA\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"WV\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"WI\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"WY\")",
            "insert into udp(module_name,udp_value) values(\"SITE\",\"DC\")"};
	

	public final static String[] S_INS_CONT = new String[]{"insert into udp(module_name,udp_value) values(\"CONTACT_TYPE\",\"Parent\")",
		"insert into udp(module_name,udp_value) values(\"CONTACT_TYPE\",\"Child\")",
		"insert into udp(module_name,udp_value) values(\"CONTACT_TYPE\",\"Insurance\")",
		"insert into udp(module_name,udp_value) values(\"CONTACT_TYPE\",\"Emergency\")"};
	
	public final static String[] S_INS_BTYPE = new String[]{"insert into udp(module_name,udp_value) values(\"BUILDING_TYPE\",\"House\")",
		"insert into udp(module_name,udp_value) values(\"BUILDING_TYPE\",\"Garage\")",
		"insert into udp(module_name,udp_value) values(\"BUILDING_TYPE\",\"Storage\")",
		"insert into udp(module_name,udp_value) values(\"BUILDING_TYPE\",\"External\")"};
}
