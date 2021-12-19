import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './auth/signup/signup.component';
import { LoginComponent } from './auth/login/login.component';
import { HomeComponent } from './page/home/home.component';
import { SubredditComponent } from './page/subreddit/subreddit.component';

const routes: Routes = [
  { path: "", component: HomeComponent },
  { path: 'r/:name', component: SubredditComponent }
]

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
