package com.sammidev.service;

import com.sammidev.entity.Student;
import com.sammidev.exception.EmailNotValidException;
import com.sammidev.exception.EmailTakenException;
import com.sammidev.exception.StudentNotFoundException;
import com.sammidev.helper.EmailValidator;
import com.sammidev.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final EmailValidator emailValidator;

    @Autowired
    public StudentService(StudentRepository studentRepository, EmailValidator emailValidator) {
        this.studentRepository = studentRepository;
        this.emailValidator = emailValidator;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        var studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new EmailTakenException("EMAIL TAKEN");
        }

        if (!emailValidator.test(student.getEmail())) {
            throw new EmailNotValidException("EMAIL NOT VALID");
        }

        studentRepository.save(student);
    }

    public void deleteStudentById(Long studentID) {
        var exists = studentRepository.existsById(studentID);
        if (!exists) throw new StudentNotFoundException("STUDENT WITH ID " + studentID + " NOT FOUND");
        studentRepository.deleteById(studentID);
    }

    @Transactional
    public void updateStudent(Long studentID,
                              String name,
                              String email) {

        var student = studentRepository.findById(studentID)
                .orElseThrow(() -> new StudentNotFoundException("STUDENT WITH ID " + studentID + " NOT FOUND"));

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null &&
                email.length() > 0 &&
                !Objects.equals(student.getEmail(), email)) {

            var studentByEmail = studentRepository.findStudentByEmail(email);

            if (studentByEmail.isPresent()) {
                throw new EmailTakenException("EMAIL TAKEN");
            }else {
                student.setEmail(email);
            }
        }
    }
}