package com.domain;

import java.util.List;

public class PageBean<T> {
	//当前页数
	private int currPage;
	//总页数
	private int totalPage;
	//总记录数
	private int totalCount;
	//每页记录数
	private int pageEach;
	//每页显示数
	private List<T> list;
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageEach() {
		return pageEach;
	}
	public void setPageEach(int pageEach) {
		this.pageEach = pageEach;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
	
}
