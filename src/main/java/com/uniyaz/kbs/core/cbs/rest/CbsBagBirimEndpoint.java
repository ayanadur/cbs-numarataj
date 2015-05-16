package com.uniyaz.kbs.core.cbs.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.jboss.logging.Logger;

import com.uniyaz.kbs.core.cbs.model.CbsBagBirim;
import com.uniyaz.kbs.core.cbs.model.CbsBina;


/**
 * 
 */
@Stateless
@Path("/cbsbagbirims")
public class CbsBagBirimEndpoint
{
	
   @PersistenceContext(unitName = "cbs-numarataj-persistence-unit")
   private EntityManager em;
   
   Logger log = Logger.getLogger(CbsBagBirimEndpoint.class);

   @POST
   @Consumes("application/json")
   public Response create(CbsBagBirim entity)
   {
	 log.info("create :" + entity);
	 CbsBina t = em.find(CbsBina.class, entity.getCbsBina().getId());
   	 entity.setCbsBina(t);
     em.persist(entity);
     return Response.created(UriBuilder.fromResource(CbsBagBirimEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
	  log.info("create : " + id);
	   
      CbsBagBirim entity = em.find(CbsBagBirim.class, id);
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      em.remove(entity);
      return Response.noContent().build();
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/json")
   public Response findById(@PathParam("id") Long id)
   {
	   log.info("findById : " + id);
	   
      TypedQuery<CbsBagBirim> findByIdQuery = em.createQuery("SELECT DISTINCT c FROM CbsBagBirim c LEFT JOIN FETCH c.cbsBina WHERE c.id = :entityId ORDER BY c.id", CbsBagBirim.class);
      findByIdQuery.setParameter("entityId", id);
      CbsBagBirim entity;
      try
      {
         entity = findByIdQuery.getSingleResult();
      }
      catch (NoResultException nre)
      {
         entity = null;
      }
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      return Response.ok(entity).build();
   }

   @GET
   @Produces("application/json")
   public List<CbsBagBirim> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult, @QueryParam("CbsBinaId") Long binaid)
   {
	   
	   log.info("listAll : ");
	   
	  String lsSql = "SELECT DISTINCT c FROM CbsBagBirim c LEFT JOIN FETCH c.cbsBina  ORDER BY c.id";
	  
      if (binaid != null)
      {
    	  lsSql = "SELECT DISTINCT c FROM CbsBagBirim c LEFT JOIN FETCH c.cbsBina WHERE c.cbsBina.id = :entityId ORDER BY c.id";
      }	 
	      
      TypedQuery<CbsBagBirim> findAllQuery = em.createQuery(lsSql, CbsBagBirim.class);
      
      if (binaid != null)
      {
    	  findAllQuery.setParameter("entityId", binaid);
      }

      if (startPosition != null)
      {
         findAllQuery.setFirstResult(startPosition);
      }
      if (maxResult != null)
      {
         findAllQuery.setMaxResults(maxResult);
      }
      final List<CbsBagBirim> results = findAllQuery.getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Long id, CbsBagBirim entity)
   {
	   log.info("update : " + entity);
      if (entity == null)
      {
         return Response.status(Status.BAD_REQUEST).build();
      }
      if (!id.equals(entity.getId()))
      {
         return Response.status(Status.CONFLICT).entity(entity).build();
      }
      if (em.find(CbsBagBirim.class, id) == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      try
      {
    	 CbsBina t = em.find(CbsBina.class, entity.getCbsBina().getId());
     	 entity.setCbsBina(t);
         entity = em.merge(entity);
      }
      catch (OptimisticLockException e)
      {
         return Response.status(Response.Status.CONFLICT).entity(e.getEntity()).build();
      }

      return Response.noContent().build();
   }
   
   /*
   
   private final String UPLOADED_FILE_PATH = "c:\\temp\\";
   
   @POST
   @Path("/image-upload")
   @Consumes("multipart/form-data")
   public Response uploadFile(MultipartFormDataInput input) throws IOException
   {
       //Get API input data
       Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        
       //Get file name
       String fileName = uploadForm.get("fileName").get(0).getBodyAsString();
        
       //Get file data to save
       List<InputPart> inputParts = uploadForm.get("attachment");

       for (InputPart inputPart : inputParts)
       {
           try
           {
               //Use this header for extra processing if required
               @SuppressWarnings("unused")
               MultivaluedMap<String, String> header = inputPart.getHeaders();
                
               // convert the uploaded file to inputstream
               InputStream inputStream = inputPart.getBody(InputStream.class, null);
                
               byte[] bytes = IOUtils.toByteArray(inputStream);
               // constructs upload file path
               fileName = UPLOADED_FILE_PATH + fileName;
               writeFile(bytes, fileName);
               System.out.println("Success !!!!!");
           }
           catch (Exception e)
           {
               e.printStackTrace();
           }
       }
       return Response.status(200)
               .entity("Uploaded file name : "+ fileName).build();
   }

   //Utility method
   private void writeFile(byte[] content, String filename) throws IOException
   {
       File file = new File(filename);
       if (!file.exists()) {
           file.createNewFile();
       }
       FileOutputStream fop = new FileOutputStream(file);
       fop.write(content);
       fop.flush();
       fop.close();
   }
*/
   
   
}
