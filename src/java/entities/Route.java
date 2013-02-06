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
    
    @XmlElement
    public ArrayList<MyFlight> name;

    public Route(ArrayList<MyFlight> name) {
        this.name = name;
    }
    public Route(){}
    
    
    
}
