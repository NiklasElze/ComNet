package bll;

import api.model.TopicPushModel;
import bll.interfaces.ITopicService;
import common.*;
import model.Group;
import model.Student;
import model.Topic;
import repository.GroupRepository;
import repository.StudentRepository;
import repository.TopicRepository;
import repository.interfaces.IGroupRepository;
import repository.interfaces.IStudentRepository;
import repository.interfaces.ITopicRepository;

import java.sql.Timestamp;
import java.util.List;

public class TopicService implements ITopicService {

    @Override
    public List<Topic> getTopicsOfGroup(int groupId) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){

            try{
                ITopicRepository topicRepository = new TopicRepository(manager.getUnwrappedManager());

                return topicRepository.getByGroupId(groupId);
            } catch (Exception exception){
                throw new ServiceException(ErrorType.INTERNAL_ERROR);
            }
        }
    }

    @Override
    public void addOrUpdateTopic(TopicPushModel model) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){
            try(MyEntityTransaction transaction = manager.beginTransaction()){

                try{
                    IGroupRepository groupRepository = new GroupRepository(manager.getUnwrappedManager());
                    ITopicRepository topicRepository = new TopicRepository(manager.getUnwrappedManager());
                    IStudentRepository studentRepository = new StudentRepository(manager.getUnwrappedManager());

                    if (model.getGroupId() > 0){
                        updateTopic(model, topicRepository);
                    } else{
                        addTopic(model, groupRepository, topicRepository, studentRepository);
                    }

                    transaction.commit();
                } catch (ServiceException exception){
                    transaction.rollback();

                    throw exception;
                } catch (Exception exception){
                    transaction.rollback();

                    throw new ServiceException(ErrorType.INTERNAL_ERROR);
                }
            }
        }
    }

    @Override
    public void deleteTopic(int topicId) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){
            try(MyEntityTransaction transaction = manager.beginTransaction()){

                try{
                    ITopicRepository topicRepository = new TopicRepository(manager.getUnwrappedManager());
                    TopicDeletionService topicDeletionService = new TopicDeletionService(manager.getUnwrappedManager());

                    Topic topic = topicRepository.getById(topicId);

                    if (topic == null){
                        throw new ServiceException(ErrorType.TOPIC_NOT_FOUND);
                    }

                    topicDeletionService.deleteTopic(topic);

                    transaction.commit();
                } catch (ServiceException exception){
                    transaction.rollback();

                    throw exception;
                } catch (Exception exception){
                    transaction.rollback();

                    throw new ServiceException(ErrorType.INTERNAL_ERROR);
                }
            }
        }
    }

    private void updateTopic(TopicPushModel model, ITopicRepository topicRepository) throws ServiceException {
        Topic topic = topicRepository.getById(model.getId());

        if (topic == null){
            throw new ServiceException(ErrorType.TOPIC_NOT_FOUND);
        }

        topic.setName(model.getName());
    }

    private void addTopic(TopicPushModel model, IGroupRepository groupRepository, ITopicRepository topicRepository, IStudentRepository studentRepository) throws ServiceException {
        Group group = groupRepository.getById(model.getGroupId());

        if (group == null){
            throw new ServiceException(ErrorType.GROUP_NOT_FOUND);
        }

        Student creator = studentRepository.getById(model.getCreatorId());

        if (creator == null){
            throw new ServiceException(ErrorType.STUDENT_NOT_FOUND);
        }

        Topic topic = new Topic();
        topic.setName(model.getName());
        topic.setCreateDate(new Timestamp(System.currentTimeMillis()));
        topic.setGroup(group);
        topic.setCreator(creator);

        topicRepository.add(topic);
    }
}
