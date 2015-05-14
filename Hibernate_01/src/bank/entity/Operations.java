/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Danya
 */
@Entity
@Table(name = "Operations")
public class Operations implements Serializable{
    public Operations(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /*@GeneratedValue(strategy = GenerationType.AUTO, generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")*/
    @Column(name = "for_id"/*, nullable = true, insertable = false, columnDefinition = "int default 100"*/)
    private Long for_id;
    
    @Column(name = "op_type")
    private int op_type;
            
    @Column(name = "id_local_side")
    private int id_local_side;
            
    @Column(name = "amount")
    private float amount;
         
    @Column(name = "date")
    private Timestamp date;
    
    @ManyToOne
    @JoinColumn(name = "FK_Opert_to_Currency", referencedColumnName="cur_id")
    private Currencies currencies;
    public Currencies getCurrencies(){
            return currencies;
        }
    public void setCurrencies(Currencies currencies){
            this.currencies=currencies;
        }
    
    //////////////
    @ManyToOne
    @JoinColumn(name = "FK_Opert_to_Bill", referencedColumnName="id")
    private Bill bill;
    public Bill getBill(){
            return bill;
        }
    public void setBill(Bill bill){
            this.bill = bill;
        }
    
    public Long getFor_id() {
        return for_id;
    }

    public void setFor_id(Long for_id) {
        this.for_id = for_id;
    }

    public int getOp_type() {
        return op_type;
    }

    public void setOp_type(int op_type) {
        this.op_type = op_type;
    }

    public int getId_local_side() {
        return id_local_side;
    }

    public void setId_local_side(int id_local_side) {
        this.id_local_side = id_local_side;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
    
    
    
    
    
    
}
