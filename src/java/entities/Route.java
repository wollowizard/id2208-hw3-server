/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gerard
 */
@XmlRootElement
public class Route {
    //@XmlElement
    //public ArrayList<String> name;
    @XmlElement
    public MyFlight f;
    //public ArrayList<MyFlight> name;
    @XmlElement
    public ArrayList<MyFlight> flights;

    public Route(){
        //name = new ArrayList<String>();
        f = new MyFlight();
        f.setFromAirport("BCN");
        flights = new ArrayList<MyFlight>();
        flights.add(f);
        //flights.add(new MyFlight());
    }
    public Route(ArrayList<MyFlight> flights) {
        this.flights = flights;
    }
    /*
    public ArrayList<MyFlight> getFlights() {
        return flights;
    }

    public void setFlights(ArrayList<MyFlight> flights) {
        this.flights = flights;
    }
   */
    
        
}
