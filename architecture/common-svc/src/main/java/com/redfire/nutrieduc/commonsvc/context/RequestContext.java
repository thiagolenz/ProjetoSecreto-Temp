package com.redfire.nutrieduc.commonsvc.context;

import com.redfire.nutrieduc.entities.account.Account;
import com.redfire.nutrieduc.entities.user.AdminUser;
import com.redfire.nutrieduc.entities.user.User;

/**
 * Class that contains the information of Request Context 
 * @author thiagolenz
 * @since Aug 26, 2014
 *
 */
public class RequestContext {
	private static final int HIGHER_LIMIT = 1000;
	private User user;
	private AdminUser adminUser;
	private Account account;
	
	private int currentPage;
	private int limit;
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
	
	public AdminUser getAdminUser() {
		return adminUser;
	}
	
	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}
	
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public void setLimit(Integer limit) {
		if (limit > HIGHER_LIMIT)
			limit = HIGHER_LIMIT;
		this.limit = limit;
	}
	
	public Integer getLimit() {
		return limit;
	}
}
