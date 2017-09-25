package cn.e3.common.pojo;

import java.io.Serializable;
import java.util.List;

import cn.e3.pojo.TbItem;

public class EasyUIVo<T> implements Serializable{
	private Long total;
	private List<T> rows;
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
}
