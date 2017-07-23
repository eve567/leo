package net.ufrog.leo.client.api.beans;

import java.util.*;

/**
 * 列表响应
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-27
 * @since 0.1
 */
public class ListResp<T extends Resp> extends Resp {

    private static final long serialVersionUID = 2078012406999625657L;

    /** 内容 */
    private List<T> content;

    /** 构造函数 */
    public ListResp() {
        content = new ArrayList<>();
    }

    /**
     * 构造函数
     *
     * @param content 内容
     */
    public ListResp(List<T> content) {
        this();
        this.content.addAll(content);
    }

    /**
     * 读取内容
     *
     * @return 内容
     */
    public List<T> getContent() {
        return content;
    }

    /**
     * 添加元素
     *
     * @param element 元素
     */
    public void add(T element) {
        content.add(element);
    }
}
