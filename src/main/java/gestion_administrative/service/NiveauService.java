package gestion_administrative.service;

import java.util.List;

import gestion_administrative.entities.Niveau;


public interface NiveauService {
	
    public Niveau save( Niveau niveau);
    public void delete(Niveau niveau);
    public List<Niveau> getAll();
    public Niveau getById(int id);
	public List<Niveau> saveAll(List<Niveau> niveaus);
	public boolean existsByCodeNiv(String codeNiv);

}