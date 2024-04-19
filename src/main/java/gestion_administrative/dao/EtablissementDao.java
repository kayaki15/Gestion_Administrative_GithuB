package gestion_administrative.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import gestion_administrative.entities.Etablissement;

public interface EtablissementDao extends JpaRepository<Etablissement, Integer> {
	
	Etablissement getByCodeEtab(String codeEtab);
}
