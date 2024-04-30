import {Alert, FormGroup, Input, Label} from "reactstrap";
import React from "react";

export const InsertArtistsComponent = ({ formData, onFormChange, error }) => {
    const handleChange = (event) => {
        const { name, value } = event.target;
        onFormChange({
            ...formData,
            [name]: value
        });
    };

    console.log('Insert artists ready');
    console.log(formData);


    return (
        <div>
            {error && <Alert color="danger">{error}</Alert>}
            <FormGroup>
                <Label for="name">Artist Name</Label>
                <Input
                    type="text"
                    id="name"
                    value={formData.name || ''}
                    onChange={handleChange}
                    name="name"
                    placeholder="Enter artist name"
                    required={true}
                />
            </FormGroup>
            <FormGroup>
                <Label for="name">Bio</Label>
                <Input
                    type="text"
                    id="bio"
                    value={formData.bio || ''}
                    onChange={handleChange}
                    name="bio"
                    placeholder="Enter bio"
                    required={true}
                />
            </FormGroup>
        </div>
    )
}