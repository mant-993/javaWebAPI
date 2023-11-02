package com.test.JerseyDemo.endpoints;

import java.io.InputStream;
import java.util.List;

import com.test.JerseyDemo.beans.Prodotto;
import com.test.JerseyDemo.daos.DaoFactory;
import com.test.JerseyDemo.daos.DaoProdotti;
import com.test.JerseyDemo.dbconfig.DbTools;
import com.test.JerseyDemo.dbconfig.DbData;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("test")
public class Test {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
    
	@Path("/tt")
	@GET
    @Produces(MediaType.TEXT_XML)
    public String sayXMLHello() {
      return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
    }
    
    
    @GET
    @Path("um")
    public String fromXml() {
    	InputStream in = DbTools.class.getResourceAsStream("/dbData.xml");
    	JAXBContext jaxbContext;
    	DbData dbData = new DbData();
		try {
			jaxbContext = JAXBContext.newInstance( DbData.class );
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    	dbData = (DbData) jaxbUnmarshaller.unmarshal( in );
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(dbData.getDbUrl()); 
		
    	
    	return "um";
    }
}
