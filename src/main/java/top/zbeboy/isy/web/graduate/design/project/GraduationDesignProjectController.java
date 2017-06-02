package top.zbeboy.isy.web.graduate.design.project;

import lombok.extern.slf4j.Slf4j;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.zbeboy.isy.domain.tables.pojos.*;
import top.zbeboy.isy.domain.tables.records.BuildingRecord;
import top.zbeboy.isy.domain.tables.records.GraduationDesignPlanRecord;
import top.zbeboy.isy.service.common.CommonControllerMethodService;
import top.zbeboy.isy.service.data.BuildingService;
import top.zbeboy.isy.service.data.DepartmentService;
import top.zbeboy.isy.service.data.StaffService;
import top.zbeboy.isy.service.graduate.design.GraduationDesignPlanService;
import top.zbeboy.isy.service.graduate.design.GraduationDesignReleaseService;
import top.zbeboy.isy.service.graduate.design.GraduationDesignTeacherService;
import top.zbeboy.isy.service.platform.UsersService;
import top.zbeboy.isy.service.util.DateTimeUtils;
import top.zbeboy.isy.service.util.UUIDUtils;
import top.zbeboy.isy.web.bean.error.ErrorBean;
import top.zbeboy.isy.web.bean.graduate.design.project.GraduationDesignPlanBean;
import top.zbeboy.isy.web.util.AjaxUtils;
import top.zbeboy.isy.web.vo.graduate.design.project.GraduationDesignProjectAddVo;
import top.zbeboy.isy.web.vo.graduate.design.project.GraduationDesignProjectUpdateVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * Created by zbeboy on 2017/5/26.
 */
@Slf4j
@Controller
public class GraduationDesignProjectController {

    @Resource
    private GraduationDesignReleaseService graduationDesignReleaseService;

    @Resource
    private UsersService usersService;

    @Resource
    private StaffService staffService;

    @Resource
    private GraduationDesignTeacherService graduationDesignTeacherService;

    @Resource
    private CommonControllerMethodService commonControllerMethodService;

    @Resource
    private GraduationDesignPlanService graduationDesignPlanService;

    @Resource
    private BuildingService buildingService;

    @Resource
    private DepartmentService departmentService;

    /**
     * 毕业设计规划
     *
     * @return 毕业设计规划页面
     */
    @RequestMapping(value = "/web/menu/graduate/design/project", method = RequestMethod.GET)
    public String project() {
        return "web/graduate/design/project/design_project::#page-wrapper";
    }

    /**
     * 规划
     *
     * @return 规划页面
     */
    @RequestMapping(value = "/web/graduate/design/project/list", method = RequestMethod.GET)
    public String projectList(@RequestParam("id") String graduationDesignReleaseId, ModelMap modelMap) {
        String page;
        ErrorBean<GraduationDesignRelease> errorBean = accessCondition(graduationDesignReleaseId);
        if (!errorBean.isHasError()) {
            page = "web/graduate/design/project/design_project_list::#page-wrapper";
            modelMap.addAttribute("graduationDesignReleaseId", graduationDesignReleaseId);
        } else {
            page = commonControllerMethodService.showTip(modelMap, errorBean.getErrorMsg());
        }
        return page;
    }

    /**
     * 我的学生
     *
     * @return 我的学生页面
     */
    @RequestMapping(value = "/web/graduate/design/project/students", method = RequestMethod.GET)
    public String students(@RequestParam("id") String graduationDesignReleaseId, ModelMap modelMap) {
        String page;
        ErrorBean<GraduationDesignRelease> errorBean = accessCondition(graduationDesignReleaseId);
        if (!errorBean.isHasError()) {
            GraduationDesignRelease graduationDesignRelease = errorBean.getData();
            if (!ObjectUtils.isEmpty(graduationDesignRelease.getIsOkTeacherAdjust()) && graduationDesignRelease.getIsOkTeacherAdjust() == 1) {
                page = "web/graduate/design/project/design_project_list::#page-wrapper";
                modelMap.addAttribute("graduationDesignReleaseId", graduationDesignReleaseId);
            } else {
                page = commonControllerMethodService.showTip(modelMap, "未确认学生填报，无法操作");
            }
        } else {
            page = commonControllerMethodService.showTip(modelMap, errorBean.getErrorMsg());
        }
        return page;
    }

