package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.BulletinInfo;
import cc.mrbird.febs.cos.service.IBulletinInfoService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
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
@RequestMapping("/cos/bulletin-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BulletinInfoController {

    private final IBulletinInfoService bulletinInfoService;

    /**
     * 分页获取公告信息
     *
     * @param page
     * @param bulletinInfo
     * @return
     */
    @GetMapping("/page")
    public R page(Page page, BulletinInfo bulletinInfo) {
        return R.ok(bulletinInfoService.page(page, Wrappers.<BulletinInfo>lambdaQuery()
                .like(!StrUtil.isBlank(bulletinInfo.getTitle()), BulletinInfo::getTitle, bulletinInfo.getTitle())
                .like(!StrUtil.isBlank(bulletinInfo.getContent()), BulletinInfo::getContent, bulletinInfo.getContent())));
    }

    @GetMapping("/list")
    public R list() {
        return R.ok(bulletinInfoService.list());
    }

    /**
     * 新增公告信息
     *
     * @param bulletinInfo
     * @return
     */
    @PostMapping
    public R save(BulletinInfo bulletinInfo) {
        bulletinInfo.setDate(DateUtil.formatDateTime(new Date()));
        return R.ok(bulletinInfoService.save(bulletinInfo));
    }

    /**
     * 修改公告信息
     *
     * @param bulletinInfo
     * @return
     */
    @PutMapping
    public R edit(BulletinInfo bulletinInfo) {
        return R.ok(bulletinInfoService.updateById(bulletinInfo));
    }

    /**
     * 删除公告信息
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(bulletinInfoService.removeByIds(ids));
    }

}
