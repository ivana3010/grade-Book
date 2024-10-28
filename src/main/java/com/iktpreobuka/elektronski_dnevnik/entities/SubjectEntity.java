package com.iktpreobuka.elektronski_dnevnik.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.elektronski_dnevnik.repositories.TeacherRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler"})
public class SubjectEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer subjectId;
	protected String name;
	protected Integer fund;
	
	@JsonIgnore
	@OneToMany(mappedBy = "subject")
    private List<TeacherSubjectClassEntity> teacherSubjectClass= new ArrayList<>();
	
	public SubjectEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public List<TeacherSubjectClassEntity> getTeacherSubjectClass() {
		return teacherSubjectClass;
	}

	public void setTeacherSubjectClass(List<TeacherSubjectClassEntity> teacherSubjectClass) {
		this.teacherSubjectClass = teacherSubjectClass;
	}

	public SubjectEntity(Integer subjectId, String name,Integer fund) {
		super();
		this.subjectId = subjectId;
		this.name = name;
		this.fund=fund;
	}
	public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getFund() {
		return fund;
	}

	public void setFund(Integer fund) {
		this.fund = fund;
	}
	
	
}
