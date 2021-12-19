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
      .subscribe(data => {
        for (var post of data) {
          this.trendingPosts.push(post);
          this.posts.push(post);
          console.log(post);
        }
      });
  }

  getTopCommunities() {

  }
  
  getPopularCommunities() {

  }
}
