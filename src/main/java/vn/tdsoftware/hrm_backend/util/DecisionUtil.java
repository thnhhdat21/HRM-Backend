package vn.tdsoftware.hrm_backend.util;

import java.util.HashMap;
import java.util.Map;

public class DecisionUtil {
    public static Map<Integer, String> DecisionType = Map.of(
            1, "Quyết định khen thưởng",
            2, "Quyết định điều chuyển",
            3, "Quyết định kỷ luật nội bộ",
            4, "Quyết định bổ nhiệm",
            5, "Quyết định điều chỉnh lương",
            6, "Quyết định chấm dứt HĐLĐ"
    );
}
