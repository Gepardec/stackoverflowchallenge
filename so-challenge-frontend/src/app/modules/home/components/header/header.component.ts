import {Component} from '@angular/core';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent {
    navLinks = [
        {
            label: 'challenges',
            link: './challenges',
            index: 0
        }, {
            label: 'participants',
            link: './participants',
            index: 1
        }, {
            label: 'tags',
            link: './tags',
            index: 2
        }
    ];


}
