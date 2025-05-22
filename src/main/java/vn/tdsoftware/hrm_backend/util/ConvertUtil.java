package vn.tdsoftware.hrm_backend.util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvertUtil {
    public static List<Integer> permissionsDefault = new ArrayList<>(Arrays.asList(2, 5, 7, 11, 16, 27, 30, 31));

    public static List<Integer> permissionValidator(String permissions) {
        List<Integer> listPermission = new ArrayList<>();
        for (String s : permissions.split(",")) {
            if (!s.isEmpty()) { // Bỏ qua chuỗi rỗng
                if (!s.matches("\\d+")) { // Kiểm tra nếu không phải số
                    throw new IllegalArgumentException("Danh sách quyền chứa giá trị không hợp lệ!");
                }
                listPermission.add(Integer.parseInt(s));
            }
        }
        return listPermission;
    }

    public static List<Long> allowanceValidator(String allowances) {
        List<Long> listPermission = new ArrayList<>();
        for (String s : allowances.split(",")) {
            if (!s.isEmpty()) { // Bỏ qua chuỗi rỗng
                if (!s.matches("\\d+")) { // Kiểm tra nếu không phải số
                    throw new IllegalArgumentException("Danh sách phụ cấp chứa giá trị không hợp lệ!");
                }
                listPermission.add(Long.parseLong(s));
            }
        }
        return listPermission;
    }

    public static List<Long> convertStringToListLong(String list) {
        List<Long> listPermission = new ArrayList<>();
        for (String s : list.split(",")) {
                listPermission.add(Long.parseLong(s));
        }
        return listPermission;
    }
}
