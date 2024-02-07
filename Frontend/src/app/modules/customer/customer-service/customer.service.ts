import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from 'src/app/auth-services/storage-services/storage.service';

const BASIC_URL = ["http://localhost:8081/"]

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient ) { }

  getRestaurant(){
    return this.http.get<any[]>(BASIC_URL + "restaurant/view",  {
     headers: this.createAuthorizationHeader()
   })
   }

   getAllRestaurantsByTitle(title:String){
    return this.http.get<any[]>(BASIC_URL + `restaurant/restaurants/${title}`,  {
     headers: this.createAuthorizationHeader()
   })
   }

  createAuthorizationHeader():HttpHeaders{
    let authHeaders:HttpHeaders= new HttpHeaders({  });
    return authHeaders.set(
      "Authorization","Bearer "+StorageService.getToken()
    );
  }

  getAllDishesByRestaurant(restaurantId:number){
    return this.http.get<any[]>(BASIC_URL + `restaurant/${restaurantId}/viewDishes`,  {
     headers: this.createAuthorizationHeader()
   })
   }

   getAllRestaurantsByRestaurantAndTitle(restaurantId:number,title:String){
    return this.http.get<any[]>(BASIC_URL + `restaurant/${restaurantId}/dishes/${title}`,  {
     headers: this.createAuthorizationHeader()
   })
   }
}
