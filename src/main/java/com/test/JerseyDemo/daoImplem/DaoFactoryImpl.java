package com.test.JerseyDemo.daoImplem;


import com.test.JerseyDemo.daos.DaoFactory;
import com.test.JerseyDemo.daos.DaoProdotti;
import com.test.JerseyDemo.daos.UtenteDao;
import com.test.JerseyDemo.dbconfig.DbConfig;


public class DaoFactoryImpl implements DaoFactory{
	
    private DbConfig connector;
	
	public DaoFactoryImpl(DbConfig connector) {
		this.connector=connector;
	}
	
	@Override
	public DaoProdotti getDaoProdotti() {
		return new DaoProdottiImpl(connector);
	}
	
	@Override
	public UtenteDao getDaoUtente() {
		return new UtenteDaoImpl(connector);
	}

    

}
