--liquibase formatted sql

--changeset sunhan:001
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(100) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `email` varchar(200) DEFAULT NULL COMMENT '邮件',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) DEFAULT NULL COMMENT '手机',
  `enabled` char(1) DEFAULT '1' COMMENT '是否可用\n1：可用\n0：停用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记\n1：删除\n0：未删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name_UNIQUE` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统用户';

CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL COMMENT '父ID',
  `parent_ids` varchar(2000) NOT NULL COMMENT '树ID',
  `name` varchar(100) NOT NULL COMMENT '菜单名称',
  `sort` int(11) NOT NULL COMMENT '排序',
  `href` varchar(2000) DEFAULT NULL COMMENT '链接',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `show` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '1' COMMENT '是否显示\n1：显示\n0：隐藏',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记\n1：删除\n0：未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='系统菜单';
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `enabled` char(1) DEFAULT '1' COMMENT '是否可用\n1：可用\n0：停用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记\n1：删除\n0：未删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统角色';

CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COMMENT='角色菜单';

CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色';

CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` int(11) NOT NULL COMMENT '数据值',
  `label` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '标签名',
  `type` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '类型',
  `description` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '描述',
  `sort` decimal(10,0) NOT NULL COMMENT '排序（升序）',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
PRIMARY KEY (`id`),
KEY `sys_dict_value` (`value`),
KEY `sys_dict_label` (`label`),
KEY `sys_dict_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='字典表';


CREATE TABLE `sys_error_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `class_name` varchar(128) DEFAULT NULL COMMENT '异常Class',
  `method_name` varchar(128) DEFAULT NULL COMMENT '异常Method',
  `line_number` int(11) DEFAULT NULL COMMENT '行号',
  `message` varchar(255) DEFAULT NULL COMMENT '异常消息',
  `status` int(11) NOT NULL COMMENT '处理状态',
  `stack_trace` text COMMENT '异常堆栈消息',
PRIMARY KEY (`id`),
UNIQUE KEY `unique` (`class_name`,`method_name`,`line_number`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 COMMENT='系统错误信息';

CREATE TABLE `gen_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `prefix` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '表名前缀',
  `output_dir` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '输出目录',
  `author` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '作者',
  `parent_module_name` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '父包名',
  `module_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '模块名称',
  `file_override` varchar(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否覆盖',
  `menu_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单名字',
  `menu_icon` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单图标',
  PRIMARY KEY (`id`),
  UNIQUE KEY `gen_table_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='业务表';

CREATE TABLE `gen_table_field` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属表编号',
  `name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '字段名称',
  `label` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '字段label',
  `nullable` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否可为空',
  `savable` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否编辑字段',
  `listable` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否列表字段',
  `queryable` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否查询字段',
  `query_type` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE）',
  `show_type` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）',
  `dict_type` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '字典类型',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='业务表字段';


