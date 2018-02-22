package com.domain;

import java.util.LinkedHashMap;
import java.util.Map;


//���ﳵ��
public class Cart {
	
	private double total = 0D;  //�ܽ��
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
	//��ӹ��ﳵ���� addCart
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
	
	//ɾ�����ﳵ���� removeCart
	public void removeCart(String pid){
		map.remove(pid);
		setTotal();
	}
	
	//��չ��ﳵ clearCart
	public void clearCart(){
		map.clear();
		total = 0D;
	}
	
	//�����ܼ۸�
	public void setTotal(){
		total = 0D;
		for (String pid : map.keySet()) {
			total += map.get(pid).getSubtotal();
		}
	}
	
	
	
}
