package employee_management.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Entity
@Table
@Data
public class Niveau implements Serializable{
	private static final long serialVersionUID = 1968356762136129291L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
    private Integer idNiveau;
    private String codeNiv;
    private String nomNiv;

}
    

