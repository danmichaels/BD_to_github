/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.entity;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "Bill")
public class Bill implements Serializable{
    public Bill(){}
    // Идентификатор для генерации сущности??
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    
    // @OneToMany
    @Column(name = "client_id")
    private int client_id;
    
    @Column(name = "balance")
    private int balance;
    
    @Column(name = "start_date")
    private Date start_date;
    
    @Column(name = "is_active")
    private short is_active;
    
    @Column(name = "currency_id")
    private int currency_id;
    
    @ManyToOne
    @JoinTable(name = "Card", joinColumns = @JoinColumn(name = "bill_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "client_id", referencedColumnName="id"))
    private Clients clients;
    public Clients getClients(){
            return clients;
        }
    
    // Для тестирования с процедурой
    @OneToMany(mappedBy="bill",cascade = CascadeType.ALL,fetch = FetchType.EAGER,targetEntity=Credits.class)
    @ForeignKey(name="FK_someItem_someSet")
    //@JoinColumn(name = "FK_client_id",referencedColumnName = "client_id")
    private Set<Credits> credits;
    public Set<Credits> getCredits() {
            return credits;
        }
    public void setCredits(Set<Credits> credits) {
            this.credits = credits;
        }
    
    @OneToMany(mappedBy="bill",cascade = CascadeType.ALL,fetch = FetchType.EAGER,targetEntity=Operations.class)
    private List<Operations> operations;
    public List<Operations> getOperations() {
            return operations;
        }
    public void setOperations(List<Operations> operations) {
            this.operations = operations;
        }
    
    /*
    @ManyToMany(targetEntity=OperationType.class)
    @JoinTable(name="Operations",
          joinColumns={@JoinColumn(name="bill_id", referencedColumnName="id_local_side")},
          inverseJoinColumns={@JoinColumn(name="operationtype_id", referencedColumnName="op_type")}
    )
    private Set<OperationType> operationtypes;
    public Set<OperationType> getOperationtypes() {
            return operationtypes;
        }
    public void setOperationtypes(Set<OperationType> operationtypes) {
            this.operationtypes = operationtypes;
        }
    */
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public short getIs_active() {
        return is_active;
    }

    public void setIs_active(short is_active) {
        this.is_active = is_active;
    }

    public int getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(int currency_id) {
        this.currency_id = currency_id;
    }
    
    
    
    
    
    
}
