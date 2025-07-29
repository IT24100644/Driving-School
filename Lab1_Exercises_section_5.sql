(a) Display the department IDs where the number of employees is less than 3.

select did
from works
group by did  
having count(eid) <3

(b) List each manager and the number of departments they manage. Show only
managers who manage more than 1 department. Sort by the number of departments ascending.

select managerid, count(*) AS number_of_departments
from dept
group by managerid
having count(*) > 1
order by number_of_departments ASC

(c) Show each age group and the average salary of employees in that group. Display only age groups where the average salary is greater than 40,000.

select age, avg(salary) as avg_salary
from emp
group by age
having avg(salary) > 40000

(d) Find the number of work assignments per employee. Display employees with
more than 2 assignments.

select eid, count(*) as num_of_assignments
from works
group by eid
having count(*) > 2

(e) List departments with a total budget greater than 1,000,000.

select did, budget
from dept
where budget > 1000000