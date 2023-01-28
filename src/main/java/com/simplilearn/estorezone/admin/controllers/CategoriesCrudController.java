package com.simplilearn.estorezone.admin.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.simplilearn.estorezone.admin.dao.CategoriesDAO;
import com.simplilearn.estorezone.admin.model.Categories;
import com.simplilearn.estorezone.admin.model.Response;

@WebServlet("/categories")
public class CategoriesCrudController extends HttpServlet{

	CategoriesDAO categoriesDAO = new CategoriesDAO();
	Response responseDto = new Response();
	
	/**
	 * Get All OR get One Categories.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");
		List<Categories> categoriesList = new ArrayList<Categories>();

		if (id != null) {
			Categories category = categoriesDAO.getOne(Long.parseLong(id));
			categoriesList.add(category);
		} else {
			categoriesList = categoriesDAO.getAll();
		}
		String jsonResponse = new Gson().toJson(categoriesList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(jsonResponse);
		out.flush();
	}

	
	/**
	 * Create a Category.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			Categories category = new Categories();
			category.setCategoryName(request.getParameter("categoryName"));
			category.setCategoryDescription(request.getParameter("categoryDescription"));
			category.setActive(Integer.parseInt(request.getParameter("active")));
			category.setCategoryImageUrl(request.getParameter("catgeoryImageUrl"));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			category.setAddedOn(format.parse(request.getParameter("addedOn")));
			
			categoriesDAO.save(category);
			responseDto.setStatus("Success");
			responseDto.setMessage("Categrory is created successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			responseDto.setMessage("Failed create categrory data");
			responseDto.setStatus("Failed");
		}
		String jsonResponse = new Gson().toJson(responseDto);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(jsonResponse);
		out.flush();
	}
	
	/**
	 * Update a Category.
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			Categories category = new Categories();
			category.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
			category.setCategoryName(request.getParameter("categoryName"));
			category.setCategoryDescription(request.getParameter("categoryDescription"));
			category.setCategoryImageUrl(request.getParameter("catgeoryImageUrl"));
			category.setActive(Integer.parseInt(request.getParameter("active")));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			category.setAddedOn(format.parse(request.getParameter("addedOn")));
			
			categoriesDAO.update(category);
			responseDto.setStatus("Success");
			responseDto.setMessage("Categrory is updated successfully!");
		} catch (Exception e) {
			responseDto.setStatus("Failed");
			responseDto.setMessage("Failed updated categrory data");
		}
		
		String jsonResponse = new Gson().toJson(responseDto);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(jsonResponse);
		out.flush();
	}
	
	/**
	 * Delete a category
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		
		try {
			int rowAffected = categoriesDAO.delete(Integer.parseInt(id));
			if(rowAffected > 0) {
				responseDto.setStatus("Success");
				responseDto.setMessage("Category is deleted successfully!");
			}else { 
				responseDto.setStatus("Failed");
				responseDto.setMessage("Category does not exist with id "+id);
			}
		} catch (Exception e) {
			responseDto.setStatus("Failed");
			responseDto.setMessage("Failed deletion of a category");
		}

		String jsonResponse = new Gson().toJson(responseDto);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(jsonResponse);
		out.flush();
	}
}
