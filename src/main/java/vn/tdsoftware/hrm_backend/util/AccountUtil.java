package vn.tdsoftware.hrm_backend.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class AccountUtil {


    public static String generateUsername(String fullName, long employeeId) {
        if (fullName == null || fullName.isBlank()) return "";

        // Bỏ dấu và chuyển thành chữ thường
        String noAccentName = removeDiacritics(fullName).toLowerCase();

        String[] parts = noAccentName.trim().split("\\s+");
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

    private static String removeDiacritics(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("")
                .replaceAll("đ", "d")
                .replaceAll("Đ", "d");
    }
}