--changeset sunhan:002
INSERT INTO `sys_user` (`id`, `login_name`, `password`, `name`, `email`, `phone`, `mobile`, `enabled`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('1', 'admin', '4297f44b13955235245b2497399d7a93', '系统管理员', 'sunhan521@qq.com', '15862614903', '15862614903', '1', '系统管理员', '2017-03-10 15:59:25', '2017-03-10 15:59:28', '0');

INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `icon`, `show`, `permission`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('1', '0', ',0,', '首页', '10', 'admin/common/home.html', 'icon-home', '1', '', '', now(), now(), '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `icon`, `show`, `permission`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('2', '9', ',0,9,', '菜单管理', '20', 'admin/sys/menu/list.html', 'icon-list', '1', 'sys:menu', '', now(), now(), '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `icon`, `show`, `permission`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('3', '2', ',0,9,2,', '查看', '10', NULL, 'icon-pencil', '0', 'view', '', now(), now(), '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `icon`, `show`, `permission`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('4', '2', ',0,9,2,', '编辑', '20', NULL, 'icon-pencil', '0', 'edit', '', now(), now(), '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `icon`, `show`, `permission`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('5', '9', ',0,9,', '用户管理', '10', 'admin/sys/user/list.html', 'icon-user', '1', 'sys:user', '', now(), now(), '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `icon`, `show`, `permission`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('6', '9', ',0,9,', '角色管理', '20', 'admin/sys/role/list.html', 'icon-users', '1', 'sys:role', '', now(), now(), '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `icon`, `show`, `permission`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('7', '5', ',0,9,5,', '查看', '10', '', 'icon-magnifier', '0', 'view', '', now(), now(), '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `icon`, `show`, `permission`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('8', '5', ',0,9,5,', '编辑', '10', '', 'icon-pencil', '0', 'edit', '', now(), now(), '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `icon`, `show`, `permission`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('9', '0', ',0,', '系统管理', '10', '', 'icon-settings', '1', 'sys:setting', '', now(), now(), '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `icon`, `show`, `permission`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('10', '0', ',0,', '代码生成', '10', 'admin/gen/list.html', 'icon-magnifier', '1', 'sys:gen', '', now(), now(), '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `icon`, `show`, `permission`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('11', '6', ',0,9,6,', '查看', '10', '', 'icon-magnifier', '0', 'view', '', now(), now(), '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `icon`, `show`, `permission`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('12', '6', ',0,9,6,', '编辑', '20', '', 'icon-pencil', '0', 'edit', '', now(), now(), '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `icon`, `show`, `permission`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('13', '9', ',0,9,', '字典表管理', '50', 'admin/sys/dict/list.html', 'icon-key', '1', 'sys:dict', '', now(), now(), '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `icon`, `show`, `permission`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('14', '13', ',0,9,34,', '查看', '10', '', 'icon-magnifier', '0', 'view', '', now(), now(), '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `icon`, `show`, `permission`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('15', '13', ',0,9,13,', '编辑', '20', '', 'icon-pencil', '0', 'edit', '', now(), now(), '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `icon`, `show`, `permission`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('16', '9', ',0,9,', '异常消息', '100', 'admin/sys/error/list.html', 'icon-ghost', '1', 'sys:error', '', now(), now(), '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `icon`, `show`, `permission`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('17', '16', ',0,9,16,', '查看', '10', '', 'icon-magnifier', '0', 'view', '', now(), now(), '0');
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `icon`, `show`, `permission`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('18', '16', ',0,9,16,', '编辑', '20', '', 'icon-pencil', '0', 'view', '', now(), now(), '0');

INSERT INTO `sys_role` (`id`, `name`, `enabled`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('1', 'ROLE_ADMIN', '1', '管理员', now(), now(), '0');
INSERT INTO `sys_role` (`id`, `name`, `enabled`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('2', 'ROLE_USER', '1', '普通用户', now(), now(), '0');
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES ('1', '1');


INSERT INTO `sys_role_menu` ( `role_id`, `menu_id`) VALUES ( '1', '1');
INSERT INTO `sys_role_menu` ( `role_id`, `menu_id`) VALUES ( '1', '2');
INSERT INTO `sys_role_menu` ( `role_id`, `menu_id`) VALUES ( '1', '3');
INSERT INTO `sys_role_menu` ( `role_id`, `menu_id`) VALUES ( '1', '4');
INSERT INTO `sys_role_menu` ( `role_id`, `menu_id`) VALUES ( '1', '5');
INSERT INTO `sys_role_menu` ( `role_id`, `menu_id`) VALUES ( '1', '6');
INSERT INTO `sys_role_menu` ( `role_id`, `menu_id`) VALUES ( '1', '7');
INSERT INTO `sys_role_menu` ( `role_id`, `menu_id`) VALUES ( '1', '8');
INSERT INTO `sys_role_menu` ( `role_id`, `menu_id`) VALUES ( '1', '9');
INSERT INTO `sys_role_menu` ( `role_id`, `menu_id`) VALUES ( '1', '10');
INSERT INTO `sys_role_menu` ( `role_id`, `menu_id`) VALUES ( '1', '11');
INSERT INTO `sys_role_menu` ( `role_id`, `menu_id`) VALUES ( '1', '12');
INSERT INTO `sys_role_menu` ( `role_id`, `menu_id`) VALUES ( '1', '13');
INSERT INTO `sys_role_menu` ( `role_id`, `menu_id`) VALUES ( '1', '14');
INSERT INTO `sys_role_menu` ( `role_id`, `menu_id`) VALUES ( '1', '15');
INSERT INTO `sys_role_menu` ( `role_id`, `menu_id`) VALUES ( '1', '16');
INSERT INTO `sys_role_menu` ( `role_id`, `menu_id`) VALUES ( '1', '17');
INSERT INTO `sys_role_menu` ( `role_id`, `menu_id`) VALUES ( '1', '18');

INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `del_flag`) VALUES ('1', '1', '是', 'yes_no', '', '10', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `del_flag`) VALUES ('2', '0', '否', 'yes_no', '', '20', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `del_flag`) VALUES ('3', '1', 'text', 'show_type', '', '10', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `del_flag`) VALUES ('4', '2', 'hidden', 'show_type', '', '20', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `del_flag`) VALUES ('5', '3', 'textarea', 'show_type', '', '30', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `del_flag`) VALUES ('6', '4', 'radio', 'show_type', '', '40', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `del_flag`) VALUES ('7', '5', 'checkbox', 'show_type', '', '50', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `del_flag`) VALUES ('8', '6', 'select', 'show_type', '', '60', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `del_flag`) VALUES ('9', '7', 'rich', 'show_type', '', '70', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `del_flag`) VALUES ('10', '8', 'date', 'show_type', '', '80', '0');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `del_flag`) VALUES ('11', '9', 'autocomplete', 'show_type', '', '90', '0');



--changeset sunhan:003
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `icon`, `show`, `permission`, `remarks`, `create_date`, `update_date`, `del_flag`) VALUES ('19', '0', ',0,', '测试', '10', 'admin/common/test.html', 'icon-home', '1', '', '', now(), now(), '0');
CREATE TABLE `test_gen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(100) NOT NULL COMMENT 'text',
  `hidden` varchar(100) DEFAULT NULL COMMENT 'hidden',
  `textarea` varchar(100) NOT NULL COMMENT 'textarea',
  `radio` int(11) DEFAULT NULL COMMENT 'radio',
  `checkbox` int(11) DEFAULT NULL COMMENT 'checkbox',
  `select` int(11) DEFAULT NULL COMMENT 'select',
  `long_text` longtext COMMENT 'longtext',
  `date` datetime DEFAULT NULL COMMENT 'date',
  `autocomplete` varchar(100) DEFAULT NULL COMMENT 'autocomplete',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='测试代码生成';