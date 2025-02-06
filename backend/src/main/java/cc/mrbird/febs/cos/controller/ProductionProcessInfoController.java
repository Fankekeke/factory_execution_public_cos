package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ProductionProcessInfo;
import cc.mrbird.febs.cos.service.IProductionProcessInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public R save(ProductionProcessInfo productionProcessInfo) {
        productionProcessInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        productionProcessInfo.setCode("PCS-" + System.currentTimeMillis());
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