    /**
     * 规划添加
     *
     * @return 规划添加页面
     */
    @RequestMapping(value = "/web/graduate/design/project/list/add", method = RequestMethod.GET)
    public String projectListAdd(@RequestParam("id") String graduationDesignReleaseId, ModelMap modelMap) {
        String page;
        ErrorBean<GraduationDesignRelease> errorBean = accessCondition(graduationDesignReleaseId);
        if (!errorBean.isHasError()) {
            // 查询最近的一条件记录，时间为当前
            GraduationDesignTeacher graduationDesignTeacher = (GraduationDesignTeacher) errorBean.getMapData().get("graduationDesignTeacher");
            Record record =
                    graduationDesignPlanService.findByGraduationDesignTeacherIdAndLessThanAddTime(graduationDesignTeacher.getGraduationDesignTeacherId(), DateTimeUtils.getNow());
            GraduationDesignPlanBean graduationDesignPlan;
            if (!ObjectUtils.isEmpty(record)) {
                graduationDesignPlan = record.into(GraduationDesignPlanBean.class);
            } else {
                graduationDesignPlan = new GraduationDesignPlanBean();
            }
            page = "web/graduate/design/project/design_project_add::#page-wrapper";
            modelMap.addAttribute("graduationDesignReleaseId", graduationDesignReleaseId);
            modelMap.addAttribute("graduationDesignPlanRecently", graduationDesignPlan);
        } else {
            page = commonControllerMethodService.showTip(modelMap, errorBean.getErrorMsg());
        }
        return page;
    }

    /**
     * 规划编辑
     *
     * @return 规划添加页面
     */
    @RequestMapping(value = "/web/graduate/design/project/list/edit", method = RequestMethod.GET)
    public String projectListEdit(@RequestParam("id") String graduationDesignReleaseId,
                                  @RequestParam("graduationDesignPlanId") String graduationDesignPlanId, ModelMap modelMap) {
        String page;
        ErrorBean<GraduationDesignRelease> errorBean = accessCondition(graduationDesignReleaseId);
        if (!errorBean.isHasError()) {
            Optional<Record> graduationDesignPlanRecord = graduationDesignPlanService.findByIdRelation(graduationDesignPlanId);
            if (graduationDesignPlanRecord.isPresent()) {
                GraduationDesignPlanBean graduationDesignPlan = graduationDesignPlanRecord.get().into(GraduationDesignPlanBean.class);
                // 查询最近的一条件记录，时间为当前
                GraduationDesignTeacher graduationDesignTeacher = (GraduationDesignTeacher) errorBean.getMapData().get("graduationDesignTeacher");
                Record record =
                        graduationDesignPlanService.findByGraduationDesignTeacherIdAndLessThanAddTime(graduationDesignTeacher.getGraduationDesignTeacherId(), graduationDesignPlan.getAddTime());
                GraduationDesignPlanBean graduationDesignPlanRecently;
                if (!ObjectUtils.isEmpty(record)) {
                    graduationDesignPlanRecently = record.into(GraduationDesignPlanBean.class);
                } else {
                    graduationDesignPlanRecently = new GraduationDesignPlanBean();
                }
                page = "web/graduate/design/project/design_project_edit::#page-wrapper";
                modelMap.addAttribute("graduationDesignReleaseId", graduationDesignReleaseId);
                modelMap.addAttribute("graduationDesignPlanRecently", graduationDesignPlanRecently);
                modelMap.addAttribute("graduationDesignPlan", graduationDesignPlan);
            } else {
                page = commonControllerMethodService.showTip(modelMap, "未查询到相关信息");
            }

        } else {
            page = commonControllerMethodService.showTip(modelMap, errorBean.getErrorMsg());
        }
        return page;
    }

    /**
     * 获取列表数据
     *
     * @param graduationDesignReleaseId 毕业设计发布id
     * @return 列表数据
     */
    @RequestMapping(value = "/web/graduate/design/project/list/data", method = RequestMethod.GET)
    @ResponseBody
    public AjaxUtils<GraduationDesignPlanBean> listData(@RequestParam("id") String graduationDesignReleaseId) {
        AjaxUtils<GraduationDesignPlanBean> ajaxUtils = AjaxUtils.of();
        ErrorBean<GraduationDesignRelease> errorBean = accessCondition(graduationDesignReleaseId);
        if (!errorBean.isHasError()) {
            GraduationDesignTeacher graduationDesignTeacher = (GraduationDesignTeacher) errorBean.getMapData().get("graduationDesignTeacher");
            List<GraduationDesignPlanBean> graduationDesignPlans = new ArrayList<>();
            Result<Record> records = graduationDesignPlanService.findByGraduationDesignTeacherIdOrderByAddTime(graduationDesignTeacher.getGraduationDesignTeacherId());
            if (records.isNotEmpty()) {
                graduationDesignPlans = records.into(GraduationDesignPlanBean.class);
            }
            ajaxUtils.success().msg("获取数据成功").listData(graduationDesignPlans);
        } else {
            ajaxUtils.fail().msg(errorBean.getErrorMsg());
        }
        return ajaxUtils;
    }

