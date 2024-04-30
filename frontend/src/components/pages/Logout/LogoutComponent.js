import {Navigate, useNavigate} from "react-router-dom";
import {useAppContext} from "../../../modules/Context/ContextFactory";

export const LogoutComponent = () => {
    const navigate = useNavigate();
    const securityManager = useAppContext().getSecurityManager();

    securityManager.clearToken();

    navigate('/login', {replace: true});
    window.location.reload();

    return <Navigate to={'/login'} replace/>;
}