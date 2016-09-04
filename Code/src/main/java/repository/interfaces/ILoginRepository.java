package repository.interfaces;

import model.Login;

import java.util.List;

public interface ILoginRepository {

    Login getByUsername(String username);
    Login getBySessionId(String sessionId);
    Login getByUserId(int id);

    List getAll();

    void add(Login login);
    void delete(Login login);
}
