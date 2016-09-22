package bll;

import api.model.TopicEntryPushModel;
import bll.interfaces.ITopicEntryService;
import common.*;
import model.Student;
import model.Topic;
import model.TopicEntry;
import repository.StudentRepository;
import repository.TopicEntryRepository;
import repository.TopicRepository;
import repository.interfaces.IStudentRepository;
import repository.interfaces.ITopicEntryRepository;
import repository.interfaces.ITopicRepository;

import java.sql.Timestamp;
import java.util.List;

public class TopicEntryService implements ITopicEntryService{
    @Override
    public List<TopicEntry> getTopicEntriesOfTopic(int topicId) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){

            try{
                ITopicEntryRepository topicEntryRepository = new TopicEntryRepository(manager.getUnwrappedManager());

                return topicEntryRepository.getByTopicId(topicId);
            } catch (Exception exception){
                throw new ServiceException(ErrorType.INTERNAL_ERROR);
            }
        }
    }

    @Override
    public void addTopicEntry(TopicEntryPushModel model) throws ServiceException {
        try(MyEntityManager manager = MyEntityManagerFactory.createEntityManager()){
            try(MyEntityTransaction transaction = manager.beginTransaction()){

                try{
                    ITopicEntryRepository topicEntryRepository = new TopicEntryRepository(manager.getUnwrappedManager());
                    ITopicRepository topicRepository = new TopicRepository(manager.getUnwrappedManager());
                    IStudentRepository studentRepository = new StudentRepository(manager.getUnwrappedManager());

                    Topic topic = topicRepository.getById(model.getTopicId());

                    if (topic == null){
                        throw new ServiceException(ErrorType.TOPIC_NOT_FOUND);
                    }

                    Student sender = studentRepository.getById(model.getSenderId());

                    if (sender == null){
                        throw new ServiceException(ErrorType.STUDENT_NOT_FOUND);
                    }

                    TopicEntry topicEntry = new TopicEntry();
                    topicEntry.setTopic(topic);
                    topicEntry.setSender(sender);
                    topicEntry.setCreateDate(new Timestamp(System.currentTimeMillis()));
                    topicEntry.setText(model.getText());

                    topicEntryRepository.add(topicEntry);

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
}
