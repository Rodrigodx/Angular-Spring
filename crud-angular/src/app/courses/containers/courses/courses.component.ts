import { Component } from '@angular/core';
import { Observable, catchError, of } from 'rxjs';

import { Course } from '../../model/course';
import { CoursesService } from '../../services/courses.service';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrl: './courses.component.scss'
})
export class CoursesComponent {

  courses$: Observable<Course[]>;

  constructor(
    private coursesService: CoursesService,
    public dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute
    ) {
    this.courses$ = this.coursesService.list()
    .pipe(
      catchError(error => {
        this.onError('Error ao carregar cursos.')
        return of ([])
      })
      );
  }

  onError(errorMsg: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: errorMsg
    });
  }

  onAdd(){
    this.router.navigate(['new'], {relativeTo: this.route});
  }


}