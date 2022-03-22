package com.ufcg.psoft.tccmatch.services.fieldsOfStudy;

import com.ufcg.psoft.tccmatch.models.fieldsOfStudy.FieldOfStudy;
import com.ufcg.psoft.tccmatch.models.users.Professor;
import com.ufcg.psoft.tccmatch.models.users.Student;
import com.ufcg.psoft.tccmatch.models.users.User;
import com.ufcg.psoft.tccmatch.repositories.fieldsOfStudy.FieldsOfStudyRepository;
import com.ufcg.psoft.tccmatch.services.users.professors.ProfessorService;
import com.ufcg.psoft.tccmatch.services.users.students.StudentService;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FieldsOfStudyService{
    
    @Autowired
    private FieldsOfStudyRepository fieldsOfStudyRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProfessorService professorService;
    
    public FieldOfStudy createFieldsOfStudy(String name){
        FieldOfStudy fieldsOfStudy = new FieldOfStudy(name);
        fieldsOfStudyRepository.save(fieldsOfStudy);
        return fieldsOfStudy;
    } 
    public Optional<FieldOfStudy> findByName(String name) {
        return fieldsOfStudyRepository.findByName(name);
    }
    public Optional<FieldOfStudy> findById(Long idField) {
        return fieldsOfStudyRepository.findById(idField);
    }
    public void selectFieldOfStudy(User user, FieldOfStudy fieldOfStudy) {
        if(user.getType() == User.Type.PROFESSOR)
            professorService.selectFieldOfStudy((Professor)user, fieldOfStudy);
        else if(user.getType() == User.Type.STUDENT)
            studentService.selectFieldOfStudy((Student)user, fieldOfStudy);
    }
}

