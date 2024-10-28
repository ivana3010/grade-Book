package com.iktpreobuka.elektronski_dnevnik.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.elektronski_dnevnik.entities.ClassEntity;

@Repository
public interface ClassRepository extends CrudRepository<ClassEntity, Integer>{

}
