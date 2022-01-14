/*
 * Copyright 2021-2022 the original author or authors.
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

ALTER TABLE `tlshop`.`t_activity` ADD COLUMN `hasBuyGroupPerson` INT NULL DEFAULT 0  AFTER `tuanPrice` ;

ALTER TABLE `tlshop`.`t_product` ADD COLUMN `giftID` VARCHAR(45) NULL  AFTER `isTimePromotion` ;

CREATE  TABLE `tlshop`.`t_gift` (

  `id` INT NOT NULL AUTO_INCREMENT ,

  `giftName` VARCHAR(100) NOT NULL ,

  `giftPrice` DECIMAL(9,2) NULL ,

  `createAccount` VARCHAR(45) NULL ,

  `createtime` DATETIME NULL ,

  `updateAccount` VARCHAR(45) NULL ,

  `updatetime` DATETIME NULL ,

  `status` VARCHAR(5) NULL DEFAULT 'down' ,

  PRIMARY KEY (`id`) )

ENGINE = InnoDB;

ALTER TABLE `tlshop`.`t_gift` ADD COLUMN `picture` VARCHAR(100) NULL  AFTER `status` ;


CREATE  TABLE `tlshop`.`t_hotQuery` (

  `id` INT NOT NULL AUTO_INCREMENT ,

  `key` VARCHAR(45) NOT NULL ,

  `url` VARCHAR(100) NOT NULL ,

  PRIMARY KEY (`id`) )

ENGINE = InnoDB;

ALTER TABLE `tlshop`.`t_hotquery` CHANGE COLUMN `key` `key1` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL  ;

ALTER TABLE `tlshop`.`t_orderdetail` ADD COLUMN `giftID` VARCHAR(45) NULL  AFTER `specInfo` ;



