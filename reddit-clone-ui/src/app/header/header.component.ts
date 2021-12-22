import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  public isLoggedIn: boolean = false;
  username: String;

  constructor(private ngbModal: NgbModal, private authService: AuthService) {

  }

  ngOnInit(): void {
    this.isLoggedIn = this.authService.isLoggedIn();
  }

  goToUserProfile() {

  }

  open(modal: any) {
    this.ngbModal.open(modal);
  }

  logout() {
    this.authService.logout();
    location.reload();
  }
}
