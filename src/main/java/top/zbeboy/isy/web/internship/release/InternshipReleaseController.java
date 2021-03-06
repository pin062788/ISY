package top.zbeboy.isy.web.internship.release;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
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
import top.zbeboy.isy.domain.tables.records.InternshipReleaseRecord;
import top.zbeboy.isy.service.common.CommonControllerMethodService;
import top.zbeboy.isy.service.common.FilesService;
import top.zbeboy.isy.service.common.UploadService;
import top.zbeboy.isy.service.internship.InternshipFileService;
import top.zbeboy.isy.service.internship.InternshipReleaseScienceService;
import top.zbeboy.isy.service.internship.InternshipReleaseService;
import top.zbeboy.isy.service.internship.InternshipTypeService;
import top.zbeboy.isy.service.platform.UsersService;
import top.zbeboy.isy.service.util.DateTimeUtils;
import top.zbeboy.isy.service.util.FilesUtils;
import top.zbeboy.isy.service.util.RequestUtils;
import top.zbeboy.isy.service.util.UUIDUtils;
import top.zbeboy.isy.web.bean.file.FileBean;
import top.zbeboy.isy.web.bean.internship.release.InternshipReleaseBean;
import top.zbeboy.isy.web.util.AjaxUtils;
import top.zbeboy.isy.web.util.PaginationUtils;
import top.zbeboy.isy.web.vo.internship.release.InternshipReleaseAddVo;
import top.zbeboy.isy.web.vo.internship.release.InternshipReleaseUpdateVo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by lenovo on 2016-11-08.
 */
@Slf4j
@Controller
public class InternshipReleaseController {

    @Resource
    private InternshipTypeService internshipTypeService;

    @Resource
    private InternshipReleaseService internshipReleaseService;

    @Resource
    private InternshipReleaseScienceService internshipReleaseScienceService;

    @Resource
    private CommonControllerMethodService commonControllerMethodService;

    @Resource
    private UsersService usersService;

    @Resource
    private UploadService uploadService;

    @Resource
    private FilesService filesService;

    @Resource
    private InternshipFileService internshipFileService;

    /**
     * 实习发布数据
     *
     * @return 实习发布数据页面
     */
    @RequestMapping(value = "/web/menu/internship/release", method = RequestMethod.GET)
    public String releaseData() {
        return "web/internship/release/internship_release::#page-wrapper";
    }

    /**
     * 获取实习发布数据
     *
     * @param paginationUtils 分页工具
     * @return 数据
     */
    @RequestMapping(value = "/web/internship/release/data", method = RequestMethod.GET)
    @ResponseBody
    public AjaxUtils<InternshipReleaseBean> releaseDatas(PaginationUtils paginationUtils) {
        AjaxUtils<InternshipReleaseBean> ajaxUtils = AjaxUtils.of();
        InternshipReleaseBean internshipReleaseBean = new InternshipReleaseBean();
        Map<String, Integer> commonData = commonControllerMethodService.accessRoleCondition();
        internshipReleaseBean.setDepartmentId(StringUtils.isEmpty(commonData.get("departmentId")) ? -1 : commonData.get("departmentId"));
        internshipReleaseBean.setCollegeId(StringUtils.isEmpty(commonData.get("collegeId")) ? -1 : commonData.get("collegeId"));
        Result<Record> records = internshipReleaseService.findAllByPage(paginationUtils, internshipReleaseBean);
        List<InternshipReleaseBean> internshipReleaseBeens = internshipReleaseService.dealData(paginationUtils, records, internshipReleaseBean);
        return ajaxUtils.success().msg("获取数据成功").listData(internshipReleaseBeens).paginationUtils(paginationUtils);
    }

    /**
     * 获取实习列表数据 用于实习教师分配等通用列表数据
     *
     * @param paginationUtils 分页工具
     * @return 数据
     */
    @RequestMapping(value = "/anyone/internship/data", method = RequestMethod.GET)
    @ResponseBody
    public AjaxUtils<InternshipReleaseBean> internshipListDatas(PaginationUtils paginationUtils) {
        AjaxUtils<InternshipReleaseBean> ajaxUtils = AjaxUtils.of();
        Byte isDel = 0;
        InternshipReleaseBean internshipReleaseBean = new InternshipReleaseBean();
        internshipReleaseBean.setInternshipReleaseIsDel(isDel);
        Map<String, Integer> commonData = commonControllerMethodService.accessRoleCondition();
        internshipReleaseBean.setDepartmentId(StringUtils.isEmpty(commonData.get("departmentId")) ? -1 : commonData.get("departmentId"));
        internshipReleaseBean.setCollegeId(StringUtils.isEmpty(commonData.get("collegeId")) ? -1 : commonData.get("collegeId"));
        Result<Record> records = internshipReleaseService.findAllByPage(paginationUtils, internshipReleaseBean);
        List<InternshipReleaseBean> internshipReleaseBeens = internshipReleaseService.dealData(paginationUtils, records, internshipReleaseBean);
        return ajaxUtils.success().msg("获取数据成功").listData(internshipReleaseBeens).paginationUtils(paginationUtils);
    }

