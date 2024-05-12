import React from 'react';
import { Alert } from 'reactstrap';

export default class NotificationManager {
    constructor(setNotification) {
        this.setNotification = setNotification;
    }

    showNotification (message) {
        this.setNotification({isVisible: true, message});
        setTimeout(() => {
            this.setNotification({isVisible: false, message: ''});
        }, 5000);
    }

    NotificationComponent = (message) => {
        return (
            <Alert color="success" style={{ position: 'fixed', bottom: '20px', right: '20px', zIndex: 1000 }}>
            {message}
        </Alert>
        )
    }
}
