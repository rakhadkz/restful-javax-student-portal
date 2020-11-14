package api.services;

import api.Response.CustomResponses;
import api.Response.ResponseMessage;
import api.interfaces.IEvent;
import api.models.Club;
import api.models.Event;
import api.models.Major;

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
        if (event.getClub() != null)
            preparedStatement.setInt(4, event.getClub().getId());
        else
            preparedStatement.setObject(4, null);
        if (event.getMajor() != null)
            preparedStatement.setInt(5, event.getMajor().getId());
        else
            preparedStatement.setObject(5, null);
        preparedStatement.executeUpdate();
        closeAll();
        return CustomResponses.CREATED;
    }

    @Override
    public Response read(int id) throws Exception {
        String query = "select *, m.name as major_name, c.name as club_name from event e " +
                "inner join major m " +
                "on e.major_id = m.id " +
                "inner join club c " +
                "on e.club_id = c.id " +
                "where e.id = " + id;
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (!resultSet.isBeforeFirst()){
            throw new Exception(ResponseMessage.NOT_FOUND);
        }
        resultSet.next();
        return CustomResponses.read(new Event(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("image"),
                resultSet.getDate("created_at"),
                new Major(resultSet.getInt("major_id"), resultSet.getString("major_name")),
                new Club(resultSet.getInt("club_id"), resultSet.getString("club_name"))
        ));
    }

    @Override
    public Response update(Event event) throws Exception {
        String query = "update event set name = ?, description = ?, image = ?, major_id = ?, club_id = ? where id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, event.getName());
        preparedStatement.setString(2, event.getDescription());
        preparedStatement.setString(3, event.getImage());
        if (event.getMajor() != null)
            preparedStatement.setInt(4, event.getMajor().getId());
        else
            preparedStatement.setObject(4, null);
        if (event.getClub() != null)
            preparedStatement.setInt(5, event.getClub().getId());
        else
            preparedStatement.setObject(5, null);
        preparedStatement.setInt(6, event.getId());
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
            query = "select *, m.name as major_name, c.name as club_name from event e inner join club c " +
                    "on e.club_id = c.id " +
                    "inner join major m " +
                    "on e.major_id = m.id";
        }else{
            query = "select *, m.name as major_name, c.name as club_name from event e inner join club c " +
                    "on e.club_id = c.id " +
                    "inner join major m " +
                    "on e.major_id = m.id " +
                    "where e.club_id = " + club_id + " or e.major_id = " + major_id;
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
                    resultSet.getDate("created_at"),
                    new Major(resultSet.getInt("major_id"), resultSet.getString("major_name")),
                    new Club(resultSet.getInt("club_id"), resultSet.getString("club_name"))
            ));
        closeAll();
        return CustomResponses.read(list);
    }
}
