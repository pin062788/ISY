package top.zbeboy.isy.service.internship;

import org.jooq.Record;
import org.jooq.Result;
import top.zbeboy.isy.domain.tables.pojos.InternshipJournal;
import top.zbeboy.isy.domain.tables.records.InternshipJournalRecord;
import top.zbeboy.isy.web.bean.internship.journal.InternshipJournalBean;
import top.zbeboy.isy.web.util.DataTablesUtils;

/**
 * Created by zbeboy on 2016/12/14.
 */
public interface InternshipJournalService {

    /**
     * 通过id查询
     *
     * @param id 主键
     * @return 实习日志
     */
    InternshipJournal findById(String id);

    /**
     * 通过实习发布id与学生id查询
     *
     * @param internshipReleaseId 实习发布id
     * @param studentId           学生id
     * @return 数据
     */
    Result<InternshipJournalRecord> findByInternshipReleaseIdAndStudentId(String internshipReleaseId, int studentId);

    /**
     * 通过实习发布id与教职工id查询
     *
     * @param internshipReleaseId 实习发布id
     * @param staffId             教职工id
     * @return 数据
     */
    Result<InternshipJournalRecord> findByInternshipReleaseIdAndStaffId(String internshipReleaseId, int staffId);

    /**
     * 保存
     *
     * @param internshipJournal 实习日志
     */
    void save(InternshipJournal internshipJournal);

    /**
     * 更新
     *
     * @param internshipJournal 实习日志
     */
    void update(InternshipJournal internshipJournal);

    /**
     * 分页查询 数据
     *
     * @param dataTablesUtils datatables工具类
     * @return 分页数据
     */
    Result<Record> findAllByPage(DataTablesUtils<InternshipJournalBean> dataTablesUtils, InternshipJournalBean internshipJournalBean);

    /**
     * 数据 总数
     *
     * @return 总数
     */
    int countAll(InternshipJournalBean internshipJournalBean);

    /**
     * 根据条件查询总数 数据
     *
     * @return 条件查询总数
     */
    int countByCondition(DataTablesUtils<InternshipJournalBean> dataTablesUtils, InternshipJournalBean internshipJournalBean);

    /**
     * 通过id删除
     *
     * @param id id
     */
    void deleteById(String id);
}
