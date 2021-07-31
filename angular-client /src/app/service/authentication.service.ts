import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';

export class User{
  constructor(
    public status:string,
     ) {}
  
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  // public username: String;
  // public password: String;


constructor(private http: HttpClient) {

  }



  authenticate(username, password) {
    return this.http.post(`http://localhost:8080/validateLogin`, {username, password},
      { headers: { authorization: this.createBasicAuthToken(username, password) } }).pipe(map (
        // this.username = username;
        // this.password = password;
        userData => {
        sessionStorage.setItem('username',username)
        sessionStorage.setItem('password',password);
        return userData;})
        // this.registerSuccessfulLogin(username, password);
      );
  }

  createBasicAuthToken(username, password) {
  console.log('test', 'Basic ' + window.btoa(username + ":" + password))
    return 'Basic ' + window.btoa(username + ":" + password)
  }

  // registerSuccessfulLogin(username, password) {
  //   sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME, username)
  // }


  isUserLoggedIn() {
    let user = sessionStorage.getItem('username')
    console.log(!(user === null))
    return !(user === null)
  }

  logOut() {
    sessionStorage.removeItem('username')
  }
}