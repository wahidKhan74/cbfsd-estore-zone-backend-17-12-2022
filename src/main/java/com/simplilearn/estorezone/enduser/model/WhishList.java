package com.simplilearn.estorezone.enduser.model;

public class WhishList {
	
	private int wishlistId;
	private int productId;
	private int userId;
	
	// no argument constructor
	public WhishList() {	}

	public WhishList(int wishlistId, int productId, int userId, int quantity) {
		super();
		this.wishlistId = wishlistId;
		this.productId = productId;
		this.userId = userId;
	}

	// getter & setters
	public int getProductId() {
		return productId;
	}

	public int getWishlistId() {
		return wishlistId;
	}

	public void setWishlistId(int wishlistId) {
		this.wishlistId = wishlistId;
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

	@Override
	public String toString() {
		return "WhisLlists [wishlistId=" + wishlistId + ", productId=" + productId + ", userId=" + userId + "]";
	}

}
