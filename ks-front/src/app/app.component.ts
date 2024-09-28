import { Component, ViewChild } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { IMember } from './entities/member.model';
import { CommonModule } from '@angular/common';
import { NgForm, FormsModule } from '@angular/forms';
import { MemberService } from './services/member.service';
import { HttpClientModule } from '@angular/common/http';
import { tap } from 'rxjs';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  restRoot = MemberService.URL;
  title = "Welcome to JBoss!";

  members: IMember[] = [];
  messages: string[] = [];

  @ViewChild('regForm') regForm!: NgForm;

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

  registerMember() {
    console.log(this.regForm);
    const member = { ...this.regForm.value /*, reg: "reg" */ };
    this.memberService.addMember(member)
      .subscribe(r => {
        if (r) {
          this.messages = [];
          for (const key in r) {
            this.messages.push(`${key}: ${r[key]}`);
          }
        } else {
          this.regForm.resetForm();
          this.messages = ["Registered!"];
          this.getMembers();
        }
      });
  }

}
