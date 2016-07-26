package repository.interfaces;

import model.SeminarGroup;

import java.util.List;

public interface ISeminarGroupRepository {

    List<SeminarGroup> getAll();

    SeminarGroup getById(int id);

    void add(SeminarGroup seminarGroup);
    void delete(SeminarGroup seminarGroup);
}
