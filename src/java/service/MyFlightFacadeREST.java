/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.MyFlight;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
    @Path("find_flight_by_itinerary/{fromAirport}/{toAirport}")
    @Produces({"application/xml", "application/json"})
    public ArrayList<MyFlight> find_flight_by_itinerary(@PathParam("fromAirport") String fromAirport, 
    @PathParam("toAirport") String toAirport) {
        ArrayList<MyFlight> list = new ArrayList<MyFlight>();
        List<MyFlight> flights = super.findAll();
        for(MyFlight f : flights){
            if(f.getFromAirport().equals(fromAirport) && f.getToAirport().equals(toAirport)){
                list.add(f);
            }
        }
        return list;
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
    
}
