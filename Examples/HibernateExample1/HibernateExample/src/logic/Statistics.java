package logic;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
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
        
        private Set<Test> tests = new HashSet<Test>(0);

        @OneToMany
        @JoinTable(name = "id")
        public Set<Test> getTests() {
                return tests;
            }
	
	@Column(name="tid")
	public Long getTid(){
		return tid;
	}
}
