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
public class TeacherEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer teacherId;
	protected String name;
	protected String surname;
	protected String email;
	private List<Integer> classesIds;
	
	@OneToMany(mappedBy = "teacher")
    private List<TeacherSubjectClassEntity> teacherSubjectClass= new ArrayList<>();
	
	
	
	public List<Integer> getClassesIds() {
		return classesIds;
	}
	public void setClassesIds(List<Integer> classesIds) {
		this.classesIds = classesIds;
	}
	public TeacherEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	@JsonIgnore
	public List<TeacherSubjectClassEntity> getTeacherSubjectClass() {
		return teacherSubjectClass;
	}
	@JsonIgnore
	public void setTeacherSubjectClass(List<TeacherSubjectClassEntity> teacherSubjectClass) {
		this.teacherSubjectClass = teacherSubjectClass;
	}

	public TeacherEntity(Integer teacherId, String name, String surname, String email) {
		super();
		this.teacherId = teacherId;
		this.name = name;
		this.surname = surname;
		this.email = email;
	}
	public Integer getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
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
