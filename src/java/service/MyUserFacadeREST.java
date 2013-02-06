/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.MyUser;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.WebServiceException;

/**
 *
 * @author Gerard
 */
@Stateless
@Path("entities.myuser")
public class MyUserFacadeREST extends AbstractFacade<MyUser> {

    @PersistenceContext(unitName = "hw3-serverPU")
    private EntityManager em;

    public MyUserFacadeREST() {
        super(MyUser.class);
    }

    @PUT
    @Path("create_new_user/{id}/{password}")
    public void create_new_user(@PathParam("id") String id, @PathParam("password") String password) {
        MyUser myUser = new MyUser();
        myUser.setId(id);
        myUser.setPassword(password);
        System.out.println("User "+myUser.getId());
        super.create(myUser);
    }
    
    @GET
    @Path("authentication/{id}/{password}")
    @Produces(MediaType.TEXT_PLAIN)
    public String authentication(@PathParam("id") String id,@PathParam("password") String password) {
        MyUser myUser = super.find(id);
        String authentication="Authentication failed";
        Query q = em.createQuery("SELECT u FROM MyUser u WHERE u.id = '"+id+"'");
        List resultList = q.getResultList();
        if(resultList.isEmpty()){
            throw new WebServiceException("Authentication failed");
        }
        MyUser u = (MyUser) resultList.get(0);
        authentication = u.getId();
        if(myUser!=null){
            if(myUser.getPassword().equals(password)){
                String token = UUID.randomUUID().toString();
                myUser.setToken(token);
                authentication=token;
            }
        }
        else{
            throw new WebServiceException("Authentication failed");
        }
        return authentication;
    }

    @DELETE
    @Path("delete_user/{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }


    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(MyUser entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(MyUser entity) {
        super.edit(entity);
    }

    /*@DELETE
     @Path("{id}")
     public void remove(@PathParam("id") String id) {
     super.remove(super.find(id));
     }*/
    
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public MyUser find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<MyUser> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<MyUser> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    
    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
}
