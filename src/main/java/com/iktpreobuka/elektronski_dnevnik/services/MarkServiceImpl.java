package com.iktpreobuka.elektronski_dnevnik.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.iktpreobuka.elektronski_dnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronski_dnevnik.entities.MarkEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.SemesterEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.StudentParentEntity;
import com.iktpreobuka.elektronski_dnevnik.entities.TeacherSubjectClassEntity;
import com.iktpreobuka.elektronski_dnevnik.repositories.MarkRepository;
import com.iktpreobuka.elektronski_dnevnik.repositories.SemesterRepository;
import com.iktpreobuka.elektronski_dnevnik.repositories.StudentParentRepository;
import com.iktpreobuka.elektronski_dnevnik.repositories.StudentRepository;
import com.iktpreobuka.elektronski_dnevnik.repositories.TeacherSubjectClassRepository;

@Service
public class MarkServiceImpl implements MarkService{
	@Autowired
    private MarkRepository markRepository;
	@Autowired
    private TeacherSubjectClassRepository teacherSubjectClassRepository;
	
	@Autowired
    private StudentRepository studentRepository;
	@Autowired
    private StudentParentRepository studentParentRepository;
	@Autowired
    private EmailService emailService;

	@Override
	public ResponseEntity<?> addMark(Integer teacherId, Integer subjectId, Integer classId, Integer studentId, Integer mark, String markType){
        TeacherSubjectClassEntity tsc = teacherSubjectClassRepository.findByTeacher_TeacherIdAndSubject_SubjectIdAndKlasa_ClassId(teacherId, subjectId, classId);
        if (tsc == null) {
            return new ResponseEntity<>(new RESTError(1, "Teacher does not teach this student in subject"),
                    HttpStatus.BAD_REQUEST);
        }

        Optional<StudentEntity> student = studentRepository.findById(studentId);
        if (!student.isPresent() ) {
            return new ResponseEntity<>(new RESTError(2, "Student not found"), HttpStatus.NOT_FOUND);
        }
        
        StudentEntity studentEntity = student.get();

        if (studentEntity.getOdeljenje() == null) {
            return new ResponseEntity<>(new RESTError(3, "Student is not assigned to any class"),
                    HttpStatus.BAD_REQUEST);
        }
        if (!studentEntity.getOdeljenje().getClassId().equals(classId)) {
            return new ResponseEntity<>(new RESTError(4, "Student is not in the specified class"),
                    HttpStatus.BAD_REQUEST);
        }
        MarkEntity newMark = new MarkEntity();
        newMark.setTeacherSubjectClassEntity(tsc);
        newMark.setStudent(student.get());
        newMark.setValue(mark);
        newMark.setMarkType(MarkEntity.MarkType.valueOf(markType));
        markRepository.save(newMark);
        
        List<StudentParentEntity> studentParents = studentParentRepository.findByStudentStudentId(studentId);//slanje mejla
        for (StudentParentEntity studentParent : studentParents) {
            String parentEmail = studentParent.getParent().getEmail();
            if (parentEmail != null && !parentEmail.isEmpty()) {
                String subject = "Nova ocena";
                String text = "<div style='background-color: lightyellow; border:2px solid pink; padding:10px;'>"
                                 + "<h2 style='color: brown;>Nova ocena</h2>"
                                 + "<p style='color: brown;>Vaš učenik <b>" + studentEntity.getName() + " " + studentEntity.getSurname() + "</b> je dobio ocenu <b>" + mark + "</b> iz predmeta <b>" + tsc.getSubject().getName() + "</b> kod nastavnika <b>" + tsc.getTeacher().getName() + " " + tsc.getTeacher().getSurname() + "</b>.</p>"
                                 + "</div>";
                    
                                 emailService.sendTemplateMessage(parentEmail, subject, text);
            }
        }

        return new ResponseEntity<>(newMark, HttpStatus.CREATED);
    }

    
    
