package api.services;

import api.Response.CustomResponses;
import api.Response.ResponseMessage;
import api.interfaces.IClub;
import api.models.*;

import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ClubService extends BasicService implements IClub {

    @Override
    public Response create(Club club) throws Exception {
        String query = "insert into club(name, description, image) values (?, ?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, club.getName());
        preparedStatement.setString(2, club.getDescription());
        preparedStatement.setString(3, club.getImage());
        preparedStatement.executeUpdate();
        closeAll();
        return CustomResponses.CREATED;
    }

    @Override
    public Response read(int id) throws Exception {
        String query = "select * from club where id = " + id;
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        closeAll();
        if (!resultSet.isBeforeFirst()){
            throw new Exception(ResponseMessage.NOT_FOUND);
        }
        resultSet.next();
        return CustomResponses.read(new Club(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("image"),
                resultSet.getDate("created_at")
        ));
    }

    @Override
    public Response update(Club club) throws Exception {
        String query = "update club set name = ?, description = ?, image = ? where id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, club.getName());
        preparedStatement.setString(2, club.getDescription());
        preparedStatement.setString(3, club.getImage());
        preparedStatement.setInt(4, club.getId());
        preparedStatement.executeUpdate();
        closeAll();
        return CustomResponses.UPDATED;
    }

    @Override
    public Response delete(int id) throws Exception {
        String query = "delete from club where id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        closeAll();
        return CustomResponses.DELETED;
    }

    @Override
    public Response getClubs() throws Exception {
        ArrayList<Club> list = new ArrayList<>();
        String query = "select * from club";
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            System.out.println(resultSet.getString("name"));
            list.add(new Club(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("image"),
                    resultSet.getDate("created_at")
            ));
        }
        closeAll();
        return CustomResponses.read(list);
    }

    @Override
    public Response enter(int club_id, int student_id) throws Exception {
        String query = "insert into students_club (club_id, student_id) values (?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, club_id);
        preparedStatement.setInt(2, student_id);
        preparedStatement.executeUpdate();
        closeAll();
        return CustomResponses.ENTERED;
    }

    @Override
    public Response leave(int club_id, int student_id) throws Exception {
        String query = "delete from students_clubs where club_id = ? and student_id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, club_id);
        preparedStatement.setInt(2, student_id);
        preparedStatement.executeUpdate();
        closeAll();
        return CustomResponses.LEFT;
    }

    @Override
    public Response getStudentsOfClub(int id) throws Exception {
        ArrayList<Student> list = new ArrayList<>();
        String query = "select *, m.name as major_name from students_clubs c " +
                "inner join student s on c.student_id = s.id " +
                "inner join _group g on s.group_id = g.id " +
                "inner join major m on s.major_id = m.id " +
                "where c.club_id = " + id;
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while(resultSet.next())
            list.add(new Student(
                    resultSet.getInt("student_id"),
                    resultSet.getString("email"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    new Group(resultSet.getString("name")),
                    new Major(resultSet.getString("major_name")),
                    resultSet.getInt("year")
            ));
        closeAll();
        return CustomResponses.read(list);
    }
}
