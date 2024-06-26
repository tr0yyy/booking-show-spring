import {useAppContext} from "../../../modules/Context/ContextFactory";
import React, {useEffect, useState} from "react";
import {SpinnerComponent} from "../../ui/Spinner/SpinnerComponent";
import {constructApiPath, Models, Operations, Privileges} from "../../../modules/AppDataProvider/DataProviderPaths";
import {Alert, Button, Form, Input, Tooltip} from "reactstrap";
import {
    searchInArrayByKeyAndGetField
} from "../../../utils/Utils";

const Modal = ({ isOpen, onClose, event }) => {
    const dataProvider = useAppContext().getDataProvider();
    const [seatRow, setSeatRow] = useState('');
    const [seatNumber, setSeatNumber] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log("Ordering ticket for:", event.title, "Seat Row:", seatRow, "Seat Number:", seatNumber);
        const endpoint = constructApiPath(Models.ticket, Operations.order, Privileges.core);
        try {
            await dataProvider.postData(
                endpoint,
                {
                    eventId: event.eventId,
                    seatRow: seatRow,
                    seatNumber: seatNumber
                }
            );
            setError('');
            onClose();
        } catch (e) {
            const message = e.response.data.error;
            setError(message);
        }
    };

    if (!isOpen) return null;

    return (
        <div style={{ position: 'fixed', top: 0, left: 0, right: 0, bottom: 0, backgroundColor: 'rgba(0, 0, 0, 0.5)', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
            <div style={{ background: 'white', padding: 20, borderRadius: 5, width: '300px', textAlign: 'center' }}>
                <h2>Order Ticket</h2>
                {error && <Alert color="danger">{error}</Alert>}
                <Form onSubmit={handleSubmit}>
                    <Input type="text" placeholder="Seat Row" value={seatRow} onChange={(e) => setSeatRow(e.target.value)} required />
                    <Input type="text" placeholder="Seat Number" value={seatNumber} onChange={(e) => setSeatNumber(e.target.value)} required />
                    <Button color='primary' type="submit">Order Ticket</Button>
                </Form>
                <Button  onClick={onClose} style={{ marginTop: '10px' }}>Close</Button>
            </div>
        </div>
    );
};


export const HomeComponent = () => {
    const dataProvider = useAppContext().getDataProvider();
    const securityManager = useAppContext().getSecurityManager();
    const notificationManager = useAppContext().getNotificationManager();

    const [events, setEvents] = useState([]);
    const [locations, setLocations] = useState([]);
    const [, setArtists] = useState([]);
    const [tickets, setTickets] = useState([]);
    const [loading, setLoading] = useState(true);

    const [currentPage, setCurrentPage] = useState(1);
    const [eventsPerPage,] = useState(4);

    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [selectedEvent, setSelectedEvent] = useState(null);

    const [tooltipOpen, setTooltipOpen] = useState({});

    const toggleTooltip = (eventId) => {
        setTooltipOpen(prev => ({
            ...prev,
            [eventId]: !prev[eventId]
        }));
    };


    const [currentEvents, setCurrentEvents] = useState([]);

    const handleOpenModal = (event) => {
        setSelectedEvent(event);
        setModalIsOpen(true);
    };
    const handleCloseModal = () => {
        setModalIsOpen(false);
        setEvents([]);
        setLoading(true);
    };

    const indexOfLastEvent = currentPage * eventsPerPage;
    const indexOfFirstEvent = indexOfLastEvent - eventsPerPage;

    const paginate = (pageNumber) => setCurrentPage(pageNumber);

    const handleNext = () => {
        if (currentPage < Math.ceil(events.length / eventsPerPage)) {
            setCurrentPage(currentPage + 1);
        }
    };
    const handlePrevious = () => {
        if (currentPage > 1) {
            setCurrentPage(currentPage - 1);
        }
    };
    const setFirstPage = () => {
        setCurrentPage(1);
    };
    const setLastPage = () => {
        setCurrentPage(pageNumbers.length);
    };

    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(events.length / eventsPerPage); i++) {
        pageNumbers.push(i);
    }

    const shouldDisableOrderForEvent = (eventId) => {
        console.log(`Checking if order should be disabled for event: ${eventId}`)
        console.log(`Current userId: ${securityManager.getUserId()}`)
        if (tickets[eventId] === undefined || tickets[eventId].length === 0) {
            return false;
        }
        return tickets[eventId].find(ticket => ticket.userId === securityManager.getUserId()) !== undefined;
    };

    const [sortConfig, setSortConfig] = useState({ key: null, direction: 'ascending' });
    const requestSort = (key) => {
        console.log('requesting sort');
        let direction = 'ascending';
        if (sortConfig.key === key && sortConfig.direction === 'ascending') {
            direction = 'descending';
        }
        setSortConfig({ key, direction });
        sortArray(key, direction);
    };

    const sortArray = (key, direction) => {
        setCurrentEvents(currentEvents => {
            return currentEvents.sort((a, b) => {
                const aValue = key === 'locationName'
                    ? searchInArrayByKeyAndGetField(locations, 'locationId', a.locationId, 'name')
                    : key === 'artists'
                        ? a.artistDtos.map(artist => artist.name).join(',')
                        : a[key];
                const bValue = key === 'locationName'
                    ? searchInArrayByKeyAndGetField(locations, 'locationId', b.locationId, 'name')
                    : key === 'artists'
                        ? b.artistDtos.map(artist => artist.name).join(',')
                        : b[key];
                if (aValue < bValue) {
                    return direction === 'ascending' ? -1 : 1;
                }
                if (aValue > bValue) {
                    return direction === 'ascending' ? 1 : -1;
                }
                return 0;
            });
        });
    };

    useEffect(() => {
        async function fetchData() {
            try {
                const hateosEventsData = await dataProvider.fetchData(
                    '/core/event/get_all_events'
                );
                console.log(hateosEventsData)
                const fetchedEvents = await dataProvider.fetchData(
                    constructApiPath(Models.event, Operations.get, Privileges.core)
                );
                const fetchedLocations = await dataProvider.fetchData(
                    constructApiPath(Models.location, Operations.get, Privileges.core)
                );
                const fetchedTickets = await dataProvider.fetchData(
                    constructApiPath(Models.event, Operations.getTickets, Privileges.core)
                );
                const fetchedArtists = await dataProvider.fetchData(
                    constructApiPath(Models.event, Operations.getArtists, Privileges.core)
                );
                setEvents(hateosEventsData.content);
                setLocations(fetchedLocations);
                setTickets(fetchedTickets);
                setArtists(fetchedArtists);

                console.log(`Fetched artists: ${JSON.stringify(fetchedArtists)}`);
                console.log(`Fetched tickets: ${JSON.stringify(fetchedTickets)}`);

                setCurrentEvents(Object.values(fetchedEvents));

                notificationManager.showNotification('Data fetched successfully');

            } catch (error) {
                console.error(`Error occurred while fetching data ${error}`);
            } finally {
                setLoading(false);
            }
        }
        if (events.length === 0) {
            fetchData();
        }
    }, [events.length, dataProvider, notificationManager]);

    if (loading) {
        return (
            <SpinnerComponent/>
        );
    }

    return (
        <div style={{margin: '0 auto', maxWidth: '90%', padding: '20px'}}>
            <h1>Events List</h1>
            <table style={{width: '100%', borderCollapse: 'collapse', textAlign: 'center'}}>
                <thead>
                <tr>
                    <th onClick={() => requestSort('title')}>Title</th>
                    <th onClick={() => requestSort('dateTime')}>Date and Time</th>
                    <th onClick={() => requestSort('description')}>Description</th>
                    <th onClick={() => requestSort('locationName')}>Location Name</th>
                    <th onClick={() => requestSort('artists')}>Artists</th>
                    <th onClick={() => requestSort('ticketPrice')}>Ticket Price</th>
                    <th>Order</th>
                </tr>
                </thead>
                <tbody>
                {currentEvents.slice(indexOfFirstEvent, indexOfLastEvent).map((event) => (
                    <tr key={event.eventId} id={`event-${event.eventId}`}>
                        <td style={{"text-align":'center'}}>{event.title}</td>
                        <td style={{"text-align":'center'}}>{event.dateTime}</td>
                        <td style={{"text-align":'center'}}>{event.description}</td>
                        <td style={{"text-align":'center'}}>{searchInArrayByKeyAndGetField(locations, 'locationId', event.locationId, 'name')}</td>
                        <td style={{"text-align":'center'}}>{event.artistDtos.map(artist => artist.name).join(',')}</td>
                        <td style={{"text-align":'center'}}>${event.ticketPrice}</td>
                        <td id={`btn-event-${event.eventId}`} style={{"text-align":'center'}}>
                            <div>
                                <Button color={'primary'} disabled={shouldDisableOrderForEvent(event.eventId)} onClick={() => handleOpenModal(event)}>Order Ticket</Button>
                                {
                                    shouldDisableOrderForEvent(event.eventId) &&
                                    <Tooltip
                                        placement={"top"}
                                        isOpen={tooltipOpen[event.eventId]}
                                        target={`btn-event-${event.eventId}`}
                                        toggle={() => toggleTooltip(event.eventId)}>You already ordered a ticket for this event
                                    </Tooltip>
                                }
                            </div>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
            <div style={{marginTop: '20px', display: 'flex', justifyContent: 'center'}}>
                <Button onClick={setFirstPage} disabled={currentPage === 1} style={{margin: '0 5px'}}>First</Button>
                <Button onClick={handlePrevious} disabled={currentPage === 1} style={{margin: '0 5px'}}>Previous</Button>
                {pageNumbers.map(number => (
                    <Button key={number} onClick={() => paginate(number)} color={currentPage ===number ? 'primary' : 'secondary'} style={{margin: '0 5px'}}>
                        {number}
                    </Button>
                )).slice(currentPage - 2 < 0 ? 0 : currentPage - 2, currentPage + 2 > pageNumbers.length ? pageNumbers.length : currentPage + 2)}
                <Button onClick={handleNext} disabled={currentPage === pageNumbers.length} style={{margin: '0 5px'}}>Next</Button>
                <Button onClick={setLastPage} disabled={currentPage === pageNumbers.length} style={{margin: '0 5px'}}>Last</Button>
            </div>
            <Modal isOpen={modalIsOpen} onClose={handleCloseModal} event={selectedEvent} />
        </div>
    );
}