import {useState} from "react";
import {Alert, Button, Col, Container, Form, FormGroup, Input, Label, Row} from "reactstrap";
import {useAppContext} from "../../../modules/Context/ContextFactory";
import {useNavigate} from "react-router-dom";
import {constructApiPath, Models, Operations, Privileges} from "../../../modules/AppDataProvider/DataProviderPaths";


export const LoginComponent = () => {
    const dataProvider = useAppContext().getDataProvider();
    const securityManager = useAppContext().getSecurityManager();
    const navigate = useNavigate();

    if (securityManager.isAuthenticated()) {
        navigate('/');
    }

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleUsernameChange = (e) => {
        setUsername(e.target.value);
    };

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!username || !password) {
            setError('Both fields are required');
            return;
        }
        const endpoint = constructApiPath(Models.auth, Operations.login, Privileges.none);
        try {
            const result = await dataProvider.postData(endpoint, {username, password});
            console.log(result);
            securityManager.saveBearerToken(result.bearerToken);
            setError('');
            window.location.reload();
        } catch (e) {
            const message = e.response.data.error;
            setError(message);
        }

    };

    return (
        <Container className="mt-5">
            <Row className="justify-content-md-center">
                <Col xs={12} md={6}>
                    <h2>Login</h2>
                    {error && <Alert color="danger">{error}</Alert>}
                    <Form onSubmit={handleSubmit}>
                        <FormGroup className="mb-3" id="formBasicEmail">
                            <Label>Username</Label>
                            <Input
                                type="text"
                                placeholder="Enter username"
                                value={username}
                                onChange={handleUsernameChange}
                            />
                        </FormGroup>

                        <FormGroup className="mb-3" id="formBasicPassword">
                            <Label>Password</Label>
                            <Input
                                type="password"
                                placeholder="Password"
                                value={password}
                                onChange={handlePasswordChange}
                            />
                        </FormGroup>

                        <Button variant="primary" type="submit">
                            Login
                        </Button>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
};