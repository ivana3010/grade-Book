package com.iktpreobuka.elektronski_dnevnik.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.elektronski_dnevnik.entities.ParentEntity;


public interface ParentService {
	public ResponseEntity<?> getAllParents();
	public ResponseEntity<?> addParent(ParentEntity parent);
	public ResponseEntity<?> deleteParent(Integer parentId);
	public ResponseEntity<?> modifyParent(Integer parentId, ParentEntity parent);
	public ResponseEntity<?> findByParentId(Integer parentId);
	public ResponseEntity<?> getStudentsByParentId( Integer parentId);
}
