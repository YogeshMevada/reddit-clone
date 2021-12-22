import { HttpErrorResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthRequest } from 'src/app/model/auth-request';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @Input() modal: NgbModal;

  loginForm: FormGroup;

  constructor(private authService: AuthService,
    private router: Router,
    private fb: FormBuilder) {
    this.createLoginForm();
  }

  ngOnInit(): void {
  }

  createLoginForm() {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  login() {
    this.authService.login(new AuthRequest(this.loginForm.value.username, this.loginForm.value.password))
      .subscribe({
        next: (res) => {
          this.authService.updateToken(res.token, res.username);
          location.reload();
        }, error: (err: HttpErrorResponse) => {
          console.log("Error response");
          console.log(err.error.message);
        },
        complete: () => {
          console.log("Authenticate complete.");
          this.router.navigate(['/']);
        }
      });
  }
}
