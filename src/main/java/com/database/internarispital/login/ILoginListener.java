package com.database.internarispital.login;

import com.database.internarispital.entities.accounts.Account;

public interface ILoginListener
{
	void onLoginChanged(Account account);
}
