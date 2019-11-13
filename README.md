# custmizemybatisgenerator

#### 介绍
定制版 mybatis generator
增加service DTO query 


#### 软件架构
软件架构说明


#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 使用说明

1.  在mybatis-generator-maven-plugin中依赖本项目 jar
2.  generatorConfig.xml中context节点targetRuntime修改为ocm.zhangfeng.mybatis.CustomizeIntrospectedTable，
context节点下增加plugin 节点 ocm.zhangfeng.mybatis.CustomizePlugin
```
<context id="my" targetRuntime="ocm.zhangfeng.mybatis.CustomizeIntrospectedTable">
     <property name="javaFileEncoding" value="utf-8"/>
    <property name="beginningDelimiter" value=""/>
    <property name="endingDelimiter" value=""/>
    <plugin type="ocm.zhangfeng.mybatis.CustomizePlugin"></plugin>
    <plugin type="org.mybatis.generator.plugins.SerializablePlugin"></plugin>
    ```
3.  项目springboot依赖
```
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>2.1.9.RELEASE</version>
  <relativePath/> <!-- lookup parent from repository -->
</parent>
<dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>fastjson</artifactId>
      <version>1.2.62</version>
    </dependency>

    <dependency>
      <groupId>com.github.pagehelper</groupId>
      <artifactId>pagehelper-spring-boot-starter</artifactId>
      <version>1.2.5</version>
    </dependency>

    <dependency>
      <groupId>org.mybatis.spring.boot</groupId>
      <artifactId>mybatis-spring-boot-starter</artifactId>
      <version>2.1.0</version>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

  </dependencies>
```
#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 码云特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5.  码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
