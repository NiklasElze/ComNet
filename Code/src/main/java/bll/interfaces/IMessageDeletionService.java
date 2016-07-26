package bll.interfaces;

import common.ErrorType;
import model.Student;

public interface IMessageDeletionService {

    ErrorType deleteMessagesOfStudent(Student student);
}
