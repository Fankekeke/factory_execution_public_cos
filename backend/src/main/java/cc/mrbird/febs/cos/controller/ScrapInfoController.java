package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ScrapInfo;
import cc.mrbird.febs.cos.entity.StudentInfo;
import cc.mrbird.febs.cos.service.IScrapInfoService;
import cc.mrbird.febs.cos.service.IStudentInfoService;
import cn.hutool.core.date.DateUtil;
import com.alipay.api.domain.StaffInfo;
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
@RequestMapping("/cos/scrap-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScrapInfoController {

    private final IScrapInfoService scrapInfoService;

    private final IStudentInfoService staffInfoService;

    /**
     * 分页获取物资报废信息
     *
     * @param page      分页对象
     * @param scrapInfo 物资报废信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ScrapInfo> page, ScrapInfo scrapInfo) {
        return R.ok(scrapInfoService.queryScrapPage(page, scrapInfo));
    }

    /**
     * 获取物资报废信息详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(scrapInfoService.getById(id));
    }

    /**
     * 新增物资报废信息
     *
     * @param scrapInfo 物资报废信息
     * @return 结果
     */
    @PostMapping
    public R save(ScrapInfo scrapInfo) {
        scrapInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 设置员工ID
        StudentInfo staffInfo = staffInfoService.getOne(Wrappers.<StudentInfo>lambdaQuery().eq(StudentInfo::getUserId, scrapInfo.getStaffId()));
        if (staffInfo != null) {
            scrapInfo.setStaffId(staffInfo.getId());
        }
        return R.ok(scrapInfoService.save(scrapInfo));
    }

    /**
     * 修改物资报废信息
     *
     * @param scrapInfo 物资报废信息
     * @return 结果
     */
    @PutMapping
    public R edit(ScrapInfo scrapInfo) {
        return R.ok(scrapInfoService.updateById(scrapInfo));
    }

    /**
     * 删除物资报废信息
     *
     * @param ids 主键
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(scrapInfoService.removeByIds(ids));
    }
}
