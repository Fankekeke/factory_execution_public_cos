package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ProductionProcessInfo;
import cc.mrbird.febs.cos.dao.ProductionProcessInfoMapper;
import cc.mrbird.febs.cos.service.IProductionProcessInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
public class ProductionProcessInfoServiceImpl extends ServiceImpl<ProductionProcessInfoMapper, ProductionProcessInfo> implements IProductionProcessInfoService {

    /**
     * 分页获取生产流程信息
     *
     * @param page        分页对象
     * @param productionProcessInfo 生产流程信息
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryProductionProcessPage(Page<ProductionProcessInfo> page, ProductionProcessInfo productionProcessInfo) {
        return baseMapper.queryProductionProcessPage(page, productionProcessInfo);
    }
}
