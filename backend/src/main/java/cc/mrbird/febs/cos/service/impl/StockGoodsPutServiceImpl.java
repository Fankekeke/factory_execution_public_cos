package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.StockGoodsPut;
import cc.mrbird.febs.cos.dao.StockGoodsPutMapper;
import cc.mrbird.febs.cos.service.IStockGoodsPutService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
public class StockGoodsPutServiceImpl extends ServiceImpl<StockGoodsPutMapper, StockGoodsPut> implements IStockGoodsPutService {

    /**
     * 分页获取物资商品库房信息
     *
     * @param page           分页对象
     * @param stockGoodsPut 物资商品库房信息
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryStockGoodsPutPage(Page<StockGoodsPut> page, StockGoodsPut stockGoodsPut) {
        return baseMapper.queryStockGoodsPutPage(page, stockGoodsPut);
    }
}
