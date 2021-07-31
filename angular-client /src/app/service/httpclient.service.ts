import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthenticationService } from '../service/authentication.service'; 
import { Keyword } from '../classes/keyword';

export class Employee{
  constructor(
    public empId:string,
    public name:string,
    public designation:string,
    public salary:string,
  ) {}
}



@Injectable({
  providedIn: 'root'
})
export class HttpClientService {
  

  private tweetsUrl : string;

  constructor(
    private httpClient:HttpClient
  ) { this.tweetsUrl = 'http://localhost:8080/search/';
      
     }

     getEmployees()
  {
    let username = sessionStorage.getItem('username')
    let password= sessionStorage.getItem('password')
  
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ':' + password) });
    
       return this.httpClient.get<Employee[]>('http://localhost:8080/employees',{headers});
  }

  public deleteEmployee(employee) {
    
    let username = sessionStorage.getItem('username')
    let password= sessionStorage.getItem('password')
  
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ':' + password) });
    return this.httpClient.delete<Employee>("http://localhost:8080/employees" + "/"+ employee.empId,{headers});
  }

  public createEmployee(employee) {
   
    let username = sessionStorage.getItem('username')
    let password= sessionStorage.getItem('password')
  
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ':' + password) });
    return this.httpClient.post<Employee>("http://localhost:8080/employees", employee,{headers});
  }
 
 public sendKeyword (keyword: Keyword) {

 
   
    let username = sessionStorage.getItem('username')
    let password= sessionStorage.getItem('password')
    
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ':' + password), 'Content-Type': 'application/json'});
    
    return this.httpClient.post<Keyword>(this.tweetsUrl  + keyword.text + "/" + 2, [keyword.text, 2] , {headers});
    

 }


}