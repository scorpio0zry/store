package com.domain;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * ������ʵ��
 * @author scorpio0zry
 *
 *
 */
public class Order {
	private String oid;
	private Date ordertime;
	private Double total;
	private Integer state;  // 1:δ����   2:�Ѿ�����,δ����. 3:�ѽᷢ��,û��ȷ���ջ�.  4:�ѽ�ȷ���ջ�,��������.
	private String address;
	private String name;
	private String telephone;
	private User user;
	private List<OrderItem> list = new ArrayList<OrderItem>();
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<OrderItem> getList() {
		return list;
	}
	public void setList(List<OrderItem> list) {
		this.list = list;
	}
	
}
