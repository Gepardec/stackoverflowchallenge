import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';

import {HeaderComponent} from './components/header/header.component';
import {ParticipantComponent} from './components/participant/participant.component';
import {ChallengeComponent} from './components/challenge/challenge.component';
import {ChallengeDetailComponent} from './components/challenge-detail/challenge-detail.component';

import {FormsModule} from '@angular/forms';
import {
    MatTableModule,
    MatTabsModule,
    MatButtonModule,
    MatIconModule,
    MatTooltipModule,
    MatFormFieldModule, MatInputModule, MatDatepickerModule, MatNativeDateModule, MatSelectModule
} from "@angular/material";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';



@NgModule({
    declarations: [
        HeaderComponent,
        ParticipantComponent,
        ChallengeComponent,
        ChallengeDetailComponent
    ],
    imports: [
        MatTooltipModule,
        MatButtonModule,
        BrowserAnimationsModule,
        FormsModule,
        BrowserModule,
        HttpClientModule,
        RouterModule.forRoot(
            [
                {path: 'challenges', component: ChallengeComponent},
                {path: 'participants', component: ParticipantComponent},
                {path: '', redirectTo: '/challenges', pathMatch: 'full'},
                {path: '**', component: ChallengeComponent}
            ]
        ),
        MatTabsModule,
        MatTableModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatSelectModule
    ],
    providers: [MatDatepickerModule],
    bootstrap: [HeaderComponent]
})
export class HomeModule {
}
