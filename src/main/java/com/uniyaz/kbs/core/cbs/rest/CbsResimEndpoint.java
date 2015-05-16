package com.uniyaz.kbs.core.cbs.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.uniyaz.kbs.core.cbs.model.CbsBina;
import com.uniyaz.kbs.core.cbs.model.CbsResim;
import com.uniyaz.kbs.core.cbs.util.Base64;
import com.uniyaz.kbs.core.cbs.util.PropertiesHelper;

/**
 * 
 */
@Stateless
@Path("/cbsresims")
public class CbsResimEndpoint
{
	
   @PersistenceContext(unitName = "cbs-numarataj-persistence-unit")
   private EntityManager em;
   
   Logger log = Logger.getLogger(CbsResimEndpoint.class);
   
   private final String UPLOADED_FILE_PATH = PropertiesHelper.getDocBase();
   
   @GET
   @Path("/media/{id:[0-9][0-9]*}")
   @Produces(MediaType.APPLICATION_OCTET_STREAM)
   public Response getFile(@PathParam("id") Long id) {
	   
	   CbsResim entity = em.find(CbsResim.class, id);
	      if (entity == null)
	      {
	         return Response.status(Status.NOT_FOUND).build();
	      }
	      
     File file = new File(UPLOADED_FILE_PATH + entity.getPath());
     
     return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
         .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" ) //optional
         .build();
   }
   
   @POST
   @Consumes("application/json")
   public Response create(CbsResim entity)
   {
	 log.info("create :" + entity);
	 
	 CbsBina t = em.find(CbsBina.class, entity.getCbsBina().getId());
   	 entity.setCbsBina(t);
     em.persist(entity);
     
     return Response.created(UriBuilder.fromResource(CbsResimEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
	  log.info("deleteById : " + id);
	   
	  CbsResim entity = em.find(CbsResim.class, id);
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      em.remove(entity);
      
      File file = new File(UPLOADED_FILE_PATH + entity.getPath());
      
      if (file.exists()) {
          file.delete();
      }
      
      return Response.noContent().build();
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/json")
   public Response findById(@PathParam("id") Long id)
   {
	   log.info("findById : " + id);
	   
      TypedQuery<CbsResim> findByIdQuery = em.createQuery("SELECT DISTINCT c FROM CbsResim c LEFT JOIN FETCH c.cbsBina WHERE c.id = :entityId ORDER BY c.id", CbsResim.class);
      findByIdQuery.setParameter("entityId", id);
      CbsResim entity;
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
   public List<CbsResim> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult, @QueryParam("CbsBinaId") Long binaid)
   {
	   
	   log.info("listAll : ");
	   
	  String lsSql = "SELECT DISTINCT c FROM CbsResim c LEFT JOIN FETCH c.cbsBina  ORDER BY c.id";
	  
      if (binaid != null)
      {
    	  log.info("listAll : binaid != null");
    	  lsSql = "SELECT DISTINCT c FROM CbsResim c LEFT JOIN FETCH c.cbsBina WHERE c.cbsBina.id = :entityId ORDER BY c.id";
      }	 
	      
      TypedQuery<CbsResim> findAllQuery = em.createQuery(lsSql, CbsResim.class);
      
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
      final List<CbsResim> results = findAllQuery.getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Long id, CbsResim entity)
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
      if (em.find(CbsResim.class, id) == null)
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
     
   @POST
   @Path("/upload-file")
   @Consumes("multipart/form-data")
   public Response uploadFile(@Context UriInfo ui, @MultipartForm FileUploadForm form) {
	   
       /* Entity Kaydet */
       
       CbsResim entity = new CbsResim();
       
       entity.setId(null);
       entity.setDosyaAdi(form.getFileName());
       entity.setTuru(form.getTuru());
       entity.setVersion(null);
       
       CbsBina t = em.find(CbsBina.class, form.getBinaId());
       entity.setCbsBina(t);
       
       String _url = Base64.encodeToString(form.getPath().getBytes(), false);
       entity.setPath(_url);
       entity.setKayitTarihi(form.getKayitTarihi());
       
       em.persist(entity);
              
       try {
    	   
           File file = new File(UPLOADED_FILE_PATH + _url);
             
           if (!file.exists()) {
               file.createNewFile();
           }
     
           FileOutputStream fos = new FileOutputStream(file);
           
           fos.write(form.getFileData());
           fos.flush();
           fos.close();
           
       }
       catch (IOException e)
       {
           e.printStackTrace();
       }
       

       //Build a response to return
       return Response.status(200).entity(entity.getId()).build();
   }
     
/*	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	public Response uploadFile(MultipartFormDataInput input) {
		
		log.info("uploadFile ...");

		String fileName = "";

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		
		List<InputPart> inputParts = uploadForm.get("upload-file");

		for (InputPart inputPart : inputParts) {
		 try {

			MultivaluedMap<String, String> header = inputPart.getHeaders();
			
			fileName = getFileName(header);

			//convert the uploaded file to inputstream
			InputStream inputStream = inputPart.getBody(InputStream.class,null);

			byte [] bytes = IOUtils.toByteArray(inputStream);

			//constructs upload file path
			fileName = UPLOADED_FILE_PATH + fileName;

			writeFile(bytes,fileName);

		  } catch (IOException e) {
			e.printStackTrace();
		  }

		}

		return Response.status(200)
		    .entity(fileName+ " Kaydedildi.").build();

	}*/

	/**
	 * header sample
	 * {
	 * 	Content-Type=[image/png], 
	 * 	Content-Disposition=[form-data; name="file"; filename="filename.extension"]
	 * }
	 **/
	//get uploaded filename, is there a easy way in RESTEasy?
/*	private String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

		for (String filename : contentDisposition) {
			log.info(filename);
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

	//save to somewhere
	private void writeFile(byte[] content, String filename) throws IOException {

		File file = new File(filename);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fop = new FileOutputStream(file);

		fop.write(content);
		fop.flush();
		fop.close();

	}*/
}

