package employee_management.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import employee_management.entities.Niveau;

	public interface NiveauDao extends JpaRepository<Niveau, Integer> {

		boolean existsByCodeNiv(String codeNiv);
}
