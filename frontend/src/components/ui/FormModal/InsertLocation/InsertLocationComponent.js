import {Alert, FormGroup, Input, Label} from "reactstrap";
import React from "react";

export const InsertLocationComponent = ({ formData, onFormChange, error }) => {
    const handleChange = (event) => {
        const { name, value } = event.target;
        onFormChange({
            ...formData,
            [name]: value
        });
    };

    console.log('Insert location ready');
    console.log(formData);

    return (
        <div>
            {error && <Alert color="danger">{error}</Alert>}
            <FormGroup>
                <Label for="name">Name</Label>
                <Input
                    type="text"
                    id="name"
                    value={formData.name || ''}
                    onChange={handleChange}
                    name="name"
                    placeholder="Enter location name"
                    required={true}
                />
            </FormGroup>
            <FormGroup>
                <Label for="address">Address</Label>
                <Input
                    type="text"
                    id="address"
                    value={formData.address || ''}
                    onChange={handleChange}
                    name="address"
                    placeholder="Enter address"
                    required={true}
                />
            </FormGroup>
            <FormGroup>
                <Label for="availableRows">Available rows</Label>
                <Input
                    type="number"
                    id="availableRows"
                    value={formData.availableRows || ''}
                    onChange={handleChange}
                    name="availableRows"
                    placeholder="Enter available rows"
                    required={true}
                />
            </FormGroup>
            <FormGroup>
                <Label for="availableSeatsPerRow">Available seats per row</Label>
                <Input
                    type="number"
                    id="availableSeatsPerRow"
                    value={formData.availableSeatsPerRow || ''}
                    onChange={handleChange}
                    name="availableSeatsPerRow"
                    placeholder="Enter available seats per row"
                    required={true}
                />
            </FormGroup>
        </div>
    )
}