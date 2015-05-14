/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "Clients")
public class Clients implements Serializable{
    
    public Clients(){
        /////////////////////
        cards = new HashSet<Card>(0);
        //cards = new ArrayList<Card>();
        ////////////
        //operations = new HashSet<Operations>(0);
    }
    /////////public Clients(List<Card> cards){}
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /*@GeneratedValue(strategy = GenerationType.AUTO, generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")*/
    @Column(name = "id"/*, nullable = true, insertable = false, columnDefinition = "int default 100"*/)
    private int id;
    
    // only needed columns
    //////
    //////////////
    //@OneToMany(mappedBy="clients",cascade = CascadeType.ALL,fetch = FetchType.EAGER,targetEntity=Card.class)
    @OneToMany(mappedBy="clients",cascade = CascadeType.ALL,fetch = FetchType.EAGER,targetEntity=Card.class)
    //@JoinColumn(name = "FK_cards_connect_id",referencedColumnName = "client_id")
    //@LazyCollection(LazyCollectionOption.FALSE)
    @ForeignKey(name="FK_someItem_someSet")
    
    //@JoinColumn(name = "FK_client_id",referencedColumnName = "client_id")
    
    private Set<Card> cards;
    public Set<Card> getCard() {
            return cards;
        }
    public void setCard(Set<Card> cards) {
            this.cards = cards;
        }
    
    //@OneToMany(mappedBy="clients",cascade = CascadeType.ALL,fetch = FetchType.EAGER,targetEntity=Operations.class)
    /*
    @OneToMany(targetEntity=Operations.class)
    @JoinTable(name="Bill",
          joinColumns={@JoinColumn(name="bill_id", referencedColumnName="id_local_side")}
    )
    private Set<Operations> operations;
    public Set<Operations> getOperations() {
            return operations;
        }
    public void setOperations(Set<Operations> operations) {
            this.operations = operations;
        }
    */
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "phone")
    private String phone;
    
    // unique
    
    /*private String passport_No="default";
    @PrePersist
    public void prePersist() {
        if(passport_No == null)
            passport_No = "default";
    }
    @GeneratedValue(strategy = GenerationType.AUTO, generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "passport_No", unique = true, nullable = false, columnDefinition = "String default default")*/
    
    @Column(name = "passport_No", unique = true, nullable = false, columnDefinition = "String default default")
    private String passport_No="default";
    public String getPassport_No() {
        return passport_No;
    }

    public void setPassport_No(String passport_No) {
        this.passport_No = passport_No;
    }
    
    
    
    /*@ManyToOne
    @JoinTable(name = "Card", joinColumns = @JoinColumn(name = "client_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "bill_id", referencedColumnName="id"))
    private Bill bill;
    public Bill getBill(){
            return bill;
        }*/
    ////////////
    
    /*@ManyToMany(targetEntity="Clients")
    @JoinTable(name="clients",
          joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
          inverseJoinColumns={@JoinColumn(name="phonenumber_id", referencedColumnName="id", unique=true)}
          )*/
    
    
    
        // testing
    
        /*
        @Temporal(TemporalType.DATE)
	@Column(name = "listedDate", nullable = false, length = 10)
        private Date listedDate;
	public Date getListedDate() {
		return this.listedDate;
	}
 
	public void setListedDate(Date listedDate) {
		this.listedDate = listedDate;
	}*/
 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    
}
