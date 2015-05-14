/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.entity;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 *
 * @author Danya
 */
@Entity
@Table(name = "Credits")
public class Credits {
    public Credits(){}
    // Идентификатор для генерации сущности??
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bill_id")
    private int bill_id;
    
    @Column(name = "rate")
    private float rate;
    
    @Column(name = "start_date")
    private Date start_date;
    
    @Column(name = "period")
    private int period;
    
    @Column(name = "last_update")
    private Date last_update;
    
    @ManyToOne(targetEntity=Bill.class/*,inversedBy=Card.class*/)
    //@JoinTable(name = "client",joinColumns = @JoinColumn(name = "FK_client_id", referencedColumnName="client_id"))
    @JoinColumn(name = "FK_name_bill_id",referencedColumnName = "id")
    //@JoinColumn(name = "client_id",referencedColumnName = "id")
    private Bill bill;
    public Bill getBill(){
        return bill;
    }
    public void setBill(Bill bill){
        this.bill = bill;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }
    
}
