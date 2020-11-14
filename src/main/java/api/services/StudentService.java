package api.services;

import api.Response.CustomResponses;
import api.Response.ResponseMessage;
import api.interfaces.IStudent;
import api.models.*;

import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StudentService extends BasicService implements IStudent {
    @Override
    public Response create(Student student) throws Exception {
        String query = "insert into student(firstName, lastName, email, password, group_id, major_id, year) values (?, ?, ?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, student.getFirstName());
        preparedStatement.setString(2, student.getLastName());
        preparedStatement.setString(3, student.getEmail());
        preparedStatement.setString(4, student.getPassword());
        preparedStatement.setInt(5, student.getGroup().getId());
        preparedStatement.setInt(6, student.getMajor().getId());
        preparedStatement.setInt(7, student.getYear());
        preparedStatement.executeUpdate();
        return CustomResponses.CREATED;
    }

    @Override
    public Response read(int id) throws Exception {
        String query = "select *,m.name as major_name, g.name as group_name from student s " +
                "inner join _group g on s.group_id = g.id " +
                "inner join major m on s.major_id = m.id " +
                "where s.id = " + id;
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (!resultSet.isBeforeFirst()){
            throw new Exception(ResponseMessage.NOT_FOUND);
        }
        resultSet.next();
        return CustomResponses.read(new Student(
                resultSet.getInt("id"),
                resultSet.getString("email"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                new Group(resultSet.getInt("group_id"), resultSet.getString("group_name")),
                new Major(resultSet.getInt("major_id"), resultSet.getString("major_name")),
                resultSet.getInt("year")
        ));
    }

    @Override
    public Response update(Student student) throws Exception {
        String query = "update student set firstName = ?, lastName = ?, email = ?, group_id = ?, major_id = ?, year = ? where id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, student.getFirstName());
        preparedStatement.setString(2, student.getLastName());
        preparedStatement.setString(3, student.getEmail());
        preparedStatement.setInt(4, student.getGroup().getId());
        preparedStatement.setInt(5, student.getMajor().getId());
        preparedStatement.setInt(6, student.getYear());
        preparedStatement.setInt(7, student.getId());
        preparedStatement.executeUpdate();
        return CustomResponses.UPDATED;
    }

    @Override
    public Response delete(int id) throws Exception {
        String query = "delete from student where id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        closeAll();
        return CustomResponses.DELETED;
    }

    @Override
    public Response getStudents(int group_id, int major_id, int year, String name, String email) throws Exception {
        String query;
        if (group_id == 0 && major_id == 0 && year == 0 && name == null && email == null)
            query = "select *, g.name as group_name, m.name as major_name from student s " +
                    "inner join _group g " +
                    "on s.group_id = g.id " +
                    "inner join major m " +
                    "on s.major_id = m.id ";
        else
            query = "select *, g.name as group_name, m.name as major_name from student s " +
                    "inner join _group g " +
                    "on s.group_id = g.id " +
                    "inner join major m " +
                    "on s.major_id = m.id " +
                    "where s.major_id = ifnull(" + major_id + ", s.major_id) or " +
                    "s.group_id = ifnull(" + group_id + ", s.group_id) or " +
                    "s.year = ifnull(" + year + ", s.year) or " +
                    "concat(firstName, ' ', lastName) LIKE  ifnull('%" + name + "%', '%%') or " +
                    "email like ifnull('%" + email + "%', '%%')";

        ArrayList<Student> list = new ArrayList<>();
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next())
            list.add(new Student(
                    resultSet.getInt("id"),
                    resultSet.getString("email"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    new Group(resultSet.getInt("group_id"), resultSet.getString("group_name")),
                    new Major(resultSet.getInt("major_id"), resultSet.getString("major_name")),
                    resultSet.getInt("year")
            ));
        closeAll();
        return CustomResponses.read(list);
    }
}
