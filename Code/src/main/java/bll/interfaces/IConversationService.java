package bll.interfaces;

import api.model.ConversationPushModel;
import common.ServiceException;
import model.Conversation;

import java.util.List;

public interface IConversationService {
    List<Conversation> getConversationsOfStudent(int studentId) throws ServiceException;

    Conversation getConversationByMembers(List<Integer> memberIds) throws ServiceException;
    Conversation getConversationById(int id) throws ServiceException;

    void addOrUpdateConversation(ConversationPushModel model) throws ServiceException;
}
