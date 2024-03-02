/******************************************/
/*   创建 nacos 数据库   */
/******************************************/
CREATE DATABASE IF NOT EXISTS gitea default charset = utf8mb4;

/******************************************/
/*   创建 MySQL 用户，Nacos Server 连接用   */
/******************************************/
CREATE USER 'taowu-gitea'@'%' IDENTIFIED BY 'taowu-gitea';
GRANT ALL PRIVILEGES ON `gitea`.* TO 'taowu-gitea'@'%' IDENTIFIED BY 'taowu-gitea';
FLUSH PRIVILEGES;