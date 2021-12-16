import { Component, Input, OnInit } from '@angular/core';
import { Post } from 'src/app/model/post';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  @Input() post: Post;

  constructor() {
    console.log("Constructor: " + this.post);
  }

  ngOnInit(): void {
    console.log("OnInit: " + this.post);
  }

}
