## Spring boot 动态JDBC数据源 starter

### 配置
#### 前缀
    dynamic.ds
#### 默认数据源
    dynamic.ds.default-data-source
#### 未找到指定数据源是否使用默认数据源
    dynamic.ds.not-found-use-default
#### 其他属性参照 `DataSourceProperties`

### 例
    dynamic.ds.datasource.other.url = jdbc:mysql://ip:port/database?characterEncoding=utf8&useSSL=false&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true
    dynamic.ds.datasource.other.username = root
    dynamic.ds.datasource.other.password = xxx
    dynamic.ds.datasource.other.driver-class-name = com.mysql.jdbc.Driver
    
    spring.datasource.url = jdbc:mysql://ip:port/database?characterEncoding=utf8&useSSL=false&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true 
    spring.datasource.username = root
    spring.datasource.password = xxx
    spring.datasource.driver-class-name = com.mysql.jdbc.Driver
    
    在上面例子中默认会初始化2个数据源，一个other一个default，当使用@TargetDataSource("other")注解的时候自动切换为other的数据源，
    在没有注解的函数时使用default数据源。
    
> 当然你也可以配置`default-data-source`来手动指定默认数据源，默认数据源为`spring.datasource`,
    如果`not-found-use-default`为`true`并`@TargetDataSource`注解了一个不存在的数据源则会使用默认数据源。
    上面例子中的`other`为其中一个数据源，但不限于只有`other`，任何一个符合命名规范的都可以作为动态数据源名称，
    并可以支持多个。
