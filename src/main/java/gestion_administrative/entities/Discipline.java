package gestion_administrative.entities;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
@Entity
@Table
@Data
public class Discipline implements java.io.Serializable {
	private static final long serialVersionUID = -6631611169200755699L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
    private int idDiscip;

    @NotBlank(message = "Code is required")
    @Size(max = 10, message = "Code must be at most 10 characters")
    private String codeDiscip;
    @NotBlank(message = "Name is required")
    private String nomDiscip;
}
