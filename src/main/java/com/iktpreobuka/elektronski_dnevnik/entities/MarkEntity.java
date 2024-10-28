package com.iktpreobuka.elektronski_dnevnik.entities;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.elektronski_dnevnik.security.Views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarkEntity {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	 	@JsonView(Views.Public.class)
	    private Integer markId;
	 	
	 	
	 	@Min(value=1, message="Mark must be between 1 and 5!")
	    @Max(value=5, message="Mark must be between 1 and 5!")
	 	@JsonView(Views.Public.class)
	    private int value;
	    
	    @Column(name = "mark_type", nullable = false)
	    @Enumerated(EnumType.STRING)
	    @JsonView(Views.Public.class)
	    private MarkType markType;
	   
	    
	    
	    public enum MarkType {
	        ORAL_EXAM,
	        WRITTEN_EXAM,
	        TEST
	    }
	    @ManyToOne
	    @JoinColumn(name = "teacher_subject_class_id")
	    @JsonView(Views.Public.class)
	    private TeacherSubjectClassEntity teacherSubjectClassEntity;
	    
	    @ManyToOne
	    @JoinColumn(name = "student_id")
	    @JsonView(Views.Public.class)
	    private StudentEntity student;
	    
	    
	    

		public StudentEntity getStudent() {
			return student;
		}

		public void setStudent(StudentEntity student) {
			this.student = student;
		}

		

		public Integer getMarkId() {
			return markId;
		}

		public void setMarkId(Integer markId) {
			this.markId = markId;
		}

		

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public MarkType getMarkType() {
			return markType;
		}

		public void setMarkType(MarkType markType) {
			this.markType = markType;
		}

		
		public TeacherSubjectClassEntity getTeacherSubjectClassEntity() {
			return teacherSubjectClassEntity;
		}

		public void setTeacherSubjectClassEntity(TeacherSubjectClassEntity teacherSubjectClassEntity) {
			this.teacherSubjectClassEntity = teacherSubjectClassEntity;
		}

		public MarkEntity() {
			super();
			// TODO Auto-generated constructor stub
		}

		public MarkEntity(Integer markId, @Min(1) @Max(5) int value, MarkType markType,
				TeacherSubjectClassEntity teacherSubjectClassEntity) {
			super();
			this.markId = markId;
			this.value = value;
			this.markType = markType;
			this.teacherSubjectClassEntity = teacherSubjectClassEntity;
			
			
		}

		
	    
	    
}
