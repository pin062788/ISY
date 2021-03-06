package top.zbeboy.isy.web.data.staff;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.zbeboy.isy.config.ISYProperties;
import top.zbeboy.isy.config.Workbook;
import top.zbeboy.isy.domain.tables.pojos.Staff;
import top.zbeboy.isy.domain.tables.pojos.Users;
import top.zbeboy.isy.domain.tables.records.StaffRecord;
import top.zbeboy.isy.elastic.pojo.StaffElastic;
import top.zbeboy.isy.glue.data.StaffGlue;
import top.zbeboy.isy.glue.util.ResultUtils;
import top.zbeboy.isy.service.cache.CacheManageService;
import top.zbeboy.isy.service.data.StaffService;
import top.zbeboy.isy.service.platform.UsersService;
import top.zbeboy.isy.service.system.MailService;
import top.zbeboy.isy.service.util.BCryptUtils;
import top.zbeboy.isy.service.util.DateTimeUtils;
import top.zbeboy.isy.service.util.RandomUtils;
import top.zbeboy.isy.service.util.RequestUtils;
import top.zbeboy.isy.web.bean.data.staff.StaffBean;
import top.zbeboy.isy.web.util.AjaxUtils;
import top.zbeboy.isy.web.util.DataTablesUtils;
import top.zbeboy.isy.web.vo.register.staff.StaffVo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2016-08-28.
 */
@Slf4j
@Controller
public class StaffController {

    @Resource
    private StaffService staffService;

    @Resource
    private UsersService usersService;

    @Resource
    private CacheManageService cacheManageService;

    @Resource
    private MailService mailService;

    @Autowired
    private ISYProperties isyProperties;

    @Autowired
    private RequestUtils requestUtils;

    @Resource
    private StaffGlue staffGlue;

