import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tutorial } from '../models/tutorial.model';
import {API_URL} from "../app.constants";

const baseUrl = 'http://localhost:8080/api/tutorials';

@Injectable({
  providedIn: 'root',
})
export class TutorialService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Tutorial[]> {
    return this.http.get<Tutorial[]>(`${API_URL}/tutorials`);
  }

  get(id: any): Observable<Tutorial> {
    return this.http.get<Tutorial>(`${API_URL}/tutorials/${id}`);
  }

  create(data: any): Observable<any> {
    return this.http.post(`${API_URL}/tutorials`, data);
  }

  update(id: any, data: any): Observable<any> {
    return this.http.put(`${API_URL}/tutorials/${id}`, data);
  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${API_URL}/tutorials/${id}`);
  }

  deleteAll(): Observable<any> {
    return this.http.delete(`${API_URL}/tutorials`);
  }

  findByTitle(title: any): Observable<Tutorial[]> {
    return this.http.get<Tutorial[]>(`${API_URL}/tutorials?title=${title}`);
  }
}
