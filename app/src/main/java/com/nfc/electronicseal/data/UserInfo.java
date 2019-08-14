package com.nfc.electronicseal.data;

import com.nfc.electronicseal.node.UserNode;

public class UserInfo {
    private static UserInfo userInfo;
    private UserNode userNode;
    private String token;

    public static UserInfo getInstance(){
        if(userInfo==null)
            userInfo = new UserInfo();
        return userInfo;
    }

    public UserNode getUserNode() {
        return userNode;
    }

    public void setUserNode(UserNode userNode) {
        this.userNode = userNode;
    }

    public String getToken() {
        return "Bearer " + token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
