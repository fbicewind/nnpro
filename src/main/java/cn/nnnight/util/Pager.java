package cn.nnnight.util;

import java.io.Serializable;
import java.util.List;

public class Pager<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private int pageSize;
	private int pageNo;
	private int totalSize;
	private int totalNo;
	private List<T> datalist;

	public Pager() {
		super();
	}

	public Pager(int pageSize, int pageNo, int totalSize, List<T> datalist) {
		super();
		this.pageSize = pageSize;
		this.totalSize = totalSize;
		this.datalist = datalist;
		if (totalSize <= 0) {
			this.totalNo = 1;
		} else {
			this.totalNo = (int) ((totalSize + pageSize - 1) / pageSize);
		}
		this.pageNo = this.totalNo < pageNo ? this.totalNo : pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getTotalNo() {
		return totalNo;
	}

	public void setTotalNo(int totalNo) {
		this.totalNo = totalNo;
	}

	public List<T> getDatalist() {
		return datalist;
	}

	public void setDatalist(List<T> datalist) {
		this.datalist = datalist;
	}

}
