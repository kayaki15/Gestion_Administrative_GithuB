package employee_management.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import employee_management.dao.NivDiscipDao;
import employee_management.entities.NivDiscip;
import employee_management.service.NivDiscipService;


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


}
