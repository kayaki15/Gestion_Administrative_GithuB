package gestion_administrative.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gestion_administrative.dao.EtablissementDao;
import gestion_administrative.entities.Etablissement;
import gestion_administrative.service.EtablissementService;

@Component
@Transactional
public class EtablissementServiceImpl implements EtablissementService {
	
    @Autowired
    private EtablissementDao etablissementDao;


    @Override
    @Transactional
    public Etablissement save(Etablissement etablissement) {
        return this.etablissementDao.save(etablissement);
    }

    @Override
    public void delete(Etablissement etablissement) {
        this.etablissementDao.delete(etablissement);
    }

    @Override
    public List<Etablissement> getAll() {
        return this.etablissementDao.findAll();
    }

	@Override
    public Etablissement getById(int id) {
        return this.etablissementDao.getOne(id);
    }

	@Override
	public List<Etablissement> saveAll(List<Etablissement> etablissement) {
	return etablissementDao.saveAll(etablissement)	;
	}

	@Override
	public Etablissement getByCode(String codeEtab) {
	    return this.etablissementDao.getByCodeEtab(codeEtab);
	}



}
