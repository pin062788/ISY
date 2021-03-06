package top.zbeboy.isy.web.platform.users;

import com.alibaba.fastjson.JSON;
import com.octo.captcha.service.CaptchaServiceException;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import top.zbeboy.isy.config.ISYProperties;
import top.zbeboy.isy.config.Workbook;
import top.zbeboy.isy.domain.tables.pojos.*;
import top.zbeboy.isy.domain.tables.records.*;
import top.zbeboy.isy.elastic.config.ElasticBook;
import top.zbeboy.isy.elastic.pojo.StaffElastic;
import top.zbeboy.isy.elastic.pojo.StudentElastic;
import top.zbeboy.isy.elastic.pojo.UsersElastic;
import top.zbeboy.isy.elastic.repository.StaffElasticRepository;
import top.zbeboy.isy.elastic.repository.StudentElasticRepository;
import top.zbeboy.isy.elastic.repository.UsersElasticRepository;
import top.zbeboy.isy.glue.platform.UsersGlue;
import top.zbeboy.isy.glue.util.ResultUtils;
import top.zbeboy.isy.service.cache.CacheManageService;
import top.zbeboy.isy.service.common.CommonControllerMethodService;
import top.zbeboy.isy.service.common.UploadService;
import top.zbeboy.isy.service.data.CollegeRoleService;
import top.zbeboy.isy.service.data.StaffService;
import top.zbeboy.isy.service.data.StudentService;
import top.zbeboy.isy.service.platform.RoleService;
import top.zbeboy.isy.service.platform.UsersService;
import top.zbeboy.isy.service.platform.UsersTypeService;
import top.zbeboy.isy.service.system.AuthoritiesService;
import top.zbeboy.isy.service.system.MailService;
import top.zbeboy.isy.service.system.MobileService;
import top.zbeboy.isy.service.util.BCryptUtils;
import top.zbeboy.isy.service.util.FilesUtils;
import top.zbeboy.isy.service.util.RandomUtils;
import top.zbeboy.isy.service.util.RequestUtils;
import top.zbeboy.isy.web.bean.data.staff.StaffBean;
import top.zbeboy.isy.web.bean.data.student.StudentBean;
import top.zbeboy.isy.web.bean.file.FileBean;
import top.zbeboy.isy.web.bean.platform.users.UsersBean;
import top.zbeboy.isy.web.jcaptcha.CaptchaServiceSingleton;
import top.zbeboy.isy.web.util.AjaxUtils;
import top.zbeboy.isy.web.util.DataTablesUtils;
import top.zbeboy.isy.web.util.ImageUtils;
import top.zbeboy.isy.web.util.SmallPropsUtils;
import top.zbeboy.isy.web.vo.platform.users.AvatarVo;
import top.zbeboy.isy.web.vo.platform.users.UsersVo;
import top.zbeboy.isy.web.vo.register.student.StudentVo;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by lenovo on 2016-08-22.
 */
@Slf4j
@Controller
public class UsersController {

    /*
    检验邮箱使用
     */
    private static final int VALID_EMAIL = 1;

    /*
    检验手机号使用
     */
    private static final int VALID_MOBILE = 2;

    /*
    验证码错误码
     */
    private static final int CAPTCHA_ERROR = 0;

    /*
    无效的验证码
     */
    private static final int CAPTCHA_INVALID = 1;

    @Resource
    private UsersService usersService;

    @Resource
    private UsersTypeService usersTypeService;

    @Resource
    private CacheManageService cacheManageService;

    @Resource
    private AuthoritiesService authoritiesService;

    @Resource
    private StudentService studentService;

    @Resource
    private StaffService staffService;

    @Resource
    private CollegeRoleService collegeRoleService;

    @Resource
    private RoleService roleService;

    @Resource
    private MailService mailService;

    @Resource
    private MobileService mobileService;

    @Resource
    private UploadService uploadService;

    @Autowired
    private ISYProperties isyProperties;

    @Autowired
    private RequestUtils requestUtils;

    @Resource
    private CommonControllerMethodService commonControllerMethodService;

    @Resource
    private UsersElasticRepository usersElasticRepository;

    @Resource
    private StudentElasticRepository studentElasticRepository;

    @Resource
    private StaffElasticRepository staffElasticRepository;

    @Resource
    private UsersGlue usersGlue;

