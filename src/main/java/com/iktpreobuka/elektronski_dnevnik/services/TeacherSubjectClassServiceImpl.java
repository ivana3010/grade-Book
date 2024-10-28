package com.iktpreobuka.elektronski_dnevnik.services;

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
import com.iktpreobuka.elektronski_dnevnik.repositories.SubjectRepository;
import com.iktpreobuka.elektronski_dnevnik.repositories.TeacherRepository;
import com.iktpreobuka.elektronski_dnevnik.repositories.TeacherSubjectClassRepository;

@Service
public class TeacherSubjectClassServiceImpl implements TeacherSubjectClassService{
	@Autowired
    private TeacherSubjectClassRepository teacherSubjectClassRepository;
	@Autowired
    private ClassRepository classRepository;
	@Autowired
    private TeacherRepository teacherRepository;
	@Autowired
    private SubjectRepository subjectRepository;
    
	public ResponseEntity<?> assignTeacherToSubjectAndClass(Integer teacherId, Integer subjectId, Integer classId) {
		Optional<TeacherEntity> teacher = teacherRepository.findById(teacherId);
        Optional<SubjectEntity> subject = subjectRepository.findById(subjectId);
        Optional<ClassEntity> klasa = classRepository.findById(classId);
        if (!teacher.isPresent()) {
            return new ResponseEntity<>(new RESTError(1, "Teacher not found"), HttpStatus.NOT_FOUND);
        }

        if (!subject.isPresent()) {
            return new ResponseEntity<>(new RESTError(2, "Subject not found"), HttpStatus.NOT_FOUND);
        }

        if (!klasa.isPresent()) {
            return new ResponseEntity<>(new RESTError(3, "Class not found"), HttpStatus.NOT_FOUND);
        }
        
        TeacherSubjectClassEntity tsc = new TeacherSubjectClassEntity();
        tsc.setTeacher(teacher.get()); 
        tsc.setSubject(subject.get()); 
        tsc.setClassEntity(klasa.get());
        teacherSubjectClassRepository.save(tsc);
        return new ResponseEntity<>(tsc, HttpStatus.OK);
    }
	
	public ResponseEntity<?> updateTeacherSubjectClass(
	        Integer teacherId,
	        Integer currentSubjectId,
	        Integer currentClassId,
	        Integer newSubjectId,
	        Integer newClassId) {
		
		TeacherSubjectClassEntity existing = teacherSubjectClassRepository
	            .findByTeacherIdAndSubjectIdAndClassId(teacherId, currentSubjectId, currentClassId);

	    // proveram da li je zapis pronađen
	    if (existing == null) {
	        return new ResponseEntity<>(new RESTError(4, "Teacher And Subject And Class Not found"), HttpStatus.NOT_FOUND);
	    }

	    // dobijanje novog subject-a i klase
	    Optional<SubjectEntity> newSubject = subjectRepository.findById(newSubjectId);
	    Optional<ClassEntity> newClass = classRepository.findById(newClassId);

	    // provera da li novi subject i klasa postoje
	    if (newSubject.isEmpty()) {
	        return new ResponseEntity<>(new RESTError(2, "New subject not found"), HttpStatus.NOT_FOUND);
	    }

	    if (newClass.isEmpty()) {
	        return new ResponseEntity<>(new RESTError(3, "New class not found"), HttpStatus.NOT_FOUND);
	    }

	    existing.setSubject(newSubject.get());
	    existing.setClassEntity(newClass.get());

	    teacherSubjectClassRepository.save(existing);

	    return new ResponseEntity<>(existing, HttpStatus.OK);
	}}
