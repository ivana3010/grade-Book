package com.iktpreobuka.elektronski_dnevnik.entities;

import java.util.List;

public class SubjectRequest {
	private Integer subjectId;
    private String name;
    private Integer fund;
    private List<Integer> teacherIds;
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
	public List<Integer> getTeacherIds() {
		return teacherIds;
	}
	public void setTeacherIds(List<Integer> teacherIds) {
		this.teacherIds = teacherIds;
	}
	public SubjectRequest(Integer subjectId, String name, Integer fund, List<Integer> teacherIds) {
		super();
		this.subjectId = subjectId;
		this.name = name;
		this.fund = fund;
		this.teacherIds = teacherIds;
	}
	public SubjectRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
