package com.iktpreobuka.elektronski_dnevnik.controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronski_dnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronski_dnevnik.services.TeacherService;
import com.iktpreobuka.elektronski_dnevnik.services.TeacherSubjectClassService;

@RestController
@RequestMapping(value = "/school/teachers")
public class TeacherController {
	@Autowired
	protected TeacherService teacherService;
	@Autowired
	private TeacherSubjectClassService teacherSubjectClassService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllTeachers() {
		return teacherService.getAllTeachers();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findByTeacherId(@PathVariable Integer id) {
		return teacherService.findByTeacherId(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addTeacher(@RequestBody TeacherEntity teacher) {
		return teacherService.addTeacher(teacher);

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteTeacher(@PathVariable Integer id) {
		return teacherService.deleteTeacher(id);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> modifyTeacher(@PathVariable Integer id, @RequestBody TeacherEntity teacher) {
	        ResponseEntity<?> updatedTeacher = teacherService.modifyTeacher(id, teacher);
	        return ResponseEntity.ok(Collections.singletonMap("data", updatedTeacher));//jer reactadmin ocekuje data ispred informacija
	    
	}
	@RequestMapping(method = RequestMethod.POST, value = "/{teacherId}/subject/{subjectId}/class/{classId}")
	public ResponseEntity<?> assignTeacherToSubjectAndClass(@PathVariable Integer teacherId,
			@PathVariable Integer subjectId, @PathVariable Integer classId) {
		return teacherSubjectClassService.assignTeacherToSubjectAndClass(teacherId, subjectId, classId);
	}
	@RequestMapping(method = RequestMethod.PUT, value = "/updateTeacherSubjectClass")
	public ResponseEntity<?> updateTeacherSubjectClass(@RequestParam Integer teacherId,
			@RequestParam Integer currentSubjectId, @RequestParam Integer currentClassId,
			@RequestParam Integer newSubjectId, @RequestParam Integer newClassId) {
		return teacherSubjectClassService.updateTeacherSubjectClass(teacherId, currentSubjectId, currentClassId,
				newSubjectId, newClassId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/classes/{teacherId}")
	public ResponseEntity<?> getAllClassesForTeacher(@PathVariable Integer teacherId){
		return teacherService.getAllClassesForTeacher(teacherId);
	}
	@RequestMapping(method = RequestMethod.GET, value = "/subjects/{teacherId}")
	public ResponseEntity<?> getAllSubjectsForTeacher(@PathVariable Integer teacherId){
		return teacherService.getAllSubjectsForTeacher(teacherId);
	}
}
