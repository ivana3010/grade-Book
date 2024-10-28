package com.iktpreobuka.elektronski_dnevnik.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.elektronski_dnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronski_dnevnik.security.Views;
import com.iktpreobuka.elektronski_dnevnik.services.MarkService;

@RestController
@RequestMapping(value = "/school/marks")
public class MarkController {

	@Autowired
	private MarkService markService;
	private final Logger loger = LoggerFactory.getLogger(MarkController.class);//loger instanca da mozemo poslati poruku

	@JsonView(Views.Teacher.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addMark(@RequestParam Integer teacherId, @RequestParam Integer subjectId,
			@RequestParam Integer classId, @RequestParam Integer studentId, @RequestParam Integer mark,
			@RequestParam String markType) {
		

		return markService.addMark(teacherId, subjectId, classId, studentId, mark, markType);
	}

	@JsonView(Views.Student.class)
	@RequestMapping(method = RequestMethod.GET, value = "/student/{studentId}")
	public ResponseEntity<?> getGradesByStudentId(@PathVariable Integer studentId) {
		
		return markService.getGradesByStudentId(studentId);
	}

	@JsonView(Views.Teacher.class)
	@RequestMapping(value = "/{markId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteMark(@PathVariable Integer markId, @RequestParam String role) {
		if (!"teacher".equals(role) && !"admin".equals(role)) {
			return new ResponseEntity<>(new RESTError(1, "Unauthorized"), HttpStatus.UNAUTHORIZED);
		}
		return markService.deleteMark(markId);
	}

	@JsonView(Views.Teacher.class)
	@RequestMapping(value = "/{markId}", method = RequestMethod.PUT)
	public ResponseEntity<?> modify(@PathVariable Integer markId, @RequestParam Integer mark,
			@RequestParam String markType, @RequestParam String role) {
		if (!"teacher".equals(role) && !"admin".equals(role)) {
			return new ResponseEntity<>(new RESTError(1, "Unauthorized"), HttpStatus.UNAUTHORIZED);
		}
		return markService.updateMark(markId, mark, markType);
	}

	@JsonView(Views.Teacher.class)
	@RequestMapping(method = RequestMethod.GET,value = "/byTeacherAndSubject")
	public ResponseEntity<?> getMarksByTeacherAndSubject(@RequestParam Integer teacherId,
			@RequestParam Integer subjectId, @RequestParam String role) {
		if (!"teacher".equals(role) && !"admin".equals(role)) {
			return new ResponseEntity<>(new RESTError(1, "Unauthorized"), HttpStatus.UNAUTHORIZED);
		}
		return markService.getMarksByTeacherAndSubject(teacherId, subjectId);
	}

	@JsonView(Views.Teacher.class)
	@RequestMapping(method = RequestMethod.GET,value = "/test")
	public ResponseEntity<?> getTestMarks(@RequestParam String role) {
		if (!"teacher".equals(role) && !"admin".equals(role)) {
			return new ResponseEntity<>(new RESTError(1, "Unauthorized"), HttpStatus.UNAUTHORIZED);
		}
		return markService.getTestMarks();
	}

	@JsonView(Views.Teacher.class)
	@RequestMapping(value = "/written", method = RequestMethod.GET)
	public ResponseEntity<?> getWrittenExamMarks(@RequestParam String role) {
		if (!"teacher".equals(role) && !"admin".equals(role)) {
			return new ResponseEntity<>(new RESTError(1, "Unauthorized"), HttpStatus.UNAUTHORIZED);
		}
		return markService.getWrittenExamMarks();
	}

	@JsonView(Views.Teacher.class)
	@RequestMapping(value = "/oral", method = RequestMethod.GET)
	public ResponseEntity<?> getOralExamMarks(@RequestParam String role) {
		if (!"teacher".equals(role) && !"admin".equals(role)) {
			return new ResponseEntity<>(new RESTError(1, "Unauthorized"), HttpStatus.UNAUTHORIZED);
		}
		return markService.getOralExamMarks();
	}

	public ResponseEntity<?> getAllMark(@RequestParam String role) {
		if (!"admin".equals(role)) {
			return new ResponseEntity<>(new RESTError(1, "Unauthorized"), HttpStatus.UNAUTHORIZED);
		}
		return markService.getAllMark();
	}

	@JsonView(Views.Parent.class)
	@RequestMapping(method = RequestMethod.GET, value = "/parent/{parentId}")
	public ResponseEntity<?> getMarksForParent(@PathVariable Integer parentId, @RequestParam String role) {
		if (!"parent".equals(role) && !"admin".equals(role)) {
			return new ResponseEntity<>(new RESTError(1, "Unauthorized"), HttpStatus.UNAUTHORIZED);
		}

		return markService.getAllMarksForParent(parentId);
	}

}
