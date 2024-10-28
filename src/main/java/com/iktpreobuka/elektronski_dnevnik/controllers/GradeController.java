package com.iktpreobuka.elektronski_dnevnik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronski_dnevnik.entities.GradeEntity;
import com.iktpreobuka.elektronski_dnevnik.services.GradeService;

@RestController
@RequestMapping(value="/school/grades")
public class GradeController {
	@Autowired
	protected GradeService gradeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllGrades() {
		return gradeService.getAllGrades();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/gradeid/{id}")
	public ResponseEntity<?> findByGradeId(@PathVariable Integer id) {
		return gradeService.findByGradeId(id);
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addGrade(@RequestBody GradeEntity grade) {
		return gradeService.addGrade(grade);

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteGrade(@PathVariable Integer id) {
		return gradeService.deleteGrade(id);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> modifyGrade(@PathVariable Integer id, @RequestBody GradeEntity grade) {
		return gradeService.modifyGrade(id, grade);
	}
	@RequestMapping(method = RequestMethod.GET, value="/{id}")
	public ResponseEntity<?> getAllClassesForGrade(@PathVariable Integer id) {
		return gradeService.getAllClassesForGrade(id);
	}
	
}
