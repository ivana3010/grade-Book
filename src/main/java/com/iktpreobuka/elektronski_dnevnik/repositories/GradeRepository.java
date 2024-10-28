package com.iktpreobuka.elektronski_dnevnik.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.elektronski_dnevnik.entities.GradeEntity;

@Repository
public interface GradeRepository extends CrudRepository<GradeEntity, Integer>{

}
