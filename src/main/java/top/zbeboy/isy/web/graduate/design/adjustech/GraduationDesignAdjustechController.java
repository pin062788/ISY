package top.zbeboy.isy.web.graduate.design.adjustech;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.zbeboy.isy.service.cache.CacheBook;
import top.zbeboy.isy.domain.tables.pojos.GraduationDesignRelease;
import top.zbeboy.isy.domain.tables.pojos.GraduationDesignTeacher;
import top.zbeboy.isy.domain.tables.pojos.GraduationDesignTutor;
import top.zbeboy.isy.service.common.CommonControllerMethodService;
import top.zbeboy.isy.service.graduate.design.GraduationDesignHopeTutorService;
import top.zbeboy.isy.service.graduate.design.GraduationDesignReleaseService;
import top.zbeboy.isy.service.graduate.design.GraduationDesignTeacherService;
import top.zbeboy.isy.service.graduate.design.GraduationDesignTutorService;
import top.zbeboy.isy.service.util.DateTimeUtils;
import top.zbeboy.isy.service.util.UUIDUtils;
import top.zbeboy.isy.web.bean.data.student.StudentBean;
import top.zbeboy.isy.web.bean.error.ErrorBean;
import top.zbeboy.isy.web.bean.graduate.design.pharmtech.GraduationDesignHopeTutorBean;
import top.zbeboy.isy.web.bean.graduate.design.pharmtech.GraduationDesignTutorBean;
import top.zbeboy.isy.web.bean.graduate.design.release.GraduationDesignReleaseBean;
import top.zbeboy.isy.web.bean.graduate.design.teacher.GraduationDesignTeacherBean;
import top.zbeboy.isy.web.util.AjaxUtils;
import top.zbeboy.isy.web.util.DataTablesUtils;
import top.zbeboy.isy.web.util.PaginationUtils;
import top.zbeboy.isy.web.util.SmallPropsUtils;
import top.zbeboy.isy.web.vo.graduate.design.adjustech.GraduationDesignTutorAddVo;
import top.zbeboy.isy.web.vo.graduate.design.adjustech.GraduationDesignTutorUpdateVo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by lenovo on 2017-05-21.
 */
@Slf4j
@Controller
public class GraduationDesignAdjustechController {

    @Resource
    private CommonControllerMethodService commonControllerMethodService;

    @Resource
    private GraduationDesignReleaseService graduationDesignReleaseService;

    @Resource
    private GraduationDesignTutorService graduationDesignTutorService;

    @Resource
    private GraduationDesignTeacherService graduationDesignTeacherService;

    @Resource
    private GraduationDesignHopeTutorService graduationDesignHopeTutorService;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> stringValueOperations;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, List<GraduationDesignTeacherBean>> stringListValueOperations;

    @Resource
    private StringRedisTemplate template;

    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOperations;

    /**
     * 调整填报教师
     *
     * @return 调整填报教师页面
     */
    @RequestMapping(value = "/web/menu/graduate/design/adjustech", method = RequestMethod.GET)
    public String adjustech() {
        return "web/graduate/design/adjustech/design_adjustech::#page-wrapper";
    }

    /**
     * 填报情况页面
     *
     * @param graduationDesignReleaseId 发布id
     * @param modelMap                  页面对象
     * @return 页面
     */
    @RequestMapping(value = "/web/graduate/design/adjustech/apply", method = RequestMethod.GET)
    public String adjustechApply(@RequestParam("id") String graduationDesignReleaseId, ModelMap modelMap) {
        modelMap.addAttribute("graduationDesignReleaseId", graduationDesignReleaseId);
        return "web/graduate/design/adjustech/design_adjustech_apply::#page-wrapper";
    }

