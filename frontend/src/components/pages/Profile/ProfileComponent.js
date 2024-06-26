import {Alert, Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import React, {useEffect} from "react";
import {useAppContext} from "../../../modules/Context/ContextFactory";
import {constructApiPath, Models, Operations, Privileges} from "../../../modules/AppDataProvider/DataProviderPaths";
import {SpinnerComponent} from "../../ui/Spinner/SpinnerComponent";

export const ProfileComponent = () => {
    const securityManager = useAppContext().getSecurityManager();
    const dataProvider = useAppContext().getDataProvider();
    const notificationManager = useAppContext().getNotificationManager();

    const [error, setError] = React.useState('');

    const [dbDateOfBirth, setDbDateOfBirth] = React.useState('');
    const [dbBio, setDbBio] = React.useState('');
    const [dbPreferences, setDbPreferences] = React.useState('');

    const [formDateOfBirth, setFormDateOfBirth] = React.useState('');
    const [formBio, setFormBio] = React.useState('');
    const [formPreferences, setFormPreferences] = React.useState('');

    const [loading, setLoading] = React.useState(true);
    const [detectChanges, setDetectChanges] = React.useState(false);

    useEffect( () =>
    async function fetchData() {
        const endpoint = `${constructApiPath(Models.userspecifics, Operations.get, Privileges.core)}`;
        try {
            const {dateOfBirth, bio, preferences} = await dataProvider.postData(endpoint);
            if (dateOfBirth) {
                setDbDateOfBirth(dateOfBirth.split(' ')[0]);
                setFormDateOfBirth(dateOfBirth.split(' ')[0]);
            }
            setDbBio(bio);
            setDbPreferences(preferences);
            setFormBio(bio);
            setFormPreferences(preferences);
            setError('');
        } catch (e) {
            setError(e.message.data?.error ?? e.message);
        }
        setLoading(false)
    }, [dataProvider]);

    if (loading) {
        return <SpinnerComponent />;
    }

    const handleDateOfBirthChange = (e) => {
        setFormDateOfBirth(e.target.value);
        if (e.target.value !== dbDateOfBirth) {
            setDetectChanges(true);
            return;
        }
        setDetectChanges(false);
    }

    const handleBioChange = (e) => {
        const valueFormatted = e.target.value.trim().length > 0 ? e.target.value : '';
        setFormBio(valueFormatted);
        if (valueFormatted === '' && dbBio === null) {
            setDetectChanges(false);
            return;
        }
        if (valueFormatted !== dbBio) {
            setDetectChanges(true);
            return;
        }
        setDetectChanges(false);
    }

    const handlePreferencesChange = (e) => {
        const valueFormatted = e.target.value.trim().length > 0 ? e.target.value : '';
        setFormPreferences(valueFormatted);
        if (valueFormatted === '' && dbPreferences === null) {
            setDetectChanges(false);
            return;
        }
        if (valueFormatted !== dbPreferences) {
            setDetectChanges(true);
            return;
        }
        setDetectChanges(false);
    }

    const handleSubmit = async () => {
        const endpoint = constructApiPath(Models.userspecifics, Operations.update, Privileges.core);
        const data = {
            dateOfBirth: formDateOfBirth + ' 00:00:00',
            bio: formBio,
            preferences: formPreferences
        }
        console.log(data);
        try {
            await dataProvider.postData(endpoint, data);
            setError('');
            setDbPreferences(formPreferences);
            setDbBio(formBio);
            setDbDateOfBirth(formDateOfBirth);
            setDetectChanges(false);
            notificationManager.showNotification('User specifics updated');
        } catch (e) {
            notificationManager.showNotification('User specifics update failed');
            setError(e.message.data.error ?? e.message);
        }
    }

    return (
        <Container className="mt-5">
            <h2>User specifics for {securityManager.getUsername()}</h2>
            {error && <Alert color="danger">{error}</Alert>}
            <FormGroup className="mb-3">
                <Label>Birthday</Label>
                <Input
                    type="date"
                    placeholder="Enter birthday"
                    value={formDateOfBirth}
                    onChange={handleDateOfBirthChange}
                />
            </FormGroup>

            <FormGroup className="mb-3" >
                <Label>Bio</Label>
                <Input
                    type="text"
                    placeholder="Enter bio"
                    value={formBio}
                    onChange={handleBioChange}
                />
            </FormGroup>

            <FormGroup className="mb-3" >
                <Label>Preferences</Label>
                <Input
                    type="text"
                    placeholder="Enter preferences"
                    value={formPreferences}
                    onChange={handlePreferencesChange}
                />
            </FormGroup>

            <Button color="primary" type="submit" disabled={!detectChanges} onClick={handleSubmit}>
                Update
            </Button>
        </Container>
    )
};