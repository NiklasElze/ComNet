package bll.interfaces;

import api.model.ConversationPushModel;
import common.ServiceException;
import model.Conversation;

import java.util.List;

public interface IConversationService {
    List<Conversation> getConversationsOfStudent(int studentId) throws ServiceException;

    void addOrUpdateConversation(ConversationPushModel model) throws ServiceException;
}