    /**
     * 调整
     *
     * @return 调整页面
     */
    @RequestMapping(value = "/web/graduate/design/adjustech/adjust", method = RequestMethod.GET)
    public String adjust(@RequestParam("id") String graduationDesignReleaseId, ModelMap modelMap) {
        modelMap.addAttribute("graduationDesignReleaseId", graduationDesignReleaseId);
        return "web/graduate/design/adjustech/design_adjustech_adjust::#page-wrapper";
    }

    /**
     * 已填报学生
     *
     * @return 已填报学生页面
     */
    @RequestMapping(value = "/web/graduate/design/adjustech/student/submit", method = RequestMethod.GET)
    public String fill(@RequestParam("id") String graduationDesignReleaseId, ModelMap modelMap) {
        modelMap.addAttribute("graduationDesignReleaseId", graduationDesignReleaseId);
        return "web/graduate/design/adjustech/design_adjustech_submit::#page-wrapper";
    }

    /**
     * 未填报学生
     *
     * @return 已填报学生页面
     */
    @RequestMapping(value = "/web/graduate/design/adjustech/student/unsubmit", method = RequestMethod.GET)
    public String notFill(@RequestParam("id") String graduationDesignReleaseId, ModelMap modelMap) {
        modelMap.addAttribute("graduationDesignReleaseId", graduationDesignReleaseId);
        return "web/graduate/design/adjustech/design_adjustech_unsubmit::#page-wrapper";
    }

    /**
     * 填报情况教师数据
     *
     * @param graduationDesignReleaseId 发布id
     * @return 数据
     */
    @RequestMapping(value = "/web/graduate/design/adjustech/apply/data", method = RequestMethod.GET)
    @ResponseBody
    public AjaxUtils<GraduationDesignTeacherBean> applyData(@RequestParam("id") String graduationDesignReleaseId) {
        AjaxUtils<GraduationDesignTeacherBean> ajaxUtils = AjaxUtils.of();
        List<GraduationDesignTeacherBean> graduationDesignTeacherBeens;
        String cacheKey = CacheBook.GRADUATION_DESIGN_TEACHER_STUDENT + graduationDesignReleaseId;
        // 从缓存中得到列表
        if (stringListValueOperations.getOperations().hasKey(cacheKey)) {
            graduationDesignTeacherBeens = stringListValueOperations.get(cacheKey);
        } else {
            graduationDesignTeacherBeens = new ArrayList<>();
        }
        // 处理列表
        if (!ObjectUtils.isEmpty(graduationDesignTeacherBeens) && graduationDesignTeacherBeens.size() > 0) {
            for (GraduationDesignTeacherBean designTeacherBean : graduationDesignTeacherBeens) {
                // 装填剩余人数
                String studentCountKey = CacheBook.GRADUATION_DESIGN_TEACHER_STUDENT_COUNT + designTeacherBean.getGraduationDesignTeacherId();
                if (template.hasKey(studentCountKey)) {
                    ValueOperations<String, String> ops = this.template.opsForValue();
                    designTeacherBean.setResidueCount(NumberUtils.toInt(ops.get(studentCountKey)));
                }
            }
        }
        ajaxUtils.success().msg("获取数据成功").listData(graduationDesignTeacherBeens);
        return ajaxUtils;
    }

