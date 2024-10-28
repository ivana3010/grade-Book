package com.iktpreobuka.elektronski_dnevnik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronski_dnevnik.entities.ParentEntity;
import com.iktpreobuka.elektronski_dnevnik.services.ParentService;

@RestController
@RequestMapping(value="/school/parents")
public class ParentController {
	@Autowired
	protected ParentService parentService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllParents() {
		return parentService.getAllParents();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{id}")
	public ResponseEntity<?> findByParentId(@PathVariable Integer id) {
		return parentService.findByParentId(id);
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addParent(@RequestBody ParentEntity parent) {
		return parentService.addParent(parent);

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteParent(@PathVariable Integer id) {
		return parentService.deleteParent(id);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> modifyParent(@PathVariable Integer id, @RequestBody ParentEntity parent) {
		System.out.println("Received ID: " + id);
		return parentService.modifyParent(id, parent);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/studpar/{parentId}")
	public ResponseEntity<?> getStudentsByParentId(@PathVariable Integer parentId){
		return parentService.getStudentsByParentId(parentId);
	}
}
