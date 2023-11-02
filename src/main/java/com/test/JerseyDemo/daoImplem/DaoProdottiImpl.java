package com.test.JerseyDemo.daoImplem;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.test.JerseyDemo.beans.Prodotto;
import com.test.JerseyDemo.daos.DaoProdotti;
import com.test.JerseyDemo.dbconfig.DbConfig;
import com.test.JerseyDemo.exceptions.ProdottoDaoException;

import com.test.JerseyDemo.dbconfig.DbTools;



public class DaoProdottiImpl implements DaoProdotti{
	
	private DbConfig connector;
	
	public DaoProdottiImpl(DbConfig connector) {
		this.connector=connector;
	}
		
	public DbConfig getConnector() {
		return this.connector;
	}

	@Override
	public List<Prodotto> queryAll() {
		List<Prodotto> prodotti = new ArrayList<Prodotto>();
		try (Connection cn= getConnector().getConnection()) {
			Statement st=cn.createStatement();
			String sql="SELECT * FROM prodotti";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Prodotto p =new Prodotto();
				p.setIdProdotto(rs.getInt("idProdotto"));
				p.setTitolo(rs.getString("titolo"));
				p.setSottotitolo(rs.getString("sottotitolo"));
				p.setDescrizione(rs.getString("descrizione"));
				p.setPrezzo(rs.getBigDecimal("prezzo"));
				p.setAliquota(rs.getBigDecimal("aliquota"));
				p.setNomeFile(rs.getString("nomeFile"));
				prodotti.add(p);
			}
			return prodotti;
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return prodotti;
	}

	@Override
	public Prodotto getProdottoById(int idProdotto) throws ProdottoDaoException{
		try (Connection cn= getConnector().getConnection()) {
			String sql="SELECT * FROM prodotti WHERE idProdotto=?";
			PreparedStatement st=cn.prepareStatement(sql);
			st.setInt(1, idProdotto);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				Prodotto p =new Prodotto();
				p.setIdProdotto(rs.getInt("idProdotto"));
				p.setTitolo(rs.getString("titolo"));
				p.setSottotitolo(rs.getString("sottotitolo"));
				p.setDescrizione(rs.getString("descrizione"));
				p.setPrezzo(rs.getBigDecimal("prezzo"));
				p.setAliquota(rs.getBigDecimal("aliquota"));
				p.setNomeFile(rs.getString("nomeFile"));
				return p;
			}else {
				return null;
			}
		}catch(SQLException sqle) {
			throw new ProdottoDaoException("Si e' verificato un problema nel recuperare i prodotti dal database",sqle);
		}
	}

	@Override
	public Prodotto add(Prodotto newProduct) throws ProdottoDaoException{
		if(newProduct==null) return null;
		if(newProduct.getIdProdotto()>0)
			return null;
		try (Connection cn= getConnector().getConnection()) {
			StringBuilder sql=new StringBuilder();
			sql.append("INSERT INTO prodotti (idProdotto,titolo,sottotitolo,");
			sql.append("descrizione,prezzo,aliquota,nomeFile) VALUES (?,?,?,?,?,?,?)");
			PreparedStatement st=cn.prepareStatement(sql.toString());
			int nextId=-1;
			try{
				nextId = DbTools.getNextIntId(cn , "prodotti", "idProdotto");
			}catch( SQLException sqle){
				sqle.printStackTrace();
			}
			
			st.setInt(1, nextId);
			st.setString(2, newProduct.getTitolo());
			st.setString(3, newProduct.getSottotitolo());
			st.setString(4, newProduct.getDescrizione());
			st.setBigDecimal(5, newProduct.getPrezzo());
			st.setBigDecimal(6, newProduct.getAliquota());
			st.setString(7, newProduct.getNomeFile());
			int rowsAffected = st.executeUpdate();
			if(rowsAffected>0) {
				try {
					return getProdottoById(nextId);
				} catch (ProdottoDaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}else {
				return null;
			}
		}catch(SQLException sqle) {
			sqle.printStackTrace();
			return null;
		}
		
	}

	@Override
	public void update(Prodotto p) {
		if(p==null) 
			System.out.println("il prodotto non esiste");
		if(p.getIdProdotto()<1)
			System.out.println("Non esiste alcun prodotto con id "+p.getIdProdotto());
		try (Connection cn= getConnector().getConnection()) {
			StringBuilder sql=new StringBuilder();
			sql.append("UPDATE prodotti set ");
			sql.append("titolo=?, ");
			sql.append("sottotitolo=?, ");
			sql.append("descrizione=?, ");
			sql.append("prezzo=?, ");
			sql.append("aliquota=?, ");
			sql.append("nomeFile=? ");
			sql.append("where prodotti.idProdotto=? ");
			
			PreparedStatement st=cn.prepareStatement(sql.toString());
			st.setString(1, p.getTitolo());
			st.setString(2, p.getSottotitolo());
			st.setString(3, p.getDescrizione());
			st.setBigDecimal(4, p.getPrezzo());
			st.setBigDecimal(5, p.getAliquota());
			st.setString(6, p.getNomeFile());
			st.setInt(7, p.getIdProdotto());
			int rowsAffected = st.executeUpdate();
			if(rowsAffected<1){
				System.out.println("Nessun prodotto e' stato aggiornato");
			}
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}		
		
	}

	@Override
	public void delete(Prodotto p) {
		if(p==null) 
			System.out.println("Il prodotto passato come argomento non e' valorizzato");
		if(p.getIdProdotto()<1)
			System.out.println("Non esiste alcun prodotto con id "+p.getIdProdotto());
		
		try (Connection cn= getConnector().getConnection()) {
			StringBuilder sql=new StringBuilder();
			sql.append("DELETE FROM prodotti ");
			sql.append("where prodotti.idProdotto=? ");
			
			PreparedStatement st=cn.prepareStatement(sql.toString());
			st.setInt(1, p.getIdProdotto());
			int rowsAffected = st.executeUpdate();
			if(rowsAffected<1){
				System.out.println("Nessun prodotto e' stato eliminato");
			}
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
	}

}
