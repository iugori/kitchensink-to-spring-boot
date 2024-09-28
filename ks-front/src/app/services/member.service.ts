import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { IMember } from '../entities/member.model';

@Injectable({
  providedIn: 'root'
})
export class MemberService {

  public static URL = "/rest/members";

  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getMembers(): Observable<IMember[]> {
    return this.http.get<IMember[]>(MemberService.URL);
  }

  addMember(member: IMember): Observable<any> {
    return this.http.post(MemberService.URL, member, this.httpOptions)
      .pipe(
        catchError((error: HttpErrorResponse) => of(error.error))
      )
  }

}
