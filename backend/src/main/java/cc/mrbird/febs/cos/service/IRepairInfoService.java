package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.RepairInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
public interface IRepairInfoService extends IService<RepairInfo> {

    /**
     * 分页获取维保信息
     *
     * @param page       分页对象
     * @param repairInfo 维保信息
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryRepairPage(Page<RepairInfo> page, RepairInfo repairInfo);
}
