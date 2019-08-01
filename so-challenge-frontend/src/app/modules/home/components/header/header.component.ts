import {Component} from '@angular/core';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent {
    navLinks = [
        {
            label: 'Challenges',
            link: './challenges',
            index: 0
        }, {
            label: 'Teilnehmer',
            link: './participants',
            index: 1
        }, {
            label: 'Tags',
            link: './tags',
            index: 2
        }
    ];


}
