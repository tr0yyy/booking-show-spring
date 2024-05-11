import {Container} from "reactstrap";

export const SpinnerComponent = () => {
    return (
        <Container fluid>
            <div className="spinner-border" role="status">
                <span className="visually-hidden">Loading...</span>
            </div>
        </Container>
    );
}