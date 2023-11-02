package com.test.JerseyDemo.daos;

import com.test.JerseyDemo.beans.Prodotto;

import com.test.JerseyDemo.exceptions.ProdottoDaoException;

import java.util.List;

public interface DaoProdotti {
	
	public List<Prodotto> queryAll() throws ProdottoDaoException;;
	public Prodotto getProdottoById(int id) throws ProdottoDaoException;;
	public Prodotto add(Prodotto newProduct) throws ProdottoDaoException;;
	public void update(Prodotto product) throws ProdottoDaoException;;
	public void delete(Prodotto p) throws ProdottoDaoException;;

}
