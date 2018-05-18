package com.jishaokang.service.impl;

import com.jishaokang.base.Result;
import com.jishaokang.cache.ResultCache;
import com.jishaokang.dao.UserDAO;
import com.jishaokang.model.dto.User;
import com.jishaokang.service.UserService;
import com.jishaokang.service.UserWebService;
import com.jishaokang.util.FileUtil;
import com.jishaokang.util.Cryptographic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by NANA_Final on 2018/1/28.
 */
@Service
public class UserWebServiceImpl implements UserWebService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public Result login(User user, HttpSession session, HttpServletResponse response) throws NoSuchAlgorithmException {
        User userInDB = userDAO.selectByUsername(user.getUsername());
        if ((userInDB == null) || !(Cryptographic.sha256(user.getUsername()+"{"+user.getPassword()+"}").equals(userInDB.getPassword()))){
            return ResultCache.getDataFailure("wrongUserNameOrPassword");
        }
        session.setAttribute("userId",userInDB.getUserId());
        return ResultCache.OK;
    }

    @Override
    public Result register(User user) throws NoSuchAlgorithmException {
        User userInDB = userDAO.selectByUsername(user.getUsername());
        if (userInDB != null){
            return ResultCache.getDataFailure("UsernameAlreadyExists");
        }
        user.setPassword(Cryptographic.sha256(user.getUsername()+"{"+user.getPassword()+"}"));
        userDAO.insert(user);
        return ResultCache.OK;
    }

    @Override
    public Result getUserName(HttpSession session) {
        int userId =(Integer) session.getAttribute("userId");
        User user = userDAO.selectByUserId(userId);
        return ResultCache.getDataOk(user.getUsername());
    }

    @Override
    public Result unlogin(HttpSession session, HttpServletResponse response) {
        session.removeAttribute("userId");
        return ResultCache.OK;
    }

}