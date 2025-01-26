package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.DeviceInfo;
import cc.mrbird.febs.cos.service.IDeviceInfoService;
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
@RequestMapping("/cos/device-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeviceInfoController {

    private final IDeviceInfoService deviceInfoService;

    /**
     * 分页获取设备信息
     *
     * @param page       分页对象
     * @param deviceInfo 设备信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<DeviceInfo> page, DeviceInfo deviceInfo) {
        return R.ok(deviceInfoService.queryDeviceList(page, deviceInfo));
    }

    /**
     * 获取设备信息详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(deviceInfoService.getById(id));
    }

    /**
     * 新增设备信息
     *
     * @param deviceInfo 设备信息
     * @return 结果
     */
    @PostMapping
    public R save(DeviceInfo deviceInfo) {
        deviceInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(deviceInfoService.save(deviceInfo));
    }

    /**
     * 修改设备信息
     *
     * @param deviceInfo 设备信息
     * @return 结果
     */
    @PutMapping
    public R edit(DeviceInfo deviceInfo) {
        return R.ok(deviceInfoService.updateById(deviceInfo));
    }

    /**
     * 删除设备信息
     *
     * @param ids 主键
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(deviceInfoService.removeByIds(ids));
    }
}
