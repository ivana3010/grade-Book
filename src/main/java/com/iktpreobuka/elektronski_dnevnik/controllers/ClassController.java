package com.iktpreobuka.elektronski_dnevnik.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronski_dnevnik.entities.ClassEntity;
import com.iktpreobuka.elektronski_dnevnik.services.ClassService;

@RestController
@RequestMapping(value="/school/classes")
public class ClassController {
	@Autowired
	protected ClassService classService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllClasses() {
		return classService.getAllClasses();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{id}")
	public ResponseEntity<?> findByClassId(@PathVariable Integer id) {
		return classService.findByClassId(id);
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addClass(@RequestBody ClassEntity klasa) {
		return classService.addClass(klasa);

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteClass(@PathVariable Integer id) {
		return classService.deleteClass(id);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> modifyClass(@PathVariable Integer id, @RequestBody ClassEntity klasa) {
		return classService.modifyClass(id, klasa);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/students/{classId}")
	public ResponseEntity<?> getAllStudentsForClass (@PathVariable Integer classId){
		return classService.getAllStudentsForClass(classId);
	}
	@RequestMapping(method = RequestMethod.POST, value="/{classId}/grade/{gradeId}")
	public ResponseEntity<?> addClassToGrade(@PathVariable Integer classId,@PathVariable Integer gradeId){
		return classService.addClassToGrade(classId, gradeId);
	}
	@RequestMapping(method = RequestMethod.GET, value="/teachers/{classId}")
    public ResponseEntity<?> getAllTeachersForClass(@PathVariable Integer classId) {
        return classService.getAllTeachersForClass(classId);
    }
	@RequestMapping(method = RequestMethod.GET, value="/subjects/{classId}")
    public ResponseEntity<?> getAllSubjectsForClass(@PathVariable Integer classId) {
        return classService.getAllSubjectsForClass(classId);
    }
	
	
}
