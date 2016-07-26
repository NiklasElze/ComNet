package api;

import api.annotation.Secured;
import api.model.CustomPrincipal;
import api.model.Role;
import api.model.UserData;
import api.model.UserPushModel;
import api.service.SecurityService;
import api.service.StatusCodeService;
import bll.UserService;
import bll.interfaces.IUserService;
import common.ErrorType;
import common.JsonService;
import common.ServiceException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/user")
public class User {

    private IUserService m_UserService;

    public User(){
        m_UserService = new UserService();
    }

    @DELETE
    @Path("/{id}")
    @Secured
    @Produces("application/json")
    public Response deleteUser(@PathParam("id") int id, @Context SecurityContext securityContext){
        try {
            SecurityService.authorizeUser(new Role[]{Role.ADMIN},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            m_UserService.deleteUser(id);

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
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

    @PUT
    @Secured
    @Consumes("application/json")
    @Produces("application/json")
    public Response addOrUpdateUser(UserPushModel model, @Context SecurityContext securityContext) {

        try {
            SecurityService.authorizeUser(new Role[]{Role.ADMIN},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            m_UserService.addOrUpdateUser(model);

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
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
    @Path("/custodians")
    @Secured
    @Produces("application/json")
    public Response getCustodians(@Context SecurityContext securityContext) {

        try {
            SecurityService.authorizeUser(new Role[]{Role.ADMIN},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            List<model.User> custodians = m_UserService.getCustodians();

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
                    .entity(JsonService.getListAsJsonArray(custodians))
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
    @Path("/{id}")
    @Secured
    @Produces("application/json")
    public Response getUserById(@PathParam("id") int id, @Context SecurityContext securityContext){
        try {
            SecurityService.authorizeUser(new Role[]{Role.ADMIN},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            UserData userData = m_UserService.getUserById(id);

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
                    .entity(userData.toJson())
                    .build();
        } catch (
                ServiceException serviceException) {
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
