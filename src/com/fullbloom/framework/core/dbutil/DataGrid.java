package com.fullbloom.framework.core.dbutil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fullbloom.framework.util.ContextHolderUtils;


/**
 * easyui的datagrid向后台传递参数使用的model
 * 
 * @author
 * 
 */
public class DataGrid implements Serializable {

	private int page = 1;// 当前页
	private int rows = 10;// 每页显示记录数
	private String sort = null;// 排序字段名
	private SortDirection order = SortDirection.asc;// 按什么排序(asc,desc)
	private String field;// 字段
	private String treefield;// 树形数据表文本字段
	private List<?> results = new ArrayList();// 结果集
	private int total;// 总记录数
	private String footer;// 合计列
	private String footName;// 合计列名,在哪列显示"合计"
	private String sqlbuilder;// 合计列
	private int startColumn = 1; // 分页起始位置
	private int endColumn = 10; // 分页结束位置

	// 是否指定行数, 区分导出和非导出(页面传入和调用setRows方法)
	private boolean isSpecifiedNumber = false;

	public String getSqlbuilder() {
		return sqlbuilder;
	}

	public void setSqlbuilder(String sqlbuilder) {
		this.sqlbuilder = sqlbuilder;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
		this.startColumn = (this.getPage() - 1) * this.getRows() + 1;
		this.endColumn = this.getPage() * this.getRows();
		if (this.total < this.endColumn) {
			this.endColumn = this.total;
		}
	}

	public String getField() {
		return field;
	}

	public List getResults() {
		return results;
	}

	public void setResults(List results) {
		this.results = results;
	}

	public void setField(String field) {
		this.field = field;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		if (ContextHolderUtils.getRequest() != null && ContextHolderUtils.getRequest().getParameter("rows") != null) {
			return rows;
		} else if (isSpecifiedNumber)
			return rows;
		else
			return 10000;
	}

	public void setRows(int rows) {
		this.rows = rows;
		isSpecifiedNumber = true;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public SortDirection getOrder() {
		return order;
	}

	public void setOrder(SortDirection order) {
		this.order = order;
	}

	public String getTreefield() {
		return treefield;
	}

	public void setTreefield(String treefield) {
		this.treefield = treefield;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getFootName() {
		return footName;
	}

	public void setFootName(String footName) {
		this.footName = footName;
	}

	public int getStartColumn() {
		return startColumn;
	}

	public void setStartColumn(int startColumn) {
		this.startColumn = startColumn;
	}

	public int getEndColumn() {
		return endColumn;
	}

	public void setEndColumn(int endColumn) {
		this.endColumn = endColumn;
	}

}
