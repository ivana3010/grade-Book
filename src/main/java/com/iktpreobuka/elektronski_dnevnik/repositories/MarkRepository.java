package com.iktpreobuka.elektronski_dnevnik.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.elektronski_dnevnik.entities.MarkEntity;

@Repository
public interface MarkRepository extends CrudRepository<MarkEntity, Integer> {
	@Query("SELECT m FROM MarkEntity m WHERE m.student.studentId = :studentId")
	List<MarkEntity> findByStudentId(@Param("studentId") Integer studentId);

	@Query("SELECT m FROM MarkEntity m WHERE m.student.studentId = :studentId AND m.teacherSubjectClassEntity.subject.subjectId = :subjectId")
	List<MarkEntity> findByStudentIdAndSubjectId(@Param("studentId") Integer studentId,
			@Param("subjectId") Integer subjectId);

	List<MarkEntity> findByTeacherSubjectClassEntity_Teacher_TeacherIdAndTeacherSubjectClassEntity_Subject_SubjectId(
			Integer teacherId, Integer subjectId);

	List<MarkEntity> findByMarkType(MarkEntity.MarkType markType);

	List<MarkEntity> findByTeacherSubjectClassEntity_Teacher_TeacherId(Integer teacherId);

}
