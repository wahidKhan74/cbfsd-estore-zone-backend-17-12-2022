package com.simplilearn.estorezone.admin.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.simplilearn.estorezone.enduser.dao.WhishListDAO;
import com.simplilearn.estorezone.enduser.model.WhishList;
import com.simplilearn.estorezone.admin.model.Response;

@WebServlet("/whishlist")
public class WhishListController extends HttpServlet{

	WhishListDAO whishListDAO = new WhishListDAO();
	Response responseDto = new Response();
	
	/**
	 * Get All OR get One WhishList.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");
		String userId = request.getParameter("userId");
		List<WhishList> whishListList = new ArrayList<WhishList>();

		if (id != null) {
			WhishList whishList = whishListDAO.getOne(Long.parseLong(id));
			whishListList.add(whishList);
		} else if (userId != null) {
			WhishList whishList = whishListDAO.getWhishListByUserId(Long.parseLong(id));
			whishListList.add(whishList);
		}else {
			whishListList = whishListDAO.getAll();
		}
		String jsonResponse = new Gson().toJson(whishListList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(jsonResponse);
		out.flush();
	}

	
	/**
	 * Create a cart.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			WhishList whishList = new WhishList();
			whishList.setWishlistId(Integer.parseInt(request.getParameter("whishlistId")));
			whishList.setProductId(Integer.parseInt(request.getParameter("productId")));
			whishList.setUserId(Integer.parseInt(request.getParameter("userId")));
					
			whishListDAO.save(whishList);
			responseDto.setStatus("Success");
			responseDto.setMessage("Whishlist item is created successfully!");
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
	 * Update a cart.
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			WhishList whishList = new WhishList();
			whishList.setWishlistId(Integer.parseInt(request.getParameter("whishlistId")));
			whishList.setProductId(Integer.parseInt(request.getParameter("productId")));
			whishList.setUserId(Integer.parseInt(request.getParameter("userId")));
			
			whishListDAO.update(whishList);
			responseDto.setStatus("Success");
			responseDto.setMessage("Whishlist item is updated successfully!");
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
	 * Delete a cart
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		
		try {
			int rowAffected = whishListDAO.delete(Integer.parseInt(id));
			if(rowAffected > 0) {
				responseDto.setStatus("Success");
				responseDto.setMessage("Whishlist item is deleted successfully!");
			}else { 
				responseDto.setStatus("Failed");
				responseDto.setMessage("Whishlist item does not exist with id "+id);
			}
		} catch (Exception e) {
			responseDto.setStatus("Failed");
			responseDto.setMessage("Failed deletion of a cart");
		}

		String jsonResponse = new Gson().toJson(responseDto);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(jsonResponse);
		out.flush();
	}
}