    /**
     * 实习发布添加页面
     *
     * @param modelMap 页面对象
     * @return 实习发布添加页面
     */
    @RequestMapping(value = "/web/internship/release/add", method = RequestMethod.GET)
    public String releaseAdd(ModelMap modelMap) {
        Map<String, Integer> commonData = commonControllerMethodService.accessRoleCondition();
        modelMap.addAttribute("departmentId", StringUtils.isEmpty(commonData.get("departmentId")) ? -1 : commonData.get("departmentId"));
        modelMap.addAttribute("collegeId", StringUtils.isEmpty(commonData.get("collegeId")) ? -1 : commonData.get("collegeId"));
        return "web/internship/release/internship_release_add::#page-wrapper";
    }

    /**
     * 实习发布编辑页面
     *
     * @param internshipReleaseId 实习发布id
     * @param modelMap            页面对象
     * @return 实习发布编辑页面
     */
    @RequestMapping(value = "/web/internship/release/edit", method = RequestMethod.GET)
    public String releaseEdit(@RequestParam("id") String internshipReleaseId, ModelMap modelMap) {
        Optional<Record> records = internshipReleaseService.findByIdRelation(internshipReleaseId);
        InternshipReleaseBean internshipRelease = new InternshipReleaseBean();
        List<Science> sciences = new ArrayList<>();
        if (records.isPresent()) {
            internshipRelease = records.get().into(InternshipReleaseBean.class);
            Result<Record> recordResult = internshipReleaseScienceService.findByInternshipReleaseIdRelation(internshipRelease.getInternshipReleaseId());
            if (recordResult.isNotEmpty()) {
                sciences = recordResult.into(Science.class);
            }
        }
        modelMap.addAttribute("internshipRelease", internshipRelease);
        modelMap.addAttribute("sciences", sciences);
        return "web/internship/release/internship_release_edit::#page-wrapper";
    }

    /**
     * 获取实习类型数据
     *
     * @return 实习类型数据
     */
    @RequestMapping(value = "/user/internship/types", method = RequestMethod.GET)
    @ResponseBody
    public AjaxUtils<InternshipType> internshipTypes() {
        AjaxUtils<InternshipType> ajaxUtils = AjaxUtils.of();
        List<InternshipType> internshipTypes = new ArrayList<>();
        InternshipType internshipType = new InternshipType(0, "请选择实习类型");
        internshipTypes.add(internshipType);
        internshipTypes.addAll(internshipTypeService.findAll());
        return ajaxUtils.success().msg("获取实习类型数据成功").listData(internshipTypes);
    }

    /**
     * 获取实习附件数据
     *
     * @param internshipReleaseId 实习发布id
     * @return 实习附件数据
     */
    @RequestMapping(value = "/user/internship/files", method = RequestMethod.GET)
    @ResponseBody
    public AjaxUtils<Files> internshipFiles(@RequestParam("internshipReleaseId") String internshipReleaseId) {
        AjaxUtils<Files> ajaxUtils = AjaxUtils.of();
        List<Files> files = new ArrayList<>();
        Result<Record> records = internshipFileService.findByInternshipReleaseId(internshipReleaseId);
        if (records.isNotEmpty()) {
            files = records.into(Files.class);
        }
        return ajaxUtils.success().msg("获取实习附件数据成功").listData(files);
    }

