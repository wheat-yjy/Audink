package com.jishaokang.dao;

import com.jishaokang.model.dto.Collect;
import com.jishaokang.model.dto.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by NANA_Final on 2017/7/21.
 */
@Repository
public interface CollectDAO {


    ArrayList<Collect> selectByUserId(Integer userId);

    void insert(Collect collect);

    void insertView(Collect collect);

    void delete(Collect collect);


}