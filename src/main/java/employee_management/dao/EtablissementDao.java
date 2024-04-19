package employee_management.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import employee_management.entities.Etablissement;

public interface EtablissementDao extends JpaRepository<Etablissement, Integer> {

}
