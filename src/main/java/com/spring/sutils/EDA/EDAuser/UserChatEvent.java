package com.spring.sutils.EDA.EDAuser;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 15:51 2018/9/29 2018
 * @Modify:
 */
public class UserChatEvent  extends  UserOnLineEvent {

    private final String message;
    public UserChatEvent(User user ,String msg) {
        super(user);
        this.message=msg;
    }

    public String getMessage() {
        return message;
    }
}
