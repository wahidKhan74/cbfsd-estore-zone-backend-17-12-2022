package com.simplilearn.estorezone.admin.dao;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.simplilearn.estorezone.admin.model.Products;
import com.simplilearn.estorezone.utility.DB;

public class ProductsDAO implements DAO<Products>{

	DB db  = new DB();

	@Override
	public List<Products> getAll() {
		db.init();
		List<Products> productsList = new ArrayList<Products>();
		try {
			String sql = "select * from products";
			ResultSet set = db.executeQuery(sql);
			while(set.next()) { 
				Products product = new Products();
				//set or map result set to object
				product.setProductId(set.getInt("productId"));
				product.setProductTitle(set.getString("productTitle"));
				product.setProductDescription(set.getString("productDescription"));
				product.setPrice(set.getInt("price"));
				product.setProductCode(set.getString("productCode"));
				product.setRating(set.getInt("rating"));
				product.setThumbnailImage(set.getInt("thumbnailImage"));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				product.setAddedOn(format.parse(set.getString("addedOn")));
				//add product into ProductsList
				productsList.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong :: " + e.getMessage());
		} finally {
			db.destroy();
		}
		return productsList;
	}

	@Override
	public Products getOne(long id) {
		db.init();
		Products product = new Products();
		try {
			String sql = "select * from products where productId = " + id;
			ResultSet set = db.executeQuery(sql);
			if (set.next()) {
				//set /map result set to object
				product.setProductId(set.getInt("productId"));
				product.setProductTitle(set.getString("productTitle"));
				product.setProductDescription(set.getString("productDescription"));
				product.setPrice(set.getInt("price"));
				product.setProductCode(set.getString("productCode"));
				product.setRating(set.getInt("rating"));
				product.setThumbnailImage(set.getInt("tumnailImage"));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				product.setAddedOn(format.parse(set.getString("addedOn")));
			}
		} catch (Exception e) {
			System.out.println("Something went wrong: " + e);
		}
		return product;
	}

	@Override
	public int save(Products obj) {
		db.init();
		int rowsAffected = 0;
		try {
			String sql = "insert into products (productTitle, productDescription, price, rating, productCode) values('" + obj.getProductTitle() + "', '"
					+ obj.getProductDescription() + "', " + obj.getPrice() + ", " + obj.getRating()
					+ ", '" + obj.getProductCode() + "')";
			rowsAffected = db.executeUpdate(sql);
			String message = (rowsAffected > 0) ? "Product Saved successfully" : "Unable to save product";
			System.out.println(message);
		} catch (Exception e) {
			System.out.println("Exception is: " + e);
		}
		return rowsAffected;
	}

	@Override
	public int update(Products obj) {
		db.init();
		int rowsAffected = 0;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String addedOnDate = format.format(obj.getAddedOn());
			String sql = "update products set productTitle = '" + obj.getProductTitle() + "', productDescription = '"
					+ obj.getProductDescription() + "', price = " + obj.getPrice()
					+ ", rating = " + obj.getRating() + " , addedOn = '" + addedOnDate + "' where productId = "
					+ obj.getProductId();
			rowsAffected = db.executeUpdate(sql);
			String message = (rowsAffected > 0) ? "product Updated successfully"
					: "Unable to update product";
			System.out.println(message);
		} catch (Exception e) {
			System.out.println("Exception is: " + e);
		}
		return rowsAffected;
	}

	@Override
	public int delete(long id) {
		db.init();
		int rowsAffected = 0;
		try {
			String sql = "delete from products where productId = " + id;
			rowsAffected = db.executeUpdate(sql);
			String message = (rowsAffected > 0) ? "Product id deleted" : "Product cannot be deleted";
			System.out.println(message);
		} catch (Exception e) {
			System.out.println("Exception is: " + e);
		}
		return rowsAffected;
	}

}
