package api;

import api.annotation.Secured;
import api.model.ConversationPushModel;
import api.model.CustomPrincipal;
import api.model.Member;
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
public class ConversationResource {

    private IConversationService m_ConversationService;

    public ConversationResource(){
        m_ConversationService = new ConversationService();
    }

    @GET
    @Path("/student/{id}")
    @Secured
    @Consumes("application/json")
    @Produces("application/json")
    public Response getConversationsOfStudent(@PathParam("id") int studentId, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[]{Role.STUDENT},
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
    public Response getConversationById(@PathParam("id") int conversationId, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[]{Role.STUDENT},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            model.Conversation conversation = m_ConversationService.getConversationById(conversationId);

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
                    .entity(conversation.toJson())
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

    @POST
    @Secured
    @Consumes("application/json")
    @Produces("application/json")
    public Response getConversationByMembers(ConversationPushModel model, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[]{Role.STUDENT},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            model.Conversation conversation = m_ConversationService.getConversationByMembers(model.getMemberIds());

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
                    .entity(conversation.toJson())
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

    @PUT
    @Secured
    @Consumes("application/json")
    @Produces("application/json")
    public Response addOrUpdateConversation(ConversationPushModel model, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[]{Role.STUDENT},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            m_ConversationService.addOrUpdateConversation(model);

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

    @PUT
    @Path("/{id}/members/add")
    @Secured
    @Consumes("application/json")
    @Produces("application/json")
    public Response addMembersToConversation(@PathParam("id") int id, List<Member> members, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[]{Role.STUDENT},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            m_ConversationService.addMembersToConversation(id, members);

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

    @PUT
    @Path("/{id}/members/remove")
    @Secured
    @Produces("application/json")
    public Response removeStudentFromConversation(@PathParam("id") int conversationId, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[]{Role.STUDENT},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            int currentUserId = ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser().getId();

            m_ConversationService.removeStudentFromConversation(conversationId, currentUserId);

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
}
