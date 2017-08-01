package top.zbeboy.isy.web.graduate.design.manifest;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.Record;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.zbeboy.isy.config.Workbook;
import top.zbeboy.isy.domain.tables.pojos.Staff;
import top.zbeboy.isy.domain.tables.pojos.Users;
import top.zbeboy.isy.domain.tables.pojos.UsersType;
import top.zbeboy.isy.service.cache.CacheManageService;
import top.zbeboy.isy.service.common.CommonControllerMethodService;
import top.zbeboy.isy.service.data.StaffService;
import top.zbeboy.isy.service.graduate.design.GraduationDesignDeclareService;
import top.zbeboy.isy.service.graduate.design.GraduationDesignReleaseService;
import top.zbeboy.isy.service.graduate.design.GraduationDesignTeacherService;
import top.zbeboy.isy.service.platform.RoleService;
import top.zbeboy.isy.service.platform.UsersService;
import top.zbeboy.isy.service.platform.UsersTypeService;
import top.zbeboy.isy.web.bean.graduate.design.declare.GraduationDesignDeclareBean;
import top.zbeboy.isy.web.util.DataTablesUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by zbeboy on 2017/8/1.
 */
@Slf4j
@Controller
public class GraduationDesignManifestController {

    @Resource
    private GraduationDesignReleaseService graduationDesignReleaseService;

    @Resource
    private CommonControllerMethodService commonControllerMethodService;

    @Resource
    private GraduationDesignTeacherService graduationDesignTeacherService;

    @Resource
    private GraduationDesignDeclareService graduationDesignDeclareService;

    @Resource
    private UsersService usersService;

    @Resource
    private UsersTypeService usersTypeService;

    @Resource
    private CacheManageService cacheManageService;

    @Resource
    private RoleService roleService;

    @Resource
    private StaffService staffService;

    /**
     * 毕业设计清单
     *
     * @return 毕业设计清单页面
     */
    @RequestMapping(value = "/web/menu/graduate/design/manifest", method = RequestMethod.GET)
    public String manifest() {
        return "web/graduate/design/manifest/design_manifest::#page-wrapper";
    }

    /**
     * 列表
     *
     * @param graduationDesignReleaseId 毕业设计发布id
     * @param modelMap                  页面对象
     * @return 页面
     */
    @RequestMapping(value = "/web/graduate/design/manifest/list", method = RequestMethod.GET)
    public String list(@RequestParam("id") String graduationDesignReleaseId, ModelMap modelMap) {
        String page;
        Users users = usersService.getUserFromSession();
        boolean hasValue = false;
        if (usersTypeService.isCurrentUsersTypeName(Workbook.STAFF_USERS_TYPE)) {
            Staff staff = staffService.findByUsername(users.getUsername());
            if (!ObjectUtils.isEmpty(staff)) {
                Optional<Record> staffRecord = graduationDesignTeacherService.findByGraduationDesignReleaseIdAndStaffId(graduationDesignReleaseId, staff.getStaffId());
                if (staffRecord.isPresent()) {
                    modelMap.addAttribute("staffId", staff.getStaffId());
                    hasValue = true;
                }
            }
        }
        if (!hasValue) {
            modelMap.addAttribute("staffId", 0);
        }
        UsersType usersType = cacheManageService.findByUsersTypeId(users.getUsersTypeId());
        modelMap.addAttribute("usersTypeName", usersType.getUsersTypeName());
        if (roleService.isCurrentUserInRole(Workbook.SYSTEM_AUTHORITIES)) {
            modelMap.addAttribute("currentUserRoleName", Workbook.SYSTEM_ROLE_NAME);
        } else if (roleService.isCurrentUserInRole(Workbook.ADMIN_AUTHORITIES)) {
            modelMap.addAttribute("currentUserRoleName", Workbook.ADMIN_ROLE_NAME);
        }
        modelMap.addAttribute("graduationDesignReleaseId", graduationDesignReleaseId);
        page = "web/graduate/design/manifest/design_manifest_list::#page-wrapper";
        return page;
    }

    /**
     * 清单数据
     *
     * @param request 请求
     * @return 数据
     */
    @RequestMapping(value = "/web/graduate/design/manifest/list/data", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesUtils<GraduationDesignDeclareBean> listData(HttpServletRequest request) {
        String graduationDesignReleaseId = request.getParameter("graduationDesignReleaseId");
        DataTablesUtils<GraduationDesignDeclareBean> dataTablesUtils = DataTablesUtils.of();
        if (!ObjectUtils.isEmpty(graduationDesignReleaseId)) {
            // 前台数据标题 注：要和前台标题顺序一致，获取order用
            List<String> headers = new ArrayList<>();
            headers.add("presubject_title");
            headers.add("subject_type_name");
            headers.add("origin_type_name");
            headers.add("guide_teacher");
            headers.add("academic_title_name");
            headers.add("guide_peoples");
            headers.add("student_number");
            headers.add("student_name");
            headers.add("score_type_name");
            headers.add("operator");
            dataTablesUtils = new DataTablesUtils<>(request, headers);
            GraduationDesignDeclareBean otherCondition = new GraduationDesignDeclareBean();
            int staffId = NumberUtils.toInt(request.getParameter("staffId"));
            otherCondition.setGraduationDesignReleaseId(graduationDesignReleaseId);
            otherCondition.setStaffId(staffId);
            List<GraduationDesignDeclareBean> graduationDesignDeclareBeens = graduationDesignDeclareService.findAllManifestByPage(dataTablesUtils, otherCondition);
            dataTablesUtils.setData(graduationDesignDeclareBeens);
            dataTablesUtils.setiTotalRecords(graduationDesignDeclareService.countAllManifest(otherCondition));
            dataTablesUtils.setiTotalDisplayRecords(graduationDesignDeclareService.countManifestByCondition(dataTablesUtils, otherCondition));
        }
        return dataTablesUtils;
    }
}
