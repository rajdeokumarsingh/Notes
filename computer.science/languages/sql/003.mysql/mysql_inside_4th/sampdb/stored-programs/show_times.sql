# display current local and UTC times

DROP PROCEDURE IF EXISTS show_times;
delimiter $
CREATE PROCEDURE show_times()
BEGIN
  SELECT 'Local time is:', CURRENT_TIMESTAMP;
  SELECT 'UTC time is:', UTC_TIMESTAMP;
END$
delimiter ;
CALL show_times();
