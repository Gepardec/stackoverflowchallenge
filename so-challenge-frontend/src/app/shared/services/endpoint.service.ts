import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Participant} from '../models/participant';
import {Challenge} from '../models/challenge';
import {map} from 'rxjs/operators';
import {State} from '../models/state';
import {Tag} from '../models/tag';
import * as moment from 'moment';
import {Answer} from '../models/answer';


@Injectable({
  providedIn: 'root'
})
export class EndpointService {

    private BASE_URL = 'http://localhost:8080/so-challenge-backend/api/';
    public pageNr = 1;
    private items: Answer[] = [];

    private TEXT_PLAIN_OPTIONS = {
        headers: new HttpHeaders({
            'Content-Type': 'text/plain'
        })
    };

    private APPLICATION_JSON_OPTIONS = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    constructor(private http: HttpClient) {
    }

  getParticipants(): Observable<Participant[]> {
    return this.http.get<Participant[]>(this.BASE_URL + 'participant/all');
  }

    addParticipant(profileId: number) {
        return this.http.post(this.BASE_URL + 'participant/add', profileId, this.TEXT_PLAIN_OPTIONS);
    }

  deleteParticipant(profileId: number) {
    return this.http.delete(this.BASE_URL + `participant/delete/${profileId}`);
  }

    getChallenges() {
        return this.http.get<Challenge[]>(this.BASE_URL + 'challenge/all').pipe(
            map(res => res as Challenge[])
        );
    }

    deleteChallenge(id: number): Observable<Challenge> {
        return this.http.delete<Challenge>(this.BASE_URL + `challenge/delete/${id}`);
    }

    getStates(): Observable<State[]> {
        return this.http.get<State[]>(this.BASE_URL + 'state/all');
    }

    getCreateStates(): Observable<State[]> {
        return this.http.get<State[]>(this.BASE_URL + 'state/startStates');
    }


    addChallenge(c: Challenge) {
        return this.http.post(this.BASE_URL + 'challenge/add', c, this.APPLICATION_JSON_OPTIONS);
    }

    updateChallenge(c: Challenge) {
        return this.http.put(this.BASE_URL + 'challenge/update', c, this.APPLICATION_JSON_OPTIONS);
    }

    getTags(): Observable<Tag[]> {
        return this.http.get<Tag[]>(this.BASE_URL + 'tag/all');
    }

    addParticipantsToChallenge(id: number, p: string) {
        return this.http.put(this.BASE_URL + `challenge/addParticipants/${id}`, p, this.APPLICATION_JSON_OPTIONS);
    }

    async getPointsOfUser(id: number): Promise<Answer[]> {

        // Initializing
        // TODO all variables are former let
        const SE_BASE_URL = `https://api.stackexchange.com/2.2/users/`;
        // let pageNr = 1;
        const PAGES = `/answers?page=${this.pageNr}&pagesize=100`;
        const DATE = `&fromdate=${moment('2015-07-25', 'YYYY-MM-DD').unix()}`; // TODO: get startdate from database (Beitrittsdatum)
        const QUERY_PARAMS = `&order=desc&sort=votes&site=stackoverflow&filter=!.Fjr38AQkcvMg*eQSshw1WyCjkV9A`;
        const URL = SE_BASE_URL + id + PAGES + DATE + QUERY_PARAMS;
        const FILE_URL = `./assets/answers${this.pageNr++}.json`;

        console.log(URL);
        // Processing
        console.log(FILE_URL);
        try {
            let data;
            data = await this.http.get(FILE_URL).toPromise();
            for (const score of data.items) {
                const answer = new Answer();
                Object.assign(answer, score);
                this.items.push(answer);
            }
                // this.items.push(data['items']);
            if (data.has_more === true) {
                await this.getPointsOfUser(id);
            } else {
                this.pageNr = 1;
                return this.items;
            }
        } catch (error) {
            console.error(error);
        }
        return this.items;
    }
    deleteTag(id: number): Observable<Tag> {
        return this.http.delete<Tag>(this.BASE_URL + `tag/delete/${id}`);
    }

    addTag(name: string) {
        return this.http.post(this.BASE_URL + 'tag/add', name, this.TEXT_PLAIN_OPTIONS);
    }
}
