package common;

public class ServiceException extends Exception {

    private ErrorType m_ErrorType;

    public ServiceException(ErrorType errorType){
        m_ErrorType = errorType;
    }

    public ErrorType getErrorType() {
        return m_ErrorType;
    }
}
