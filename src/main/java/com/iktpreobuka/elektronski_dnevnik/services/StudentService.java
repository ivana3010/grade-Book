package com.iktpreobuka.elektronski_dnevnik.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.elektronski_dnevnik.entities.StudentEntity;

public interface StudentService {
	public ResponseEntity<?> getAllStudents();
	public ResponseEntity<?> addStudent(StudentEntity student);
	public ResponseEntity<?> deleteStudent(Integer studentId);
	public ResponseEntity<?> modifyStudent(Integer studentId, StudentEntity student);
	public ResponseEntity<?> findByStudentId(Integer studentId);
	public ResponseEntity<?> addStudentToClass(Integer studentId, Integer classId);
	public ResponseEntity<?> addParentToStudent(Integer studentId, Integer parentId);
	public ResponseEntity<?> getParentsByStudentId(Integer studentId);
}
