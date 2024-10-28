package com.iktpreobuka.elektronski_dnevnik.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.elektronski_dnevnik.entities.SubjectEntity;

public interface SubjectService {
	public ResponseEntity<?> getAllSubject();
	public ResponseEntity<?> addSubject(Integer subjectId, String name, Integer fund /*List<Integer> teacherIds*/);
	public ResponseEntity<?> deleteSubject(Integer subjectId);
	public ResponseEntity<?> modifySubject(Integer subjectId, SubjectEntity subject);
	public ResponseEntity<?> findBySubjectId(Integer subjectId);
	public ResponseEntity<?> getAllTeachersForSubject(Integer subjectId);
	public void updateTeachersForSubject(Integer subjectId, List<Integer> teacherIds);
	public List<SubjectEntity> getSubjectsName( String name);
}
