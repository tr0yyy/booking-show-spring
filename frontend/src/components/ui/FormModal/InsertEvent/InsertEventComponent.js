import {Alert, FormGroup, Input, Label} from "reactstrap";
import React from "react";

export const InsertEventComponent = ({ formData, onFormChange, error }) => {
    const handleChange = (event) => {
        const { name, value } = event.target;
        onFormChange({
            ...formData,
            [name]: value
        });
    };

    console.log('Insert event ready');
    console.log(formData);


    return (
        <div>
            {error && <Alert color="danger">{error}</Alert>}
            <FormGroup>
                <Label for="title">Event title</Label>
                <Input
                    type="text"
                    id="title"
                    value={formData.title || ''}
                    onChange={handleChange}
                    name="title"
                    placeholder="Enter title"
                    required={true}
                />
            </FormGroup>
            <FormGroup>
                <Label for="dateTime">Date</Label>
                <Input
                    type="datetime-local"
                    id="dateTime"
                    value={formData.dateTime || ''}
                    onChange={handleChange}
                    name="dateTime"
                    placeholder="Enter dateTime"
                    required={true}
                />
            </FormGroup>
            <FormGroup>
                <Label for="description">Description</Label>
                <Input
                    type="text"
                    id="description"
                    value={formData.description || ''}
                    onChange={handleChange}
                    name="description"
                    placeholder="Enter description"
                    required={true}
                />
            </FormGroup>
            <FormGroup>
                <Label for="artistId">Artist ID</Label>
                <Input
                    type="number"
                    id="artistId"
                    value={formData.artistId || ''}
                    onChange={handleChange}
                    name="artistId"
                    placeholder="Enter artist Id"
                    required={true}
                />
            </FormGroup>
            <FormGroup>
                <Label for="locationId">Location ID</Label>
                <Input
                    type="number"
                    id="locationId"
                    value={formData.locationId || ''}
                    onChange={handleChange}
                    name="locationId"
                    placeholder="Enter location Id"
                    required={true}
                />
            </FormGroup>
            <FormGroup>
                <Label for="ticketPrice">Ticket price</Label>
                <Input
                    type="number"
                    id="ticketPrice"
                    value={formData.ticketPrice || ''}
                    onChange={handleChange}
                    name="ticketPrice"
                    placeholder="Enter ticket price"
                    required={true}
                />
            </FormGroup>
        </div>
    )
}