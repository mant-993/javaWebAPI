package com.test.JerseyDemo.endpoints;

import java.util.ArrayList;
import java.util.List;

import com.test.JerseyDemo.beans.Prodotto;
import com.test.JerseyDemo.beans.Utente;
import com.test.JerseyDemo.daos.DaoFactory;
import com.test.JerseyDemo.daos.DaoProdotti;
import com.test.JerseyDemo.dbconfig.DbTools;
import com.test.JerseyDemo.exceptions.ProdottoDaoException;
import com.test.JerseyDemo.security.LoginBind;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("prodotti")
@LoginBind
public class ProdottiEP {
	
	@PathParam("idProdotto") private String idProdotto;
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Prodotto> fetchProdotti(){
		
		
    	DaoFactory df = DbTools.getDaoFactory();
        DaoProdotti daoProd = df.getDaoProdotti();
        List<Prodotto> prodotti = new ArrayList<Prodotto>();
		try {
			prodotti = daoProd.queryAll();
			
		} catch (ProdottoDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prodotti;
        
    }
	
	

	@GET
	@LoginBind
	@Path("{idProdotto}")
	@Produces(MediaType.APPLICATION_JSON)  // tipo di dati in risposta
	public Prodotto getProdotto() {
		DaoFactory factory = DbTools.getDaoFactory();
		DaoProdotti dao = factory.getDaoProdotti();
		Prodotto p = new Prodotto();
		try {
			int id = Integer.parseInt(idProdotto);
			p = dao.getProdottoById(id);
		} catch (ProdottoDaoException e) {
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return p;
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON) // tipo di dati in richiesta
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProdotto(Prodotto prod) {
		DaoFactory factory = DbTools.getDaoFactory();
		DaoProdotti dao = factory.getDaoProdotti();
		try {
			Prodotto prodotto = dao.add(prod);
			return Response.ok(prodotto).build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	
	@Path("/{idProdotto}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON) 
	public Response updateProdotto(Prodotto prod) {
		if(idProdotto==null) {
			return Response.serverError().build();
		}
		DaoFactory factory = DbTools.getDaoFactory();
		DaoProdotti dao = factory.getDaoProdotti();
		try {
			if(prod.getIdProdotto()!=Integer.parseInt(idProdotto))
				throw new WebApplicationException(400);
			dao.update(prod);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	
	@Path("/{idProdotto}")
	@DELETE
	public Response deleteProdotto() {
		if(idProdotto==null) {
			return Response.serverError().build();
		}
		DaoFactory factory = DbTools.getDaoFactory();
		DaoProdotti dao = factory.getDaoProdotti();

		try {
			Prodotto p = dao.getProdottoById(Integer.parseInt(idProdotto));
			dao.delete(p);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

}
