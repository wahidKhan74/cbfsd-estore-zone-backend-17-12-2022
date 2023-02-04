package com.simplilearn.estorezone.enduser.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.simplilearn.estorezone.admin.dao.DAO;
import com.simplilearn.estorezone.enduser.model.Carts;
import com.simplilearn.estorezone.utility.DB;

public class CartsDAO implements DAO<Carts>{
	DB db  = new DB();
	
	@Override
	public List<Carts> getAll() {
		db.init();
		List<Carts> cartList = new ArrayList<Carts>();
		try {
			String sql = "select * from cartlist";
			ResultSet set = db.executeQuery(sql);
			while(set.next()) { 
				Carts cart = new Carts();
				cart.setCartId(set.getInt("cartId"));
				cart.setProductId(set.getInt("productId"));
				cart.setUserId(set.getInt("userId"));
				cart.setQuantity(set.getInt("quantity"));
				cartList.add(cart);
			}
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong :: " + e);
		} finally {
			db.destroy();
		}
		return cartList;
	}

	@Override
	public Carts getOne(long id) {
		db.init();
		Carts cart = new Carts();
		try {
			String sql = "select * from cartlist where cartId="+id;
			ResultSet set = db.executeQuery(sql);
			if(set.next()) {
				cart.setCartId(set.getInt("cartId"));
				cart.setProductId(set.getInt("productId"));
				cart.setUserId(set.getInt("userId"));
				cart.setQuantity(set.getInt("quantity"));
				
			}
			return cart;
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong :: " + e);
		}finally {
			db.destroy();
		}
	}
	
	public Carts getCartsByUserId(long userId) {
		db.init();
		Carts cart = new Carts();
		try {
			String sql = "select * from cartlist where userId="+userId;
			ResultSet set = db.executeQuery(sql);
			if(set.next()) {
				cart.setCartId(set.getInt("cartId"));
				cart.setProductId(set.getInt("productId"));
				cart.setUserId(set.getInt("userId"));
				cart.setQuantity(set.getInt("quantity"));
				
			}
			return cart;
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong :: " + e);
		}finally {
			db.destroy();
		}
	}

	@Override
	public int save(Carts obj) {
		db.init();
		try {
			String sql = String.format("insert into cartlist(productId, userId,quantity) values ('%d' , '%d', '%d')", 
					obj.getProductId(), obj.getUserId(), obj.getQuantity());
			int rowsAffed = db.executeUpdate(sql) ;
			String message = ( rowsAffed> 0 ) ? "Cart Items was created successfully." : "Unable to save items to cart.";
			System.out.println(message);
			return rowsAffed;
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong :: " + e);
		}finally {
			db.destroy();
		}
	}

	@Override
	public int update(Carts obj) {
		db.init();
		try {
			String sql = String.format("update cartlist set productId='%d', userId='%d' , quantity='%d' where cartId='%d'",
					obj.getProductId(), obj.getUserId(),obj.getQuantity(),  obj.getCartId());
			int rowsAffed = db.executeUpdate(sql) ;
			String message = ( rowsAffed> 0 ) ? "Cart Items was created successfully." : "Unable to save items to cart.";
			System.out.println(message);
			return rowsAffed;
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong :: " + e);
		}finally {
			db.destroy();
		}
	}

	@Override
	public int delete(long id) {
		db.init();
		try {
			String sql = "delete from cartlist where cartId="+id;
			int rowsAffed = db.executeUpdate(sql) ;
			String message = ( rowsAffed > 0 ) ? "Cart Items was deleted successfully." : "Unable to delete items to cart.";
			System.out.println(message);
			return rowsAffed;
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong :: " + e);
		} finally {
			db.destroy();
		}
	}

	
	public int deleteByUserId(long userId) {
		try {
			String sql = "delete from cartlist where userId="+userId;
			int rowsAffed = db.executeUpdate(sql) ;
			String message = ( rowsAffed > 0 ) ? "Cart Items was deleted successfully." : "Unable to delete items to cart.";
			System.out.println(message);
			return rowsAffed;
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong :: " + e);
		}
	}
}
