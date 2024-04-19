package gestion_administrative.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gestion_administrative.dao.NiveauDao;
import gestion_administrative.entities.Niveau;
import gestion_administrative.service.NiveauService;

@Component
@Transactional
public  class NiveauServiceImpl implements NiveauService  {
    @Autowired
    private NiveauDao niveauDao;

    @Override
    @Transactional
    public Niveau save(Niveau niveau) {
        return this.niveauDao.save(niveau);
    }

    @Override
    public void delete(Niveau niveau) {
        this.niveauDao.delete(niveau);
    }

    @Override
    public List<Niveau> getAll() {
        return this.niveauDao.findAll();
    }

	@Override
	public Niveau getById(int id) {
	return this.niveauDao.getOne(id);
	}

	
	@Override
	public List<Niveau> saveAll(List<Niveau> niveaus) {
	return niveauDao.saveAll(niveaus)	;
	}


	  @Override
	    public boolean existsByCodeNiv(String codeNiv) {
	        return niveauDao.existsByCodeNiv(codeNiv);
	    }

}
