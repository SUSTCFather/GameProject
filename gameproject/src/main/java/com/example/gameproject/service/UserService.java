package com.example.gameproject.service;

import com.example.gameproject.Constant;
import com.example.gameproject.api.RelationRepository;
import com.example.gameproject.api.UserRepository;
import com.example.gameproject.bean.model.Relationship;
import com.example.gameproject.bean.request.FriendRequest;
import com.example.gameproject.bean.request.UserRequest;
import com.example.gameproject.bean.response.HttpResult;
import com.example.gameproject.bean.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RelationRepository relationRepository;

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
     * 添加好友
     * @param request
     * @return
     */
    public HttpResult addFriend(FriendRequest request) {
        HttpResult<List<User>> httpResult = new HttpResult<>();
        User fromUser = userRepository.findByUserName(request.getFromUserName());
        User toUser = userRepository.findByUserName(request.getToUserName());
        if(fromUser.getUserId() == toUser.getUserId()) {
            httpResult.setStatus(Constant.FAIL);
            httpResult.setMessage("不能添加自己为好友");
            return httpResult;
        }
        if(toUser == null || fromUser == null) {
            httpResult.setStatus(Constant.FAIL);
            httpResult.setMessage("查无此人");
            return httpResult;
        }
        //查重
        List<User> friendList = getFriendList(fromUser);
        boolean isContainFriend = isContainFriend(friendList, toUser);
        if(isContainFriend) {
            httpResult.setStatus(Constant.FAIL);
            httpResult.setMessage("你们已经是好友");
            return httpResult;
        }
        List<User> list = new ArrayList<>();
        list.add(toUser);
        //添加
        Relationship relationship = new Relationship();
        relationship.setFromUser(fromUser);
        relationship.setToUser(toUser);
        relationRepository.save(relationship);
        httpResult.setStatus(Constant.SUCCESS);
        httpResult.setMessage("添加好友成功");
        httpResult.setData(list);
        return httpResult;
    }

    public HttpResult getFriends(FriendRequest request) {
        HttpResult<List<User>> httpResult = new HttpResult<>();
        User fromUser = userRepository.findByUserName(request.getFromUserName());
        httpResult.setStatus(Constant.SUCCESS);
        httpResult.setData(getFriendList(fromUser));
        httpResult.setMessage("好友列表加载完成");
        return httpResult;
    }

    private boolean isContainFriend(List<User> friendList, User toUser) {
        for(User user : friendList) {
            if(user.getUserId() == toUser.getUserId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 寻找该用户的好友列表
     * @param fromUser
     * @return
     */
    private List<User> getFriendList(User fromUser) {
        List<User> friendList = new ArrayList<>();
        List<Relationship> followers = fromUser.getFollowers(); //fromUser
        for(Relationship relationship : followers) {
            friendList.add(relationship.getFromUser());
        }
        List<Relationship> followUsers = fromUser.getFollowUsers(); //toUser
        for(Relationship relationship : followUsers) {
            friendList.add(relationship.getToUser());
        }
        return friendList;
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

    public User findUserById(Long id) {
        return userRepository.findByUserId(id);
    }

}
