import AppDataProvider from "../AppDataProvider/AppDataProvider";
import SecurityManager from "../SecurityManager/SecurityManager";

export class Context {
    constructor(dataProvider: AppDataProvider, securityManager: SecurityManager) {
        this._dataProvider = dataProvider;
        this._securityManager = securityManager;
    }

    getDataProvider(): AppDataProvider {
        return this._dataProvider;
    }

    getSecurityManager(): SecurityManager {
        return this._securityManager;
    }
}