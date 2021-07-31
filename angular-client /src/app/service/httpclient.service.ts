import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthenticationService } from '../service/authentication.service'; 
import { Keyword } from '../classes/keyword';


@Injectable({
  providedIn: 'root'
})
export class HttpClientService {
  

  private tweetsUrl : string;

  constructor(
    private httpClient:HttpClient
  ) { this.tweetsUrl = 'http://localhost:8080/search/';
      
     }

    
 public sendKeyword (keyword: Keyword) {

 
   
    let username = sessionStorage.getItem('username')
    let password= sessionStorage.getItem('password')
    
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ':' + password), 'Content-Type': 'application/json'});
    
    return this.httpClient.post<Keyword>(this.tweetsUrl  + keyword.text + "/" + 2, [keyword.text, 2] , {headers});
    

 }


}
