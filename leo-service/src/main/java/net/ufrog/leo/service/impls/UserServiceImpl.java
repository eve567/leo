package net.ufrog.leo.service.impls;

import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Objects;
import net.ufrog.common.utils.Passwords;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.domain.models.User;
import net.ufrog.leo.domain.repositories.UserRepository;
import net.ufrog.leo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户业务实现
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-02-26
 * @since 0.1
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    /** 用户仓库 */
    private UserRepository userRepository;

    /**
     * 构造函数
     *
     * @param userRepository 用户仓库
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(String id) {
        return userRepository.findOne(id);
    }

    @Override
    @Transactional
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        User oUser = userRepository.findOne(user.getId());
        Objects.copyProperties(oUser, user, "creator", "createTime", "updater", "updateTime");
        return userRepository.save(oUser);
    }

    @Override
    @Transactional
    public User reset(String id, String prev, String next) {
        User user = userRepository.findOne(id);
        if (user != null && Passwords.match(prev, user.getPassword())) {
            //TODO 设置强制修改密码为否
            user.setPassword(Passwords.encode(next));
            return userRepository.save(user);
        }
        throw new ServiceException("cannot find user or password is not match.", "service.user.reset.failure.match");
    }

    @Override
    @Transactional
    public User reset(String id, String next) {
        User user = userRepository.findOne(id);
        if (user != null) {
            //TODO 设置强制修改密码为是
            user.setPassword(Passwords.encode(next));
            return userRepository.save(user);
        }
        throw new ServiceException("cannot find user or password is not match.", "service.user.reset.failure.match");
    }

    @Override
    @Transactional
    public User freezeOrUnfreeze(String id) {
        User user = userRepository.findOne(id);
        if (user != null && Strings.in(user.getStatus(), User.Status.FROZEN, User.Status.ENABLED)) {
            user.setStatus(Strings.equals(User.Status.FROZEN, user.getStatus()) ? User.Status.ENABLED : User.Status.FROZEN);
            return userRepository.save(user);
        }
        throw new ServiceException("cannot find user or user status invalid.", "service.user.toggle.failure.status");
    }
}
