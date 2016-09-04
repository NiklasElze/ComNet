package bll.interfaces;

import api.model.MessagePushModel;
import common.ServiceException;
import model.Message;

import java.util.List;

public interface IMessageService {
    List<Message> getMessagesOfConversation(int conversationId) throws ServiceException;

    void addMessage(MessagePushModel model) throws ServiceException;
}
