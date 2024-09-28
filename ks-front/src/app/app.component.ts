import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { IMember } from './entities/member.model';
import { CommonModule } from '@angular/common';
import { NgForm, FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = "Welcome to JBoss!";

  restRoot = "/rest/members";


  members: IMember[] = [
    {
      id: 0,
      name: "John Smith",
      email: "john.smith@mailinator.com",
      phoneNumber: "2125551212",
    }
  ];

  trackById(index: number, item: IMember): number {
    return item.id;
  }

  registerUser(regForm: NgForm) {
    const formData = { ...regForm.value, reg: "reg" };
    console.log(formData);
  }

}
