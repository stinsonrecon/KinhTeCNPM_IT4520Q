package vn.com.hust.utils;

import org.springframework.web.bind.annotation.RequestMapping;

public class Constants {
    public static final class SITE_MAP {
        // BẢNG TÍNH TOÁN ĐIỂM CÁC TÁC NHÂN (ACTORS) TƯƠNG TÁC
        public static final String INTERACTIVE_ACTOR_POINT = "/hust/interactive-actor-point";

        // BẢNG TÍNH TOÁN ĐIỂM CÁC TRƯỜNG HỢP SỬ DỤNG
        public static final String USE_CASES_POINT = "/hust/use-cases-point";

        // BẢNG XÁC ĐỊNH GIÁ PHẦN MỀM
        public static final String SOFTWARE_PRICE_DETERMINATION = "/hust/software-price-determination";

        // BẢNG TÍNH TOÁN HỆ SỐ PHỨC TẠP KỸ THUẬT - CÔNG NGHỆ
        public static final String TECHNOLOGY_COMPREHENSION_CALCULATION = "/hust/technology-comprehension-calculation";

        // HỆ SỐ PTMT - KN - NỘI SUY
        public static final String HE_SO_PTMT_KN_NOI_SUY = "/hust/he-so-PTMT-KN-noi-suy";

        // BẢNG TÍNH LƯƠNG BÌNH QUÂN / NGƯỜI/ GIỜ
        public static final String PERSONAL_SALARY = "/hust/personal-salary";
    }

    public static final class REQUEST_MAPPING {
        public static final String ACTION_COMMON_REPORT = "/hust/exportFile";
    }
}
