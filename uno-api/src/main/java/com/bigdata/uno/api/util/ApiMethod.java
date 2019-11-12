package com.bigdata.uno.api.util;

import com.bigdata.uno.common.model.Response;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@RequestMapping
public @interface ApiMethod {

    /**
     * Alias for {@link RequestMapping#name}.
     */
    @AliasFor(annotation = RequestMapping.class)
    String name() default "";

    /**
     * Alias for {@link RequestMapping#value}.
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] value() default {};

    /**
     * Alias for {@link RequestMapping#path}.
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] path() default {};

    /**
     * Alias for {@link RequestMapping#params}.
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] params() default {};

    /**
     * Alias for {@link RequestMapping#headers}.
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] headers() default {};

    /**
     * Alias for {@link RequestMapping#consumes}.
     *
     * @since 4.3.5
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] consumes() default {};

    /**
     * Alias for {@link RequestMapping#produces}.
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] produces() default {};

    /**
     * Alias for {@link RequestMapping#method}.
     */
    @AliasFor(annotation = RequestMapping.class)
    RequestMethod[] method() default RequestMethod.GET;

    /**
     * @return 超时毫秒（0表示不检查超时）
     */
    int timeout() default 0;

    /**
     * @return 是否需要登录
     */
    boolean requireLogin() default true;

    /**
     * @return 是否需要管理员权限
     */
    boolean requireAdmin() default false;

    /**
     * @return 是否需要接口权限检查
     */
    boolean requireAuth() default true;

    /**
     * @return 是否记录日志
     */
    boolean requireLog() default true;

    enum CacheType {
        /**
         * 不开启缓存
         */
        NO_CACHE,

        /**
         * Redis缓存
         */
        REDIS
    }

    /**
     * @return 数据缓存方式
     */
    CacheType cacheType() default CacheType.NO_CACHE;

    /**
     * @return 缓存超时(毫秒 ）
     */
    long cacheTimeout() default 10L * 60 * 1000;

    Class responseType() default Response.class;
}
