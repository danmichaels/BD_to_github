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
import javax.persistence.Table;
/**
 *
 * @author Danya
 */
@Entity
@Table(name = "Currencies")
public class Currencies implements Serializable{
    public Currencies(){}
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cur_id")
    private int cur_id;
    
    @Column(name = "abbr")
    private String abbr;
    
    @Column(name = "name")
    private String name;

    public int getCur_id() {
        return cur_id;
    }

    public void setCur_id(int cur_id) {
        this.cur_id = cur_id;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
