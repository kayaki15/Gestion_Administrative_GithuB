package employee_management.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import employee_management.entities.Discipline;

public interface DisciplineDao extends JpaRepository<Discipline, Integer> {
	
    @Query("SELECT d FROM Discipline d WHERE d.codeDiscip = :code AND d.nomDiscip = :name")
    public Discipline getByCodeAndName(@Param("code") String codeDiscip, @Param("name") String nomDiscip);
	

}
