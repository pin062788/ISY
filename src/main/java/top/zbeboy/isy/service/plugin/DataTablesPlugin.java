package top.zbeboy.isy.service.plugin;

import org.jooq.*;
import org.springframework.util.ObjectUtils;
import top.zbeboy.isy.web.util.DataTablesUtils;

/**
 * Created by zbeboy on 2016/9/20.
 * datatables 分页插件
 */
public class DataTablesPlugin<T> {

    public static final int CONDITION_TYPE = 0;

    public static final int JOIN_TYPE = 1;

    /**
     * 查询全部数据
     * @param dataTablesUtils
     * @param create
     * @param table
     * @return 全部数据
     */
    public Result<Record> dataPagingQueryAll(DataTablesUtils<T> dataTablesUtils,final DSLContext create,TableLike<?> table) {
        Result<Record> records = null;
        Condition a = searchCondition(dataTablesUtils);
        if (ObjectUtils.isEmpty(a)) {
            SelectJoinStep<Record> selectJoinStep = create.select()
                    .from(table);
            sortCondition(dataTablesUtils, null, selectJoinStep, JOIN_TYPE);
            pagination(dataTablesUtils, null, selectJoinStep, JOIN_TYPE);
            records = selectJoinStep.fetch();
        } else {
            SelectConditionStep<Record> selectConditionStep = create.select()
                    .from(table)
                    .where(a);
            sortCondition(dataTablesUtils, selectConditionStep, null, CONDITION_TYPE);
            pagination(dataTablesUtils, selectConditionStep, null, CONDITION_TYPE);
            records = selectConditionStep.fetch();
        }
        return records;
    }

    /**
     * 统计全部
     * @param create
     * @param table
     * @return 统计
     */
    public int statisticsAll(final DSLContext create,TableLike<?> table){
        Record1<Integer> count = create.selectCount()
                .from(table)
                .fetchOne();
        return count.value1();
    }

    /**
     * 根据条件统计
     * @param dataTablesUtils
     * @param create
     * @param table
     * @return 统计
     */
    public int statisticsWithCondition(DataTablesUtils<T> dataTablesUtils,final DSLContext create,TableLike<?> table){
        Record1<Integer> count = null;
        Condition a = searchCondition(dataTablesUtils);
        if (ObjectUtils.isEmpty(a)) {
            SelectJoinStep<Record1<Integer>> selectJoinStep = create.selectCount()
                    .from(table);
            count = selectJoinStep.fetchOne();
        } else {
            SelectConditionStep<Record1<Integer>> selectConditionStep = create.selectCount()
                    .from(table)
                    .where(a);
            count = selectConditionStep.fetchOne();
        }
        return count.value1();
    }

    /**
     * 查询条件，需要自行覆盖
     * @param dataTablesUtils
     * @return
     */
    public Condition searchCondition(DataTablesUtils<T> dataTablesUtils) {
        return null;
    }

    /**
     * 排序方式，需要自行覆盖
     * @param dataTablesUtils
     * @param selectConditionStep
     * @param selectJoinStep
     * @param type
     */
    public void sortCondition(DataTablesUtils<T> dataTablesUtils, SelectConditionStep<Record> selectConditionStep, SelectJoinStep<Record> selectJoinStep, int type) {

    }

    /**
     * 分页方式
     * @param dataTablesUtils
     * @param selectConditionStep
     * @param selectJoinStep
     * @param type
     */
    public void pagination(DataTablesUtils<T> dataTablesUtils, SelectConditionStep<Record> selectConditionStep, SelectJoinStep<Record> selectJoinStep, int type) {
        int start = dataTablesUtils.getStart();
        int length = dataTablesUtils.getLength();

        if (type == CONDITION_TYPE) {
            selectConditionStep.limit(start, length);
        }

        if (type == JOIN_TYPE) {
            selectJoinStep.limit(start, length);
        }
    }
}
