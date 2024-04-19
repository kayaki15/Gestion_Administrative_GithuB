package employee_management.service;

import java.util.List;

import employee_management.entities.Employee;

public interface EmployeeService {
    public Employee save(Employee employee);
    public void delete(Employee employee);
    public List<Employee> getAll();
    public Employee getById(int id);
}
