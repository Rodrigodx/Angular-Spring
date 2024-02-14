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
                .map(recordFound -> ResponseEntity.ok().body(recordFound))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course save(@RequestBody Course course){
        return repository.save(course);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Course> updated (@PathVariable Integer id, @RequestBody Course course){
        return  repository.findById(id).
                map(recordFound -> {
                    recordFound.setName(course.getName());
                    recordFound.setCategory(course.getCategory());
                    Course updated = repository.save(recordFound);
                    return ResponseEntity.ok().body(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete (@PathVariable Integer id){
        return repository.findById(id).
                map(recordFound -> {
                    repository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
