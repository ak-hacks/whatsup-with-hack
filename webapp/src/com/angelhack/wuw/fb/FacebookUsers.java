package com.angelhack.wuw.fb;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.angelhack.wuw.db.FacebookUsersDAO;

/**
 * Servlet implementation class FacebookUsers
 */
@WebServlet("/FacebookUsers")
public class FacebookUsers extends HttpServlet {

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -4165097819968116835L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FacebookUsers() {
		super();
	}

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
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		PrintWriter servletOutput = res.getWriter();

		String uid = req.getParameter("uid");
		String accessTokenString = req.getParameter("accessToken");

		if (accessTokenString == null || uid == null
				|| "".equals(accessTokenString.trim()) || "".equals(uid.trim())) {

			System.err.println("Uh oh, access token is not available");
			servletOutput.println("Uh oh, something's wrong :(");

		} else {

			FacebookUsersDAO facebookUsersDAO = new FacebookUsersDAO();
			facebookUsersDAO.insertFacebookUser(uid, accessTokenString);
		}

		servletOutput.close();
	}

}
