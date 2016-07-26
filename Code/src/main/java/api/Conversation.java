package api;

import api.annotation.Secured;
import api.model.ConversationPushModel;
import bll.ConversationService;
import bll.interfaces.IConversationService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

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
    public Response getConversationsOfStudent(@PathParam("id") int studentId){
        return null;
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
