package com.iktpreobuka.elektronski_dnevnik.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler"})
public class ParentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer parentId;
	protected String name;
	protected String surname;
	protected String email;
	@JsonIgnore
	 @OneToMany(mappedBy = "parent")
	 private List<StudentParentEntity> studentParent = new ArrayList<>();
	 
	 
	public List<StudentParentEntity> getStudentParent() {
		return studentParent;
	}
	public void setStudentParent(List<StudentParentEntity> studentParent) {
		this.studentParent = studentParent;
	}
	public ParentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ParentEntity(Integer parentId, String name, String surname, String email) {
		super();
		this.parentId = parentId;
		this.name = name;
		this.surname = surname;
		this.email = email;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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