    /**
     * 保存时检验标题
     *
     * @param title 标题
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/release/save/valid", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils saveValid(@RequestParam("releaseTitle") String title) {
        String releaseTitle = StringUtils.trimWhitespace(title);
        if (StringUtils.hasLength(releaseTitle)) {
            List<InternshipRelease> internshipReleases = internshipReleaseService.findByReleaseTitle(releaseTitle);
            if (ObjectUtils.isEmpty(internshipReleases) && internshipReleases.isEmpty()) {
                return AjaxUtils.of().success().msg("标题不重复");
            }
        }
        return AjaxUtils.of().fail().msg("标题重复");
    }

    /**
     * 更新时检验标题
     *
     * @param internshipReleaseId 实习发布id
     * @param title               标题
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/release/update/valid", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils updateValid(@RequestParam("internshipReleaseId") String internshipReleaseId, @RequestParam("releaseTitle") String title) {
        String releaseTitle = StringUtils.trimWhitespace(title);
        if (StringUtils.hasLength(releaseTitle)) {
            Result<InternshipReleaseRecord> internshipReleases = internshipReleaseService.findByReleaseTitleNeInternshipReleaseId(releaseTitle, internshipReleaseId);
            if (ObjectUtils.isEmpty(internshipReleases) && internshipReleases.isEmpty()) {
                return AjaxUtils.of().success().msg("标题不重复");
            }
        }
        return AjaxUtils.of().fail().msg("标题重复");
    }

    /**
     * 保存
     *
     * @param internshipReleaseAddVo 实习
     * @param bindingResult          检验
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/release/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils save(@Valid InternshipReleaseAddVo internshipReleaseAddVo, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            String internshipReleaseId = UUIDUtils.getUUID();
            String teacherDistributionTime = internshipReleaseAddVo.getTeacherDistributionTime();
            String time = internshipReleaseAddVo.getTime();
            String files = internshipReleaseAddVo.getFiles();
            InternshipRelease internshipRelease = new InternshipRelease();
            internshipRelease.setInternshipReleaseId(internshipReleaseId);
            internshipRelease.setInternshipTitle(internshipReleaseAddVo.getReleaseTitle());
            internshipRelease.setReleaseTime(new Timestamp(Clock.systemDefaultZone().millis()));
            Users users = usersService.getUserFromSession();
            internshipRelease.setUsername(users.getUsername());
            saveOrUpdateTime(internshipRelease, teacherDistributionTime, time);
            internshipRelease.setAllowGrade(internshipReleaseAddVo.getGrade());

            internshipRelease.setDepartmentId(internshipReleaseAddVo.getDepartmentId());
            internshipRelease.setInternshipReleaseIsDel(internshipReleaseAddVo.getInternshipReleaseIsDel());
            internshipRelease.setInternshipTypeId(internshipReleaseAddVo.getInternshipTypeId());
            internshipReleaseService.save(internshipRelease);
            if (StringUtils.hasLength(internshipReleaseAddVo.getScienceId())) {
                String[] scienceArr = internshipReleaseAddVo.getScienceId().split(",");
                for (String scienceId : scienceArr) {
                    internshipReleaseScienceService.save(internshipReleaseId, NumberUtils.toInt(scienceId));
                }
            }
            saveOrUpdateFiles(files, internshipReleaseId);
            return AjaxUtils.of().success().msg("保存成功");
        }
        return AjaxUtils.of().fail().msg("保存失败");
    }

    /**
     * 更新
     *
     * @param internshipReleaseUpdateVo 实习
     * @param bindingResult             检验
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/release/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils update(@Valid InternshipReleaseUpdateVo internshipReleaseUpdateVo, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            String internshipReleaseId = internshipReleaseUpdateVo.getInternshipReleaseId();
            String teacherDistributionTime = internshipReleaseUpdateVo.getTeacherDistributionTime();
            String time = internshipReleaseUpdateVo.getTime();
            String files = internshipReleaseUpdateVo.getFiles();
            InternshipRelease internshipRelease = internshipReleaseService.findById(internshipReleaseId);
            internshipRelease.setInternshipTitle(internshipReleaseUpdateVo.getReleaseTitle());
            saveOrUpdateTime(internshipRelease, teacherDistributionTime, time);
            internshipRelease.setInternshipReleaseIsDel(internshipReleaseUpdateVo.getInternshipReleaseIsDel());
            internshipReleaseService.update(internshipRelease);
            Result<Record> records = internshipFileService.findByInternshipReleaseId(internshipReleaseId);
            if (records.isNotEmpty()) {
                internshipFileService.deleteByInternshipReleaseId(internshipReleaseId);
                List<InternshipFile> internshipFiles = records.into(InternshipFile.class);
                internshipFiles.forEach(f -> filesService.deleteById(f.getFileId()));
            }
            saveOrUpdateFiles(files, internshipReleaseId);
            return AjaxUtils.of().success().msg("保存成功");
        }
        return AjaxUtils.of().fail().msg("保存失败");
    }

    /**
     * 更新实习发布状态
     *
     * @param internshipReleaseId 实习发布id
     * @param isDel               注销参数
     * @return true or false
     */
    @RequestMapping(value = "/web/internship/release/update/del", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUtils updateDel(@RequestParam("internshipReleaseId") String internshipReleaseId, @RequestParam("isDel") Byte isDel) {
        InternshipRelease internshipRelease = internshipReleaseService.findById(internshipReleaseId);
        internshipRelease.setInternshipReleaseIsDel(isDel);
        internshipReleaseService.update(internshipRelease);
        return AjaxUtils.of().success().msg("更新状态成功");
    }

    /**
     * 更新或保存时间
     *
     * @param internshipRelease       实习
     * @param teacherDistributionTime 教师分配时间
     * @param time                    申请时间
     */
    private void saveOrUpdateTime(InternshipRelease internshipRelease, String teacherDistributionTime, String time) {
        try {
            String format = "yyyy-MM-dd HH:mm:ss";
            String[] teacherDistributionArr = DateTimeUtils.splitDateTime("至", teacherDistributionTime);
            internshipRelease.setTeacherDistributionStartTime(DateTimeUtils.formatDateToTimestamp(teacherDistributionArr[0], format));
            internshipRelease.setTeacherDistributionEndTime(DateTimeUtils.formatDateToTimestamp(teacherDistributionArr[1], format));
            String[] timeArr = DateTimeUtils.splitDateTime("至", time);
            internshipRelease.setStartTime(DateTimeUtils.formatDateToTimestamp(timeArr[0], format));
            internshipRelease.setEndTime(DateTimeUtils.formatDateToTimestamp(timeArr[1], format));
        } catch (ParseException e) {
            log.error(" format time is exception.", e);
        }
    }

    /**
     * 更新或保存文件
     *
     * @param files               文件json
     * @param internshipReleaseId 实习id
     */
    private void saveOrUpdateFiles(String files, String internshipReleaseId) {
        if (StringUtils.hasLength(files)) {
            List<Files> filesList = JSON.parseArray(files, Files.class);
            for (Files f : filesList) {
                String fileId = UUIDUtils.getUUID();
                f.setFileId(fileId);
                filesService.save(f);
                InternshipFile internshipFile = new InternshipFile(internshipReleaseId, fileId);
                internshipFileService.save(internshipFile);
            }
        }
    }

    /**
     * 上传实习附件
     *
     * @param schoolId                    学校id
     * @param collegeId                   院id
     * @param departmentId                系id
     * @param multipartHttpServletRequest 文件请求
     * @return 文件信息
     */
    @RequestMapping("/anyone/users/upload/internship")
    @ResponseBody
    public AjaxUtils<FileBean> usersUploadInternship(int schoolId, int collegeId, @RequestParam("departmentId") int departmentId,
                                                     MultipartHttpServletRequest multipartHttpServletRequest) {
        AjaxUtils<FileBean> data = AjaxUtils.of();
        try {
            String path = Workbook.internshipPath(uploadService.schoolInfoPath(schoolId, collegeId, departmentId));
            List<FileBean> fileBeen = uploadService.upload(multipartHttpServletRequest,
                    RequestUtils.getRealPath(multipartHttpServletRequest) + path, multipartHttpServletRequest.getRemoteAddr());
            data.success().listData(fileBeen).obj(path);
        } catch (Exception e) {
            log.error("Upload file exception,is {}", e);
        }
        return data;
    }

    /**
     * 删除实习附件
     *
     * @param filePath            文件路径
     * @param fileId              文件id
     * @param internshipReleaseId 实习id
     * @param request             请求
     * @return true or false
     */
    @RequestMapping("/anyone/users/delete/file/internship")
    @ResponseBody
    public AjaxUtils deleteFileInternship(@RequestParam("filePath") String filePath, @RequestParam("fileId") String fileId,
                                          @RequestParam("internshipReleaseId") String internshipReleaseId, HttpServletRequest request) {
        AjaxUtils ajaxUtils = AjaxUtils.of();
        try {
            if (FilesUtils.deleteFile(RequestUtils.getRealPath(request) + filePath)) {
                internshipFileService.deleteByFileIdAndInternshipReleaseId(fileId, internshipReleaseId);
                filesService.deleteById(fileId);
                ajaxUtils.success().msg("删除文件成功");
            } else {
                ajaxUtils.fail().msg("删除文件失败");
            }
        } catch (Exception e) {
            log.error("Delete file exception, is {}", e);
        }
        return ajaxUtils;
    }
}
