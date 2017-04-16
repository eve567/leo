package net.ufrog.leo.domain.models;

import net.ufrog.common.dict.Element;

import java.io.Serializable;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-04-04
 * @since 0.1
 */
public class RoleResource implements Serializable {

    private static final long serialVersionUID = -6454843644437079602L;

    public String getResourceId() {
        return null;
    }

    public String getStatus() {
        return null;
    }

    public static class Status {

        @Element("禁止")
        public static final String BAN = "00";

        @Element("允许")
        public static final String ALLOW = "01";
    }
}
