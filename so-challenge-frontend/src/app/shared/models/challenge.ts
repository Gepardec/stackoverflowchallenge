import {Participant} from './participant';
import {Status} from "./status";
import {Tag} from "./tag";

export interface Challenge {
    id: number;
    title: string
    description: string;
    fromDate: Date;
    toDate: Date;
    status: Status;
    tagSet: Tag[];
    award1: string;
    award2: string;
    award3: string;
    participantSet: Participant[];
}