    /**
     * 填报教师数据
     *
     * @param paginationUtils 分页工具
     * @return 数据
     */
    @RequestMapping(value = "/web/graduate/design/adjustech/data", method = RequestMethod.GET)
    @ResponseBody
    public AjaxUtils<GraduationDesignReleaseBean> adjustechDatas(PaginationUtils paginationUtils) {
        AjaxUtils<GraduationDesignReleaseBean> ajaxUtils = AjaxUtils.of();
        Byte isDel = 0;
        GraduationDesignReleaseBean graduationDesignReleaseBean = new GraduationDesignReleaseBean();
        graduationDesignReleaseBean.setGraduationDesignIsDel(isDel);
        Map<String, Integer> commonData = commonControllerMethodService.accessRoleCondition();
        graduationDesignReleaseBean.setDepartmentId(StringUtils.isEmpty(commonData.get("departmentId")) ? -1 : commonData.get("departmentId"));
        graduationDesignReleaseBean.setCollegeId(StringUtils.isEmpty(commonData.get("collegeId")) ? -1 : commonData.get("collegeId"));
        Result<Record> records = graduationDesignReleaseService.findAllByPage(paginationUtils, graduationDesignReleaseBean);
        List<GraduationDesignReleaseBean> graduationDesignReleaseBeens = graduationDesignReleaseService.dealData(paginationUtils, records, graduationDesignReleaseBean);
        graduationDesignReleaseBeens.forEach(graduationDesignRelease -> {
                    graduationDesignRelease.setStudentNotFillCount(graduationDesignTutorService.countNotFillStudent(graduationDesignRelease));
                    graduationDesignRelease.setStudentFillCount(graduationDesignTutorService.countFillStudent(graduationDesignRelease));
                    String syncTimeKey = CacheBook.GRADUATION_DESIGN_ADJUSTECH_SYNC_TIME + graduationDesignRelease.getGraduationDesignReleaseId();
                    if (stringValueOperations.getOperations().hasKey(syncTimeKey)) {
                        graduationDesignRelease.setHasSyncDate(true);
                        graduationDesignRelease.setSyncDate(stringValueOperations.get(syncTimeKey));
                    } else {
                        graduationDesignRelease.setHasSyncDate(false);
                    }
                }
        );
        return ajaxUtils.success().msg("获取数据成功").listData(graduationDesignReleaseBeens).paginationUtils(paginationUtils);
    }

    /**
     * 同步数据
     *
     * @param graduationDesignReleaseId 毕业发布id
     * @return true or false
     */
    @RequestMapping(value = "/web/graduate/design/adjustech/sync/data", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils syncData(@RequestParam("id") String graduationDesignReleaseId) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        ErrorBean<GraduationDesignRelease> errorBean = accessCondition(graduationDesignReleaseId);
        if (!errorBean.isHasError()) {
            GraduationDesignRelease graduationDesignRelease = errorBean.getData();
            // 仅允许在填报时间内同步，若超出填报时间，缓存 KEY 有可能失效，同时在非填报时间内做的调整，因同步将会被清空
            if (DateTimeUtils.timestampRangeDecide(graduationDesignRelease.getFillTeacherStartTime(), graduationDesignRelease.getFillTeacherEndTime())) {
                // step 1. 检查同步时间
                String syncTimeKey = CacheBook.GRADUATION_DESIGN_ADJUSTECH_SYNC_TIME + graduationDesignReleaseId;
                if (!stringValueOperations.getOperations().hasKey(syncTimeKey)) {
                    // 更新剩余人数到指导教师表 ， 并且删除学生指导教师表中的关联数据
                    List<GraduationDesignTeacher> graduationDesignTeachers = graduationDesignTeacherService.findByGraduationDesignReleaseId(graduationDesignReleaseId);
                    ValueOperations<String, String> ops = this.template.opsForValue();
                    graduationDesignTeachers.forEach(graduationDesignTeacher -> {
                                String studentCountKey = CacheBook.GRADUATION_DESIGN_TEACHER_STUDENT_COUNT + graduationDesignTeacher.getGraduationDesignTeacherId();
                                if (template.hasKey(studentCountKey)) {
                                    graduationDesignTeacher.setResidue(NumberUtils.toInt(ops.get(studentCountKey)));
                                }
                                graduationDesignTutorService.deleteByGraduationDesignTeacherId(graduationDesignTeacher.getGraduationDesignTeacherId());
                            }
                    );
                    // 更新
                    graduationDesignTeacherService.update(graduationDesignTeachers);

                    // 同步关联数据
                    // step 2. 先取所有学生key
                    String listKey = CacheBook.GRADUATION_DESIGN_PHARMTECH_STUDENT_LIST + graduationDesignReleaseId;
                    List<String> keys = listOperations.range(listKey, 0, listOperations.size(listKey));
                    for (String key : keys) {
                        if (stringValueOperations.getOperations().hasKey(key)) {
                            String str = stringValueOperations.get(key);
                            if (StringUtils.hasLength(str)) {
                                String[] arr = str.split(",");
                                if (arr.length >= 2 && !arr[0].equals("-1")) {
                                    // step 3. 存储关联信息
                                    GraduationDesignTutor graduationDesignTutor = new GraduationDesignTutor();
                                    graduationDesignTutor.setGraduationDesignTutorId(UUIDUtils.getUUID());
                                    graduationDesignTutor.setGraduationDesignTeacherId(arr[0]);
                                    graduationDesignTutor.setStudentId(NumberUtils.toInt(arr[1]));
                                    graduationDesignTutorService.save(graduationDesignTutor);
                                }
                            }
                        }
                    }
                    DateTime dateTime = DateTime.now();
                    DateTime s = dateTime.plusHours(4);
                    stringValueOperations.set(syncTimeKey, DateTimeUtils.formatDate(s.toDate()), CacheBook.EXPIRES_HOURS, TimeUnit.HOURS);
                    ajaxUtils.success().msg("同步完成");
                } else {
                    ajaxUtils.fail().msg("请等到 " + stringValueOperations.get(syncTimeKey) + " 后再进行同步");
                }
            } else {
                ajaxUtils.fail().msg("不在填报时间范围，无法进行操作");
            }
        } else {
            ajaxUtils.fail().msg(errorBean.getErrorMsg());
        }
        return ajaxUtils;
    }

