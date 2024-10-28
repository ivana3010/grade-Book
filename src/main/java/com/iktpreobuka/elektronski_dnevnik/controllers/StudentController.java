package com.iktpreobuka.elektronski_dnevnik.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronski_dnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronski_dnevnik.repositories.StudentRepository;
import com.iktpreobuka.elektronski_dnevnik.services.StudentService;

@RestController

@RequestMapping(value="/school/students")
public class StudentController {
	@Autowired
	protected StudentService studentService;
	@Autowired
	protected StudentRepository studentRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllStudents() {
		return studentService.getAllStudents();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{id}")
	public ResponseEntity<?> findByStudentId(@PathVariable Integer id) {
		return studentService.findByStudentId(id);
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addStudent(@RequestBody StudentEntity student) {
		return studentService.addStudent(student);

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable Integer id) {
		return studentService.deleteStudent(id);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> modifyStudent(@PathVariable Integer id, @RequestBody StudentEntity student) {
		return studentService.modifyStudent(id, student);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/{studentId}/class/{classId}")
	public ResponseEntity<?> addStudentToClass(@PathVariable Integer studentId, @PathVariable Integer classId) {
		return studentService.addStudentToClass(studentId, classId);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/{studentId}/parent/{parentId}")
	public ResponseEntity<?> addParentToStudent(@PathVariable Integer studentId, @PathVariable Integer parentId) {
		return studentService.addParentToStudent(studentId, parentId);
	}
	@RequestMapping(method = RequestMethod.GET, value="/studpar/{studentId}")
	public ResponseEntity<?> getParentsByStudentId(@PathVariable Integer studentId) {
		return studentService.getParentsByStudentId(studentId);
		
	}
	@RequestMapping(method=RequestMethod.GET, value="/search")
    public Iterable<StudentEntity> searchStudentByName(@RequestParam String name) {
        Iterable<StudentEntity> l = studentRepository.findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(name, name);
        ArrayList<StudentEntity> ll = new ArrayList<>();
        for(StudentEntity g : l) {
            ll.add(g);
        }
        return ll;
    }
}
