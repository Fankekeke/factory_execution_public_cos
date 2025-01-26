package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.ProcessInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
public interface IProcessInfoService extends IService<ProcessInfo> {

    /**
     * 分页获取流程管理信息
     *
     * @param page        分页对象
     * @param processInfo 流程管理信息
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryProcessPage(Page<ProcessInfo> page, ProcessInfo processInfo);
}
