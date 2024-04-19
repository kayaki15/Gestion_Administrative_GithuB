package gestion_administrative.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import gestion_administrative.entities.Niveau;

	public interface NiveauDao extends JpaRepository<Niveau, Integer> {

		boolean existsByCodeNiv(String codeNiv);
}
