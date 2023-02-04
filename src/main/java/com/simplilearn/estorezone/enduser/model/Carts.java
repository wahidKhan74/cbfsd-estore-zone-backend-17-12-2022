package com.simplilearn.estorezone.enduser.model;

public class Carts {
	
	private int cartId;
	private int productId;
	private int userId;
	private int quantity;
	
	// no argument constructor
	public Carts() {	}

	public Carts(int cartId, int productId, int userId, int quantity) {
		super();
		this.cartId = cartId;
		this.productId = productId;
		this.userId = userId;
		this.quantity = quantity;
	}

	// getter & setters
	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Carts [cartId=" + cartId + ", productId=" + productId + ", userId=" + userId + ", quantity=" + quantity
				+ "]";
	}
	
}
