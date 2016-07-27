package repository.interfaces;

import model.Conversation;
import model.Student;

import java.util.Collection;
import java.util.List;

public interface IConversationRepository {

    List<Conversation> getByStudentId(int id);

    Conversation getByMembers(Collection<Student> members);
    Conversation getById(int id);

    void add(Conversation conversation);
    void delete(Conversation conversation);
}
