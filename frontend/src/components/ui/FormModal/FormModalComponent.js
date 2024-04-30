import React from 'react';
import {Modal, Button, Form, FormGroup, Label, Input, ModalHeader, ModalBody, Alert, ModalFooter} from 'reactstrap';

const FormModalComponent = ({ show, handleSubmit, handleClose, formType, FormContent = null }) => {
    const formTitleSplit = formType.replace(/([A-Z])/g, ' $1').trim()
    const formTitle = formTitleSplit.charAt(0).toUpperCase() + formTitleSplit.slice(1);

    if (!show) {
        return null;
    }

    console.log('Entered form modal')
    console.log(FormContent)


    return (
        <Modal isOpen={show} onAbort={handleClose}>
            <ModalHeader onClick={handleClose}>{formTitle}</ModalHeader>
            <ModalBody>
                <div>{FormContent}</div>
            </ModalBody>
            <ModalFooter>
                <Button color="primary" onClick={handleSubmit}>
                    Submit
                </Button>{' '}
                <Button color="secondary" onClick={handleClose}>
                    Cancel
                </Button>
            </ModalFooter>
        </Modal>
    );
};

export default FormModalComponent;