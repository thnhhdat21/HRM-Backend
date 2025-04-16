package vn.tdsoftware.hrm_backend.util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvertUtil {
    public static List<Integer> permissionsDefault = new ArrayList<>(Arrays.asList(1, 2, 3));
    public static List<Integer> permissionsAdmin = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29));

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
