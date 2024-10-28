package com.iktpreobuka.elektronski_dnevnik.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.elektronski_dnevnik.entities.ClassEntity;


public interface ClassService {
	public ResponseEntity<?> getAllClasses();
	public ResponseEntity<?> addClass(ClassEntity klasa);
	public ResponseEntity<?> deleteClass(Integer classId);
	public ResponseEntity<?> modifyClass(Integer classId, ClassEntity klasa);
	public ResponseEntity<?> findByClassId(Integer classId);
	public ResponseEntity<?> getAllStudentsForClass(Integer classId);
	public ResponseEntity<?> addClassToGrade(Integer classId, Integer gradeId);
	public ResponseEntity<?> getAllTeachersForClass(Integer classId);
	public ResponseEntity<?> getAllSubjectsForClass(Integer classId);
	
}
