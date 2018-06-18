package com.qa.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import com.qa.domain.Account;
import com.qa.util.JSONUtil;

@Alternative
public class AccountServiceMapImpl implements Service {

	private Map<Long, Account> accountMap;

	@Inject
	private JSONUtil util;

	public AccountServiceMapImpl() {
		accountMap = new HashMap<Long, Account>();
	}

	@Override
	public String getAllAccounts() {
		List<Account> allAccounts = (List<Account>) accountMap.values();
		return util.getJSONForObject(allAccounts);
	}

	@Override
	public String getAccount(long id) {
		return util.getJSONForObject(accountMap.get(id));
	}

	@Override
	public String createAccount(String account) {
		Account userAccount = util.getObjectForJSON(account, Account.class);
		accountMap.put(userAccount.getId(), userAccount);
		return "{\"message\": \"Account successfully created\"}";
	}

	@Override
	public String updateAccount(long id, String account) {
		boolean countExists = accountMap.containsKey(id);
		if (!countExists) {
			return "{\"message\": \"ERROR: Account does not exist!\"}";
		} else {
			accountMap.get(id).setFirstName(util.getObjectForJSON(account, Account.class).getFirstName());
			accountMap.get(id).setSecondName(util.getObjectForJSON(account, Account.class).getSecondName());
			accountMap.get(id).setAccountNumber(util.getObjectForJSON(account, Account.class).getAccountNumber());
			return "{\"message\": \"Account updated!\"}";
		}
	}

	@Override
	public String deleteAccount(long id) {
		boolean countExists = accountMap.containsKey(id);
		if (countExists) {
			accountMap.remove(id);
			return "{\"message\": \"Account deleted!\"}";
		} else
			return "{\"message\": \"ERROR: Account not found!\"}";
	}

	public void setUtil(JSONUtil util) {
		this.util = util;
	}

}
