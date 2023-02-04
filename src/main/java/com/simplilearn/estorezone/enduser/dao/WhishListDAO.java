package com.simplilearn.estorezone.enduser.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.simplilearn.estorezone.admin.dao.DAO;
import com.simplilearn.estorezone.enduser.model.WhishList;
import com.simplilearn.estorezone.utility.DB;

public class WhishListDAO implements DAO<WhishList>{
	
	DB db  = new DB();
	
	@Override
	public List<WhishList> getAll() {
		db.init();
		List<WhishList> whishlistList = new ArrayList<WhishList>();
		try {
			String sql = "select * from whishlist";
			ResultSet set = db.executeQuery(sql);
			while(set.next()) { 
				WhishList whishlist = new WhishList();
				whishlist.setWishlistId(set.getInt("wishlistId"));
				whishlist.setProductId(set.getInt("productId"));
				whishlist.setUserId(set.getInt("userId"));
				whishlistList.add(whishlist);
			}
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong :: " + e);
		} finally {
			db.destroy();
		}
		return whishlistList;
	}

	@Override
	public WhishList getOne(long id) {
		db.init();
		WhishList whishlist = new WhishList();
		try {
			String sql = "select * from whishlist where wishlistId="+id;
			ResultSet set = db.executeQuery(sql);
			if(set.next()) {
				whishlist.setWishlistId(set.getInt("wishlistId"));
				whishlist.setProductId(set.getInt("productId"));
				whishlist.setUserId(set.getInt("userId"));
			}
			return whishlist;
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong :: " + e);
		}finally {
			db.destroy();
		}
	}
	
	public WhishList getWhishListByUserId(long userId) {
		db.init();
		WhishList whishlist = new WhishList();
		try {
			String sql = "select * from whishlist where userId="+userId;
			ResultSet set = db.executeQuery(sql);
			if(set.next()) {
				whishlist.setWishlistId(set.getInt("wishlistId"));
				whishlist.setProductId(set.getInt("productId"));
				whishlist.setUserId(set.getInt("userId"));
			}
			return whishlist;
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong :: " + e);
		}finally {
			db.destroy();
		}
	}

	@Override
	public int save(WhishList obj) {
		db.init();
		try {
			String sql = String.format("insert into WhishList(productId, userId,quantity) values ('%d' , '%d')", 
					obj.getProductId(), obj.getUserId());
			int rowsAffed = db.executeUpdate(sql) ;
			String message = ( rowsAffed> 0 ) ? "whishlist Items was created successfully." : "Unable to save items to whishlist.";
			System.out.println(message);
			return rowsAffed;
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong :: " + e);
		}finally {
			db.destroy();
		}
	}

	@Override
	public int update(WhishList obj) {
		db.init();
		try {
			String sql = String.format("update whishlist set productId='%d', userId='%d' where whishlistId='%d'",
					obj.getProductId(), obj.getUserId() , obj.getWishlistId());
			int rowsAffed = db.executeUpdate(sql) ;
			String message = ( rowsAffed> 0 ) ? "whishlist Items was created successfully." : "Unable to save items to whishlist.";
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
			String sql = "delete from whishlist where whishlistId="+id;
			int rowsAffed = db.executeUpdate(sql) ;
			String message = ( rowsAffed > 0 ) ? "whishlist Items was deleted successfully." : "Unable to delete items to whishlist.";
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
			String sql = "delete from whishlist where userId="+userId;
			int rowsAffed = db.executeUpdate(sql) ;
			String message = ( rowsAffed > 0 ) ? "whishlist Items was deleted successfully." : "Unable to delete items to whishlist.";
			System.out.println(message);
			return rowsAffed;
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong :: " + e);
		}
	}
}
