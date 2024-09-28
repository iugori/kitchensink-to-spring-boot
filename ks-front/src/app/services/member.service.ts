import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { IMember } from '../entities/member.model';

@Injectable({
  providedIn: 'root'
})
export class MemberService {

  public static URL = "/rest/members";

  constructor(private http: HttpClient) { }

  getMembers(): Observable<IMember[]> {
    const members = of([
      {
        id: 0,
        name: "John Smith",
        email: "john.smith@mailinator.com",
        phoneNumber: "2125551212",
      },
      {
        id: 1,
        name: "John Smith",
        email: "john.smith@mailinator.com",
        phoneNumber: "2125551212",
      }
    ]);
    return this.http.get<IMember[]>(MemberService.URL);
    // return this.http.get<IMember[]>(MemberService.URL);
  }

}
