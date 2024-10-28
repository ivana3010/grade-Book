package com.iktpreobuka.elektronski_dnevnik.services;

import org.springframework.http.ResponseEntity;




public interface MarkService {
	ResponseEntity<?> addMark(Integer teacherId, Integer subjectId, Integer classId, Integer studentId, Integer mark, String markType);
	public ResponseEntity<?> getGradesByStudentId(Integer studentId);
	ResponseEntity<?> deleteMark(Integer markId);
	ResponseEntity<?> updateMark(Integer markId, Integer markValue, String markType);
	ResponseEntity<?> getMarksByTeacherAndSubject(Integer teacherId, Integer subjectId);
	public ResponseEntity<?> getTestMarks() ;
	public ResponseEntity<?> getWrittenExamMarks();
	public ResponseEntity<?> getOralExamMarks();
	public ResponseEntity<?> getAllMark();
	public ResponseEntity<?> getAllMarksForParent(Integer parentId);
}