    /**
     * 获取全部楼
     *
     * @param graduationDesignReleaseId 毕业设计发布id
     * @return 全部楼
     */
    @RequestMapping(value = "/web/graduate/design/project/buildings", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils<Building> buildings(@RequestParam("id") String graduationDesignReleaseId) {
        AjaxUtils<Building> ajaxUtils = AjaxUtils.of();
        ErrorBean<GraduationDesignRelease> errorBean = accessCondition(graduationDesignReleaseId);
        if (!errorBean.isHasError()) {
            List<Building> buildings = new ArrayList<>();
            Byte isDel = 0;
            Building building = new Building(0, "请选择楼", 0, isDel);
            buildings.add(building);
            GraduationDesignRelease graduationDesignRelease = errorBean.getData();
            Optional<Record> record = departmentService.findByIdRelation(graduationDesignRelease.getDepartmentId());
            if (record.isPresent()) {
                College college = record.get().into(College.class);
                Result<BuildingRecord> buildingRecords = buildingService.findByCollegeIdAndIsDel(college.getCollegeId(), isDel);
                for (BuildingRecord r : buildingRecords) {
                    Building tempBuilding = new Building(r.getBuildingId(), r.getBuildingName(), r.getCollegeId(), r.getBuildingIsDel());
                    buildings.add(tempBuilding);
                }
            }
            ajaxUtils.success().msg("获取楼数据成功！").listData(buildings);
        } else {
            ajaxUtils.fail().msg(errorBean.getErrorMsg());
        }
        return ajaxUtils;
    }

    /**
     * 保存
     *
     * @param graduationDesignProjectAddVo 数据
     * @param bindingResult                检验
     * @return true or false
     */
    @RequestMapping(value = "/web/graduate/design/project/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils save(@Valid GraduationDesignProjectAddVo graduationDesignProjectAddVo, BindingResult bindingResult) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        if (!bindingResult.hasErrors()) {
            ErrorBean<GraduationDesignRelease> errorBean = accessCondition(graduationDesignProjectAddVo.getGraduationDesignReleaseId());
            if (!errorBean.isHasError()) {
                GraduationDesignPlan graduationDesignPlan = new GraduationDesignPlan();
                GraduationDesignTeacher graduationDesignTeacher = (GraduationDesignTeacher) errorBean.getMapData().get("graduationDesignTeacher");
                graduationDesignPlan.setGraduationDesignPlanId(UUIDUtils.getUUID());
                graduationDesignPlan.setGraduationDesignTeacherId(graduationDesignTeacher.getGraduationDesignTeacherId());
                graduationDesignPlan.setScheduling(graduationDesignProjectAddVo.getScheduling());
                graduationDesignPlan.setSupervisionTime(graduationDesignProjectAddVo.getSupervisionTime());
                graduationDesignPlan.setGuideContent(graduationDesignProjectAddVo.getGuideContent());
                graduationDesignPlan.setNote(graduationDesignProjectAddVo.getNote());
                graduationDesignPlan.setSchoolroomId(graduationDesignProjectAddVo.getSchoolroomId());
                graduationDesignPlan.setAddTime(DateTimeUtils.getNow());
                graduationDesignPlanService.save(graduationDesignPlan);
                ajaxUtils.success().msg("保存成功");
            } else {
                ajaxUtils.fail().msg(errorBean.getErrorMsg());
            }
        } else {
            ajaxUtils.fail().msg("参数异常");
        }
        return ajaxUtils;
    }