    /**
     * 检验注册表单
     *
     * @param username  账号(邮箱)
     * @param mobile    手机号
     * @param validType 检验类型：1.邮箱；2.手机号
     * @return true 检验通过 false 不通过
     */
    @RequestMapping(value = "/user/register/valid/users", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils validUsers(String username, String mobile, int validType) {
        if (validType == VALID_EMAIL) {
            Users tempUsers = usersService.findByUsername(StringUtils.trimWhitespace(username));
            if (!ObjectUtils.isEmpty(tempUsers)) {
                return AjaxUtils.of().fail().msg("该邮箱已被注册");
            } else {
                return AjaxUtils.of().success();
            }
        }

        if (validType == VALID_MOBILE) {
            List<Users> tempUsers = usersService.findByMobile(StringUtils.trimWhitespace(mobile));
            if (!ObjectUtils.isEmpty(tempUsers)) {
                return AjaxUtils.of().fail().msg("该手机号已被注册");
            } else {
                return AjaxUtils.of().success();
            }
        }

        return AjaxUtils.of().fail().msg("检验类型异常");
    }

    /**
     * 检验注册表单
     *
     * @param username  账号(邮箱)
     * @param mobile    手机号
     * @param validType 检验类型：2.手机号
     * @return true 检验通过 false 不通过
     */
    @RequestMapping(value = "/anyone/valid/users", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils validLoginUsers(@RequestParam("username") String username, String mobile, int validType) {
        if (validType == VALID_MOBILE) {
            Result<UsersRecord> tempUsers = usersService.findByMobileNeUsername(StringUtils.trimWhitespace(mobile), StringUtils.trimWhitespace(username));
            if (!ObjectUtils.isEmpty(tempUsers)) {
                return AjaxUtils.of().fail().msg("该手机号已被注册");
            } else {
                return AjaxUtils.of().success();
            }
        }

        return AjaxUtils.of().fail().msg("检验类型异常");
    }

    /**
     * 检验当前用户类型是否为学生
     *
     * @return true or false
     */
    @RequestMapping(value = "/anyone/valid/cur/is/student", method = RequestMethod.GET)
    @ResponseBody
    public AjaxUtils validIsStudent() {
        if (usersTypeService.isCurrentUsersTypeName(Workbook.STUDENT_USERS_TYPE)) {
            Users users = usersService.getUserFromSession();
            Student student = studentService.findByUsername(users.getUsername());
            return AjaxUtils.of().success().msg("学生用户").obj(student.getStudentId());
        }
        return AjaxUtils.of().fail().msg("非学生用户");
    }

    /**
     * 检验当前用户类型是否为教职工
     *
     * @return true or false
     */
    @RequestMapping(value = "/anyone/valid/cur/is/staff", method = RequestMethod.GET)
    @ResponseBody
    public AjaxUtils validIsStaff() {
        if (usersTypeService.isCurrentUsersTypeName(Workbook.STAFF_USERS_TYPE)) {
            Users users = usersService.getUserFromSession();
            Staff staff = staffService.findByUsername(users.getUsername());
            return AjaxUtils.of().success().msg("教职工用户").obj(staff.getStaffId());
        }
        return AjaxUtils.of().fail().msg("非教职工用户");
    }

    /**
     * 检验手机验证码
     *
     * @param mobile          手机号
     * @param phoneVerifyCode 验证码
     * @param session         session
     * @return true 通过 false 不通过
     */
    @RequestMapping(value = "/user/register/valid/mobile", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils validMobile(@RequestParam("mobile") String mobile, @RequestParam("phoneVerifyCode") String phoneVerifyCode, HttpSession session) {
        if (!ObjectUtils.isEmpty(session.getAttribute("mobile"))) {
            String tempMobile = (String) session.getAttribute("mobile");
            if (!mobile.equals(tempMobile)) {
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
                            if (!phoneVerifyCode.equals(mobileCode)) {
                                return AjaxUtils.of().fail().msg("验证码错误");
                            } else {
                                return AjaxUtils.of().success().msg("");
                            }
                        }
                    }
                }
            }
        }
        return AjaxUtils.of().fail().msg("请输入手机号，并点击获取验证码按钮");
    }

    /**
     * 邮箱邮件验证
     *
     * @param key      验证码
     * @param username 账号
     * @param modelMap 页面对象
     * @return 消息页面
     */
    @RequestMapping("/user/register/mailbox/valid")
    public String validEmail(@RequestParam("key") String key, @RequestParam("username") String username, ModelMap modelMap) {
        Users users = usersService.findByUsername(username);
        if (!ObjectUtils.isEmpty(users)) {
            Timestamp mailboxVerifyValid = users.getMailboxVerifyValid();
            Timestamp now = new Timestamp(Clock.systemDefaultZone().millis());
            if (now.before(mailboxVerifyValid)) {
                if (key.equals(users.getMailboxVerifyCode())) {
                    Byte verifyMailbox = 1;
                    users.setVerifyMailbox(verifyMailbox);
                    usersService.update(users);
                    modelMap.put("msg", "恭喜您注册成功，请等待管理员审核后便可登录，审核结果会很快发至您的邮箱，注意查收。");
                } else {
                    modelMap.put("msg", "您的邮箱验证码有误，验证邮箱失败！");
                }
            } else {
                modelMap.put("msg", "您的邮箱验证已过有效期！");
            }
        } else {
            modelMap.put("msg", "该邮箱未注册！");
        }
        return "msg";
    }

    /**
     * 重新发送验证邮件
     *
     * @param username 用户账号
     * @param request  请求
     * @param modelMap 页面对象
     * @return 消息
     */
    @RequestMapping("/user/register/mailbox/anew")
    public String anewSendVerifyMail(@RequestParam("username") String username, HttpServletRequest request, ModelMap modelMap) {
        Users users = usersService.findByUsername(username);
        if (!ObjectUtils.isEmpty(users)) {
            if (users.getVerifyMailbox() <= 0) {
                DateTime dateTime = DateTime.now();
                dateTime = dateTime.plusDays(Workbook.MAILBOX_VERIFY_VALID);
                String mailboxVerifyCode = RandomUtils.generateEmailCheckKey();
                users.setMailboxVerifyCode(mailboxVerifyCode);
                users.setMailboxVerifyValid(new Timestamp(dateTime.toDate().getTime()));
                usersService.update(users);
                //发送验证邮件
                if (isyProperties.getMail().isOpen()) {
                    mailService.sendValidEmailMail(users, requestUtils.getBaseUrl(request));
                    modelMap.put("msg", "验证邮件已发送至您邮箱，请登录邮箱进行验证！");
                } else {
                    modelMap.put("msg", "邮件推送已被管理员关闭，暂时无法验证");
                }
            } else {
                modelMap.put("msg", "该邮箱已经验证，请勿重复验证");
            }
        } else {
            modelMap.put("msg", "未查询到用户信息");
        }
        return "msg";
    }

