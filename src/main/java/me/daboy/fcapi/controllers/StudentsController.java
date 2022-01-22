package me.daboy.fcapi.controllers;

import me.daboy.fcapi.entities.Student;
import me.daboy.fcapi.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@CrossOrigin(origins = "*")
public class StudentsController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping(value = "/api/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<HashMap<String, Object>> findStudents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "") String search) {
        Pageable first = PageRequest.of(page, size);
        HashMap<String, Object> jsonResponse = new HashMap<>();

        if(search.equals("")) { // Get all students if there is no search filter
            Page<Student> allStudents = studentRepository.findAll(first);
            jsonResponse.put("students", allStudents.getContent());
            jsonResponse.put("total", allStudents.getTotalElements());
            jsonResponse.put("current", allStudents.getNumber());
            jsonResponse.put("pages", allStudents.getTotalPages());
        } else { // Students with search filter
            Page<Student> studentsByName = studentRepository.findByCityContainingOrFullNameContainingOrCountryContainingOrEmailContainingOrPhoneContaining(search, search, search, search, search, first);
            jsonResponse.put("students", studentsByName.getContent());
            jsonResponse.put("total", studentsByName.getTotalElements());
            jsonResponse.put("current", studentsByName.getNumber());
            jsonResponse.put("pages", studentsByName.getTotalPages());
        }

        return ResponseEntity.ok(jsonResponse);
    }
}
