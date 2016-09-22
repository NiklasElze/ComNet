package api;

import api.annotation.Secured;
import api.model.CustomPrincipal;
import api.model.Role;
import api.model.TopicEntryPushModel;
import api.service.SecurityService;
import api.service.StatusCodeService;
import bll.TopicEntryService;
import common.ErrorType;
import common.JsonService;
import common.ServiceException;
import model.TopicEntry;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/topicentry")
public class TopicEntryResource {

    private TopicEntryService m_TopicEntryService;

    public TopicEntryResource(){
        m_TopicEntryService = new TopicEntryService();
    }

    @GET
    @Path("topic/{id}")
    @Secured
    @Produces("application/json")
    public Response getEntriesOfTopic(@PathParam("id") int topicId, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[]{Role.STUDENT},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            List<TopicEntry> entries = m_TopicEntryService.getTopicEntriesOfTopic(topicId);

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
                    .entity(JsonService.getListAsJsonArray(entries))
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
    public Response addTopicEntry(TopicEntryPushModel model, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[] {Role.STUDENT},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            m_TopicEntryService.addTopicEntry(model);

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
