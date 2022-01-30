package me.daboy.fcapi.controllers;

import me.daboy.fcapi.entities.Student;
import me.daboy.fcapi.repositories.StudentRepository;
import me.daboy.fcapi.utils.WorkType;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class StudentsController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping(value = "/api/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<HashMap<String, Object>> getStudents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "") String search) {
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

    // &tags=1;4;&country=ES&city=sevilla&site=true&remote=true&move_yes=true&move_no=true
    @GetMapping(value = "/api/students/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<HashMap<String, Object>> searchStudents(@RequestParam(defaultValue = "") String tags, @RequestParam(defaultValue = "ES") String country, @RequestParam(defaultValue = "Madrid") String city,
                                                              @RequestParam(defaultValue = "false") boolean site, @RequestParam(defaultValue = "false") boolean move) {
        Pageable first = PageRequest.of(0, 25);
        HashMap<String, Object> jsonResponse = new HashMap<>();

        Page<Student> allStudents = studentRepository.customSearch(country, city, site ? "SITE" : "REMOTE", move, first);

        if(tags.equals("")) {
            jsonResponse.put("students", allStudents.getContent());
            jsonResponse.put("total", allStudents.getTotalElements());
            jsonResponse.put("current", allStudents.getNumber());
            jsonResponse.put("pages", allStudents.getTotalPages());
        } else {
            List<Student> finalStudents = new ArrayList<>();
            for (Student student : allStudents.getContent()) {
                String studentTags = student.getTags();
                String[] searchTags = tags.split(";");
                boolean hasOneOfThem = false;
                for(String searchTag : searchTags) {
                    if(studentTags.contains(searchTag + ";"))
                        hasOneOfThem = true;
                }
                if(hasOneOfThem)
                    finalStudents.add(student);
            }
            jsonResponse.put("students", finalStudents);
            jsonResponse.put("total", finalStudents.size());
            jsonResponse.put("current", 0);
            jsonResponse.put("pages", 1);
        }

        return ResponseEntity.ok(jsonResponse);
    }
}
