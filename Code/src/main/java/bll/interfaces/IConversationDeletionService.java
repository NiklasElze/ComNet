package bll.interfaces;

import common.ServiceException;
import model.Conversation;

public interface IConversationDeletionService {

    void deleteConversation(Conversation conversation) throws ServiceException;
}
