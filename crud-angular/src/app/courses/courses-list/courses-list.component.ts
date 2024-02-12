import { Component, Input} from '@angular/core';
import { Course } from '../model/course';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-courses-list',
  templateUrl: './courses-list.component.html',
  styleUrl: './courses-list.component.scss'
})
export class CoursesListComponent {

  constructor(
    private router: Router,
    private route: ActivatedRoute
  ){}

  @Input() courses: Course[] = [];
  readonly displayedColumns = ['name', 'category', 'actions'];


  onAdd(){
    this.router.navigate(['new'], {relativeTo: this.route});
  }

}
