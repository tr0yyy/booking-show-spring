import axios from 'axios';
import Cookies from "js-cookie";
import {Privileges} from "./DataProviderPaths";

export default class AppDataProvider {
    constructor(baseUrl) {
        this.client = axios.create({
            baseURL: baseUrl,
            json: true
        })
    }

    _authenticatedEndpoints = [Privileges.admin, Privileges.core]
        .map(privilege => `/${privilege}/`);

    /**
     * Fetch data from the Backend API
     * The output from Backend
     * {
     *     "success": true/false
     *     "data": {}/null if success false
     *     "error": ""/"error message if success false"
     * }
     * @param endpoint
     * @returns {Promise<axios.AxiosResponse<any>>} If success, returns the data object from the response, else throws an error
     */
    async fetchData(endpoint): Promise<any> {
        this._handleBearerToken(endpoint);
        return await this.client.get(endpoint)
            .then(response => this.processResponse(response))
            .catch(error => {
                throw error;
            });
    }

    /**
     * Send data to the Backend API
     * The output from Backend
     * {
     *     "success": true/false
     *     "data": {}/null if success false
     *     "error": ""/"error message if success false"
     * }
     * @param endpoint
     * @param data
     * @returns {Promise<axios.AxiosResponse<any>>} If success, returns the data object from the response, else throws an error
     */
    async postData(endpoint, data): Promise<any> {
        this._handleBearerToken(endpoint);
        return await this.client.post(endpoint, data)
            .then(response => this.processResponse(response))
            .catch(error => {
                throw error;
            });
    }

    processResponse(response) {
        const data = response.data;
        if (data.success === false) {
            throw new Error(data.error);
        }
        return data.data;
    }

    _handleBearerToken(endpoint: string) {
        const possibleEndpoint = this._authenticatedEndpoints.filter(secureEndpoint => endpoint.startsWith(secureEndpoint));
        if (possibleEndpoint.length > 0) {
            const token = Cookies.get("token");
            if (token) {
                this.client.defaults.headers.common["Authorization"] = `Bearer ${token}`;
            }
        } else {
            delete this.client.defaults.headers.common["Authorization"];
        }
    }
}