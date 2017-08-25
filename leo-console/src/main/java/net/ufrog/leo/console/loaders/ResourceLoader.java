package net.ufrog.leo.console.loaders;

import java.util.List;

/**
 * 资源加载接口
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-22
 * @since 0.1
 */
public interface ResourceLoader {

    /**
     * 加载
     *
     * @return 资源对象列表
     */
    List<ResourceWrapper> load();

    /**
     * 资源封装
     *
     * @author ultrafrog, ufrog.net@gmail.com
     * @version 0.1, 2017-08-22
     * @since 0.1
     */
    final class ResourceWrapper {

        /** 编号 */
        private String id;

        /** 名称 */
        private String name;

        /** 资源编号 */
        private String resourceId;

        /** 构造函数 */
        public ResourceWrapper() {}

        /**
         * 构造函数
         *
         * @param id 编号
         * @param name 名称
         * @param resourceId 资源编号
         */
        public ResourceWrapper(String id, String name, String resourceId) {
            this();
            this.id = id;
            this.name = name;
            this.resourceId = resourceId;
        }

        /**
         * 读取编号
         *
         * @return 编号
         */
        public String getId() {
            return id;
        }

        /**
         * 设置编号
         *
         * @param id 编号
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * 读取名称
         *
         * @return 名称
         */
        public String getName() {
            return name;
        }

        /**
         * 设置名称
         *
         * @param name 名称
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * 读取资源编号
         *
         * @return 资源编号
         */
        public String getResourceId() {
            return resourceId;
        }

        /**
         * 设置资源编号
         *
         * @param resourceId 资源编号
         */
        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }
    }
}
