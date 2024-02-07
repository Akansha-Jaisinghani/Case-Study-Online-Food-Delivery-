import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  @Output() cartClose = new EventEmitter();
  @Input() cartArray: any;
  @Input() products: any;
  visible = true;
  sum = 0;

  constructor() { }

  ngOnInit() {
    this.totalAmount();
    this.productPrice();
  }

  productPrice() {
    for (let i = 0; i < this.cartArray.length; i++) {
      this.cartArray[i]['totalPrice'] = this.cartArray[i].availableQuantity * this.cartArray[i].price;
    }
  }

  totalAmount() {
    for (let i = 0; i < this.cartArray.length; i++) {
      this.sum += this.cartArray[i].availableQuantity * this.cartArray[i].price;
    }
  }

  onClose() {
    this.visible = false;
    this.cartClose.emit(this.visible);
  }

  removeFromCart(index) {
    let promptValue = prompt("Enter the quantity");
    let q=Number(promptValue);
    if (this.cartArray[index].availableQuantity < q) {
      alert("Value Invalid");
    }
    else {
      for (let i = 0; i < this.products.length; i++) {
        if (this.products[i].id == this.cartArray[index].id) {
          this.products[i].availableQuantity += q;
        }
      }
      if (this.cartArray[index].availableQuantity == q) {
        this.cartArray.splice(index, 1);
      }
      this.cartArray[index].availableQuantity -= q;
      this.productPrice();
      this.totalAmount();
    }

  }

}
