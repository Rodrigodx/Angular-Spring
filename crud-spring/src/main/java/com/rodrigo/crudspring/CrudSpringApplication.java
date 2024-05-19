package com.rodrigo.crudspring;

import com.rodrigo.crudspring.enums.Category;
import com.rodrigo.crudspring.model.Course;
import com.rodrigo.crudspring.model.Lesson;
import com.rodrigo.crudspring.repositories.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudSpringApplication implements CommandLineRunner {

	private final CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	public CrudSpringApplication(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	public void run(String... args) {
		Course c = new Course();
		c.setName("Aula de Angular");
		c.setCategory(Category.FRONT_END);

		Lesson l = new Lesson();
		l.setCourse(c);
		l.setName("Introdução");
		l.setUrl("1234567890");
		c.getLessons().add(l);

		courseRepository.save(c);
		}
}

