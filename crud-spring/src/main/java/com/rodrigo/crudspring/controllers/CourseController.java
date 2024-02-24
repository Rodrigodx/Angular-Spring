package com.rodrigo.crudspring.controllers;

import com.rodrigo.crudspring.model.Course;
import com.rodrigo.crudspring.service.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
@RequestMapping (value = "/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController (CourseService service){
        courseService = service;
    }

    @GetMapping
    public ResponseEntity<List<Course>> findAll(){
        return ResponseEntity.ok(courseService.findAll());
    }

    @GetMapping(value = "/{id}")
    public Course findById (@PathVariable @NotNull @Positive Integer id){
        return courseService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course save(@RequestBody @Valid Course course){
        return courseService.save(course);
    }

    @PutMapping(value = "/{id}")
    public Course updated (@PathVariable @NotNull @Positive Integer id, @RequestBody @Valid Course course){
        return courseService.updated(id, course);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete (@PathVariable @NotNull @Positive Integer id){
        courseService.delete(id);
    }
}
