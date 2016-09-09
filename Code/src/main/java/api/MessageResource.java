package api;

import api.annotation.Secured;
import api.model.CustomPrincipal;
import api.model.MessagePushModel;
import api.model.Role;
import api.service.SecurityService;
import api.service.StatusCodeService;
import bll.MessageService;
import bll.interfaces.IMessageService;
import common.ErrorType;
import common.JsonService;
import common.ServiceException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/message")
public class MessageResource {

    private IMessageService m_MessageService;

    public MessageResource(){
        m_MessageService = new MessageService();
    }

    @PUT
    @Secured
    @Consumes("application/json")
    @Produces("application/json")
    public Response addMessage(MessagePushModel model, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[]{Role.STUDENT},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            m_MessageService.addMessage(model);

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
    @Path("conversation/{id}")
    @Secured
    @Produces("application/json")
    public Response getMessagesOfConversation(@PathParam("id") int conversationId, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[]{Role.STUDENT},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            List<model.Message> messages = m_MessageService.getMessagesOfConversation(conversationId);

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
                    .entity(JsonService.getListAsJsonArray(messages))
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
