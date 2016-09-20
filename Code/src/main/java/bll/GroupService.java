package bll;

import api.model.GroupPushModel;
import bll.interfaces.IGroupService;
import common.*;
import model.Group;
import model.Student;
import model.Topic;
import repository.GroupRepository;
import repository.StudentRepository;
import repository.interfaces.IGroupRepository;
import repository.interfaces.IStudentRepository;

import java.util.ArrayList;
import java.util.List;

public class GroupService implements IGroupService {
    @Override
    public List<Group> getGroupsOfStudent(int studentId) throws ServiceException {
        try (MyEntityManager manager = MyEntityManagerFactory.createEntityManager()) {
            try {
                IGroupRepository groupRepository = new GroupRepository(manager.getUnwrappedManager());

                return groupRepository.getByStudentId(studentId);
            } catch (Exception exception) {
                throw new ServiceException(ErrorType.INTERNAL_ERROR);
            }
        }
    }

    @Override
    public Group getGroupById(int id) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){
            try{
                IGroupRepository groupRepository = new GroupRepository(manager.getUnwrappedManager());

                return groupRepository.getById(id);
            } catch (Exception exception){
                throw new ServiceException(ErrorType.INTERNAL_ERROR);
            }
        }
    }

    @Override
    public void addOrUpdateGroup(GroupPushModel model) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){
            try(MyEntityTransaction transaction = manager.beginTransaction()){
                try{
                    IGroupRepository groupRepository = new GroupRepository(manager.getUnwrappedManager());
                    IStudentRepository studentRepository = new StudentRepository(manager.getUnwrappedManager());

                    if (model.getId() > 0){
                        updateGroup(model, groupRepository, studentRepository);
                    } else{
                        addGroup(model, groupRepository, studentRepository);
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
    public void deleteGroup(int id) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){
            try(MyEntityTransaction transaction = manager.beginTransaction()){

                try{
                    IGroupRepository groupRepository = new GroupRepository(manager.getUnwrappedManager());
                    TopicDeletionService topicDeletionService = new TopicDeletionService(manager.getUnwrappedManager());

                    Group group = groupRepository.getById(id);

                    if (group == null){
                        throw new ServiceException(ErrorType.GROUP_NOT_FOUND);
                    }

                    for (Topic topic : group.getTopics()){
                        topicDeletionService.deleteTopic(topic);
                    }

                    groupRepository.delete(group);

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

    private void updateGroup(GroupPushModel model, IGroupRepository groupRepository, IStudentRepository studentRepository) throws ServiceException {
        Group group = groupRepository.getById(model.getId());

        if (group == null){
            throw new ServiceException(ErrorType.GROUP_NOT_FOUND);
        }

        group.setName(model.getName());

        List<Student> newMembers = new ArrayList<>();

        for (int id : model.getMemberIds()){
            Student member = studentRepository.getById(id);

            if (member == null){
                throw new ServiceException(ErrorType.MEMBER_NOT_FOUND);
            }

            newMembers.add(member);
        }

        for (Student member : newMembers){
            if (!group.getMembers().contains(member)){
                group.getMembers().add(member);
            }
        }

        for (Student member : group.getMembers()){
            if (!newMembers.contains(member)){
                group.getMembers().remove(member);
            }
        }

        List<Student> newAdministrators = new ArrayList<>();

        for (int id : model.getAdministratorIds()){
            Student admin = studentRepository.getById(id);

            if (admin == null){
                throw new ServiceException(ErrorType.GROUP_ADMINSTRATOR_NOT_FOUND);
            }

            newAdministrators.add(admin);
        }

        for (Student admin : newAdministrators){
            if (!group.getAdministrators().contains(admin)){
                group.getAdministrators().add(admin);
            }
        }

        for (Student admin : group.getAdministrators()){
            if (!newMembers.contains(admin)){
                group.getAdministrators().remove(admin);
            }
        }
    }

    private void addGroup(GroupPushModel model, IGroupRepository groupRepository, IStudentRepository studentRepository) throws ServiceException {
        Group group = new Group();
        group.setName(model.getName());

        for (int memberId : model.getMemberIds()){
            Student member = studentRepository.getById(memberId);

            if (member == null){
                throw new ServiceException(ErrorType.MEMBER_NOT_FOUND);
            }

            group.getMembers().add(member);
        }

        for (int administratorId : model.getAdministratorIds()){
            Student admin = studentRepository.getById(administratorId);

            if (admin == null){
                throw new ServiceException(ErrorType.GROUP_ADMINSTRATOR_NOT_FOUND);
            }

            group.getAdministrators().add(admin);
        }

        groupRepository.add(group);
    }
}
