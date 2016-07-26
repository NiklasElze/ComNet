package bll.interfaces;

import common.ErrorType;
import model.Conversation;

public interface IConversationDeletionService {

    ErrorType deleteConversation(Conversation conversation);
}
