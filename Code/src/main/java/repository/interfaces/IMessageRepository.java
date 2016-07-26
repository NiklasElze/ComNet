package repository.interfaces;

import model.Message;

import java.util.List;

public interface IMessageRepository {

    Message getById(int id);

    List<Message> getByStudentId(int id);

    void add(Message message);
    void delete(Message message);
}
