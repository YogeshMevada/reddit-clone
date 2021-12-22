import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Post } from '../../model/post';
import { PostService } from '../../service/post.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  trendingPosts: Array<Post> = [];
  posts: Array<Post> = [];
  topCommunities: Array<String> = [];
  popularCommunities: Array<String> = [];

  constructor(private postService: PostService) {
  }

  ngOnInit(): void {
    this.getAllTrendingPosts();
    this.getAllPosts();
    this.getTopCommunities();
    this.getPopularCommunities();
  }

  getAllTrendingPosts() {

  }

  getAllPosts() {
    this.postService.getAllPosts()
      .subscribe({
        next: (res) => {
          console.log(res);
          for (var post of res) {
            this.trendingPosts.push(post);
            this.posts.push(post);
          }
        },
        error: (err: HttpErrorResponse) => {
          console.log("Get Post Error response: " + err);
        },
        complete: () => {
          console.log("Get Post Complete");
        }
      });
  }

  getTopCommunities() {

  }

  getPopularCommunities() {

  }
}
