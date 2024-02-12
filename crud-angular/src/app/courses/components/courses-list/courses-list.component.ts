import { Component, EventEmitter, Input, Output, input} from '@angular/core';
import { Course } from '../../model/course';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-courses-list',
  templateUrl: './courses-list.component.html',
  styleUrl: './courses-list.component.scss'
})
export class CoursesListComponent {


  @Input() courses: Course[] = [];
  readonly displayedColumns = ['name', 'category', 'actions'];
  @Output() add = new EventEmitter(false);

  onAdd(){
    this.add.emit(true);
  }

}