    /**
     * 获取教师数据
     *
     * @param graduationDesignReleaseId 毕业发布id
     * @return 数据
     */
    @RequestMapping(value = "/web/graduate/design/adjustech/teacher/data", method = RequestMethod.GET)
    @ResponseBody
    public AjaxUtils<GraduationDesignTeacherBean> adjustTeacherData(@RequestParam("id") String graduationDesignReleaseId) {
        AjaxUtils<GraduationDesignTeacherBean> ajaxUtils = AjaxUtils.of();
        List<GraduationDesignTeacherBean> graduationDesignTeacherBeens =
                graduationDesignTeacherService.findByGraduationDesignReleaseIdRelationForStaff(graduationDesignReleaseId);
        return ajaxUtils.success().msg("获取数据成功").listData(graduationDesignTeacherBeens);
    }

    /**
     * 获取学生数据
     *
     * @param graduationDesignTeacherId 毕业指导教师id
     * @return 数据
     */
    @RequestMapping(value = "/web/graduate/design/adjustech/student/data", method = RequestMethod.GET)
    @ResponseBody
    public AjaxUtils<GraduationDesignTutorBean> adjustStudentData(@RequestParam("graduationDesignTeacherId") String graduationDesignTeacherId) {
        AjaxUtils<GraduationDesignTutorBean> ajaxUtils = AjaxUtils.of();
        List<GraduationDesignTutorBean> graduationDesignTutorBeens = new ArrayList<>();
        Result<Record> records =
                graduationDesignTutorService.findByGraduationDesignTeacherIdRelationForStudent(graduationDesignTeacherId);
        if (records.isNotEmpty()) {
            graduationDesignTutorBeens = records.into(GraduationDesignTutorBean.class);
        }
        return ajaxUtils.success().msg("获取数据成功").listData(graduationDesignTutorBeens);
    }

