package com.iktpreobuka.elektronski_dnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.elektronski_dnevnik.entities.SubjectEntity;

@Repository
public interface SubjectRepository extends CrudRepository<SubjectEntity, Integer>{
	List<SubjectEntity> findByNameContainingIgnoreCase(String name);
}
