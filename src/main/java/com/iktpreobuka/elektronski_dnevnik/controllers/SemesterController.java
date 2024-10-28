package com.iktpreobuka.elektronski_dnevnik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronski_dnevnik.entities.SemesterEntity;
import com.iktpreobuka.elektronski_dnevnik.services.SemesterService;

@RestController
@RequestMapping(value="/school/semesters")
public class SemesterController {
	@Autowired
	private SemesterService semesterService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllSemesters() {
		return semesterService.getAllSemesters();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{id}")
	public ResponseEntity<?> findBySemesterId(@PathVariable Integer id) {
		return semesterService.findBySemesterId(id);
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addSemester(@RequestBody SemesterEntity semester) {
		return semesterService.addSemester(semester);

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteSemester(@PathVariable Integer id) {
		return semesterService.deleteSemester(id);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> modifySemester(@PathVariable Integer id, @RequestBody SemesterEntity semester) {
		return semesterService.modifySemester(id, semester);
	}
}
