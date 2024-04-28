package com.rodrigo.crudspring;

import com.rodrigo.crudspring.enums.Category;
import com.rodrigo.crudspring.model.Course;
import com.rodrigo.crudspring.model.Lesson;
import com.rodrigo.crudspring.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudSpringApplication implements CommandLineRunner {

	@Autowired
	private CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	public void run(String... args) throws Exception {
		Course c = new Course();
		c.setName("Angular e React");
		c.setCategory(Category.FRONT_END);

		Lesson l = new Lesson();
		l.setCourse(c);
		l.setName("Introdução");
		l.setUrl("watch?v=1");
		c.getLessons().add(l);

		courseRepository.save(c);
		}
}

