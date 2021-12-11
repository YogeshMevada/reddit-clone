import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { SignupRequest } from './signup-request.payload';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signupRequest: SignupRequest;
  signupForm: FormGroup = new FormGroup({
    username: new FormControl('', Validators.required),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required),
  });

  constructor(private router: Router, private authService: AuthService) {
    console.log('Loged Signup construction');
    this.signupRequest = {
      username: '',
      email: '',
      password: ''
    }
  }

  ngOnInit(): void {
    console.log('Loged Signup oninit');
  }

  signup() {
    this.signupRequest.email = this.signupForm.get('email')?.value;
    this.signupRequest.username = this.signupForm.get('username')?.value;
    this.signupRequest.password = this.signupForm.get('password')?.value;

    this.authService.signup(this.signupRequest)
      .subscribe(data => {
        this.router.navigate(['/login'], { queryParams: { registerd: 'true' } });
      }, error => {
        console.log(error);
      })

  }

}
