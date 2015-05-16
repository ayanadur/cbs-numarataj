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

import com.uniyaz.kbs.core.cbs.model.CbsIlce;
import com.uniyaz.kbs.core.cbs.model.CbsMahalle;

/**
 * 
 */
@Stateless
@Path("/cbsmahalles")
public class CbsMahalleEndpoint
{

   @PersistenceContext(unitName = "cbs-numarataj-persistence-unit")
   private EntityManager em;
   
   Logger log = Logger.getLogger(CbsMahalleEndpoint.class);

   @POST
   @Consumes("application/json")
   public Response create(CbsMahalle entity)
   {
	  CbsIlce t = em.find(CbsIlce.class, entity.getCbsIlce().getId());
	  entity.setCbsIlce(t);		   
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(CbsMahalleEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      CbsMahalle entity = em.find(CbsMahalle.class, id);
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
      TypedQuery<CbsMahalle> findByIdQuery = em.createQuery("SELECT DISTINCT c FROM CbsMahalle c LEFT JOIN FETCH c.cbsIlce WHERE c.id = :entityId ORDER BY c.id", CbsMahalle.class);
      findByIdQuery.setParameter("entityId", id);
      CbsMahalle entity;
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
   public List<CbsMahalle> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult, @QueryParam("ilceid") Long ilceId)
   {
	  TypedQuery<CbsMahalle> findAllQuery = null;
	  
	  findAllQuery = em.createQuery("SELECT DISTINCT c FROM CbsMahalle c LEFT JOIN FETCH c.cbsIlce ORDER BY c.id", CbsMahalle.class); 
      
	  if (ilceId != null)
      {
    	  findAllQuery = em.createQuery("SELECT DISTINCT c FROM CbsMahalle c LEFT JOIN FETCH c.cbsIlce WHERE c.cbsIlce.id = :ilceId ORDER BY c.id", CbsMahalle.class);
    	  findAllQuery.setParameter("ilceId", ilceId);
      }
	      
      //TypedQuery<CbsMahalle> findAllQuery = em.createQuery("SELECT DISTINCT c FROM CbsMahalle c LEFT JOIN FETCH c.cbsIlce ORDER BY c.id", CbsMahalle.class);
      if (startPosition != null)
      {
         findAllQuery.setFirstResult(startPosition);
      }
      if (maxResult != null)
      {
         findAllQuery.setMaxResults(maxResult);
      }
      final List<CbsMahalle> results = findAllQuery.getResultList();
      return results;
      
   }

   @GET
   @Path("/cbsilce/{ilceid:[0-9][0-9]*}")
   @Produces("application/json")
   public List<CbsMahalle> listAllByIlceId(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult, @PathParam("ilceid") Long ilceid)
   {
      TypedQuery<CbsMahalle> findAllQuery = em.createQuery("SELECT DISTINCT c FROM CbsMahalle c LEFT JOIN FETCH c.cbsIlce WHERE c.cbsIlce.id = :ilceid ORDER BY c.cbsIlce.id", CbsMahalle.class);
      
      findAllQuery.setParameter("ilceid", ilceid);
      
      if (startPosition != null)
      {
         findAllQuery.setFirstResult(startPosition);
      }
      if (maxResult != null)
      {
         findAllQuery.setMaxResults(maxResult);
      }
      final List<CbsMahalle> results = findAllQuery.getResultList();
      return results;
      
   }
   
   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Long id, CbsMahalle entity)
   {
      if (entity == null)
      {
         return Response.status(Status.BAD_REQUEST).build();
      }
      if (!id.equals(entity.getId()))
      {
         return Response.status(Status.CONFLICT).entity(entity).build();
      }
      if (em.find(CbsMahalle.class, id) == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      try
      {
    	  CbsIlce t = em.find(CbsIlce.class, entity.getCbsIlce().getId());
    	  entity.setCbsIlce(t);	    	  
         entity = em.merge(entity);
      }
      catch (OptimisticLockException e)
      {
         return Response.status(Response.Status.CONFLICT).entity(e.getEntity()).build();
      }

      return Response.noContent().build();
   }
}
