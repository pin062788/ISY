package top.zbeboy.isy.web.internship.apply;

import lombok.extern.slf4j.Slf4j;
import org.jooq.Record;
import org.jooq.Result;
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
import top.zbeboy.isy.config.Workbook;
import top.zbeboy.isy.domain.tables.pojos.*;
import top.zbeboy.isy.service.common.CommonControllerMethodService;
import top.zbeboy.isy.service.common.FilesService;
import top.zbeboy.isy.service.common.UploadService;
import top.zbeboy.isy.service.data.StaffService;
import top.zbeboy.isy.service.data.StudentService;
import top.zbeboy.isy.service.internship.*;
import top.zbeboy.isy.service.platform.RoleService;
import top.zbeboy.isy.service.platform.UsersService;
import top.zbeboy.isy.service.platform.UsersTypeService;
import top.zbeboy.isy.service.util.DateTimeUtils;
import top.zbeboy.isy.service.util.FilesUtils;
import top.zbeboy.isy.service.util.RequestUtils;
import top.zbeboy.isy.service.util.UUIDUtils;
import top.zbeboy.isy.web.bean.data.staff.StaffBean;
import top.zbeboy.isy.web.bean.data.student.StudentBean;
import top.zbeboy.isy.web.bean.error.ErrorBean;
import top.zbeboy.isy.web.bean.file.FileBean;
import top.zbeboy.isy.web.bean.internship.apply.InternshipApplyBean;
import top.zbeboy.isy.web.bean.internship.release.InternshipReleaseBean;
import top.zbeboy.isy.web.util.AjaxUtils;
import top.zbeboy.isy.web.util.PaginationUtils;
import top.zbeboy.isy.web.vo.internship.apply.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Clock;
import java.util.*;

/**
 * Created by zbeboy on 2016/11/25.
 */
@Slf4j
@Controller
public class InternshipApplyController {

    @Resource
    private InternshipReleaseService internshipReleaseService;

    @Resource
    private InternshipApplyService internshipApplyService;

    @Resource
    private StudentService studentService;

    @Resource
    private StaffService staffService;

    @Resource
    private UsersTypeService usersTypeService;

    @Resource
    private UsersService usersService;

    @Resource
    private RoleService roleService;

    @Resource
    private InternshipReleaseScienceService internshipReleaseScienceService;

    @Resource
    private InternshipTeacherDistributionService internshipTeacherDistributionService;

    @Resource
    private InternshipTypeService internshipTypeService;

    @Resource
    private InternshipCollegeService internshipCollegeService;

    @Resource
    private InternshipCompanyService internshipCompanyService;

    @Resource
    private GraduationPracticeCollegeService graduationPracticeCollegeService;

    @Resource
    private GraduationPracticeCompanyService graduationPracticeCompanyService;

    @Resource
    private GraduationPracticeUnifyService graduationPracticeUnifyService;

    @Resource
    private InternshipChangeCompanyHistoryService internshipChangeCompanyHistoryService;

    @Resource
    private InternshipChangeHistoryService internshipChangeHistoryService;

    @Resource
    private CommonControllerMethodService commonControllerMethodService;

    @Resource
    private UploadService uploadService;

    @Resource
    private FilesService filesService;

    /**
     * 实习申请
     *
     * @return 实习申请页面
     */
    @RequestMapping(value = "/web/menu/internship/apply", method = RequestMethod.GET)
    public String internshipApply() {
        return "web/internship/apply/internship_apply::#page-wrapper";
    }

    /**
     * 获取实习申请数据
     *
     * @param paginationUtils 分页工具
     * @return 数据
     */
    @RequestMapping(value = "/web/internship/apply/data", method = RequestMethod.GET)
    @ResponseBody
    public AjaxUtils<InternshipReleaseBean> applyDatas(PaginationUtils paginationUtils) {
        AjaxUtils<InternshipReleaseBean> ajaxUtils = AjaxUtils.of();
        Byte isDel = 0;
        InternshipReleaseBean internshipReleaseBean = new InternshipReleaseBean();
        internshipReleaseBean.setInternshipReleaseIsDel(isDel);
        if (!roleService.isCurrentUserInRole(Workbook.SYSTEM_AUTHORITIES)
                && !roleService.isCurrentUserInRole(Workbook.ADMIN_AUTHORITIES)) {
            Users users = usersService.getUserFromSession();
            Optional<Record> record = usersService.findUserSchoolInfo(users);
            int departmentId = roleService.getRoleDepartmentId(record);
            internshipReleaseBean.setDepartmentId(departmentId);
            if (record.isPresent() && usersTypeService.isCurrentUsersTypeName(Workbook.STUDENT_USERS_TYPE)) {
                Organize organize = record.get().into(Organize.class);
                internshipReleaseBean.setAllowGrade(organize.getGrade());
            }
        }
        if (roleService.isCurrentUserInRole(Workbook.ADMIN_AUTHORITIES)) {
            Users users = usersService.getUserFromSession();
            Optional<Record> record = usersService.findUserSchoolInfo(users);
            int collegeId = roleService.getRoleCollegeId(record);
            internshipReleaseBean.setCollegeId(collegeId);
            if (record.isPresent() && usersTypeService.isCurrentUsersTypeName(Workbook.STUDENT_USERS_TYPE)) {
                Organize organize = record.get().into(Organize.class);
                internshipReleaseBean.setAllowGrade(organize.getGrade());
            }
        }
        Result<Record> records = internshipReleaseService.findAllByPage(paginationUtils, internshipReleaseBean);
        List<InternshipReleaseBean> internshipReleaseBeens = internshipReleaseService.dealData(paginationUtils, records, internshipReleaseBean);
        return ajaxUtils.success().msg("获取数据成功").listData(internshipReleaseBeens).paginationUtils(paginationUtils);
    }

    /**
     * 获取我的实习申请数据
     *
     * @param paginationUtils 分页工具
     * @return 数据
     */
    @RequestMapping(value = "/web/internship/apply/my/data", method = RequestMethod.GET)
    @ResponseBody
    public AjaxUtils<InternshipApplyBean> myApplyDatas(PaginationUtils paginationUtils) {
        AjaxUtils<InternshipApplyBean> ajaxUtils = AjaxUtils.of();
        Byte isDel = 0;
        InternshipApplyBean internshipApplyBean = new InternshipApplyBean();
        internshipApplyBean.setInternshipReleaseIsDel(isDel);
        Users users = usersService.getUserFromSession();
        List<InternshipApplyBean> internshipApplyBeens = new ArrayList<>();
        if (usersTypeService.isCurrentUsersTypeName(Workbook.STUDENT_USERS_TYPE)) {
            Student student = studentService.findByUsername(users.getUsername());
            internshipApplyBean.setStudentId(student.getStudentId());
            Result<Record> records = internshipApplyService.findAllByPage(paginationUtils, internshipApplyBean);
            internshipApplyBeens = internshipApplyService.dealData(paginationUtils, records, internshipApplyBean);
            internshipApplyBeens.forEach(i -> {
                        Optional<Record> staffRecord = internshipTeacherDistributionService.findByInternshipReleaseIdAndStudentIdForStaff(i.getInternshipReleaseId(), student.getStudentId());
                        if (staffRecord.isPresent()) {
                            Users staff = staffRecord.get().into(Users.class);
                            i.setSchoolGuidanceTeacher(staff.getRealName());
                            i.setSchoolGuidanceTeacherTel(staff.getMobile());
                        }
                    }
            );
        }
        return ajaxUtils.success().msg("获取数据成功").listData(internshipApplyBeens).paginationUtils(paginationUtils);
    }

    /**
     * 进入申请页
     *
     * @param internshipReleaseId 实习发布id
     * @param studentId           学生id
     * @return 申请页
     */
    @RequestMapping(value = "/web/internship/apply/access", method = RequestMethod.GET)
    public String applyAccess(@RequestParam("id") String internshipReleaseId, @RequestParam("studentId") int studentId, ModelMap modelMap) {
        String page;
        ErrorBean<InternshipRelease> errorBean = accessCondition(internshipReleaseId, studentId);
        if (!errorBean.isHasError()) {
            InternshipRelease internshipRelease = errorBean.getData();
            InternshipType internshipType = internshipTypeService.findByInternshipTypeId(internshipRelease.getInternshipTypeId());
            switch (internshipType.getInternshipTypeName()) {
                case Workbook.INTERNSHIP_COLLEGE_TYPE:
                    Optional<Record> internshipCollegeRecord = internshipCollegeService.findByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
                    if (internshipCollegeRecord.isPresent()) {
                        InternshipCollege internshipCollege = internshipCollegeRecord.get().into(InternshipCollege.class);
                        modelMap.addAttribute("internshipData", internshipCollege);
                        modelMap.addAttribute("internshipApply", errorBean.getMapData().get("internshipApply"));
                        page = "web/internship/apply/internship_college_edit::#page-wrapper";
                    } else {
                        buildCommonModelMap(modelMap, errorBean);
                        modelMap.addAttribute("internshipReleaseId", internshipReleaseId);
                        page = "web/internship/apply/internship_college_add::#page-wrapper";
                    }
                    break;
                case Workbook.INTERNSHIP_COMPANY_TYPE:
                    Optional<Record> internshipCompanyRecord = internshipCompanyService.findByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
                    if (internshipCompanyRecord.isPresent()) {
                        InternshipCompany internshipCompany = internshipCompanyRecord.get().into(InternshipCompany.class);
                        modelMap.addAttribute("internshipData", internshipCompany);
                        modelMap.addAttribute("internshipApply", errorBean.getMapData().get("internshipApply"));
                        page = "web/internship/apply/internship_company_edit::#page-wrapper";
                    } else {
                        buildCommonModelMap(modelMap, errorBean);
                        modelMap.addAttribute("internshipReleaseId", internshipReleaseId);
                        page = "web/internship/apply/internship_company_add::#page-wrapper";
                    }
                    break;
                case Workbook.GRADUATION_PRACTICE_COLLEGE_TYPE:
                    Optional<Record> graduationPracticeCollegeRecord = graduationPracticeCollegeService.findByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
                    if (graduationPracticeCollegeRecord.isPresent()) {
                        GraduationPracticeCollege graduationPracticeCollege = graduationPracticeCollegeRecord.get().into(GraduationPracticeCollege.class);
                        modelMap.addAttribute("internshipData", graduationPracticeCollege);
                        modelMap.addAttribute("internshipApply", errorBean.getMapData().get("internshipApply"));
                        page = "web/internship/apply/graduation_practice_college_edit::#page-wrapper";
                    } else {
                        buildCommonModelMap(modelMap, errorBean);
                        modelMap.addAttribute("internshipReleaseId", internshipReleaseId);
                        page = "web/internship/apply/graduation_practice_college_add::#page-wrapper";
                    }
                    break;
                case Workbook.GRADUATION_PRACTICE_UNIFY_TYPE:
                    Optional<Record> graduationPracticeUnifyRecord = graduationPracticeUnifyService.findByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
                    if (graduationPracticeUnifyRecord.isPresent()) {
                        GraduationPracticeUnify graduationPracticeUnify = graduationPracticeUnifyRecord.get().into(GraduationPracticeUnify.class);
                        modelMap.addAttribute("internshipData", graduationPracticeUnify);
                        modelMap.addAttribute("internshipApply", errorBean.getMapData().get("internshipApply"));
                        page = "web/internship/apply/graduation_practice_unify_edit::#page-wrapper";
                    } else {
                        buildCommonModelMap(modelMap, errorBean);
                        modelMap.addAttribute("internshipReleaseId", internshipReleaseId);
                        page = "web/internship/apply/graduation_practice_unify_add::#page-wrapper";
                    }
                    break;
                case Workbook.GRADUATION_PRACTICE_COMPANY_TYPE:
                    Optional<Record> graduationPracticeCompanyRecord = graduationPracticeCompanyService.findByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
                    if (graduationPracticeCompanyRecord.isPresent()) {
                        GraduationPracticeCompany graduationPracticeCompany = graduationPracticeCompanyRecord.get().into(GraduationPracticeCompany.class);
                        modelMap.addAttribute("internshipData", graduationPracticeCompany);
                        modelMap.addAttribute("internshipApply", errorBean.getMapData().get("internshipApply"));
                        page = "web/internship/apply/graduation_practice_company_edit::#page-wrapper";
                    } else {
                        buildCommonModelMap(modelMap, errorBean);
                        modelMap.addAttribute("internshipReleaseId", internshipReleaseId);
                        page = "web/internship/apply/graduation_practice_company_add::#page-wrapper";
                    }
                    break;
                default:
                    page = commonControllerMethodService.showTip(modelMap, "未找到相关实习类型页面");
            }
        } else {
            page = commonControllerMethodService.showTip(modelMap, "您不符合进入条件");
        }
        return page;
    }

