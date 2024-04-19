package employee_management.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import employee_management.dao.DisciplineDao;
import employee_management.entities.Discipline;
import employee_management.service.DisciplineService;


@Component
@Transactional
public class DisciplineServiceImpl implements DisciplineService {
    @Autowired
    private DisciplineDao disciplineDao;


    @Override
    @Transactional
    public Discipline save(Discipline discipline) {
        return this.disciplineDao.save(discipline);
    }
    

    @Override
    public void delete(Discipline discipline) {
        this.disciplineDao.delete(discipline);
    }

    @Override
    public List<Discipline> getAll() {
        return this.disciplineDao.findAll();
    }

	@Override
	public Discipline getById(int id) {
		 return this.disciplineDao.getOne(id);
	}

	@Override
	public Discipline getByCodeAndName(String codeDiscip, String nomDiscip) {
	    return this.disciplineDao.getByCodeAndName(codeDiscip, nomDiscip);
	}


   @Override
    public Discipline update(Discipline discipline) {
        return disciplineDao.save(discipline);
    }


@Override
public List<Discipline> saveAll(List<Discipline> discipline) {
return disciplineDao.saveAll(discipline)	;
}

}
