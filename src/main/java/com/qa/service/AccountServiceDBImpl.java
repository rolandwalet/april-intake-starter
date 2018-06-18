package com.qa.service;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.qa.domain.Account;
import com.qa.util.JSONUtil;

@Transactional(SUPPORTS)
public class AccountServiceDBImpl {

	@PersistenceContext(unitName = "primary")
	private EntityManager manager;

	private JSONUtil jsonUtil = new JSONUtil();
	
	public String getAllAccounts() {
		TypedQuery<Account> query = manager.createQuery("SELECT a FROM Account a ORDER BY a.id", Account.class);
		return jsonUtil.getJSONForObject(query.getResultList());
	}

	public String getAccountById(long id) {
		return jsonUtil.getJSONForObject(manager.find(Account.class, id));
	}

	@Transactional(REQUIRED)
	public String createAccount(String account) {
		Account newAccount = jsonUtil.getObjectForJSON(account, Account.class);
		manager.persist(newAccount);
		return "{\"message\": \"Account successfully created\"}";
	}

	@Transactional(REQUIRED)
	public String updateAccountById(long id, String account) {
		Account accountToUpdate = manager.find(Account.class, id);
		if (accountToUpdate.equals(null)) {
			return "{\"message\": \"ERROR: Account does not exist!\"}";
		} else {
			accountToUpdate.setFirstName(jsonUtil.getObjectForJSON(account, Account.class).getFirstName());
			accountToUpdate.setSecondName(jsonUtil.getObjectForJSON(account, Account.class).getSecondName());
			accountToUpdate.setAccountNumber(jsonUtil.getObjectForJSON(account, Account.class).getAccountNumber());
			return "{\"message\": \"Account updated!\"}";
		}
	}

	@Transactional(REQUIRED)
	public String deleteAccountById(long id) {
		if (manager.find(Account.class, id).equals(null)) {
			return "{\"message\": \"ERROR: Account not found!\"}";
		}
		manager.remove(manager.find(Account.class, id));
		return "{\"message\": \"Account deleted!\"}";
	}
}
