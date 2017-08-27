package net.ufrog.leo.service.impls;

import net.ufrog.common.data.spring.Domains;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Objects;
import net.ufrog.common.utils.Passwords;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.domain.models.User;
import net.ufrog.leo.domain.models.UserSignLog;
import net.ufrog.leo.domain.repositories.UserRepository;
import net.ufrog.leo.domain.repositories.UserSignLogRepository;
import net.ufrog.leo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Stream;

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
    private final UserRepository userRepository;

    /** 用户登录日志仓库 */
    private final UserSignLogRepository userSignLogRepository;

    /**
     * 构造函数
     *
     * @param userRepository 用户仓库
     * @param userSignLogRepository 用户登录日志仓库
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserSignLogRepository userSignLogRepository) {
        this.userRepository = userRepository;
        this.userSignLogRepository = userSignLogRepository;
    }

    @Override
    public Page<User> findAll(Integer page, Integer size, String... types) {
        Pageable pageable = Domains.pageable(page, size, Sort.Direction.ASC, "email", "cellphone");
        return userRepository.findByTypeIn(Arrays.asList(types), pageable);
    }

    @Override
    public User findById(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public User findByAccount(String account) {
        User user = null;
        if (Strings.cellphone(account)) {
            user = userRepository.findByCellphone(account);
        } if (user == null && Strings.email(account)) {
            user = userRepository.findByEmail(account);
        } if (user == null) {
            user = userRepository.findByAccount(account);
        }
        return user;
    }

    @Override
    public User authenticate(String account, String password, UserType... userTypes) throws ServiceException {
        User user = findByAccount(account);
        if (user == null || !Passwords.match(password, user.getPassword())) {
            throw new ServiceException("cannot authenticate user.", "service.authenticate.failure.account");
        } else if (Strings.in(user.getStatus(), User.Status.PENDING, User.Status.FROZEN, User.Status.DISABLED)) {
            throw new ServiceException("cannot authenticate user.", "service.authenticate.failure.status");
        } if (Stream.of(userTypes).filter(userType -> Strings.in(user.getType(), userType.getTypes())).count() <= 0) {
            throw new ServiceException("cannot authenticate user.", "service.authenticate.failure.type");
        }
        return user;
    }

    @Override
    @Transactional
    public User create(User user) {
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
            user.setPassword(Passwords.encode(next));
            user.setForced(User.Forced.FALSE);
            return userRepository.save(user);
        }
        throw new ServiceException("cannot find user or password is not match.", "service.user.reset.failure.match");
    }

    @Override
    @Transactional
    public User reset(String id, String next) {
        User user = userRepository.findOne(id);
        if (user != null) {
            user.setPassword(Passwords.encode(next));
            user.setForced(User.Forced.TRUE);
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

    @Override
    @Transactional
    public UserSignLog createSignLog(String type, String mode, String appId, String userId, String platformCode, String remark) {
        UserSignLog userSignLog = new UserSignLog();

        userSignLog.setDatetime(new Date());
        userSignLog.setType(type);
        userSignLog.setMode(mode);
        userSignLog.setAppId(appId);
        userSignLog.setUserId(userId);
        userSignLog.setPlatformCode(platformCode);
        userSignLog.setRemark(remark);
        return userSignLogRepository.save(userSignLog);
    }
}
