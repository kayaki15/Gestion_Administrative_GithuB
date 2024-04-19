package employee_management.entities;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class NivDiscip implements Serializable{
	
	private static final long serialVersionUID = 1206683138747297564L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
		private Integer idNivDiscip; 	
	    private String codeNivDiscip;
	    private int nbreHeures;
	    
		@ManyToOne
		@JoinColumn(name = "idDiscip")
		private Discipline discipline;
		
		@ManyToOne
		@JoinColumn(name = "idNiveau")
		private Niveau niveau;

}
    

