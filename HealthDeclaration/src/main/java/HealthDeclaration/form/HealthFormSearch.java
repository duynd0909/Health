package HealthDeclaration.form;

import lombok.Data;

import java.util.Date;

@Data
public class HealthFormSearch {
    // health form
    private boolean diChuyen;
    private boolean dauHieuCovid;
    private boolean tiepXucCovider;
    private boolean tiepXucForeign;
    private boolean tiepXucNguoiCoBieuHienCovid;
    // move form
    private String tenPhuongTien;
    private String SoHieu;
    private Date startDate;
    private int startProvinceId;
    private int startDistrictId;
    private int startWardId;
    private String startDetailAddress;
    private Date endDate;
    private int endProvinceId;
    private int endDistrictId;
    private int endWardId;
    private String endDetailAddress;
    // username
    private String username;
}
