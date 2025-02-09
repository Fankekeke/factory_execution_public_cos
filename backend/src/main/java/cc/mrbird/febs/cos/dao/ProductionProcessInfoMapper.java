package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ProductionProcessInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
public interface ProductionProcessInfoMapper extends BaseMapper<ProductionProcessInfo> {

    /**
     * 分页获取生产流程信息
     *
     * @param page        分页对象
     * @param productionProcessInfo 生产流程信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryProductionProcessPage(Page<ProductionProcessInfo> page, @Param("productionProcessInfo") ProductionProcessInfo productionProcessInfo);
}