    /**
     * 调整教师数据
     *
     * @param graduationDesignReleaseId 毕业发布id
     * @param graduationDesignTeacherId 毕业指导教师id
     * @return 数据
     */
    @RequestMapping(value = "/web/graduate/design/adjustech/teachers", method = RequestMethod.GET)
    @ResponseBody
    public AjaxUtils<GraduationDesignTeacherBean> adjustTeachers(@RequestParam("id") String graduationDesignReleaseId,
                                                                 @RequestParam("graduationDesignTeacherId") String graduationDesignTeacherId) {
        AjaxUtils<GraduationDesignTeacherBean> ajaxUtils = AjaxUtils.of();
        ErrorBean<GraduationDesignRelease> errorBean = accessCondition(graduationDesignReleaseId);
        if (!errorBean.isHasError()) {
            GraduationDesignRelease graduationDesignRelease = errorBean.getData();
            // 仅允许在填报时间之后调整
            if (DateTimeUtils.timestampAfterDecide(graduationDesignRelease.getFillTeacherEndTime())) {
                List<GraduationDesignTeacherBean> graduationDesignTeacherBeens =
                        graduationDesignTeacherService.findByGraduationDesignReleaseIdRelationForStaff(graduationDesignReleaseId);
                if (!graduationDesignTeacherId.equals("-1")) {
                    graduationDesignTeacherBeens.forEach(graduationDesignTeacherBean -> {
                        if (graduationDesignTeacherId.equals(graduationDesignTeacherBean.getGraduationDesignTeacherId())) {
                            graduationDesignTeacherBean.setSelected(true);
                        }
                    });
                }
                ajaxUtils.success().msg("获取数据成功").listData(graduationDesignTeacherBeens);
            } else {
                ajaxUtils.fail().msg("请在填报时间结束后操作");
            }
        } else {
            ajaxUtils.fail().msg(errorBean.getErrorMsg());
        }
        return ajaxUtils;
    }

