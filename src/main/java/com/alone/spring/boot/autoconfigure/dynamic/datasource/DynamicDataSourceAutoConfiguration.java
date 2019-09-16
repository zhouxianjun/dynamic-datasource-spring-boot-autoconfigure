package com.alone.spring.boot.autoconfigure.dynamic.datasource;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhouxianjun(Alone)
 * @ClassName:
 * @Description: 动态数据源自动配置类
 * @date 2019-09-12 14:26
 */
@Configuration
@ConditionalOnClass(DataSource.class)
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@Import(DynamicDataSourceAop.class)
public class DynamicDataSourceAutoConfiguration implements EnvironmentAware {
	private final DynamicDataSourceProperties properties;
	/**
	 * 其它数据源
	 */
	private Map<Object, Object> dataSources;
	/**
	 * 默认数据源
	 */
	private DataSource defaultDateSource;

	public DynamicDataSourceAutoConfiguration(DynamicDataSourceProperties properties) {
		this.properties = properties;
		this.dataSources = properties.getDatasource().entrySet().stream().collect(
				Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().initializeDataSourceBuilder().build())
		);
		Assert.notEmpty(properties.getDatasource(), "请配置动态数据源");
	}

	@Bean
	public DataSource dataSource() {
		DynamicDataSource dataSource = new DynamicDataSource();
		dataSource.setTargetDataSources(this.dataSources);
		dataSource.setDefaultTargetDataSource(defaultDateSource);
		return dataSource;
	}

	@Override
	public void setEnvironment(Environment environment) {
		if (!properties.haveDefault()) {
			if (hasPrefix(environment, "spring.datasource")) {
				Binder binder = Binder.get(environment);
				defaultDateSource = binder
						.bind("spring.datasource", DataSourceProperties.class)
						.get()
						.initializeDataSourceBuilder()
						.build();
			} else {
				int size = dataSources.size();
				Assert.isTrue(size <= 1, "当前数据源中有多个，但没有指定默认数据源.");
				defaultDateSource = (DataSource) dataSources.values().iterator().next();
			}
		} else {
			defaultDateSource = (DataSource) dataSources.get(properties.getDefaultDataSource());
		}
	}

	private boolean hasPrefix(Environment environment, String prefix) {
		AbstractEnvironment env = (AbstractEnvironment) environment;
		return env.getPropertySources().stream().filter(p -> p instanceof MapPropertySource)
				.anyMatch((p) -> ((MapPropertySource) p).getSource().keySet().stream()
						.anyMatch(k -> k.startsWith(prefix)));
	}
}
