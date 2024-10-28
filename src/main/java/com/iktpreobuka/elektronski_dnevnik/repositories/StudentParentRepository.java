package com.iktpreobuka.elektronski_dnevnik.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.iktpreobuka.elektronski_dnevnik.entities.StudentParentEntity;

import jakarta.transaction.Transactional;

public interface StudentParentRepository extends CrudRepository<StudentParentEntity, Integer>{
	List<StudentParentEntity> findByStudentStudentId(Integer studentId);
	List<StudentParentEntity> findByParentParentId(Integer parentId);
	@Modifying
	@Transactional
	@Query("DELETE FROM StudentParentEntity s WHERE s.parent.parentId = :parentId")
	void deleteByParentId(@Param("parentId") Integer parentId);
}
