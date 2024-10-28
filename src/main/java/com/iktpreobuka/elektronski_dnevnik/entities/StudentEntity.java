package com.iktpreobuka.elektronski_dnevnik.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler"})
public class StudentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer studentId;
	protected String name;
	protected String surname;
	protected String email;
	
	
	 @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	 @JoinColumn(name = "odeljenje")
	 
	 private ClassEntity odeljenje;
	 @JsonManagedReference 
	 @OneToMany(mappedBy = "student")
	 private List<StudentParentEntity> studentParent = new ArrayList<>();
	 
	public List<StudentParentEntity> getStudentParent() {
		return studentParent;
	}
	public void setStudentParent(List<StudentParentEntity> studentParent) {
		this.studentParent = studentParent;
	}
	public ClassEntity getOdeljenje() {
		return odeljenje;
	}
	public void setOdeljenje(ClassEntity odeljenje) {
		this.odeljenje = odeljenje;
	}
	public StudentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StudentEntity(Integer studentId, String name, String surname, String email) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.surname = surname;
		this.email = email;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
