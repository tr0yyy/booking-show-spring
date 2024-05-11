import React from 'react';
import AppDataProvider from "../AppDataProvider/AppDataProvider";
import {Context} from "./Context";
import SecurityManager from "../SecurityManager/SecurityManager";

const ContextFactory = React.createContext(null);

// sharing data provider using dependency injection
export function ContextFactoryGenerator({children, baseUrl}) {
    const context = new Context(
        new AppDataProvider(baseUrl),
        new SecurityManager()
    );
    return (
        <ContextFactory.Provider value={context}>
            {children}
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