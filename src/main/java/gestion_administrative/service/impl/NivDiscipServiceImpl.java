package gestion_administrative.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gestion_administrative.dao.NivDiscipDao;
import gestion_administrative.entities.Discipline;
import gestion_administrative.entities.NivDiscip;
import gestion_administrative.service.NivDiscipService;


@Component
@Transactional
public class NivDiscipServiceImpl implements NivDiscipService {
    @Autowired
    private NivDiscipDao nivDiscipDao;

    @Override
    @Transactional
    public NivDiscip save(NivDiscip nivDiscip) {
        return this.nivDiscipDao.save(nivDiscip);
    }

    @Override
    public void delete(NivDiscip nivDiscip) {
        this.nivDiscipDao.delete(nivDiscip);
    }

    @Override
    public List<NivDiscip> getAll() {
        return this.nivDiscipDao.findAll();
    }

	@Override
	public NivDiscip getById(int id) {
	return this.nivDiscipDao.getOne(id);
	}

	@Override
	public boolean existsByCodeNivDiscip(String codeNivDiscip) {
	return this.existsByCodeNivDiscip(codeNivDiscip);
	}

	
	@Override
	public List<NivDiscip> saveAll(List<NivDiscip> nivDiscips) {
	return nivDiscipDao.saveAll(nivDiscips)	;
	}




}