    /**
     * 更新
     *
     * @param graduationDesignProjectUpdateVo 数据
     * @param bindingResult                   检验
     * @return true or false
     */
    @RequestMapping(value = "/web/graduate/design/project/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils save(@Valid GraduationDesignProjectUpdateVo graduationDesignProjectUpdateVo, BindingResult bindingResult) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        if (!bindingResult.hasErrors()) {
            ErrorBean<GraduationDesignRelease> errorBean = accessCondition(graduationDesignProjectUpdateVo.getGraduationDesignReleaseId());
            if (!errorBean.isHasError()) {
                GraduationDesignPlan graduationDesignPlan = graduationDesignPlanService.findById(graduationDesignProjectUpdateVo.getGraduationDesignPlanId());
                graduationDesignPlan.setScheduling(graduationDesignProjectUpdateVo.getScheduling());
                graduationDesignPlan.setSupervisionTime(graduationDesignProjectUpdateVo.getSupervisionTime());
                graduationDesignPlan.setGuideContent(graduationDesignProjectUpdateVo.getGuideContent());
                graduationDesignPlan.setNote(graduationDesignProjectUpdateVo.getNote());
                graduationDesignPlan.setSchoolroomId(graduationDesignProjectUpdateVo.getSchoolroomId());
                graduationDesignPlanService.update(graduationDesignPlan);
                ajaxUtils.success().msg("保存成功");
            } else {
                ajaxUtils.fail().msg(errorBean.getErrorMsg());
            }
        } else {
            ajaxUtils.fail().msg("参数异常");
        }
        return ajaxUtils;
    }

    /**
     * 进入我的学生页面判断条件
     *
     * @param graduationDesignReleaseId 毕业设计发布id
     * @return true or false
     */
    @RequestMapping(value = "/web/graduate/design/project/student/condition", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils studentCondition(@RequestParam("id") String graduationDesignReleaseId) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        ErrorBean<GraduationDesignRelease> errorBean = accessCondition(graduationDesignReleaseId);
        if (!errorBean.isHasError()) {
            GraduationDesignRelease graduationDesignRelease = errorBean.getData();
            if (!ObjectUtils.isEmpty(graduationDesignRelease.getIsOkTeacherAdjust()) && graduationDesignRelease.getIsOkTeacherAdjust() == 1) {
                ajaxUtils.success().msg("在条件范围，允许使用");
            } else {
                ajaxUtils.fail().msg("未确认学生填报，无法操作");
            }
        } else {
            ajaxUtils.fail().msg(errorBean.getErrorMsg());
        }
        return ajaxUtils;
    }

    /**
     * 进入页面判断条件
     *
     * @param graduationDesignReleaseId 毕业设计发布id
     * @return true or false
     */
    @RequestMapping(value = "/web/graduate/design/project/condition", method = RequestMethod.POST)
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
     * 进入入口条件
     *
     * @param graduationDesignReleaseId 毕业设计发布id
     * @return true or false
     */
    private ErrorBean<GraduationDesignRelease> accessCondition(String graduationDesignReleaseId) {
        ErrorBean<GraduationDesignRelease> errorBean = ErrorBean.of();
        Map<String, Object> mapData = new HashMap<>();
        GraduationDesignRelease graduationDesignRelease = graduationDesignReleaseService.findById(graduationDesignReleaseId);
        if (!ObjectUtils.isEmpty(graduationDesignRelease)) {
            errorBean.setData(graduationDesignRelease);
            if (graduationDesignRelease.getGraduationDesignIsDel() == 1) {
                errorBean.setHasError(true);
                errorBean.setErrorMsg("该毕业设计已被注销");
            } else {
                // 是否已确认毕业设计指导教师
                if (!ObjectUtils.isEmpty(graduationDesignRelease.getIsOkTeacher()) && graduationDesignRelease.getIsOkTeacher() == 1) {
                    // 是否为该次毕业设计指导教师
                    Users users = usersService.getUserFromSession();
                    Staff staff = staffService.findByUsername(users.getUsername());
                    if (!ObjectUtils.isEmpty(staff)) {
                        mapData.put("staff", staff);
                        Optional<Record> record = graduationDesignTeacherService.findByGraduationDesignReleaseIdAndStaffId(graduationDesignReleaseId, staff.getStaffId());
                        if (record.isPresent()) {
                            GraduationDesignTeacher graduationDesignTeacher = record.get().into(GraduationDesignTeacher.class);
                            mapData.put("graduationDesignTeacher", graduationDesignTeacher);
                            // 毕业时间范围
                            if (DateTimeUtils.timestampRangeDecide(graduationDesignRelease.getStartTime(), graduationDesignRelease.getEndTime())) {
                                errorBean.setHasError(false);
                            } else {
                                errorBean.setHasError(true);
                                errorBean.setErrorMsg("不在毕业设计时间范围，无法操作");
                            }
                        } else {
                            errorBean.setHasError(true);
                            errorBean.setErrorMsg("您不是该次毕业设计的指导教师");
                        }
                    } else {
                        errorBean.setHasError(true);
                        errorBean.setErrorMsg("未查询到相关教职工信息");
                    }
                } else {
                    errorBean.setHasError(true);
                    errorBean.setErrorMsg("未确认毕业设计指导教师，无法操作");
                }
            }
        } else {
            errorBean.setHasError(true);
            errorBean.setErrorMsg("未查询到相关毕业设计信息");
        }
        errorBean.setMapData(mapData);
        return errorBean;
    }
}
