package employee_management.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import employee_management.dao.EmployeeDao;
import employee_management.entities.Employee;
import employee_management.service.EmployeeService;



@Component
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;


    @Override
    @Transactional
    public Employee save(Employee employee) {
        return this.employeeDao.save(employee);
    }

    @Override
    public void delete(Employee employee) {
        this.employeeDao.delete(employee);
    }

    @Override
    public List<Employee> getAll() {
        return this.employeeDao.findAll();
    }

	@Override
    public Employee getById(int id) {
        return this.employeeDao.getOne(id);
    }
}
