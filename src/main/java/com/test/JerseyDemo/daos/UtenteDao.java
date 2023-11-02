package com.test.JerseyDemo.daos;

import com.test.JerseyDemo.beans.Utente;
import com.test.JerseyDemo.exceptions.UtenteDaoException;

public interface UtenteDao {

	public Utente getByLogin(String userName, String userPassword) throws UtenteDaoException;
	public void addUtente(Utente u) throws UtenteDaoException;
	public void updateUtente(Utente u) throws UtenteDaoException;
	public Utente getById(int u) throws UtenteDaoException;
		
}
