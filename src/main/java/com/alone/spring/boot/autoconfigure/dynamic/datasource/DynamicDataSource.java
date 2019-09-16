package com.alone.spring.boot.autoconfigure.dynamic.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author zhouxianjun(Alone)
 * @ClassName:
 * @Description:
 * @date 2019-09-12 14:38
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceContextHolder.get();
	}
}
