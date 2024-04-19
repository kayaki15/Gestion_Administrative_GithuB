package employee_management.service;

import java.util.List;

import employee_management.entities.Etablissement;

public interface EtablissementService {
    public Etablissement save(Etablissement etablissement);
    public void delete(Etablissement etablissement);
    public List<Etablissement> getAll();
    public Etablissement getById(int id);
	public List<Etablissement> saveAll(List<Etablissement> etablissement);
}
