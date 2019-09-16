package com.alone.spring.boot.autoconfigure.dynamic.datasource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhouxianjun(Alone)
 * @ClassName:
 * @Description: 动态数据源上下文
 * @date 2019-09-12 14:40
 */
@Slf4j
public class DynamicDataSourceContextHolder {
	private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

	public static void set(String type) {
		HOLDER.set(type);
	}


	public static String get() {
		return HOLDER.get();
	}


	public static void clear() {
		HOLDER.remove();
	}
}
