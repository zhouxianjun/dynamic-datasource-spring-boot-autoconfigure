package com.alone.spring.boot.autoconfigure.dynamic.datasource;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author zhouxianjun(Alone)
 * @ClassName:
 * @Description: 动态数据源切换切面
 * @date 2019-09-12 15:21
 */
@Aspect
@Order(-1)
@Slf4j
public class DynamicDataSourceAop {
	@Resource
	private DynamicDataSourceProperties properties;

	@Around("@annotation(source)")
	@SneakyThrows
	public Object method(ProceedingJoinPoint joinPoint, TargetDataSource source){
		return around(joinPoint, source);
	}

	@Around("@within(source)")
	@SneakyThrows
	public Object targetClass(ProceedingJoinPoint joinPoint, TargetDataSource source){
		return around(joinPoint, source);
	}

	private Object around(ProceedingJoinPoint joinPoint, TargetDataSource source) throws Throwable {
		String name = source.value();
		if (!properties.has(name)) {
			Assert.isTrue(properties.getNotFoundUseDefault(), "指定的数据源: " + name + " 未配置，并 notFoundUseDefault is false.");
			log.warn("指定的数据源: {} 未配置，使用默认数据源.", name);
			return joinPoint.proceed();
		}
		DynamicDataSourceContextHolder.set(name);
		try {
			return joinPoint.proceed();
		} finally {
			DynamicDataSourceContextHolder.clear();
		}
	}

	@PostConstruct
	public void init() {
		log.info("dynamic datasource aop init ok.");
	}
}
