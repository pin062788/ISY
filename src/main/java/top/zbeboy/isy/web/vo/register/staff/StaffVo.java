package top.zbeboy.isy.web.vo.register.staff;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by lenovo on 2016-08-28.
 */
public class StaffVo {

    @NotNull
    @Min(1)
    private int school;

    @NotNull
    @Min(1)
    private int college;

    @NotNull
    @Min(1)
    private int department;

    @NotNull
    @Pattern(regexp = "^[\\d]{8,}$")
    private String staffNumber;

    @NotNull
    @Pattern(regexp = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$")
    private String email;

    @NotNull
    @Pattern(regexp = "^1[0-9]{10}")
    private String mobile;

    @NotNull
    private String phoneVerifyCode;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]\\w{5,17}$")
    private String password;

    @NotNull
    private String confirmPassword;

    public int getSchool() {
        return school;
    }

    public void setSchool(int school) {
        this.school = school;
    }

    public int getCollege() {
        return college;
    }

    public void setCollege(int college) {
        this.college = college;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhoneVerifyCode() {
        return phoneVerifyCode;
    }

    public void setPhoneVerifyCode(String phoneVerifyCode) {
        this.phoneVerifyCode = phoneVerifyCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
