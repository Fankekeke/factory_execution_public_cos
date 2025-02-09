package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.DeviceInfo;
import cc.mrbird.febs.cos.entity.ProcessDetail;
import cc.mrbird.febs.cos.entity.ProductionProcessInfo;
import cc.mrbird.febs.cos.dao.ProductionProcessInfoMapper;
import cc.mrbird.febs.cos.service.IDeviceInfoService;
import cc.mrbird.febs.cos.service.IProcessDetailService;
import cc.mrbird.febs.cos.service.IProductionProcessInfoService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductionProcessInfoServiceImpl extends ServiceImpl<ProductionProcessInfoMapper, ProductionProcessInfo> implements IProductionProcessInfoService {

    private final IProcessDetailService processDetailService;

    private final IDeviceInfoService deviceInfoService;

    /**
     * 分页获取生产流程信息
     *
     * @param page        分页对象
     * @param productionProcessInfo 生产流程信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryProductionProcessPage(Page<ProductionProcessInfo> page, ProductionProcessInfo productionProcessInfo) {
        return baseMapper.queryProductionProcessPage(page, productionProcessInfo);
    }

    /**
     * 获取生产流程信息详情
     *
     * @param id 主键
     * @return 结果
     */
    @Override
    public List<ProcessDetail> queryProcessDetail(Integer id) {
        // 获取流程信息
        ProductionProcessInfo productionProcess = this.getById(id);
        List<ProcessDetail> processDetailList = processDetailService.list(Wrappers.<ProcessDetail>lambdaQuery().eq(ProcessDetail::getProcessCode, productionProcess.getCode()).orderByAsc(ProcessDetail::getStepIndex));
        if (CollectionUtil.isEmpty(processDetailList)) {
            return Collections.emptyList();
        }
        // 获取原材料出库
        List<String> outNumList = processDetailList.stream().map(ProcessDetail::getOutNum).collect(Collectors.toList());
        // 获取设备信息
        List<DeviceInfo> deviceInfoList = deviceInfoService.list();
        Map<Integer, String> deviceMap = deviceInfoList.stream().collect(Collectors.toMap(DeviceInfo::getId, DeviceInfo::getDeviceName));
        for (ProcessDetail processDetail : processDetailList) {
            if (processDetail.getDeviceId() == null || CollUtil.isEmpty(deviceMap)) {
                continue;
            }
            processDetail.setDeviceName(deviceMap.get(processDetail.getDeviceId()));
        }
        return processDetailList;
    }
}
