package com.iktpreobuka.elektronski_dnevnik.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronski_dnevnik.controllers.util.RESTError;

import com.iktpreobuka.elektronski_dnevnik.entities.SemesterEntity;

import com.iktpreobuka.elektronski_dnevnik.repositories.SemesterRepository;

@Service
public class SemesterServiceImpl implements SemesterService{
	
	@Autowired
	protected SemesterRepository semesterRepository;
	
	@Override
	public ResponseEntity<?> getAllSemesters() {
		return new ResponseEntity<>(semesterRepository.findAll(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> addSemester(SemesterEntity semester) {
		SemesterEntity entity = new SemesterEntity();
        entity.setSemesterId(semester.getSemesterId());
        entity.setValue(semester.getValue());
        semesterRepository.save(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteSemester(Integer semesterId) {
		Optional<SemesterEntity> se = semesterRepository.findById(semesterId);
		if (se != null) {
			semesterRepository.delete(se.get());
			return new ResponseEntity<>(se.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(new RESTError(1, "Semester not found"), HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<?> modifySemester(Integer semesterId, SemesterEntity semester) {
		Optional<SemesterEntity> semesterEntity = semesterRepository.findById(semesterId);
		if (semesterEntity.isPresent()) {
			SemesterEntity entity = semesterEntity.get();
			entity.setSemesterId(semester.getSemesterId());
	        entity.setValue(semester.getValue());
	        semesterRepository.save(entity);
			return new ResponseEntity<>(entity, HttpStatus.OK);
	}
		return new ResponseEntity<>(new RESTError(1, "Semester not found"), HttpStatus.NOT_FOUND);
		}

	@Override
	public ResponseEntity<?> findBySemesterId(Integer semesterId) {
		Optional<SemesterEntity> semester = semesterRepository.findById(semesterId);
		if (semester.isPresent()) {
			return new ResponseEntity<>(semester.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new RESTError(1, "Semester not found"), HttpStatus.NOT_FOUND);
		}
	}
	}
	

