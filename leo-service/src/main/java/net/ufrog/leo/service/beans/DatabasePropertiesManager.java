package net.ufrog.leo.service.beans;

import net.ufrog.common.spring.interceptor.PropertiesManager;
import net.ufrog.leo.domain.models.Prop;
import net.ufrog.leo.service.PropService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据库属性管理器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-23
 * @since 0.1
 */
public class DatabasePropertiesManager implements PropertiesManager {

    /** 系统属性业务接口 */
    private PropService propService;

    /**
     * 构造函数
     *
     * @param propService 系统属性业务接口
     */
    @Autowired
    public DatabasePropertiesManager(PropService propService) {
        this.propService = propService;
    }

    @Override
    public Map<String, String> load() {
        return propService.findAll().stream().collect(Collectors.toMap(Prop::getCode, Prop::getValue));
    }
}
