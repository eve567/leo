package net.ufrog.leo.console.forms;

import java.io.Serializable;

/**
 * 备注表单
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-12-05
 * @since 0.1
 */
public class RemarkForm implements Serializable {

    private static final long serialVersionUID = 1977341294142485754L;

    /** 备注 */
    private String remark;

    /**
     * 读取备注
     *
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
