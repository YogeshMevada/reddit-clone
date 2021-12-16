import { Component, Input, OnInit } from '@angular/core';
import { Post } from 'src/app/model/post';

@Component({
  selector: 'app-trending-post',
  templateUrl: './trending-post.component.html',
  styleUrls: ['./trending-post.component.css']
})
export class TrendingPostComponent implements OnInit {

  @Input() post: Post;

  constructor() { }

  ngOnInit(): void {
  }

}
