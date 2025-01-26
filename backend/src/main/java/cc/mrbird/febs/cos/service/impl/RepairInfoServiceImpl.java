package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.RepairInfo;
import cc.mrbird.febs.cos.dao.RepairInfoMapper;
import cc.mrbird.febs.cos.service.IRepairInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
public class RepairInfoServiceImpl extends ServiceImpl<RepairInfoMapper, RepairInfo> implements IRepairInfoService {

    /**
     * 分页获取维保信息
     *
     * @param page       分页对象
     * @param repairInfo 维保信息
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryRepairPage(Page<RepairInfo> page, RepairInfo repairInfo) {
        return baseMapper.queryRepairPage(page, repairInfo);
    }
}
