package top.zbeboy.isy.elastic.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import top.zbeboy.isy.elastic.pojo.SystemSmsElastic;

/**
 * Created by lenovo on 2017-04-08.
 */
public interface SystemSmsElasticRepository extends ElasticsearchRepository<SystemSmsElastic, String> {
    /**
     * 根据发送时间删除
     *
     * @param sendTime 发送时间
     */
    void deleteBySendTimeLessThanEqual(long sendTime);
}
