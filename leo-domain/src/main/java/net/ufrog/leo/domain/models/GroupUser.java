package net.ufrog.leo.domain.models;

/**
 * 组织用户模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-12-01
 * @since 0.1
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "leo_group_user")
public class GroupUser extends Model {

    private static final long serialVersionUID = -5083741352260270052L;

    /** 备注 */
    @javax.persistence.Column(name = "vc_remark")
    private java.lang.String remark;

    /** 用户编号 */
    @javax.persistence.Column(name = "fk_user_id")
    private java.lang.String userId;

    /** 组织编号 */
    @javax.persistence.Column(name = "fk_group_id")
    private java.lang.String groupId;

    /**
     * 读取备注
     *
     * @return 备注
     */
    public java.lang.String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(java.lang.String remark) {
        this.remark = remark;
    }

    /**
     * 读取用户编号
     *
     * @return 用户编号
     */
    public java.lang.String getUserId() {
        return userId;
    }

    /**
     * 设置用户编号
     *
     * @param userId 用户编号
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    /**
     * 读取组织编号
     *
     * @return 组织编号
     */
    public java.lang.String getGroupId() {
        return groupId;
    }

    /**
     * 设置组织编号
     *
     * @param groupId 组织编号
     */
    public void setGroupId(java.lang.String groupId) {
        this.groupId = groupId;
    }
}