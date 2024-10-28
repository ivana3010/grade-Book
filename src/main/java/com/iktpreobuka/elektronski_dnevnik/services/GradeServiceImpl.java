package com.iktpreobuka.elektronski_dnevnik.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronski_dnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronski_dnevnik.entities.ClassEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.GradeEntity;
import com.iktpreobuka.elektronski_dnevnik.repositories.GradeRepository;

@Service
public class GradeServiceImpl implements GradeService {
	@Autowired
	private GradeRepository gradeRepository;

	@Override
	public ResponseEntity<?> getAllGrades() {
		return new ResponseEntity<>(gradeRepository.findAll(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> addGrade(GradeEntity grade) {
		GradeEntity entity = new GradeEntity();
		entity.setGradeId(grade.getGradeId());
		entity.setName(grade.getName());
		gradeRepository.save(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteGrade(Integer gradeId) {
		Optional<GradeEntity> ge = gradeRepository.findById(gradeId);
		if (ge.isPresent()) {
			gradeRepository.delete(ge.get());
			return new ResponseEntity<>(ge.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(new RESTError(1, "Grade not found"), HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<?> modifyGrade(Integer gradeId, GradeEntity grade) {
		Optional<GradeEntity> gradeEntity = gradeRepository.findById(gradeId);
		if (gradeEntity.isPresent()) {
			GradeEntity entity = gradeEntity.get();
			entity.setGradeId(grade.getGradeId());
			entity.setName(grade.getName());
			gradeRepository.save(entity);
			return new ResponseEntity<>(entity, HttpStatus.OK);
		}
		return new ResponseEntity<>(new RESTError(1, "Grade not found"), HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<?> findByGradeId(Integer gradeId) {
		Optional<GradeEntity> grade = gradeRepository.findById(gradeId);
		if (grade.isPresent()) {
			return new ResponseEntity<>(grade.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new RESTError(1, "Grade not found"), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<?> getAllClassesForGrade(Integer gradeId) {
		Optional<GradeEntity> gradeEntity = gradeRepository.findById(gradeId);

		if (gradeEntity.isPresent()) {
			List<ClassEntity> classes = gradeEntity.get().getClasses();
			return new ResponseEntity<>(classes, HttpStatus.OK);
		} else {

			return new ResponseEntity<>(new RESTError(1, "Grade not found"), HttpStatus.NOT_FOUND);
		}
	}
}
