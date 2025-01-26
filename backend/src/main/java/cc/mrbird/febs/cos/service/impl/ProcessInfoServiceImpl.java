package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ProcessInfo;
import cc.mrbird.febs.cos.dao.ProcessInfoMapper;
import cc.mrbird.febs.cos.service.IProcessInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
public class ProcessInfoServiceImpl extends ServiceImpl<ProcessInfoMapper, ProcessInfo> implements IProcessInfoService {

    /**
     * 分页获取流程管理信息
     *
     * @param page        分页对象
     * @param processInfo 流程管理信息
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryProcessPage(Page<ProcessInfo> page, ProcessInfo processInfo) {
        return baseMapper.queryProcessPage(page, processInfo);
    }
}
