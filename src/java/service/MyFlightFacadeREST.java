/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.MyFlight;
import entities.MyUser;
import entities.Route;
import entities.Ticket;
import java.util.ArrayList;
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
@Path("entities.myflight")
public class MyFlightFacadeREST extends AbstractFacade<MyFlight> {

    @PersistenceContext(unitName = "hw3-serverPU")
    private EntityManager em;

    public MyFlightFacadeREST() {
        super(MyFlight.class);
    }

    @PUT
    @Path("add_new_flight/{fromAirport}/{toAirport}/{flightDate}/{price}/{places}")
    public void add_new_flight(@PathParam("fromAirport") String fromAirport, @PathParam("toAirport") String toAirport,
            @PathParam("flightDate") String date, @PathParam("price") String price, @PathParam("places") String places) {
        MyFlight f = new MyFlight();
        double p = Double.parseDouble(price);
        int freePlaces = Integer.parseInt(places);
        f.setFromAirport(fromAirport);
        f.setToAirport(toAirport);
        f.setFlightDate(date);
        f.setPrice(p);
        f.setFreePlaces(freePlaces);
        super.create(f);
        super.edit(f);
        //PUT MIME:application/xml
        //PUT MIME:text/xml
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public MyFlight find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("find_flight_by_itinerary/{fromAirport}/{toAirport}/{token}")
    @Produces({"application/xml", "application/json"})
    public Route find_flight_by_itinerary(@PathParam("fromAirport") String fromAirport,
            @PathParam("toAirport") String toAirport, @PathParam("token") String token) {

        ArrayList<MyFlight> list = new ArrayList<MyFlight>();
        List<MyFlight> flights = super.findAll();
        for (MyFlight f : flights) {
            if (f.getFromAirport().equals(fromAirport) && f.getToAirport().equals(toAirport)) {
                list.add(f);
            }
        }

        return new Route();
        //return list;
    }

    @GET
    @Path("aaa/{ids}/{cards}/{token}")
    @Produces({"application/xml", "application/json"})
    public Ticket aaa(@PathParam("ids") String ids,@PathParam("cards") String cards, @PathParam("token") String token) {
        
        if(!checkToken(token)){
            throw new WebServiceException("Authentication failed. Invalid Token");
        }
        
        String x = ids;
        String[] split = x.split(";");
      
        ArrayList<MyFlight> name = new ArrayList<MyFlight>();

        Double tot=0.0;
        for (int i = 0; i < split.length; i++) {
            MyFlight find = null;
            find = super.find(Integer.parseInt(split[i]));
            if (find != null) {
                name.add(find);
                tot+=find.getPrice();
            }
        }
        Route route = new Route(name);

        return new Ticket(route, tot,cards);


    }

    @GET
    @Path("authentication/{id}/{password}")
    @Produces(MediaType.TEXT_PLAIN)
    public String authentication(@PathParam("id") String id, @PathParam("password") String password) {
        String authentication = "";
        String token = "adf29b96-d74f-42da-99d5-91ac9d7930b2";
        Query q = em.createQuery("SELECT u FROM MyUser u WHERE u.token = '" + token + "'");
        List resultList = q.getResultList();
        if (resultList.isEmpty()) {
            throw new WebServiceException("Authentication failed. Invalid Token");
        }
        MyUser u = (MyUser) resultList.get(0);
        authentication = u.getId() + " " + u.getToken() + " " + u.getPassword();
        return authentication;
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(MyFlight entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(MyFlight entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<MyFlight> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<MyFlight> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private boolean checkToken(String token) {
        boolean valid;
        Query q = em.createQuery("SELECT u FROM MyUser u WHERE u.token = '" + token + "'");
        List resultList = q.getResultList();
        valid = !resultList.isEmpty();
        return valid;
    }
}
