package com.sammidev.config;

import com.sammidev.entity.Student;
import com.sammidev.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            var sam = new Student(1L,"sammidev 1", "sammidev4@mail.com", LocalDate.of(2001, Month.OCTOBER, 20));
            var dev = new Student(2L,"sammidev 2", "sammidev4@mail.com", LocalDate.of(2001, Month.OCTOBER, 20));
            repository.saveAll(List.of(sam,dev));
        };
    }
}
