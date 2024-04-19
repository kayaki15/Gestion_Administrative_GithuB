package employee_management.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import employee_management.entities.NivDiscip;


public interface NivDiscipDao extends JpaRepository<NivDiscip, Integer> {
  
}
