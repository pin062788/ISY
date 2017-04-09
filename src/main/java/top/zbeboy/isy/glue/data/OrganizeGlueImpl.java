package top.zbeboy.isy.glue.data;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import top.zbeboy.isy.config.Workbook;
import top.zbeboy.isy.domain.tables.pojos.Users;
import top.zbeboy.isy.elastic.pojo.OrganizeElastic;
import top.zbeboy.isy.elastic.repository.OrganizeElasticRepository;
import top.zbeboy.isy.glue.plugin.ElasticPlugin;
import top.zbeboy.isy.glue.util.ResultUtils;
import top.zbeboy.isy.glue.util.SearchUtils;
import top.zbeboy.isy.service.data.OrganizeService;
import top.zbeboy.isy.service.platform.RoleService;
import top.zbeboy.isy.service.platform.UsersService;
import top.zbeboy.isy.service.util.SQLQueryUtils;
import top.zbeboy.isy.web.bean.data.organize.OrganizeBean;
import top.zbeboy.isy.web.util.DataTablesUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by lenovo on 2017-04-09.
 */
@Repository("organizeGlue")
public class OrganizeGlueImpl extends ElasticPlugin<OrganizeBean> implements OrganizeGlue {

    private final Logger log = LoggerFactory.getLogger(OrganizeGlueImpl.class);

    @Resource
    private OrganizeService organizeService;

    @Resource
    private OrganizeElasticRepository organizeElasticRepository;

    @Resource
    private RoleService roleService;

    @Resource
    private UsersService usersService;

    @Override
    public ResultUtils<List<OrganizeBean>> findAllByPage(DataTablesUtils<OrganizeBean> dataTablesUtils) {
        JSONObject search = dataTablesUtils.getSearch();
        ResultUtils<List<OrganizeBean>> resultUtils = new ResultUtils<>();
        if (SearchUtils.mapValueIsNotEmpty(search)) {
            // 分权限显示用户数据
            if (roleService.isCurrentUserInRole(Workbook.SYSTEM_AUTHORITIES)) { // 系统
                Page<OrganizeElastic> organizeElasticPage = organizeElasticRepository.search(buildSearchQuery(search, dataTablesUtils, false));
                resultUtils.data(dataBuilder(organizeElasticPage)).isSearch(true).totalElements(organizeElasticPage.getTotalElements());
            } else if (roleService.isCurrentUserInRole(Workbook.ADMIN_AUTHORITIES)) { // 管理员
                Page<OrganizeElastic> organizeElasticPage = organizeElasticRepository.search(buildSearchQuery(search, dataTablesUtils, true));
                resultUtils.data(dataBuilder(organizeElasticPage)).isSearch(true).totalElements(organizeElasticPage.getTotalElements());
            }
        } else {
            resultUtils.data(freestanding(dataTablesUtils)).isSearch(false);
        }
        return resultUtils;
    }

    @Override
    public long countAll(DataTablesUtils<OrganizeBean> dataTablesUtils) {
        JSONObject search = dataTablesUtils.getSearch();
        long count = 0;
        if (SearchUtils.mapValueIsNotEmpty(search)) {
            // 分权限显示用户数据
            if (roleService.isCurrentUserInRole(Workbook.SYSTEM_AUTHORITIES)) { // 系统
                count = organizeElasticRepository.count();
            } else if (roleService.isCurrentUserInRole(Workbook.ADMIN_AUTHORITIES)) { // 管理员
                Users users = usersService.getUserFromSession();
                Optional<Record> record = usersService.findUserSchoolInfo(users);
                int collegeId = roleService.getRoleCollegeId(record);
                count = organizeElasticRepository.countByCollegeId(collegeId);
            }
        } else {
            count = organizeService.countAll();
        }
        return count;
    }

    @Override
    public long countByCondition(DataTablesUtils<OrganizeBean> dataTablesUtils) {
        return organizeService.countByCondition(dataTablesUtils);
    }


    /**
     * 构建新数据
     *
     * @param organizeElasticPage 分页数据
     * @return 新数据
     */
    private List<OrganizeBean> dataBuilder(Page<OrganizeElastic> organizeElasticPage) {
        List<OrganizeBean> organizes = new ArrayList<>();
        for (OrganizeElastic organizeElastic : organizeElasticPage.getContent()) {
            OrganizeBean organizeBean = new OrganizeBean();
            organizeBean.setSchoolName(organizeElastic.getSchoolName());
            organizeBean.setCollegeName(organizeElastic.getCollegeName());
            organizeBean.setDepartmentName(organizeElastic.getDepartmentName());
            organizeBean.setScienceName(organizeElastic.getScienceName());
            organizeBean.setSchoolId(organizeElastic.getSchoolId());
            organizeBean.setCollegeId(organizeElastic.getCollegeId());
            organizeBean.setScienceId(organizeElastic.getScienceId());
            organizeBean.setDepartmentId(organizeElastic.getDepartmentId());
            organizeBean.setOrganizeId(organizeElastic.getOrganizeId());
            organizeBean.setOrganizeName(organizeElastic.getOrganizeName());
            organizeBean.setOrganizeIsDel(organizeElastic.getOrganizeIsDel());
            organizeBean.setGrade(organizeElastic.getGrade());
            organizes.add(organizeBean);
        }
        return organizes;
    }

    /**
     * 数据原生实现方式
     *
     * @param dataTablesUtils datatables工具类
     * @return 原生数据
     */
    private List<OrganizeBean> freestanding(DataTablesUtils<OrganizeBean> dataTablesUtils) {
        List<OrganizeBean> organizes = new ArrayList<>();
        Result<Record> records = organizeService.findAllByPage(dataTablesUtils);
        if (!ObjectUtils.isEmpty(records) && records.isNotEmpty()) {
            organizes = records.into(OrganizeBean.class);
        }
        return organizes;
    }

