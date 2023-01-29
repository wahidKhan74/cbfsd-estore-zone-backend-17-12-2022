package com.simplilearn.estorezone.admin.dao;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.simplilearn.estorezone.admin.model.Orders;
import com.simplilearn.estorezone.utility.DB;

public class OrdersDAO implements DAO<Orders>{

	DB db  = new DB();

	@Override
	public List<Orders> getAll() {
		db.init();
		List<Orders> ordersList = new ArrayList<Orders>();
		try {
			String sql = "select * from orders";
			ResultSet set = db.executeQuery(sql);
			while(set.next()) { 
				Orders order = new Orders();
				
				//set or map result set to object
				order.setOrderId(set.getInt("orderId"));
				order.setOrderStatus(set.getString("orderStatus"));
				order.setTotalItems(set.getInt("totalItems"));
				order.setItemsSubTotal(set.getInt("itemsSubTotal"));
				order.setShipmentCharges(set.getInt("shipmentCharges"));
				order.setTotalAmount(set.getInt("totalAmount"));
				order.setPaymentStatus(set.getInt("paymentStatus"));
				order.setPaymentStatusTitle(set.getString("paymentStatusTitle"));
				order.setPaymentMethod(set.getInt("paymentMethod"));
				order.setPaymentMethodTitle(set.getString("paymentMethodTitle"));
				order.setUserId(set.getInt("userId"));
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				order.setOrderDate(format.parse(set.getString("orderDate")));
				//add order into OrdersList
				ordersList.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong :: " + e.getMessage());
		} finally {
			db.destroy();
		}
		return ordersList;
	}

	@Override
	public Orders getOne(long id) {
		db.init();
		Orders order = new Orders();
		try {
			String sql = "select * from orders where orderId = " + id;
			ResultSet set = db.executeQuery(sql);
			if (set.next()) {
				//set /map result set to object
				//set or map result set to object
				order.setOrderId(set.getInt("orderId"));
				order.setOrderStatus(set.getString("orderStatus"));
				order.setTotalItems(set.getInt("totalItems"));
				order.setItemsSubTotal(set.getInt("itemsSubTotal"));
				order.setShipmentCharges(set.getInt("shipmentCharges"));
				order.setTotalAmount(set.getInt("totalAmount"));
				order.setPaymentStatus(set.getInt("paymentStatus"));
				order.setPaymentStatusTitle(set.getString("paymentStatusTitle"));
				order.setPaymentMethod(set.getInt("paymentMethod"));
				order.setPaymentMethodTitle(set.getString("paymentMethodTitle"));
				order.setUserId(set.getInt("userId"));
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				order.setOrderDate(format.parse(set.getString("orderDate")));
			}
		} catch (Exception e) {
			System.out.println("Something went wrong: " + e);
		}
		return order;
	}

	@Override
	public int save(Orders object) {
		db.init();
		int rowsAffected = 0;
		try {
			SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
			String orderDate = format.format(object.getOrderDate());
			String sql = String.format("insert into orders(orderDate,orderStatus, totalItems, itemsSubTotal,shipmentCharges,totalAmount,paymentStatus,"
					+ "paymentStatusTitle,paymentMethod,paymentMethodTitle,userId, name, email, contact, address) "
					+ "values ('%s', '%s', '%d', '%d', '%d', '%d', '%d', '%s', '%d', '%s', '%d', '%s', '%s','%d','%s')", 
				orderDate,
				object.getOrderStatus(),
				object.getTotalItems(),
				object.getItemsSubTotal(),
				object.getShipmentCharges(),
				object.getTotalAmount(),
				object.getPaymentStatus(),
				object.getPaymentStatusTitle(),
				object.getPaymentMethod(),
				object.getPaymentMethodTitle(),
				object.getUserId(),
				object.getName(),
				object.getEmail(),
				object.getContact(),
				object.getAddress()
			);
			rowsAffected = db.executeUpdate(sql);
			String message = (rowsAffected > 0) ? "Order Saved successfully" : "Unable to save order";
			System.out.println(message);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception is: " + e);
		}
		return rowsAffected;
	}

	@Override
	public int update(Orders object) {
		db.init();
		int rowsAffected = 0;
		try {
			SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
			String orderDate = format.format(object.getOrderDate());
			String sql = String.format("update orders set orderDate='%s', orderStatus='%s', totalItems=%d, itemsSubTotal=%d, shipmentCharges=%d, totalAmount=%d, paymentStatus=%d, paymentStatusTitle='%s', paymentMethod=%d, paymentMethodTitle='%s', userId='%s', name='%s', email='%s', contact=%d, address='%s' where orderId=%d", 
				orderDate,
				object.getOrderStatus(),
				object.getTotalItems(),
				object.getItemsSubTotal(),
				object.getShipmentCharges(),
				object.getTotalAmount(),
				object.getPaymentStatus(),
				object.getPaymentStatusTitle(),
				object.getPaymentMethod(),
				object.getPaymentMethodTitle(),
				object.getUserId()
			);
			rowsAffected = db.executeUpdate(sql);
			String message = (rowsAffected > 0) ? "order Updated successfully"
					: "Unable to update order";
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
			String sql = "delete from Orders where orderId = " + id;
			rowsAffected = db.executeUpdate(sql);
			String message = (rowsAffected > 0) ? "order id deleted" : "order cannot be deleted";
			System.out.println(message);
		} catch (Exception e) {
			System.out.println("Exception is: " + e);
		}
		return rowsAffected;
	}

}
