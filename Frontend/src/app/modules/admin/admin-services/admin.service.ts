import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { StorageService } from 'src/app/auth-services/storage-services/storage.service';

const BASIC_URL = ["http://localhost:8081/"]

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  registerRestaurant(restaurantRequest: any):Observable<any>{
    return this.http.post<[]>(BASIC_URL + "restaurant/register",restaurantRequest,
    {
      headers: this.createAuthorizationHeader()
    }).pipe(
      catchError((error) => {
        // Handle errors here
        console.error('Error in registration:', error);

        if (error.status === 409 ) {
          // Handle specific error cases if needed
          return throwError("Restaurant Name already exists");
        }
        if (error.status === 400 ) {
          // Handle specific error cases if needed
          return throwError("Food and license doc field is mandatory");
        }

        // For all other cases, throw a generic error
        return throwError('An unexpected error occurred. Please try again later.');
      })
    );
  }

  getRestaurantById(restaurantId: number){
    return this.http.get<[]>(BASIC_URL + `restaurant/view/${restaurantId}`, 
    {
      headers: this.createAuthorizationHeader()
    })

  }

  deleteRestaurant(restaurantId:number){
    return this.http.delete<[]>(BASIC_URL + `restaurant/remove/${restaurantId}`, 
    {
      headers: this.createAuthorizationHeader()
    })
  }

  deleteDish(dishId:number){
    return this.http.delete<[]>(BASIC_URL + `dish/remove/${dishId}`, 
    {
      headers: this.createAuthorizationHeader()
    })
  }

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

   getAllRestaurantsByRestaurantAndTitle(restaurantId:number,title:String){
    return this.http.get<any[]>(BASIC_URL + `restaurant/${restaurantId}/dishes/${title}`,  {
     headers: this.createAuthorizationHeader()
   })
   }

   getAllDishesByRestaurant(restaurantId:number){
    return this.http.get<any[]>(BASIC_URL + `restaurant/${restaurantId}/viewDishes`,  {
     headers: this.createAuthorizationHeader()
   })
   }


   getAllDishesById(dishId:number){
    return this.http.get<any[]>(BASIC_URL + `dish/view/${dishId}`,  {
     headers: this.createAuthorizationHeader()
   })
   }

  // getAllDishes():Observable<any>{
  //   return this.http.get<any[]>(BASIC_URL + "dish/viewall",  {
  //    headers: this.createAuthorizationHeader()
  //  })
  //  }

  addDish(restaurantId: any, dishData: any):Observable<any>{
    return this.http.post<[]>(`${BASIC_URL}restaurant/${restaurantId}/addDish`,dishData,
    {
      headers: this.createAuthorizationHeader()
    }).pipe(
      catchError((error) => {
        // Handle errors here
        console.error('Error in registration:', error);

        if (error.status === 409 ) {
          // Handle specific error cases if needed
          return throwError("Dish Name already exists");
        }

        // For all other cases, throw a generic error
        return throwError('An unexpected error occurred. Please try again later.');
      })
    );
  }

  updateDish(dishId: any, dishData: any):Observable<any>{
    return this.http.put<[]>(`${BASIC_URL}dish/update/${dishId}`,dishData,
    {
      headers: this.createAuthorizationHeader()
    })
  }

  updateRestaurant(restaurantId: any, restaurantData: any):Observable<any>{
    return this.http.put<[]>(`${BASIC_URL}restaurant/update/${restaurantId}`,restaurantData,
    {
      headers: this.createAuthorizationHeader()
    }).pipe(
      catchError((error) => {
        // Handle errors here
        console.error('Error in registration:', error);

        if (error.status === 409 ) {
          // Handle specific error cases if needed
          return throwError("Restaurant Name already exists");
        }

        // For all other cases, throw a generic error
        return throwError('An unexpected error occurred. Please try again later.');
      })
    );
  }

  createAuthorizationHeader():HttpHeaders{
    let authHeaders:HttpHeaders= new HttpHeaders({  });
    return authHeaders.set(
      "Authorization","Bearer "+StorageService.getToken()
    );
  }
}
