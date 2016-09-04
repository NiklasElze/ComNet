package api;

import api.model.Credential;
import api.model.Identity;
import api.service.StatusCodeService;
import bll.LoginService;
import bll.interfaces.ILoginService;
import common.ErrorType;
import common.ServiceException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/login")
public class Login {

    private ILoginService m_LoginService;

    public Login(){
        m_LoginService = new LoginService();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response loginUser(Credential credential) {

        try{
            Identity identity = m_LoginService.loginUser(credential.getUsername(), credential.getPassword());

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
                    .entity(identity.toJson())
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