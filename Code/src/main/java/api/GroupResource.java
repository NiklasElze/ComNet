package api;

import api.annotation.Secured;
import api.model.CustomPrincipal;
import api.model.Role;
import api.service.SecurityService;
import api.service.StatusCodeService;
import bll.GroupService;
import bll.interfaces.IGroupService;
import common.ErrorType;
import common.JsonService;
import common.ServiceException;
import model.Group;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/group")
public class GroupResource {

    private IGroupService m_GroupService;

    public GroupResource(){
        m_GroupService = new GroupService();
    }

    @GET
    @Path("/student/{id}")
    @Secured
    @Produces("application/json")
    public Response getGroupsOfStudent(@PathParam("id") int studentId, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[]{Role.STUDENT},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            List<Group> groups = m_GroupService.getGroupsOfStudent(studentId);

            return Response
                    .status(200)
                    .entity(JsonService.getListAsJsonArray(groups))
                    .build();
        }
        catch (ServiceException serviceException){
            ErrorType errorType = serviceException.getErrorType();

            return Response
                    .status(StatusCodeService.getStatusByErrorType(errorType))
                    .entity(errorType)
                    .build();
        }
        catch (Exception exception){
            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.INTERNAL_ERROR))
                    .entity(ErrorType.INTERNAL_ERROR)
                    .build();
        }
    }

    @GET
    @Path("/id")
    @Secured
    @Produces
    public Response getGroupById(@PathParam("id") int id, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[]{Role.STUDENT},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            Group group = m_GroupService.getGroupById(id);

            return Response
                    .status(200)
                    .entity(group.toJson())
                    .build();
        }
        catch (ServiceException serviceException){
            ErrorType errorType = serviceException.getErrorType();

            return Response
                    .status(StatusCodeService.getStatusByErrorType(errorType))
                    .entity(errorType)
                    .build();
        }
        catch (Exception exception){
            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.INTERNAL_ERROR))
                    .entity(ErrorType.INTERNAL_ERROR)
                    .build();
        }
    }
}
