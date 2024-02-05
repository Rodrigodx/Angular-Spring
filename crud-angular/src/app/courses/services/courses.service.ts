import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Course } from '../model/course';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  constructor(private httpCLient: HttpClient) { }

  list(): Course[] {
    return [
      {_id: '1', name: 'Angular + Spring', category: 'Remoto'}
  ];
  }
}
