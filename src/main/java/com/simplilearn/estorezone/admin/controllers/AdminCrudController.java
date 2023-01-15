package com.simplilearn.estorezone.admin.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.simplilearn.estorezone.admin.dao.AdminsDAO;
import com.simplilearn.estorezone.admin.model.Admins;
import com.simplilearn.estorezone.admin.model.Response;

@WebServlet("/admins")
public class AdminCrudController extends HttpServlet {
	
	AdminsDAO adminDao = new AdminsDAO();
	Response responseDto = new Response();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		List<Admins> adminList = adminDao.getAll();
		String jsonResponse = null;
		try {
			if (id != null && Integer.parseInt(id )!=0) {
				Admins admin = adminDao.getOne(Long.parseLong(id));
				jsonResponse = new Gson().toJson(admin);
			} else {
				adminList = adminDao.getAll();
				jsonResponse = new Gson().toJson(adminList);
			}
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(jsonResponse);
			out.flush();
		} catch (Exception e) {
			System.out.println("-- Something Went Wrong: -- " + e);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Response responseDto = new Response();
		try {
			// map admin object fields with request parameters
			Admins admin = new Admins();
			
			admin.setEmail(request.getParameter("email"));
			admin.setPassword(request.getParameter("password"));
			admin.setFullName(request.getParameter("fullName"));
			admin.setLoginType(Integer.parseInt(request.getParameter("loginType")));

			adminDao.save(admin);
			responseDto.setStatus("Success");
			responseDto.setMessage("Admin object saved successfully");
			responseDto.setData(admin);
		} catch (Exception e) {
			responseDto.setStatus("Failed");
			responseDto.setMessage("Something Went Wrong , Failed to create admin data");
		}
		String jsonResponse = new Gson().toJson(responseDto);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(jsonResponse);
		out.flush();

	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Response responseDto = new Response();
		try {
			// map admin object fields with request parameters
			Admins admin = new Admins();
			admin.setAdminId((Integer.parseInt(request.getParameter("adminId"))));
			admin.setEmail(request.getParameter("email"));
			admin.setPassword(request.getParameter("password"));
			admin.setFullName(request.getParameter("fullName"));
			admin.setLoginType(Integer.parseInt(request.getParameter("loginType")));

			adminDao.update(admin);
			responseDto.setStatus("Success");
			responseDto.setMessage("Admin object updated successfully");
			responseDto.setData(admin);
		} catch (Exception e) {
			responseDto.setStatus("Failed");
			responseDto.setMessage("Something Went Wrong , Failed to update admin data");
		}

		String jsonResponse = new Gson().toJson(responseDto);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(jsonResponse);
		out.flush();
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String id = request.getParameter("adminId");
			int rowAffected = adminDao.delete(Integer.parseInt(id));
			if(rowAffected > 0) {
				responseDto.setStatus("Success");
				responseDto.setMessage("Admin object deleted successfully");
			} else {
				throw new Exception();
			}
			
		} catch (Exception e) {
			responseDto.setStatus("Failed");
			responseDto.setMessage("Something Went Wrong , Failed to delete admin data");
		}
		
		String jsonResponse = new Gson().toJson(responseDto);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(jsonResponse);
		out.flush();
	}
}