    /**
     * 判断工号是否已被注册
     *
     * @param staffNumber 工号
     * @return true未被注册 false已被注册
     */
    @RequestMapping(value = "/user/register/valid/staff", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils validStaff(@RequestParam("staffNumber") String staffNumber) {
        Staff staff = staffService.findByStaffNumber(staffNumber);
        if (!ObjectUtils.isEmpty(staff)) {
            return AjaxUtils.of().fail();
        }
        return AjaxUtils.of().success();
    }

    /**
     * 已登录用户工号更新检验
     *
     * @param username    用户账号
     * @param staffNumber 工号
     * @return true 可以用 false 不可以
     */
    @RequestMapping(value = "/anyone/users/valid/staff", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils validAnyoneStudent(@RequestParam("username") String username, @RequestParam("staffNumber") String staffNumber) {
        Result<StaffRecord> staffRecords = staffService.findByStaffNumberNeUsername(username, staffNumber);
        if (staffRecords.isEmpty()) {
            return AjaxUtils.of().success();
        }
        return AjaxUtils.of().fail();
    }

    /**
     * 教职工注册
     *
     * @param staffVo       教职工
     * @param bindingResult 检验
     * @param session       session
     * @param request       请求
     * @return true 注册成功 false注册失败
     */
    @RequestMapping(value = "/user/register/staff", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils registerStaff(@Valid StaffVo staffVo, BindingResult bindingResult, HttpSession session, HttpServletRequest request) {
        if (!bindingResult.hasErrors()) {
            String email = StringUtils.trimWhitespace(staffVo.getEmail());
            String mobile = StringUtils.trimWhitespace(staffVo.getMobile());
            if (!ObjectUtils.isEmpty(session.getAttribute("mobile"))) {
                String tempMobile = (String) session.getAttribute("mobile");
                if (!staffVo.getMobile().equals(tempMobile)) {
                    return AjaxUtils.of().fail().msg("发现手机号不一致，请重新获取验证码");
                } else {
                    if (!ObjectUtils.isEmpty(session.getAttribute("mobileExpiry"))) {
                        Date mobileExpiry = (Date) session.getAttribute("mobileExpiry");
                        Date now = new Date();
                        if (!now.before(mobileExpiry)) {
                            return AjaxUtils.of().fail().msg("验证码已过有效期(30分钟)");
                        } else {
                            if (!ObjectUtils.isEmpty(session.getAttribute("mobileCode"))) {
                                String mobileCode = (String) session.getAttribute("mobileCode");
                                if (!staffVo.getPhoneVerifyCode().equals(mobileCode)) {
                                    return AjaxUtils.of().fail().msg("验证码错误");
                                } else {
                                    String password = StringUtils.trimWhitespace(staffVo.getPassword());
                                    String confirmPassword = StringUtils.trimWhitespace(staffVo.getConfirmPassword());
                                    if (!password.equals(confirmPassword)) {
                                        return AjaxUtils.of().fail().msg("密码不一致");
                                    } else {
                                        // 注册成功
                                        Users saveUsers = new Users();
                                        StaffElastic saveStaff = new StaffElastic();
                                        Byte enabled = 1;
                                        Byte verifyMailbox = 0;
                                        saveUsers.setUsername(email);
                                        saveUsers.setEnabled(enabled);
                                        saveStaff.setEnabled(enabled);
                                        saveUsers.setMobile(mobile);
                                        saveStaff.setMobile(mobile);
                                        saveUsers.setPassword(BCryptUtils.bCryptPassword(password));
                                        saveUsers.setUsersTypeId(cacheManageService.findByUsersTypeName(Workbook.STAFF_USERS_TYPE).getUsersTypeId());
                                        saveUsers.setJoinDate(new java.sql.Date(Clock.systemDefaultZone().millis()));
                                        saveStaff.setJoinDate(saveUsers.getJoinDate());

                                        DateTime dateTime = DateTime.now();
                                        dateTime = dateTime.plusDays(Workbook.MAILBOX_VERIFY_VALID);
                                        String mailboxVerifyCode = RandomUtils.generateEmailCheckKey();
                                        saveUsers.setMailboxVerifyCode(mailboxVerifyCode);
                                        saveUsers.setMailboxVerifyValid(new Timestamp(dateTime.toDate().getTime()));
                                        saveUsers.setLangKey(request.getLocale().toLanguageTag());
                                        saveStaff.setLangKey(saveUsers.getLangKey());
                                        saveUsers.setAvatar(Workbook.USERS_AVATAR);
                                        saveStaff.setAvatar(saveUsers.getAvatar());
                                        saveUsers.setVerifyMailbox(verifyMailbox);
                                        saveUsers.setRealName(staffVo.getRealName());
                                        saveStaff.setRealName(saveUsers.getRealName());
                                        usersService.save(saveUsers);

                                        saveStaff.setSchoolId(staffVo.getSchool());
                                        saveStaff.setSchoolName(staffVo.getSchoolName());
                                        saveStaff.setCollegeId(staffVo.getCollege());
                                        saveStaff.setCollegeName(staffVo.getCollegeName());
                                        saveStaff.setDepartmentId(staffVo.getDepartment());
                                        saveStaff.setDepartmentName(staffVo.getDepartmentName());
                                        saveStaff.setDepartmentId(staffVo.getDepartment());
                                        saveStaff.setStaffNumber(staffVo.getStaffNumber());
                                        saveStaff.setUsername(email);
                                        staffService.save(saveStaff);

                                        //清空session
                                        session.removeAttribute("mobile");
                                        session.removeAttribute("mobileExpiry");
                                        session.removeAttribute("mobileCode");

                                        //发送验证邮件
                                        if (isyProperties.getMail().isOpen()) {
                                            mailService.sendValidEmailMail(saveUsers, requestUtils.getBaseUrl(request));
                                            return AjaxUtils.of().success().msg("恭喜注册成功，请验证邮箱");
                                        } else {
                                            return AjaxUtils.of().fail().msg("邮件推送已被管理员关闭");
                                        }
                                    }
                                }
                            } else {
                                return AjaxUtils.of().fail().msg("无法获取当前用户电话验证码，请重新获取手机验证码");
                            }
                        }
                    } else {
                        return AjaxUtils.of().fail().msg("无法获取当前用户验证码有效期，请重新获取手机验证码");
                    }
                }
            } else {
                return AjaxUtils.of().fail().msg("无法获取当前用户电话，请重新获取手机验证码");
            }
        } else {
            return AjaxUtils.of().fail().msg("参数异常，请检查输入内容是否正确");
        }
    }

    /**
     * 教职工数据
     *
     * @return 教职工数据页面
     */
    @RequestMapping(value = "/web/menu/data/staff", method = RequestMethod.GET)
    public String staffData() {
        return "web/data/staff/staff_data::#page-wrapper";
    }

    /**
     * datatables ajax查询数据
     *
     * @param request 请求
     * @return datatables数据
     */
    @RequestMapping(value = "/web/data/staff/pass/data", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesUtils<StaffBean> staffPassDatas(HttpServletRequest request) {
        // 前台数据标题 注：要和前台标题顺序一致，获取order用
        List<String> headers = new ArrayList<>();
        headers.add("select");
        headers.add("real_name");
        headers.add("staff_number");
        headers.add("username");
        headers.add("mobile");
        headers.add("id_card");
        headers.add("role_name");
        headers.add("school_name");
        headers.add("college_name");
        headers.add("department_name");
        headers.add("academic_title_name");
        headers.add("post");
        headers.add("sex");
        headers.add("birthday");
        headers.add("nation_name");
        headers.add("politicalLandscape_name");
        headers.add("family_residence");
        headers.add("enabled");
        headers.add("lang_key");
        headers.add("join_date");
        headers.add("operator");
        DataTablesUtils<StaffBean> dataTablesUtils = new DataTablesUtils<>(request, headers);
        ResultUtils<List<StaffBean>> resultUtils = staffGlue.findAllByPageExistsAuthorities(dataTablesUtils);
        dataTablesUtils.setData(resultUtils.getData());
        dataTablesUtils.setiTotalRecords(staffGlue.countAllExistsAuthorities());
        dataTablesUtils.setiTotalDisplayRecords(resultUtils.getTotalElements());
        return dataTablesUtils;
    }

    /**
     * datatables ajax查询数据
     *
     * @param request 请求
     * @return datatables数据
     */
    @RequestMapping(value = "/web/data/staff/wait/data", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesUtils<StaffBean> staffWaitDatas(HttpServletRequest request) {
        // 前台数据标题 注：要和前台标题顺序一致，获取order用
        List<String> headers = new ArrayList<>();
        headers.add("select");
        headers.add("real_name");
        headers.add("staff_number");
        headers.add("username");
        headers.add("mobile");
        headers.add("school_name");
        headers.add("college_name");
        headers.add("department_name");
        headers.add("lang_key");
        headers.add("join_date");
        headers.add("operator");
        DataTablesUtils<StaffBean> dataTablesUtils = new DataTablesUtils<>(request, headers);
        ResultUtils<List<StaffBean>> resultUtils = staffGlue.findAllByPageNotExistsAuthorities(dataTablesUtils);
        dataTablesUtils.setData(resultUtils.getData());
        dataTablesUtils.setiTotalRecords(staffGlue.countAllNotExistsAuthorities());
        dataTablesUtils.setiTotalDisplayRecords(resultUtils.getTotalElements());
        return dataTablesUtils;
    }

    /**
     * 更新用户学校信息
     *
     * @param department 系id
     * @return true 更新成功 false 更新失败
     */
    @RequestMapping(value = "/anyone/users/profile/staff/school/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils staffSchoolUpdate(@RequestParam("department") int department) {
        Users users = usersService.getUserFromSession();
        Staff staff = staffService.findByUsername(users.getUsername());
        staff.setDepartmentId(department);
        staffService.update(staff);
        return AjaxUtils.of().success().msg("更新学校信息成功");
    }

    /**
     * 更新基本信息
     *
     * @param staffVo       教职工信息
     * @param bindingResult 检验
     * @return true or false
     */
    @RequestMapping(value = "/anyone/users/staff/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils studentUpdate(@Valid top.zbeboy.isy.web.vo.platform.users.StaffVo staffVo, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            try {
                Users users = usersService.findByUsername(staffVo.getUsername());
                String realName = staffVo.getRealName();
                String avatar = staffVo.getAvatar();
                if (StringUtils.hasLength(realName)) {
                    users.setRealName(realName);
                } else {
                    users.setRealName(null);
                }
                if (StringUtils.hasLength(avatar)) {
                    users.setAvatar(staffVo.getAvatar());
                } else {
                    users.setAvatar(Workbook.USERS_AVATAR);
                }
                usersService.update(users);

                Staff staff = staffService.findByUsername(staffVo.getUsername());
                staff.setStaffNumber(staffVo.getStaffNumber());
                staff.setSex(staffVo.getSex());
                staff.setNationId(staffVo.getNationId());
                staff.setPoliticalLandscapeId(staffVo.getPoliticalLandscapeId());
                staff.setAcademicTitleId(staffVo.getAcademicTitleId());
                if (StringUtils.hasLength(staffVo.getBirthday())) {
                    staff.setBirthday(DateTimeUtils.formatDate(staffVo.getBirthday()));
                } else {
                    staff.setBirthday(null);
                }
                if (StringUtils.hasLength(staffVo.getIdCard())) {
                    staff.setIdCard(staffVo.getIdCard());
                } else {
                    staff.setIdCard(null);
                }
                staff.setFamilyResidence(staffVo.getFamilyResidence());
                staff.setPost(staffVo.getPost());
                staffService.update(staff);
                return AjaxUtils.of().success();
            } catch (ParseException e) {
                log.error("Birthday to sql date is exception : {}", e.getMessage());
                return AjaxUtils.of().fail().msg("时间转换异常");
            }
        }
        return AjaxUtils.of().fail().msg("参数检验错误");
    }
}
