package com.iktpreobuka.elektronski_dnevnik.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.elektronski_dnevnik.entities.GradeEntity;


public interface GradeService {
	public ResponseEntity<?> getAllGrades();
	public ResponseEntity<?> addGrade(GradeEntity grade);
	public ResponseEntity<?> deleteGrade(Integer gradeId);
	public ResponseEntity<?> modifyGrade(Integer gradeId, GradeEntity grade);
	public ResponseEntity<?> findByGradeId(Integer gradeId);
	public ResponseEntity<?> getAllClassesForGrade(Integer gradeId);
}
