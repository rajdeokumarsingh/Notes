package com.agile.payroll.database;

import com.agile.payroll.utils.Debug;
import com.agile.payroll.domain.Employee;

import java.util.Collection;
import java.util.HashMap;

public class PayrollDatabase {
    private HashMap<String, Employee> employeeMap = new HashMap<String, Employee>();
    private HashMap<String, Employee> memberMap = new HashMap<String, Employee>();
    
    private static final PayrollDatabase mInstance = new PayrollDatabase();
    
    public static PayrollDatabase getInstance() {
        return mInstance;
    }
    
    public void addEmployee(String id, Employee emp) {
        Debug.LogDB("addEmployee, id: " + id + ", info: " + emp);
        employeeMap.put(id, emp);
    }
    
    public void removeEmployee(String id) {
        Debug.LogDB("removeEmployee, id: " + id);
        employeeMap.remove(id);
    }
    
    public Employee getEmployee(String id) {
        return employeeMap.get(id);
    }

    public Collection<Employee> allEmployees() {
        return employeeMap.values();
    }

    public void clearAllEmployee() {
        Debug.LogDB("clearAllEmployee");
        employeeMap.clear();
    }

    public void addUnionMember(String memberId, Employee e) {
        Debug.LogDB("addUnionMemeber, member id:" + memberId
                + ", employee: " + e.toString());

        memberMap.put(memberId, e);
    }
    
    public Employee getUnionMember(String id) {
        Debug.LogDB("getUnoinMember: " +id);
        return memberMap.get(id);
    }

    public void removeUnionMember(Employee e) {
        Debug.LogDB("removeUnionMember: " +e.toString());
        memberMap.remove(e.getMemberId());
    }
}
