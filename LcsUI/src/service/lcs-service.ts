import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { LcsRequest   } from '../model/LcsRequest';
import { LcsResponse  } from '../model/LcsResponse';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable()
export class LcsService {
  private url = "http://localhost:8081/lcs";

  constructor(private http: HttpClient) {}

  /** POST: add a new user to the server */
  calculateLcs(lcsRequest: LcsRequest): Observable<LcsResponse> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json; charset=utf-8',
    });
    return this.http.post<LcsResponse>(
      this.url,
      JSON.stringify(lcsRequest),
      { headers: headers }
    );
  }
}
