package com.simplilearn.estorezone.admin.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.simplilearn.estorezone.admin.dao.AdminsDAO;
import com.simplilearn.estorezone.admin.model.Admins;
import com.simplilearn.estorezone.admin.model.Response;

@WebServlet("/admins/login")
public class AdminAuthController extends HttpServlet {

	AdminsDAO adminDao = new AdminsDAO();
	Response responseDto = new Response();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Response responseDto = new Response();
		try {
			// map admin object fields with request parameters
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			Admins admin = adminDao.login(email,password);
			if(admin.getEmail()!=null) {
				responseDto.setStatus("Success");
				responseDto.setMessage("Login successfull");
				responseDto.setData(admin);
			} else {
				responseDto.setStatus("Failed");
				responseDto.setMessage("Failed to login as admin. user");
			}
			
		} catch (Exception e) {
			responseDto.setStatus("Failed");
			responseDto.setMessage("Failed to login as admin user");
		}

		String jsonResponse = new Gson().toJson(responseDto);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(jsonResponse);
		out.flush();
	}
}
