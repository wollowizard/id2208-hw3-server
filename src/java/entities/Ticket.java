/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alfredo
 */
@XmlRootElement
public class Ticket {

    @XmlElement
    public Double totalPrice;
    @XmlElement
    public String creditCardNumber;
    @XmlElement
    private Route r;

    public Ticket() {
    }

    public Ticket(Route r, Double totprice, String card) {
        this.r=r;
        this.totalPrice = totprice;
        this.creditCardNumber = card;
    }
}
