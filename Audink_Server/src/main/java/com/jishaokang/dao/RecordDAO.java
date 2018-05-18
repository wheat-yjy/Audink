package com.jishaokang.dao;


import com.jishaokang.model.dto.Record;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by NANA_Final on 2017/7/21.
 */
@Repository
public interface RecordDAO {

    List<Record> select(int userId);

    void insert(Record record);

    void delete(int recordId);

    void updateFinish(int recordId);

    int countAll(int userId);
}