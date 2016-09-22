package bll;

import bll.interfaces.ITopicEntryDeletionService;
import model.Group;
import model.Student;
import model.Topic;
import model.TopicEntry;
import repository.GroupRepository;
import repository.TopicEntryRepository;
import repository.interfaces.IGroupRepository;
import repository.interfaces.ITopicEntryRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class TopicEntryDeletionService implements ITopicEntryDeletionService{

    private EntityManager m_EntityManager;

    public TopicEntryDeletionService(EntityManager manager){
        m_EntityManager = manager;
    }

    @Override
    public void deleteEntriesOfTopic(Topic topic) {
        ITopicEntryRepository topicEntryRepository = new TopicEntryRepository(m_EntityManager);

        for (TopicEntry entry : topic.getEntries()){
            topicEntryRepository.delete(entry);
        }
    }

    @Override
    public void deleteEntriesOfStudent(Student student) {
        ITopicEntryRepository topicEntryRepository = new TopicEntryRepository(m_EntityManager);
        IGroupRepository groupRepository = new GroupRepository(m_EntityManager);
        TopicDeletionService topicDeletionService = new TopicDeletionService(m_EntityManager);

        List<TopicEntry> entries = topicEntryRepository.getByStudentId(student.getUserdata().getId());
        List<Group> groupsToDelete = new ArrayList<>();

        for (TopicEntry entry : entries){

            if (!studentIsLastInGroup(entry.getTopic().getGroup(), student)){
                entry.setSender(null);

                if (entry.getTopic().getGroup().getMembers().contains(student)){
                    entry.getTopic().getGroup().getMembers().remove(student);
                }
            } else{
                if (!groupsToDelete.contains(entry.getTopic().getGroup())){
                    groupsToDelete.add(entry.getTopic().getGroup());
                }
            }
        }

        for (Group group : groupsToDelete){
            for (Topic topic : group.getTopics()){
                topicDeletionService.deleteTopic(topic);
            }

            groupRepository.delete(group);
        }
    }

    private boolean studentIsLastInGroup(Group group, Student student){
        return group.getMembers().size() == 1 && group.getMembers().contains(student);
    }
}
