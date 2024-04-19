package gestion_administrative.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gestion_administrative.dao.StructureDao;
import gestion_administrative.entities.Etablissement;
import gestion_administrative.entities.Structure;
import gestion_administrative.service.StructureService;




@Component
@Transactional
public class StructureServiceImpl implements StructureService {
    @Autowired
    private StructureDao structureDao ;

    @Override
    @Transactional
    public Structure save(Structure structure) {
        return this.structureDao.save(structure);
    }

    @Override
    public void delete(Structure structure) {
        this.structureDao.delete(structure);
    }

    @Override
    public List<Structure> getAll() {
        return this.structureDao.findAll();
    }

	@Override
	public Structure getById(int id) {
	return this.structureDao.getOne(id);
	}

	   @Override
	    public boolean existsByCodeStr(String codeStr) {
	        return structureDao.existsByCodeStr(codeStr);
	    }


		@Override
		public List<Structure> saveAll(List<Structure> structures) {
		return structureDao.saveAll(structures)	;
		}
}
