package com.iktpreobuka.elektronski_dnevnik.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler"})
public class TeacherSubjectClassEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	@ManyToOne
    @JoinColumn(name = "teacher_Id")
    private TeacherEntity teacher;
    
    @ManyToOne
    @JoinColumn(name = "subject_Id")
    private SubjectEntity subject;
    
    @ManyToOne
    @JoinColumn(name = "klasa_Id")
    private ClassEntity klasa;
   
    @JsonIgnore
    @OneToMany(mappedBy = "teacherSubjectClassEntity")
    private List<MarkEntity> marks;


	public List<MarkEntity> getMarks() {
		return marks;
	}

	public void setMarks(List<MarkEntity> marks) {
		this.marks = marks;
	}

	public ClassEntity getKlasa() {
		return klasa;
	}

	public void setKlasa(ClassEntity klasa) {
		this.klasa = klasa;
	}

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TeacherEntity getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherEntity teacher) {
		this.teacher = teacher;
	}

	public SubjectEntity getSubject() {
		return subject;
	}

	public void setSubject(SubjectEntity subject) {
		this.subject = subject;
	}

	public ClassEntity getClassEntity() {
		return klasa;
	}

	public void setClassEntity(ClassEntity classEntity) {
		this.klasa = classEntity;
	}

	public TeacherSubjectClassEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherSubjectClassEntity(Integer id, TeacherEntity teacher, SubjectEntity subject, ClassEntity klasa,
			List<MarkEntity> marks) {
		super();
		this.id = id;
		this.teacher = teacher;
		this.subject = subject;
		this.klasa = klasa;
		
		this.marks = marks;
	}
	
    
    
}
