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
import com.simplilearn.estorezone.admin.dao.OrdersDAO;
import com.simplilearn.estorezone.admin.model.Orders;
import com.simplilearn.estorezone.admin.model.Response;

@WebServlet("/orders")
public class OrdersCrudController extends HttpServlet{

	OrdersDAO ordersDAO = new OrdersDAO();
	Response responseDto = new Response();
	
	/**
	 * Get All OR get One Orders.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");
		List<Orders> OrdersList = new ArrayList<Orders>();

		if (id != null) {
			Orders order = ordersDAO.getOne(Long.parseLong(id));
			OrdersList.add(order);
		} else {
			OrdersList = ordersDAO.getAll();
		}
		String jsonResponse = new Gson().toJson(OrdersList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(jsonResponse);
		out.flush();
	}

	
	/**
	 * Create a order.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			Orders order = new Orders();
			order.setOrderStatus(request.getParameter("orderStatus"));
			order.setTotalItems(Integer.parseInt(request.getParameter("totalItems")));
			order.setItemsSubTotal(Integer.parseInt(request.getParameter("itemsSubTotal")));
			order.setShipmentCharges(Integer.parseInt(request.getParameter("shipmentCharges")));
			order.setTotalAmount(Integer.parseInt(request.getParameter("totalAmount")));
			order.setPaymentStatus(Integer.parseInt(request.getParameter("paymentStatus")));
			order.setPaymentStatusTitle(request.getParameter("paymentStatusTitle"));
			order.setPaymentMethod(Integer.parseInt(request.getParameter("paymentMethod")));
			order.setPaymentMethodTitle(request.getParameter("paymentMethodTitle"));
			order.setUserId(Integer.parseInt(request.getParameter("userId")));		
			
			order.setName(request.getParameter("name"));	
			order.setEmail(request.getParameter("userId"));	
			order.setContact(Long.parseLong(request.getParameter("userId")));	
			order.setAddress(request.getParameter("userId"));	
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			order.setOrderDate(format.parse(request.getParameter("orderDate")));
			
			int rowAffected =  ordersDAO.save(order);;
			if(rowAffected > 0) {
				responseDto.setStatus("Success");
				responseDto.setMessage("order is created successfully!");
			}else {
				responseDto.setMessage("Failed to create order.");
				responseDto.setStatus("Failed");
			}
		} catch (Exception e) {
			responseDto.setMessage("Failed to create order.");
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
	 * Update a order.
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			Orders order = new Orders();
			order.setOrderId(Integer.parseInt(request.getParameter("orderId")));
			order.setOrderStatus(request.getParameter("orderStatus"));
			order.setTotalItems(Integer.parseInt(request.getParameter("totalItems")));
			order.setItemsSubTotal(Integer.parseInt(request.getParameter("itemsSubTotal")));
			order.setShipmentCharges(Integer.parseInt(request.getParameter("shipmentCharges")));
			order.setTotalAmount(Integer.parseInt(request.getParameter("totalAmount")));
			order.setPaymentStatus(Integer.parseInt(request.getParameter("paymentStatus")));
			order.setPaymentStatusTitle(request.getParameter("paymentStatusTitle"));
			order.setPaymentMethod(Integer.parseInt(request.getParameter("paymentMethod")));
			order.setPaymentMethodTitle(request.getParameter("paymentMethodTitle"));
			order.setUserId(Integer.parseInt(request.getParameter("userId")));		
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			order.setOrderDate(format.parse(request.getParameter("orderDate")));
			
			int rowAffected =  ordersDAO.update(order);;
			if(rowAffected > 0) {
				responseDto.setStatus("Success");
				responseDto.setMessage("order is created successfully!");
			}else {
				responseDto.setMessage("Failed to create order.");
				responseDto.setStatus("Failed");
			}
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
	 * Delete a order
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		
		try {
			int rowAffected = ordersDAO.delete(Integer.parseInt(id));
			if(rowAffected > 0) {
				responseDto.setStatus("Success");
				responseDto.setMessage("Order is deleted successfully!");
			}else { 
				responseDto.setStatus("Failed");
				responseDto.setMessage("Order does not exist with id "+id);
			}
		} catch (Exception e) {
			responseDto.setStatus("Failed");
			responseDto.setMessage("Failed deletion of a order");
		}

		String jsonResponse = new Gson().toJson(responseDto);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(jsonResponse);
		out.flush();
	}
}
