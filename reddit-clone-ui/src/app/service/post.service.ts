import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../model/post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  getAllPosts(): Observable<Array<Post>> {
    console.log("Get Post.")
    return this.http.get<Array<Post>>("http://localhost:8080/reddit-clone/api/v1/post");
  }
}