    /**
     * 查看详情页
     *
     * @param internshipReleaseId 实习发布id
     * @param modelMap            页面对象
     * @return 页面
     */
    @RequestMapping(value = "/web/internship/apply/audit/detail", method = RequestMethod.GET)
    public String auditDetail(@RequestParam("id") String internshipReleaseId, ModelMap modelMap) {
        String page;

        Users users = usersService.getUserFromSession();
        Student student = studentService.findByUsername(users.getUsername());
        if (Objects.isNull(student)) {
            page = commonControllerMethodService.showTip(modelMap, "查询学生信息为空");
            return page;
        }
        int studentId = student.getStudentId();
        InternshipRelease internshipRelease = internshipReleaseService.findById(internshipReleaseId);
        InternshipType internshipType = internshipTypeService.findByInternshipTypeId(internshipRelease.getInternshipTypeId());
        switch (internshipType.getInternshipTypeName()) {
            case Workbook.INTERNSHIP_COLLEGE_TYPE:
                Optional<Record> internshipCollegeRecord = internshipCollegeService.findByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
                if (internshipCollegeRecord.isPresent()) {
                    InternshipCollege internshipCollege = internshipCollegeRecord.get().into(InternshipCollege.class);
                    modelMap.addAttribute("internshipData", internshipCollege);
                    page = "web/internship/apply/internship_college_detail::#page-wrapper";
                } else {
                    page = commonControllerMethodService.showTip(modelMap, "未查询到相关实习信息");
                }
                break;
            case Workbook.INTERNSHIP_COMPANY_TYPE:
                Optional<Record> internshipCompanyRecord = internshipCompanyService.findByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
                if (internshipCompanyRecord.isPresent()) {
                    InternshipCompany internshipCompany = internshipCompanyRecord.get().into(InternshipCompany.class);
                    modelMap.addAttribute("internshipData", internshipCompany);
                    page = "web/internship/apply/internship_company_detail::#page-wrapper";
                } else {
                    page = commonControllerMethodService.showTip(modelMap, "未查询到相关实习信息");
                }
                break;
            case Workbook.GRADUATION_PRACTICE_COLLEGE_TYPE:
                Optional<Record> graduationPracticeCollegeRecord = graduationPracticeCollegeService.findByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
                if (graduationPracticeCollegeRecord.isPresent()) {
                    GraduationPracticeCollege graduationPracticeCollege = graduationPracticeCollegeRecord.get().into(GraduationPracticeCollege.class);
                    modelMap.addAttribute("internshipData", graduationPracticeCollege);
                    page = "web/internship/apply/graduation_practice_college_detail::#page-wrapper";
                } else {
                    page = commonControllerMethodService.showTip(modelMap, "未查询到相关实习信息");
                }
                break;
            case Workbook.GRADUATION_PRACTICE_UNIFY_TYPE:
                Optional<Record> graduationPracticeUnifyRecord = graduationPracticeUnifyService.findByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
                if (graduationPracticeUnifyRecord.isPresent()) {
                    GraduationPracticeUnify graduationPracticeUnify = graduationPracticeUnifyRecord.get().into(GraduationPracticeUnify.class);
                    modelMap.addAttribute("internshipData", graduationPracticeUnify);
                    page = "web/internship/apply/graduation_practice_unify_detail::#page-wrapper";
                } else {
                    page = commonControllerMethodService.showTip(modelMap, "未查询到相关实习信息");
                }
                break;
            case Workbook.GRADUATION_PRACTICE_COMPANY_TYPE:
                Optional<Record> graduationPracticeCompanyRecord = graduationPracticeCompanyService.findByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
                if (graduationPracticeCompanyRecord.isPresent()) {
                    GraduationPracticeCompany graduationPracticeCompany = graduationPracticeCompanyRecord.get().into(GraduationPracticeCompany.class);
                    modelMap.addAttribute("internshipData", graduationPracticeCompany);
                    page = "web/internship/apply/graduation_practice_company_detail::#page-wrapper";
                } else {
                    page = commonControllerMethodService.showTip(modelMap, "未查询到相关实习信息");
                }
                break;
            default:
                page = commonControllerMethodService.showTip(modelMap, "未找到相关实习类型页面");
        }
        return page;
    }

