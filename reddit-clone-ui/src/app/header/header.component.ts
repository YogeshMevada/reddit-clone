import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLoggedIn: boolean = false;
  username: String = "";
  users: any[];

  constructor() {
    this.users = [];
  }

  ngOnInit(): void {
  }

  goToUserProfile() {

  }

  logout() {

  }

  addUser(user: any) {
    console.log(user);
    this.users.push(user);
  }
}
