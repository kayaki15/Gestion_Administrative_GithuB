package gestion_administrative.service;

import java.util.List;

import gestion_administrative.entities.Discipline;


public interface DisciplineService {
    public Discipline save(Discipline discipline);
    public void delete(Discipline discipline);
    public List<Discipline> getAll();
    public Discipline getById(int id);
	public Discipline getByCodeAndName(String codeDiscip, String nomDiscip);
	public Discipline update(Discipline discipline);
	public List<Discipline> saveAll(List<Discipline> discipline);
	public Discipline getBycodeDiscip(String codeDiscip);
}
