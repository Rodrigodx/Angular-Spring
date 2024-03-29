package com.rodrigo.crudspring.service;

import com.rodrigo.crudspring.dto.CourseDTO;
import com.rodrigo.crudspring.exception.RecordNotFoundException;
import com.rodrigo.crudspring.mapper.CourseMapper;












































































































































































































































































































































import com.rodrigo.crudspring.repositories.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

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

    public List<CourseDTO> findAll(){
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toDTO)
                    .toList();
    }

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
                    recordFound.setName(course.name());
                    recordFound.setCategory(courseMapper.convertCategoryValue(course.category()));
                    return courseMapper.toDTO(courseRepository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete (@NotNull @Positive Integer id){

        courseRepository.delete(courseRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));

    }
}
