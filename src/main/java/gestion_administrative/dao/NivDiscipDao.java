package gestion_administrative.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import gestion_administrative.entities.NivDiscip;


public interface NivDiscipDao extends JpaRepository<NivDiscip, Integer> {
  
}
