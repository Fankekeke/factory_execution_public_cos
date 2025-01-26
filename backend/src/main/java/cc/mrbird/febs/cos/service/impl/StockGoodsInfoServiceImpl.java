package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.StockGoodsInfo;
import cc.mrbird.febs.cos.dao.StockGoodsInfoMapper;
import cc.mrbird.febs.cos.service.IStockGoodsInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
public class StockGoodsInfoServiceImpl extends ServiceImpl<StockGoodsInfoMapper, StockGoodsInfo> implements IStockGoodsInfoService {

    /**
     * 分页获取物资商品库房信息
     *
     * @param page           分页对象
     * @param stockGoodsInfo 物资商品库房信息
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryStockGoodsPage(Page<StockGoodsInfo> page, StockGoodsInfo stockGoodsInfo) {
        return baseMapper.queryStockGoodsPage(page, stockGoodsInfo);
    }
}
