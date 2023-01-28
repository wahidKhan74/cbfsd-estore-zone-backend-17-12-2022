package com.simplilearn.estorezone.admin.dao;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.simplilearn.estorezone.admin.model.Categories;
import com.simplilearn.estorezone.utility.DB;

public class CategoriesDAO implements DAO<Categories>{

	DB db  = new DB();

	@Override
	public List<Categories> getAll() {
		db.init();
		List<Categories> categoriesList = new ArrayList<Categories>();
		try {
			String sql = "select * from categories";
			ResultSet set = db.executeQuery(sql);
			while(set.next()) { 
				Categories category = new Categories();
				//set or map result set to object
				category.setCategoryId(set.getInt("categoryId"));
				category.setCategoryName(set.getString("categoryName"));
				category.setCategoryDescription(set.getString("categoryDescription"));
				category.setCategoryImageUrl(set.getString("categoryImageUrl"));
				category.setActive(set.getInt("active"));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				category.setAddedOn(format.parse(set.getString("addedOn")));
				//add category into categoriesList
				categoriesList.add(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong :: " + e.getMessage());
		} finally {
			db.destroy();
		}
		return categoriesList;
	}

	@Override
	public Categories getOne(long id) {
		db.init();
		Categories category = new Categories();
		try {
			String sql = "select * from categories where categoryId = " + id;
			ResultSet set = db.executeQuery(sql);
			if (set.next()) {
				//set /map result set to object
				category.setCategoryId(set.getInt("categoryId"));
				category.setCategoryName(set.getString("categoryName"));
				category.setCategoryDescription(set.getString("categoryDescription"));
				category.setCategoryImageUrl(set.getString("categoryImageUrl"));
				category.setActive(set.getInt("active"));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				category.setAddedOn(format.parse(set.getString("addedOn")));
			}
		} catch (Exception e) {
			System.out.println("Something went wrong: " + e);
		}
		return category;
	}

	@Override
	public int save(Categories obj) {
		db.init();
		int rowsAffected = 0;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String addedOnDate = format.format(obj.getAddedOn());
			String sql = "insert into categories(categoryName,categoryDescription,categoryImageUrl,active,addedOn) values('" + obj.getCategoryName() + "', '"
					+ obj.getCategoryDescription() + "', '" + obj.getCategoryImageUrl() + "', " + obj.getActive()
					+ " , '" + addedOnDate + "')";
			rowsAffected = db.executeUpdate(sql);
			String message = (rowsAffected > 0) ? "Category Saved successfully" : "Unable to save category";
			System.out.println(message);
		} catch (Exception e) {
			System.out.println("Exception is: " + e);
		}
		return rowsAffected;
	}

	@Override
	public int update(Categories obj) {
		db.init();
		int rowsAffected = 0;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String addedOnDate = format.format(obj.getAddedOn());
			String sql = "update categories set categoryName = '" + obj.getCategoryName() + "', categoryDescription = '"
					+ obj.getCategoryDescription() + "', categoryImageUrl = '" + obj.getCategoryImageUrl()
					+ "', active = " + obj.getActive() + " , addedOn = '" + addedOnDate + "' where categoryId = "
					+ obj.getCategoryId();
			rowsAffected = db.executeUpdate(sql);
			String message = (rowsAffected > 0) ? "category Updated successfully"
					: "Unable to update category";
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
			String sql = "delete from categories where categoryId = " + id;
			rowsAffected = db.executeUpdate(sql);
			String message = (rowsAffected > 0) ? "Category id deleted" : "Category cannot be deleted";
			System.out.println(message);
		} catch (Exception e) {
			System.out.println("Exception is: " + e);
		}
		return rowsAffected;
	}

}
