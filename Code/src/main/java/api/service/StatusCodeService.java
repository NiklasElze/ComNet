package api.service;

import common.ErrorType;

import javax.ws.rs.core.Response;

public class StatusCodeService {

    public static Response.Status getStatusByErrorType(ErrorType errorType){

        switch (errorType){
            case NO_ERROR:
                return Response.Status.OK;

            case GROUP_NOT_FOUND:
            case STUDENT_NOT_FOUND:
            case LOGIN_NOT_FOUND:
                return Response.Status.NOT_FOUND;

            case GROUP_CONTAINS_STUDENTS:
            case INVALID_LOGIN_DATA:
                return Response.Status.FORBIDDEN;

            case LOGIN_ALREADY_EXISTS:
                return Response.Status.CONFLICT;

            case INTERNAL_ERROR:
                return Response.Status.INTERNAL_SERVER_ERROR;

            default:
                return Response.Status.INTERNAL_SERVER_ERROR;
        }
    }
}
