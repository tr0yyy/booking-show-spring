import {useEffect, useState} from "react";
import {Button} from "reactstrap";
import FormModalComponent from "../FormModal/FormModalComponent";
import {ArchiveTicketsComponent} from "../FormModal/ArchiveTickets/ArchiveTicketsComponent";
import {InsertArtistsComponent} from "../FormModal/InsertArtists/InsertArtistsComponent";
import {constructApiPath, Models, Operations, Privileges} from "../../../modules/AppDataProvider/DataProviderPaths";
import {useAppContext} from "../../../modules/Context/ContextFactory";
import {InsertLocationComponent} from "../FormModal/InsertLocation/InsertLocationComponent";
import {InsertEventComponent} from "../FormModal/InsertEvent/InsertEventComponent";

const GridButtonsComponent = () => {
    const dataProvider = useAppContext().getDataProvider();
    const notificationManager = useAppContext().getNotificationManager();

  const [showModal, setShowModal] = useState(false);
  const [activeForm, setActiveForm] = useState('');
  const [formData, setFormData] = useState({});
  const [error, setError] = useState('');

  useEffect(() => {
      // This useEffect can react to formData changes and do something, like validations or autosave
      console.log(formData);
  }, [formData]);

  const handleFormChange = (data) => {
      Object.entries(data).forEach(([key, value]) => {
          if (typeof value === 'string') {
              data[key] = value.trim().length === 0 ? '' : value;
          }
      });
      setFormData(data);
  };

  const handleSubmit = async () => {
      console.log('Submitting form data:', formData);
      let endpoint = ''
      switch (activeForm) {
          case 'archiveTickets':
              endpoint = constructApiPath(Models.ticket, Operations.archive, Privileges.admin);
              break;
          case 'insertEvent':
                if (!formData.title || !formData.dateTime || !formData.description || !formData.artistId || !formData.locationId || !formData.ticketPrice) {
                    setError('All fields are required');
                    return;
                }
                formData.artistIds = [formData.artistId];
                formData.dateTime = formData.dateTime.replace('T', ' ') + ':00';
              endpoint = constructApiPath(Models.event, Operations.import, Privileges.admin);
              break;
          case 'insertLocation':
                if (!formData.name || !formData.address || !formData.availableRows || !formData.availableSeatsPerRow) {
                    setError('All fields are required');
                    return;
                }
              endpoint = constructApiPath(Models.location, Operations.import, Privileges.admin);
              break;
          case 'insertArtist':
              if (!formData.name || !formData.bio) {
                  setError('Name and Bio are required fields');
                  return;
              }
              endpoint = constructApiPath(Models.artist, Operations.import, Privileges.admin);
              break;
          default:
              break;
      }
      try {
          console.log('Making request');
          await dataProvider.postData(endpoint, formData);
          setError('');
          notificationManager.showNotification('Form submitted successfully');
      } catch (e) {
          console.log(e);
          setError(e.response.data.error);
          return;
      }
      closeModal();
  };

  const formContentMapping = {
      archiveTickets: <ArchiveTicketsComponent/>,
      insertArtist: <InsertArtistsComponent onFormChange={handleFormChange} formData={formData} error={error} />,
      insertLocation: <InsertLocationComponent onFormChange={handleFormChange} formData={formData} error={error} />,
      insertEvent: <InsertEventComponent onFormChange={handleFormChange} formData={formData} error={error} />
  }

  const openModal = (formType) => {
      setError('');
    setActiveForm(formType);
    switch (formType) {
      case 'insertEvent':
        setFormData({
            title: '',
            dateTime: new Date(),
            description: '',
            artistId: 0,
            locationId: 0,
            ticketPrice: 0
        });
        break;
      case 'insertLocation':
        setFormData({
            name: '',
            address: '',
            availableRows: 0,
            availableSeatsPerRow: 0
        });
        break;
      case 'insertArtist':
        setFormData({
            name: '',
            bio: ''
        });
        break;
      default:
        setFormData({});
        break;
    }
    console.log(formData);
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
  };

  return (
    <div className="flex flex-wrap gap-2">
        <Button onClick={() => openModal('archiveTickets')} style={{margin: '10px 10px'}}>Archive Tickets</Button>
        <Button onClick={() => openModal('insertEvent')} style={{margin: '10px 10px'}}>Insert Event</Button>
        <Button onClick={() => openModal('insertLocation')} style={{margin: '10px 10px'}}>Insert Location</Button>
        <Button onClick={() => openModal('insertArtist')} style={{margin: '10px 10px'}}>Insert Artist</Button>
      <FormModalComponent
          show={showModal}
          handleSubmit={handleSubmit}
          handleClose={closeModal}
          formType={activeForm}
          FormContent={formContentMapping[activeForm]} />
    </div>
  );
};

export default GridButtonsComponent;