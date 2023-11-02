package com.test.JerseyDemo.dbconfig;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.test.JerseyDemo.daos.DaoFactory;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

/**
 * Strumenti di utilita' per la gestione delle operazioni di persistenza 
 */
public class DbTools {
			
	public static final String SALT_BASIC="r4a6n3d7o6m4s5a9l0t";
	private static final String ATTR_DAO_FACTORY="DaoFactory";
	
	public static final String PARAM_DB_DRIVER_CLASS="db.driver.class";
	public static final String PARAM_DB_URL="db.url";
	public static final String PARAM_DB_USER="db.user";
	public static final String PARAM_DB_PASS="db.pass";
	public static final String PARAM_DAO_FACTORY_CLASS="dao.factory.class";
	
	private static Object daoFactory=null;
	
	public static DaoFactory getDaoFactory() {
		if(daoFactory==null) {
			JAXBContext jaxbContext;
	    	DbData dbData = new DbData();
			InputStream in = DbTools.class.getResourceAsStream("/dbData.xml");
			try {
				jaxbContext = JAXBContext.newInstance( DbData.class );
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		    	dbData = (DbData) jaxbUnmarshaller.unmarshal( in );
							
				String dbUrl=dbData.getDbUrl();
				String dbUser=dbData.getDbUser();
				String dbPass=dbData.getDbPass();
				String dbDriver=dbData.getDbDriver();
				try {
					Class.forName(dbDriver);
				} catch (ClassNotFoundException e) {
					// TODO Trasferire su eventuale sistema di log
					e.printStackTrace();
				}
				DbConfig config=new DbConfig(dbUrl,dbUser,dbPass);
				try {
					// caricamento per "reflection"
					String daoFactoryClassName=dbData.getDbFactory();
					Class daoFactoryClass=Class.forName(daoFactoryClassName);
					Constructor constructor=daoFactoryClass.getConstructor(DbConfig.class);
					DaoFactory factory = (DaoFactory)constructor.newInstance(config);
					DbTools.daoFactory=factory;
					return factory;
				}catch(Exception ex) {
					ex.printStackTrace();
					return null;
				}
			} catch (JAXBException e) {
				e.printStackTrace();
				return null;
			}
		}else {
			return (DaoFactory)daoFactory;
		}
	}
	
	
	
	/** 
	 * <p>Restituisce una sequenza di caratteri trattati con crittografia hash irreversibile 
	 * basata su algoritmo PBKDF2 con uso di parametro "salt" </p>
	 * @param text Il testo da crittografare
	 * @param salt Il parametro salt di rafforzamento
	 * @return Un oggetto String contenente la sequenza crittografata
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */ 
	public static String getHashed(String text, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		System.out.println(text+" "+salt);
		if(text==null)  
    		throw new IllegalArgumentException("Invalid text");
    	if(salt ==null)
    		throw new IllegalArgumentException("Invalid salt");
    	KeySpec spec = new PBEKeySpec(text.toCharArray(), salt.getBytes(), 65536, 128);
    	SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    	byte[] hash=factory.generateSecret(spec).getEncoded();    	
    	System.out.println(new String(hash));
    	return new String(hash);
    }
	
	/**
	 * Ricerca l'id con valore più alto nella colonna chiave di una tabella
	 * La colonna chiave si presume contenga numeri interi 
	 * @param cn La connessione su cui agire (anche se in transaction)
	 * @param tableName Il nome della tabella di cui ricercare il prossimo id
	 * @param idColumnName Il nome della colonna chiave primaria che contiene gli id
	 * @return Il valore int immediatamente successivo al più alto id presente nella colonna chiave della tabella
	 * @throws SQLException
	 */
	public static int getNextIntId(Connection cn, String tableName, String idColumnName) throws SQLException {
		tableName=tableName.replace("'","''").replace(";","");
		idColumnName=idColumnName.replace("'","''").replace(";","");
		String sql="SELECT MAX("+idColumnName+") as maxId FROM "+tableName;
		Statement ps = cn.createStatement();
		ResultSet rs= ps.executeQuery(sql);
		int currentId=0;
		if(rs.next())
			currentId= rs.getInt(1);
		return (currentId+1);
	}

		
}
