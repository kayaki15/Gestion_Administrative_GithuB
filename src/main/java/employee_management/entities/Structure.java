package employee_management.entities;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.Data;
@Entity
@Table
@Data
public class Structure implements java.io.Serializable {
	private static final long serialVersionUID = 340026182270855208L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
    private Integer idStr;
	
    private String codeStr;
    
    private int nbrClasse;
    
    @ManyToOne
    @JoinColumn(name = "IdNiveau")
    private Niveau niveau;
    
    @ManyToOne
    @JoinColumn(name = "IdEtab")
    private Etablissement etablissement;

}
