CREATE DEFINER = CURRENT_USER TRIGGER `deltacom`.`contract_AFTER_DELETE` AFTER DELETE ON `contract` FOR EACH ROW
BEGIN
	UPDATE `deltacom`.`number_pool` SET `deltacom`.`number_pool`.used = 0 WHERE `deltacom`.`number_pool`.number = OLD.number;
END