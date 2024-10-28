package com.iktpreobuka.elektronski_dnevnik.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronski_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.SubjectRequest;
import com.iktpreobuka.elektronski_dnevnik.repositories.SubjectRepository;
import com.iktpreobuka.elektronski_dnevnik.services.SubjectService;

@RestController
@RequestMapping(value="/school/subjects")
public class SubjectController {
	@Autowired
	protected SubjectService subjectService;
	@Autowired
	protected SubjectRepository subjectRepository;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllSubject() {
		return subjectService.getAllSubject();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{id}")
	public ResponseEntity<?> findBySubjectId(@PathVariable Integer id) {
		return subjectService.findBySubjectId(id);
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addSubject(@RequestBody SubjectRequest request) {
	    return subjectService.addSubject(request.getSubjectId(), request.getName(), request.getFund() /*request.getTeacherIds()*/);
	}

	@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable Integer id) {
        try {
            subjectService.deleteSubject(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

	
	@RequestMapping(method = RequestMethod.GET, value="/teachers/{subjectId}")
	public ResponseEntity<?> getAllTeachersForSubject(@PathVariable Integer subjectId){
		return subjectService.getAllTeachersForSubject(subjectId);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/{id}")
	public ResponseEntity<?> modifySubject(@PathVariable Integer id, @RequestBody SubjectEntity subject) {
	    System.out.println("Modify subject: " + id);
	    try {
	        ResponseEntity<?> response = subjectService.modifySubject(id, subject);
	        //subjectService.updateTeachersForSubject(id, subject.getTeacherIds());//zbog liste nastavnika na frontu
	        return response;
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    }
	}
	/*@RequestMapping(method = RequestMethod.GET)
	    public ResponseEntity<?> getSubjectsName(@RequestParam(required = false) String name) {
		if (name != null && !name.isEmpty()) {
	        return ResponseEntity.ok(subjectService.getSubjectsName(name));
	    } else {
	        return ResponseEntity.ok(subjectService.getAllSubject());
	    }
	    }*/
	@RequestMapping(method=RequestMethod.GET, value="/search")
    public Iterable<SubjectEntity> searchSubjectByName(@RequestParam String name) {
        Iterable<SubjectEntity> l = subjectRepository.findByNameContainingIgnoreCase(name);
        ArrayList<SubjectEntity> ll = new ArrayList<>();
        for(SubjectEntity g : l) {
            ll.add(g);
        }
        return ll;
    }

    }

