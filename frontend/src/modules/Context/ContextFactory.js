import React, {useState} from 'react';
import AppDataProvider from "../AppDataProvider/AppDataProvider";
import {Context} from "./Context";
import SecurityManager from "../SecurityManager/SecurityManager";
import NotificationManager from "../NotificationManager/NotificationManager";

const ContextFactory = React.createContext(null);

// sharing data provider using dependency injection
export function ContextFactoryGenerator({children, baseUrl}) {
    const [notification, setNotification] = useState({ isVisible: false, message: '' });

    const context = new Context(
        new AppDataProvider(baseUrl),
        new SecurityManager(),
        new NotificationManager(setNotification)
    );

    return (
        <ContextFactory.Provider value={context}>
            {children}
            {notification.isVisible && context.getNotificationManager().NotificationComponent(notification.message)}
        </ContextFactory.Provider>
    )
}

/**
 *
 * @returns Context
 */
export function useAppContext(): Context {
    const context = React.useContext(ContextFactory);
    if (context === null) {
        throw new Error('useDataProvider must be used within a DataProvider');
    }
    return context;
}