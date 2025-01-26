package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ProcessDetail;
import cc.mrbird.febs.cos.service.IProcessDetailService;
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
@RequestMapping("/cos/process-detail")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProcessDetailController {

    private final IProcessDetailService processDetailService;

    /**
     * 分页获取生产流程阶段消耗信息
     *
     * @param page          分页对象
     * @param processDetail 生产流程阶段消耗信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ProcessDetail> page, ProcessDetail processDetail) {
        return R.ok();
    }

    /**
     * 获取生产流程阶段消耗信息详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(processDetailService.getById(id));
    }

    /**
     * 新增生产流程阶段消耗信息
     *
     * @param processDetail 生产流程阶段消耗信息
     * @return 结果
     */
    @PostMapping
    public R save(ProcessDetail processDetail) {
        return R.ok(processDetailService.save(processDetail));
    }

    /**
     * 修改生产流程阶段消耗信息
     *
     * @param processDetail 生产流程阶段消耗信息
     * @return 结果
     */
    @PutMapping
    public R edit(ProcessDetail processDetail) {
        return R.ok(processDetailService.updateById(processDetail));
    }

    /**
     * 删除生产流程阶段消耗信息
     *
     * @param ids 主键
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(processDetailService.removeByIds(ids));
    }
}
