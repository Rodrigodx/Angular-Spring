import { Lesson } from './../../model/lesson';
import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { FormGroup, NonNullableFormBuilder, UntypedFormArray, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

import { CoursesService } from '../../services/courses.service';
import { ActivatedRoute } from '@angular/router';
import { Course } from '../../model/course';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrl: './course-form.component.scss'
})
export class CourseFormComponent{

  form!: FormGroup;

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private service: CoursesService,
    private snackBar: MatSnackBar,
    private location: Location,
    private route: ActivatedRoute,
    ){
      const course: Course = this.route.snapshot.data['course'];

      this.form = this.formBuilder.group({
        _id: [course._id],
        name: [course.name, [
          Validators.required,
          Validators.minLength(10),
          Validators.maxLength(100)
        ]],
        category: [course.category, [
          Validators.required
        ]],
        lessons: this.formBuilder.array(this.retrieveLessons(course))
       });
  }

  private retrieveLessons(course: Course){
    const lessons = [];
    if (course?.lessons){
      course.lessons.forEach(lesson => lessons.push(this.createLesson(lesson)));
    } else {
      lessons.push(this.createLesson());
    }
    return lessons;
  }

  private createLesson(lesson: Lesson = {id: '', name:'', url: ''}){

    return this.formBuilder.group({
      id: [lesson.id],
      name: [lesson.name],
      url: [lesson.url]

    })
  }

  getLessonsFormArray(){
    return (<UntypedFormArray>this.form.get('lessons')).controls;
  }

  AddNewLesson(){
    const lessons = this.form.get('lessons') as UntypedFormArray;
    lessons.push(this.createLesson());
  }

  removeLesson(index: number){
    const lessons = this.form.get('lessons') as UntypedFormArray;
    lessons.removeAt(index);
  }

  onSubmit(){
    this.service.save(this.form.value).subscribe(result => this.onSuccess(), error => this.onError());
  }

  onCancel(){
    this.location.back();
  }


  onSuccess(){
    this.snackBar.open('Curso salvo com sucesso.', '', {
      duration: 3000
    })
    this.onCancel();
  }

  onError(){
    this.snackBar.open('Erro ao salvar curso.', '', {
      duration: 3000
    })
  }

  getErrorMessage(fieldName: string){

    const field = this.form.get(fieldName);

    if (field?.hasError('required')){
      return 'Campo obrigatório';
    }

    if (field?.hasError('minlength')){
      const requiredLength: number = field.errors ? field.errors['minlength']['requiredLength']: 10;
      return `Tamanho mínimo precisa ser de ${requiredLength}`;
    }

    if (field?.hasError('maxlength')){
      const requiredLength: number = field.errors ? field.errors['maxlength']['requiredLength']: 100;
      return `Tamanho máxido excedido de ${requiredLength} caracteres`;
    }

    return 'Campo inválido';

  }

}
