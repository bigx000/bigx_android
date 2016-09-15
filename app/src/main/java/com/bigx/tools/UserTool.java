package com.bigx.tools;

import com.bigx.beans.UserBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhaoshuai on 16/9/15.
 */
public class UserTool {
    
    public static final String DEFAULT_PORTRAIT_URI = "http://img.hb.aicdn.com/65f6b7d99b547f094caf3ad5ce1f3dd25f1068122f8e3-CD0JS9_fw658";
    
    private static final Map<String, UserBean> USER_MAP = new HashMap<>();
    
    static {
        initUsers();
    }
    
    private static void initUsers() {
        UserBean userBean = new UserBean();
        userBean.userId = 1;
        userBean.account = "zhaoshuai";
        userBean.name = "赵帅";
        userBean.password = "123456";
        userBean.portraitUri = DEFAULT_PORTRAIT_URI;
        userBean.token = "opG4CTet50zveRbKPcshkCv8NjHf7SxgxocJfkOj7pNpS4/A5km7F1TEb30etFgXJHHtweE3M1w=";
        USER_MAP.put(userBean.account, userBean);

        userBean = new UserBean();
        userBean.userId = 2;
        userBean.account = "huangbo";
        userBean.name = "黄博";
        userBean.password = "123456";
        userBean.portraitUri = DEFAULT_PORTRAIT_URI;
        userBean.token = "ZGBFQMTmQP8BOc8BG8loBCV5npCppRYRIIXtXeQMr0V3Fh+z+bDU2CXnYWT87De/izK1NL78ISNf+rM/dKNEHA==";
        USER_MAP.put(userBean.account, userBean);
    }
    
    public static UserBean getUser(String account, String password) {
        UserBean userBean = USER_MAP.get(account);
        if (userBean == null) {
            return null;
        }
        if (!userBean.password.equals(password)) {
            return null;
        }
        return userBean;
    }
    
    public static List<UserBean> getUsers(String account) {
        List<UserBean> users = new ArrayList<>();
        for (Entry<String, UserBean> entry : USER_MAP.entrySet()) {
            if (!entry.getKey().equals(account)) {
                users.add(entry.getValue());
            }
        }
        return users;
    }
    
}
