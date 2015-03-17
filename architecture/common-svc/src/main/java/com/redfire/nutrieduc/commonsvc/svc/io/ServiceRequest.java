package com.redfire.nutrieduc.commonsvc.svc.io;

import java.io.Serializable;

import com.redfire.nutrieduc.entities.account.Account;

/**
 * Encapsulate the request, records range, current page and entity filter
 * @author thiagolenz
 * @since Aug 26, 2014
 *
 * @param <T>
 */
public class ServiceRequest<T> implements Serializable {

	private static final long serialVersionUID = 2895009578384768374L;
	private T entity;
	private int recordsRange;
	private int currentPage;
	private Account account;
	private boolean admin;

	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	public int getRecordsRange() {
		return recordsRange;
	}
	public void setRecordsRange(Integer recordsRange) {
		this.recordsRange = recordsRange;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}	
	public int getOffset () {
		return currentPage * recordsRange;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
