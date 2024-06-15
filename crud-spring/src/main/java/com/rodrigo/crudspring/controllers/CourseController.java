package com.rodrigo.crudspring.controllers;

import com.rodrigo.crudspring.dto.CourseDTO;
import com.rodrigo.crudspring.dto.CoursePageDTO;
import com.rodrigo.crudspring.service.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.query.Page;
import org.springframework.http.HttpStatus;
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
    public CoursePageDTO findAll(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                 @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize){
        return courseService.findAll(page, pageSize);
    }

    @GetMapping(value = "/{id}")
    public CourseDTO findById (@PathVariable @NotNull @Positive Integer id){
        return courseService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CourseDTO save(@RequestBody @Valid @NotNull CourseDTO course){
        return courseService.save(course);
    }

    @PutMapping(value = "/{id}")
    public CourseDTO updated (@PathVariable @NotNull @Positive Integer id, @RequestBody @Valid @NotNull CourseDTO course){
        return courseService.updated(id, course);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete (@PathVariable @NotNull @Positive Integer id){
        courseService.delete(id);
    }
}
