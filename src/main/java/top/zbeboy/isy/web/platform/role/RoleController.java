package top.zbeboy.isy.web.platform.role;

import lombok.extern.slf4j.Slf4j;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.zbeboy.isy.config.Workbook;
import top.zbeboy.isy.domain.tables.pojos.CollegeRole;
import top.zbeboy.isy.domain.tables.pojos.Role;
import top.zbeboy.isy.domain.tables.pojos.RoleApplication;
import top.zbeboy.isy.domain.tables.pojos.Users;
import top.zbeboy.isy.domain.tables.records.RoleApplicationRecord;
import top.zbeboy.isy.domain.tables.records.RoleRecord;
import top.zbeboy.isy.service.common.CommonControllerMethodService;
import top.zbeboy.isy.service.data.CollegeRoleService;
import top.zbeboy.isy.service.data.ElasticSyncService;
import top.zbeboy.isy.service.platform.RoleApplicationService;
import top.zbeboy.isy.service.platform.RoleService;
import top.zbeboy.isy.service.platform.UsersService;
import top.zbeboy.isy.service.system.ApplicationService;
import top.zbeboy.isy.service.system.AuthoritiesService;
import top.zbeboy.isy.service.util.RandomUtils;
import top.zbeboy.isy.service.util.UUIDUtils;
import top.zbeboy.isy.web.bean.platform.role.RoleBean;
import top.zbeboy.isy.web.bean.tree.TreeBean;
import top.zbeboy.isy.web.util.AjaxUtils;
import top.zbeboy.isy.web.util.DataTablesUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static top.zbeboy.isy.domain.Tables.*;

/**
 * Created by lenovo on 2016-10-16.
 */
