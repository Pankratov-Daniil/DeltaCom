CREATE DEFINER = CURRENT_USER TRIGGER `deltacom`.`contract_AFTER_INSERT` AFTER INSERT ON `contract` FOR EACH ROW
BEGIN
	UPDATE `deltacom`.`number_pool` SET `deltacom`.`number_pool`.used = 1 WHERE `deltacom`.`number_pool`.number = NEW.number;
END