package com.domain;

import java.util.LinkedHashMap;
import java.util.Map;


//购物车类
public class Cart {
	
	private double total = 0D;  //总金额
	private Map<String, CartItem> map = new LinkedHashMap<String, CartItem>();
	private User user;
	
	public double getTotal() {
		return total;
	}
	public Map<String, CartItem> getMap() {
		return map;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	//添加购物车订单 addCart
	public void addCart(CartItem cartItem){
		String pid = cartItem.getProduct().getPid();
		if(map.containsKey(pid)){
			CartItem _cartItem = map.get(pid);
			_cartItem.setNum(map.get(pid).getNum() + cartItem.getNum());
		}else{
			map.put(pid, cartItem);
		}
		
		setTotal();
	}
	
	//删除购物车订单 removeCart
	public void removeCart(String pid){
		map.remove(pid);
		setTotal();
	}
	
	//清空购物车 clearCart
	public void clearCart(){
		map.clear();
		total = 0D;
	}
	
	//计算总价格
	public void setTotal(){
		total = 0D;
		for (String pid : map.keySet()) {
			total += map.get(pid).getSubtotal();
		}
	}
	
	
	
}
