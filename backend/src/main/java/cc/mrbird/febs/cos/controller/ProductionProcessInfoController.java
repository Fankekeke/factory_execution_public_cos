package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ProcessDetail;
import cc.mrbird.febs.cos.entity.ProcessInfo;
import cc.mrbird.febs.cos.entity.ProductionProcessInfo;
import cc.mrbird.febs.cos.service.IProcessDetailService;
import cc.mrbird.febs.cos.service.IProcessInfoService;
import cc.mrbird.febs.cos.service.IProductionProcessInfoService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/production-process-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductionProcessInfoController {

    private final IProductionProcessInfoService productionProcessInfoService;

    private final IProcessInfoService processInfoService;

    private final IProcessDetailService processDetailService;

    /**
     * 分页获取生产流程信息
     *
     * @param page                  分页对象
     * @param productionProcessInfo 生产流程信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ProductionProcessInfo> page, ProductionProcessInfo productionProcessInfo) {
        return R.ok(productionProcessInfoService.queryProductionProcessPage(page, productionProcessInfo));
    }

    /**
     * 更新流程状态
     *
     * @param productionProcessInfo 生产流程信息
     * @return 结果
     */
    @PostMapping("/updateProcessStatus")
    public R updateProcessStatus(ProductionProcessInfo productionProcessInfo) throws FebsException {
        return R.ok(productionProcessInfoService.updateProcessStatus(productionProcessInfo));
    }

    /**
     * 流程状态申请原材料
     *
     * @param productionProcessInfo 生产流程信息
     * @return 结果
     */
    @PostMapping("/requestProcessStatus")
    public R requestProcessStatus(ProductionProcessInfo productionProcessInfo) throws FebsException {
        return R.ok(productionProcessInfoService.requestProcessStatus(productionProcessInfo));
    }

    /**
     * 流程完成商品入库
     *
     * @param productionProcessInfo 生产流程信息
     * @return 结果
     */
    @PostMapping("/addWarehouse")
    public R addWarehouse(ProductionProcessInfo productionProcessInfo) throws FebsException {
        return R.ok(productionProcessInfoService.addWarehouse(productionProcessInfo));
    }

    /**
     * 获取生产流程信息详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/queryProcessDetail/{id}")
    public R queryProcessDetail(@PathVariable("id") Integer id) {
        return R.ok(productionProcessInfoService.queryProcessDetail(id));
    }

    /**
     * 获取生产流程信息详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(productionProcessInfoService.getById(id));
    }

    /**
     * 新增生产流程信息
     *
     * @param productionProcessInfo 生产流程信息
     * @return 结果
     */
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public R save(ProductionProcessInfo productionProcessInfo) {
        productionProcessInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        productionProcessInfo.setCode("PCS-" + System.currentTimeMillis());
        productionProcessInfo.setStatus("0");
        // 获取流程步骤
        List<ProcessInfo> processList = processInfoService.list(Wrappers.<ProcessInfo>lambdaQuery().orderByAsc(ProcessInfo::getStepIndex));
        if (CollUtil.isEmpty(processList)) {
            return R.error("流程步骤为空，请先添加流程步骤");
        }
        productionProcessInfo.setStepNum(processList.size());
        productionProcessInfo.setCurrentStep(processList.get(0).getStepIndex());

        // 添加流程详情
        List<ProcessDetail> processDetailList = new ArrayList<>();
        for (ProcessInfo processInfo : processList) {
            ProcessDetail processDetail = new ProcessDetail();
            processDetail.setProcessCode(productionProcessInfo.getCode());
            processDetail.setStepIndex(processInfo.getStepIndex());
            processDetailList.add(processDetail);
        }
        processDetailService.saveBatch(processDetailList);
        return R.ok(productionProcessInfoService.save(productionProcessInfo));
    }

    /**
     * 修改生产流程信息
     *
     * @param productionProcessInfo 生产流程信息
     * @return 结果
     */
    @PutMapping
    public R edit(ProductionProcessInfo productionProcessInfo) {
        return R.ok(productionProcessInfoService.updateById(productionProcessInfo));
    }

    /**
     * 删除生产流程信息
     *
     * @param ids 主键
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(productionProcessInfoService.removeByIds(ids));
    }
}
