package com.alone.spring.boot.autoconfigure.dynamic.datasource;

import java.lang.annotation.*;

/**
 * @author zhouxianjun(Alone)
 * @ClassName:
 * @Description: 数据源注解
 * @date 2019-09-12 15:21
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
	String value();
}
