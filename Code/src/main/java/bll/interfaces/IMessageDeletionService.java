package bll.interfaces;

import common.ServiceException;
import model.Student;

public interface IMessageDeletionService {

    void deleteMessagesOfStudent(Student student) throws ServiceException;
}
