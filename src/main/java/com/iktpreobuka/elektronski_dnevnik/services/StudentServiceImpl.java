package com.iktpreobuka.elektronski_dnevnik.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronski_dnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronski_dnevnik.entities.ClassEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.ParentEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.StudentParentEntity;
import com.iktpreobuka.elektronski_dnevnik.repositories.ClassRepository;
import com.iktpreobuka.elektronski_dnevnik.repositories.ParentRepository;
import com.iktpreobuka.elektronski_dnevnik.repositories.StudentParentRepository;
import com.iktpreobuka.elektronski_dnevnik.repositories.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private ClassRepository classRepository;
	@Autowired
    private StudentParentRepository studentParentRepository;
	@Autowired
    private ParentRepository parentRepository;

	@Override
	public ResponseEntity<?> getAllStudents() {
		return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> addStudent(StudentEntity student) {
		StudentEntity entity = new StudentEntity();
        entity.setStudentId(student.getStudentId());
        entity.setName(student.getName());
        entity.setSurname(student.getSurname());
        entity.setEmail(student.getEmail());
        if (student.getOdeljenje() != null && student.getOdeljenje().getClassId() != null) {
            entity.setOdeljenje(student.getOdeljenje());
            studentRepository.save(entity);
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
        
        //Map<String, Object> response = new HashMap<>();
        //response.put("data", Collections.singletonList(entity));//zbog fronta i ocekivanja date
		//return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/*@Override
	public ResponseEntity<?> deleteStudent(Integer studentId) {
		Optional<StudentEntity> se = studentRepository.findById(studentId);
		if (se.isPresent()) {
			studentRepository.delete(se.get());
			return new ResponseEntity<>(se.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(new RESTError(1, "Student not found"), HttpStatus.NOT_FOUND);
	}*/
	@Transactional
    public ResponseEntity<?> deleteStudent(Integer id) {
        StudentEntity student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
        
        // Ručno inicijalizujte lazy-loaded entitete
        Hibernate.initialize(student.getOdeljenje());
        
        // Brisanje
        studentRepository.delete(student);
        return ResponseEntity.ok().build();
    }

	@Override
	public ResponseEntity<?> modifyStudent(Integer studentId, StudentEntity student) {
		Optional<StudentEntity> studentEntity = studentRepository.findById(studentId);
		if (studentEntity.isPresent()) {
			StudentEntity entity = studentEntity.get();
			
	        entity.setName(student.getName());
	        entity.setSurname(student.getSurname());
	        entity.setEmail(student.getEmail());
	        entity.setOdeljenje(student.getOdeljenje());
	        studentRepository.save(entity);
	        Map<String, Object> responseBody = new HashMap<>();
	        responseBody.put("id", entity.getStudentId()); // jer ocekuje datu plus sve ove podatke
	        responseBody.put("name", entity.getName());
	        responseBody.put("surname", entity.getSurname());
	        responseBody.put("email", entity.getEmail());
	        return new ResponseEntity<>(Map.of("data", entity), HttpStatus.OK);
			
		}
		return new ResponseEntity<>(new RESTError(1, "Student not found"), HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<?> findByStudentId(Integer studentId) {
		Optional<StudentEntity> student = studentRepository.findById(studentId);
		if (student.isPresent()) {
			return new ResponseEntity<>(student.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new RESTError(1, "Student not found"), HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<?> addStudentToClass(Integer studentId, Integer classId) {
		Optional<StudentEntity> student = studentRepository.findById(studentId);
        Optional<ClassEntity> klasa = classRepository.findById(classId);

        if (student.isPresent() && klasa.isPresent()) {
        	StudentEntity studentEntity=student.get();
        	ClassEntity classEntity=klasa.get();
            studentEntity.setOdeljenje(classEntity);
           studentRepository.save(studentEntity);
           
           Map<String, Object> response = new HashMap<>();
           response.put("data", studentEntity);
           return new ResponseEntity<>(studentEntity, HttpStatus.OK);
        }
        return new ResponseEntity<>(new RESTError(1, "Student not found"), HttpStatus.NOT_FOUND);
    }
	
	public ResponseEntity<?> addParentToStudent(Integer studentId, Integer parentId) {
        Optional<StudentEntity> student = studentRepository.findById(studentId);
        Optional<ParentEntity> parent = parentRepository.findById(parentId);
        
        if (student.isPresent() && parent.isPresent()) {
        	StudentEntity studentEntity = student.get();//da dobijemo stvarne entitete
            ParentEntity parentEntity = parent.get();
            StudentParentEntity studentParent = new StudentParentEntity();
	        studentParent.setStudent(studentEntity);
	        studentParent.setParent(parentEntity);
	        studentParentRepository.save(studentParent);
	        return new ResponseEntity<>(studentParent, HttpStatus.OK);}
	        else if (!student.isPresent()) {
	            return new ResponseEntity<>(new RESTError(1, "Student not found"), HttpStatus.NOT_FOUND);
	        } else {
	            return new ResponseEntity<>(new RESTError(2, "Parent not found"), HttpStatus.NOT_FOUND);
	        }
    }
	
	public ResponseEntity<?> getParentsByStudentId(Integer studentId) {
	    List<StudentParentEntity> studentParents = studentParentRepository.findByStudentStudentId(studentId);
	    if (studentParents == null || studentParents.isEmpty()) {
	        // prazan niz sa statusom 200 ok zbog gresaka na frontu
	        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
	    }
	    List<ParentEntity> parents = new ArrayList<>();
	    for (StudentParentEntity studentParent : studentParents) {
	        parents.add(studentParent.getParent());
	    }

	    return new ResponseEntity<>(parents, HttpStatus.OK);
	}
	
}
