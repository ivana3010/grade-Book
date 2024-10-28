package com.iktpreobuka.elektronski_dnevnik.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.elektronski_dnevnik.entities.TeacherEntity;

public interface TeacherService {
	public ResponseEntity<?> getAllTeachers();
	public ResponseEntity<?> addTeacher(TeacherEntity teacher);
	public ResponseEntity<?> deleteTeacher(Integer teacherId);
	public ResponseEntity<?> modifyTeacher(Integer teacherId, TeacherEntity teacher);
	public ResponseEntity<?> findByTeacherId(Integer teacherId);
	public ResponseEntity<?> getAllClassesForTeacher(Integer teacherId);
	public ResponseEntity<?> getAllSubjectsForTeacher(Integer teacherId);
	public void updateClassesForTeacher(Integer teacherId, List<Integer> classesIds);
}
