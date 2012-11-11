/**
 * 
 */
package com.angelhack.wuw.service;

import java.util.Iterator;
import java.util.List;

import com.angelhack.wuw.db.FacebookUsersDAO;
import com.mongodb.DBObject;

/**
 * @author anurag.kapur
 *
 */
public class FBService {

	public void processUsers() {
		FacebookUsersDAO dao = new FacebookUsersDAO();
		List<DBObject> users = dao.getFacebookUsers();
		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
			DBObject dbObject = (DBObject) iterator.next();
			dbObject.get("_id");
			dbObject.get("accessToken");
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
