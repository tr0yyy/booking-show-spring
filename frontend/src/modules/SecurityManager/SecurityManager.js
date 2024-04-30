import Cookies from "js-cookie";
import {decodeToken, isExpired} from "react-jwt";
import {ReactElement} from "react";
import {Navigate} from "react-router-dom";

const Roles = {
    ADMIN: 'ROLE_ADMIN',
    USER: 'ROLE_USER'
}

export default class SecurityManager {
    saveBearerToken(token) {
        const decodedToken = decodeToken(token);
        Cookies.set('token', token, {expires: decodedToken.exp});
    }

    isAuthenticated() {
        if (Cookies.get('token') !== undefined && isExpired(Cookies.get('token'))) {
            this.clearToken();
        }
        return Cookies.get('token') !== undefined;
    }

    isAdmin() {
        const token = Cookies.get('token');
        if (token === undefined || isExpired(token)) {
            return false;
        }
        const decodedToken = decodeToken(token);
        return decodedToken.role === Roles.ADMIN;
    }

    getUserId() {
        const token = Cookies.get('token');
        if (token === undefined || isExpired(token)) {
            return null;
        }
        const decodedToken = decodeToken(token);
        return decodedToken.userId;
    }

    clearToken() {
        Cookies.remove("token");
    }

    requireAuth(reactComponent: ReactElement): ReactElement {
        if (!this.isAuthenticated()) {
            console.log('Not authenticated');
            return <Navigate to={'/login'} replace/>;
        }
        return reactComponent;
    }

    redirectAuthenticated(reactComponent: ReactElement): ReactElement {
        if (this.isAuthenticated()) {
            console.log('Authenticated');
            return <Navigate to={'/'} replace/>;
        }
        return reactComponent;
    }

    requireAdmin(reactComponent: ReactElement): ReactElement {
        if (!this.isAdmin()) {
            console.log('Not admin');
            return <Navigate to={'/'} replace/>;
        }
        return reactComponent;
    }
}