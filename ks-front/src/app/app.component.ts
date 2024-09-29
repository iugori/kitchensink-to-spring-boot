import { CommonModule } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { IMember } from './entities/member.model';
import { MemberService } from './services/member.service';

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
  statusMessages: string[] = [];
  validationMessages: Record<string, string> = {};

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
    const member = { ...this.regForm.value };
    this.memberService.addMember(member).subscribe(r => {
      this.statusMessages = [];
      this.validationMessages = {};
      if (r) {
        Object.keys(r).forEach((key) => {
          if (["name", "email", "phoneNumber"].includes(key)) {
            this.validationMessages[key] = `${r[key]}`;
          } else {
            this.statusMessages.push(`${r[key]}`);
          }
        });
      } else {
        this.regForm.resetForm();
        this.statusMessages = ["Registered!"];
        this.getMembers();
      }
    });
  }

}