    /**
     * 保存顶岗实习(留学院)
     *
     * @param internshipCollegeVo 顶岗实习(留学院)
     * @param bindingResult       检验
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/apply/college/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils applyCollegeSave(@Valid InternshipCollegeVo internshipCollegeVo, BindingResult bindingResult) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        if (!bindingResult.hasErrors()) {
            ErrorBean<InternshipRelease> errorBean = accessCondition(internshipCollegeVo.getInternshipReleaseId(), internshipCollegeVo.getStudentId());
            if (!errorBean.isHasError()) {
                internshipCollegeService.saveWithTransaction(internshipCollegeVo);
                ajaxUtils.success().msg("保存成功");
            } else {
                ajaxUtils.fail().msg("不符合实习条件，无法进行数据操作");
            }
        } else {
            ajaxUtils.fail().msg("参数异常，保存失败");
        }
        return ajaxUtils;
    }

    /**
     * 更新顶岗实习(留学院)
     *
     * @param internshipCollegeVo 顶岗实习(留学院)
     * @param bindingResult       检验
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/apply/college/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils applyCollegeUpdate(@Valid InternshipCollegeVo internshipCollegeVo, BindingResult bindingResult) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        try {
            if (!bindingResult.hasErrors() && !ObjectUtils.isEmpty(internshipCollegeVo.getInternshipCollegeId())) {
                ErrorBean<InternshipRelease> errorBean = accessCondition(internshipCollegeVo.getInternshipReleaseId(), internshipCollegeVo.getStudentId());
                if (!errorBean.isHasError()) {
                    String[] headmasterArr = internshipCollegeVo.getHeadmaster().split(" ");
                    if (headmasterArr.length >= 2) {
                        internshipCollegeVo.setHeadmaster(headmasterArr[0]);
                        internshipCollegeVo.setHeadmasterContact(headmasterArr[1]);
                    }
                    String[] schoolGuidanceTeacherArr = internshipCollegeVo.getSchoolGuidanceTeacher().split(" ");
                    if (schoolGuidanceTeacherArr.length >= 2) {
                        internshipCollegeVo.setSchoolGuidanceTeacher(schoolGuidanceTeacherArr[0]);
                        internshipCollegeVo.setSchoolGuidanceTeacherTel(schoolGuidanceTeacherArr[1]);
                    }
                    InternshipCollege internshipCollege = internshipCollegeService.findById(internshipCollegeVo.getInternshipCollegeId());
                    InternshipApply internshipApply = (InternshipApply) errorBean.getMapData().get("internshipApply");
                    if (internshipApply.getInternshipApplyState() == 5) {// 5：基本信息变更填写中
                        internshipCollege.setStudentName(internshipCollegeVo.getStudentName());
                        internshipCollege.setCollegeClass(internshipCollegeVo.getCollegeClass());
                        internshipCollege.setStudentSex(internshipCollegeVo.getStudentSex());
                        internshipCollege.setStudentNumber(internshipCollegeVo.getStudentNumber());
                        internshipCollege.setPhoneNumber(internshipCollegeVo.getPhoneNumber());
                        internshipCollege.setQqMailbox(internshipCollegeVo.getQqMailbox());
                        internshipCollege.setParentalContact(internshipCollegeVo.getParentalContact());
                        internshipCollege.setHeadmaster(internshipCollegeVo.getHeadmaster());
                        internshipCollege.setHeadmasterContact(internshipCollegeVo.getHeadmasterContact());
                        internshipCollege.setSchoolGuidanceTeacher(internshipCollegeVo.getSchoolGuidanceTeacher());
                        internshipCollege.setSchoolGuidanceTeacherTel(internshipCollegeVo.getSchoolGuidanceTeacherTel());
                        internshipCollege.setStartTime(DateTimeUtils.formatDate(internshipCollegeVo.getStartTime()));
                        internshipCollege.setEndTime(DateTimeUtils.formatDate(internshipCollegeVo.getEndTime()));
                        internshipCollegeService.update(internshipCollege);
                        ajaxUtils.success().msg("更新成功");
                    } else if (internshipApply.getInternshipApplyState() == 7) {// 7：单位信息变更填写中

                        Result<Record> internshipChangeCompanyHistoryRecord =
                                internshipChangeCompanyHistoryService.findByInternshipReleaseIdAndStudentId(internshipCollegeVo.getInternshipReleaseId(), internshipCollegeVo.getStudentId());
                        if (internshipChangeCompanyHistoryRecord.isEmpty()) {
                            InternshipChangeCompanyHistory internshipChangeCompanyHistory = new InternshipChangeCompanyHistory();
                            internshipChangeCompanyHistory.setInternshipChangeCompanyHistoryId(UUIDUtils.getUUID());
                            internshipChangeCompanyHistory.setInternshipReleaseId(internshipCollege.getInternshipReleaseId());
                            internshipChangeCompanyHistory.setStudentId(internshipCollege.getStudentId());
                            internshipChangeCompanyHistory.setChangeTime(new Timestamp(Clock.systemDefaultZone().millis()));
                            internshipChangeCompanyHistory.setCompanyName(internshipCollege.getInternshipCollegeName());
                            internshipChangeCompanyHistory.setCompanyAddress(internshipCollege.getInternshipCollegeAddress());
                            internshipChangeCompanyHistory.setCompanyContacts(internshipCollege.getInternshipCollegeContacts());
                            internshipChangeCompanyHistory.setCompanyTel(internshipCollege.getInternshipCollegeTel());
                            internshipChangeCompanyHistoryService.save(internshipChangeCompanyHistory);
                        }

                        internshipCollege.setInternshipCollegeName(internshipCollegeVo.getInternshipCollegeName());
                        internshipCollege.setInternshipCollegeAddress(internshipCollegeVo.getInternshipCollegeAddress());
                        internshipCollege.setInternshipCollegeContacts(internshipCollegeVo.getInternshipCollegeContacts());
                        internshipCollege.setInternshipCollegeTel(internshipCollegeVo.getInternshipCollegeTel());
                        internshipCollegeService.update(internshipCollege);
                        ajaxUtils.success().msg("更新成功");

                        InternshipChangeCompanyHistory internshipChangeCompanyHistory = new InternshipChangeCompanyHistory();
                        internshipChangeCompanyHistory.setInternshipChangeCompanyHistoryId(UUIDUtils.getUUID());
                        internshipChangeCompanyHistory.setInternshipReleaseId(internshipCollegeVo.getInternshipReleaseId());
                        internshipChangeCompanyHistory.setStudentId(internshipCollegeVo.getStudentId());
                        internshipChangeCompanyHistory.setChangeTime(new Timestamp(Clock.systemDefaultZone().millis()));
                        internshipChangeCompanyHistory.setCompanyName(internshipCollegeVo.getInternshipCollegeName());
                        internshipChangeCompanyHistory.setCompanyAddress(internshipCollegeVo.getInternshipCollegeAddress());
                        internshipChangeCompanyHistory.setCompanyContacts(internshipCollegeVo.getInternshipCollegeContacts());
                        internshipChangeCompanyHistory.setCompanyTel(internshipCollegeVo.getInternshipCollegeTel());
                        internshipChangeCompanyHistoryService.save(internshipChangeCompanyHistory);
                    } else {
                        if (internshipApply.getInternshipApplyState() == 3) { // 未通过
                            internshipApply.setInternshipApplyState(1); // 更新为申请中
                            internshipApplyService.update(internshipApply);
                        }
                        internshipCollege.setStudentName(internshipCollegeVo.getStudentName());
                        internshipCollege.setCollegeClass(internshipCollegeVo.getCollegeClass());
                        internshipCollege.setStudentSex(internshipCollegeVo.getStudentSex());
                        internshipCollege.setStudentNumber(internshipCollegeVo.getStudentNumber());
                        internshipCollege.setPhoneNumber(internshipCollegeVo.getPhoneNumber());
                        internshipCollege.setQqMailbox(internshipCollegeVo.getQqMailbox());
                        internshipCollege.setParentalContact(internshipCollegeVo.getParentalContact());
                        internshipCollege.setHeadmaster(internshipCollegeVo.getHeadmaster());
                        internshipCollege.setHeadmasterContact(internshipCollegeVo.getHeadmasterContact());
                        internshipCollege.setInternshipCollegeName(internshipCollegeVo.getInternshipCollegeName());
                        internshipCollege.setInternshipCollegeAddress(internshipCollegeVo.getInternshipCollegeAddress());
                        internshipCollege.setInternshipCollegeContacts(internshipCollegeVo.getInternshipCollegeContacts());
                        internshipCollege.setInternshipCollegeTel(internshipCollegeVo.getInternshipCollegeTel());
                        internshipCollege.setSchoolGuidanceTeacher(internshipCollegeVo.getSchoolGuidanceTeacher());
                        internshipCollege.setSchoolGuidanceTeacherTel(internshipCollegeVo.getSchoolGuidanceTeacherTel());
                        internshipCollege.setStartTime(DateTimeUtils.formatDate(internshipCollegeVo.getStartTime()));
                        internshipCollege.setEndTime(DateTimeUtils.formatDate(internshipCollegeVo.getEndTime()));
                        internshipCollegeService.update(internshipCollege);
                        ajaxUtils.success().msg("更新成功");
                    }
                } else {
                    ajaxUtils.fail().msg("不符合实习条件，无法进行数据操作");
                }
            } else {
                ajaxUtils.fail().msg("参数异常，更新失败");
            }
        } catch (ParseException e) {
            log.error("Parse time is exception {}", e);
            ajaxUtils.fail().msg("转换时间异常，更新失败");
        }
        return ajaxUtils;
    }

    /**
     * 保存校外自主实习(去单位)
     *
     * @param internshipCompanyVo 校外自主实习(去单位)
     * @param bindingResult       检验
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/apply/company/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils applyCompanySave(@Valid InternshipCompanyVo internshipCompanyVo, BindingResult bindingResult) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        if (!bindingResult.hasErrors()) {
            ErrorBean<InternshipRelease> errorBean = accessCondition(internshipCompanyVo.getInternshipReleaseId(), internshipCompanyVo.getStudentId());
            if (!errorBean.isHasError()) {
                internshipCompanyService.saveWithTransaction(internshipCompanyVo);
                ajaxUtils.success().msg("保存成功");
            } else {
                ajaxUtils.fail().msg("不符合实习条件，无法进行数据操作");
            }
        } else {
            ajaxUtils.fail().msg("参数异常，保存失败");
        }
        return ajaxUtils;
    }

    /**
     * 更新校外自主实习(去单位)
     *
     * @param internshipCompanyVo 校外自主实习(去单位)
     * @param bindingResult       检验
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/apply/company/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils applyCompanyUpdate(@Valid InternshipCompanyVo internshipCompanyVo, BindingResult bindingResult) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        try {
            if (!bindingResult.hasErrors() && !ObjectUtils.isEmpty(internshipCompanyVo.getInternshipCompanyId())) {
                ErrorBean<InternshipRelease> errorBean = accessCondition(internshipCompanyVo.getInternshipReleaseId(), internshipCompanyVo.getStudentId());
                if (!errorBean.isHasError()) {
                    String[] headmasterArr = internshipCompanyVo.getHeadmaster().split(" ");
                    if (headmasterArr.length >= 2) {
                        internshipCompanyVo.setHeadmaster(headmasterArr[0]);
                        internshipCompanyVo.setHeadmasterContact(headmasterArr[1]);
                    }
                    String[] schoolGuidanceTeacherArr = internshipCompanyVo.getSchoolGuidanceTeacher().split(" ");
                    if (schoolGuidanceTeacherArr.length >= 2) {
                        internshipCompanyVo.setSchoolGuidanceTeacher(schoolGuidanceTeacherArr[0]);
                        internshipCompanyVo.setSchoolGuidanceTeacherTel(schoolGuidanceTeacherArr[1]);
                    }
                    InternshipCompany internshipCompany = internshipCompanyService.findById(internshipCompanyVo.getInternshipCompanyId());
                    InternshipApply internshipApply = (InternshipApply) errorBean.getMapData().get("internshipApply");
                    if (internshipApply.getInternshipApplyState() == 5) {
                        internshipCompany.setStudentName(internshipCompanyVo.getStudentName());
                        internshipCompany.setCollegeClass(internshipCompanyVo.getCollegeClass());
                        internshipCompany.setStudentSex(internshipCompanyVo.getStudentSex());
                        internshipCompany.setStudentNumber(internshipCompanyVo.getStudentNumber());
                        internshipCompany.setPhoneNumber(internshipCompanyVo.getPhoneNumber());
                        internshipCompany.setQqMailbox(internshipCompanyVo.getQqMailbox());
                        internshipCompany.setParentalContact(internshipCompanyVo.getParentalContact());
                        internshipCompany.setHeadmaster(internshipCompanyVo.getHeadmaster());
                        internshipCompany.setHeadmasterContact(internshipCompanyVo.getHeadmasterContact());
                        internshipCompany.setSchoolGuidanceTeacher(internshipCompanyVo.getSchoolGuidanceTeacher());
                        internshipCompany.setSchoolGuidanceTeacherTel(internshipCompanyVo.getSchoolGuidanceTeacherTel());
                        internshipCompany.setStartTime(DateTimeUtils.formatDate(internshipCompanyVo.getStartTime()));
                        internshipCompany.setEndTime(DateTimeUtils.formatDate(internshipCompanyVo.getEndTime()));
                        internshipCompanyService.update(internshipCompany);
                        ajaxUtils.success().msg("更新成功");
                    } else if (internshipApply.getInternshipApplyState() == 7) {
                        Result<Record> internshipChangeCompanyHistoryRecord =
                                internshipChangeCompanyHistoryService.findByInternshipReleaseIdAndStudentId(internshipCompanyVo.getInternshipReleaseId(), internshipCompanyVo.getStudentId());
                        if (internshipChangeCompanyHistoryRecord.isEmpty()) {
                            InternshipChangeCompanyHistory internshipChangeCompanyHistory = new InternshipChangeCompanyHistory();
                            internshipChangeCompanyHistory.setInternshipChangeCompanyHistoryId(UUIDUtils.getUUID());
                            internshipChangeCompanyHistory.setInternshipReleaseId(internshipCompany.getInternshipReleaseId());
                            internshipChangeCompanyHistory.setStudentId(internshipCompany.getStudentId());
                            internshipChangeCompanyHistory.setChangeTime(new Timestamp(Clock.systemDefaultZone().millis()));
                            internshipChangeCompanyHistory.setCompanyName(internshipCompany.getInternshipCompanyName());
                            internshipChangeCompanyHistory.setCompanyAddress(internshipCompany.getInternshipCompanyAddress());
                            internshipChangeCompanyHistory.setCompanyContacts(internshipCompany.getInternshipCompanyContacts());
                            internshipChangeCompanyHistory.setCompanyTel(internshipCompany.getInternshipCompanyTel());
                            internshipChangeCompanyHistoryService.save(internshipChangeCompanyHistory);
                        }

                        internshipCompany.setInternshipCompanyName(internshipCompanyVo.getInternshipCompanyName());
                        internshipCompany.setInternshipCompanyAddress(internshipCompanyVo.getInternshipCompanyAddress());
                        internshipCompany.setInternshipCompanyContacts(internshipCompanyVo.getInternshipCompanyContacts());
                        internshipCompany.setInternshipCompanyTel(internshipCompanyVo.getInternshipCompanyTel());
                        internshipCompanyService.update(internshipCompany);
                        ajaxUtils.success().msg("更新成功");

                        InternshipChangeCompanyHistory internshipChangeCompanyHistory = new InternshipChangeCompanyHistory();
                        internshipChangeCompanyHistory.setInternshipChangeCompanyHistoryId(UUIDUtils.getUUID());
                        internshipChangeCompanyHistory.setInternshipReleaseId(internshipCompanyVo.getInternshipReleaseId());
                        internshipChangeCompanyHistory.setStudentId(internshipCompanyVo.getStudentId());
                        internshipChangeCompanyHistory.setChangeTime(new Timestamp(Clock.systemDefaultZone().millis()));
                        internshipChangeCompanyHistory.setCompanyName(internshipCompanyVo.getInternshipCompanyName());
                        internshipChangeCompanyHistory.setCompanyAddress(internshipCompanyVo.getInternshipCompanyAddress());
                        internshipChangeCompanyHistory.setCompanyContacts(internshipCompanyVo.getInternshipCompanyContacts());
                        internshipChangeCompanyHistory.setCompanyTel(internshipCompanyVo.getInternshipCompanyTel());
                        internshipChangeCompanyHistoryService.save(internshipChangeCompanyHistory);
                    } else {
                        if (internshipApply.getInternshipApplyState() == 3) { // 未通过
                            internshipApply.setInternshipApplyState(1); // 更新为申请中
                            internshipApplyService.update(internshipApply);
                        }
                        internshipCompany.setStudentName(internshipCompanyVo.getStudentName());
                        internshipCompany.setCollegeClass(internshipCompanyVo.getCollegeClass());
                        internshipCompany.setStudentSex(internshipCompanyVo.getStudentSex());
                        internshipCompany.setStudentNumber(internshipCompanyVo.getStudentNumber());
                        internshipCompany.setPhoneNumber(internshipCompanyVo.getPhoneNumber());
                        internshipCompany.setQqMailbox(internshipCompanyVo.getQqMailbox());
                        internshipCompany.setParentalContact(internshipCompanyVo.getParentalContact());
                        internshipCompany.setHeadmaster(internshipCompanyVo.getHeadmaster());
                        internshipCompany.setHeadmasterContact(internshipCompanyVo.getHeadmasterContact());
                        internshipCompany.setInternshipCompanyName(internshipCompanyVo.getInternshipCompanyName());
                        internshipCompany.setInternshipCompanyAddress(internshipCompanyVo.getInternshipCompanyAddress());
                        internshipCompany.setInternshipCompanyContacts(internshipCompanyVo.getInternshipCompanyContacts());
                        internshipCompany.setInternshipCompanyTel(internshipCompanyVo.getInternshipCompanyTel());
                        internshipCompany.setSchoolGuidanceTeacher(internshipCompanyVo.getSchoolGuidanceTeacher());
                        internshipCompany.setSchoolGuidanceTeacherTel(internshipCompanyVo.getSchoolGuidanceTeacherTel());
                        internshipCompany.setStartTime(DateTimeUtils.formatDate(internshipCompanyVo.getStartTime()));
                        internshipCompany.setEndTime(DateTimeUtils.formatDate(internshipCompanyVo.getEndTime()));
                        internshipCompanyService.update(internshipCompany);
                        ajaxUtils.success().msg("更新成功");
                    }
                } else {
                    ajaxUtils.fail().msg("不符合实习条件，无法进行数据操作");
                }
            } else {
                ajaxUtils.fail().msg("参数异常，更新失败");
            }
        } catch (ParseException e) {
            log.error("Parse time is exception {}", e);
            ajaxUtils.fail().msg("转换时间异常，更新失败");
        }
        return ajaxUtils;
    }

    /**
     * 保存毕业实习(校内)
     *
     * @param graduationPracticeCollegeVo 毕业实习(校内)
     * @param bindingResult               检验
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/apply/graduation/college/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils graduationCollegeSave(@Valid GraduationPracticeCollegeVo graduationPracticeCollegeVo, BindingResult bindingResult) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        if (!bindingResult.hasErrors()) {
            ErrorBean<InternshipRelease> errorBean = accessCondition(graduationPracticeCollegeVo.getInternshipReleaseId(), graduationPracticeCollegeVo.getStudentId());
            if (!errorBean.isHasError()) {
                graduationPracticeCollegeService.saveWithTransaction(graduationPracticeCollegeVo);
                ajaxUtils.success().msg("保存成功");
            } else {
                ajaxUtils.fail().msg("不符合实习条件，无法进行数据操作");
            }
        } else {
            ajaxUtils.fail().msg("参数异常，保存失败");
        }
        return ajaxUtils;
    }

    /**
     * 更新毕业实习(校内)
     *
     * @param graduationPracticeCollegeVo 毕业实习(校内)
     * @param bindingResult               检验
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/apply/graduation/college/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils graduationCollegeUpdate(@Valid GraduationPracticeCollegeVo graduationPracticeCollegeVo, BindingResult bindingResult) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        try {
            if (!bindingResult.hasErrors() && !ObjectUtils.isEmpty(graduationPracticeCollegeVo.getGraduationPracticeCollegeId())) {
                ErrorBean<InternshipRelease> errorBean = accessCondition(graduationPracticeCollegeVo.getInternshipReleaseId(), graduationPracticeCollegeVo.getStudentId());
                if (!errorBean.isHasError()) {
                    String[] headmasterArr = graduationPracticeCollegeVo.getHeadmaster().split(" ");
                    if (headmasterArr.length >= 2) {
                        graduationPracticeCollegeVo.setHeadmaster(headmasterArr[0]);
                        graduationPracticeCollegeVo.setHeadmasterContact(headmasterArr[1]);
                    }
                    String[] schoolGuidanceTeacherArr = graduationPracticeCollegeVo.getSchoolGuidanceTeacher().split(" ");
                    if (schoolGuidanceTeacherArr.length >= 2) {
                        graduationPracticeCollegeVo.setSchoolGuidanceTeacher(schoolGuidanceTeacherArr[0]);
                        graduationPracticeCollegeVo.setSchoolGuidanceTeacherTel(schoolGuidanceTeacherArr[1]);
                    }
                    GraduationPracticeCollege graduationPracticeCollege = graduationPracticeCollegeService.findById(graduationPracticeCollegeVo.getGraduationPracticeCollegeId());
                    InternshipApply internshipApply = (InternshipApply) errorBean.getMapData().get("internshipApply");
                    if (internshipApply.getInternshipApplyState() == 5) {
                        graduationPracticeCollege.setStudentName(graduationPracticeCollegeVo.getStudentName());
                        graduationPracticeCollege.setCollegeClass(graduationPracticeCollegeVo.getCollegeClass());
                        graduationPracticeCollege.setStudentSex(graduationPracticeCollegeVo.getStudentSex());
                        graduationPracticeCollege.setStudentNumber(graduationPracticeCollegeVo.getStudentNumber());
                        graduationPracticeCollege.setPhoneNumber(graduationPracticeCollegeVo.getPhoneNumber());
                        graduationPracticeCollege.setQqMailbox(graduationPracticeCollegeVo.getQqMailbox());
                        graduationPracticeCollege.setParentalContact(graduationPracticeCollegeVo.getParentalContact());
                        graduationPracticeCollege.setHeadmaster(graduationPracticeCollegeVo.getHeadmaster());
                        graduationPracticeCollege.setHeadmasterContact(graduationPracticeCollegeVo.getHeadmasterContact());
                        graduationPracticeCollege.setSchoolGuidanceTeacher(graduationPracticeCollegeVo.getSchoolGuidanceTeacher());
                        graduationPracticeCollege.setSchoolGuidanceTeacherTel(graduationPracticeCollegeVo.getSchoolGuidanceTeacherTel());
                        graduationPracticeCollege.setStartTime(DateTimeUtils.formatDate(graduationPracticeCollegeVo.getStartTime()));
                        graduationPracticeCollege.setEndTime(DateTimeUtils.formatDate(graduationPracticeCollegeVo.getEndTime()));
                        graduationPracticeCollegeService.update(graduationPracticeCollege);
                        ajaxUtils.success().msg("更新成功");
                    } else if (internshipApply.getInternshipApplyState() == 7) {
                        Result<Record> internshipChangeCompanyHistoryRecord =
                                internshipChangeCompanyHistoryService.findByInternshipReleaseIdAndStudentId(graduationPracticeCollegeVo.getInternshipReleaseId(), graduationPracticeCollegeVo.getStudentId());
                        if (internshipChangeCompanyHistoryRecord.isEmpty()) {
                            InternshipChangeCompanyHistory internshipChangeCompanyHistory = new InternshipChangeCompanyHistory();
                            internshipChangeCompanyHistory.setInternshipChangeCompanyHistoryId(UUIDUtils.getUUID());
                            internshipChangeCompanyHistory.setInternshipReleaseId(graduationPracticeCollege.getInternshipReleaseId());
                            internshipChangeCompanyHistory.setStudentId(graduationPracticeCollege.getStudentId());
                            internshipChangeCompanyHistory.setChangeTime(new Timestamp(Clock.systemDefaultZone().millis()));
                            internshipChangeCompanyHistory.setCompanyName(graduationPracticeCollege.getGraduationPracticeCollegeName());
                            internshipChangeCompanyHistory.setCompanyAddress(graduationPracticeCollege.getGraduationPracticeCollegeAddress());
                            internshipChangeCompanyHistory.setCompanyContacts(graduationPracticeCollege.getGraduationPracticeCollegeContacts());
                            internshipChangeCompanyHistory.setCompanyTel(graduationPracticeCollege.getGraduationPracticeCollegeTel());
                            internshipChangeCompanyHistoryService.save(internshipChangeCompanyHistory);
                        }

                        graduationPracticeCollege.setGraduationPracticeCollegeName(graduationPracticeCollegeVo.getGraduationPracticeCollegeName());
                        graduationPracticeCollege.setGraduationPracticeCollegeAddress(graduationPracticeCollegeVo.getGraduationPracticeCollegeAddress());
                        graduationPracticeCollege.setGraduationPracticeCollegeContacts(graduationPracticeCollegeVo.getGraduationPracticeCollegeContacts());
                        graduationPracticeCollege.setGraduationPracticeCollegeTel(graduationPracticeCollegeVo.getGraduationPracticeCollegeTel());
                        graduationPracticeCollegeService.update(graduationPracticeCollege);
                        ajaxUtils.success().msg("更新成功");

                        InternshipChangeCompanyHistory internshipChangeCompanyHistory = new InternshipChangeCompanyHistory();
                        internshipChangeCompanyHistory.setInternshipChangeCompanyHistoryId(UUIDUtils.getUUID());
                        internshipChangeCompanyHistory.setInternshipReleaseId(graduationPracticeCollegeVo.getInternshipReleaseId());
                        internshipChangeCompanyHistory.setStudentId(graduationPracticeCollegeVo.getStudentId());
                        internshipChangeCompanyHistory.setChangeTime(new Timestamp(Clock.systemDefaultZone().millis()));
                        internshipChangeCompanyHistory.setCompanyName(graduationPracticeCollegeVo.getGraduationPracticeCollegeName());
                        internshipChangeCompanyHistory.setCompanyAddress(graduationPracticeCollegeVo.getGraduationPracticeCollegeAddress());
                        internshipChangeCompanyHistory.setCompanyContacts(graduationPracticeCollegeVo.getGraduationPracticeCollegeContacts());
                        internshipChangeCompanyHistory.setCompanyTel(graduationPracticeCollegeVo.getGraduationPracticeCollegeTel());
                        internshipChangeCompanyHistoryService.save(internshipChangeCompanyHistory);
                    } else {
                        if (internshipApply.getInternshipApplyState() == 3) { // 未通过
                            internshipApply.setInternshipApplyState(1); // 更新为申请中
                            internshipApplyService.update(internshipApply);
                        }
                        graduationPracticeCollege.setStudentName(graduationPracticeCollegeVo.getStudentName());
                        graduationPracticeCollege.setCollegeClass(graduationPracticeCollegeVo.getCollegeClass());
                        graduationPracticeCollege.setStudentSex(graduationPracticeCollegeVo.getStudentSex());
                        graduationPracticeCollege.setStudentNumber(graduationPracticeCollegeVo.getStudentNumber());
                        graduationPracticeCollege.setPhoneNumber(graduationPracticeCollegeVo.getPhoneNumber());
                        graduationPracticeCollege.setQqMailbox(graduationPracticeCollegeVo.getQqMailbox());
                        graduationPracticeCollege.setParentalContact(graduationPracticeCollegeVo.getParentalContact());
                        graduationPracticeCollege.setHeadmaster(graduationPracticeCollegeVo.getHeadmaster());
                        graduationPracticeCollege.setHeadmasterContact(graduationPracticeCollegeVo.getHeadmasterContact());
                        graduationPracticeCollege.setGraduationPracticeCollegeName(graduationPracticeCollegeVo.getGraduationPracticeCollegeName());
                        graduationPracticeCollege.setGraduationPracticeCollegeAddress(graduationPracticeCollegeVo.getGraduationPracticeCollegeAddress());
                        graduationPracticeCollege.setGraduationPracticeCollegeContacts(graduationPracticeCollegeVo.getGraduationPracticeCollegeContacts());
                        graduationPracticeCollege.setGraduationPracticeCollegeTel(graduationPracticeCollegeVo.getGraduationPracticeCollegeTel());
                        graduationPracticeCollege.setSchoolGuidanceTeacher(graduationPracticeCollegeVo.getSchoolGuidanceTeacher());
                        graduationPracticeCollege.setSchoolGuidanceTeacherTel(graduationPracticeCollegeVo.getSchoolGuidanceTeacherTel());
                        graduationPracticeCollege.setStartTime(DateTimeUtils.formatDate(graduationPracticeCollegeVo.getStartTime()));
                        graduationPracticeCollege.setEndTime(DateTimeUtils.formatDate(graduationPracticeCollegeVo.getEndTime()));
                        graduationPracticeCollegeService.update(graduationPracticeCollege);
                        ajaxUtils.success().msg("更新成功");
                    }
                } else {
                    ajaxUtils.fail().msg("不符合实习条件，无法进行数据操作");
                }
            } else {
                ajaxUtils.fail().msg("参数异常，更新失败");
            }
        } catch (ParseException e) {
            log.error("Parse time is exception {}", e);
            ajaxUtils.fail().msg("转换时间异常，更新失败");
        }
        return ajaxUtils;
    }

    /**
     * 保存毕业实习(学校统一组织校外实习)
     *
     * @param graduationPracticeUnifyVo 毕业实习(学校统一组织校外实习)
     * @param bindingResult             检验
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/apply/graduation/unify/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils graduationUnifySave(@Valid GraduationPracticeUnifyVo graduationPracticeUnifyVo, BindingResult bindingResult) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        if (!bindingResult.hasErrors()) {
            ErrorBean<InternshipRelease> errorBean = accessCondition(graduationPracticeUnifyVo.getInternshipReleaseId(), graduationPracticeUnifyVo.getStudentId());
            if (!errorBean.isHasError()) {
                graduationPracticeUnifyService.saveWithTransaction(graduationPracticeUnifyVo);
                ajaxUtils.success().msg("保存成功");
            } else {
                ajaxUtils.fail().msg("不符合实习条件，无法进行数据操作");
            }
        } else {
            ajaxUtils.fail().msg("参数异常，保存失败");
        }
        return ajaxUtils;
    }

    /**
     * 更新毕业实习(学校统一组织校外实习)
     *
     * @param graduationPracticeUnifyVo 毕业实习(学校统一组织校外实习)
     * @param bindingResult             检验
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/apply/graduation/unify/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils graduationUnifyUpdate(@Valid GraduationPracticeUnifyVo graduationPracticeUnifyVo, BindingResult bindingResult) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        try {
            if (!bindingResult.hasErrors() && !ObjectUtils.isEmpty(graduationPracticeUnifyVo.getGraduationPracticeUnifyId())) {
                ErrorBean<InternshipRelease> errorBean = accessCondition(graduationPracticeUnifyVo.getInternshipReleaseId(), graduationPracticeUnifyVo.getStudentId());
                if (!errorBean.isHasError()) {
                    String[] headmasterArr = graduationPracticeUnifyVo.getHeadmaster().split(" ");
                    if (headmasterArr.length >= 2) {
                        graduationPracticeUnifyVo.setHeadmaster(headmasterArr[0]);
                        graduationPracticeUnifyVo.setHeadmasterContact(headmasterArr[1]);
                    }
                    String[] schoolGuidanceTeacherArr = graduationPracticeUnifyVo.getSchoolGuidanceTeacher().split(" ");
                    if (schoolGuidanceTeacherArr.length >= 2) {
                        graduationPracticeUnifyVo.setSchoolGuidanceTeacher(schoolGuidanceTeacherArr[0]);
                        graduationPracticeUnifyVo.setSchoolGuidanceTeacherTel(schoolGuidanceTeacherArr[1]);
                    }
                    GraduationPracticeUnify graduationPracticeUnify = graduationPracticeUnifyService.findById(graduationPracticeUnifyVo.getGraduationPracticeUnifyId());
                    InternshipApply internshipApply = (InternshipApply) errorBean.getMapData().get("internshipApply");
                    if (internshipApply.getInternshipApplyState() == 5) {
                        graduationPracticeUnify.setStudentName(graduationPracticeUnifyVo.getStudentName());
                        graduationPracticeUnify.setCollegeClass(graduationPracticeUnifyVo.getCollegeClass());
                        graduationPracticeUnify.setStudentSex(graduationPracticeUnifyVo.getStudentSex());
                        graduationPracticeUnify.setStudentNumber(graduationPracticeUnifyVo.getStudentNumber());
                        graduationPracticeUnify.setPhoneNumber(graduationPracticeUnifyVo.getPhoneNumber());
                        graduationPracticeUnify.setQqMailbox(graduationPracticeUnifyVo.getQqMailbox());
                        graduationPracticeUnify.setParentalContact(graduationPracticeUnifyVo.getParentalContact());
                        graduationPracticeUnify.setHeadmaster(graduationPracticeUnifyVo.getHeadmaster());
                        graduationPracticeUnify.setHeadmasterContact(graduationPracticeUnifyVo.getHeadmasterContact());
                        graduationPracticeUnify.setSchoolGuidanceTeacher(graduationPracticeUnifyVo.getSchoolGuidanceTeacher());
                        graduationPracticeUnify.setSchoolGuidanceTeacherTel(graduationPracticeUnifyVo.getSchoolGuidanceTeacherTel());
                        graduationPracticeUnify.setStartTime(DateTimeUtils.formatDate(graduationPracticeUnifyVo.getStartTime()));
                        graduationPracticeUnify.setEndTime(DateTimeUtils.formatDate(graduationPracticeUnifyVo.getEndTime()));
                        graduationPracticeUnifyService.update(graduationPracticeUnify);
                        ajaxUtils.success().msg("更新成功");
                    } else if (internshipApply.getInternshipApplyState() == 7) {
                        Result<Record> internshipChangeCompanyHistoryRecord =
                                internshipChangeCompanyHistoryService.findByInternshipReleaseIdAndStudentId(graduationPracticeUnifyVo.getInternshipReleaseId(), graduationPracticeUnifyVo.getStudentId());
                        if (internshipChangeCompanyHistoryRecord.isEmpty()) {
                            InternshipChangeCompanyHistory internshipChangeCompanyHistory = new InternshipChangeCompanyHistory();
                            internshipChangeCompanyHistory.setInternshipChangeCompanyHistoryId(UUIDUtils.getUUID());
                            internshipChangeCompanyHistory.setInternshipReleaseId(graduationPracticeUnify.getInternshipReleaseId());
                            internshipChangeCompanyHistory.setStudentId(graduationPracticeUnify.getStudentId());
                            internshipChangeCompanyHistory.setChangeTime(new Timestamp(Clock.systemDefaultZone().millis()));
                            internshipChangeCompanyHistory.setCompanyName(graduationPracticeUnify.getGraduationPracticeUnifyName());
                            internshipChangeCompanyHistory.setCompanyAddress(graduationPracticeUnify.getGraduationPracticeUnifyAddress());
                            internshipChangeCompanyHistory.setCompanyContacts(graduationPracticeUnify.getGraduationPracticeUnifyContacts());
                            internshipChangeCompanyHistory.setCompanyTel(graduationPracticeUnify.getGraduationPracticeUnifyTel());
                            internshipChangeCompanyHistoryService.save(internshipChangeCompanyHistory);
                        }

                        graduationPracticeUnify.setGraduationPracticeUnifyName(graduationPracticeUnifyVo.getGraduationPracticeUnifyName());
                        graduationPracticeUnify.setGraduationPracticeUnifyAddress(graduationPracticeUnifyVo.getGraduationPracticeUnifyAddress());
                        graduationPracticeUnify.setGraduationPracticeUnifyContacts(graduationPracticeUnifyVo.getGraduationPracticeUnifyContacts());
                        graduationPracticeUnify.setGraduationPracticeUnifyTel(graduationPracticeUnifyVo.getGraduationPracticeUnifyTel());
                        graduationPracticeUnifyService.update(graduationPracticeUnify);
                        ajaxUtils.success().msg("更新成功");

                        InternshipChangeCompanyHistory internshipChangeCompanyHistory = new InternshipChangeCompanyHistory();
                        internshipChangeCompanyHistory.setInternshipChangeCompanyHistoryId(UUIDUtils.getUUID());
                        internshipChangeCompanyHistory.setInternshipReleaseId(graduationPracticeUnifyVo.getInternshipReleaseId());
                        internshipChangeCompanyHistory.setStudentId(graduationPracticeUnifyVo.getStudentId());
                        internshipChangeCompanyHistory.setChangeTime(new Timestamp(Clock.systemDefaultZone().millis()));
                        internshipChangeCompanyHistory.setCompanyName(graduationPracticeUnifyVo.getGraduationPracticeUnifyName());
                        internshipChangeCompanyHistory.setCompanyAddress(graduationPracticeUnifyVo.getGraduationPracticeUnifyAddress());
                        internshipChangeCompanyHistory.setCompanyContacts(graduationPracticeUnifyVo.getGraduationPracticeUnifyContacts());
                        internshipChangeCompanyHistory.setCompanyTel(graduationPracticeUnifyVo.getGraduationPracticeUnifyTel());
                        internshipChangeCompanyHistoryService.save(internshipChangeCompanyHistory);
                    } else {
                        if (internshipApply.getInternshipApplyState() == 3) { // 未通过
                            internshipApply.setInternshipApplyState(1); // 更新为申请中
                            internshipApplyService.update(internshipApply);
                        }
                        graduationPracticeUnify.setStudentName(graduationPracticeUnifyVo.getStudentName());
                        graduationPracticeUnify.setCollegeClass(graduationPracticeUnifyVo.getCollegeClass());
                        graduationPracticeUnify.setStudentSex(graduationPracticeUnifyVo.getStudentSex());
                        graduationPracticeUnify.setStudentNumber(graduationPracticeUnifyVo.getStudentNumber());
                        graduationPracticeUnify.setPhoneNumber(graduationPracticeUnifyVo.getPhoneNumber());
                        graduationPracticeUnify.setQqMailbox(graduationPracticeUnifyVo.getQqMailbox());
                        graduationPracticeUnify.setParentalContact(graduationPracticeUnifyVo.getParentalContact());
                        graduationPracticeUnify.setHeadmaster(graduationPracticeUnifyVo.getHeadmaster());
                        graduationPracticeUnify.setHeadmasterContact(graduationPracticeUnifyVo.getHeadmasterContact());
                        graduationPracticeUnify.setGraduationPracticeUnifyName(graduationPracticeUnifyVo.getGraduationPracticeUnifyName());
                        graduationPracticeUnify.setGraduationPracticeUnifyAddress(graduationPracticeUnifyVo.getGraduationPracticeUnifyAddress());
                        graduationPracticeUnify.setGraduationPracticeUnifyContacts(graduationPracticeUnifyVo.getGraduationPracticeUnifyContacts());
                        graduationPracticeUnify.setGraduationPracticeUnifyTel(graduationPracticeUnifyVo.getGraduationPracticeUnifyTel());
                        graduationPracticeUnify.setSchoolGuidanceTeacher(graduationPracticeUnifyVo.getSchoolGuidanceTeacher());
                        graduationPracticeUnify.setSchoolGuidanceTeacherTel(graduationPracticeUnifyVo.getSchoolGuidanceTeacherTel());
                        graduationPracticeUnify.setStartTime(DateTimeUtils.formatDate(graduationPracticeUnifyVo.getStartTime()));
                        graduationPracticeUnify.setEndTime(DateTimeUtils.formatDate(graduationPracticeUnifyVo.getEndTime()));
                        graduationPracticeUnifyService.update(graduationPracticeUnify);
                        ajaxUtils.success().msg("更新成功");
                    }
                } else {
                    ajaxUtils.fail().msg("不符合实习条件，无法进行数据操作");
                }
            } else {
                ajaxUtils.fail().msg("参数异常，更新失败");
            }
        } catch (ParseException e) {
            log.error("Parse time is exception {}", e);
            ajaxUtils.fail().msg("转换时间异常，更新失败");
        }
        return ajaxUtils;
    }

    /**
     * 保存毕业实习(校外)
     *
     * @param graduationPracticeCompanyVo 毕业实习(校外)
     * @param bindingResult               检验
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/apply/graduation/company/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils graduationCompanySave(@Valid GraduationPracticeCompanyVo graduationPracticeCompanyVo, BindingResult bindingResult) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        if (!bindingResult.hasErrors()) {
            ErrorBean<InternshipRelease> errorBean = accessCondition(graduationPracticeCompanyVo.getInternshipReleaseId(), graduationPracticeCompanyVo.getStudentId());
            if (!errorBean.isHasError()) {
                graduationPracticeCompanyService.saveWithTransaction(graduationPracticeCompanyVo);
                ajaxUtils.success().msg("保存成功");
            } else {
                ajaxUtils.fail().msg("不符合实习条件，无法进行数据操作");
            }
        } else {
            ajaxUtils.fail().msg("参数异常，保存失败");
        }
        return ajaxUtils;
    }

    /**
     * 更新毕业实习(校外)
     *
     * @param graduationPracticeCompanyVo 毕业实习(校外)
     * @param bindingResult               检验
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/apply/graduation/company/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils graduationCompanyUpdate(@Valid GraduationPracticeCompanyVo graduationPracticeCompanyVo, BindingResult bindingResult) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        try {
            if (!bindingResult.hasErrors() && !ObjectUtils.isEmpty(graduationPracticeCompanyVo.getGraduationPracticeCompanyId())) {
                ErrorBean<InternshipRelease> errorBean = accessCondition(graduationPracticeCompanyVo.getInternshipReleaseId(), graduationPracticeCompanyVo.getStudentId());
                if (!errorBean.isHasError()) {
                    String[] headmasterArr = graduationPracticeCompanyVo.getHeadmaster().split(" ");
                    if (headmasterArr.length >= 2) {
                        graduationPracticeCompanyVo.setHeadmaster(headmasterArr[0]);
                        graduationPracticeCompanyVo.setHeadmasterContact(headmasterArr[1]);
                    }
                    String[] schoolGuidanceTeacherArr = graduationPracticeCompanyVo.getSchoolGuidanceTeacher().split(" ");
                    if (schoolGuidanceTeacherArr.length >= 2) {
                        graduationPracticeCompanyVo.setSchoolGuidanceTeacher(schoolGuidanceTeacherArr[0]);
                        graduationPracticeCompanyVo.setSchoolGuidanceTeacherTel(schoolGuidanceTeacherArr[1]);
                    }
                    GraduationPracticeCompany graduationPracticeCompany = graduationPracticeCompanyService.findById(graduationPracticeCompanyVo.getGraduationPracticeCompanyId());
                    InternshipApply internshipApply = (InternshipApply) errorBean.getMapData().get("internshipApply");
                    if (internshipApply.getInternshipApplyState() == 5) {
                        graduationPracticeCompany.setStudentName(graduationPracticeCompanyVo.getStudentName());
                        graduationPracticeCompany.setCollegeClass(graduationPracticeCompanyVo.getCollegeClass());
                        graduationPracticeCompany.setStudentSex(graduationPracticeCompanyVo.getStudentSex());
                        graduationPracticeCompany.setStudentNumber(graduationPracticeCompanyVo.getStudentNumber());
                        graduationPracticeCompany.setPhoneNumber(graduationPracticeCompanyVo.getPhoneNumber());
                        graduationPracticeCompany.setQqMailbox(graduationPracticeCompanyVo.getQqMailbox());
                        graduationPracticeCompany.setParentalContact(graduationPracticeCompanyVo.getParentalContact());
                        graduationPracticeCompany.setHeadmaster(graduationPracticeCompanyVo.getHeadmaster());
                        graduationPracticeCompany.setHeadmasterContact(graduationPracticeCompanyVo.getHeadmasterContact());
                        graduationPracticeCompany.setSchoolGuidanceTeacher(graduationPracticeCompanyVo.getSchoolGuidanceTeacher());
                        graduationPracticeCompany.setSchoolGuidanceTeacherTel(graduationPracticeCompanyVo.getSchoolGuidanceTeacherTel());
                        graduationPracticeCompany.setStartTime(DateTimeUtils.formatDate(graduationPracticeCompanyVo.getStartTime()));
                        graduationPracticeCompany.setEndTime(DateTimeUtils.formatDate(graduationPracticeCompanyVo.getEndTime()));
                        graduationPracticeCompanyService.update(graduationPracticeCompany);
                        ajaxUtils.success().msg("更新成功");
                    } else if (internshipApply.getInternshipApplyState() == 7) {
                        Result<Record> internshipChangeCompanyHistoryRecord =
                                internshipChangeCompanyHistoryService.findByInternshipReleaseIdAndStudentId(graduationPracticeCompanyVo.getInternshipReleaseId(), graduationPracticeCompanyVo.getStudentId());
                        if (internshipChangeCompanyHistoryRecord.isEmpty()) {
                            InternshipChangeCompanyHistory internshipChangeCompanyHistory = new InternshipChangeCompanyHistory();
                            internshipChangeCompanyHistory.setInternshipChangeCompanyHistoryId(UUIDUtils.getUUID());
                            internshipChangeCompanyHistory.setInternshipReleaseId(graduationPracticeCompany.getInternshipReleaseId());
                            internshipChangeCompanyHistory.setStudentId(graduationPracticeCompany.getStudentId());
                            internshipChangeCompanyHistory.setChangeTime(new Timestamp(Clock.systemDefaultZone().millis()));
                            internshipChangeCompanyHistory.setCompanyName(graduationPracticeCompany.getGraduationPracticeCompanyName());
                            internshipChangeCompanyHistory.setCompanyAddress(graduationPracticeCompany.getGraduationPracticeCompanyAddress());
                            internshipChangeCompanyHistory.setCompanyContacts(graduationPracticeCompany.getGraduationPracticeCompanyContacts());
                            internshipChangeCompanyHistory.setCompanyTel(graduationPracticeCompany.getGraduationPracticeCompanyTel());
                            internshipChangeCompanyHistoryService.save(internshipChangeCompanyHistory);
                        }
                        graduationPracticeCompany.setGraduationPracticeCompanyName(graduationPracticeCompanyVo.getGraduationPracticeCompanyName());
                        graduationPracticeCompany.setGraduationPracticeCompanyAddress(graduationPracticeCompanyVo.getGraduationPracticeCompanyAddress());
                        graduationPracticeCompany.setGraduationPracticeCompanyContacts(graduationPracticeCompanyVo.getGraduationPracticeCompanyContacts());
                        graduationPracticeCompany.setGraduationPracticeCompanyTel(graduationPracticeCompanyVo.getGraduationPracticeCompanyTel());
                        graduationPracticeCompanyService.update(graduationPracticeCompany);
                        ajaxUtils.success().msg("更新成功");

                        InternshipChangeCompanyHistory internshipChangeCompanyHistory = new InternshipChangeCompanyHistory();
                        internshipChangeCompanyHistory.setInternshipChangeCompanyHistoryId(UUIDUtils.getUUID());
                        internshipChangeCompanyHistory.setInternshipReleaseId(graduationPracticeCompanyVo.getInternshipReleaseId());
                        internshipChangeCompanyHistory.setStudentId(graduationPracticeCompanyVo.getStudentId());
                        internshipChangeCompanyHistory.setChangeTime(new Timestamp(Clock.systemDefaultZone().millis()));
                        internshipChangeCompanyHistory.setCompanyName(graduationPracticeCompanyVo.getGraduationPracticeCompanyName());
                        internshipChangeCompanyHistory.setCompanyAddress(graduationPracticeCompanyVo.getGraduationPracticeCompanyAddress());
                        internshipChangeCompanyHistory.setCompanyContacts(graduationPracticeCompanyVo.getGraduationPracticeCompanyContacts());
                        internshipChangeCompanyHistory.setCompanyTel(graduationPracticeCompanyVo.getGraduationPracticeCompanyTel());
                        internshipChangeCompanyHistoryService.save(internshipChangeCompanyHistory);
                    } else {
                        if (internshipApply.getInternshipApplyState() == 3) { // 未通过
                            internshipApply.setInternshipApplyState(1); // 更新为申请中
                            internshipApplyService.update(internshipApply);
                        }
                        graduationPracticeCompany.setStudentName(graduationPracticeCompanyVo.getStudentName());
                        graduationPracticeCompany.setCollegeClass(graduationPracticeCompanyVo.getCollegeClass());
                        graduationPracticeCompany.setStudentSex(graduationPracticeCompanyVo.getStudentSex());
                        graduationPracticeCompany.setStudentNumber(graduationPracticeCompanyVo.getStudentNumber());
                        graduationPracticeCompany.setPhoneNumber(graduationPracticeCompanyVo.getPhoneNumber());
                        graduationPracticeCompany.setQqMailbox(graduationPracticeCompanyVo.getQqMailbox());
                        graduationPracticeCompany.setParentalContact(graduationPracticeCompanyVo.getParentalContact());
                        graduationPracticeCompany.setHeadmaster(graduationPracticeCompanyVo.getHeadmaster());
                        graduationPracticeCompany.setHeadmasterContact(graduationPracticeCompanyVo.getHeadmasterContact());
                        graduationPracticeCompany.setGraduationPracticeCompanyName(graduationPracticeCompanyVo.getGraduationPracticeCompanyName());
                        graduationPracticeCompany.setGraduationPracticeCompanyAddress(graduationPracticeCompanyVo.getGraduationPracticeCompanyAddress());
                        graduationPracticeCompany.setGraduationPracticeCompanyContacts(graduationPracticeCompanyVo.getGraduationPracticeCompanyContacts());
                        graduationPracticeCompany.setGraduationPracticeCompanyTel(graduationPracticeCompanyVo.getGraduationPracticeCompanyTel());
                        graduationPracticeCompany.setSchoolGuidanceTeacher(graduationPracticeCompanyVo.getSchoolGuidanceTeacher());
                        graduationPracticeCompany.setSchoolGuidanceTeacherTel(graduationPracticeCompanyVo.getSchoolGuidanceTeacherTel());
                        graduationPracticeCompany.setStartTime(DateTimeUtils.formatDate(graduationPracticeCompanyVo.getStartTime()));
                        graduationPracticeCompany.setEndTime(DateTimeUtils.formatDate(graduationPracticeCompanyVo.getEndTime()));
                        graduationPracticeCompanyService.update(graduationPracticeCompany);
                        ajaxUtils.success().msg("更新成功");
                    }
                } else {
                    ajaxUtils.fail().msg("不符合实习条件，无法进行数据操作");
                }
            } else {
                ajaxUtils.fail().msg("参数异常，更新失败");
            }
        } catch (ParseException e) {
            log.error("Parse time is exception {}", e);
            ajaxUtils.fail().msg("转换时间异常，更新失败");
        }
        return ajaxUtils;
    }

    /**
     * 组装页面相同参数
     *
     * @param modelMap  页面对象
     * @param errorBean 判断条件
     */
    private void buildCommonModelMap(ModelMap modelMap, ErrorBean<InternshipRelease> errorBean) {
        StudentBean studentBean = (StudentBean) errorBean.getMapData().get("student");
        String qqMail = "";
        if (studentBean.getUsername().toLowerCase().contains("@qq.com")) {
            qqMail = studentBean.getUsername();
        }
        modelMap.addAttribute("qqMail", qqMail);
        modelMap.addAttribute("student", studentBean);
        Map<String, String> internshipTeacherParams = getInternshipTeacherFromErrorBean(errorBean);
        modelMap.addAttribute("internshipTeacher", internshipTeacherParams.get("internshipTeacher"));
        modelMap.addAttribute("internshipTeacherName", internshipTeacherParams.get("internshipTeacherName"));
        modelMap.addAttribute("internshipTeacherMobile", internshipTeacherParams.get("internshipTeacherMobile"));
        modelMap.addAttribute("companyInfo", studentBean.getSchoolName() + studentBean.getCollegeName());
        modelMap.addAttribute("companyAddr", studentBean.getCollegeAddress());
    }

