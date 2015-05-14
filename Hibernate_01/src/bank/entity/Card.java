package bank.entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Card")
public class Card implements Serializable{
    
    public Card(){}
    
    public Card(Clients client){
       this.clients = client;
    }
    
    // Идентификатор для генерации сущности??
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /*@GeneratedValue(strategy = GenerationType.AUTO, generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")*/
    @Column(name = "number")
    private int number=1;
    
    @Column(name = "client_id")
    private int client_id;
    
    // many objects with same box_id
    /*@ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Clients clients;*/
    
    @ManyToOne(targetEntity=Clients.class/*,inversedBy=Card.class*/)
    //@JoinTable(name = "client",joinColumns = @JoinColumn(name = "FK_client_id", referencedColumnName="client_id"))
    @JoinColumn(name = "FK_name_client_id",referencedColumnName = "id")
    //@JoinColumn(name = "client_id",referencedColumnName = "id")
    private Clients clients;
    public Clients getClients(){
        return clients;
    }
    public void setClients(Clients client){
        this.clients = client;
    }
    
    @Column(name = "bill_id",unique = true)
    private int bill_id;
    
    /*@ManyToOne
    @JoinColumn(name = "bill_id", referencedColumnName = "id")
    private Clients clients;*/
    
    @Column(name = "start_date")
    private Date start_date;
    
    @Column(name = "end_date")
    private Date end_date;
    
    @Column(name = "cvc")
    private int cvc;
    
    @Column(name = "pin")
    private int pin;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
   
}

/*public class Card {

    private int number;
    private int client_id;
    private int bill_id;
    private Date start_date;
    private Date end_date;
    private int cvc;
    private int pin;
    
    public Card() {
        this.number = 0;
        this.client_id = 0;
        this.bill_id = 0;
        this.start_date = Date.valueOf("2015-04-27");
        this.end_date = Date.valueOf("2015-04-27");
        this.cvc = 0;
        this.pin = 0;
    }

    public int getNumber() {
        return number;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

   
}

*/