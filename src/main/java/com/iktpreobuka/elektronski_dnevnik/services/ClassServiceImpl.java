package com.iktpreobuka.elektronski_dnevnik.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronski_dnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronski_dnevnik.entities.ClassEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.GradeEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.TeacherSubjectClassEntity;
import com.iktpreobuka.elektronski_dnevnik.repositories.ClassRepository;
import com.iktpreobuka.elektronski_dnevnik.repositories.GradeRepository;
import com.iktpreobuka.elektronski_dnevnik.repositories.SubjectRepository;
import com.iktpreobuka.elektronski_dnevnik.repositories.TeacherRepository;
import com.iktpreobuka.elektronski_dnevnik.repositories.TeacherSubjectClassRepository;

@Service
public class ClassServiceImpl implements ClassService {
	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private GradeRepository gradeRepository;
	@Autowired
	private TeacherSubjectClassRepository teacherSubjectClassRepository;

	@Override
	public ResponseEntity<?> getAllClasses() {
		return new ResponseEntity<>(classRepository.findAll(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> addClass(ClassEntity klasa) {
		ClassEntity entity = new ClassEntity();
		entity.setClassId(klasa.getClassId());
		entity.setName(klasa.getName());
		entity.setGrade(klasa.getGrade());
		classRepository.save(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteClass(Integer classId) {
		Optional<ClassEntity> ce = classRepository.findById(classId);
		if (ce.isPresent()) {

			classRepository.delete(ce.get());
			return new ResponseEntity<>(ce, HttpStatus.OK);
		}else {
		return new ResponseEntity<>(new RESTError(1, "Class not found"), HttpStatus.NOT_FOUND);
	}}

	@Override
	public ResponseEntity<?> modifyClass(Integer classId, ClassEntity klasa) {
		Optional<ClassEntity> entity = classRepository.findById(classId);
		if (entity.isPresent()) {
			ClassEntity classEntity = entity.get();
			classEntity.setClassId(klasa.getClassId());
			classEntity.setName(klasa.getName());
			classEntity.setGrade(klasa.getGrade());
			classRepository.save(classEntity);
			return new ResponseEntity<>(classEntity, HttpStatus.OK);
		}
		return new ResponseEntity<>(new RESTError(1, "Class not found"), HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<?> findByClassId(Integer classId) {
		Optional<ClassEntity> klasa = classRepository.findById(classId);
		if (klasa.isPresent()) {
			return new ResponseEntity<>(klasa.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new RESTError(1, "Class not found"), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<?> addClassToGrade(Integer classId, Integer gradeId) {
		Optional<ClassEntity> klasa = classRepository.findById(classId);
		Optional<GradeEntity> grade = gradeRepository.findById(gradeId);

		if (klasa.isPresent() && grade.isPresent()) {
			ClassEntity classEntity = klasa.get();
	        GradeEntity gradeEntity = grade.get();
	        classEntity.setGrade(gradeEntity);
			classRepository.save(classEntity);
			return new ResponseEntity<>(classEntity, HttpStatus.OK);
		}
		return new ResponseEntity<>(new RESTError(1, "Class not found"), HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> getAllStudentsForClass(Integer classId) {
		Optional<ClassEntity> classEntity = classRepository.findById(classId);

		if (classEntity.isPresent()) {
			List<StudentEntity> students = classEntity.get().getStudents();
			return new ResponseEntity<>(students, HttpStatus.OK);
		} else {

			return new ResponseEntity<>(new RESTError(1, "Class not found"), HttpStatus.NOT_FOUND);
		}
	}
	public ResponseEntity<?> getAllTeachersForClass(Integer classId) {
        Optional<ClassEntity> classOpt = classRepository.findById(classId);

        if (!classOpt.isPresent()) {
            return new ResponseEntity<>(new RESTError(1, "Class not found"), HttpStatus.NOT_FOUND);
        }

        List<TeacherSubjectClassEntity> teacherSubjectClasses = teacherSubjectClassRepository.findByKlasa_ClassId(classId);

        if (teacherSubjectClasses.isEmpty()) {
            return new ResponseEntity<>(new RESTError(2, "No teachers found for the class"), HttpStatus.NOT_FOUND);
        }

        List<TeacherEntity> teachers = new ArrayList<>();
        for (TeacherSubjectClassEntity tsc : teacherSubjectClasses) {
            teachers.add(tsc.getTeacher());
        }

        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }
    
	public ResponseEntity<?> getAllSubjectsForClass(Integer classId) {
        Optional<ClassEntity> classOpt = classRepository.findById(classId);

        if (!classOpt.isPresent()) {
            return new ResponseEntity<>(new RESTError(1, "Class not found"), HttpStatus.NOT_FOUND);
        }

        List<TeacherSubjectClassEntity> teacherSubjectClasses = teacherSubjectClassRepository.findByKlasa_ClassId(classId);

        if (teacherSubjectClasses.isEmpty()) {
            return new ResponseEntity<>(new RESTError(2, "No subjects found for the class"), HttpStatus.NOT_FOUND);
        }

        List<SubjectEntity> subjects = new ArrayList<>();
        for (TeacherSubjectClassEntity tsc : teacherSubjectClasses) {
        	subjects.add(tsc.getSubject());
        }

        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }
	
	
	


    
}
