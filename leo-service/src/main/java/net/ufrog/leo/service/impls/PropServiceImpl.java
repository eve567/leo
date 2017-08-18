package net.ufrog.leo.service.impls;

import net.ufrog.common.utils.Strings;
import net.ufrog.leo.domain.models.Prop;
import net.ufrog.leo.domain.repositories.PropRepository;
import net.ufrog.leo.service.PropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统属性业务实现
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-23
 * @since 0.1
 */
@Service
@Transactional(readOnly = true)
public class PropServiceImpl implements PropService {

    /** 系统属性仓库 */
    private PropRepository propRepository;

    /**
     * 构造函数
     *
     * @param propRepository 系统属性仓库
     */
    @Autowired
    public PropServiceImpl(PropRepository propRepository) {
        this.propRepository = propRepository;
    }

    @Override
    public List<Prop> findAll() {
        return propRepository.findAll();
    }

    @Override
    @Transactional
    public Prop save(String code, String value) {
        Prop prop = propRepository.findByCode(code);
        if (prop == null) {
            prop = new Prop();
            prop.setCode(code);
        } if (!Strings.equals(prop.getValue(), value)) {
            prop.setValue(value);
            propRepository.save(prop);
        }
        return prop;
    }
}