@Slf4j
@Controller
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private UsersService usersService;

    @Resource
    private AuthoritiesService authoritiesService;

    @Resource
    private RoleApplicationService roleApplicationService;

    @Resource
    private CollegeRoleService collegeRoleService;

    @Resource
    private CommonControllerMethodService commonControllerMethodService;

    @Resource
    private ApplicationService applicationService;

    @Resource
    private ElasticSyncService elasticSyncService;

    /**
     * 平台角色页面
     *
     * @return 页面
     */
    @RequestMapping(value = "/web/menu/platform/role", method = RequestMethod.GET)
    public String platformRole() {
        return "web/platform/role/role_data::#page-wrapper";
    }

    /**
     * datatables ajax查询数据
     *
     * @param request 请求
     * @return datatables数据
     */
    @RequestMapping(value = "/web/platform/role/data", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesUtils<RoleBean> roleDatas(HttpServletRequest request) {
        // 前台数据标题 注：要和前台标题顺序一致，获取order用
        ArrayList<String> headers = new ArrayList<>();
        headers.add("role_name");
        headers.add("school_name");
        headers.add("college_name");
        headers.add("role_en_name");
        headers.add("operator");
        RoleBean otherCondition = new RoleBean();
        otherCondition.setRoleType(2);
        DataTablesUtils<RoleBean> dataTablesUtils = new DataTablesUtils<>(request, headers);
        Result<Record> records = roleService.findAllByPage(dataTablesUtils, otherCondition);
        List<RoleBean> roleBeens = new ArrayList<>();
        if (!ObjectUtils.isEmpty(records) && records.isNotEmpty()) {
            for (Record record : records) {
                RoleBean roleBean = new RoleBean();
                roleBean.setRoleId(record.getValue(ROLE.ROLE_ID));
                roleBean.setRoleName(record.getValue(ROLE.ROLE_NAME));
                roleBean.setRoleEnName(record.getValue(ROLE.ROLE_EN_NAME));
                roleBean.setCollegeId(record.getValue(COLLEGE.COLLEGE_ID));
                roleBean.setCollegeName(record.getValue(COLLEGE.COLLEGE_NAME));
                roleBean.setSchoolId(record.getValue(SCHOOL.SCHOOL_ID));
                roleBean.setSchoolName(record.getValue(SCHOOL.SCHOOL_NAME));
                roleBeens.add(roleBean);
            }
        }
        dataTablesUtils.setData(roleBeens);
        dataTablesUtils.setiTotalRecords(roleService.countAll(otherCondition));
        dataTablesUtils.setiTotalDisplayRecords(roleService.countByCondition(dataTablesUtils, otherCondition));
        return dataTablesUtils;
    }

    /**
     * 角色数据添加
     *
     * @param modelMap 页面对象
     * @return 添加页面
     */
    @RequestMapping(value = "/web/platform/role/add", method = RequestMethod.GET)
    public String roleAdd(ModelMap modelMap) {
        commonControllerMethodService.currentUserRoleNameAndCollegeIdPageParam(modelMap);
        return "web/platform/role/role_add::#page-wrapper";
    }

    /**
     * 角色数据编辑
     *
     * @param roleId   角色id
     * @param modelMap 页面对象
     * @return 编辑页面
     */
    @RequestMapping(value = "/web/platform/role/edit", method = RequestMethod.GET)
    public String roleEdit(@RequestParam("id") String roleId, ModelMap modelMap) {
        Optional<Record> record = roleService.findByRoleIdRelation(roleId);
        RoleBean roleBean = new RoleBean();
        if (record.isPresent()) {
            Record temp = record.get();
            roleBean.setRoleId(temp.getValue(ROLE.ROLE_ID));
            roleBean.setRoleName(temp.getValue(ROLE.ROLE_NAME));
            roleBean.setRoleEnName(temp.getValue(ROLE.ROLE_EN_NAME));
            roleBean.setCollegeId(temp.getValue(COLLEGE.COLLEGE_ID));
            roleBean.setCollegeName(temp.getValue(COLLEGE.COLLEGE_NAME));
            roleBean.setSchoolId(temp.getValue(SCHOOL.SCHOOL_ID));
            roleBean.setSchoolName(temp.getValue(SCHOOL.SCHOOL_NAME));
        }
        modelMap.addAttribute("role", roleBean);
        commonControllerMethodService.currentUserRoleNameAndCollegeIdPageParam(modelMap);
        return "web/platform/role/role_edit::#page-wrapper";
    }

    /**
     * 保存时检验角色是否重复
     *
     * @param name      角色名
     * @param collegeId 院id
     * @return true 合格 false 不合格
     */
    @RequestMapping(value = "/web/platform/role/save/valid", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils saveValid(@RequestParam("roleName") String name, @RequestParam(value = "collegeId", defaultValue = "0") int collegeId) {
        String roleName = StringUtils.trimWhitespace(name);
        if (StringUtils.hasLength(roleName)) {
            if (roleService.isCurrentUserInRole(Workbook.ADMIN_AUTHORITIES)) { // 管理员
                Users users = usersService.getUserFromSession();
                Optional<Record> record = usersService.findUserSchoolInfo(users);
                collegeId = roleService.getRoleCollegeId(record);
            }
            if (collegeId > 0) {
                Result<Record> records = roleService.findByRoleNameAndCollegeId(roleName, collegeId);
                if (records.isEmpty()) {
                    return AjaxUtils.of().success().msg("角色名不重复");
                } else {
                    return AjaxUtils.of().fail().msg("角色名重复");
                }
            } else {
                Result<RoleRecord> roleRecords = roleService.findByRoleNameNotExistsCollegeRole(roleName);
                if (roleRecords.isEmpty()) {
                    return AjaxUtils.of().success().msg("角色名不重复");
                } else {
                    return AjaxUtils.of().fail().msg("角色名重复");
                }
            }
        }
        return AjaxUtils.of().fail().msg("角色名不能为空");
    }

    /**
     * 更新时检验角色名
     *
     * @param name      角色名
     * @param collegeId 院id
     * @param roleId    角色id
     * @return true 合格 false 不合格
     */
    @RequestMapping(value = "/web/platform/role/update/valid", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils updateValid(@RequestParam("roleName") String name, @RequestParam(value = "collegeId", defaultValue = "0") int collegeId,
                                 @RequestParam("roleId") String roleId) {
        String roleName = StringUtils.trimWhitespace(name);
        if (StringUtils.hasLength(roleName)) {
            if (roleService.isCurrentUserInRole(Workbook.ADMIN_AUTHORITIES)) { // 管理员
                Users users = usersService.getUserFromSession();
                Optional<Record> record = usersService.findUserSchoolInfo(users);
                collegeId = roleService.getRoleCollegeId(record);
            }
            if (collegeId > 0) {
                Result<Record> records = roleService.findByRoleNameAndCollegeIdNeRoleId(roleName, collegeId, roleId);
                if (records.isEmpty()) {
                    return AjaxUtils.of().success().msg("角色名不重复");
                } else {
                    return AjaxUtils.of().fail().msg("角色名重复");
                }
            } else {
                Result<RoleRecord> roleRecords = roleService.findByRoleNameNotExistsCollegeRoleNeRoleId(roleName, roleId);
                if (roleRecords.isEmpty()) {
                    return AjaxUtils.of().success().msg("角色名不重复");
                } else {
                    return AjaxUtils.of().fail().msg("角色名重复");
                }
            }
        }
        return AjaxUtils.of().fail().msg("角色名不能为空");
    }

    /**
     * 保存角色
     *
     * @param collegeId      院id
     * @param roleName       角色名
     * @param applicationIds 应用ids
     * @return true 保存成功 false 保存失败
     */
    @RequestMapping(value = "/web/platform/role/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils roleSave(@RequestParam(value = "collegeId", defaultValue = "0") int collegeId, @RequestParam("roleName") String roleName, String applicationIds) {
        Role role = new Role();
        String roleId = UUIDUtils.getUUID();
        role.setRoleId(roleId);
        role.setRoleName(StringUtils.trimAllWhitespace(roleName));
        role.setRoleEnName("ROLE_" + RandomUtils.generateRoleEnName().toUpperCase());
        role.setRoleType(2);
        roleService.save(role);
        if (roleService.isCurrentUserInRole(Workbook.ADMIN_AUTHORITIES)) { // 管理员
            Users users = usersService.getUserFromSession();
            Optional<Record> record = usersService.findUserSchoolInfo(users);
            collegeId = roleService.getRoleCollegeId(record);
        }
        saveOrUpdate(collegeId, applicationIds, roleId);
        return AjaxUtils.of().success().msg("保存成功");
    }

    /**
     * 更新角色
     *
     * @param roleId         角色id
     * @param collegeId      院id
     * @param roleName       角色名
     * @param applicationIds 应用ids
     * @return true 保存成功 false 保存失败
     */
    @RequestMapping(value = "/web/platform/role/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils roleUpdate(@RequestParam("roleId") String roleId, @RequestParam(value = "collegeId", defaultValue = "0") int collegeId, @RequestParam("roleName") String roleName, String applicationIds) {
        Role role = roleService.findById(roleId);
        String oldRoleName = role.getRoleName();
        role.setRoleName(StringUtils.trimAllWhitespace(roleName));
        roleService.update(role);
        // 用户可能同时更改菜单
        roleApplicationService.deleteByRoleId(roleId);
        // 当是系统角色时，可能改变这个角色到其它院下
        collegeRoleService.deleteByRoleId(roleId);
        if (roleService.isCurrentUserInRole(Workbook.ADMIN_AUTHORITIES)) { // 管理员
            Users users = usersService.getUserFromSession();
            Optional<Record> record = usersService.findUserSchoolInfo(users);
            collegeId = roleService.getRoleCollegeId(record);
        }
        saveOrUpdate(collegeId, applicationIds, roleId);
        elasticSyncService.collegeRoleNameUpdate(collegeId, oldRoleName);
        return AjaxUtils.of().success().msg("更新成功");
    }

    /**
     * 保存或更新与角色相关的表
     *
     * @param collegeId      院id
     * @param applicationIds 应用ids
     * @param roleId         角色id
     */
    private void saveOrUpdate(int collegeId, String applicationIds, String roleId) {
        roleApplicationService.batchSaveRoleApplication(applicationIds, roleId);
        if (collegeId > 0) {
            CollegeRole collegeRole = new CollegeRole(roleId, collegeId);
            collegeRoleService.save(collegeRole);
        }
    }

    /**
     * 删除角色
     *
     * @param roleId 角色id
     * @return true成功
     */
    @RequestMapping(value = "/web/platform/role/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils roleDelete(@RequestParam("roleId") String roleId) {
        Optional<Record> record = roleService.findByRoleIdRelation(roleId);
        if (record.isPresent()) {
            Record temp = record.get();
            collegeRoleService.deleteByRoleId(roleId);
            roleApplicationService.deleteByRoleId(roleId);
            authoritiesService.deleteByAuthorities(temp.getValue(ROLE.ROLE_EN_NAME));
            roleService.deleteById(roleId);
            elasticSyncService.collegeRoleNameUpdate(temp.getValue(COLLEGE.COLLEGE_ID), temp.getValue(ROLE.ROLE_NAME));
        }
        return AjaxUtils.of().success().msg("删除成功");
    }

    /**
     * 获取角色id 下的 应用id
     *
     * @param roleId 角色id
     * @return 应用
     */
    @RequestMapping(value = "/web/platform/role/application/data", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils<RoleApplication> roleApplicationData(@RequestParam("roleId") String roleId) {
        AjaxUtils<RoleApplication> ajaxUtils = AjaxUtils.of();
        Result<RoleApplicationRecord> roleApplicationRecords = roleApplicationService.findByRoleId(roleId);
        List<RoleApplication> roleApplications = new ArrayList<>();
        if (roleApplicationRecords.isNotEmpty()) {
            roleApplications = roleApplicationRecords.into(RoleApplication.class);
        }
        return ajaxUtils.success().listData(roleApplications);
    }

    /**
     * 数据json
     *
     * @param collegeId 院id
     * @return json
     */
    @RequestMapping(value = "/web/platform/role/application/json", method = RequestMethod.GET)
    @ResponseBody
    public AjaxUtils<TreeBean> applicationJson(@RequestParam("collegeId") int collegeId) {
        AjaxUtils<TreeBean> ajaxUtils = AjaxUtils.of();
        List<TreeBean> treeBeens = applicationService.getApplicationJsonByCollegeId("0", collegeId);
        return ajaxUtils.success().listData(treeBeens);
    }
}
