import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';

import {HeaderComponent} from './components/header/header.component';
import {ParticipantComponent} from './components/participant/participant.component';
import {ChallengeComponent} from './components/challenge/challenge.component';
import {PointsComponent} from './components/points/points.component';

import {ChartsModule} from "ng2-charts";


@NgModule({
    declarations: [
        HeaderComponent,
        ParticipantComponent,
        ChallengeComponent,
        PointsComponent
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        ChartsModule,
        RouterModule.forRoot(
            [
                {path: 'challenges', component: ChallengeComponent},
                {path: 'participants', component: ParticipantComponent},
                {path: 'participantInfo', component: PointsComponent},
                {path: '', redirectTo: '/participants', pathMatch: 'full'},
                {path: '**', component: ChallengeComponent}
            ]
        )
    ],
    providers: [],
    bootstrap: [HeaderComponent]
})
export class HomeModule {
}
