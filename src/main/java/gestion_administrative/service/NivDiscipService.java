package employee_management.service;

import java.util.List;

import employee_management.entities.NivDiscip;

public interface NivDiscipService {
    public NivDiscip save(NivDiscip nivDiscip);
    public void delete(NivDiscip nivDiscip);
    public List<NivDiscip> getAll();
    public NivDiscip getById(int id);
	public boolean existsByCodeNivDiscip(String codeNivDiscip);
}