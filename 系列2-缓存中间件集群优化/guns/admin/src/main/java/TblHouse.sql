/*
 * Copyright 2021-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

INSERT INTO `guns`.`sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('947048430251225089', 'tblHouse', '0', '[0],', '房屋管理', '', '/tblHouse', '99', '2', '1', NULL, '1', '0');
INSERT INTO `guns`.`sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('947048430251225090', 'tblHouse_list', 'tblHouse', '[0],[947048430251225089],', '房屋管理列表', '', '/tblHouse/list', '99', '3', '0', NULL, '1', '0');
INSERT INTO `guns`.`sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('947048430251225091', 'tblHouse_add', 'tblHouse', '[0],[947048430251225089],', '房屋管理添加', '', '/tblHouse/add', '99', '3', '0', NULL, '1', '0');
INSERT INTO `guns`.`sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('947048430251225092', 'tblHouse_update', 'tblHouse', '[0],[947048430251225089],', '房屋管理更新', '', '/tblHouse/update', '99', '3', '0', NULL, '1', '0');
INSERT INTO `guns`.`sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('947048430251225093', 'tblHouse_delete', 'tblHouse', '[0],[947048430251225089],', '房屋管理删除', '', '/tblHouse/delete', '99', '3', '0', NULL, '1', '0');
INSERT INTO `guns`.`sys_menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`) VALUES ('947048430251225094', 'tblHouse_detail', 'tblHouse', '[0],[947048430251225089],', '房屋管理详情', '', '/tblHouse/detail', '99', '3', '0', NULL, '1', '0');