    /**
     * 获取手机验证码
     *
     * @param mobile  手机号
     * @param session session
     * @return 发送验证码到手机
     */
    @RequestMapping(value = "/user/register/mobile/code", method = RequestMethod.GET)
    @ResponseBody
    public AjaxUtils mobileCode(@RequestParam("mobile") String mobile, HttpSession session) {
        String regex = "1[0-9]{10}";
        if (mobile.matches(regex)) {
            DateTime dateTime = DateTime.now();
            dateTime = dateTime.plusMinutes(Workbook.MOBILE_VERIFY_VALID);
            String mobileKey = RandomUtils.generateMobileKey();
            session.setAttribute("mobile", mobile);
            session.setAttribute("mobileExpiry", dateTime.toDate());
            session.setAttribute("mobileCode", mobileKey);
            mobileService.sendValidMobileShortMessage(mobile, mobileKey);
            if (isyProperties.getMobile().isOpen()) {
                return AjaxUtils.of().success().msg("短信已发送，请您耐心等待(验证码30分钟内有效，无需重复发送)");
            } else {
                return AjaxUtils.of().fail().msg("短信发送已被管理员关闭");
            }
        } else {
            return AjaxUtils.of().fail().msg("手机号格式不正确");
        }
    }

    /**
     * 获取验证码
     *
     * @param request  请求
     * @param response 响应
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/user/login/jcaptcha")
    public void jCaptcha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        byte[] captchaChallengeAsJpeg;
        // the output stream to render the captcha image as jpeg into
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // get the session id that will identify the generated captcha.
            // the same id must be used to validate the response, the session id is a good candidate!
            String captchaId = request.getSession().getId();
            // call the ImageCaptchaService getChallenge method
            BufferedImage challenge = CaptchaServiceSingleton.getInstance().getImageChallengeForID(captchaId, request.getLocale());
            // a jpeg encoder
            ImageIO.write(challenge, "jpeg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            log.error(" jcaptcha exception : {} ", e);
            return;
        } catch (CaptchaServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error(" jcaptcha exception : {} ", e);
            return;
        }

        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

        // flush it in the response
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();

        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    /**
     * js 检验验证码
     *
     * @param captcha 验证码
     * @param request 请求
     * @return true or false
     */
    @RequestMapping("/user/login/valid/jcaptcha")
    @ResponseBody
    public AjaxUtils validateCaptchaForId(@RequestParam("j_captcha_response") String captcha, HttpServletRequest request) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        Boolean isResponseCorrect = Boolean.FALSE;
        // remember that we need an id to validate!
        String captchaId = request.getSession().getId();
        log.debug(" valid captchaId : {}", captchaId);
        log.debug(" j_captcha_response : {} ", captcha);
        // call the service method
        try {
            isResponseCorrect = CaptchaServiceSingleton.getInstance().validateResponseForID(captchaId, captcha);
            if (isResponseCorrect) {
                ajaxUtils.success().msg("验证码正确");
            } else {
                ajaxUtils.fail().msg("验证码错误").obj(CAPTCHA_ERROR);
            }
        } catch (CaptchaServiceException e) {
            // should not happen,may be thrown if the id is not valid
            ajaxUtils.fail().msg("参数无效,请重新输入验证码").obj(CAPTCHA_INVALID);
            log.error(" valid exception : {} ", e);
        }
        return ajaxUtils;
    }

    /**
     * 忘记密码邮箱验证
     *
     * @param email 邮箱
     * @return true or false
     */
    @RequestMapping(value = "/user/login/valid/email", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils validEmail(@RequestParam("email") String email) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        Users users = usersService.findByUsername(email);
        if (!ObjectUtils.isEmpty(users)) {
            if (users.getVerifyMailbox() == 1) {
                ajaxUtils.success().msg("邮箱正常");
            } else {
                ajaxUtils.fail().msg("该邮箱未激活");
            }
        } else {
            ajaxUtils.fail().msg("该邮箱不存在");
        }
        return ajaxUtils;
    }

    /**
     * 忘记密码邮件
     *
     * @param email   账号
     * @param request 请求
     * @return 是否发送成功
     */
    @RequestMapping("/user/login/password/forget/email")
    @ResponseBody
    public AjaxUtils loginPasswordForgetEmail(@RequestParam("username") String email, HttpServletRequest request) {
        String username = StringUtils.trimWhitespace(email);
        String msg;
        if (StringUtils.hasLength(username)) {
            Users users = usersService.findByUsername(username);
            if (!ObjectUtils.isEmpty(users)) {
                DateTime dateTime = DateTime.now();
                dateTime = dateTime.plusDays(Workbook.MAILBOX_FORGET_PASSWORD_VALID);
                String passwordResetKey = RandomUtils.generateResetKey();
                users.setPasswordResetKey(passwordResetKey);
                users.setPasswordResetKeyValid(new Timestamp(dateTime.toDate().getTime()));
                usersService.update(users);
                if (isyProperties.getMail().isOpen()) {
                    mailService.sendPasswordResetMail(users, requestUtils.getBaseUrl(request));
                    return AjaxUtils.of().success().msg("密码重置邮件已发送至您的邮箱");
                } else {
                    msg = "邮件推送已被管理员关闭";
                }
            } else {
                msg = "获取账号信息失败";
            }
        } else {
            msg = "获取参数有误";
        }
        return AjaxUtils.of().fail().msg(msg);
    }

    /**
     * 密码重置页面
     *
     * @param key      验证码
     * @param email    邮箱账号
     * @param modelMap 页面对象
     * @return 密码重置页面
     */
    @RequestMapping("/user/login/password/forget/reset")
    public String loginPasswordForgetReset(@RequestParam("key") String key, @RequestParam("username") String email, ModelMap modelMap) {
        String resetKey = StringUtils.trimWhitespace(key);
        String username = StringUtils.trimWhitespace(email);
        String msg;
        if (StringUtils.hasLength(resetKey) && StringUtils.hasLength(username)) {
            Users users = usersService.findByUsername(username);
            if (!ObjectUtils.isEmpty(users)) {
                Timestamp passwordResetKeyValid = users.getPasswordResetKeyValid();
                Timestamp now = new Timestamp(Clock.systemDefaultZone().millis());
                if (now.before(passwordResetKeyValid)) {
                    if (resetKey.equals(users.getPasswordResetKey())) {
                        modelMap.addAttribute("username", email);
                        return "reset_password";
                    } else {
                        msg = "验证码错误";
                    }
                } else {
                    msg = "验证码有效期已过";
                }
            } else {
                msg = "该账号不存在";
            }
        } else {
            msg = "参数异常";
        }
        modelMap.addAttribute("msg", msg);
        return "msg";
    }

    /**
     * 重置密码
     *
     * @param studentVo     页面对象
     * @param bindingResult 检验
     * @return true重置成功 false重置失败
     */
    @RequestMapping("/user/login/password/reset")
    @ResponseBody
    public AjaxUtils loginPasswordReset(StudentVo studentVo, BindingResult bindingResult) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        if (!bindingResult.hasErrors()) {
            String username = StringUtils.trimWhitespace(studentVo.getEmail());
            String password = StringUtils.trimWhitespace(studentVo.getPassword());
            String confirmPassword = StringUtils.trimWhitespace(studentVo.getConfirmPassword());
            if (password.equals(confirmPassword)) {
                Users users = usersService.findByUsername(username);
                if (!ObjectUtils.isEmpty(users)) {
                    users.setPassword(BCryptUtils.bCryptPassword(confirmPassword));
                    usersService.update(users);
                    ajaxUtils.success().msg("密码更新成功");
                } else {
                    ajaxUtils.fail().msg("该账号不存在");
                }
            } else {
                ajaxUtils.fail().msg("密码不一致");
            }
        } else {
            ajaxUtils.fail().msg("参数异常");
        }
        return ajaxUtils;
    }

    /**
     * 获取用户类型数据
     *
     * @return 数据
     */
    @RequestMapping(value = "/web/platform/users/type/data", method = RequestMethod.GET)
    @ResponseBody
    public AjaxUtils<UsersType> usersTypeData() {
        AjaxUtils<UsersType> ajaxUtils = AjaxUtils.of();
        List<UsersType> usersTypes = new ArrayList<>();
        usersTypes.add(new UsersType(0, "注册类型"));
        Result<UsersTypeRecord> usersTypeRecords = usersTypeService.findAll();
        if (usersTypeRecords.isNotEmpty()) {
            usersTypes.addAll(usersTypeRecords.into(UsersType.class));
        }
        return ajaxUtils.success().listData(usersTypes);
    }

    /**
     * 用户角色数据
     *
     * @param username 用户账号
     * @return 数据
     */
    @RequestMapping(value = "/special/channel/users/role/data", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils<Role> roleData(@RequestParam("username") String username) {
        AjaxUtils<Role> ajaxUtils = AjaxUtils.of();
        List<Role> roles = new ArrayList<>();
        if (roleService.isCurrentUserInRole(Workbook.SYSTEM_AUTHORITIES)) {
            roles.add(roleService.findByRoleEnName(Workbook.ADMIN_AUTHORITIES));
            roles.add(roleService.findByRoleEnName(Workbook.OPS_AUTHORITIES));
        }
        // 根据此用户账号查询院下所有角色
        Users users = usersService.findByUsername(username);
        Optional<Record> record = usersService.findUserSchoolInfo(users);
        if (record.isPresent()) {
            College college = record.get().into(College.class);
            List<CollegeRoleRecord> collegeRoleRecords = collegeRoleService.findByCollegeId(college.getCollegeId());
            if (!ObjectUtils.isEmpty(collegeRoleRecords) && !collegeRoleRecords.isEmpty()) {
                List<String> roleIds = new ArrayList<>();
                collegeRoleRecords.forEach(role -> roleIds.add(role.getRoleId()));
                Result<RoleRecord> roleRecords = roleService.findInRoleId(roleIds);
                roles.addAll(roleRecords.into(Role.class));
            }
        }
        return ajaxUtils.success().listData(roles);
    }

    /**
     * 保存用户角色
     *
     * @param username 用户账号
     * @param roles    角色
     * @param request  请求
     * @return true 成功 false 角色为空
     */
    @RequestMapping(value = "/special/channel/users/role/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils roleSave(@RequestParam("username") String username, @RequestParam("roles") String roles, HttpServletRequest request) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        if (StringUtils.hasLength(roles)) {
            Users users = usersService.findByUsername(username);
            if (!ObjectUtils.isEmpty(users)) {
                if (!ObjectUtils.isEmpty(users.getVerifyMailbox()) && users.getVerifyMailbox() == 1) {
                    List<String> roleList = SmallPropsUtils.StringIdsToStringList(roles);
                    // 禁止非系统用户 提升用户权限到系统或管理员级别权限
                    if (!roleService.isCurrentUserInRole(Workbook.SYSTEM_AUTHORITIES)
                            && (roleList.contains(Workbook.ADMIN_AUTHORITIES) || roleList.contains(Workbook.SYSTEM_AUTHORITIES))) {
                        return ajaxUtils.fail().msg("禁止非系统用户角色提升用户权限到系统或管理员级别权限");
                    }
                    authoritiesService.deleteByUsername(username);
                    UsersElastic usersElastic = usersElasticRepository.findOne(username);
                    List<String> roleEnNames = new ArrayList<>();
                    StringBuilder stringBuilder = new StringBuilder();
                    roleList.forEach(role -> {
                        Authorities authorities = new Authorities(username, role);
                        authoritiesService.save(authorities);
                        Role tempRole = roleService.findByRoleEnName(role);
                        roleEnNames.add(tempRole.getRoleEnName());
                        stringBuilder.append(tempRole.getRoleName()).append(" ");
                    });
                    if (roleEnNames.contains(Workbook.SYSTEM_AUTHORITIES)) {
                        usersElastic.setAuthorities(ElasticBook.SYSTEM_AUTHORITIES);
                    } else if (roleEnNames.contains(Workbook.ADMIN_AUTHORITIES)) {
                        usersElastic.setAuthorities(ElasticBook.ADMIN_AUTHORITIES);
                    } else {
                        usersElastic.setAuthorities(ElasticBook.HAS_AUTHORITIES);
                    }
                    usersElastic.setRoleName(stringBuilder.toString().trim());
                    usersElasticRepository.delete(username);
                    usersElasticRepository.save(usersElastic);
                    UsersType usersType = cacheManageService.findByUsersTypeId(users.getUsersTypeId());
                    if (usersType.getUsersTypeName().equals(Workbook.STUDENT_USERS_TYPE)) {
                        StudentElastic studentElastic = studentElasticRepository.findByUsername(username);
                        studentElastic.setAuthorities(usersElastic.getAuthorities());
                        studentElastic.setRoleName(usersElastic.getRoleName());
                        studentElasticRepository.deleteByUsername(username);
                        studentElasticRepository.save(studentElastic);
                    } else if (usersType.getUsersTypeName().equals(Workbook.STAFF_USERS_TYPE)) {
                        StaffElastic staffElastic = staffElasticRepository.findByUsername(username);
                        staffElastic.setAuthorities(usersElastic.getAuthorities());
                        staffElastic.setRoleName(usersElastic.getRoleName());
                        staffElasticRepository.deleteByUsername(username);
                        staffElasticRepository.save(staffElastic);
                    }
                    Users curUsers = usersService.getUserFromSession();
                    String notify = "您的权限已变更为" + usersElastic.getRoleName() + " ，请登录查看。";
                    commonControllerMethodService.sendNotify(users, curUsers, "权限变更", notify, request);
                    ajaxUtils.success().msg("更改用户角色成功");
                } else {
                    ajaxUtils.fail().msg("该用户未激活账号");
                }
            } else {
                ajaxUtils.fail().msg("未查询到该用户信息");
            }
        } else {
            ajaxUtils.fail().msg("用户角色参数异常");
        }
        return ajaxUtils;
    }

    /**
     * 平台用户
     *
     * @return 平台用户页面
     */
    @RequestMapping(value = "/web/menu/platform/users", method = RequestMethod.GET)
    public String platformUsers() {
        return "web/platform/users/users_data::#page-wrapper";
    }

    /**
     * datatables ajax查询数据
     *
     * @param request 请求
     * @return datatables数据
     */
    @RequestMapping(value = "/web/platform/users/pass/data", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesUtils<UsersBean> platformPassDatas(HttpServletRequest request) {
        // 前台数据标题 注：要和前台标题顺序一致，获取order用
        List<String> headers = new ArrayList<>();
        headers.add("select");
        headers.add("real_name");
        headers.add("username");
        headers.add("mobile");
        headers.add("role_name");
        headers.add("users_type_name");
        headers.add("enabled");
        headers.add("lang_key");
        headers.add("join_date");
        headers.add("operator");
        DataTablesUtils<UsersBean> dataTablesUtils = new DataTablesUtils<>(request, headers);
        ResultUtils<List<UsersBean>> resultUtils = usersGlue.findAllByPageExistsAuthorities(dataTablesUtils);
        dataTablesUtils.setData(resultUtils.getData());
        dataTablesUtils.setiTotalRecords(usersGlue.countAllExistsAuthorities());
        dataTablesUtils.setiTotalDisplayRecords(resultUtils.getTotalElements());
        return dataTablesUtils;
    }

    /**
     * datatables ajax查询数据
     *
     * @param request 请求
     * @return datatables数据
     */
    @RequestMapping(value = "/web/platform/users/wait/data", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesUtils<UsersBean> platformWaitDatas(HttpServletRequest request) {
        // 前台数据标题 注：要和前台标题顺序一致，获取order用
        List<String> headers = new ArrayList<>();
        headers.add("select");
        headers.add("real_name");
        headers.add("username");
        headers.add("mobile");
        headers.add("users_type_name");
        headers.add("lang_key");
        headers.add("join_date");
        headers.add("operator");
        DataTablesUtils<UsersBean> dataTablesUtils = new DataTablesUtils<>(request, headers);
        ResultUtils<List<UsersBean>> resultUtils = usersGlue.findAllByPageNotExistsAuthorities(dataTablesUtils);
        dataTablesUtils.setData(resultUtils.getData());
        dataTablesUtils.setiTotalRecords(usersGlue.countAllNotExistsAuthorities());
        dataTablesUtils.setiTotalDisplayRecords(resultUtils.getTotalElements());
        return dataTablesUtils;
    }

    /**
     * 更新用户状态
     *
     * @param userIds ids
     * @param enabled 状态
     * @return true 成功 false 失败
     */
    @RequestMapping("/special/channel/users/update/enabled")
    @ResponseBody
    public AjaxUtils usersUpdateEnabled(String userIds, Byte enabled) {
        if (StringUtils.hasLength(userIds)) {
            usersService.updateEnabled(SmallPropsUtils.StringIdsToStringList(userIds), enabled);
            return AjaxUtils.of().success().msg("注销用户成功");
        }
        return AjaxUtils.of().fail().msg("注销用户失败");
    }

    /**
     * 删除无角色关联的用户
     *
     * @param userIds 用户账号
     * @return true 成功 false 失败
     */
    @RequestMapping("/special/channel/users/deletes")
    @ResponseBody
    public AjaxUtils deleteUsers(@RequestParam("username") String userIds) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        if (StringUtils.hasLength(userIds)) {
            List<String> ids = SmallPropsUtils.StringIdsToStringList(userIds);
            ids.forEach(id -> {
                List<AuthoritiesRecord> authoritiesRecords = authoritiesService.findByUsername(id);
                if (!ObjectUtils.isEmpty(authoritiesRecords) && !authoritiesRecords.isEmpty()) {
                    ajaxUtils.fail().msg("用户存在角色关联，无法删除");
                } else {
                    Users users = usersService.findByUsername(id);
                    if (!ObjectUtils.isEmpty(users)) {
                        UsersType usersType = cacheManageService.findByUsersTypeId(users.getUsersTypeId());
                        switch (usersType.getUsersTypeName()) {
                            case Workbook.STUDENT_USERS_TYPE:  // 学生
                                studentService.deleteByUsername(id);
                                usersService.deleteById(id);
                                ajaxUtils.success().msg("删除用户成功");
                                break;
                            case Workbook.STAFF_USERS_TYPE:  // 教职工
                                staffService.deleteByUsername(id);
                                usersService.deleteById(id);
                                ajaxUtils.success().msg("删除用户成功");
                                break;
                            default:
                                ajaxUtils.fail().msg("未获取到用户类型");
                                break;
                        }
                    } else {
                        ajaxUtils.fail().msg("未查询到用户");
                    }
                }
            });
        } else {
            ajaxUtils.fail().msg("用户账号为空");
        }
        return ajaxUtils;
    }

    /**
     * 用户资料页面
     *
     * @param modelMap 页面对象
     * @param request  请求
     * @return 资料页面
     */
    @RequestMapping("/anyone/users/profile")
    public String usersProfile(ModelMap modelMap, HttpServletRequest request) {
        Users users = usersService.getUserFromSession();
        UsersType usersType = cacheManageService.findByUsersTypeId(users.getUsersTypeId());
        String page;
        switch (usersType.getUsersTypeName()) {
            case Workbook.STUDENT_USERS_TYPE:  // 学生
                page = "web/platform/users/users_profile_student::#page-wrapper";
                profileStudent(users, modelMap, request);
                break;
            case Workbook.STAFF_USERS_TYPE:  // 教职工
                page = "web/platform/users/users_profile_staff::#page-wrapper";
                profileStaff(users, modelMap, request);
                break;
            case Workbook.SYSTEM_USERS_TYPE:  // 系统
                page = "web/platform/users/users_profile_system::#page-wrapper";
                profileSystem(users, modelMap, request);
                break;
            default:
                page = "login";
                break;
        }
        return page;
    }

    /**
     * 用户资料编辑页面
     *
     * @param modelMap 页面对象
     * @param request  请求
     * @return 资料编辑页面
     */
    @RequestMapping("/anyone/users/profile/edit")
    public String usersProfileEdit(ModelMap modelMap, HttpServletRequest request) {
        Users users = usersService.getUserFromSession();
        UsersType usersType = cacheManageService.findByUsersTypeId(users.getUsersTypeId());
        String page;
        switch (usersType.getUsersTypeName()) {
            case Workbook.STUDENT_USERS_TYPE:  // 学生
                page = "web/platform/users/users_profile_student_edit::#page-wrapper";
                profileStudent(users, modelMap, request);
                break;
            case Workbook.STAFF_USERS_TYPE:  // 教职工
                page = "web/platform/users/users_profile_staff_edit::#page-wrapper";
                profileStaff(users, modelMap, request);
                break;
            case Workbook.SYSTEM_USERS_TYPE:  // 系统
                page = "web/platform/users/users_profile_system_edit::#page-wrapper";
                profileSystem(users, modelMap, request);
                break;
            default:
                page = "login";
                break;
        }
        return page;
    }

    /**
     * 处理学生数据
     *
     * @param users    用户
     * @param modelMap 页面对象
     * @param request  请求
     */
    private void profileStudent(Users users, ModelMap modelMap, HttpServletRequest request) {
        Optional<Record> student = studentService.findByUsernameRelation(users.getUsername());
        if (student.isPresent()) {
            StudentBean studentBean = student.get().into(StudentBean.class);
            modelMap.addAttribute("avatarForSaveOrUpdate", studentBean.getAvatar());
            String showAvatar = getAvatar(studentBean.getAvatar(), request);
            studentBean.setAvatar(showAvatar);
            String dormitoryNumber = studentBean.getDormitoryNumber();
            if (StringUtils.hasLength(dormitoryNumber) && dormitoryNumber.contains("-")) {
                studentBean.setRidgepole(dormitoryNumber.substring(0, dormitoryNumber.lastIndexOf('-')));
                studentBean.setDorm(dormitoryNumber.substring(dormitoryNumber.lastIndexOf('-') + 1));
            }
            modelMap.addAttribute("user", studentBean);
        }
    }

    /**
     * 处理教职工数据
     *
     * @param users    用户
     * @param modelMap 页面对象
     * @param request  请求
     */
    private void profileStaff(Users users, ModelMap modelMap, HttpServletRequest request) {
        Optional<Record> staff = staffService.findByUsernameRelation(users.getUsername());
        if (staff.isPresent()) {
            StaffBean staffBean = staff.get().into(StaffBean.class);
            modelMap.addAttribute("avatarForSaveOrUpdate", staffBean.getAvatar());
            String showAvatar = getAvatar(staffBean.getAvatar(), request);
            staffBean.setAvatar(showAvatar);
            modelMap.addAttribute("user", staffBean);
        }
    }

    /**
     * 处理系统数据
     *
     * @param users    用户
     * @param modelMap 页面对象
     * @param request  请求
     */
    private void profileSystem(Users users, ModelMap modelMap, HttpServletRequest request) {
        Users newUsers = usersService.findByUsername(users.getUsername());
        modelMap.addAttribute("avatarForSaveOrUpdate", newUsers.getAvatar());
        String showAvatar = getAvatar(newUsers.getAvatar(), request);
        newUsers.setAvatar(showAvatar);
        modelMap.addAttribute("user", newUsers);
    }

    /**
     * 得到处理过的用户头像
     *
     * @param avatar  头像
     * @param request 请求
     * @return 处理过的头像
     */
    private String getAvatar(String avatar, HttpServletRequest request) {
        String showAvatar;
        if (avatar.equals(Workbook.USERS_AVATAR)) {
            showAvatar = requestUtils.getBaseUrl(request) + "/" + avatar;
        } else {
            showAvatar = requestUtils.getBaseUrl(request) + "/anyone/users/review/avatar?path=" + avatar;
        }
        return showAvatar;
    }

    /**
     * 用户配置页面
     *
     * @param modelMap 页面对象
     * @return 配置页面
     */
    @RequestMapping(value = "/anyone/users/setting", method = RequestMethod.GET)
    public String userSetting(ModelMap modelMap) {
        Users users = usersService.getUserFromSession();
        modelMap.addAttribute("user", users);
        return "web/platform/users/users_setting::#page-wrapper";
    }

    /**
     * 已登录用户身份证号更新检验
     *
     * @param username 用户账号
     * @param idCard   身份证号
     * @return true 可以用 false 不可以
     */
    @RequestMapping(value = "/anyone/users/valid/id_card", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils validIdCard(@RequestParam("username") String username, @RequestParam("idCard") String idCard) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        Users users = usersService.getUserFromSession();
        UsersType usersType = cacheManageService.findByUsersTypeId(users.getUsersTypeId());
        switch (usersType.getUsersTypeName()) {
            case Workbook.STUDENT_USERS_TYPE:  // 学生
                Result<StudentRecord> studentRecords = studentService.findByIdCardNeUsername(username, idCard);
                List<Staff> staffs = staffService.findByIdCard(idCard);
                if (ObjectUtils.isEmpty(staffs) && staffs.isEmpty() && studentRecords.isEmpty()) {
                    ajaxUtils.success();
                } else {
                    ajaxUtils.fail();
                }
                break;
            case Workbook.STAFF_USERS_TYPE:  // 教职工
                Result<StaffRecord> staffRecords = staffService.findByIdCardNeUsername(username, idCard);
                List<Student> students = studentService.findByIdCard(idCard);
                if (ObjectUtils.isEmpty(students) && students.isEmpty() && staffRecords.isEmpty()) {
                    ajaxUtils.success();
                } else {
                    ajaxUtils.fail();
                }
                break;
            default:
                ajaxUtils.fail();
                break;
        }
        return ajaxUtils;
    }

    /**
     * 用户上传头像
     *
     * @param multipartHttpServletRequest 文件请求
     * @param request                     请求
     * @return 文件信息
     */
    @RequestMapping(value = "/anyone/users/upload/avatar")
    @ResponseBody
    public AjaxUtils<FileBean> usersUploadAvatar(MultipartHttpServletRequest multipartHttpServletRequest, HttpServletRequest request) {
        AjaxUtils<FileBean> data = AjaxUtils.of();
        try {
            Users users = usersService.getUserFromSession();
            List<FileBean> fileBeen = uploadService.upload(multipartHttpServletRequest,
                    RequestUtils.getRealPath(request) + Workbook.avatarPath(users), request.getRemoteAddr());
            AvatarVo avatarVo = JSON.parseObject(request.getParameter("avatar_data"), AvatarVo.class);
            int x = avatarVo.getX();
            int y = avatarVo.getY();
            int height = avatarVo.getHeight();
            int width = avatarVo.getWidth();
            int rotate = avatarVo.getRotate();

            for (FileBean curFileInfo : fileBeen) {
                File curFile = new File(curFileInfo.getLastPath());
                File rotateFile = new File(curFile.getPath().substring(0, curFile.getPath().lastIndexOf('.')) + "_rotate." + curFileInfo.getExt());

                // 旋转头像
                ImageUtils.makeRotate(curFile, rotateFile, rotate);

                // 裁剪头像
                if (rotateFile.exists()) {
                    ImageUtils.crop(rotateFile,
                            curFile, x, y, width, height);
                    FilesUtils.deleteFile(rotateFile.getAbsolutePath());
                } else {
                    data.fail().msg("头像创建失败");
                }

                if (curFile.exists()) {
                    data.success().listData(fileBeen).obj(Workbook.avatarPath(users));

                    String tempNewName = curFileInfo.getNewName().substring(0, curFileInfo.getNewName().lastIndexOf('.')) + "." + curFileInfo.getExt();
                    String tempLastPath = curFileInfo.getLastPath().substring(0, curFileInfo.getLastPath().lastIndexOf('.')) + "." + curFileInfo.getExt();
                    curFileInfo.setNewName(tempNewName);
                    curFileInfo.setLastPath(tempLastPath);
                } else {
                    data.fail().msg("头像创建失败");
                }
            }

        } catch (Exception e) {
            log.error("Upload avatar error, error is {}", e);
            data.fail().msg("头像创建失败");
        }
        return data;
    }

    /**
     * 预览当前用户头像
     *
     * @param request 请求
     */
    @RequestMapping(value = "/anyone/users/review/avatar", method = RequestMethod.GET)
    public void reviewAvatar(@RequestParam("path") String path, HttpServletRequest request, HttpServletResponse response) {
        uploadService.reviewPic("/" + path, request, response);
    }

    /**
     * 用户更新手机号
     *
     * @param username        用户账号
     * @param newMobile       手机号
     * @param phoneVerifyCode 验证码
     * @param session         手机信息
     * @return true or false
     */
    @RequestMapping(value = "/anyone/user/mobile/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils mobileUpdate(@RequestParam("username") String username, @RequestParam("newMobile") String newMobile,
                                  @RequestParam("phoneVerifyCode") String phoneVerifyCode, HttpSession session) {
        if (!ObjectUtils.isEmpty(session.getAttribute("mobile"))) {
            String tempMobile = (String) session.getAttribute("mobile");
            if (!newMobile.equals(tempMobile)) {
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
                            if (!phoneVerifyCode.equals(mobileCode)) {
                                return AjaxUtils.of().fail().msg("验证码错误");
                            } else {
                                Users users = usersService.findByUsername(username);
                                if (!ObjectUtils.isEmpty(users)) {
                                    users.setMobile(newMobile);
                                    usersService.update(users);
                                    //清空session
                                    session.removeAttribute("mobileExpiry");
                                    session.removeAttribute("mobile");
                                    session.removeAttribute("mobileCode");
                                    return AjaxUtils.of().success().msg("更新手机号成功");
                                } else {
                                    return AjaxUtils.of().fail().msg("未查询到用户信息");
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
    }

    /**
     * 更新用户密码
     *
     * @param username    用户账号
     * @param newPassword 新密码
     * @param okPassword  确认密码
     * @return true or false
     */
    @RequestMapping(value = "/anyone/user/password/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils passwordUpdate(@RequestParam("username") String username, @RequestParam("newPassword") String newPassword,
                                    @RequestParam("okPassword") String okPassword) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        String regex = "^[a-zA-Z0-9]\\w{5,17}$";
        if (newPassword.matches(regex)) {
            if (okPassword.equals(newPassword)) {
                Users users = usersService.findByUsername(username);
                if (!ObjectUtils.isEmpty(users)) {
                    users.setPassword(BCryptUtils.bCryptPassword(newPassword));
                    usersService.update(users);
                    ajaxUtils.success().msg("更新密码成功");
                } else {
                    ajaxUtils.fail().msg("未查询到用户信息");
                }
            } else {
                ajaxUtils.fail().msg("密码不一致");
            }
        } else {
            ajaxUtils.fail().msg("密码为6位数字或大小写字母");
        }
        return ajaxUtils;
    }

    /**
     * 系统更新信息
     *
     * @param usersVo       系统
     * @param bindingResult 检验
     * @return true or false
     */
    @RequestMapping(value = "/anyone/users/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils usersUpdate(@Valid UsersVo usersVo, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            Users updateUsers = usersService.findByUsername(usersVo.getUsername());
            if (!ObjectUtils.isEmpty(updateUsers)) {
                updateUsers.setRealName(usersVo.getRealName());
                updateUsers.setAvatar(usersVo.getAvatar());
                usersService.update(updateUsers);
                return AjaxUtils.of().success().msg("更新成功");
            } else {
                return AjaxUtils.of().fail().msg("未查询到用户");
            }
        }
        return AjaxUtils.of().fail().msg("参数异常");
    }
}
