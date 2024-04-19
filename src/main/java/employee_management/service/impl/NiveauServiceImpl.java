package employee_management.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import employee_management.dao.NiveauDao;
import employee_management.entities.Niveau;
import employee_management.service.NiveauService;

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
