package com.edu.web2.gateway.sys.uimodel;

import java.util.List;

public class SysPageList<T> {

	private Integer total;

	private List<T> rows;

	public SysPageList(Integer total, List<T> rows) {
		this.total = total;
		this.rows = rows;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
