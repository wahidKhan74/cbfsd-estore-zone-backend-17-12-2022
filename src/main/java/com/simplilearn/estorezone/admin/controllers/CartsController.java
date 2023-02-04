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
import com.simplilearn.estorezone.enduser.dao.CartsDAO;
import com.simplilearn.estorezone.enduser.model.Carts;
import com.simplilearn.estorezone.admin.model.Response;

@WebServlet("/cartlist")
public class CartsController extends HttpServlet{

	CartsDAO CartsDAO = new CartsDAO();
	Response responseDto = new Response();
	
	/**
	 * Get All OR get One Carts.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");
		String userId = request.getParameter("userId");
		List<Carts> CartsList = new ArrayList<Carts>();

		if (id != null) {
			Carts cart = CartsDAO.getOne(Long.parseLong(id));
			CartsList.add(cart);
		} else if (userId != null) {
			Carts cart = CartsDAO.getCartsByUserId(Long.parseLong(userId));
			CartsList.add(cart);
		} else {
			CartsList = CartsDAO.getAll();
		}
		String jsonResponse = new Gson().toJson(CartsList);
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
			Carts cart = new Carts();
			cart.setCartId(Integer.parseInt(request.getParameter("cartId")));
			cart.setProductId(Integer.parseInt(request.getParameter("productId")));
			cart.setUserId(Integer.parseInt(request.getParameter("userId")));
			cart.setQuantity(Integer.parseInt(request.getParameter("quantity")));
					
			CartsDAO.save(cart);
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
	 * Update a cart.
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			Carts cart = new Carts();
			cart.setCartId(Integer.parseInt(request.getParameter("cartId")));
			cart.setProductId(Integer.parseInt(request.getParameter("productId")));
			cart.setUserId(Integer.parseInt(request.getParameter("userId")));
			cart.setQuantity(Integer.parseInt(request.getParameter("quantity")));
			
			CartsDAO.update(cart);
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
	 * Delete a cart
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		
		try {
			int rowAffected = CartsDAO.delete(Integer.parseInt(id));
			if(rowAffected > 0) {
				responseDto.setStatus("Success");
				responseDto.setMessage("cart is deleted successfully!");
			}else { 
				responseDto.setStatus("Failed");
				responseDto.setMessage("cart does not exist with id "+id);
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
