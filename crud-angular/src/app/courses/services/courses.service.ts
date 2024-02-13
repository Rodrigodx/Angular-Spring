import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Course } from '../model/course';
import {first, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

    private readonly API = '/api/courses';

  constructor(private httpCLient: HttpClient) { }

  list() {
    return this.httpCLient.get<Course[]>(this.API)
    .pipe(
      first(),
      tap(courses => console.log(courses))
    );
  }

  findById(id: string){
    return this.httpCLient.get<Course>(`${this.API}/${id}`);
  }

  save(record: Partial<Course>){
    return this.httpCLient.post<Course>(this.API, record).pipe(first());
  }

}
