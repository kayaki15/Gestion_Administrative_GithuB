package employee_management.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import employee_management.entities.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Integer> {

}
