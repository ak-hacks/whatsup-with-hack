package com.angelhack.wuw.db;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

/**
 * @author anurag.kapur
 *
 */
public class DBConnectionManager {

	private static DB db;
	
	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static DB getDatabase() throws UnknownHostException {
		if(db == null) {
			Mongo m = new Mongo( "localhost" , 27017 );
			db = m.getDB( "whatsupwith" );
		}
		return db;
	}
	
	public static DBCollection getCollection(String name) throws UnknownHostException {
		return getDatabase().getCollection(name);
	}
	
	public static void main(String args[]) {
		Set<String> colls;
		try {
			colls = getDatabase().getCollectionNames();
			for (String s : colls) {
			    System.out.println(s);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
