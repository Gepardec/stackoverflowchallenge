import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {EndpointService} from "../../../../shared/services/endpoint.service";

@Component({
    selector: 'app-points',
    templateUrl: './points.component.html',
    styleUrls: ['./points.component.css']
})
export class PointsComponent implements OnInit {

    result: object[];
    score: number[] = [];
    totalScore: number = 0;
    errorMessage: boolean;

    constructor(private service: EndpointService) {
    }

    ngOnInit() {
        /*this.service.getPointsOfUser(331508).subscribe(data => {
            this.score.push(data['items']);
            if (data['has_more'] === true) {
                this.service.getPointsOfUser(331508);
            } else {
                this.service.pageNr = 1;
            }
        }, error => {
            console.log(error);
        })*/


        this.service.getPointsOfUser(331508).then(array => {
            let scores = array;
/*            for (let entry of scores) {
                this.totalScore += entry;
            }*/
            console.log(scores);
            // console.log(this.totalScore);
        });

    }


}
