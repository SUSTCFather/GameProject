package com.example.gameproject.service;

import com.example.gameproject.Constant;
import com.example.gameproject.mapper.UserRepository;
import com.example.gameproject.bean.model.GameRecord;
import com.example.gameproject.bean.request.FriendRequest;
import com.example.gameproject.bean.request.UserRequest;
import com.example.gameproject.bean.response.HttpResult;
import com.example.gameproject.bean.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 注册
     * @param request
     * @return
     */
    public HttpResult register(UserRequest request) {
        HttpResult<User> httpResult = new HttpResult<>();
        User userFound = findUser(request.getUserName(),request.getMailAddress());
        if(userFound != null){
            httpResult.setStatus(Constant.FAIL);
            httpResult.setMessage("该用户名或邮箱已被注册");
            return httpResult;
        }
        User user = new User();
        user.setUserName(request.getUserName());
        user.setMailAddress(request.getMailAddress());
        user.setPassword(request.getPassword());
        userRepository.save(user);
        httpResult.setStatus(Constant.SUCCESS);
        httpResult.setMessage("注册成功");
        return httpResult;
    }

    /**
     * 登录
     * @param request
     * @return
     */
    public HttpResult login(UserRequest request) {
        HttpResult<User> httpResult = new HttpResult<>();
        User userFound = findUser(request.getUserName(),request.getUserName());
        if(userFound == null){
            httpResult.setStatus(Constant.FAIL);
            httpResult.setMessage("没有这个用户");
            return httpResult;
        }
        if(userFound.getPassword().equals(request.getPassword())){
            httpResult.setStatus(Constant.SUCCESS);
            httpResult.setMessage("登录成功");
            httpResult.setData(userFound);

        }else {
            httpResult.setStatus(Constant.FAIL);
            httpResult.setMessage("密码错误");
        }
        return httpResult;
    }

    /**
     * 修改密码
     * @param request
     * @return
     */
    public HttpResult changePassword(UserRequest request) {
        HttpResult httpResult = new HttpResult();
        User userFound = findUserByMail(request.getMailAddress());
        if(userFound == null){
            httpResult.setStatus(Constant.FAIL);
            httpResult.setMessage("该邮箱未被注册");
            return httpResult;
        }
        userFound.setPassword(request.getPassword());
        userRepository.save(userFound);
        httpResult.setStatus(Constant.SUCCESS);
        httpResult.setMessage("修改密码成功");
        return httpResult;
    }

    /**
     * 获取个人信息
     * @param userId
     * @return
     */
    public HttpResult getPersonInfo(long userId) {
        HttpResult<User> result = new HttpResult<>();
        User user = userRepository.findByUserId(userId);
        if(user != null) {
            result.setStatus(Constant.SUCCESS);
            result.setData(user);
        }else {
            result.setStatus(Constant.FAIL);
            result.setMessage("id错误");
        }
        return result;
    }

    /**
     * 获取用户历史战绩
     * @param id
     * @return
     */
    public HttpResult getHistory(Long id) {
        HttpResult<List<GameRecord>> result = new HttpResult<>();
        User user = userRepository.findByUserId(id);
        if(user == null) {
            result.setStatus(Constant.FAIL);
            result.setMessage("id错误");
            return result;
        }
        List<GameRecord> recordList = new LinkedList<>();
        recordList.addAll(user.getLoseGames());
        recordList.addAll(user.getWinGames());
        Collections.sort(recordList, new Comparator<GameRecord>() {
            @Override
            public int compare(GameRecord o1, GameRecord o2) {
                return -o1.getGameTime().compareTo(o2.getGameTime());
            }
        });


        result.setStatus(Constant.SUCCESS);
        result.setData(recordList);
        return result;
    }

    /**
     * 获取排行榜
     * @return
     */
    public HttpResult getRankList() {
        HttpResult<List<User>> result = new HttpResult<>();
        Sort sort = new Sort(Sort.Direction.DESC, "score");
        PageRequest pageRequest = PageRequest.of(0,15,sort);
        result.setData(userRepository.findAllBy(pageRequest));
        result.setStatus(Constant.SUCCESS);
        return result;
    }

    /**
     * 通过用户名或邮箱寻找用户
     * @param userName
     * @param mailAddress
     * @return
     */
    public User findUser(String userName, String mailAddress) {
        List<User> result = userRepository.findByUserNameOrMailAddress(userName,mailAddress);
        if(result.size() != 0){
            return result.get(0);
        }
        return null;
    }

    /**
     * 通过邮箱寻找用户
     * @param mailAddress
     * @return
     */
    public User findUserByMail(String mailAddress) {
        List<User> result = userRepository.findByMailAddress(mailAddress);
        if(result.size() != 0){
            return result.get(0);
        }
        return null;
    }

}
