package bll;

import bll.interfaces.IGroupService;
import common.ErrorType;
import common.MyEntityManager;
import common.MyEntityManagerFactory;
import common.ServiceException;
import model.Group;
import repository.GroupRepository;
import repository.interfaces.IGroupRepository;

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
    public void addOrUpdateGroup(Group group) {

    }

    @Override
    public void deleteGroup(int id) {

    }
}
