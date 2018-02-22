package com.domain;
// 单个购物订单
public class CartItem {
	private String pid;
	private int num = 0;  //数量
	private double subtotal = 0D;  //小计
	private Product product;
	private User user;
	public CartItem() {
	}
	
	public CartItem(Product product, int num) {
		super();
		this.product = product;
		this.num = num;
		this.subtotal = this.num * this.product.getShop_price();
	}
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public double getSubtotal() {
		return subtotal;
	}
	
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public void resetSubtotal(){
		subtotal = num * product.getShop_price();
	}

	
	
}
