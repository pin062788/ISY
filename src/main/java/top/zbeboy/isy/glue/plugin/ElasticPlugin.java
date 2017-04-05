package top.zbeboy.isy.glue.plugin;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import top.zbeboy.isy.web.util.DataTablesUtils;

/**
 * Created by lenovo on 2017-03-29.
 */
public class ElasticPlugin<T> {

    /**
     * 构建查询语句
     *
     * @param search          搜索条件
     * @param dataTablesUtils datatables工具类
     * @return 条件
     */
    public SearchQuery buildSearchQuery(JSONObject search, DataTablesUtils<T> dataTablesUtils) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(searchCondition(search));
        return sortCondition(dataTablesUtils, nativeSearchQueryBuilder).withPageable(pagination(dataTablesUtils)).build();
    }

    /**
     * 查询条件，需要自行覆盖
     *
     * @param search 搜索条件
     * @return 查询条件
     */
    public QueryBuilder searchCondition(JSONObject search) {
        return null;
    }

    /**
     * 排序方式，需要自行覆盖
     *
     * @param dataTablesUtils          datatables工具类
     * @param nativeSearchQueryBuilder builer
     */
    public NativeSearchQueryBuilder sortCondition(DataTablesUtils<T> dataTablesUtils, NativeSearchQueryBuilder nativeSearchQueryBuilder) {
        return null;
    }

    /**
     * 分页方式
     *
     * @param dataTablesUtils datatables工具类
     */
    public PageRequest pagination(DataTablesUtils<T> dataTablesUtils) {
        return new PageRequest(dataTablesUtils.getExtraPage(), dataTablesUtils.getLength());
    }

}
