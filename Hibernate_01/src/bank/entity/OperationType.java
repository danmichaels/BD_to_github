/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Danya
 */
@Entity
@Table(name = "OperationType")
public class OperationType implements Serializable{
    
    OperationType(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /*@GeneratedValue(strategy = GenerationType.AUTO, generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")*/
    @Column(name = "type_id"/*, nullable = true, insertable = false, columnDefinition = "int default 100"*/)
    private int type_id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "comission")
    private float comission;

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getComission() {
        return comission;
    }

    public void setComission(float comission) {
        this.comission = comission;
    }
    
}
