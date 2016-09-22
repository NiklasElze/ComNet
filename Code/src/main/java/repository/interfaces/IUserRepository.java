package repository.interfaces;

import model.User;

import java.util.List;

public interface IUserRepository {

    List<User> getAll();

    User getById(int id);

    void add(User user);
    void delete (User user);
}
