package com.iktpreobuka.elektronski_dnevnik.services;

import org.springframework.http.ResponseEntity;

public interface TeacherSubjectClassService {
	public ResponseEntity<?> updateTeacherSubjectClass(Integer teacherId, Integer currentSubjectId, Integer currentClassId, Integer newSubjectId,
           Integer newClassId);

	public ResponseEntity<?> assignTeacherToSubjectAndClass(Integer teacherId, Integer subjectId, Integer classId);
}
