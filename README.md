<!-- TOC -->

- [AYANAMI](#ayanami)
    - [frontend-xlmall1.0](#frontend-xlmall10)
        - [admin-fe](#admin-fe)
        - [project_code](#project_code)
    - [productionEnvBuild](#productionenvbuild)
    - [xlmall-api1.0](#xlmall-api10)
    - [xlmall-api2.0](#xlmall-api20)

<!-- /TOC -->
# 1. AYANAMI
基于Java SpringBoot实现的前后端分离商城(PC前端，APP及H5移动端)

## 1.1. frontend-xlmall1.0

### 1.1.1. admin-fe
通过react实现的前端后台管理系统

### 1.1.2. project_code
通过js,webpack实现的前端前台商城，通过js封装请求工具，通过实现每个模块的service进行API调用


## 1.2. productionEnvBuild
在实际生产环境使用Docker进行项目部署的笔记

## 1.3. xlmall-api1.0
商城1.0的API接口，通过SpringBoot实现了最基本的功能

## 1.4. xlmall-api2.0
商城1.0的API接口，分布式接口,新增了如下功能：

1. Redis分布式锁定时关闭订单
2. 使用JWT+Shiro进行token无状态登录
3. 一些功能加入Redis缓存
4. Redis单点登录的笔记（使用JWT,暂不使用这个）