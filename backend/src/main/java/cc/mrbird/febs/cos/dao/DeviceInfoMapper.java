package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.DeviceInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
public interface DeviceInfoMapper extends BaseMapper<DeviceInfo> {

    /**
     * 分页获取设备信息
     *
     * @param page       分页对象
     * @param deviceInfo 设备信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryDeviceList(Page<DeviceInfo> page, @Param("deviceInfo") DeviceInfo deviceInfo);
}
