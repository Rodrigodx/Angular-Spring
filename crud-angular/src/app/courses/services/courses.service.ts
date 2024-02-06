import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Course } from '../model/course';
import { delay, first, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

    private readonly API = '/assets/courses.json';

  constructor(private httpCLient: HttpClient) { }

  list() {
    return this.httpCLient.get<Course[]>(this.API)
    .pipe(
      first(),
      delay(3000),
      tap(courses => console.log(courses))
    );
  }
}