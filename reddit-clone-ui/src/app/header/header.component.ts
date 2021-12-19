import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLoggedIn: boolean = false;
  username: String = "";
  users: any[];

  constructor(private ngbModal: NgbModal) {
    this.users = [];
  }

  ngOnInit(): void {
  }

  goToUserProfile() {

  }

  open(modal: any) {
    this.ngbModal.open(modal);
  }

  logout() {

  }

  addUser(user: any) {
    console.log(user);
    this.users.push(user);
  }
}
