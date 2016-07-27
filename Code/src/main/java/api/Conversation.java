package api;

import api.annotation.Secured;
import api.model.ConversationPushModel;
import api.model.CustomPrincipal;
import api.model.Role;
import api.service.SecurityService;
import api.service.StatusCodeService;
import bll.ConversationService;
import bll.interfaces.IConversationService;
import common.ErrorType;
import common.JsonService;
import common.ServiceException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/conversation")
public class Conversation {

    private IConversationService m_ConversationService;

    public Conversation(){
        m_ConversationService = new ConversationService();
    }

    @GET
    @Path("/student/{id}")
    @Secured
    @Consumes("application/json")
    @Produces("application/json")
    public Response getConversationsOfStudent(@PathParam("id") int studentId, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[]{Role.PERSON_IN_AUTHORITY},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            List<model.Conversation> conversations = m_ConversationService.getConversationsOfStudent(studentId);

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
                    .entity(JsonService.getListAsJsonArray(conversations))
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
    @Path("/{id}")
    @Secured
    @Consumes("application/json")
    @Produces("application/json")
    public Response getConversationById(@PathParam("id") int conversationId){
        return null;
    }

    @DELETE
    @Secured
    @Consumes("application/json")
    @Produces("application/json")
    public Response deleteConversation(int conversationId){
        return null;
    }

    @PUT
    @Secured
    @Consumes("application/json")
    @Produces("application/json")
    public Response addConversation(ConversationPushModel model){
        return null;
    }
}
