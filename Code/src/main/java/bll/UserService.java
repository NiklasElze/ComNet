package bll;

import api.model.UserData;
import api.model.UserPushModel;
import bll.interfaces.IUserService;
import common.*;
import model.Login;
import model.Privilege;
import model.Student;
import model.User;
import repository.LoginRepository;
import repository.StudentRepository;
import repository.UserRepository;
import repository.interfaces.ILoginRepository;
import repository.interfaces.IStudentRepository;
import repository.interfaces.IUserRepository;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserService implements IUserService {

    @Override
    public UserData getUserById(int id) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){

            try{
                IUserRepository userRepository = new UserRepository(manager.getUnwrappedManager());
                ILoginRepository loginRepository = new LoginRepository(manager.getUnwrappedManager());

                Login login = loginRepository.getByUserId(id);
                User user = userRepository.getById(id);

                UserData userData = new UserData();

                userData.setId(id);
                userData.setFirstname(user.getFirstname());
                userData.setLastname(user.getLastname());
                userData.setUsername(login.getUsername());
                userData.setAccessDenied(login.isAccessDenied());
                userData.setPrivileges(user.getPrivileges());

                return userData;
            }
            catch (Exception excpeption){
                throw new ServiceException(ErrorType.INTERNAL_ERROR);
            }
        }
    }

    @Override
    public List<User> getCustodians() throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){
            try{
                IUserRepository userRepository = new UserRepository(manager.getUnwrappedManager());

                List<User> users = userRepository.getAll();

                List<User> custodians = new ArrayList<>();

                for (User user : users){
                    for (Privilege privilege : user.getPrivileges()){
                        if (privilege.getId() == 2 || privilege.getId() == 3){
                            custodians.add(user);
                            break;
                        }
                    }
                }

                return custodians;
            }
            catch (Exception exception){
                throw new ServiceException(ErrorType.INTERNAL_ERROR);
            }
        }
    }

    @Override
    public User getUserBySessionId(String sessionId) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){

            try{
                IUserRepository userRepository = new UserRepository(manager.getUnwrappedManager());
                ILoginRepository loginRepository = new LoginRepository(manager.getUnwrappedManager());

                Login login = loginRepository.getBySessionId(sessionId);

                User user = userRepository.getById(login.getUserId());

                if (user == null){
                    throw new ServiceException(ErrorType.USER_NOT_FOUND);
                }

                return user;
            }
            catch (ServiceException exception){
                throw exception;
            }
            catch (Exception exception){
                throw new ServiceException(ErrorType.INTERNAL_ERROR);
            }
        }
    }

    @Override
    public void addOrUpdateUser(UserPushModel model) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){
            try(MyEntityTransaction transaction = manager.beginTransaction()) {

                try{
                    IUserRepository userRepository = new UserRepository(manager.getUnwrappedManager());
                    ILoginRepository loginRepository = new LoginRepository(manager.getUnwrappedManager());

                    if (model.getId() > 0){
                        updateUser(model, userRepository, loginRepository);
                    }
                    else{
                        createUser(model, userRepository, loginRepository);
                    }

                    transaction.commit();
                }
                catch (ServiceException exception){
                    transaction.rollback();

                    throw exception;
                }
                catch (Exception exception){
                    transaction.rollback();

                    throw new ServiceException(ErrorType.INTERNAL_ERROR);
                }
            }
        }
    }

    @Override
    public void deleteUser(int id) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){
            try(MyEntityTransaction transaction = manager.beginTransaction()){

                try{
                    IUserRepository userRepository = new UserRepository(manager.getUnwrappedManager());
                    IStudentRepository studentRepository = new StudentRepository(manager.getUnwrappedManager());
                    ILoginRepository loginRepository = new LoginRepository(manager.getUnwrappedManager());

                    Student student = studentRepository.getById(id);

                    if (student != null){
                        throw new ServiceException(ErrorType.USER_IS_STUDENT);
                    }

                    User user = userRepository.getById(id);

                    if (user == null){
                        throw new ServiceException(ErrorType.USER_NOT_FOUND);
                    }

                    Login login = loginRepository.getByUserId(id);

                    if (login == null){
                        throw new ServiceException(ErrorType.LOGIN_NOT_FOUND);
                    }

                    userRepository.delete(user);
                    loginRepository.delete(login);

                    transaction.commit();
                }
                catch(ServiceException exception){
                    transaction.rollback();

                    throw exception;
                }
                catch(Exception exception){
                    transaction.rollback();

                    throw new ServiceException(ErrorType.INTERNAL_ERROR);
                }
            }
        }
    }

    private void createUser(UserPushModel model, IUserRepository userRepository, ILoginRepository loginRepository) throws InvalidKeySpecException, NoSuchAlgorithmException {

        User user = new User();

        user.setFirstname(model.getFirstname());
        user.setLastname(model.getLastname());
        user.setPrivileges(getPrivilegesById(model.getPrivilegeIds()));
        userRepository.add(user);

        Login login = new Login();
        login.setUsername(model.getUsername());
        login.setPassword(PasswordManager.encrypt(model.getPassword()));
        login.setUserId(user.getId());
        login.setAccessDenied(model.isAccesDenied());

        loginRepository.add(login);
    }

    private void updateUser(UserPushModel model, IUserRepository userRepository, ILoginRepository loginRepository) throws ServiceException, InvalidKeySpecException, NoSuchAlgorithmException {
        User user = userRepository.getById(model.getId());

        if (user == null){
            throw new ServiceException(ErrorType.USER_NOT_FOUND);
        }

        Login existingLogin = loginRepository.getByUserId(model.getId());

        if (existingLogin == null){
            throw new ServiceException(ErrorType.LOGIN_NOT_FOUND);
        }

        String password;

        if (model.getPassword() != null && !Objects.equals(model.getPassword().trim(), "")){
            password = PasswordManager.encrypt(model.getPassword());
        }
        else{
            password = existingLogin.getPassword();
        }

        if (!Objects.equals(existingLogin.getUsername(), model.getUsername())){
            loginRepository.delete(existingLogin);

            Login login = new Login();

            login.setUserId(model.getId());
            login.setUsername(model.getUsername());
            login.setPassword(password);
            login.setAccessDenied(model.isAccesDenied());

            loginRepository.add(login);
        }
        else{
            existingLogin.setPassword(password);
            existingLogin.setAccessDenied(model.isAccesDenied());
        }

        user.setFirstname(model.getFirstname());
        user.setLastname(model.getLastname());
        user.setPrivileges(getPrivilegesById(model.getPrivilegeIds()));
    }

    private Collection<Privilege> getPrivilegesById(Collection<Integer> privilegeIds){
        List<Privilege> privileges = new ArrayList<>();

        for (int privilegeId : privilegeIds){
            Privilege privilege = new Privilege();
            privilege.setId(privilegeId);

            privileges.add(privilege);
        }

        return privileges;
    }
}
