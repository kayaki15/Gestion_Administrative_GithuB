package gestion_administrative.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import gestion_administrative.entities.Structure;

	public interface StructureDao extends JpaRepository<Structure, Integer> {

		boolean existsByCodeStr(String codeStr);
}
