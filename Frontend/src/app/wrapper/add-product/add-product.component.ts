import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  @Output() array = new EventEmitter();
  @Output() arrayUpdate = new EventEmitter();
  @Output() cancel = new EventEmitter();
  valid = true;
  change = true;
  disable = false;
  id: number;
  name: string;
  availableQuantity: number;
  price: number;
  restType: ('A' | 'B' | 'C' | 'D');
  imagePath:string;
  constructor() { }

  ngOnInit() {
  }

  validation() {
    let name1 = /[a-zA-Z]+/;

    if (this.name == undefined || this.id == undefined
      || this.availableQuantity == undefined || this.price == undefined || this.restType == undefined) {
      alert("Fields cannot be empty")
      this.valid = false;
    }
    if (!this.name.match(name1)) {
      alert("Name not valid");
      this.valid = false;
    }
    if (this.price < 1 || this.availableQuantity < 1) {
      alert("Value cannot be less than 1");
      this.valid = false;
    }
  }
  onSubmit() {
    this.validation();
    if (this.valid == false) {
      return;
    }
    else {
      const data = {
        id: this.id,
        name: this.name,
        availableQuantity: this.availableQuantity,
        price: this.price,
        restType: this.restType,
        imagePath:this.imagePath
      }
      this.array.emit(data);

    }
  }

  onEdit(e) {
    this.id = e.id;
    this.name = e.name;
    this.availableQuantity = e.availableQuantity;
    this.price = e.price;
    this.restType = e.restType;
    this.imagePath=e.imagePath;
    this.disable = true;
  }

  onUpdate() {
    this.validation();
    if (this.valid == false) {
      return;
    }
    else {
      const data = {
        id: this.id,
        name: this.name,
        availableQuantity: this.availableQuantity,
        price: this.price,
        restType: this.restType,
        imagePath:this.imagePath
      }
      this.arrayUpdate.emit(data);
    }

  }
  onCancel() {
    this.change = false;
    this.cancel.emit(this.change);
  }
}
