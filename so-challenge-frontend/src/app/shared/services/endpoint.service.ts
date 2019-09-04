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

    private BASE_URL = 'http://localhost:8080/api/';
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
        return this.http.get<Participant[]>(this.BASE_URL + 'participant/all', this.APPLICATION_JSON_OPTIONS);
    }

    addParticipant(profileId: number) {
        return this.http.post(this.BASE_URL + 'participant/add', profileId, this.APPLICATION_JSON_OPTIONS);
    }

    deleteParticipant(profileId: number) {
        return this.http.delete(this.BASE_URL + `participant/delete/${profileId}`);
    }

    getChallenges() {
        return this.http.get<Challenge[]>(this.BASE_URL + 'challenge/all', this.APPLICATION_JSON_OPTIONS)
            .pipe(map(res => res as Challenge[]));
    }

    deleteChallenge(id: number): Observable<Challenge> {
        return this.http.delete<Challenge>(this.BASE_URL + `challenge/delete/${id}`, this.APPLICATION_JSON_OPTIONS);
    }

    getStates(): Observable<State[]> {
        return this.http.get<State[]>(this.BASE_URL + 'state/all');
    }

    getAvailableStates(s: State): Observable<State[]> {
        if (s == null) {
            this.getCreateStates();
        }
        return this.http.get<State[]>(this.BASE_URL + 'state/available');
    }

    getCreateStates(): Observable<State[]> {
        return this.http.get<State[]>(this.BASE_URL + 'state/startStates', this.APPLICATION_JSON_OPTIONS);
    }


    addChallenge(c: Challenge) {
        return this.http.post(this.BASE_URL + 'challenge/add', c, this.APPLICATION_JSON_OPTIONS);
    }

    updateChallenge(c: Challenge) {
        return this.http.put(this.BASE_URL + 'challenge/update', c, this.TEXT_PLAIN_OPTIONS);
    }

    getTags(): Observable<Tag[]> {
        return this.http.get<Tag[]>(this.BASE_URL + 'tag/all');
    }

    addParticipantsToChallenge(chTitle: string, profileIds: string) {
        return this.http.put(this.BASE_URL + `challenge/participants/add/${chTitle}/${profileIds}`, this.APPLICATION_JSON_OPTIONS);
    }

    addParticipantsToNewChallenge(c: Challenge, profileIds: string) {
        return this.http.post(this.BASE_URL + `challenge/participants/add/${profileIds}/new`, c, this.APPLICATION_JSON_OPTIONS);
    }

    // addParticipantsToChallenge(c: Challenge, profileIds: string) {
    //     return this.http.put(this.BASE_URL + `challenge/participants/add/${profileIds}`, c, this.APPLICATION_JSON_OPTIONS);
    // }

    removeParticipantsFromChallenge(chId: number, p: string) {
        // TODO remove participants
    }

    // TODO
    addTagsToChallenge(chId: number, t: Tag[]) {
        return this.http.put(this.BASE_URL + `${chId}/tags/add`, t, this.TEXT_PLAIN_OPTIONS);
    }

    async getPointsOfUser(id: number): Promise<Answer[]> {

        // Initializing
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
        return this.http.post(this.BASE_URL + 'tag/add', name, this.APPLICATION_JSON_OPTIONS);
    }
}
