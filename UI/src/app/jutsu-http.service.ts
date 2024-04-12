import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { jutsuInfo } from './jutsuInfo';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class JutsuHttpService {
  public baseUrl: string;
  constructor(private http: HttpClient) {
    this.baseUrl = environment.baseUrl;
   }

  createJutsu(handseals: string){
    return this.http.post<jutsuInfo>(`${this.baseUrl}/jutsu?handseals=${handseals}`, undefined);
  }

  generateImage(jutsuInfo: jutsuInfo) {
    return this.http.post<jutsuInfo>(`${this.baseUrl}/jutsu/visualize`, jutsuInfo);
  }
}
