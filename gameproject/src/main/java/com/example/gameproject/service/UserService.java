package com.example.gameproject.service;

import com.example.gameproject.HttpUtil;
import com.example.gameproject.api.UserRepository;
import com.example.gameproject.bean.request.RegisterRequest;
import com.example.gameproject.bean.response.HttpResult;
import com.example.gameproject.bean.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public HttpResult register(RegisterRequest request) {
        HttpResult<User> httpResult = new HttpResult<>();
        User userFound = findUser(request.getUserName(),request.getMailAddress());
        if(userFound != null){
            httpResult.setStatus(HttpUtil.FAIL);
            httpResult.setMessage("该用户名或邮箱已被注册");
            return httpResult;
        }
        User user = new User();
        user.setUserName(request.getUserName());
        user.setMailAddress(request.getMailAddress());
        user.setPassword(request.getPassword());
        userRepository.save(user);
        httpResult.setStatus(HttpUtil.SUCCESS);
        httpResult.setMessage("注册成功");
        return httpResult;
    }

    public HttpResult login(User user) {
        HttpResult<User> httpResult = new HttpResult<>();
        User userFound = findUser(user.getUserName(),user.getUserName());
        if(userFound == null){
            httpResult.setStatus(HttpUtil.FAIL);
            httpResult.setMessage("没有这个用户");
            return httpResult;
        }
        if(userFound.getPassword().equals(user.getPassword())){
            httpResult.setStatus(HttpUtil.SUCCESS);
            httpResult.setMessage("登录成功");
            httpResult.setData(userFound);

        }else {
            httpResult.setStatus(HttpUtil.FAIL);
            httpResult.setMessage("密码错误");
        }
        return httpResult;
    }

    /**
     * 修改密码
     * @param request
     * @return
     */
    public HttpResult changePassword(RegisterRequest request) {
        HttpResult httpResult = new HttpResult();
        User userFound = findUser(request.getMailAddress());
        if(userFound == null){
            httpResult.setStatus(HttpUtil.FAIL);
            httpResult.setMessage("该邮箱未被注册");
            return httpResult;
        }
        userFound.setPassword(request.getPassword());
        userRepository.save(userFound);
        httpResult.setStatus(HttpUtil.SUCCESS);
        httpResult.setMessage("修改密码成功");
        return httpResult;
    }


    public User findUser(String userName, String mailAddress) {
        List<User> result = userRepository.findByUserNameOrMailAddress(userName,mailAddress);
        if(result.size() != 0){
            return result.get(0);
        }
        return null;
    }

    public User findUser(String mailAddress) {
        List<User> result = userRepository.findByMailAddress(mailAddress);
        if(result.size() != 0){
            return result.get(0);
        }
        return null;
    }

}
