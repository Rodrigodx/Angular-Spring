package com.rodrigo.crudspring.service;

import com.rodrigo.crudspring.model.Course;
import com.rodrigo.crudspring.repositories.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Validated
@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll(){
        return courseRepository.findAll();
    }

    public Optional<Course> findById (@NotNull @Positive Integer id){
        return courseRepository.findById(id);
    }

    public Course save( @Valid Course course){
        return courseRepository.save(course);
    }

    public Optional<Course> updated ( @NotNull @Positive Integer id, @Valid Course course){
        return  courseRepository.findById(id).
                map(recordFound -> {
                    recordFound.setName(course.getName());
                    recordFound.setCategory(course.getCategory());
                    return courseRepository.save(recordFound);
                });
    }

    public boolean delete (@PathVariable @NotNull @Positive Integer id){
        return courseRepository.findById(id).
                map(recordFound -> {
                    courseRepository.deleteById(id);
                    return true;
                })
                .orElse(false);
    }
}
