package employee_management.entities;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
@Entity
@Table
@Data
public class Etablissement implements java.io.Serializable {
	private static final long serialVersionUID = 6845195032507710844L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
    private Integer idEtabli;
    private String codeEtab;
    private String nomEtab;

}
