import AppDataProvider from "../AppDataProvider/AppDataProvider";
import SecurityManager from "../SecurityManager/SecurityManager";
import type NotificationManager from "../NotificationManager/NotificationManager";

export class Context {
    constructor(dataProvider: AppDataProvider, securityManager: SecurityManager, notificationManager: NotificationManager) {
        this._dataProvider = dataProvider;
        this._securityManager = securityManager;
        this._notificationManager = notificationManager;
    }

    getDataProvider(): AppDataProvider {
        return this._dataProvider;
    }

    getSecurityManager(): SecurityManager {
        return this._securityManager;
    }

    getNotificationManager(): NotificationManager {
        return this._notificationManager;
    }
}