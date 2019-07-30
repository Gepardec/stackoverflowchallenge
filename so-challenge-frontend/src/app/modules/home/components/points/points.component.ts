import {Component, OnInit, ViewChild} from '@angular/core';
import {EndpointService} from "../../../../shared/services/endpoint.service";

@Component({
    selector: 'app-points',
    templateUrl: './points.component.html',
    styleUrls: ['./points.component.css']
})
export class PointsComponent implements OnInit {

    chartOptions = {
        responsive: true
    };

    chartData = [
        { data: [330, 600, 260, 700], label: 'Account A' },
        { data: [120, 455, 100, 340], label: 'Account B' },
        { data: [45, 67, 800, 500], label: 'Account C' }
    ];

    chartLabels = ['January', "February", "March", "April"]

    onChartClick(event) {
        console.log(event);
    }




    result: object[];
    score: number[] = [];
    totalScore: number = 0;
    errorMessage: boolean;

    constructor(private service: EndpointService) {
    }


    ngOnInit() {
        /*this.chart = new Chart(this.chartRef.nativeElement, {
            type: 'line',
            data: {
                labels: ["Montag", "Dienstag", "Mittwoch", "Donnerstag"], // your labels array
                datasets: [
                    {
                        data: [1, 2, 3, 4, 5], // your data array
                        borderColor: '#00AEFF',
                        fill: false
                    }
                ]
            },
            options: {
                legend: {
                    display: false
                },
                scales: {
                    xAxes: [{
                        display: true
                    }],
                    yAxes: [{
                        display: true
                    }],
                }
            }
        });*/




        /*this.service.getPointsOfUser(331508).then(answers => {
            let sum = 0;
            answers.forEach(answer => {
                sum += answer.score;
            });
            var elem = document.getElementById('2d').getconte
            var element = document.getElementById('lineChart');
            var context = element.getContext('2d');
            var lineChart = new Chart(context, {

            });



            let scores = answers;
            /!*            for (let entry of scores) {
                            this.totalScore += entry;
                        }*!/
            console.log(scores);
            // console.log(this.totalScore);
        });
*/
    }


}
