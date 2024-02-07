import { Component, ViewChild } from '@angular/core';
import { Product } from '../productEntity';
import { RESTAURANT_TYPES } from '../restaurant-type';
import { CartComponent } from './cart/cart.component';
import { AddProductComponent } from './add-product/add-product.component';
import { isNgTemplate } from '@angular/compiler';


@Component({
  selector: 'app-wrapper',
  templateUrl: './wrapper.component.html',
  styleUrls: ['./wrapper.component.css']
})
export class WrapperComponent {
  title = 'restaurantManagement';
  restaurantTypes = RESTAURANT_TYPES;
  type: string = this.restaurantTypes.all;
  onFormShow = false;
  onCartShow = false;
  cartArray = [];

  index: number;
  @ViewChild(AddProductComponent, { static: false }) edit: AddProductComponent;

  products: Product[] = [
    {
      id: 1,
      name: "pizza",
      availableQuantity: 4,
      price: 200,
      restType: this.restaurantTypes.type1,
      imagePath:"../assets/images/pizza.jpeg"
    },
    {
      id: 2,
      name: "sandwich",
      availableQuantity: 3,
      price: 400,
      restType: this.restaurantTypes.type1,
      imagePath:"../assets/images/sandwich.jpeg"

    },
    {
      id: 3,
      name: "bread",
      availableQuantity: 0,
      price: 100,
      restType: this.restaurantTypes.type2,
      imagePath:"../assets/images/bread.jpeg"
    },
    {
      id: 4,
      name: "burger",
      availableQuantity: 2,
      price: 450,
      restType: this.restaurantTypes.type3,
      imagePath:"../assets/images/burger.jpeg"
    },
    {
      id: 5,
      name: "spaghetti",
      availableQuantity: 9,
      price: 500,
      restType: this.restaurantTypes.type4,
      imagePath:"../assets/images/spaghetti.jpeg"
    },
    {
      id: 6,
      name: "apple juice",
      availableQuantity: 1,
      price: 390,
      restType: this.restaurantTypes.type4,
      imagePath:"../assets/images/apple.jpeg"
    },
    {
      id: 7,
      name: "eggs",
      availableQuantity: 2,
      price: 60,
      restType: this.restaurantTypes.type2,
      imagePath:"../assets/images/eggs.jpeg"
    },
    {
      id: 8,
      name: "cheese",
      availableQuantity: 4,
      price: 40,
      restType: this.restaurantTypes.type3,
      imagePath:"../assets/images/cheese.jpeg"
    }
  ]


  onAdd() {
    this.onFormShow = !this.onFormShow;
  }

  showCart() {
    this.onCartShow = !this.onCartShow;
  }

  onclickSubmit(event: any) {
    for (let i = 0; i < this.products.length; i++) {
      if (this.products[i].id == event.id) {
        alert("Id already Exist");
        return;
      }
     }
    this.products.push(event);
    this.onFormShow = false;
  }


  onEdit(event: any) {
    this.onAdd();
    this.index = event;
    setTimeout(() => {
      this.edit.onEdit(this.products[event]);
    }, 100);

  }

  onUpdate(event) {
    this.products[this.index] = event;
    this.onFormShow = false;
  }

  onCancel() {
    this.onFormShow = false;
  }

  onClose() {
    this.onCartShow = false;
  }

  addData(event) {
    let promptValue = prompt("Enter quantity");
    let q=Number(promptValue);
    if (q == null || q == 0) {
      alert("Please enter a valid quantity");
      return;
    }
    let flag = false;
    for (let i = 0; i < this.products.length; i++) {
      if (event == this.products[i].id && q > this.products[i].availableQuantity) {
        alert("Quantity is greater than available quantity")
        return;
      }
    }

    for (let i = 0; i < this.cartArray.length; i++) {
      if (event == this.cartArray[i].id) {
        this.cartArray[i].availableQuantity += q;
        flag = true;
        break;
      }
    }

    if (flag) {
      for (let i = 0; i < this.products.length; i++) {
        if (event == this.products[i].id) {
          this.products[i].availableQuantity -= q;
          return;
        }
      }
    }
    for (let i = 0; i < this.products.length; i++) {
      if (event == this.products[i].id) {
        let a = { ...this.products[i] };
        this.cartArray.push(a);
        this.products[i].availableQuantity -= q;

      }
    }
    for (let i = 0; i < this.cartArray.length; i++) {
      if (event == this.cartArray[i].id) {
        this.cartArray[i].availableQuantity = q;
      }
    }
  }


  Analytics() {
    let array = [];
    let arrayValue = [];
    let restAvg = [];
    let exp = 0;
    let val = 0;
    let value;
    let maxprod = [];
    let restaurant;
    let product;



    //Maximum available product in list
    for (let i = 0; i < this.products.length; i++) {
      let temp = 0;
      let rest = 0;
      for (let j = 0; j < array.length; j++) {
        if (this.products[i].name == array[j]) {
          arrayValue[j] += this.products[i].availableQuantity;
          temp = 1;
        }
      }
      if (temp == 0) {
        array.push(this.products[i].name);
        arrayValue.push(this.products[i].availableQuantity);
      }
    }

    let max = arrayValue[0];
    let index = 0;
    for (let i = 0; i < arrayValue.length; i++) {
      if (arrayValue[i] > max) {
        max = arrayValue[i];
        index = i;
      }
    }

    //Restaurant with maximum average price
    const values = Object.values(this.restaurantTypes);
    let maxAvg = 0;
    let maxAvgRestType = values[0];
    let maxQuan = 0;
    let maxQuanRest = values[0];
    for (let i = 0; i < values.length - 1; i++) {
      let totalValue = 0;
      let totalQuan = 0;
      for (let j = 0; j < this.products.length; j++) {
        if (values[i] == this.products[j].restType) {
          totalQuan += this.products[j].availableQuantity;
          totalValue += this.products[j].availableQuantity * this.products[j].price;
        }
      }
      restAvg[i] = totalValue / totalQuan;
      if (restAvg[i] > maxAvg) {
        maxAvg = restAvg[i];
        maxAvgRestType = values[i];
      }
      //Restaurant with maximum product

      if (totalQuan > maxQuan) {
        maxQuan = totalQuan;
        maxQuanRest = values[i];
      }
    }


    //expensive product
    this.products.forEach((item) => {
      if (exp < item.price) {
        exp = item.price;
        restaurant = item.restType;
        product = item.name;
      }
    })

    //Maximum occured product in cart
    this.cartArray.forEach((item) => {
      if (item.availableQuantity > val) {
        val = item.availableQuantity;
        value = item.name;
      }
    })
    alert(val);
    this.cartArray.forEach((item) => {
      if (item.availableQuantity == val) {
        maxprod.push(item.name);
      }
    })
    console.log("Most available product: ", array[index]);
    console.log(values);
    console.log(restAvg);
    console.log("Restaurant with maximum average price: ", maxAvgRestType + " and average price =", maxAvg);
    console.log("Restaurant with expensive product :Restaurant ", restaurant + "  and Most expensive product:", product);
    console.log("Product occuring maximum times in cart: ", maxprod)
    console.log("Restaurant with maximum products :", maxQuanRest);
  }
}



