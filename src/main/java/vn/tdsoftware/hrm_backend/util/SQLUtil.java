package vn.tdsoftware.hrm_backend.util;

import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.util.constant.FilterConstant;

import java.util.List;

public class SQLUtil {
    public static String sqlFilter(EmployeeFilter filter, int typeFiler) {
        StringBuilder sql = new StringBuilder();
        if (filter.getName() != null && !filter.getName().isEmpty()) {
            sql.append(" and employee.fullname like '%").append(filter.getName()).append("%'");
        }
        if (typeFiler == FilterConstant.TYPE_EMPLOYEE && filter.getType() != null && filter.getType() > 0) {
            sql.append(" and employee.type = ").append(filter.getType());
        } else if (typeFiler == FilterConstant.TYPE_CONTRACT && filter.getType() != null && filter.getType() > 0) {
            sql.append(" and contract.type = ").append(filter.getType());
        }

        if (typeFiler == FilterConstant.TYPE_EMPLOYEE && filter.getStatus() != null && filter.getStatus() > 0) {
            sql.append(" and employee.status = ").append(filter.getStatus());
        } else if (typeFiler == FilterConstant.TYPE_CONTRACT && filter.getStatus() != null && filter.getStatus() > 0) {
            sql.append(" and contract.status = ").append(filter.getStatus());
        }else if (typeFiler == FilterConstant.TYPE_DECISION && filter.getType() != null && filter.getType() > 0) {
            sql.append(" and decision.state = ").append(filter.getType());
        }else if (typeFiler == FilterConstant.TYPE_LETTER && filter.getType() != null && filter.getType() > 0) {
            sql.append(" and letter.state = ").append(filter.getType());
        }else if (typeFiler == FilterConstant.TYPE_INSURANCE && filter.getStatus() != null && filter.getStatus() > 0) {
            sql.append(" and insurance.status = ").append(filter.getStatus());
        }


        if (filter.getDateJoin() != null && !filter.getDateJoin().isEmpty()) {
            sql.append(" and employee.createdAt >= '").append(filter.getDateJoin()).append("' ");
        }
        if (filter.getDepartment() != null && !filter.getDepartment().isEmpty()) {
            sql.append(" and  ( ");
            List<Long> departments = filter.getDepartment();
            for (int i = 0; i < departments.size(); i++) {
                sql.append(" department.id = ").append(departments.get(i));
                if (i < departments.size() - 1) {
                    sql.append(" or ");
                }
            }
            sql.append(" ) ");
        }
        boolean getJobPosition = filter.getJobPosition() != null && !filter.getJobPosition().isEmpty();
        boolean getDuty = filter.getDuty() != null && !filter.getDuty().isEmpty();
        if (getJobPosition || getDuty) {
            sql.append(" and  ( ");
            if (!filter.getJobPosition().isEmpty()) {
                List<Long> jobPositions = filter.getJobPosition();
                for (int i = 0; i < jobPositions.size(); i++) {
                    sql.append(" jobPosition.id = ").append(jobPositions.get(i));
                    if (i < jobPositions.size() - 1) {
                        sql.append(" or ");
                    }
                }
            }
            if (getJobPosition && getDuty) {
                sql.append(" or ");
            }
            if (getDuty) {
                List<Long> dutys = filter.getDuty();
                for (int i = 0; i < dutys.size(); i++) {
                    sql.append("duty.id = ").append(dutys.get(i));
                    if (i < dutys.size() - 1) {
                        sql.append(" or ");
                    }
                }
            }
            sql.append(" ) ");
        }
        return sql.toString();
    }
}
