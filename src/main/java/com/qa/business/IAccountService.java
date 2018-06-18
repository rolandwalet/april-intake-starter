package com.qa.business;

public interface IAccountService {

	String getAllAccounts();

	String getAccount(long id);

	String createAccount(String account);

	String updateAccount(long id, String account);

	String deleteAccount(long id);

}