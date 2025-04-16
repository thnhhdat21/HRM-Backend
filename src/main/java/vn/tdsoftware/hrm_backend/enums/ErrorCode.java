package vn.tdsoftware.hrm_backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SQL_MAPPER_ERROR(999, "Lỗi mapper"),
    NAME_AND_CODE_IS_NULL(1001, "Không để trống tên hoặc mã"),
    ROLE_NOT_EXISTED(1002, "Không tồn tại quyền này"),
    LIST_BUSINESS_BLOCK_IS_EMPTY(1003, "Không tồn tại khối nghiệp vụ nào"),
    BUSINESS_BLOCK_IS_EMPTY(1004, "Không tồn tại khối nghiệp vụ này"),
    DELETE_BUSINESS_BLOCK_ERROR(1005, "Không thể xóa khối nghiệp vụ này do vẫn còn nhân sự trong khối này"),

    DEPARTMENT_IS_EMPTY(1006, "Không tồn tại phòng ban nào"),
    DELETE_DEPARTMENT_ERROR(1007, "Không thể xóa phòng này do vẫn còn nhân sự trong phòng này"),
    LIST_ACCOUNT_IS_EMPTY(1008, "Không tồn tại tài khoản nào"),
    TYPE_ACCOUNT_NOT_EXIST(1009, "Trạng thái tài khoản không phù hợp"),
    ID_NOT_EXIST(1010, "Id không hợp lệ"),
    ACCOUNT_UNLOCKED(1011, "Tài khoản đã được mở khóa trước đó"),
    ACCOUNT_LOCKED(1012, "Tài khoản này đã bị khóa trước đó"),
    PASSWORD_INVALID(1013, "Mật khẩu không hợp lệ"),
    EMPLOYEE_IS_EMPTY(1014, "Không tồn tại nhân viên này"),
    USERNAME_OR_PASSWORD_IS_EXIST(1015, "Tên tài khoản hoặc mật khẩu không hợp lệ"),
    ACCOUNT_EXIST(1016, "Nhân sự đã có tài khoản"),
    ACCOUNT_NOT_EXIST(1017, "Không có tài khoản nào"),
    ACCOUNT_ACTIVATED(1018, "Tài khoản đã được kích hoạt từ trước"),
    EMPLOYEE_CODE_NOT_EXIST(1019, "Mã nhân viên không chính xác"),

    LIST_DUTY_IS_EMPTY(1020, "Không có chức vụ nào"),
    NAME_DUTY_INVALID(1021, "Tên chức vụ không hợp lệ"),
    DUTY_NOT_EXIST(1022, "Không tồn tại chức vụ có id như vậy"),

    LIST_JOB_POSITION_IS_EMPTY(1023, "Không tồn tại vị trí công việc nào"),
    NAME_INVALID(1024, "Tên không hợp lệ"),
    RANK_SALARY_INVALID(1025, "Mức lương không phù hợp"),
    JOB_POSITION_NOT_EXIST(1026, "Không tồn tại vị trí công việc nào"),
    DELETE_DUTY_ERROR_DUE_JOB_POSITION(1027, "Không thể xóa chức vụ do còn những vị trí tương ứng"),

    TYPE_INVALID(1028, "Type không hợp lệ"),
    LIST_REWARD_OR_PENALTY_IS_EMPTY(1029, "Chưa có danh sách"),
    AMOUNT_INVALID(1030, "Số tiền không hơp lệ"),
    REWARD_OR_PENALTY_IS_EMPTY(1031, "Không có khen thưởng hoặc phạt nội bộ nào"),

    LIST_ALLOWANCE_IS_EMPTY(1032, "Chưa có danh sách phụ cấp"),
    UNIT_INVALID(1033, "Đơn vị không hợp lệ"),
    ALLOWANCE_IS_EMPTY(1034, "Phụ cấp không tồn tại"),

    LIST_TYPE_CONTRACT_IS_EMPTY(1035, "Chưa có danh sách loại hợp đồng"),
    TYPE_CONTRACT_IS_EMPTY(1037, "Loại hợp đồng không tồn tại"),
    ALLOWANCE_IS_EMPTY_FLEX(1038, "Không tồn tại phụ cấp có id ["),
    TYPE_CONTRACT_INVALID(1039, "Loại hợp đồng không hợp lệ"),
    METHOD_INVALID(1040, "Hình thức không hợp lệ"),
    TERM_INVALID(1041, "thời hạn không hợp lệ"),


    LIST_ASSET_GROUP_IS_EMPTY(1042, "Danh sách nhóm tài sản trống"),
    ASSET_GROUP_IS_EMPTY(1043, "Nhóm tài sản không tồn tại"),
    ASSET_GROUP_PARENT_IS_EMPTY(1044, "Nhóm tài sản cha không tồn tại"),
    DELETE_ASSET_GROUP_ERROR(1045, "Không thể xóa nhóm tài sản do vẫn còn các nhóm tài sản con"),

    LIST_ASSET_UNIT_IS_EMPTY(1046, "Danh sách đơn vị tài sản trống"),
    ASSET_UNIT_IS_EMPTY(1047, "Đơn vị tài sản không tồn tại"),

    INSURANCE_SETTING_IS_EMPTY(1048, "Chưa có cài đặt bảo hiểm nào"),
    INSURANCE_SETTING_ERROR(1049, "Không tìm thấy cài đặt bảo hiểm"),

    LIST_INSURANCE_RATIO_IS_EMPTY(1050, "Danh sách cài đặt tỉ lệ trống"),
    INSURANCE_RATIO_INVALID(1051, "Cài đặt tỉ lệ không tồn tại"),
    INSURANCE_RATIO_DETAIL_INVALID(1052, "Chi tiết tỉ lệ không tồn tại"),

    LEAVE_SETTING_IS_EMPTY(1053, "Chưa có cài đặt nghỉ phép nào"),

    LIST_WORK_SHIFT_IS_EMPTY(1054, "Chưa có ca làm việc nào"),
    TIME_IN_OR_TIME_OUT_INVALID(1055, "Thời gian vào hoặc ra không hợp lý"),
    TIME_BREAK_INVALID(1056, "Thời gian nghỉ không hợp lý"),
    WORK_SHIFT_IS_EMPTY(1057, "Ca làm việc không tồn tại"),

    LIST_APPROVAL_REASON_IS_EMPTY(1058, "Chưa có danh sách lý do"),
    LIST_APPROVAL_REASON_REQUEST_IS_EMPTY(1059, "Không có yêu cầu tạo mới nào"),
    REASON_INVALID(1060, "Yêu cầu nhập lý do"),
    SYMBOL_INVALID(1061, "Ký hiệu không hợp lệ"),
    MAXIMUM_INVALID(1062, "Số ngày tối đa không hợp lệ"),
    WORK_DAY_INVALID(1063, "Tính công không hợp lệ"),
    GO_LATE_INVALID(1064, "Thời gian đi muộn không hợp lệ"),
    BACK_EARLY_INVALID(1065, "Thời gian về sớm không hợp lệ"),
    REASON_IS_EMPTY(1066, "Không có lý do hợp lệ"),
    ;

    private final int code;
    private final String message;
}
