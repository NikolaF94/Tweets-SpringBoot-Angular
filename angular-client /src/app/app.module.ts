import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TweetsComponent } from './tweets/tweets.component';
import { HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TweetService } from './services/tweet.service';



@NgModule({
  declarations: [
    AppComponent,
    TweetsComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    LogoutComponent,
    

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [TweetService],
  bootstrap: [AppComponent]
})
export class AppModule { }
