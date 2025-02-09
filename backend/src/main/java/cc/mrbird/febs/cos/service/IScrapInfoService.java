package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.ScrapInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
public interface IScrapInfoService extends IService<ScrapInfo> {

    /**
     * 分页获取物资报废信息
     *
     * @param page      分页对象
     * @param scrapInfo 物资报废信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryScrapPage(Page<ScrapInfo> page, ScrapInfo scrapInfo);
}
