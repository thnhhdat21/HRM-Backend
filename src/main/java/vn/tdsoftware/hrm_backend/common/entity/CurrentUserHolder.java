package vn.tdsoftware.hrm_backend.common.entity;

public class CurrentUserHolder {

    private static final ThreadLocal<Long> employeeIdHolder = new ThreadLocal<>();

    public static void setEmployeeId(Long employeeId) {
        employeeIdHolder.set(employeeId);
    }

    public static Long getEmployeeId() {
        return employeeIdHolder.get();
    }

    public static void clear() {
        employeeIdHolder.remove();
    }
}
