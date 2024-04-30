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
                    <Button type="submit">Order Ticket</Button>
                </Form>
                <Button  onClick={onClose} style={{ marginTop: '10px' }}>Close</Button>
            </div>
        </div>
    );
};


export const HomeComponent = () => {
    const dataProvider = useAppContext().getDataProvider();
    const securityManager = useAppContext().getSecurityManager();

    const [events, setEvents] = useState([]);
    const [locations, setLocations] = useState([]);
    const [artists, setArtists] = useState([]);
    const [tickets, setTickets] = useState([]);
    const [loading, setLoading] = useState(true);

    const [currentPage, setCurrentPage] = useState(1);
    const [eventsPerPage, setEventsPerPage] = useState(10);


    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [selectedEvent, setSelectedEvent] = useState(null);

    const [tooltipOpen, setTooltipOpen] = useState(false);
    const toggle = () => setTooltipOpen(!tooltipOpen);

    const handleOpenModal = (event) => {
        setSelectedEvent(event);
        setModalIsOpen(true);
    };
    const handleCloseModal = () => {
        setModalIsOpen(false);
    };

    const indexOfLastEvent = currentPage * eventsPerPage;
    const indexOfFirstEvent = indexOfLastEvent - eventsPerPage;
    const currentEvents = Object.values(events).slice(indexOfFirstEvent, indexOfLastEvent);

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

    useEffect(() => {
        async function fetchData() {
            try {
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
                setEvents(fetchedEvents);
                setLocations(fetchedLocations);
                setTickets(fetchedTickets);
                setArtists(fetchedArtists);

                console.log(`Fetched artists: ${JSON.stringify(fetchedArtists)}`);
                console.log(`Fetched tickets: ${JSON.stringify(fetchedTickets)}`);

            } catch (error) {
                console.error(`Error occurred while fetching data ${error}`);
            } finally {
                setLoading(false);
            }
        }
        if (events.length === 0) {
            fetchData();
        }
    }, [events.length, dataProvider]);

    if (loading) {
        return (
            <SpinnerComponent/>
        );
    }

    return (
        <div style={{margin: '0 auto', maxWidth: '90%', padding: '20px'}}>
            <h1>Events List</h1>
            <table style={{width: '100%', borderCollapse: 'collapse'}}>
                <thead>
                <tr>
                    <th>Title</th>
                    <th>Date and Time</th>
                    <th>Description</th>
                    <th>Location Name</th>
                    <th>Artists</th>
                    <th>Ticket Price</th>
                    <th>Order</th>
                </tr>
                </thead>
                <tbody>
                {currentEvents.map((event) => (
                    <tr key={event.eventId} id={`event-${event.eventId}`}>
                        <td>{event.title}</td>
                        <td>{event.dateTime}</td>
                        <td>{event.description}</td>
                        <td>{searchInArrayByKeyAndGetField(locations, 'locationId', event.locationId, 'name')}</td>
                        <td>{artists[event.eventId] === undefined
                            ? artists[event.eventId].map(artist => artist.name).join(', ')
                            : 'No artists'}</td>
                        <td>${event.ticketPrice}</td>
                        <td>
                            <div id={`btn-event-${event.eventId}`}>
                                <Button disabled={shouldDisableOrderForEvent(event.eventId)} onClick={() => handleOpenModal(event)}>Order Ticket</Button>
                                {
                                    shouldDisableOrderForEvent(event.eventId) &&
                                    <Tooltip
                                        placement={"top"}
                                        isOpen={tooltipOpen}
                                        target={`btn-event-${event.eventId}`}
                                        toggle={toggle}>You already ordered a ticket for this event
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
                    <Button key={number} onClick={() => paginate(number)} style={{margin: '0 5px'}}>
                        {number}
                    </Button>
                )).slice(currentPage - 3, currentPage + 2)}
                <Button onClick={handleNext} disabled={currentPage === pageNumbers.length} style={{margin: '0 5px'}}>Next</Button>
                <Button onClick={setLastPage} disabled={currentPage === pageNumbers.length} style={{margin: '0 5px'}}>Last</Button>
            </div>
            <Modal isOpen={modalIsOpen} onClose={handleCloseModal} event={selectedEvent} />
        </div>
    );
}