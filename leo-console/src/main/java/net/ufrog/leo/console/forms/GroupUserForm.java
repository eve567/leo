package net.ufrog.leo.console.forms;

import java.io.Serializable;

/**
 * 组织用户表单
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-12-04
 * @since 0.1
 */
public class GroupUserForm implements Serializable {

    private static final long serialVersionUID = -4263500434165291501L;

    /** 编号数组 */
    private String[] ids;

    /**
     * 读取编号数组
     *
     * @return 编号数组
     */
    public String[] getIds() {
        return ids;
    }

    /**
     * 设置编号数组
     *
     * @param ids 编号数组
     */
    public void setIds(String[] ids) {
        this.ids = ids;
    }
}
