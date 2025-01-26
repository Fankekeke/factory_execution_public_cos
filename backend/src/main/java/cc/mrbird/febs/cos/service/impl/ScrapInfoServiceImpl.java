package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ScrapInfo;
import cc.mrbird.febs.cos.dao.ScrapInfoMapper;
import cc.mrbird.febs.cos.service.IScrapInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
public class ScrapInfoServiceImpl extends ServiceImpl<ScrapInfoMapper, ScrapInfo> implements IScrapInfoService {

    /**
     * 分页获取物资报废信息
     *
     * @param page      分页对象
     * @param scrapInfo 物资报废信息
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryScrapPage(Page<ScrapInfo> page, ScrapInfo scrapInfo) {
        return baseMapper.queryScrapPage(page, scrapInfo);
    }
}
