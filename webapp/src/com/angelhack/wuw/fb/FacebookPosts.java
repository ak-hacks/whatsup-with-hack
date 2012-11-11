package com.angelhack.wuw.fb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.angelhack.wuw.db.FacebookPostsDAO;
import com.angelhack.wuw.model.StatusMessage;
import com.mongodb.DBObject;

/**
 * Servlet implementation class FacebookPosts
 */
@WebServlet("/FacebookPosts")
public class FacebookPosts extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(FacebookPosts.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -5207887439065559956L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// empty method
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("IN HERE___");

		String topic = request.getParameter("t");
		String uid = request.getParameter("uid");
		

		System.out.println("uid = " + uid + " topic = " + topic);

		if (topic != null && !"".equals(topic.trim())) {

			FacebookPostsDAO facebookPostsDAO = new FacebookPostsDAO();

			List<DBObject> objList = facebookPostsDAO.getFilteredFacebookPosts(
					topic, uid);

			List<StatusMessage> statusMessageList = new ArrayList<StatusMessage>();
			for (DBObject dbObject : objList) {
				StatusMessage statusMessage = new StatusMessage();

				statusMessage.setStatusId((String) dbObject.get("_id"));
				statusMessage
						.setTime(((Integer) dbObject.get("time")).longValue());
				statusMessage.setMessage((String) dbObject.get("message"));
				statusMessage.setUid((String) dbObject.get("uid"));
				statusMessage.setParentUid((String) dbObject.get("parentUid"));
				statusMessageList.add(statusMessage);
				System.out.println("status----------" +statusMessage);
			}

			
			request.getSession().setAttribute("statusMessageList", statusMessageList);
			response.sendRedirect("/wuw/topic.jsp?t="+topic);
		} else {
			LOGGER.error("No topic to filter");
		}

	}

}
