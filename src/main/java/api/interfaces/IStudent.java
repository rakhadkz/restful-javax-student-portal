package api.interfaces;

import api.models.Club;
import api.models.Student;

import javax.ws.rs.core.Response;

public interface IStudent {
    //CRUD
    Response create(Student student) throws Exception;
    Response read(int id) throws Exception;
    Response update(Student student) throws Exception;
    Response delete(int id) throws Exception;

    //All students
    Response getStudents(int group_id, int major_id, int year, String name, String email) throws Exception;

}
