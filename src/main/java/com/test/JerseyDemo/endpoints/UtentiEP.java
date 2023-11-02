package com.test.JerseyDemo.endpoints;


import java.util.Collection;
import java.util.Iterator;

import com.test.JerseyDemo.beans.Utente;
import com.test.JerseyDemo.daos.DaoFactory;
import com.test.JerseyDemo.daos.UtenteDao;
import com.test.JerseyDemo.dbconfig.DbTools;
import com.test.JerseyDemo.exceptions.UtenteDaoException;
import com.test.JerseyDemo.security.JwtTools;
import com.test.JerseyDemo.security.LoginBind;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("utenti")
public class UtentiEP {
	

	@PathParam("idUtente") String idUtente;
	
	@Context
	private ContainerRequestContext req;
	
	@GET
	@Path("profilo")
	@LoginBind
	@Produces(MediaType.APPLICATION_JSON)
	public Utente getProfilo() {
		return new Utente();
	}
	
	// application/json
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(Utente user) {

		DaoFactory df = DbTools.getDaoFactory();
		UtenteDao dao = df.getDaoUtente();
		
		try {
			dao.addUtente(user);
			
		} catch (UtenteDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.serverError().build();
		}
		return Response.ok().header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.allow("OPTIONS").build();
	}
	
	// application/x-www-form-urlencoded
	@POST
	@Path("login")
	@Produces(MediaType.TEXT_PLAIN)
	public Response loginUser(@FormParam("username") String username,
							  @FormParam("userPass") String password) {
		
		
		
		UtenteDao dao = DbTools.getDaoFactory().getDaoUtente();
		Utente u = null;
		try {
			u = dao.getByLogin(username, password);
		} catch (UtenteDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.serverError().build();

		}
		if(u!=null){
			String token = JwtTools.generateToken(u);
			return Response.ok().entity(token).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.allow("OPTIONS").build();
		}
		else
			return Response.status(Status.NOT_FOUND).build();
			
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(Utente user) {
		
		UtenteDao dao = DbTools.getDaoFactory().getDaoUtente();
		Utente u = null;
		try {
			u = dao.getById(Integer.parseInt(idUtente));
			
		} catch (UtenteDaoException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		if(u!=null)
			return Response.ok().entity(u).build();
		if(user!=null && u.getIdUtente()==user.getIdUtente()) {
			try {
				dao.updateUtente(user);
				return Response.ok().build();
			} catch (UtenteDaoException e) {
				e.printStackTrace();
				return Response.status(Status.NOT_MODIFIED).build();
			}
		}else {
			return Response.status(Status.NOT_MODIFIED).build();
		}
					
	}
	

}
