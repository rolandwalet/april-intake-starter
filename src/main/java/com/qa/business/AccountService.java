package com.qa.business;

import javax.inject.Inject;

import com.qa.domain.Account;
import com.qa.repository.*;
import com.qa.util.JSONUtil;

public class AccountService implements IAccountService {

	@Inject
	private Service service;
	@Inject
	private JSONUtil util;
	
	public AccountService() {
		
	}
	
	@Override
	public String getAllAccounts() {
		return service.getAllAccounts();
	}

	@Override
	public String getAccount(long id) {
		return service.getAccount(id);
	}

	@Override
	public String createAccount(String account) {
		if (util.getObjectForJSON(account, Account.class).getAccountNumber().equals("9999")) {
			return "{\"message\": \"ERROR: Account blocked!\"}";
		} else
			return service.createAccount(account);
	}

	@Override
	public String updateAccount(long id, String account) {
		return service.updateAccount(id, account);
	}

	@Override
	public String deleteAccount(long id) {
		return service.deleteAccount(id);
	}

	public void setService(Service service) {
		this.service = service;
	}

	public void setUtil(JSONUtil util) {
		this.util = util;
	}

}
