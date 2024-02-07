import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';

const BASIC_URL = ["http://localhost:8081/"]

@Injectable({
  providedIn: 'root'
})

 export class AuthService {

    constructor(private http: HttpClient){}

    signup(signuprequest: any): Observable<any> {
        return this.http.post<[]>(BASIC_URL + "api/auth/signUp",signuprequest).pipe(
          catchError((error) => {
            // Handle errors here
            console.error('Error in signup:', error);
    
            if (error.status === 409 ) {
              // Handle specific error cases if needed
              return throwError("Mail already exists");
            }
    
            // For all other cases, throw a generic error
            return throwError('An unexpected error occurred. Please try again later.');
          })
        );
      }
    

    login(loginRequest : any): Observable<any> {
      return this.http.post<[]>(BASIC_URL + "api/auth/login",loginRequest).pipe(
        catchError((error) => {
          if (error.status === 401) {
            // Handle Bad Credentials Exception
            return throwError('Incorrect username or password');
          }
          return throwError(error);
        })
      );
    }
    
}