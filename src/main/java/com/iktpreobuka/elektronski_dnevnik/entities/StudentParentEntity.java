package com.iktpreobuka.elektronski_dnevnik.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler"})
public class StudentParentEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer studparId;
	
	@JsonBackReference
	@ManyToOne
    @JoinColumn(name = "student")
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name = "parent")
    private ParentEntity parent;

	public Integer getStudparId() {
		return studparId;
	}

	public void setStudparId(Integer studparId) {
		this.studparId = studparId;
	}

	public StudentEntity getStudent() {
		return student;
	}

	public void setStudent(StudentEntity student) {
		this.student = student;
	}

	public ParentEntity getParent() {
		return parent;
	}

	public void setParent(ParentEntity parent) {
		this.parent = parent;
	}

	public StudentParentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentParentEntity(Integer studparId, StudentEntity student, ParentEntity parent) {
		super();
		this.studparId = studparId;
		this.student = student;
		this.parent = parent;
	}
    
    
}
