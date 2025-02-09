package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ProcessInfo;
import cc.mrbird.febs.cos.service.IProcessInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
@RequestMapping("/cos/process-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProcessInfoController {

    private final IProcessInfoService processInfoService;

    /**
     * 分页获取流程管理信息
     *
     * @param page        分页对象
     * @param processInfo 流程管理信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ProcessInfo> page, ProcessInfo processInfo) {
        return R.ok(processInfoService.queryProcessPage(page, processInfo));
    }

    /**
     * 获取流程管理信息详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(processInfoService.getById(id));
    }

    /**
     * 新增流程管理信息
     *
     * @param processInfo 流程管理信息
     * @return 结果
     */
    @PostMapping
    public R save(ProcessInfo processInfo) throws FebsException {
        processInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        int count = processInfoService.count(Wrappers.<ProcessInfo>lambdaQuery().eq(ProcessInfo::getStepIndex, processInfo.getStepIndex()));
        if (count > 0) {
            throw new FebsException("当前步骤已存在，请修改步骤");
        }
        return R.ok(processInfoService.save(processInfo));
    }

    /**
     * 修改流程管理信息
     *
     * @param processInfo 流程管理信息
     * @return 结果
     */
    @PutMapping
    public R edit(ProcessInfo processInfo) {
        return R.ok(processInfoService.updateById(processInfo));
    }

    /**
     * 删除流程管理信息
     *
     * @param ids 主键
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(processInfoService.removeByIds(ids));
    }
}
