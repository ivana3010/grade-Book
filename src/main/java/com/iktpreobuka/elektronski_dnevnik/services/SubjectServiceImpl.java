package com.iktpreobuka.elektronski_dnevnik.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.iktpreobuka.elektronski_dnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronski_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.TeacherSubjectClassEntity;
import com.iktpreobuka.elektronski_dnevnik.repositories.SubjectRepository;
import com.iktpreobuka.elektronski_dnevnik.repositories.TeacherRepository;
import com.iktpreobuka.elektronski_dnevnik.repositories.TeacherSubjectClassRepository;

import jakarta.transaction.Transactional;

@Service
public class SubjectServiceImpl implements SubjectService {
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private TeacherSubjectClassRepository teacherSubjectClassRepository;
	@Autowired
	private TeacherRepository teacherRepository;
	
	

	@Override
	public ResponseEntity<?> getAllSubject() {
		return new ResponseEntity<>(subjectRepository.findAll(), HttpStatus.OK);
	}

	public ResponseEntity<?> addSubject(Integer subjectId, String name, Integer fund /*List<Integer> teacherIds*/) {
	    SubjectEntity entity = new SubjectEntity();
	    entity.setSubjectId(subjectId);
	    entity.setName(name);
	    entity.setFund(fund);
	    entity = subjectRepository.save(entity);

	    // veza izmedju predmeta i nastavnika
	    /*List<TeacherSubjectClassEntity> teacherSubjectClassList = new ArrayList<>();
	    for (Integer teacherId : teacherIds) {
	        TeacherEntity teacher = teacherRepository.findById(teacherId)
	                .orElseThrow(() -> new RuntimeException("Teacher not found"));
	        TeacherSubjectClassEntity teacherSubjectClass = new TeacherSubjectClassEntity();
	        teacherSubjectClass.setTeacher(teacher);
	        teacherSubjectClass.setSubject(entity);

	        teacherSubjectClassList.add(teacherSubjectClass);
	    }
	    teacherSubjectClassRepository.saveAll(teacherSubjectClassList);*/
	    return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	
	@Transactional
    @Override
	public ResponseEntity<?> deleteSubject(Integer subjectId) {
            //zbog foreign kljuceva iz baze
            teacherSubjectClassRepository.deleteBySubject_SubjectId(subjectId);
            subjectRepository.deleteById(subjectId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//no content znaci uspesno brisanje
    }

	@Transactional
	@Override
	public ResponseEntity<?> modifySubject(Integer subjectId, SubjectEntity subject) {
	    Optional<SubjectEntity> subjectEntity = subjectRepository.findById(subjectId);
	    if (subjectEntity.isPresent()) {
	        SubjectEntity entity = subjectEntity.get();
	        entity.setName(subject.getName());
	        entity.setFund(subject.getFund());
	        
	        /*if (subject.getTeacherIds() != null) {
	            updateTeachersForSubject(subjectId, subject.getTeacherIds());//druga metoda
	        }
	        subjectRepository.save(entity);
	        
	        Map<String, Object> response = new HashMap<>();//jer ocekuje id front
	        response.put("id", entity.getSubjectId()); 
	        response.put("name", entity.getName());
	        response.put("fund", entity.getFund());
	        return new ResponseEntity<>(response, HttpStatus.OK);*/
	        return new ResponseEntity<>(entity, HttpStatus.OK);
	    }
	    return new ResponseEntity<>(new RESTError(1, "Subject not found"), HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<?> findBySubjectId(Integer subjectId) {
		Optional<SubjectEntity> subject = subjectRepository.findById(subjectId);
		if (subject.isPresent()) {
			return new ResponseEntity<>(subject.get(), HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(new RESTError(1, "Subject not found"), HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<?> getAllTeachersForSubject(Integer subjectId) {
        Optional<SubjectEntity> subject = subjectRepository.findById(subjectId);

        if (!subject.isPresent()) {
            return new ResponseEntity<>(new RESTError(1, "Subject not found"), HttpStatus.NOT_FOUND);
        }
        List<TeacherSubjectClassEntity> teacherSubjectClasses = teacherSubjectClassRepository.findBySubject_SubjectId(subjectId);//nalazimo sve zapise po subjectId
        List<TeacherEntity> teachers = new ArrayList<>();
        for (TeacherSubjectClassEntity tsc : teacherSubjectClasses) {
            teachers.add(tsc.getTeacher());
        }
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }
	
	@Override
	public void updateTeachersForSubject(Integer subjectId, List<Integer> teacherIds) {
	 Optional<SubjectEntity> subjectOpt = subjectRepository.findById(subjectId);
	    if (subjectOpt.isEmpty()) {
	        throw new RuntimeException("Subject not found");
	    }
	    SubjectEntity subject = subjectOpt.get();
	    
	    List<TeacherSubjectClassEntity> existingRelations = teacherSubjectClassRepository.findBySubject_SubjectId(subjectId);
	    
	   teacherSubjectClassRepository.deleteAll(existingRelations);
	    for (Integer teacherId : teacherIds) {
	        TeacherEntity teacher = teacherRepository.findById(teacherId)
	            .orElseThrow(() -> new RuntimeException("Teacher not found"));
	        TeacherSubjectClassEntity teacherSubjectClass = new TeacherSubjectClassEntity();
	        teacherSubjectClass.setTeacher(teacher);
	        teacherSubjectClass.setSubject(subject);

	        teacherSubjectClassRepository.save(teacherSubjectClass);
	    }
	}
	
	
    public List<SubjectEntity> getSubjectsName( String name) {
        if (name != null && !name.isEmpty()) {
            return subjectRepository.findByNameContainingIgnoreCase(name); // Pretraga po imenu
        } else {
            return (List<SubjectEntity>) subjectRepository.findAll(); // Svi predmeti
        }
    }
    }



