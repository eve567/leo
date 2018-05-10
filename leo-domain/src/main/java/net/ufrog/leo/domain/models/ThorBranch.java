package net.ufrog.leo.domain.models;

import net.ufrog.aries.common.jpa.Model;

/**
 * 客户关系门店模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2018-05-02
 * @since 0.1
 */
@java.lang.SuppressWarnings("unused")
@javax.persistence.Entity
@javax.persistence.Table(name = "leo_thor_branch")
public class ThorBranch extends Model {

    private static final long serialVersionUID = 3287243120150381942L;

    /** 名称 */
    @javax.persistence.Column(name = "vc_name")
    private java.lang.String name;

    /** 代码 */
    @javax.persistence.Column(name = "vc_code")
    private java.lang.String code;

    /** 类型 */
    @javax.persistence.Column(name = "dc_type")
    private java.lang.String type;

    /** 省份编号 */
    @javax.persistence.Column(name = "fk_province_id")
    private java.lang.String provinceId;

    /** 城市编号 */
    @javax.persistence.Column(name = "fk_city_id")
    private java.lang.String cityId;

    /** 区县编号 */
    @javax.persistence.Column(name = "fk_district_id")
    private java.lang.String districtId;

    /** 客户关系编号 */
    @javax.persistence.Column(name = "fk_thor_id")
    private java.lang.String thorId;

    /**
     * 读取名称
     *
     * @return 名称
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    /**
     * 读取代码
     *
     * @return 代码
     */
    public java.lang.String getCode() {
        return code;
    }

    /**
     * 设置代码
     *
     * @param code 代码
     */
    public void setCode(java.lang.String code) {
        this.code = code;
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
     * 读取省份编号
     *
     * @return 省份编号
     */
    public java.lang.String getProvinceId() {
        return provinceId;
    }

    /**
     * 设置省份编号
     *
     * @param provinceId 省份编号
     */
    public void setProvinceId(java.lang.String provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * 读取城市编号
     *
     * @return 城市编号
     */
    public java.lang.String getCityId() {
        return cityId;
    }

    /**
     * 设置城市编号
     *
     * @param cityId 城市编号
     */
    public void setCityId(java.lang.String cityId) {
        this.cityId = cityId;
    }

    /**
     * 读取区县编号
     *
     * @return 区县编号
     */
    public java.lang.String getDistrictId() {
        return districtId;
    }

    /**
     * 设置区县编号
     *
     * @param districtId 区县编号
     */
    public void setDistrictId(java.lang.String districtId) {
        this.districtId = districtId;
    }

    /**
     * 读取客户关系编号
     *
     * @return 客户关系编号
     */
    public java.lang.String getThorId() {
        return thorId;
    }

    /**
     * 设置客户关系编号
     *
     * @param thorId 客户关系编号
     */
    public void setThorId(java.lang.String thorId) {
        this.thorId = thorId;
    }
}