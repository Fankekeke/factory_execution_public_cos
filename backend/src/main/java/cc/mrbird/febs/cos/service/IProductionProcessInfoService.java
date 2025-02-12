package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.entity.ProcessDetail;
import cc.mrbird.febs.cos.entity.ProductionProcessInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
public interface IProductionProcessInfoService extends IService<ProductionProcessInfo> {

    /**
     * 分页获取生产流程信息
     *
     * @param page        分页对象
     * @param productionProcessInfo 生产流程信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryProductionProcessPage(Page<ProductionProcessInfo> page, ProductionProcessInfo productionProcessInfo);

    /**
     * 更新流程状态
     *
     * @param productionProcessInfo 生产流程信息
     * @return 结果
     */
    boolean updateProcessStatus(ProductionProcessInfo productionProcessInfo) throws FebsException;

    /**
     * 流程状态申请原材料
     *
     * @param productionProcessInfo 生产流程信息
     * @return 结果
     */
    boolean requestProcessStatus(ProductionProcessInfo productionProcessInfo) throws FebsException;

    /**
     * 流程完成商品入库
     *
     * @param productionProcessInfo 生产流程信息
     * @return 结果
     */
    boolean addWarehouse(ProductionProcessInfo productionProcessInfo) throws FebsException;

    /**
     * 获取生产流程信息详情
     *
     * @param id 主键
     * @return 结果
     */
    List<ProcessDetail> queryProcessDetail(Integer id);
}
