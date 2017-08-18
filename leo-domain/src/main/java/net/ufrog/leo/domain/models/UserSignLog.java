package net.ufrog.leo.domain.models;

/**
 * 用户登录日志模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-16
 * @since 0.1
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "leo_user_sign_log")
public class UserSignLog extends Model {

    private static final long serialVersionUID = -4594431958486559314L;

    /** 时间 */
    @javax.persistence.Column(name = "dt_datetime")
    private java.util.Date datetime;

    /** 备注 */
    @javax.persistence.Column(name = "vc_remark")
    private java.lang.String remark;

    /** 平台代码 */
    @javax.persistence.Column(name = "vc_platform_code")
    private java.lang.String platformCode;

    /** 类型 */
    @javax.persistence.Column(name = "dc_type")
    private java.lang.String type;

    /** 模式 */
    @javax.persistence.Column(name = "dc_mode")
    private java.lang.String mode;

    /** 应用编号 */
    @javax.persistence.Column(name = "fk_app_id")
    private java.lang.String appId;

    /** 用户编号 */
    @javax.persistence.Column(name = "fk_user_id")
    private java.lang.String userId;

    /**
     * 读取时间
     *
     * @return 时间
     */
    public java.util.Date getDatetime() {
        return datetime;
    }

    /**
     * 设置时间
     *
     * @param datetime 时间
     */
    public void setDatetime(java.util.Date datetime) {
        this.datetime = datetime;
    }

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
     * 读取平台代码
     *
     * @return 平台代码
     */
    public java.lang.String getPlatformCode() {
        return platformCode;
    }

    /**
     * 设置平台代码
     *
     * @param platformCode 平台代码
     */
    public void setPlatformCode(java.lang.String platformCode) {
        this.platformCode = platformCode;
    }

    /**
     * 读取类型
     *
     * @return 类型
     */
    public java.lang.String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }

    /**
     * 读取模式
     *
     * @return 模式
     */
    public java.lang.String getMode() {
        return mode;
    }

    /**
     * 设置模式
     *
     * @param mode 模式
     */
    public void setMode(java.lang.String mode) {
        this.mode = mode;
    }

    /**
     * 读取应用编号
     *
     * @return 应用编号
     */
    public java.lang.String getAppId() {
        return appId;
    }

    /**
     * 设置应用编号
     *
     * @param appId 应用编号
     */
    public void setAppId(java.lang.String appId) {
        this.appId = appId;
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
     * 类型
     *
     * @author ultrafrog
     * @version 0.1, 2017-08-16
     * @since 0.1
     */
    public static final class Type {

        @net.ufrog.common.dict.Element("登出")
        public static final String SIGN_OUT = "00";

        @net.ufrog.common.dict.Element("登录")
        public static final String SIGN_IN = "01";
    }

    /**
     * 模式
     *
     * @author ultrafrog
     * @version 0.1, 2017-08-16
     * @since 0.1
     */
    public static final class Mode {

        @net.ufrog.common.dict.Element("统一入口")
        public static final String GATEWAY = "00";

        @net.ufrog.common.dict.Element("应用")
        public static final String APP = "01";

        @net.ufrog.common.dict.Element("开放平台")
        public static final String PLATFORM = "02";
    }
}