import {Participant} from './participant';
import {State} from './state';
import {Tag} from './tag';

export interface Challenge {
    id: number;
    title: string;
    description: string;
    fromDate: Date;
    toDate: Date;
    state: State;
    tagSet: Tag[];
    award1: string;
    award2: string;
    award3: string;
    participantSet: Participant[];
}
