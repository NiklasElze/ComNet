package api;

import api.annotation.Secured;
import api.model.CustomPrincipal;
import api.model.Role;
import api.model.SeminarGroupPushModel;
import api.service.SecurityService;
import api.service.StatusCodeService;
import bll.SeminarGroupService;
import bll.interfaces.ISeminarGroupService;
import common.ErrorType;
import common.JsonService;
import common.ServiceException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/seminargroup")
public class SeminarGroupResource {

    private ISeminarGroupService m_SeminarGroupService;

    public SeminarGroupResource(){
        m_SeminarGroupService = new SeminarGroupService();
    }

    @PUT
    @Secured
    @Consumes("application/json")
    @Produces("application/json")
    public Response addOrUpdateSeminarGroup(SeminarGroupPushModel model, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[] {Role.PERSON_IN_AUTHORITY},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            m_SeminarGroupService.addOrUpdateSeminarGroup(model);

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
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

    @DELETE
    @Path("/{id}")
    @Secured
    @Produces("application/json")
    public Response deleteSeminarGroup(@PathParam("id") int seminarGroupId, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[] {Role.PERSON_IN_AUTHORITY},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            m_SeminarGroupService.deleteSeminarGroup(seminarGroupId);

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
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
    @Path("/list")
    @Secured
    @Produces("application/json")
    public Response getSeminarGroupList(@Context SecurityContext securityContext) {
        try {
            SecurityService.authorizeUser(new Role[] {Role.PERSON_IN_AUTHORITY},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            List<model.SeminarGroup> seminarGroupList = m_SeminarGroupService.getSeminarGroupList();

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
                    .entity(JsonService.getListAsJsonArray(seminarGroupList))
                    .build();
        } catch (ServiceException serviceException) {
            ErrorType errorType = serviceException.getErrorType();

            return Response
                    .status(StatusCodeService.getStatusByErrorType(errorType))
                    .entity(errorType)
                    .build();
        } catch (Exception exception) {
            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.INTERNAL_ERROR))
                    .entity(ErrorType.INTERNAL_ERROR)
                    .build();
        }
    }

    @GET
    @Path("/contactlist/conversation/{id}")
    @Secured
    @Produces("application/json")
    public Response getContactListOfConversation(@PathParam("id") int conversationId, @Context SecurityContext securityContext){
        try {
            SecurityService.authorizeUser(new Role[] {Role.STUDENT},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            List<model.SeminarGroup> seminarGroupList = m_SeminarGroupService.getContactListOfConversation(conversationId);

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
                    .entity(JsonService.getListAsJsonArray(seminarGroupList))
                    .build();
        } catch (ServiceException serviceException) {
            ErrorType errorType = serviceException.getErrorType();

            return Response
                    .status(StatusCodeService.getStatusByErrorType(errorType))
                    .entity(errorType)
                    .build();
        } catch (Exception exception) {
            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.INTERNAL_ERROR))
                    .entity(ErrorType.INTERNAL_ERROR)
                    .build();
        }
    }

    @GET
    @Path("/contactlist/group/{id}")
    @Secured
    @Produces("application/json")
    public Response getContactListOfGroup(@PathParam("id") int groupId, @Context SecurityContext securityContext){
        try {
            SecurityService.authorizeUser(new Role[] {Role.STUDENT},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            List<model.SeminarGroup> seminarGroupList = m_SeminarGroupService.getContactListOfGroup(groupId);

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
                    .entity(JsonService.getListAsJsonArray(seminarGroupList))
                    .build();
        } catch (ServiceException serviceException) {
            ErrorType errorType = serviceException.getErrorType();

            return Response
                    .status(StatusCodeService.getStatusByErrorType(errorType))
                    .entity(errorType)
                    .build();
        } catch (Exception exception) {
            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.INTERNAL_ERROR))
                    .entity(ErrorType.INTERNAL_ERROR)
                    .build();
        }
    }

    @GET
    @Path("/contactlist/conversation/new")
    @Secured
    @Produces("application/json")
    public Response getContactListOfNewConversation(@Context SecurityContext securityContext){
        try {
            SecurityService.authorizeUser(new Role[] {Role.STUDENT},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            int currentUserId = ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser().getId();

            List<model.SeminarGroup> seminarGroupList = m_SeminarGroupService.getContactListOfNewConversation(currentUserId);

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
                    .entity(JsonService.getListAsJsonArray(seminarGroupList))
                    .build();
        } catch (ServiceException serviceException) {
            ErrorType errorType = serviceException.getErrorType();

            return Response
                    .status(StatusCodeService.getStatusByErrorType(errorType))
                    .entity(errorType)
                    .build();
        } catch (Exception exception) {
            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.INTERNAL_ERROR))
                    .entity(ErrorType.INTERNAL_ERROR)
                    .build();
        }
    }
}
