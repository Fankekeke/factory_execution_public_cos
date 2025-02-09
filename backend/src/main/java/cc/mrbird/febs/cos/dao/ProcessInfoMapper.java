package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ProcessInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
public interface ProcessInfoMapper extends BaseMapper<ProcessInfo> {

    /**
     * 分页获取流程管理信息
     *
     * @param page        分页对象
     * @param processInfo 流程管理信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryProcessPage(Page<ProcessInfo> page, @Param("processInfo") ProcessInfo processInfo);
}
