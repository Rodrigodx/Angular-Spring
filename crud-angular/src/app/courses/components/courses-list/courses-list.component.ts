import { Component, EventEmitter, Input, Output } from '@angular/core';

import { Course } from '../../model/course';


@Component({
  selector: 'app-courses-list',
  templateUrl: './courses-list.component.html',
  styleUrl: './courses-list.component.scss'
})
export class CoursesListComponent {


  @Input() courses: Course[] = [];
  readonly displayedColumns = ['name', 'category', 'actions'];
  @Output() add = new EventEmitter(false);
  @Output() edit = new EventEmitter(false);
  @Output() delete = new EventEmitter(false);

  onAdd(){
    this.add.emit(true);
  }

  onEdit(course: Course){
    this.edit.emit(course);
  }

  onDelete(course: Course){
    this.delete.emit(course);
  }

}
