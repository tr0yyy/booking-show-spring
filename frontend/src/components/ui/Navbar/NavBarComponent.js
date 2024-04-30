import {Navbar, NavbarBrand, NavItem} from "reactstrap";
import {Link} from "react-router-dom";
import {useAppContext} from "../../../modules/Context/ContextFactory";

export const NavBarComponent = () => {
    const isLoggedIn = useAppContext().getSecurityManager().isAuthenticated();
    const isAdmin = useAppContext().getSecurityManager().isAdmin();

    return <Navbar color="dark" dark expand="md" className="navbar-custom">
        <div className="nav-item-group left">
            <div>
                <NavItem className="nav-item">
                    <NavbarBrand tag={Link} to="/">Home</NavbarBrand>
                    {
                        isLoggedIn && (
                            <NavbarBrand tag={Link} to="/profile">Profile</NavbarBrand>
                        )
                    }
                </NavItem>
            </div>
        </div>
        <div className="nav-item-group">
            <NavItem className="nav-item">
                <NavbarBrand>Booking Show Platform</NavbarBrand>
            </NavItem>
        </div>
        <div className="nav-item-group right">
            <div>
                {
                    !isLoggedIn && (<NavItem className="nav-item">
                            <NavbarBrand tag={Link} to="/login">Login</NavbarBrand>
                            <NavbarBrand tag={Link} to="/register">Register</NavbarBrand>
                        </NavItem>
                    )
                }
                {
                    isLoggedIn && (<NavItem className="nav-item">
                            {
                                isAdmin && (
                                    <NavbarBrand tag={Link} to="/admin">Admin Panel</NavbarBrand>
                                )
                            }
                            <NavbarBrand tag={Link} to="/logout">Logout</NavbarBrand>
                        </NavItem>
                    )
                }
            </div>
        </div>
    </Navbar>;
}