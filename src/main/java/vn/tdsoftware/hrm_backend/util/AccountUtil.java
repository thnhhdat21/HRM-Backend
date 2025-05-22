package vn.tdsoftware.hrm_backend.util;

public class AccountUtil {

    public static String generateUsername(String fullName, long employeeId) {
        if (fullName == null || fullName.isBlank()) return "";

        String[] parts = fullName.trim().toLowerCase().split("\\s+");
        if (parts.length == 0) return "";

        StringBuilder username = new StringBuilder();
        // Tên là phần cuối
        username.append(parts[parts.length - 1]);

        // Lấy ký tự đầu của các phần còn lại (họ và tên đệm)
        for (int i = 0; i < parts.length - 1; i++) {
            username.append(parts[i].charAt(0));
        }
        return username.toString() + employeeId + "@tds.vn";
    }

}
