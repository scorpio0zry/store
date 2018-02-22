package com.domain;

import java.util.List;

public class PageBean<T> {
	//��ǰҳ��
	private int currPage;
	//��ҳ��
	private int totalPage;
	//�ܼ�¼��
	private int totalCount;
	//ÿҳ��¼��
	private int pageEach;
	//ÿҳ��ʾ��
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
