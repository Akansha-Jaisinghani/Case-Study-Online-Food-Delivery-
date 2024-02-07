export class DishDto {
    dishId: number;
    dishName: string;
    quantity: number;
    cost: number;
    imgBase64: string; // New property to hold Base64-encoded file content
  }