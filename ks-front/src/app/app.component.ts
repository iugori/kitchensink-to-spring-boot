import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { IMember } from './entities/member.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = "Welcome to JBoss!";
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

}
