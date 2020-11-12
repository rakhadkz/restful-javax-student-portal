package api.interfaces;

import api.models.Club;

import javax.ws.rs.core.Response;

public interface IClub {
    //CRUD
    Response create(Club club) throws Exception;
    Response read(int id) throws Exception;
    Response update(Club club) throws Exception;
    Response delete(int id) throws Exception;

    //All clubs
    Response getClubs() throws Exception;

    //Students
    Response enter(int club_id, int student_id) throws Exception;
    Response leave(int club_id, int student_id) throws Exception;
    Response getStudentsOfClub(int id) throws Exception;
}
