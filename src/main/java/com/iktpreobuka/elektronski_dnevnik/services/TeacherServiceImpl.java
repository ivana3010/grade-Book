package com.iktpreobuka.elektronski_dnevnik.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronski_dnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronski_dnevnik.entities.ClassEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.TeacherSubjectClassEntity;
import com.iktpreobuka.elektronski_dnevnik.repositories.ClassRepository;
import com.iktpreobuka.elektronski_dnevnik.repositories.TeacherRepository;
import com.iktpreobuka.elektronski_dnevnik.repositories.TeacherSubjectClassRepository;

@Service
public class TeacherServiceImpl implements TeacherService {
	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private TeacherSubjectClassRepository teacherSubjectClassRepository;
	@Autowired
	private ClassRepository classRepository;
	

	@Override
	public ResponseEntity<?> getAllTeachers() {
		return new ResponseEntity<>(teacherRepository.findAll(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> addTeacher(TeacherEntity teacher) {
		TeacherEntity entity = new TeacherEntity();
		entity.setTeacherId(teacher.getTeacherId());
        entity.setName(teacher.getName());
        entity.setSurname(teacher.getSurname());
        entity.setEmail(teacher.getEmail());
        teacherRepository.save(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteTeacher(Integer teacherId) {
		Optional<TeacherEntity> te = teacherRepository.findById(teacherId);
		if (te.isPresent()) {
			teacherRepository.delete(te.get());
			return new ResponseEntity<>(te.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(new RESTError(1, "Teacher not found"), HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<?> modifyTeacher(Integer teacherId, TeacherEntity teacher) {
	    Optional<TeacherEntity> teacherEntity = teacherRepository.findById(teacherId);
	    if (teacherEntity.isPresent()) {
	        TeacherEntity entity = teacherEntity.get();
	        entity.setName(teacher.getName());
	        entity.setSurname(teacher.getSurname());
	        entity.setEmail(teacher.getEmail());
	        if (teacher.getClassesIds() != null) {
	            updateClassesForTeacher(teacherId, teacher.getClassesIds());
	        }
	        entity.setClassesIds(teacher.getClassesIds());
	        teacherRepository.save(entity);

	        // prilagodjavanje odgovora ka frontu jer on ocekuje ovo dole+data ispred
	        Map<String, Object> response = new HashMap<>();
	        response.put("id", entity.getTeacherId());
	        response.put("name", entity.getName());
	        response.put("surname", entity.getSurname());
	        response.put("email", entity.getEmail());
	        response.put("classesIds", entity.getClassesIds());

	        Map<String, Object> data = new HashMap<>();
	        data.put("data", response);

	        return new ResponseEntity<>(data, HttpStatus.OK);
	    }
	    return new ResponseEntity<>(new RESTError(1, "Teacher not found"), HttpStatus.NOT_FOUND);
	}


	@Override
	public ResponseEntity<?> findByTeacherId(Integer teacherId) {
		Optional<TeacherEntity> teacher = teacherRepository.findById(teacherId);
		if (teacher.isPresent()) {
			return new ResponseEntity<>(teacher.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new RESTError(1, "Teacher not found"), HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<?> getAllClassesForTeacher(Integer teacherId) {
	    Optional<TeacherEntity> teacher = teacherRepository.findById(teacherId);

	    if (!teacher.isPresent()) {
	        return new ResponseEntity<>(new RESTError(1, "Teacher not found"), HttpStatus.NOT_FOUND);
	    }

	    List<TeacherSubjectClassEntity> teacherSubjectClasses = teacherSubjectClassRepository.findByTeacher_TeacherId(teacherId);//moram zbog fronta 404

	    List<ClassEntity> classes = new ArrayList<>();
	    for (TeacherSubjectClassEntity tsc : teacherSubjectClasses) {
	        classes.add(tsc.getClassEntity());
	    }

	    return new ResponseEntity<>(classes, HttpStatus.OK);
	}
    
	public ResponseEntity<?> getAllSubjectsForTeacher(Integer teacherId) {
	    Optional<TeacherEntity> teacher = teacherRepository.findById(teacherId);

	    if (!teacher.isPresent()) {
	        return new ResponseEntity<>(new RESTError(1, "Teacher not found"), HttpStatus.NOT_FOUND);
	    }

	    List<TeacherSubjectClassEntity> teacherSubjectClasses = teacherSubjectClassRepository.findByTeacher_TeacherId(teacherId);

	    List<SubjectEntity> subjects = new ArrayList<>();
	    for (TeacherSubjectClassEntity tsc : teacherSubjectClasses) {
	        subjects.add(tsc.getSubject());
	    }

	    return new ResponseEntity<>(subjects, HttpStatus.OK);
	}
	@Override
	public void updateClassesForTeacher(Integer teacherId, List<Integer> classesIds) {
	    Optional<TeacherEntity> teacherOpt = teacherRepository.findById(teacherId);
	    if (teacherOpt.isEmpty()) {
	        throw new RuntimeException("Teacher not found");
	    }

	    TeacherEntity teacher = teacherOpt.get();
	    //deelte jer se veze ponavljaju i 5 puta pise isto
	    List<TeacherSubjectClassEntity> existingRelations = teacherSubjectClassRepository.findByTeacher_TeacherId(teacherId);
	    System.out.println("Deleting existing relations: " + existingRelations.size());
	    teacherSubjectClassRepository.deleteAll(existingRelations);

	  
	    for (Integer classId : classesIds) {
	        ClassEntity klasa = classRepository.findById(classId)
	            .orElseThrow(() -> new RuntimeException("Class not found"));
	        TeacherSubjectClassEntity teacherSubjectClass = new TeacherSubjectClassEntity();
	        teacherSubjectClass.setTeacher(teacher);
	        teacherSubjectClass.setKlasa(klasa);

	        teacherSubjectClassRepository.save(teacherSubjectClass);
	    }
	}
}

