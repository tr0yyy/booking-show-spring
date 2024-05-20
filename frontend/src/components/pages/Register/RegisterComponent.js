import {useState} from "react";
import {Alert, Button, Col, Container, Form, FormGroup, Input, Label, Row} from "reactstrap";
import {useAppContext} from "../../../modules/Context/ContextFactory";
import {useNavigate} from "react-router-dom";
import {constructApiPath, Models, Operations, Privileges} from "../../../modules/AppDataProvider/DataProviderPaths";


export const RegisterComponent = () => {
    const dataProvider = useAppContext().getDataProvider();
    const navigate = useNavigate();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState('');

    const handleUsernameChange = (e) => {
        setUsername(e.target.value);
    };

    const handleEmailChange = (e) => {
        setEmail(e.target.value);
    };

    const handlePasswordChange = (e) => {
        if (e.target.value !== confirmPassword) {
            setError('Passwords do not match');
        } else {
            setError('');
        }
        setPassword(e.target.value);
    };

    const handleConfirmPasswordChange = (e) => {
        if (e.target.value !== password) {
            setError('Passwords do not match');
        } else {
            setError('');
        }
        setConfirmPassword(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!username || !password || !confirmPassword || !email) {
            setError('All fields are required');
            return;
        }
        if (error) {
            return;
        }
        const endpoint = constructApiPath(Models.auth, Operations.register, Privileges.none)
        try {
            const result = await dataProvider.postData(endpoint, {username, password, email});
            console.log(result);
            navigate('/login');
            setError('');
        } catch (e) {
            console.log(e)
            setError(e.response.data?.error ?? e.message)
        }
    };

    return (
        <Container className="mt-5">
            <Row className="justify-content-md-center">
                <Col xs={12} md={6}>
                    <h2>Register</h2>
                    {error && <Alert variant="danger">{error}</Alert>}
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

                        <FormGroup className="mb-3" id="formBasicEmail">
                            <Label>Email</Label>
                            <Input
                                type="text"
                                placeholder="Enter email"
                                value={email}
                                onChange={handleEmailChange}
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

                        <FormGroup className="mb-3" id="formBasicPassword">
                            <Label>Confirm Password</Label>
                            <Input
                                type="password"
                                placeholder="Password"
                                value={confirmPassword}
                                onChange={handleConfirmPasswordChange}
                            />
                        </FormGroup>

                        <Button variant="primary" type="submit">
                            Register
                        </Button>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
};