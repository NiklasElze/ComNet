package bll;

import api.model.SeminarGroupPushModel;
import bll.interfaces.ISeminarGroupService;
import common.*;
import model.SeminarGroup;
import repository.SeminarGroupRepository;
import repository.interfaces.ISeminarGroupRepository;

import java.util.List;

public class SeminarGroupService implements ISeminarGroupService {

    @Override
    public List<SeminarGroup> getSeminarGroupList() throws ServiceException {
        try(MyEntityManager manager = EntityManagerHandler.createEntityManager()){

            try{
                ISeminarGroupRepository seminarGroupRepository = new SeminarGroupRepository(manager.getManager());

                return seminarGroupRepository.getAll();
            }
            catch (Exception exception){
                throw new ServiceException(ErrorType.INTERNAL_ERROR);
            }
        }
    }

    @Override
    public void addOrUpdateSeminarGroup(SeminarGroupPushModel model) throws ServiceException {
        try(MyEntityManager manager = EntityManagerHandler.createEntityManager()){

            try(MyEntityTransaction transaction = manager.beginTransaction()){

                try{
                    ISeminarGroupRepository seminarGroupRepository = new SeminarGroupRepository(manager.getManager());

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
        try(MyEntityManager manager = EntityManagerHandler.createEntityManager()) {

            try (MyEntityTransaction transaction = manager.beginTransaction()) {

                try {
                    ISeminarGroupRepository seminarGroupRepository = new SeminarGroupRepository(manager.getManager());

                    SeminarGroup seminarGroup = seminarGroupRepository.getById(id);

                    if (seminarGroup == null){
                        throw new ServiceException(ErrorType.GROUP_NOT_FOUND);
                    }

                    if (seminarGroup.getStudents().size() > 0){
                        throw new ServiceException(ErrorType.GROUP_CONTAINS_STUDENTS);
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
}
