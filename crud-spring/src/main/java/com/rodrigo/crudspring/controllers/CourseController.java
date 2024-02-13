package com.rodrigo.crudspring.controllers;

import com.rodrigo.crudspring.model.Course;
import com.rodrigo.crudspring.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping (value = "/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseRepository repository;

    @GetMapping
    public ResponseEntity<List<Course>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Course> findById (@PathVariable Integer id){
        return repository.findById(id)
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course save(@RequestBody Course course){
        return repository.save(course);
    }
}
