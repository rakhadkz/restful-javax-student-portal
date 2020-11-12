package api.services;

import api.Response.CustomResponses;
import api.Response.ResponseMessage;
import api.interfaces.IEvent;
import api.models.Club;
import api.models.Event;

import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EventService extends BasicService implements IEvent {
    @Override
    public Response create(Event event) throws Exception {
        String query = "insert into event(name, description, image, club_id, major_id) values (?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, event.getName());
        preparedStatement.setString(2, event.getDescription());
        preparedStatement.setString(3, event.getImage());
        preparedStatement.setInt(4, event.getClub_id());
        preparedStatement.setInt(5, event.getMajor_id());
        preparedStatement.executeUpdate();
        closeAll();
        return CustomResponses.CREATED;
    }

    @Override
    public Response read(int id) throws Exception {
        String query = "select * from event where id = " + id;
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        closeAll();
        if (!resultSet.isBeforeFirst()){
            throw new Exception(ResponseMessage.NOT_FOUND);
        }
        resultSet.next();
        return CustomResponses.read(new Event(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("image"),
                resultSet.getDate("created_at")
        ));
    }

    @Override
    public Response update(Event event) throws Exception {
        String query = "update event set name = ?, description = ?, image = ? where id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, event.getName());
        preparedStatement.setString(2, event.getDescription());
        preparedStatement.setString(3, event.getImage());
        preparedStatement.setInt(4, event.getId());
        preparedStatement.executeUpdate();
        closeAll();
        return CustomResponses.UPDATED;
    }

    @Override
    public Response delete(int id) throws Exception {
        String query = "delete from event where id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        closeAll();
        return CustomResponses.DELETED;
    }

    @Override
    public Response getEvents(int major_id, int club_id) throws Exception {
        String query;
        if (major_id == 0 && club_id == 0){
            query = "select * from event";
        }else{
            query = "select * from event where major_id = " + major_id +
                    " or club_id = " + club_id;
        }
        ArrayList<Event> list = new ArrayList<>();
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next())
            list.add(new Event(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("image"),
                    resultSet.getDate("created_at")
            ));
        closeAll();
        return CustomResponses.read(list);
    }
}
