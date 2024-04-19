package employee_management.service;

import java.util.List;

import employee_management.entities.Niveau;


public interface NiveauService {
	
    public Niveau save( Niveau niveau);
    public void delete(Niveau niveau);
    public List<Niveau> getAll();
    public Niveau getById(int id);
	public List<Niveau> saveAll(List<Niveau> niveaus);
	public boolean existsByCodeNiv(String codeNiv);
}