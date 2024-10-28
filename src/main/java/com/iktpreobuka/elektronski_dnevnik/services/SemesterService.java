package com.iktpreobuka.elektronski_dnevnik.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.elektronski_dnevnik.entities.SemesterEntity;

public interface SemesterService {
	public ResponseEntity<?> getAllSemesters();
	public ResponseEntity<?> addSemester(SemesterEntity semester);
	public ResponseEntity<?> deleteSemester(Integer semesterId);
	public ResponseEntity<?> modifySemester(Integer semesterId, SemesterEntity semester);
	public ResponseEntity<?> findBySemesterId(Integer semesterId);
}
