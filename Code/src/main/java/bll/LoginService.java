package bll;

import api.model.Identity;
import bll.interfaces.ILoginService;
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

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class LoginService implements ILoginService{

    @Override
    public Identity loginUser(String username, String password) throws ServiceException {

        try(MyEntityManager manager = EntityManagerHandler.createEntityManager()){

            try(MyEntityTransaction transaction = manager.beginTransaction()){

                try{
                    ILoginRepository loginRepository = new LoginRepository(manager.getManager());
                    IStudentRepository studentRepository = new StudentRepository(manager.getManager());
                    IUserRepository userRepository = new UserRepository(manager.getManager());

                    Login login = loginRepository.getByUsername(username);

                    if (login == null || !PasswordManager.validate(password, login.getPassword())){
                        throw new ServiceException(ErrorType.INVALID_LOGIN_DATA);
                    }

                    String sessionId = createSessionId(login, loginRepository);
                    login.setSessionId(sessionId);
                    updateExpirationDate(login);
                    transaction.commit();

                    Identity identity = new Identity();

                    Student student = studentRepository.getById(login.getUserId());

                    if (student == null){
                        User user = userRepository.getById(login.getUserId());

                        identity.setSessionId(sessionId);
                        identity.setFirstname(user.getFirstname());
                        identity.setLastname(user.getLastname());
                        identity.setPrivileges((List<Privilege>) user.getPrivileges());

                        return identity;
                    }

                    identity.setFirstname(student.getUserdata().getFirstname());
                    identity.setLastname(student.getUserdata().getLastname());
                    identity.setSessionId(sessionId);
                    identity.setSeminarGroup(student.getSeminarGroup());
                    identity.setPrivileges((List<Privilege>) student.getUserdata().getPrivileges());

                    return identity;
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
    public boolean sessionIdIsValid(String sessionId) {

        try(MyEntityManager manager = EntityManagerHandler.createEntityManager()) {

            try(MyEntityTransaction transaction = manager.beginTransaction()) {

                ILoginRepository loginRepository = new LoginRepository(manager.getManager());

                Login login = loginRepository.getBySessionId(sessionId);

                if (login == null){
                    transaction.rollback();
                    return false;
                }

                Timestamp currentTime = new Timestamp(System.currentTimeMillis());

                if (login.getExpirationDate().getTime() < currentTime.getTime()){
                    transaction.rollback();
                    return false;
                }

                updateExpirationDate(login);
                transaction.commit();
                return true;
            }

        }
    }

    private String createSessionId(Login login, ILoginRepository loginRepository){

        SecureRandom random = new SecureRandom();
        String sessionId = new BigInteger(130, random).toString(32);

        List<Login> logins = loginRepository.getAll();

        for (Login loginEntry : logins){
            if (Objects.equals(loginEntry.getSessionId(), sessionId)){
                sessionId = createSessionId(login, loginRepository);
                break;
            }
        }

        return sessionId;
    }

    private void updateExpirationDate(Login login){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MINUTE, 60);
        Timestamp expirationDate = new Timestamp(calendar.getTimeInMillis());

        login.setExpirationDate(expirationDate);
    }
}
