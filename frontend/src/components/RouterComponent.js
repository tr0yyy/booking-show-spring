import {BrowserRouter, Route, Routes} from "react-router-dom";
import {HomeComponent} from "./pages/Home/HomeComponent";
import {NavBarComponent} from "./ui/Navbar/NavBarComponent";
import {LoginComponent} from "./pages/Login/LoginComponent";
import {useAppContext} from "../modules/Context/ContextFactory";
import {LogoutComponent} from "./pages/Logout/LogoutComponent";
import {RegisterComponent} from "./pages/Register/RegisterComponent";
import {AdminPanelComponent} from "./pages/AdminPanel/AdminPanelComponent";
import {ProfileComponent} from "./pages/Profile/ProfileComponent";

// all the paths are defined in this file
export const RouterComponent = () => {
    const securityManager = useAppContext().getSecurityManager();

    return (
        <BrowserRouter>
            <div>
                <NavBarComponent/>
                <Routes>
                    <Route path='' exact={true} element={securityManager.requireAuth(<HomeComponent/>)}/>
                    <Route path="/" exact={true} element={securityManager.requireAuth(<HomeComponent/>)}/>
                    <Route path='/login' exact={true} element={securityManager.redirectAuthenticated(<LoginComponent/>)} />
                    <Route path='/register' exact={true} element={securityManager.redirectAuthenticated(<RegisterComponent/>)} />
                    <Route path='/admin' exact={true} element={securityManager.requireAdmin(<AdminPanelComponent/>)} />
                    <Route path="/logout" exact={true} element={securityManager.requireAuth(<LogoutComponent/>)}/>
                    <Route path="/profile" exact={true} element={securityManager.requireAuth(<ProfileComponent/>)}/>
                </Routes>
            </div>
        </BrowserRouter>
    )
}