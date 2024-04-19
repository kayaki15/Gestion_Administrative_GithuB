package gestion_administrative.service;

import java.util.List;

import gestion_administrative.entities.NivDiscip;

public interface NivDiscipService {
    public NivDiscip save(NivDiscip nivDiscip);
    public void delete(NivDiscip nivDiscip);
    public List<NivDiscip> getAll();
    public NivDiscip getById(int id);
	public boolean existsByCodeNivDiscip(String codeNivDiscip);
	public List<NivDiscip> saveAll(List<NivDiscip> nivDiscips);
}