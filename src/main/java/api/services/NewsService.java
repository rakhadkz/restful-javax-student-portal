package api.services;

import api.Response.CustomResponses;
import api.Response.ResponseMessage;
import api.interfaces.INews;
import api.models.Event;
import api.models.Major;
import api.models.News;

import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.util.ArrayList;

public class NewsService extends BasicService implements INews {

    @Override
    public Response create(News news) throws Exception {
        String query = "insert into news(name, description, image, major_id) values (?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, news.getName());
        preparedStatement.setString(2, news.getDescription());
        preparedStatement.setString(3, news.getImage());
        if (news.getMajor() != null)
            preparedStatement.setInt(4, news.getMajor().getId());
        else
            preparedStatement.setObject(4, null);
        preparedStatement.executeUpdate();
        closeAll();
        return CustomResponses.CREATED;
    }

    @Override
    public Response read(int id) throws Exception {
        String query = "select *, m.name as major_name from news n inner join major m " +
                "on n.major_id = m.id where n.id = " + id;
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (!resultSet.isBeforeFirst()){
            throw new Exception(ResponseMessage.NOT_FOUND);
        }
        resultSet.next();
        return CustomResponses.read(new News(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("image"),
                new Major(resultSet.getInt("major_id"), resultSet.getString("major_name")),
                resultSet.getDate("created_at")
        ));
    }

    @Override
    public Response update(News news) throws Exception {
        String query = "update news set name = ?, description = ?, image = ?, major_id = ? where id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, news.getName());
        preparedStatement.setString(2, news.getDescription());
        preparedStatement.setString(3, news.getImage());
        if (news.getMajor() != null)
            preparedStatement.setInt(4, news.getMajor().getId());
        else
            preparedStatement.setObject(4, null);
        preparedStatement.setInt(5, news.getId());
        preparedStatement.executeUpdate();
        closeAll();
        return CustomResponses.UPDATED;
    }

    @Override
    public Response delete(int id) throws Exception {
        String query = "delete from news where id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        closeAll();
        return CustomResponses.DELETED;
    }

    @Override
    public Response getNews(int major_id) throws Exception {
        String query;
        if (major_id == 0){
            query = "select *, m.name as major_name from news n inner join major m on n.major_id = m.id";
        }else{
            query = "select *, m.name as major_name from news n inner join major m " +
                    "on n.major_id = m.id where n.major_id = " + major_id;
        }
        ArrayList<News> list = new ArrayList<>();
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next())
            list.add(new News(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("image"),
                    new Major(resultSet.getInt("major_id"), resultSet.getString("major_name")),
                    resultSet.getDate("created_at")
            ));
        closeAll();
        return CustomResponses.read(list);
    }
}
