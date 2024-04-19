package employee_management.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import employee_management.entities.Structure;

	public interface StructureDao extends JpaRepository<Structure, Integer> {

		boolean existsByCodeStr(String codeStr);
}
