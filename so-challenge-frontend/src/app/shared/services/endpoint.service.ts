import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Participant } from '../models/participant';
import { Challenge } from '../models/challenge';


@Injectable({
  providedIn: 'root'
})
export class EndpointService {

  private BASE_URL = 'http://localhost:8080/so-challenge-backend/api/';

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'text/plain'
    })
  };

  constructor(private http: HttpClient) { }

  getParticipants(): Observable<Participant[]> {
    return this.http.get<Participant[]>(this.BASE_URL + 'participant/all');
  }

  addParticipant(profileId: number) {
    return this.http.post(this.BASE_URL + 'participant/add', profileId, this.httpOptions);
  }

  deleteParticipant(profileId: number) {
    return this.http.delete(this.BASE_URL + `participant/delete/${profileId}`);
  }

  getChallenges(): Observable<Challenge[]> {
    return this.http.get<Challenge[]>(this.BASE_URL + 'challenge/all');
  }

  deleteChallenge(id: number) {
    return this.http.delete(this.BASE_URL + `challenge/delete/${id}`);
  }
}
