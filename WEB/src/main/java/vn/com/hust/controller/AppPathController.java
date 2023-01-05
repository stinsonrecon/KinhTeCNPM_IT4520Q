package vn.com.hust.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.com.hust.utils.Constants;

@Controller
public class AppPathController {
    // Trang chủ
    @RequestMapping(value = "/")
    public String index() {
        return "index/index";
    }

    // Báo cáo START
    // BẢNG TÍNH TOÁN ĐIỂM CÁC TÁC NHÂN (ACTORS) TƯƠNG TÁC
    @RequestMapping(Constants.SITE_MAP.INTERACTIVE_ACTOR_POINT)
    public String interactiveActorPoint() {
        return "report/interactive-actor-point";
    }

    // BẢNG TÍNH TOÁN ĐIỂM CÁC TRƯỜNG HỢP SỬ DỤNG
    @RequestMapping(Constants.SITE_MAP.USE_CASES_POINT)
    public String useCasesPoint() {
        return "report/use-cases-point";
    }

    // BẢNG XÁC ĐỊNH GIÁ PHẦN MỀM
    @RequestMapping(Constants.SITE_MAP.SOFTWARE_PRICE_DETERMINATION)
    public String softwarePriceDetermination() {
        return "report/software-price-determination";
    }

    // BẢNG TÍNH TOÁN HỆ SỐ PHỨC TẠP KỸ THUẬT - CÔNG NGHỆ
    @RequestMapping(Constants.SITE_MAP.TECHNOLOGY_COMPREHENSION_CALCULATION)
    public String technologyComprehensionCalculation() {
        return "report/technology-comprehension-calculation";
    }

    // HỆ SỐ PTMT - KN - NỘI SUY
    @RequestMapping(Constants.SITE_MAP.HE_SO_PTMT_KN_NOI_SUY)
    public String heSo() {
        return "report/he-so-PTMT-KN-noi-suy";
    }

    // BẢNG TÍNH LƯƠNG BÌNH QUÂN / NGƯỜI/ GIỜ
    @RequestMapping(Constants.SITE_MAP.PERSONAL_SALARY)
    public String personalSalary() {
        return "report/personal-salary";
    }
}
