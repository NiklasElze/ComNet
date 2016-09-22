package bll.interfaces;

import model.Student;
import model.Topic;

public interface ITopicEntryDeletionService {
    void deleteEntriesOfTopic(Topic topic);
    void deleteEntriesOfStudent(Student student);
}
