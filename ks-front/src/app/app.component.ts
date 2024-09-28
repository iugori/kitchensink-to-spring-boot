import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { IMember } from './entities/member.model';
import { CommonModule } from '@angular/common';
import { NgForm, FormsModule } from '@angular/forms';
import { MemberService } from './services/member.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule,RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = "Welcome to JBoss!";

  restRoot = MemberService.URL;

  members: IMember[] = [];

  constructor(private memberService: MemberService) { }

  ngOnInit(): void {
    this.getMembers();
  }

  trackById(index: number, item: IMember): number {
    return item.id;
  }

  getMembers(): void {
    this.memberService.getMembers().subscribe(members => this.members = members);
  }

  registerMember(regForm: NgForm) {
    const formData = { ...regForm.value, reg: "reg" };
    console.log(formData);
  }

}
