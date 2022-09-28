package com.examination.serice;

import com.examination.bean.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {

    List<User> queryUserList();

    User queryUserByName(String username);

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(Integer id);
}
