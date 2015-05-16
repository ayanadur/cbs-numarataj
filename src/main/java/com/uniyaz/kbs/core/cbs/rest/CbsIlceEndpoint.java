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

import com.uniyaz.kbs.core.cbs.model.CbsIl;
import com.uniyaz.kbs.core.cbs.model.CbsIlce;

/**
 * 
 */
@Stateless
@Path("/cbsilces")
public class CbsIlceEndpoint
{
   @PersistenceContext(unitName = "cbs-numarataj-persistence-unit")
   private EntityManager em;
   
   Logger log = Logger.getLogger(CbsIlceEndpoint.class);

   @POST
   @Consumes("application/json")
   public Response create(CbsIlce entity)
   {
	  CbsIl t = em.find(CbsIl.class, entity.getCbsIl().getId());
	  entity.setCbsIl(t);
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(CbsIlceEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") long id)
   {
      CbsIlce entity = em.find(CbsIlce.class, id);
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
   public Response findById(@PathParam("id") long id)
   {
      TypedQuery<CbsIlce> findByIdQuery = em.createQuery("SELECT DISTINCT c FROM CbsIlce c LEFT JOIN FETCH c.cbsIl WHERE c.id = :entityId ORDER BY c.id", CbsIlce.class);
      findByIdQuery.setParameter("entityId", id);
      CbsIlce entity;
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
   public List<CbsIlce> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult, @QueryParam("ilid") Long ilId)
   {
	  log.info("listAll ...");
      //
	  TypedQuery<CbsIlce> findAllQuery = null;
	  
	  findAllQuery = em.createQuery("SELECT DISTINCT c FROM CbsIlce c LEFT JOIN FETCH c.cbsIl ORDER BY c.id", CbsIlce.class);
      
	  if (ilId != null)
      {
    	  findAllQuery = em.createQuery("SELECT DISTINCT c FROM CbsIlce c LEFT JOIN FETCH c.cbsIl WHERE c.cbsIl.id = :ilId ORDER BY c.id", CbsIlce.class);
    	  findAllQuery.setParameter("ilId", ilId);
      }
	  //
	  
      if (startPosition != null)
      {
         findAllQuery.setFirstResult(startPosition);
      }
      if (maxResult != null)
      {
         findAllQuery.setMaxResults(maxResult);
      }
      final List<CbsIlce> results = findAllQuery.getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Long id, CbsIlce entity)
   {
      if (entity == null)
      {
         return Response.status(Status.BAD_REQUEST).build();
      }
      if (!id.equals(entity.getId()))
      {
         return Response.status(Status.CONFLICT).entity(entity).build();
      }
      if (em.find(CbsIlce.class, id) == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      try
      {
    	 CbsIl t = em.find(CbsIl.class, entity.getCbsIl().getId());
    	 entity.setCbsIl(t);    	 
         entity = em.merge(entity);
      }
      catch (OptimisticLockException e)
      {
         return Response.status(Response.Status.CONFLICT).entity(e.getEntity()).build();
      }

      return Response.noContent().build();
   }
}
