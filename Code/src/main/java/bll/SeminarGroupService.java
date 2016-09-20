package bll;

import api.model.SeminarGroupPushModel;
import bll.interfaces.ISeminarGroupService;
import common.*;
import model.Conversation;
import model.SeminarGroup;
import model.Student;
import repository.ConversationRepository;
import repository.SeminarGroupRepository;
import repository.StudentRepository;
import repository.interfaces.IConversationRepository;
import repository.interfaces.ISeminarGroupRepository;
import repository.interfaces.IStudentRepository;

import java.util.ArrayList;
import java.util.List;

public class SeminarGroupService implements ISeminarGroupService {

    @Override
    public List<SeminarGroup> getContactListOfNewConversation(int currentUserId) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){

            try{
                IStudentRepository studentRepository = new StudentRepository(manager.getUnwrappedManager());
                ISeminarGroupRepository seminarGroupRepository = new SeminarGroupRepository(manager.getUnwrappedManager());

                Student student = studentRepository.getById(currentUserId);

                if (student == null){
                    throw new ServiceException(ErrorType.STUDENT_NOT_FOUND);
                }

                List<SeminarGroup> seminarGroups = seminarGroupRepository.getAll();

                List<Student> students = new ArrayList<>();
                students.add(student);

                excludeStudentsFromSeminarGroups(seminarGroups, students);

                return seminarGroups;
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
    public List<SeminarGroup> getContactListOfConversation(int conversationId) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){

            try{
                IConversationRepository conversationRepository = new ConversationRepository(manager.getUnwrappedManager());
                ISeminarGroupRepository seminarGroupRepository = new SeminarGroupRepository(manager.getUnwrappedManager());

                Conversation conversation = conversationRepository.getById(conversationId);

                if (conversation == null){
                    throw new ServiceException(ErrorType.CONVERSATION_NOT_FOUND);
                }

                List<SeminarGroup> seminarGroups = seminarGroupRepository.getAll();

                excludeStudentsFromSeminarGroups(seminarGroups, (List<Student>) conversation.getMembers());

                return seminarGroups;
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
    public List<SeminarGroup> getSeminarGroupList() throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){

            try{
                ISeminarGroupRepository seminarGroupRepository = new SeminarGroupRepository(manager.getUnwrappedManager());

                return seminarGroupRepository.getAll();
            }
            catch (Exception exception){
                throw new ServiceException(ErrorType.INTERNAL_ERROR);
            }
        }
    }

    @Override
    public void addOrUpdateSeminarGroup(SeminarGroupPushModel model) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){

            try(MyEntityTransaction transaction = manager.beginTransaction()){

                try{
                    ISeminarGroupRepository seminarGroupRepository = new SeminarGroupRepository(manager.getUnwrappedManager());

                    SeminarGroup seminarGroup = seminarGroupRepository.getById(model.getId());

                    if (seminarGroup == null){
                        addSeminarGroup(model, seminarGroupRepository);
                    }
                    else{
                        updateSeminarGroup(model, seminarGroup);
                    }

                    transaction.commit();
                }
                catch (Exception exception){
                    transaction.rollback();

                    throw new ServiceException(ErrorType.INTERNAL_ERROR);
                }
            }
        }
    }

    @Override
    public void deleteSeminarGroup(int id) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()) {

            try (MyEntityTransaction transaction = manager.beginTransaction()) {

                try {
                    ISeminarGroupRepository seminarGroupRepository = new SeminarGroupRepository(manager.getUnwrappedManager());

                    SeminarGroup seminarGroup = seminarGroupRepository.getById(id);

                    if (seminarGroup == null){
                        throw new ServiceException(ErrorType.SEMINARGROUP_NOT_FOUND);
                    }

                    if (seminarGroup.getStudents().size() > 0){
                        throw new ServiceException(ErrorType.SEMINARGROUP_CONTAINS_STUDENTS);
                    }

                    seminarGroupRepository.delete(seminarGroup);
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

    private void addSeminarGroup(SeminarGroupPushModel model, ISeminarGroupRepository seminarGroupRepository){

        SeminarGroup seminarGroup = new SeminarGroup();

        SeminarGroup fatherGroup = null;

        if (model.getFatherGroupId() != null && model.getFatherGroupId() > 0) {

            fatherGroup = new SeminarGroup();
            fatherGroup.setId(model.getFatherGroupId());
        }

        seminarGroup.setName(model.getName());
        seminarGroup.setFatherGroup(fatherGroup);

        seminarGroupRepository.add(seminarGroup);
    }

    private void updateSeminarGroup(SeminarGroupPushModel model, SeminarGroup existingGroup){

        SeminarGroup fatherGroup = null;

        if (model.getFatherGroupId() != null && model.getFatherGroupId() > 0){

            fatherGroup = new SeminarGroup();
            fatherGroup.setId(model.getFatherGroupId());
        }

        existingGroup.setFatherGroup(fatherGroup);
        existingGroup.setName(model.getName());
    }

    private void excludeStudentsFromSeminarGroups(List<SeminarGroup> seminarGroups, List<Student> studentsToExclude){
        for (SeminarGroup seminarGroup : seminarGroups){
            List<Student> students = new ArrayList<>();

            for (Student student : seminarGroup.getStudents()){
                if (!studentsToExclude.contains(student)){
                    students.add(student);
                }
            }

            seminarGroup.setStudents(students);
        }
    }
}
