package com.iktpreobuka.elektronski_dnevnik.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClassEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer classId;
	protected String name;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "odeljenje", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
    private List<StudentEntity> students = new ArrayList<>();
    
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "grade")
	@JsonIgnore
	private GradeEntity grade;
	
	@OneToMany(mappedBy = "klasa")
    private List<TeacherSubjectClassEntity> teacherSubjectClass= new ArrayList<>();
	
	
	
	@JsonIgnore
	public List<TeacherSubjectClassEntity> getTeacherSubjectClass() {
		return teacherSubjectClass;
	}
	public void setTeacherSubjectClass(List<TeacherSubjectClassEntity> teacherSubjectClass) {
		this.teacherSubjectClass = teacherSubjectClass;
	}
	public List<StudentEntity> getStudents() {
		return students;
	}
	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}
	public GradeEntity getGrade() {
		return grade;
	}
	public void setGrade(GradeEntity grade) {
		this.grade = grade;
	}
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ClassEntity(Integer classId, String name) {
		super();
		this.classId = classId;
		this.name = name;
	}
	public ClassEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
