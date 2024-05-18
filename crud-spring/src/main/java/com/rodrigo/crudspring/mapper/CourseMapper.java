package com.rodrigo.crudspring.mapper;

import com.rodrigo.crudspring.dto.CourseDTO;
import com.rodrigo.crudspring.dto.LessonDTO;
import com.rodrigo.crudspring.enums.Category;
import com.rodrigo.crudspring.model.Course;
import com.rodrigo.crudspring.model.Lesson;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course){
        if (course == null){
            return null;
        }
        List<LessonDTO> lessonDTOS = course.getLessons().stream()
                .map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(), lesson.getUrl()))
                .toList();
        return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue(),
                lessonDTOS);
    }

    public Course toEntity(CourseDTO courseDTO){
        if (courseDTO == null){
            return null;
        }

        Course course = new Course();
        if(courseDTO.id() != null){
            course.setId(courseDTO.id());
        }
        course.setName(courseDTO.name());
        course.setCategory(convertCategoryValue(courseDTO.category()));

        List<Lesson> lessons = courseDTO.lessons().stream().map(lessonDTO -> {
            var lesson = new Lesson();
            //lesson.setId(lessonDTO.id());
            lesson.setName(lessonDTO.name());
            lesson.setUrl(lessonDTO.url());
            lesson.setCourse(course);
            return lesson;
        }).toList();
        course.setLessons(lessons);

        return course;
    }

    public Category convertCategoryValue(String value){
        if(value == null){
            return null;
        }
        return switch (value) {
            case "Front-end" -> Category.FRONT_END;
            case "Back-end" -> Category.BACK_END;
            default -> throw new IllegalArgumentException("Categoria invalida" + value);
        };
    }
}
