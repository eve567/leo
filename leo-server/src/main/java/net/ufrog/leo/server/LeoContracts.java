package net.ufrog.leo.server;

import net.ufrog.leo.client.contracts.ResultCode;
import net.ufrog.leo.client.contracts.UserRequest;
import net.ufrog.leo.client.contracts.UserResponse;
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
     * @param userRequest 用户请求
     * @return 用户模型
     */
    public static User toUserModel(UserRequest userRequest) {
        User user = new User();

        user.setAccount(userRequest.getAccount());
        user.setCellphone(userRequest.getCellphone());
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword());
        user.setType(userRequest.getType());
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
    public static UserResponse toUserResp(User user) {
        UserResponse userResponse = new UserResponse();

        userResponse.setResultCode(ResultCode.SUCCESS);
        userResponse.setId(user.getId());
        userResponse.setAccount(user.getAccount());
        userResponse.setCellphone(user.getCellphone());
        userResponse.setEmail(user.getEmail());
        userResponse.setName(user.getName());
        userResponse.setStatus(user.getStatus());
        userResponse.setStatusName(user.getStatusName());
        userResponse.setType(user.getType());
        userResponse.setTypeName(user.getTypeName());
        return userResponse;
    }
}
