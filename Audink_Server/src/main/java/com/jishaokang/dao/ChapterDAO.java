package com.jishaokang.dao;

import com.jishaokang.model.dto.Chapter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by NANA_Final on 2017/7/21.
 */
@Repository
public interface ChapterDAO {


    ArrayList<Chapter> selectByBookId(Integer bookId);

    void insert(Chapter chapter);
}