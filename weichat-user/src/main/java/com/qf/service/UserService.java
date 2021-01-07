package com.qf.service;

import com.qf.common.BaseResp;
import com.qf.pojo.User;
import com.qf.pojo.resp.ReqUser;

public interface UserService {
    BaseResp login(ReqUser user);

    BaseResp sendEmail(String email);

    BaseResp registey(User user);
}