    /**
     * 获取已填报学生数据
     *
     * @param request 请求
     * @return 已填报学生数据
     */
    @RequestMapping(value = "/web/graduate/design/adjustech/student/submit/data", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesUtils<GraduationDesignTutorBean> fillData(HttpServletRequest request) {
        // 前台数据标题 注：要和前台标题顺序一致，获取order用
        List<String> headers = new ArrayList<>();
        headers.add("select");
        headers.add("student_name");
        headers.add("student_number");
        headers.add("organize_name");
        headers.add("staff_name");
        headers.add("operator");
        DataTablesUtils<GraduationDesignTutorBean> dataTablesUtils = new DataTablesUtils<>(request, headers);
        GraduationDesignTutorBean condition = new GraduationDesignTutorBean();
        String graduationDesignReleaseId = request.getParameter("graduationDesignReleaseId");
        if (!ObjectUtils.isEmpty(graduationDesignReleaseId)) {
            condition.setGraduationDesignReleaseId(request.getParameter("graduationDesignReleaseId"));
            List<GraduationDesignTutorBean> graduationDesignTutorBeens = graduationDesignTutorService.findAllFillByPage(dataTablesUtils, condition);
            dataTablesUtils.setData(graduationDesignTutorBeens);
            dataTablesUtils.setiTotalRecords(graduationDesignTutorService.countAllFill(condition));
            dataTablesUtils.setiTotalDisplayRecords(graduationDesignTutorService.countFillByCondition(dataTablesUtils, condition));
        }
        return dataTablesUtils;
    }

    /**
     * 获取未填报学生数据
     *
     * @param request 请求
     * @return 已填报学生数据
     */
    @RequestMapping(value = "/web/graduate/design/adjustech/student/unsubmit/data", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesUtils<StudentBean> notFillData(HttpServletRequest request) {
        // 前台数据标题 注：要和前台标题顺序一致，获取order用
        List<String> headers = new ArrayList<>();
        headers.add("select");
        headers.add("student_name");
        headers.add("student_number");
        headers.add("organize_name");
        headers.add("operator");
        DataTablesUtils<StudentBean> dataTablesUtils = new DataTablesUtils<>(request, headers);
        String graduationDesignReleaseId = request.getParameter("graduationDesignReleaseId");
        if (!ObjectUtils.isEmpty(graduationDesignReleaseId)) {
            GraduationDesignRelease condition = graduationDesignReleaseService.findById(graduationDesignReleaseId);
            List<StudentBean> studentBeens = new ArrayList<>();
            Result<Record> records = graduationDesignTutorService.findAllNotFillByPage(dataTablesUtils, condition);
            if (records.isNotEmpty()) {
                studentBeens = records.into(StudentBean.class);
            }
            dataTablesUtils.setData(studentBeens);
            dataTablesUtils.setiTotalRecords(graduationDesignTutorService.countAllNotFill(condition));
            dataTablesUtils.setiTotalDisplayRecords(graduationDesignTutorService.countNotFillByCondition(dataTablesUtils, condition));
        }
        return dataTablesUtils;
    }

    /**
     * 查询学生志愿信息
     *
     * @param graduationDesignTutorId 毕业设计教师与学生关联id
     * @return 数据
     */
    @RequestMapping(value = "/web/graduate/design/adjustech/student/wish", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils<GraduationDesignHopeTutorBean> studentWish(@RequestParam("id") String graduationDesignReleaseId,
                                                                @RequestParam("graduationDesignTutorId") String graduationDesignTutorId) {
        AjaxUtils<GraduationDesignHopeTutorBean> ajaxUtils = AjaxUtils.of();
        GraduationDesignTutor graduationDesignTutor = graduationDesignTutorService.findById(graduationDesignTutorId);
        if (!ObjectUtils.isEmpty(graduationDesignTutor)) {
            Result<Record> records = graduationDesignHopeTutorService.findByStudentIdAndGraduationDesignReleaseIdRelationForStaff(graduationDesignTutor.getStudentId(), graduationDesignReleaseId);
            List<GraduationDesignHopeTutorBean> graduationDesignHopeTutorBeens = new ArrayList<>();
            if (records.isNotEmpty()) {
                graduationDesignHopeTutorBeens = records.into(GraduationDesignHopeTutorBean.class);
            }
            ajaxUtils.success().msg("获取数据成功").listData(graduationDesignHopeTutorBeens);
        } else {
            ajaxUtils.fail().msg("未查询到相关信息");
        }
        return ajaxUtils;
    }

    /**
     * 更新
     *
     * @param graduationDesignTutorUpdateVo 页面数据
     * @param bindingResult                 检验
     * @return true or false
     */
    @RequestMapping(value = "/web/graduate/design/adjustech/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils update(@Valid GraduationDesignTutorUpdateVo graduationDesignTutorUpdateVo, BindingResult bindingResult) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        if (!bindingResult.hasErrors()) {
            ErrorBean<GraduationDesignRelease> errorBean = accessCondition(graduationDesignTutorUpdateVo.getGraduationDesignReleaseId());
            if (!errorBean.isHasError()) {
                GraduationDesignRelease graduationDesignRelease = errorBean.getData();
                // 仅允许在填报时间之后调整
                if (DateTimeUtils.timestampAfterDecide(graduationDesignRelease.getFillTeacherEndTime())) {
                    List<String> graduationDesignTutorIds = SmallPropsUtils.StringIdsToStringList(graduationDesignTutorUpdateVo.getGraduationDesignTutorId());
                    graduationDesignTutorIds.forEach(graduationDesignTutorId -> {
                        GraduationDesignTutor graduationDesignTutor = graduationDesignTutorService.findById(graduationDesignTutorId);
                        graduationDesignTutor.setGraduationDesignTeacherId(graduationDesignTutorUpdateVo.getGraduationDesignTeacherId());
                        graduationDesignTutorService.update(graduationDesignTutor);
                    });
                    ajaxUtils.success().msg("保存成功");
                } else {
                    ajaxUtils.fail().msg("请在填报时间结束后操作");
                }
            } else {
                ajaxUtils.fail().msg(errorBean.getErrorMsg());
            }
        } else {
            ajaxUtils.fail().msg("参数异常");
        }
        return ajaxUtils;
    }

    /**
     * 保存
     *
     * @param graduationDesignTutorAddVo 页面数据
     * @param bindingResult              检验
     * @return true or false
     */
    @RequestMapping(value = "/web/graduate/design/adjustech/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils save(@Valid GraduationDesignTutorAddVo graduationDesignTutorAddVo, BindingResult bindingResult) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        if (!bindingResult.hasErrors()) {
            ErrorBean<GraduationDesignRelease> errorBean = accessCondition(graduationDesignTutorAddVo.getGraduationDesignReleaseId());
            if (!errorBean.isHasError()) {
                GraduationDesignRelease graduationDesignRelease = errorBean.getData();
                // 仅允许在填报时间之后调整
                if (DateTimeUtils.timestampAfterDecide(graduationDesignRelease.getFillTeacherEndTime())) {
                    if (SmallPropsUtils.StringIdsIsNumber(graduationDesignTutorAddVo.getStudentId())) {
                        List<Integer> studentIds = SmallPropsUtils.StringIdsToList(graduationDesignTutorAddVo.getStudentId());
                        studentIds.forEach(studentId -> {
                            GraduationDesignTutor graduationDesignTutor = new GraduationDesignTutor();
                            graduationDesignTutor.setGraduationDesignTutorId(UUIDUtils.getUUID());
                            graduationDesignTutor.setStudentId(studentId);
                            graduationDesignTutor.setGraduationDesignTeacherId(graduationDesignTutorAddVo.getGraduationDesignTeacherId());
                            graduationDesignTutorService.save(graduationDesignTutor);
                        });
                        ajaxUtils.success().msg("保存成功");
                    } else {
                        ajaxUtils.fail().msg("保存失败");
                    }
                } else {
                    ajaxUtils.fail().msg("请在填报时间结束后操作");
                }
            } else {
                ajaxUtils.fail().msg(errorBean.getErrorMsg());
            }
        } else {
            ajaxUtils.fail().msg("参数异常");
        }
        return ajaxUtils;
    }

    /**
     * 删除
     *
     * @param graduationDesignReleaseId 毕业发布id
     * @param graduationDesignTutorIds  关联id
     * @return true or false
     */
    @RequestMapping(value = "/web/graduate/design/adjustech/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils delete(@RequestParam("id") String graduationDesignReleaseId, String graduationDesignTutorIds) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        ErrorBean<GraduationDesignRelease> errorBean = accessCondition(graduationDesignReleaseId);
        if (!errorBean.isHasError()) {
            GraduationDesignRelease graduationDesignRelease = errorBean.getData();
            // 仅允许在填报时间之后调整
            if (DateTimeUtils.timestampAfterDecide(graduationDesignRelease.getFillTeacherEndTime())) {
                graduationDesignTutorService.deleteByIds(SmallPropsUtils.StringIdsToStringList(graduationDesignTutorIds));
                ajaxUtils.success().msg("删除成功");
            } else {
                ajaxUtils.fail().msg("请在填报时间结束后操作");
            }
        } else {
            ajaxUtils.fail().msg(errorBean.getErrorMsg());
        }
        return ajaxUtils;
    }

    /**
     * 确认调整
     *
     * @param graduationDesignReleaseId 毕业设计发布id
     * @return true or false
     */
    @RequestMapping(value = "/web/graduate/design/adjustech/ok", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils adjustechOk(@RequestParam("id") String graduationDesignReleaseId) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        GraduationDesignRelease graduationDesignRelease = graduationDesignReleaseService.findById(graduationDesignReleaseId);
        if (!ObjectUtils.isEmpty(graduationDesignRelease)) {
            if (ObjectUtils.isEmpty(graduationDesignRelease.getGraduationDesignIsDel()) || graduationDesignRelease.getGraduationDesignIsDel() != 1) {
                // 毕业时间范围
                if (DateTimeUtils.timestampRangeDecide(graduationDesignRelease.getStartTime(), graduationDesignRelease.getEndTime())) {
                    // 仅允许在填报时间之后调整
                    if (DateTimeUtils.timestampAfterDecide(graduationDesignRelease.getFillTeacherEndTime())) {
                        // 是否已确认
                        if (!ObjectUtils.isEmpty(graduationDesignRelease.getIsOkTeacher()) && graduationDesignRelease.getIsOkTeacher() == 1) {
                            // 是否已确认调整
                            if (!ObjectUtils.isEmpty(graduationDesignRelease.getIsOkTeacherAdjust()) && graduationDesignRelease.getIsOkTeacherAdjust() == 1) {
                                ajaxUtils.fail().msg("已确认调整");
                            } else {
                                Byte b = 1;
                                graduationDesignRelease.setIsOkTeacherAdjust(b);
                                graduationDesignReleaseService.update(graduationDesignRelease);
                                ajaxUtils.success().msg("确认调整成功");
                            }
                        } else {
                            ajaxUtils.fail().msg("未确认毕业设计指导教师，无法进行操作");
                        }
                    } else {
                        ajaxUtils.fail().msg("请在填报时间结束后操作");
                    }
                } else {
                    ajaxUtils.fail().msg("不在毕业设计时间范围，无法操作");
                }
            } else {
                ajaxUtils.fail().msg("该毕业设计已被注销");
            }
        } else {
            ajaxUtils.fail().msg("未查询到相关毕业设计信息");
        }
        return ajaxUtils;
    }

    /**
     * 进入页面判断条件
     *
     * @param graduationDesignReleaseId 毕业设计发布id
     * @return true or false
     */
    @RequestMapping(value = "/web/graduate/design/adjustech/condition", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils canUse(@RequestParam("id") String graduationDesignReleaseId) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        ErrorBean<GraduationDesignRelease> errorBean = accessCondition(graduationDesignReleaseId);
        if (!errorBean.isHasError()) {
            ajaxUtils.success().msg("在条件范围，允许使用");
        } else {
            ajaxUtils.fail().msg(errorBean.getErrorMsg());
        }
        return ajaxUtils;
    }

    /**
     * 进入调整填报教师入口条件
     *
     * @param graduationDesignReleaseId 毕业设计发布id
     * @return true or false
     */
    private ErrorBean<GraduationDesignRelease> accessCondition(String graduationDesignReleaseId) {
        ErrorBean<GraduationDesignRelease> errorBean = graduationDesignReleaseService.basicCondition(graduationDesignReleaseId);
        if (!errorBean.isHasError()) {
            GraduationDesignRelease graduationDesignRelease = errorBean.getData();
            // 毕业时间范围
            if (DateTimeUtils.timestampRangeDecide(graduationDesignRelease.getStartTime(), graduationDesignRelease.getEndTime())) {
                // 是否已确认
                if (!ObjectUtils.isEmpty(graduationDesignRelease.getIsOkTeacher()) && graduationDesignRelease.getIsOkTeacher() == 1) {
                    // 是否已确认调整
                    if (!ObjectUtils.isEmpty(graduationDesignRelease.getIsOkTeacherAdjust()) && graduationDesignRelease.getIsOkTeacherAdjust() == 1) {
                        errorBean.setHasError(true);
                        errorBean.setErrorMsg("已确认毕业设计指导教师调整，无法进行操作");
                    } else {
                        errorBean.setHasError(false);
                    }
                } else {
                    errorBean.setHasError(true);
                    errorBean.setErrorMsg("未确认毕业设计指导教师，无法进行操作");
                }
            } else {
                errorBean.setHasError(true);
                errorBean.setErrorMsg("不在毕业设计时间范围，无法操作");
            }
        }
        return errorBean;
    }
}
