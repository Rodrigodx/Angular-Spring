package com.rodrigo.crudspring.service;

import com.rodrigo.crudspring.dto.CourseDTO;
import com.rodrigo.crudspring.dto.CoursePageDTO;
import com.rodrigo.crudspring.exception.RecordNotFoundException;
import com.rodrigo.crudspring.mapper.CourseMapper;


import com.rodrigo.crudspring.model.Course;
import com.rodrigo.crudspring.repositories.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public CoursePageDTO findAll(@PositiveOrZero int pageNumber, @Positive @Max(100) int pageSize) {
        Page<Course> page = courseRepository.findAll(PageRequest.of(0, 10));
        List<CourseDTO> courses = page.get().map(courseMapper::toDTO).toList();
        return new CoursePageDTO(courses, page.getTotalElements(), page.getTotalPages());
    }

    /*public List<CourseDTO> findAll(){
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toDTO)
                    .toList();
    }*/

    public CourseDTO findById (@NotNull @Positive Integer id){
        return courseRepository.findById(id)
                .map(courseMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO save( @Valid @NotNull CourseDTO course){
        return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
    }

    public CourseDTO updated ( @NotNull @Positive Integer id, @Valid @NotNull CourseDTO course){
        return  courseRepository.findById(id).
                map(recordFound -> {
                    Course course1 = courseMapper.toEntity(course);
                    recordFound.setName(course.name());
                    recordFound.setCategory(courseMapper.convertCategoryValue(course.category()));
                    //recordFound.setLessons(course1.getLessons());
                    recordFound.getLessons().clear();
                    course1.getLessons().forEach(recordFound.getLessons()::add);
                    return courseMapper.toDTO(courseRepository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete (@NotNull @Positive Integer id){

        courseRepository.delete(courseRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));

    }
}