    /**
     * 从error bean中获取指导教师
     *
     * @param errorBean 判断条件
     * @return 指导教师
     */
    private Map<String, String> getInternshipTeacherFromErrorBean(ErrorBean<InternshipRelease> errorBean) {
        Map<String, String> params = new HashMap<>();
        InternshipTeacherDistribution internshipTeacherDistribution = (InternshipTeacherDistribution) errorBean.getMapData().get("internshipTeacherDistribution");
        int staffId = internshipTeacherDistribution.getStaffId();
        Optional<Record> staffRecord = staffService.findByIdRelation(staffId);
        if (staffRecord.isPresent()) {
            StaffBean staffBean = staffRecord.get().into(StaffBean.class);
            params.put("internshipTeacherName", staffBean.getRealName());
            params.put("internshipTeacherMobile", staffBean.getMobile());
            params.put("internshipTeacher", staffBean.getRealName() + " " + staffBean.getMobile());
        }
        return params;
    }

    /**
     * 进入实习申请页面判断条件
     *
     * @param internshipReleaseId 实习发布id
     * @param studentId           学生id
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/apply/condition", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils canUse(@RequestParam("id") String internshipReleaseId, int studentId) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        ErrorBean<InternshipRelease> errorBean = accessCondition(internshipReleaseId, studentId);
        if (!errorBean.isHasError()) {
            ajaxUtils.success().msg("在条件范围，允许使用");
        } else {
            ajaxUtils.fail().msg(errorBean.getErrorMsg());
        }
        return ajaxUtils;
    }

    /**
     * 检验学生
     *
     * @param info 学生信息
     * @param type 检验类型
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/apply/valid/student", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils validStudent(@RequestParam("student") String info, int type) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        Student student = null;
        if (type == 0) {
            student = studentService.findByUsername(info);
        } else if (type == 1) {
            student = studentService.findByStudentNumber(info);
        }
        if (!ObjectUtils.isEmpty(student)) {
            ajaxUtils.success().msg("查询学生数据成功").obj(student.getStudentId());
        } else {
            ajaxUtils.fail().msg("查询学生数据失败");
        }
        return ajaxUtils;
    }

    /**
     * 获取班主任数据
     *
     * @param internshipReleaseId 实习发布id
     * @param studentId           学生id
     * @return 数据
     */
    @RequestMapping(value = "/web/internship/apply/teachers", method = RequestMethod.GET)
    @ResponseBody
    public AjaxUtils<StaffBean> teachers(@RequestParam("id") String internshipReleaseId, @RequestParam("studentId") int studentId) {
        AjaxUtils<StaffBean> ajaxUtils = AjaxUtils.of();
        ErrorBean<InternshipRelease> errorBean = accessCondition(internshipReleaseId, studentId);
        if (!errorBean.isHasError()) {
            InternshipRelease internshipRelease = errorBean.getData();
            List<StaffBean> staffs = new ArrayList<>();
            Byte enabled = 1;
            Byte verifyMailbox = 1;
            Result<Record> staffRecord = staffService.findByDepartmentIdAndEnabledAndVerifyMailboxExistsAuthoritiesRelation(internshipRelease.getDepartmentId(), enabled, verifyMailbox);
            if (staffRecord.isNotEmpty()) {
                staffs = staffRecord.into(StaffBean.class);
            }
            ajaxUtils.success().msg("获取班主任数据成功").listData(staffs);
        } else {
            ajaxUtils.fail().msg("您不符合申请条件，无法获取数据");
        }
        return ajaxUtils;
    }

