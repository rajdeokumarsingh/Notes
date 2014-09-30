# Compute student counts by sex.
# Uses OUT variables to pass values back from procedure to caller.

DROP PROCEDURE IF EXISTS count_students_by_sex;
delimiter $
#@ _FRAG_
CREATE PROCEDURE count_students_by_sex (OUT p_male INT, OUT p_female INT)
BEGIN
  SELECT COUNT(*) FROM student WHERE sex = 'M' INTO p_male;
  SELECT COUNT(*) FROM student WHERE sex = 'F' INTO p_female;
END;
#@ _FRAG_
$
delimiter ;
CALL count_students_by_sex(@mcount, @fcount);
SELECT 'Number of male students:  ', @mcount;
SELECT 'Number of female students:', @fcount;
