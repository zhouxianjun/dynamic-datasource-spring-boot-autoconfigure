package com.alone.spring.boot.autoconfigure.dynamic.datasource;

import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author zhouxianjun(Alone)
 * @ClassName:
 * @Description: 动态数据源配置属性字段类
 * @date 2019-09-12 14:17
 */
@ConfigurationProperties(prefix = "dynamic.ds")
@Data
public class DynamicDataSourceProperties {
	/**
	 * 数据源map
	 */
	private Map<String, DataSourceProperties> datasource;
	/**
	 * 默认数据源
	 */
	private String defaultDataSource;
	/**
	 * 未找到指定数据源是否使用默认数据源
	 */
	private Boolean notFoundUseDefault = false;

	public boolean haveDefault() {
		return defaultDataSource != null && datasource.containsKey(defaultDataSource);
	}

	public boolean has(String name) {
		return datasource.containsKey(name);
	}
}
