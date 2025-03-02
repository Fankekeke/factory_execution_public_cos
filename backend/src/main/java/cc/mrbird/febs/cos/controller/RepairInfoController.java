package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.RepairInfo;
import cc.mrbird.febs.cos.entity.StudentInfo;
import cc.mrbird.febs.cos.service.IRepairInfoService;
import cc.mrbird.febs.cos.service.IStudentInfoService;
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
@RequestMapping("/cos/repair-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RepairInfoController {

    private final IRepairInfoService repairInfoService;

    private final IStudentInfoService staffInfoService;

    /**
     * 分页获取维保信息
     *
     * @param page       分页对象
     * @param repairInfo 维保信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<RepairInfo> page, RepairInfo repairInfo) {
        return R.ok(repairInfoService.queryRepairPage(page, repairInfo));
    }

    /**
     * 获取维保信息详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(repairInfoService.getById(id));
    }

    /**
     * 新增维保信息
     *
     * @param repairInfo 维保信息
     * @return 结果
     */
    @PostMapping
    public R save(RepairInfo repairInfo) {
        repairInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 设置员工ID
        StudentInfo staffInfo = staffInfoService.getOne(Wrappers.<StudentInfo>lambdaQuery().eq(StudentInfo::getUserId, repairInfo.getStaffId()));
        if (staffInfo != null) {
            repairInfo.setStaffId(staffInfo.getId());
        }
        return R.ok(repairInfoService.save(repairInfo));
    }

    /**
     * 修改维保信息
     *
     * @param repairInfo 维保信息
     * @return 结果
     */
    @PutMapping
    public R edit(RepairInfo repairInfo) {
        return R.ok(repairInfoService.updateById(repairInfo));
    }

    /**
     * 删除维保信息
     *
     * @param ids 主键
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(repairInfoService.removeByIds(ids));
    }
}
