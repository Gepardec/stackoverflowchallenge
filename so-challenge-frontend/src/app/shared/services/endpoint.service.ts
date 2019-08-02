import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Participant} from '../models/participant';
import {Challenge} from '../models/challenge';
import {map} from 'rxjs/operators';
import {Status} from '../models/status';
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

    getStatuses(): Observable<Status[]> {
        return this.http.get<Status[]>(this.BASE_URL + 'status/all');
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

    async getPointsOfUser(id: number): Promise<Answer[]> {

        // Initializing
        let SE_BASE_URL = `https://api.stackexchange.com/2.2/users/`;
        // let pageNr = 1;
        let PAGES = `/answers?page=${this.pageNr}&pagesize=100`;
        let DATE = `&fromdate=${moment('2015-07-25', 'YYYY-MM-DD').unix()}`; // TODO: get startdate from database (Beitrittsdatum)
        let QUERY_PARAMS = `&order=desc&sort=votes&site=stackoverflow&filter=!.Fjr38AQkcvMg*eQSshw1WyCjkV9A`;
        let URL = SE_BASE_URL + id + PAGES + DATE + QUERY_PARAMS;
        let FILE_URL = `./assets/answers${this.pageNr++}.json`;

        console.log(URL);
        // Processing
        console.log(FILE_URL);
        try {
            let data = await this.http.get(FILE_URL).toPromise();
            for (let score of data['items']) {
                let answer = new Answer();
                Object.assign(answer, score);
                this.items.push(answer);
            }
                // this.items.push(data['items']);
            if (data['has_more'] === true) {
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
