# staff-management
Java Swing + mysql 员工管理系统，只简单实现了对员工表的增删改查。

### 克隆本项目

```git
git clone git@github.com:wang-gs5/staff-management.git
```

### 开发环境

项目为Maven项目，请确保系统安装了Maven和Mysql。

Java 17

Mysql 8.30

### 数据库导入

1. 在Navicat或者mysql控制台(或者其他软件)创建`staff_management`数据库(或者其他名称)
2. 在`staff_managemt`数据库执行项目下的`staff_management.sql`文件，导入数据

### 项目启动

1. 使用IDEA打开项目
2. 下载Maven依赖
3. 修改`resources`目录下的`mysql.properties`配置文件
    1. 修改`jdbc.url`中的数据库为你自己创建的数据库(若数据库导入部分没有修改为其他名称，则不需要更改)
    2. 修改用户名和密码
4. 执行`top.wang3.staffmamagement`包下的Main类中的main方法，管理员账号为为`admin`，密码为`123456`(可自行在数据库中更改)

代码中含有注释，可结合注释二次开发。