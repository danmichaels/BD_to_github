package logic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Statistics")
public class Statistics {	
	
	private Long stid;	
	private Long id;	
	private Long tid;
		
	public Statistics(){		
	}
		
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name="stid")
	public Long getStid(){
		return stid;
	}
	
	@Column(name="id")
	public Long getId(){
		return id;
	}
	
	@Column(name="tid")
	public Long getTid(){
		return tid;
	}
}
