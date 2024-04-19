package employee_management.service;

import java.util.List;

import employee_management.entities.Structure;

public interface StructureService {
	
    public Structure save( Structure structure);
    public void delete(Structure structure);
    public List<Structure> getAll();
    public Structure getById(int id);
    public boolean existsByCodeStr(String codeStr);
	public List<Structure> saveAll(List<Structure> structures);
}