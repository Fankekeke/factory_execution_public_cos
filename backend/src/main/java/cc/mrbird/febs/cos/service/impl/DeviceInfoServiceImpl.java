package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.DeviceInfo;
import cc.mrbird.febs.cos.dao.DeviceInfoMapper;
import cc.mrbird.febs.cos.service.IDeviceInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
public class DeviceInfoServiceImpl extends ServiceImpl<DeviceInfoMapper, DeviceInfo> implements IDeviceInfoService {

    /**
     * 分页获取设备信息
     *
     * @param page       分页对象
     * @param deviceInfo 设备信息
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryDeviceList(Page<DeviceInfo> page, DeviceInfo deviceInfo) {
        return baseMapper.queryDeviceList(page, deviceInfo);
    }
}
