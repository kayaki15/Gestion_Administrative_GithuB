package employee_management.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import employee_management.dao.EtablissementDao;
import employee_management.entities.Etablissement;
import employee_management.service.EtablissementService;

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
}
