package net.ufrog.leo.service;

import net.ufrog.leo.domain.models.Prop;

import java.util.List;

/**
 * 系统属性业务接口
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-23
 * @since 0.1
 */
public interface PropService {

    /**
     * 查询所有属性
     *
     * @return 属性列表
     */
    List<Prop> findAll();
}