    /**
     * 撤消状态
     *
     * @param internshipReleaseId 实习发布id
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/apply/recall", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils applyRecall(@RequestParam("id") String internshipReleaseId) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        Users users = usersService.getUserFromSession();
        Student student = studentService.findByUsername(users.getUsername());
        if (Objects.isNull(student)) {
            return ajaxUtils.fail().msg("未查询到相关学生信息");
        }
        int studentId = student.getStudentId();
        Optional<Record> internshipApplyRecord = internshipApplyService.findByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
        if (internshipApplyRecord.isPresent()) {
            InternshipApply internshipApply = internshipApplyRecord.get().into(InternshipApply.class);
            // 处于以下状态不允许撤消 2：已通过；5：基本信息变更填写中；7：单位信息变更填写中
            if (internshipApply.getInternshipApplyState() == 2 || internshipApply.getInternshipApplyState() == 5 ||
                    internshipApply.getInternshipApplyState() == 7) {
                ajaxUtils.fail().msg("您当前状态下，不允许进行撤消操作");
            }
            boolean isCancel = false;
            // 处于 0：未提交申请 1：申请中 允许撤消 该状态下的撤消将会删除所有相关实习信息
            if (internshipApply.getInternshipApplyState() == 1 || internshipApply.getInternshipApplyState() == 0) {
                InternshipRelease internshipRelease = internshipReleaseService.findById(internshipReleaseId);
                internshipApplyService.deleteInternshipApplyRecord(internshipRelease.getInternshipTypeId(), internshipReleaseId, studentId);
                internshipApplyService.deleteByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
                internshipChangeHistoryService.deleteByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
                internshipChangeCompanyHistoryService.deleteByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
                ajaxUtils.success().msg("撤消申请成功");
            }
            // 处于4：基本信息变更申请中 6：单位信息变更申请中 在这两个状态下将返回已通过状态
            if (internshipApply.getInternshipApplyState() == 4 || internshipApply.getInternshipApplyState() == 6) {
                internshipApply.setInternshipApplyState(2);
                internshipApplyService.update(internshipApply);
                ajaxUtils.success().msg("撤消申请成功");
                isCancel = true;
            }

            if (isCancel) {
                InternshipChangeHistory internshipChangeHistory = new InternshipChangeHistory();
                internshipChangeHistory.setState(-1);
                if (internshipApply.getInternshipApplyState() == 4) {
                    internshipChangeHistory.setReason("撤消基本信息变更申请");
                }
                if (internshipApply.getInternshipApplyState() == 6) {
                    internshipChangeHistory.setReason("撤消单位信息变更申请");
                }
                internshipChangeHistory.setInternshipChangeHistoryId(UUIDUtils.getUUID());
                internshipChangeHistory.setInternshipReleaseId(internshipReleaseId);
                internshipChangeHistory.setStudentId(studentId);
                internshipChangeHistory.setApplyTime(new Timestamp(Clock.systemDefaultZone().millis()));
                internshipChangeHistoryService.save(internshipChangeHistory);
            }
        } else {
            ajaxUtils.fail().msg("未查询到相关申请信息");
        }
        return ajaxUtils;
    }

    /**
     * 基础信息变更 单位信息变更申请
     *
     * @param reason               原因
     * @param internshipApplyState 状态
     * @param internshipReleaseId  实习发布id
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/apply/state", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils applyState(@RequestParam("reason") String reason, @RequestParam("internshipApplyState") int internshipApplyState,
                                @RequestParam("internshipReleaseId") String internshipReleaseId) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        Users users = usersService.getUserFromSession();
        Student student = studentService.findByUsername(users.getUsername());
        if (Objects.isNull(student)) {
            return ajaxUtils.fail().msg("未查询到相关学生信息");
        }
        int studentId = student.getStudentId();
        Optional<Record> internshipApplyRecord = internshipApplyService.findByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
        if (internshipApplyRecord.isPresent()) {
            InternshipApply internshipApply = internshipApplyRecord.get().into(InternshipApply.class);
            // 处于 2：已通过 才可变更申请
            if (internshipApply.getInternshipApplyState() == 2) {
                Timestamp now = new Timestamp(Clock.systemDefaultZone().millis());
                internshipApply.setInternshipApplyState(internshipApplyState);
                internshipApply.setReason(reason);
                internshipApply.setApplyTime(now);
                internshipApplyService.update(internshipApply);
                ajaxUtils.success().msg("申请成功");

                InternshipChangeHistory internshipChangeHistory = new InternshipChangeHistory();
                internshipChangeHistory.setInternshipChangeHistoryId(UUIDUtils.getUUID());
                internshipChangeHistory.setInternshipReleaseId(internshipReleaseId);
                internshipChangeHistory.setStudentId(studentId);
                internshipChangeHistory.setState(internshipApplyState);
                internshipChangeHistory.setApplyTime(now);
                internshipChangeHistory.setReason(internshipApply.getReason());
                internshipChangeHistoryService.save(internshipChangeHistory);

            } else {
                ajaxUtils.fail().msg("您当前状态，无法变更申请");
            }
        } else {
            ajaxUtils.fail().msg("未查询到相关申请信息");
        }
        return ajaxUtils;
    }

    /**
     * 保存实习申请资料
     *
     * @param internshipReleaseId         实习发布id
     * @param multipartHttpServletRequest 文件
     * @param request                     请求
     * @return true or false
     */
    @RequestMapping("/web/internship/apply/upload")
    @ResponseBody
    public AjaxUtils<FileBean> applyUpload(@RequestParam("internshipReleaseId") String internshipReleaseId, MultipartHttpServletRequest multipartHttpServletRequest, HttpServletRequest request) {
        AjaxUtils<FileBean> data = AjaxUtils.of();
        try {
            Users users = usersService.getUserFromSession();
            Student student = studentService.findByUsername(users.getUsername());
            if (Objects.isNull(student)) {
                return data.fail().msg("未查询到相关学生信息");
            }
            int studentId = student.getStudentId();
            if (!ObjectUtils.isEmpty(student)) {
                Optional<Record> internshipApplyRecord = internshipApplyService.findByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
                if (internshipApplyRecord.isPresent()) {
                    String path = Workbook.internshipApplyPath(users);
                    List<FileBean> fileBeen = uploadService.upload(multipartHttpServletRequest,
                            RequestUtils.getRealPath(request) + path, request.getRemoteAddr());
                    fileBeen.forEach(fileBean -> {
                        String fileId = UUIDUtils.getUUID();
                        InternshipApply internshipApply = internshipApplyRecord.get().into(InternshipApply.class);
                        if (StringUtils.hasLength(internshipApply.getInternshipFileId())) {
                            Files oldFile = filesService.findById(internshipApply.getInternshipFileId());
                            try {
                                FilesUtils.deleteFile(RequestUtils.getRealPath(request) + oldFile.getRelativePath());
                                Files files = new Files();
                                files.setFileId(fileId);
                                files.setExt(fileBean.getExt());
                                files.setNewName(fileBean.getNewName());
                                files.setOriginalFileName(fileBean.getOriginalFileName());
                                files.setSize(String.valueOf(fileBean.getSize()));
                                files.setRelativePath(path + fileBean.getNewName());
                                filesService.save(files);
                                internshipApply.setInternshipFileId(fileId);
                                internshipApplyService.update(internshipApply);
                                filesService.deleteById(oldFile.getFileId());
                            } catch (IOException e) {
                                log.error("Delete file error, error is {}", e);
                            }
                        }
                    });
                }
                data.success().msg("保存成功");
            } else {
                data.fail().msg("保存失败");
            }
        } catch (Exception e) {
            log.error("Upload apply error, error is {}", e);
            data.fail().msg("保存文件异常");
        }
        return data;
    }

