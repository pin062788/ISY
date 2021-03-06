package top.zbeboy.isy.service.graduate.design;

import top.zbeboy.isy.domain.tables.pojos.DefenseRate;
import top.zbeboy.isy.domain.tables.records.DefenseRateRecord;
import top.zbeboy.isy.web.bean.graduate.design.reorder.DefenseRateBean;

import java.util.List;

/**
 * Created by zbeboy on 2017/7/28.
 */
public interface DefenseRateService {

    /**
     * 通过顺序id与毕业设计指导教师id查询
     *
     * @param defenseOrderId            毕业设计顺序id
     * @param graduationDesignTeacherId 毕业设计指导教师id
     * @return 数据
     */
    DefenseRateRecord findByDefenseOrderIdAndGraduationDesignTeacherId(String defenseOrderId, String graduationDesignTeacherId);

    /**
     * 保存
     *
     * @param defenseRate 数据
     */
    void saveOrUpdate(DefenseRate defenseRate);

    /**
     * 通过毕业答辩顺序id与毕业答辩组id查询教师打分信息
     *
     * @param defenseOrderId 毕业答辩顺序id
     * @param defenseGroupId 毕业答辩组id
     * @return 数据
     */
    List<DefenseRateBean> findByDefenseOrderIdAndDefenseGroupId(String defenseOrderId, String defenseGroupId);

    /**
     * 通过答辩顺序id删除
     *
     * @param defenseOrderId 答辩顺序id
     */
    void deleteByDefenseOrderId(String defenseOrderId);
}
