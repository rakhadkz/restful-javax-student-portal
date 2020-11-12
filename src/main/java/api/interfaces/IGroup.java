package api.interfaces;

import javax.ws.rs.core.Response;

public interface IGroup {
    //All groups
    Response getGroups() throws Exception;
}
