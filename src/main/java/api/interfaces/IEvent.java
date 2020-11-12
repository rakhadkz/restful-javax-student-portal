package api.interfaces;

import api.models.Event;

import javax.ws.rs.core.Response;

public interface IEvent {
    //CRUD
    Response create(Event event) throws Exception;
    Response read(int id) throws Exception;
    Response update(Event event) throws Exception;
    Response delete(int id) throws Exception;

    //All events
    Response getEvents(int major_id, int club_id) throws Exception;

}
