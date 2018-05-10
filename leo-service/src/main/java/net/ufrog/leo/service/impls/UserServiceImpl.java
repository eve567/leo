package net.ufrog.leo.service.impls;

import net.ufrog.common.Logger;
import net.ufrog.common.data.spring.Domains;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Objects;
import net.ufrog.common.utils.Passwords;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.domain.Models;
import net.ufrog.leo.domain.models.Role;
import net.ufrog.leo.domain.models.User;
import net.ufrog.leo.domain.models.UserRole;
import net.ufrog.leo.domain.models.UserSignLog;
import net.ufrog.leo.domain.repositories.UserRepository;
import net.ufrog.leo.domain.repositories.UserRoleRepository;
import net.ufrog.leo.domain.repositories.UserSignLogRepository;
import net.ufrog.leo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

    /** 用户角色仓库 */
    private final UserRoleRepository userRoleRepository;

    /** 用户登录日志仓库 */
    private final UserSignLogRepository userSignLogRepository;

    /**
     * 构造函数
     *
     * @param userRepository 用户仓库
     * @param userRoleRepository 用户角色仓库
     * @param userSignLogRepository 用户登录日志仓库
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, UserSignLogRepository userSignLogRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userSignLogRepository = userSignLogRepository;
    }

    @Override
    public List<User> findAll(String... types) {
        return userRepository.findByTypeIn(Arrays.asList(types), Domains.sort(Sort.Direction.ASC, "name"));
    }

    @Override
    public Page<User> findAll(Integer page, Integer size, String... types) {
        Pageable pageable = Domains.pageable(page, size, Sort.Direction.ASC, "email", "cellphone");
        return userRepository.findByTypeIn(Arrays.asList(types), pageable);
    }

    @Override
    public List<UserRole> findUserRoles(String appId, String userId) {
        return userRoleRepository.findByUserIdAndAppIdAndType(userId, appId, Role.Type.PUBLIC);
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
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
        return userRepository.findById(user.getId()).map(oUser -> {
            Objects.copyProperties(oUser, user, "creator", "createTime", "updater", "updateTime");
            return userRepository.save(oUser);
        }).orElseThrow(() -> new ServiceException("cannot find user by id: " + user.getId()));
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

    @Override
    @Transactional
    public List<UserRole> bindRoles(String userId, String appId, String[] roleIds) {
        List<UserRole> lUserRole = userRoleRepository.findByUserIdAndAppIdAndType(userId, appId, Role.Type.PUBLIC);
        userRoleRepository.deleteAll(lUserRole);
        Logger.info("delete %s original user role reference(s).", lUserRole.size());

        List<UserRole> lnUserRole = new ArrayList<>(roleIds.length);
        Stream.of(roleIds).forEach(roleId -> lnUserRole.add(Models.newUserRole(userId, roleId)));
        userRoleRepository.saveAll(lnUserRole);
        return lnUserRole;
    }
}
