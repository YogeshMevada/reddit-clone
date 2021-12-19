import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from "@angular/common/http";

import { AppComponent } from './app.component';
import { AuthModule } from './auth/auth.module';
import { HeaderComponent } from './header/header.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UserComponent } from './user/user.component';
import { HomeComponent } from './page/home/home.component';
import { PostComponent } from './component/post/post.component';
import { TrendingPostComponent } from './component/trending-post/trending-post.component';
import { SubredditComponent } from './page/subreddit/subreddit.component'

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    UserComponent,
    HomeComponent,
    PostComponent,
    TrendingPostComponent,
    SubredditComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    FormsModule,
    NgbModule,
    AuthModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
