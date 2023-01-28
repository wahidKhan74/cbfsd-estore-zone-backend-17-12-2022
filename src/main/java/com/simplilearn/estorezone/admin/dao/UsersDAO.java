package com.simplilearn.estorezone.admin.dao;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.simplilearn.estorezone.admin.model.Users;
import com.simplilearn.estorezone.utility.DB;

public class UsersDAO implements DAO<Users>{

	DB db  = new DB();

	@Override
	public List<Users> getAll() {
		db.init();
		List<Users> usersList = new ArrayList<Users>();
		try {
			String sql = "select * from users";
			ResultSet set = db.executeQuery(sql);
			Users user = new Users();
			while (set.next()) {
				user.setUserId(set.getInt("userId"));
				user.setEmail(set.getString("email"));
				user.setPassword(set.getString("password"));
				user.setFullName(set.getString("fullName"));
				user.setStreet(set.getString("street"));
				user.setCity(set.getString("city"));
				user.setState(set.getString("state"));
				user.setCountry(set.getString("country"));
				user.setPincode(set.getInt("pincode"));
				user.setImage(set.getString("image"));
				user.setContact(set.getLong("contact"));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				user.setAddedOn(format.parse(set.getString("addedOn")));
				usersList.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong :: " + e.getMessage());
		} finally {
			db.destroy();
		}
		return usersList;
	}

	@Override
	public Users getOne(long id) {
		db.init();
		Users user = new Users();
		try {
			String sql = "select * from users where userId = " + id;
			ResultSet set = db.executeQuery(sql);
			if (set.next()) {
				user.setUserId(set.getInt("userId"));
				user.setEmail(set.getString("email"));
				user.setPassword(set.getString("password"));
				user.setFullName(set.getString("fullName"));
				user.setStreet(set.getString("street"));
				user.setCity(set.getString("city"));
				user.setState(set.getString("state"));
				user.setCountry(set.getString("country"));
				user.setPincode(set.getInt("pincode"));
				user.setImage(set.getString("image"));
				user.setContact(set.getLong("contact"));
			}
		} catch (Exception e) {
			System.out.println("Something went wrong: " + e);
		}
		return user;
	}

	@Override
	public int save(Users obj) {
		db.init();
		int rowsAffected = 0;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String addedOnDate = format.format(obj.getAddedOn());
			String sql = String.format(
					"insert into users values(null, '%s', '%s', '%s', '%s', '%s', '%s', '%s', %d, '%s', %d, '%s')",
					obj.getEmail(), obj.getPassword(), obj.getFullName(), obj.getStreet(), obj.getCity(),
					obj.getState(), obj.getCountry(), obj.getPincode(), obj.getImage(), obj.getContact(), addedOnDate);
			rowsAffected = db.executeUpdate(sql);
			String message = ( rowsAffected> 0) ? "User saved successfully" : "Unable to save user";
			System.out.println(message);
		} catch (Exception e) {
			System.out.println("Exception message is " + e.getMessage());
		}
		return rowsAffected;
	}

	@Override
	public int update(Users obj) {
		db.init();
		int rowsAffected = 0;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String addedOnDate = format.format(obj.getAddedOn());
			String sql = String.format(
					"update users set email='%s', password='%s', fullName='%s', street='%s', city='%s', state='%s', country='%s', pincode='%s', image='%s', contact='%d', addedonDate='%s' where userId=%d",
					obj.getEmail(), obj.getPassword(), obj.getFullName(), obj.getStreet(), obj.getCity(),
					obj.getState(), obj.getCountry(), obj.getPincode(), obj.getImage(), obj.getContact(), addedOnDate,
					obj.getUserId()

			);
			rowsAffected = db.executeUpdate(sql);
			String message = ( rowsAffected > 0) ? "User upadted successfully" : "Unable to update user";
			System.out.println(message);

		} catch (Exception e) {
			System.out.println("Exception message is " + e.getMessage());
		}
		return rowsAffected;
	}

	@Override
	public int delete(long id) {
		db.init();
		int rowsAffected = 0;
		try {
			String sql = "delete from users where userId = " + id;
			rowsAffected = db.executeUpdate(sql);
			String message = (rowsAffected > 0) ? "User deleted successfully" : "Unable to delete user";
			System.out.println(message);
		} catch (Exception e) {
			System.out.println("Exception is: " + e);
		}
		return rowsAffected;
	}
	
	
}
