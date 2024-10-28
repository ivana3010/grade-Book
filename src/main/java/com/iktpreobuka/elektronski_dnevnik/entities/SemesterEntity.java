package com.iktpreobuka.elektronski_dnevnik.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler"})
public class SemesterEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer semesterId;

    @Column(nullable = false)
    private Integer value;

	public Integer getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(Integer semesterId) {
		this.semesterId = semesterId;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public SemesterEntity(Integer semesterId, Integer value) {
		super();
		this.semesterId = semesterId;
		this.value = value;
	}

	public SemesterEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    

}