    /**
     * 搜索前置条件
     *
     * @param search 搜索内容
     * @return 前置
     */
    @Override
    public QueryBuilder prepositionCondition(JSONObject search) {
        Users users = usersService.getUserFromSession();
        Optional<Record> record = usersService.findUserSchoolInfo(users);
        int collegeId = roleService.getRoleCollegeId(record);
        BoolQueryBuilder boolqueryBuilder = QueryBuilders.boolQuery();
        boolqueryBuilder.must(searchCondition(search));
        boolqueryBuilder.must(QueryBuilders.matchQuery("collegeId", collegeId));
        return boolqueryBuilder;
    }

    /**
     * 班级全局搜索条件
     *
     * @param search 搜索参数
     * @return 搜索条件
     */
    @Override
    public QueryBuilder searchCondition(JSONObject search) {
        BoolQueryBuilder boolqueryBuilder = QueryBuilders.boolQuery();
        String schoolName = StringUtils.trimWhitespace(search.getString("schoolName"));
        String collegeName = StringUtils.trimWhitespace(search.getString("collegeName"));
        String departmentName = StringUtils.trimWhitespace(search.getString("departmentName"));
        String scienceName = StringUtils.trimWhitespace(search.getString("scienceName"));
        String grade = StringUtils.trimWhitespace(search.getString("grade"));
        String organizeName = StringUtils.trimWhitespace(search.getString("organizeName"));

        if (StringUtils.hasLength(schoolName)) {
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchPhraseQuery("schoolName", schoolName);
            boolqueryBuilder.must(matchQueryBuilder);
        }

        if (StringUtils.hasLength(collegeName)) {
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchPhraseQuery("collegeName", collegeName);
            boolqueryBuilder.must(matchQueryBuilder);
        }

        if (StringUtils.hasLength(departmentName)) {
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchPhraseQuery("departmentName", departmentName);
            boolqueryBuilder.must(matchQueryBuilder);
        }

        if (StringUtils.hasLength(scienceName)) {
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchPhraseQuery("scienceName", scienceName);
            boolqueryBuilder.must(matchQueryBuilder);
        }

        if (StringUtils.hasLength(grade)) {
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("grade", SQLQueryUtils.elasticLikeAllParam(grade));
            boolqueryBuilder.must(wildcardQueryBuilder);
        }

        if (StringUtils.hasLength(organizeName)) {
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchPhraseQuery("organizeName", organizeName);
            boolqueryBuilder.must(matchQueryBuilder);
        }

        return boolqueryBuilder;
    }

    /**
     * 班级排序
     *
     * @param dataTablesUtils          datatables工具类
     * @param nativeSearchQueryBuilder 查询器
     */
    @Override
    public NativeSearchQueryBuilder sortCondition(DataTablesUtils<OrganizeBean> dataTablesUtils, NativeSearchQueryBuilder nativeSearchQueryBuilder) {
        String orderColumnName = dataTablesUtils.getOrderColumnName();
        String orderDir = dataTablesUtils.getOrderDir();
        boolean isAsc = "asc".equalsIgnoreCase(orderDir);
        if (StringUtils.hasLength(orderColumnName)) {
            if ("organize_id".equalsIgnoreCase(orderColumnName)) {
                if (isAsc) {
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeId").order(SortOrder.ASC));
                } else {
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeId").order(SortOrder.DESC));
                }
            }

            if ("school_name".equalsIgnoreCase(orderColumnName)) {
                if (isAsc) {
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("schoolName").order(SortOrder.ASC));
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeId").order(SortOrder.ASC));
                } else {
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("schoolName").order(SortOrder.DESC));
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeId").order(SortOrder.DESC));
                }
            }

            if ("college_name".equalsIgnoreCase(orderColumnName)) {
                if (isAsc) {
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("collegeName").order(SortOrder.ASC));
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeId").order(SortOrder.ASC));
                } else {
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("collegeName").order(SortOrder.DESC));
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeId").order(SortOrder.DESC));
                }
            }

            if ("department_name".equalsIgnoreCase(orderColumnName)) {
                if (isAsc) {
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("departmentName").order(SortOrder.ASC));
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeId").order(SortOrder.ASC));
                } else {
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("departmentName").order(SortOrder.DESC));
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeId").order(SortOrder.DESC));
                }
            }

            if ("science_name".equalsIgnoreCase(orderColumnName)) {
                if (isAsc) {
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("scienceName").order(SortOrder.ASC));
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeId").order(SortOrder.ASC));
                } else {
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("scienceName").order(SortOrder.DESC));
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeId").order(SortOrder.DESC));
                }
            }

            if ("grade".equalsIgnoreCase(orderColumnName)) {
                if (isAsc) {
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("grade").order(SortOrder.ASC));
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeId").order(SortOrder.ASC));
                } else {
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("grade").order(SortOrder.DESC));
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeId").order(SortOrder.DESC));
                }
            }

            if ("organize_name".equalsIgnoreCase(orderColumnName)) {
                if (isAsc) {
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeName").order(SortOrder.ASC));
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeId").order(SortOrder.ASC));
                } else {
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeName").order(SortOrder.DESC));
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeId").order(SortOrder.DESC));
                }
            }

            if ("organize_is_del".equalsIgnoreCase(orderColumnName)) {
                if (isAsc) {
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeIsDel").order(SortOrder.ASC));
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeId").order(SortOrder.ASC));
                } else {
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeIsDel").order(SortOrder.DESC));
                    nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("organizeId").order(SortOrder.DESC));
                }
            }
        }
        return nativeSearchQueryBuilder;
    }
}
