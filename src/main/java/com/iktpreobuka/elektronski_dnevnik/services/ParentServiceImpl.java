package com.iktpreobuka.elektronski_dnevnik.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronski_dnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronski_dnevnik.entities.ParentEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.StudentParentEntity;
import com.iktpreobuka.elektronski_dnevnik.repositories.ParentRepository;
import com.iktpreobuka.elektronski_dnevnik.repositories.StudentParentRepository;

import jakarta.transaction.Transactional;

@Service
public class ParentServiceImpl implements ParentService {
	@Autowired
	protected ParentRepository parentRepository;
	@Autowired
	protected StudentParentRepository studentParentRepository;
	

	@Override
	public ResponseEntity<?> getAllParents() {
		return new ResponseEntity<>(parentRepository.findAll(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> addParent(ParentEntity parent) {
		ParentEntity entity = new ParentEntity();
        entity.setParentId(parent.getParentId());
        entity.setName(parent.getName());
        entity.setSurname(parent.getSurname());
        entity.setEmail(parent.getEmail());
        parentRepository.save(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@Transactional
	@Override
	public ResponseEntity<?> deleteParent(Integer parentId) {
		Optional<ParentEntity> pe = parentRepository.findById(parentId);//jer metoda findById vraca optional-moze da postoji u bazi a i ne mora
		if (pe.isPresent()) {
			 studentParentRepository.deleteByParentId(parentId);
			parentRepository.delete(pe.get());
			return new ResponseEntity<>(pe.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(new RESTError(1, "Parent not found"), HttpStatus.NOT_FOUND);
	}

	
	public ResponseEntity<?> modifyParent( Integer id,  ParentEntity parent) {
	    if (id == null || id <= 0) {
	        return new ResponseEntity<>(new RESTError(1, "Invalid ID"), HttpStatus.BAD_REQUEST);
	    }
	    
	    Optional<ParentEntity> parentEntity = parentRepository.findById(id);
	    if (parentEntity.isPresent()) {
	        ParentEntity entity = parentEntity.get();
	        entity.setName(parent.getName());
	        entity.setSurname(parent.getSurname());
	        entity.setEmail(parent.getEmail());
	        parentRepository.save(entity);
	        return new ResponseEntity<>(entity, HttpStatus.OK);
	    }
	    return new ResponseEntity<>(new RESTError(1, "Parent not found"), HttpStatus.NOT_FOUND);
	}

	

	@Override
	public ResponseEntity<?> findByParentId(Integer parentId) {
		Optional<ParentEntity> parent = parentRepository.findById(parentId);
		if (parent.isPresent()) {
			return new ResponseEntity<>(parent.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new RESTError(1, "Parent not found"), HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<?> getStudentsByParentId(Integer parentId) {
		Optional<ParentEntity> parentEntity = parentRepository.findById(parentId);
	    if (parentEntity.isPresent()) {
	        List<StudentParentEntity> parentStudents = studentParentRepository.findByParentParentId(parentId);
	        if (!parentStudents.isEmpty()) {
	            List<StudentEntity> students = new ArrayList<>();
	            for (StudentParentEntity parentStudent : parentStudents) {
	                students.add(parentStudent.getStudent());
	            }
	            return new ResponseEntity<>(students, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(new RESTError(1, "No students found for the parent"), HttpStatus.NOT_FOUND);
	        }
	    } else {
	        return new ResponseEntity<>(new RESTError(1, "Parent not found"), HttpStatus.NOT_FOUND);
	    }
    }
}
