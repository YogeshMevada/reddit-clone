import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './page/home/home.component';
import { SubredditComponent } from './page/subreddit/subreddit.component';
import { AuthGuard } from './service/auth.guard';

const routes: Routes = [
  { path: "", component: HomeComponent},
  { path: 'r/:name', component: SubredditComponent }
]

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
