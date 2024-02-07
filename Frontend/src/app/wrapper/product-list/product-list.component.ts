import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { RESTAURANT_TYPES } from '../../restaurant-type';


@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  @Input() products: any;
  @Input() type: any;
  @Output() add = new EventEmitter();
  @Output() editData = new EventEmitter();
  value = true;
  matchedList = this.products;


  restaurantTypes = RESTAURANT_TYPES;

  constructor() { }

  ngOnInit() {
    this.matchedList=this.products;
  }

  addToCart(id) {
    this.value = false;
    this.add.emit(id);
  }

  onEdit(index) {
    this.editData.emit(index);
  }

  addQuantity(i) {
    let promptValue = prompt("Enter quantity");
    let q = Number(promptValue);
    this.products[i].availableQuantity += q;
  }

  text: string = "";
  studentName = new Array();
  getValues(name) {
    if (name.trim() == "") {
      return;
    }
    let studentName = new Array();

    this.text = name;
    if (this.text.length >= 1) {
      for (let i = 0; i < this.products.length; i++) {
        console.log(this.products[i].name);
        console.log((this.products[i].name).startsWith(this.text));
        if ((this.products[i].name.toLowerCase().trim()).startsWith(this.text.toLowerCase().trim())) {
          studentName.push(this.products[i]);
        }
      }
    }
    return studentName;
  }

  
  matchFound(name) {

    if (!name) {
      this.matchedList = this.products;
    }
    else {
      this.matchedList = this.getValues(name);

    }
  }
}





