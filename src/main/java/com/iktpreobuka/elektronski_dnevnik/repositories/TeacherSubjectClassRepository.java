package com.iktpreobuka.elektronski_dnevnik.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.elektronski_dnevnik.entities.TeacherSubjectClassEntity;

@Repository
public interface TeacherSubjectClassRepository extends CrudRepository<TeacherSubjectClassEntity, Integer>{
	@Query("SELECT tsc FROM TeacherSubjectClassEntity tsc WHERE tsc.teacher.id = :teacherId AND tsc.subject.id = :subjectId AND tsc.klasa.id = :classId")
    TeacherSubjectClassEntity findByTeacherIdAndSubjectIdAndClassId(@Param("teacherId") Integer teacherId,
            @Param("subjectId") Integer subjectId, @Param("classId") Integer classId);
	List<TeacherSubjectClassEntity> findByTeacher_TeacherId(Integer teacherId);
	List<TeacherSubjectClassEntity> findByKlasa_ClassId(Integer classId);
	List<TeacherSubjectClassEntity> findBySubject_SubjectId(Integer subjectId);
	TeacherSubjectClassEntity findByTeacher_TeacherIdAndSubject_SubjectIdAndKlasa_ClassId(Integer teacherId, Integer subjectId, Integer classId);
	List<TeacherSubjectClassEntity> findByTeacher_TeacherIdAndSubject_SubjectId(Integer teacherId, Integer subjectId);
	
	void deleteBySubject_SubjectId(Integer subjectId);
	
}
