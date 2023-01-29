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
import com.simplilearn.estorezone.admin.dao.ProductsDAO;
import com.simplilearn.estorezone.admin.model.Products;
import com.simplilearn.estorezone.admin.model.Response;

@WebServlet("/products")
public class ProductsCrudController extends HttpServlet{

	ProductsDAO productsDAO = new ProductsDAO();
	Response responseDto = new Response();
	
	/**
	 * Get All OR get One Products.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");
		List<Products> ProductsList = new ArrayList<Products>();

		if (id != null) {
			Products product = productsDAO.getOne(Long.parseLong(id));
			ProductsList.add(product);
		} else {
			ProductsList = productsDAO.getAll();
		}
		String jsonResponse = new Gson().toJson(ProductsList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(jsonResponse);
		out.flush();
	}

	
	/**
	 * Create a product.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			Products product = new Products();
			product.setProductTitle(request.getParameter("productTitle"));
			product.setProductDescription(request.getParameter("productDescription"));
			product.setPrice(Integer.parseInt(request.getParameter("price")));
			product.setProductCode(request.getParameter("productCode"));
			product.setRating(Integer.parseInt(request.getParameter("rating")));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			product.setAddedOn(format.parse(request.getParameter("addedOn")));
			
			int rowAffected = productsDAO.save(product);
			if(rowAffected > 0) {
				responseDto.setStatus("Success");
				responseDto.setMessage("Product is created successfully!");
			}else {
				responseDto.setMessage("Failed create product data");
				responseDto.setStatus("Failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseDto.setMessage("Failed create product data");
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
	 * Update a product.
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			Products product = new Products();
			product.setProductId(Integer.parseInt(request.getParameter("productId")));
			product.setProductTitle(request.getParameter("productTitle"));
			product.setProductDescription(request.getParameter("productDescription"));
			product.setPrice(Integer.parseInt(request.getParameter("price")));
			product.setProductCode(request.getParameter("productCode"));
			product.setRating(Integer.parseInt(request.getParameter("rating")));
			product.setThumbnailImage(Integer.parseInt(request.getParameter("thumbnailImage")));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			product.setAddedOn(format.parse(request.getParameter("addedOn")));
			
			int rowAffected =  productsDAO.update(product);
			if(rowAffected > 0) {
				responseDto.setStatus("Success");
				responseDto.setMessage("Product is update successfully!");
			}else {
				responseDto.setMessage("Failed to pdate product data");
				responseDto.setStatus("Failed");
			}
		} catch (Exception e) {
			responseDto.setStatus("Failed");
			responseDto.setMessage("Failed updated product data");
		}
		
		String jsonResponse = new Gson().toJson(responseDto);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(jsonResponse);
		out.flush();
	}
	
	/**
	 * Delete a product
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		
		try {
			int rowAffected = productsDAO.delete(Integer.parseInt(id));
			if(rowAffected > 0) {
				responseDto.setStatus("Success");
				responseDto.setMessage("Product is deleted successfully!");
			}else { 
				responseDto.setStatus("Failed");
				responseDto.setMessage("Product does not exist with id "+id);
			}
		} catch (Exception e) {
			responseDto.setStatus("Failed");
			responseDto.setMessage("Failed deletion of a product");
		}

		String jsonResponse = new Gson().toJson(responseDto);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(jsonResponse);
		out.flush();
	}
}
