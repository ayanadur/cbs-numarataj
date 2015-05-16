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

import com.uniyaz.kbs.core.cbs.model.CbsBina;
import com.uniyaz.kbs.core.cbs.model.CbsSokak;

/**
 * 
 */
@Stateless
@Path("/cbsbinas")
public class CbsBinaEndpoint
{
   @PersistenceContext(unitName = "cbs-numarataj-persistence-unit")
   private EntityManager em;
   
   Logger log = Logger.getLogger(CbsBinaEndpoint.class);

   @POST
   @Consumes("application/json")
   public Response create(CbsBina entity)
   {
	  CbsSokak t = em.find(CbsSokak.class, entity.getCbsSokak().getId());
	  entity.setCbsSokak(t);		   
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(CbsBinaEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      CbsBina entity = em.find(CbsBina.class, id);
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
      TypedQuery<CbsBina> findByIdQuery = em.createQuery("SELECT DISTINCT c FROM CbsBina c LEFT JOIN FETCH c.cbsSokak WHERE c.id = :entityId ORDER BY c.id", CbsBina.class);
      findByIdQuery.setParameter("entityId", id);
      CbsBina entity;
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
   public List<CbsBina> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult, @QueryParam("csid") Long csid)
   {
	  log.info("ListAll");
	  
	  String lsSql = "SELECT DISTINCT c FROM CbsBina c LEFT JOIN FETCH c.cbsSokak ORDER BY c.id";
	  
      if (csid != null)
      {
    	  lsSql = "SELECT DISTINCT c FROM CbsBina c LEFT JOIN FETCH c.cbsSokak WHERE c.cbsSokak.id= :entityId ORDER BY c.id";
      }	  
      
      TypedQuery<CbsBina> findAllQuery = em.createQuery(lsSql, CbsBina.class);
      
      if (csid != null)
      {
    	  findAllQuery.setParameter("entityId", csid);
      }
      
      if (startPosition != null)
      {
         findAllQuery.setFirstResult(startPosition);
      }
      if (maxResult != null)
      {
         findAllQuery.setMaxResults(maxResult);
      }
      final List<CbsBina> results = findAllQuery.getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Long id, CbsBina entity)
   {
      if (entity == null)
      {
         return Response.status(Status.BAD_REQUEST).build();
      }
      if (!id.equals(entity.getId()))
      {
         return Response.status(Status.CONFLICT).entity(entity).build();
      }
      if (em.find(CbsBina.class, id) == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      try
      {
    	 CbsSokak t = em.find(CbsSokak.class, entity.getCbsSokak().getId());
    	 entity.setCbsSokak(t);    	  
         entity = em.merge(entity);
      }
      catch (OptimisticLockException e)
      {
         return Response.status(Response.Status.CONFLICT).entity(e.getEntity()).build();
      }

      return Response.noContent().build();
   }

}
