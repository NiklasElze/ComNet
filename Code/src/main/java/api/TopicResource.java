package api;

import api.annotation.Secured;
import api.model.CustomPrincipal;
import api.model.Role;
import api.model.TopicPushModel;
import api.service.SecurityService;
import api.service.StatusCodeService;
import bll.TopicService;
import bll.interfaces.ITopicService;
import common.ErrorType;
import common.JsonService;
import common.ServiceException;
import model.Topic;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/topic")
public class TopicResource {

    private ITopicService m_TopicService;

    public TopicResource(){
        m_TopicService = new TopicService();
    }

    @GET
    @Path("/group/{id}")
    @Secured
    @Produces("application/json")
    public Response getTopicsOfGroup(@PathParam("id") int groupId, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[] {Role.STUDENT},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            List<Topic> topics = m_TopicService.getTopicsOfGroup(groupId);

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
                    .entity(JsonService.getListAsJsonArray(topics))
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
    @Produces("application/json")
    public Response getTopicById(@PathParam("id") int id, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[] {Role.STUDENT},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            Topic topic = m_TopicService.getTopicById(id);

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
                    .entity(topic.toJson())
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
    public Response addOrUpdateTopic(TopicPushModel model, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[] {Role.STUDENT},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            m_TopicService.addOrUpdateTopic(model);

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
    public Response deleteTopic(@PathParam("id") int id, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[] {Role.STUDENT},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            m_TopicService.deleteTopic(id);

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
