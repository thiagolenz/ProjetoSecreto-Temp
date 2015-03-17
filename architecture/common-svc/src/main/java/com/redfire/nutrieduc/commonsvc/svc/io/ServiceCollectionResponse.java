package com.redfire.nutrieduc.commonsvc.svc.io;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Response of collection that contains the paginate response
 * @author thiagolenz
 * @since Aug 26, 2014
 *
 * @param <T>
 */
public class ServiceCollectionResponse <T> implements Serializable {

	private static final long serialVersionUID = 5689499677429750972L;
	
	private List<T> dataList;
	private int totalRecordsCount;
	
	public ServiceCollectionResponse() {
	}
	
	public ServiceCollectionResponse(List<T> dataList, int totalRecordsCount) {
		super();
		this.dataList = dataList;
		this.totalRecordsCount = totalRecordsCount;
	}

	public ServiceCollectionResponse(List<T> dataList) {
		super();
		this.dataList = dataList;
	}

	public List<T> getDataList() {
		if (dataList == null)
			dataList = new ArrayList<>();
		return dataList;
	}
	
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
	public ServiceCollectionResponse<T> addAll(List<T> results) {
		if (dataList == null)
			dataList = new ArrayList<>();
		dataList.addAll(results);
		return this;
	}
	
	public int getTotalRecordsCount() {
		return totalRecordsCount;
	}
	
	public void setTotalRecordsCount(int totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}
	
}
