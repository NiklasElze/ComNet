package api;

import api.annotation.Secured;
import api.model.CustomPrincipal;
import api.model.Role;
import api.model.StudentData;
import api.model.StudentPushModel;
import api.service.SecurityService;
import api.service.StatusCodeService;
import bll.StudentService;
import bll.interfaces.IStudentService;
import common.ErrorType;
import common.JsonService;
import common.ServiceException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/student")
public class Student {

    private IStudentService m_StudentService;

    public Student(){
        m_StudentService = new StudentService();
    }

    @DELETE
    @Path("/{id}")
    @Secured
    @Produces("application/json")
    public Response deleteStudent(@PathParam("id") int id, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[] {Role.PERSON_IN_AUTHORITY},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            m_StudentService.deleteStudent(id);

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
    @Secured
    @Consumes("application/json")
    @Produces("application/json")
    public Response createOrUpdateStudent(StudentPushModel model, @Context SecurityContext securityContext){

        try{
            SecurityService.authorizeUser(new Role[] {Role.PERSON_IN_AUTHORITY},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            m_StudentService.createOrUpdateStudent(model);

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
    @Secured
    @Path("/list")
    @Produces("application/json")
    public Response getStudentList(@Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[]{Role.PERSON_IN_AUTHORITY},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            List<model.Student> studentList = m_StudentService.getStudentList();

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
                    .entity(JsonService.getListAsJsonArray(studentList))
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
    @Secured
    @Path("/{id}")
    @Produces("application/json")
    public Response getStudentById(@PathParam("id") int id, @Context SecurityContext securityContext){
        try{
            SecurityService.authorizeUser(new Role[]{Role.PERSON_IN_AUTHORITY},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            StudentData data = m_StudentService.getStudentData(id);

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
                    .entity(data.toJson())
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
    @Path("/seminargroup/{id}")
    @Secured
    @Produces("application/json")
    public Response getStudentsOfSeminarGroup(@PathParam("id") int seminarGroupId, @Context SecurityContext securityContext){
        try {
            SecurityService.authorizeUser(new Role[] {Role.PERSON_IN_AUTHORITY},
                    ((CustomPrincipal) securityContext.getUserPrincipal()).getAuthorizedUser());

            List<model.Student> students = m_StudentService.getStudentsOfSeminarGroup(seminarGroupId);

            return Response
                    .status(StatusCodeService.getStatusByErrorType(ErrorType.NO_ERROR))
                    .entity(JsonService.getListAsJsonArray(students))
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
