package com.jishaokang.dao;

import com.jishaokang.model.dto.User;
import org.springframework.stereotype.Repository;

/**
 * Created by NANA_Final on 2017/7/21.
 */
@Repository
public interface UserDAO {

    User selectByUsername(String username);
    void insert(User user);
    User selectByUserId(int userId);
}