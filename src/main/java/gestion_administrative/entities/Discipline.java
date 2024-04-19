package gestion_administrative.entities;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Entity
@Table
@Data
public class Discipline implements java.io.Serializable {
	private static final long serialVersionUID = -6631611169200755699L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
    private int idDiscip;
    private String codeDiscip;
    private String nomDiscip;
}