    @Override
    public ResponseEntity<?> getGradesByStudentId(Integer studentId) {
        List<MarkEntity> marks = markRepository.findByStudentId(studentId);
        if (marks.isEmpty()) {
            return new ResponseEntity<>(new RESTError(2, "No grades found for the student"), HttpStatus.NOT_FOUND);
        }

        Map<Integer, List<MarkEntity>> gradesBySubject = marks.stream().collect(Collectors.groupingBy(mark -> mark.getTeacherSubjectClassEntity().getSubject().getSubjectId()));//marks lista se pretvara u tok podataka(stream), zatim kolektor sakuplja sve elemente u mapu, mark-> uzima svaku ocenu i vraca id predmeta

        Map<String, Double> finalGrades = new HashMap<>();
        for (MarkEntity mark : marks) {
            String subjectName = mark.getTeacherSubjectClassEntity().getSubject().getName();
            if (!finalGrades.containsKey(subjectName)) {//da li mapa sazdrzi kljuc ime predmeta
                List<MarkEntity> subjectMarks = markRepository.findByStudentIdAndSubjectId(studentId, mark.getTeacherSubjectClassEntity().getSubject().getSubjectId());
                double sum = 0;
                for (MarkEntity subjectMark : subjectMarks) {//sabiramo ocene
                    sum += subjectMark.getValue();
                }
                double finalGrade;
                if (subjectMarks.size() == 0) {
                    finalGrade = 0;
                } else {
                    finalGrade = sum / subjectMarks.size();
                }
                finalGrades.put(subjectName, finalGrade);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("grades", marks);//dodamo sve ocene
        response.put("finalGrades", finalGrades);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<?> deleteMark(Integer markId) {
        Optional<MarkEntity> mark = markRepository.findById(markId);
        if (!mark.isPresent()) {
            return new ResponseEntity<>(new RESTError(1, "Mark not found"), HttpStatus.NOT_FOUND);
        }
        markRepository.deleteById(markId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<?> updateMark(Integer markId, Integer markValue, String markType) {
        Optional<MarkEntity> mark = markRepository.findById(markId);
        if (!mark.isPresent()) {
            return new ResponseEntity<>(new RESTError(1, "Mark not found"), HttpStatus.NOT_FOUND);
        }
        MarkEntity markEntity = mark.get();
        markEntity.setValue(markValue);
        markEntity.setMarkType(MarkEntity.MarkType.valueOf(markType));
        markRepository.save(markEntity);
        return new ResponseEntity<>(markEntity, HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<?> getMarksByTeacherAndSubject(Integer teacherId, Integer subjectId) {
        List<TeacherSubjectClassEntity> list = teacherSubjectClassRepository.findByTeacher_TeacherIdAndSubject_SubjectId(teacherId, subjectId);
        if (list.isEmpty()) {
            return new ResponseEntity<>(new RESTError(1, "Teacher doesn't teach subject"), HttpStatus.BAD_REQUEST);
        }

        List<MarkEntity> marks = markRepository.findByTeacherSubjectClassEntity_Teacher_TeacherIdAndTeacherSubjectClassEntity_Subject_SubjectId(teacherId, subjectId);//da nadjemo ocene za predmet i nastavnika
        if (marks.isEmpty()) {
            return new ResponseEntity<>(new RESTError(2, "No marks found for the given teacher and subject"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(marks, HttpStatus.OK);
    }
    
    
    
    
    public ResponseEntity<?> getTestMarks() {
        List<MarkEntity> testMarks = markRepository.findByMarkType(MarkEntity.MarkType.TEST);
        if (testMarks.isEmpty()) {
            return new ResponseEntity<>(new RESTError(1, "No test marks found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(testMarks, HttpStatus.OK);
    }

    
    public ResponseEntity<?> getWrittenExamMarks() {
        List<MarkEntity> writtenExamMarks = markRepository.findByMarkType(MarkEntity.MarkType.WRITTEN_EXAM);
        if (writtenExamMarks.isEmpty()) {
            return new ResponseEntity<>(new RESTError(1, "No written exam marks found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(writtenExamMarks, HttpStatus.OK);
    }



	@Override
	public ResponseEntity<?> getOralExamMarks() {
		List<MarkEntity> oralExamMarks = markRepository.findByMarkType(MarkEntity.MarkType.ORAL_EXAM);
        if (oralExamMarks.isEmpty()) {
            return new ResponseEntity<>(new RESTError(1, "No oral exam marks found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(oralExamMarks, HttpStatus.OK);
	}



	@Override
	public ResponseEntity<?> getAllMark() {
		return new ResponseEntity<>(markRepository.findAll(), HttpStatus.OK);
	}
	public ResponseEntity<?> getAllMarksForParent(Integer parentId){
	List<MarkEntity> marks = markRepository.findByTeacherSubjectClassEntity_Teacher_TeacherId(parentId);
    if (marks.isEmpty()) {
        return new ResponseEntity<>(new RESTError(2, "No marks found for parent's children"), HttpStatus.NOT_FOUND);
    }return new ResponseEntity<>(marks, HttpStatus.OK);}
}

