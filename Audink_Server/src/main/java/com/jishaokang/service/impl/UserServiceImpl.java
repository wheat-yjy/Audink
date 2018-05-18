package com.jishaokang.service.impl;

import com.jishaokang.base.Result;
import com.jishaokang.cache.ResultCache;
import com.jishaokang.dao.UserDAO;
import com.jishaokang.model.dto.User;
import com.jishaokang.service.UserService;
import com.jishaokang.util.Cryptographic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

/**
 * Created by NANA_Final on 2018/1/28.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public Result login(User user) throws NoSuchAlgorithmException {
        User userInDB = userDAO.selectByUsername(user.getUsername());
        if ((userInDB == null) || !(Cryptographic.sha256(user.getUsername()+"{"+user.getPassword()+"}").equals(userInDB.getPassword()))){
            return ResultCache.getDataFailure("wrongUserNameOrPassword");
        }
        return ResultCache.getDataOk(userInDB);
    }

    @Override
    public Result register(User user) throws NoSuchAlgorithmException {
        User userInDB = userDAO.selectByUsername(user.getUsername());
        if (userInDB != null){
            return ResultCache.getDataFailure("UsernameAlreadyExists");
        }
        user.setPassword(Cryptographic.sha256(user.getUsername()+"{"+user.getPassword()+"}"));
        userDAO.insert(user);
        return ResultCache.getDataOk(user);
    }

    @Override
    public Result getUserName(User user) {
        User userInDB = userDAO.selectByUserId(user.getUserId());
        return ResultCache.getDataOk(user.getUsername());
    }

}