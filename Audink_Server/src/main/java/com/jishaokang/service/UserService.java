package com.jishaokang.service;


import com.jishaokang.base.Result;
import com.jishaokang.model.dto.User;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;


/**
 * Created by NANA_Final on 2017/7/21.
 */

public interface UserService {

    

    Result login(User user) throws NoSuchAlgorithmException;

    Result register(User user) throws NoSuchAlgorithmException;

    Result getUserName(User user);
}