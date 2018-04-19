package net.ufrog.leo.server;

import net.ufrog.leo.client.contract.ResultCode;
import net.ufrog.leo.client.contract.UserReq;
import net.ufrog.leo.client.contract.UserResp;
import net.ufrog.leo.domain.models.User;

/**
 * 约束工具
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-19
 * @since 3.0.0
 */
public class LeoContracts {

    /** 构造函数 */
    private LeoContracts() {}

    /**
     * 转换用户模型
     *
     * @param userReq 用户请求
     * @return 用户模型
     */
    public static User toUserModel(UserReq userReq) {
        User user = new User();

        user.setAccount(userReq.getAccount());
        user.setCellphone(userReq.getCellphone());
        user.setEmail(userReq.getEmail());
        user.setName(userReq.getName());
        user.setPassword(userReq.getPassword());
        user.setType(userReq.getType());
        user.setStatus(User.Status.PENDING);
        user.setForced(User.Forced.FALSE);
        return user;
    }

    /**
     * 转换用户响应
     *
     * @param user 用户模型
     * @return 用户响应
     */
    public static UserResp toUserResp(User user) {
        UserResp userResp = new UserResp();

        userResp.setResultCode(ResultCode.SUCCESS);
        userResp.setId(user.getId());
        userResp.setAccount(user.getAccount());
        userResp.setCellphone(user.getCellphone());
        userResp.setEmail(user.getEmail());
        userResp.setName(user.getName());
        userResp.setStatus(user.getStatus());
        userResp.setStatusName(user.getStatusName());
        userResp.setType(user.getType());
        userResp.setTypeName(user.getTypeName());
        return userResp;
    }
}
