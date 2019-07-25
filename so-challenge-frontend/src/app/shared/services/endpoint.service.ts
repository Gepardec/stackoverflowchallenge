import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Participant} from '../models/participant';
import {Challenge} from '../models/challenge';
import * as moment from 'moment';


@Injectable({
    providedIn: 'root'
})
export class EndpointService {

    private BASE_URL = 'http://localhost:8080/so-challenge-backend/api/';
    public pageNr = 1;
    private items: number[] = [];

    private httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'text/plain'
        })
    };

    constructor(private http: HttpClient) {
    }

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

    async getPointsOfUser(id: number): Promise<number[]> {

        // Initializing
        let SE_BASE_URL = `https://api.stackexchange.com/2.2/users/`;
        // let pageNr = 1;
        let PAGES = `/answers?page=${this.pageNr}&pagesize=100`;
        let DATE = `&fromdate=${moment('2015-07-25', 'YYYY-MM-DD').unix()}`; // TODO: get startdate from database (Beitrittsdatum)
        let QUERY_PARAMS = `&order=desc&sort=votes&site=stackoverflow&filter=!Fcb(61J.xH8s_o6a0x*qPzhX)U`;
        let URL = SE_BASE_URL + id + PAGES + DATE + QUERY_PARAMS;
        let FILE_URL = `./assets/answers${this.pageNr++}.json`;

        // Processing
        console.log(FILE_URL);
        try {
            let data = await this.http.get(FILE_URL).toPromise();
            for (let score of data['items']) {
                this.items.push(score['score']);
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
}
