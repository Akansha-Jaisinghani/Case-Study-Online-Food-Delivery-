import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

const BASIC_URL = ["http://localhost:8080/"]

@Injectable({
  providedIn: 'root'
})

 export class AuthenticateService {

    constructor(private http: HttpClient){}

    signup(signuprequest): Observable<any> {
        return this.http.post<[]>(BASIC_URL + "api/auth/signUp",signuprequest)
    }
    
}
//   AUTHORIZED_USERS=AUTHORIZED_USERS         //query if use directly
//   constructor() { }

//   authenticate: boolean;
//   isAuthenticate(loginData) {
//     let grantAccess= this.AUTHORIZED_USERS.find(user => 
//       user.username==loginData.username&& user.password==loginData.password
//     );

//     if (grantAccess) {
//       localStorage.setItem('userin',JSON.stringify(loginData))
//       return true;
//     }
//     return false;
//   }
// }