    /**
     * 删除电子材料
     *
     * @param internshipReleaseId 实习发布id
     * @param request             请求
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/apply/delete/file", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils deleteFile(@RequestParam("id") String internshipReleaseId, HttpServletRequest request) {
        AjaxUtils data = AjaxUtils.of();
        try {
            Users users = usersService.getUserFromSession();
            Student student = studentService.findByUsername(users.getUsername());
            if (Objects.isNull(student)) {
                return data.fail().msg("未查询到相关学生信息");
            }
            int studentId = student.getStudentId();
            Optional<Record> internshipApplyRecord = internshipApplyService.findByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
            if (internshipApplyRecord.isPresent()) {
                InternshipApply internshipApply = internshipApplyRecord.get().into(InternshipApply.class);
                Files files = filesService.findById(internshipApply.getInternshipFileId());
                internshipApply.setInternshipFileId("");
                internshipApplyService.update(internshipApply);
                FilesUtils.deleteFile(RequestUtils.getRealPath(request) + files.getRelativePath());
                data.success().msg("删除文件成功");
            } else {
                data.fail().msg("未发现申请信息");
            }
        } catch (IOException e) {
            log.error(" delete file is exception.", e);
            data.fail().msg("删除文件异常");
        }
        return data;
    }

    /**
     * 进入实习申请入口条件
     *
     * @param internshipReleaseId 实习发布id
     * @return true or false
     */
    private ErrorBean<InternshipRelease> accessCondition(String internshipReleaseId, int studentId) {
        ErrorBean<InternshipRelease> errorBean = internshipReleaseService.basicCondition(internshipReleaseId);
        if (!errorBean.isHasError()) {
            if (!commonControllerMethodService.limitCurrentStudent(studentId)) {
                errorBean.setHasError(true);
                errorBean.setErrorMsg("您的个人信息有误");
                return errorBean;
            }

            Map<String, Object> mapData = new HashMap<>();
            InternshipRelease internshipRelease = errorBean.getData();
            boolean inTimeRange;// 在实习申请时间范围
            if (DateTimeUtils.timestampRangeDecide(internshipRelease.getStartTime(), internshipRelease.getEndTime())) {
                errorBean.setHasError(false);
                errorBean.setErrorMsg("允许填写");
                inTimeRange = true;
            } else {
                errorBean.setHasError(true);
                errorBean.setErrorMsg("不在时间范围，无法进入");
                inTimeRange = false;
            }
            if (inTimeRange) {
                Optional<Record> studentRecord = studentService.findByIdRelation(studentId);
                if (studentRecord.isPresent()) {
                    StudentBean studentBean = studentRecord.get().into(StudentBean.class);
                    mapData.put("student", studentBean);
                    Optional<Record> internshipReleaseScienceRecord = internshipReleaseScienceService.findByInternshipReleaseIdAndScienceId(internshipReleaseId, studentBean.getScienceId());
                    if (internshipReleaseScienceRecord.isPresent()) { // 判断专业
                        Optional<Record> internshipTeacherDistributionRecord = internshipTeacherDistributionService.findByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
                        if (internshipTeacherDistributionRecord.isPresent()) { // 判断指导教师
                            InternshipTeacherDistribution internshipTeacherDistribution = internshipTeacherDistributionRecord.get().into(InternshipTeacherDistribution.class);
                            mapData.put("internshipTeacherDistribution", internshipTeacherDistribution);
                            errorBean.setHasError(false);
                        } else {
                            errorBean.setHasError(true);
                            errorBean.setErrorMsg("该学生账号未分配实习指导教师");
                            return errorBean;
                        }
                    } else {
                        errorBean.setHasError(true);
                        errorBean.setErrorMsg("该学生账号所在专业不在实习范围");
                        return errorBean;
                    }
                } else {
                    errorBean.setHasError(true);
                    errorBean.setErrorMsg("未查询到学生信息");
                    return errorBean;
                }
            }
            Optional<Record> internshipApplyRecord = internshipApplyService.findByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
            if (internshipApplyRecord.isPresent()) {
                InternshipApply internshipApply = internshipApplyRecord.get().into(InternshipApply.class);
                mapData.put("internshipApply", internshipApply);
                // 状态为 5：基本信息变更填写中 或 7：单位信息变更填写中 位于这两个状态，一定是通过审核后的 无视实习时间条件 但需要判断更改时间条件
                if (internshipApply.getInternshipApplyState() == 5 || internshipApply.getInternshipApplyState() == 7) {
                    // 判断更改时间条件
                    if (DateTimeUtils.timestampRangeDecide(internshipApply.getChangeFillStartTime(), internshipApply.getChangeFillEndTime())) {
                        errorBean.setHasError(false);
                        errorBean.setErrorMsg("允许填写");
                    } else {
                        errorBean.setHasError(true);
                        errorBean.setErrorMsg("不在时间范围，无法进入");
                    }
                }
                // 状态为 3：未通过 该状态下 无视时间条件
                if (internshipApply.getInternshipApplyState() == 3) {
                    // 可直接填写
                    errorBean.setHasError(false);
                    errorBean.setErrorMsg("允许填写");
                }
                // 状态为 1：申请中；2：已通过；4：基本信息变更申请中；6：单位信息变更申请中； 则不允许进行填写 无视时间条件
                if (internshipApply.getInternshipApplyState() == 1 || internshipApply.getInternshipApplyState() == 2 ||
                        internshipApply.getInternshipApplyState() == 4 || internshipApply.getInternshipApplyState() == 6) {
                    // 不允许直接填写
                    errorBean.setHasError(true);
                    errorBean.setErrorMsg("您当前状态，不允许填写");
                }
                // 状态为 0：未提交申请
                if (internshipApply.getInternshipApplyState() == 0) {
                    if (inTimeRange) {
                        errorBean.setHasError(false);
                        errorBean.setErrorMsg("允许填写");
                    } else {
                        errorBean.setHasError(true);
                        errorBean.setErrorMsg("不在时间范围，无法进入");
                    }
                }
            }
            if (!inTimeRange && !errorBean.isHasError()) {
                Optional<Record> studentRecord = studentService.findByIdRelation(studentId);
                if (studentRecord.isPresent()) {
                    StudentBean studentBean = studentRecord.get().into(StudentBean.class);
                    mapData.put("student", studentBean);
                }
                Optional<Record> internshipTeacherDistributionRecord = internshipTeacherDistributionService.findByInternshipReleaseIdAndStudentId(internshipReleaseId, studentId);
                if (internshipTeacherDistributionRecord.isPresent()) {
                    InternshipTeacherDistribution internshipTeacherDistribution = internshipTeacherDistributionRecord.get().into(InternshipTeacherDistribution.class);
                    mapData.put("internshipTeacherDistribution", internshipTeacherDistribution);
                }
            }
            errorBean.setMapData(mapData);
        }

        return errorBean;
    }
}
