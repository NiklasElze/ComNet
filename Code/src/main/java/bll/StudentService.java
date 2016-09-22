package bll;

import api.model.StudentData;
import api.model.StudentPushModel;
import bll.interfaces.IMessageDeletionService;
import bll.interfaces.IStudentService;
import bll.interfaces.ITopicEntryDeletionService;
import common.*;
import model.*;
import repository.LoginRepository;
import repository.SeminarGroupRepository;
import repository.StudentRepository;
import repository.UserRepository;
import repository.interfaces.ILoginRepository;
import repository.interfaces.ISeminarGroupRepository;
import repository.interfaces.IStudentRepository;
import repository.interfaces.IUserRepository;

import javax.persistence.EntityManager;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentService implements IStudentService {

    @Override
    public List<Student> getStudentsOfSeminarGroup(int seminarGroupId) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){

            try{
                IStudentRepository studentRepository = new StudentRepository(manager.getUnwrappedManager());

                return studentRepository.getBySeminarGroupId(seminarGroupId);
            }
            catch (Exception exception){
                throw new ServiceException(ErrorType.INTERNAL_ERROR);
            }
        }
    }

    @Override
    public List<Student> getStudentList() throws ServiceException {

        try (MyEntityManager manager = MyEntityManagerFactory.createEntityManager()) {

            try {
                IStudentRepository studentRepository = new StudentRepository(manager.getUnwrappedManager());

                return studentRepository.getAll();
            }
            catch (Exception exception) {
                throw new ServiceException(ErrorType.INTERNAL_ERROR);
            }
        }
    }

    @Override
    public StudentData getStudentData(int id) throws ServiceException {
        try (MyEntityManager manager = MyEntityManagerFactory.createEntityManager()) {

            try {
                StudentData data = new StudentData();

                ILoginRepository loginRepository = new LoginRepository(manager.getUnwrappedManager());
                IStudentRepository studentRepository = new StudentRepository(manager.getUnwrappedManager());

                Student student = studentRepository.getById(id);

                if (student == null) {
                    throw new ServiceException(ErrorType.STUDENT_NOT_FOUND);
                }

                Login login = loginRepository.getByUserId(id);

                if (login == null){
                    throw new ServiceException(ErrorType.LOGIN_NOT_FOUND);
                }

                data.setUsername(login.getUsername());
                data.setFirstname(student.getUserdata().getFirstname());
                data.setLastname(student.getUserdata().getLastname());
                data.setSeminarGroup(student.getSeminarGroup());
                data.setAccessDenied(login.isAccessDenied());
                data.setId(student.getUserdata().getId());

                return data;
            } catch (ServiceException exception) {
                throw exception;
            }
            catch (Exception exception){
                throw new ServiceException(ErrorType.INTERNAL_ERROR);
            }
        }
    }

    @Override
    public void createOrUpdateStudent(StudentPushModel model) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){

            try(MyEntityTransaction transaction = manager.beginTransaction()){

                try{
                    IStudentRepository studentRepository = new StudentRepository(manager.getUnwrappedManager());

                    Student student = studentRepository.getById(model.getId());

                    if (student == null){
                        createStudent(model, manager.getUnwrappedManager());
                    }
                    else{
                        updateStudent(model, manager.getUnwrappedManager());
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
    public void deleteStudent(int id) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){

            try(MyEntityTransaction transaction = manager.beginTransaction()){

                try{
                    IStudentRepository studentRepository = new StudentRepository(manager.getUnwrappedManager());
                    ILoginRepository loginRepository = new LoginRepository(manager.getUnwrappedManager());
                    IMessageDeletionService messageDeletionService = new MessageDeletionService(manager.getUnwrappedManager());
                    IUserRepository userRepository = new UserRepository(manager.getUnwrappedManager());
                    ITopicEntryDeletionService topicEntryDeletionService = new TopicEntryDeletionService(manager.getUnwrappedManager());

                    Student student = studentRepository.getById(id);

                    if (student == null){
                        throw new ServiceException(ErrorType.STUDENT_NOT_FOUND);
                    }

                    Login login = loginRepository.getByUserId(id);

                    if (login == null){
                        throw new ServiceException(ErrorType.LOGIN_NOT_FOUND);
                    }

                    User userdata = student.getUserdata();
                    loginRepository.delete(login);

                    messageDeletionService.deleteMessagesOfStudent(student);
                    topicEntryDeletionService.deleteEntriesOfStudent(student);

                    studentRepository.delete(student);
                    userRepository.delete(userdata);
                    transaction.commit();
                }
                catch (ServiceException exception){
                    throw exception;
                }
                catch (Exception exception){
                    transaction.rollback();

                    throw new ServiceException(ErrorType.INTERNAL_ERROR);
                }
            }
        }
    }

    private void createStudent(StudentPushModel model, EntityManager manager) throws InvalidKeySpecException, NoSuchAlgorithmException, ServiceException {

        IStudentRepository studentRepository = new StudentRepository(manager);
        ILoginRepository loginRepository = new LoginRepository(manager);
        ISeminarGroupRepository seminarGroupRepository = new SeminarGroupRepository(manager);
        IUserRepository userRepository = new UserRepository(manager);

        Login existingLogin = loginRepository.getByUsername(model.getUsername());

        if (existingLogin != null){
            throw new ServiceException(ErrorType.LOGIN_ALREADY_EXISTS);
        }

        Student student = new Student();
        User userdata = new User();
        Privilege studentPrivilege = new Privilege();
        ArrayList<Privilege> privileges = new ArrayList<>();
        studentPrivilege.setId(1);
        privileges.add(studentPrivilege);

        userdata.setFirstname(model.getFirstname());
        userdata.setLastname(model.getLastname());
        userdata.setPrivileges(privileges);

        userRepository.add(userdata);

        student.setUserdata(userdata);

        SeminarGroup seminarGroup = seminarGroupRepository.getById(model.getSeminarGroupId());
        student.setSeminarGroup(seminarGroup);

        studentRepository.add(student);

        Login login = new Login();
        login.setUsername(model.getUsername());
        login.setUserId(student.getUserdata().getId());

        String encryptedPassword = PasswordManager.encrypt(model.getPassword());
        login.setPassword(encryptedPassword);

        loginRepository.add(login);
    }

    private void updateStudent(StudentPushModel model, EntityManager manager) throws ServiceException, InvalidKeySpecException, NoSuchAlgorithmException {

        IStudentRepository studentRepository = new StudentRepository(manager);
        ILoginRepository loginRepository = new LoginRepository(manager);
        ISeminarGroupRepository seminarGroupRepository = new SeminarGroupRepository(manager);

        Student student = studentRepository.getById(model.getId());

        if (student == null){
            throw new ServiceException(ErrorType.STUDENT_NOT_FOUND);
        }

        Login existingLogin = loginRepository.getByUserId(model.getId());

        if (existingLogin == null){
            throw new ServiceException(ErrorType.LOGIN_NOT_FOUND);
        }

        student.getUserdata().setFirstname(model.getFirstname());
        student.getUserdata().setLastname(model.getLastname());

        SeminarGroup seminarGroup = seminarGroupRepository.getById(model.getSeminarGroupId());
        student.setSeminarGroup(seminarGroup);

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
            login.setAccessDenied(model.isAccessDenied());

            loginRepository.add(login);
        }
        else{
            existingLogin.setPassword(password);
            existingLogin.setAccessDenied(model.isAccessDenied());
        }
    }
}
