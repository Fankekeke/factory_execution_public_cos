package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.dao.StockGoodsInfoMapper;
import cc.mrbird.febs.cos.service.IGoodsBelongService;
import cc.mrbird.febs.cos.service.IStockGoodsInfoService;
import cc.mrbird.febs.cos.service.IStockGoodsPutService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StockGoodsInfoServiceImpl extends ServiceImpl<StockGoodsInfoMapper, StockGoodsInfo> implements IStockGoodsInfoService {

    private final IGoodsBelongService goodsBelongService;

    private final IStockGoodsPutService stockGoodsPutService;

    /**
     * 分页获取物资商品库房信息
     *
     * @param page           分页对象
     * @param stockGoodsInfo 物资商品库房信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryStockGoodsPage(Page<StockGoodsInfo> page, StockGoodsInfo stockGoodsInfo) {
        return baseMapper.queryStockGoodsPage(page, stockGoodsInfo);
    }

    @Override
    public Boolean stockPut(String goods, String custodian, String putUser, String content, BigDecimal price) {
        // 添加入库单
        StockGoodsPut stockPut = new StockGoodsPut();
        stockPut.setContent(content);
        stockPut.setCreateDate(DateUtil.formatDateTime(new Date()));
        stockPut.setCustodian(custodian);
        stockPut.setPutUser(putUser);
        stockPut.setPrice(price);
        stockPut.setNum("PUT-GOODS-" + System.currentTimeMillis());
        stockGoodsPutService.save(stockPut);

        // 添加入库
        JSONArray array = JSONUtil.parseArray(goods);
        List<GoodsBelong> goodsBelongList = JSONUtil.toList(array, GoodsBelong.class);
        goodsBelongList.forEach(item -> {
            item.setCreateDate(DateUtil.formatDateTime(new Date()));
            item.setNum(stockPut.getNum());
            // 判断库房物品是否存在
            StockGoodsInfo stockInfo = this.getOne(Wrappers.<StockGoodsInfo>lambdaQuery().eq(StockGoodsInfo::getName, item.getName()).eq(StockGoodsInfo::getTypeId, item.getTypeId()).eq(StockGoodsInfo::getIsIn, 0));
            if (stockInfo != null) {
                // 更改库房数据
                this.update(Wrappers.<StockGoodsInfo>lambdaUpdate().set(StockGoodsInfo::getAmount, stockInfo.getAmount() + item.getAmount())
                        .set(StockGoodsInfo::getPrice, stockInfo.getPrice())
                        .set(StockGoodsInfo::getStockId, item.getStockId())
                        .eq(StockGoodsInfo::getId, stockInfo.getId()));
            } else {
                // 重新添加库房数据
                StockGoodsInfo stock = new StockGoodsInfo();
                stock.setName(item.getName());
                stock.setAmount(item.getAmount());
                stock.setCreateDate(DateUtil.formatDateTime(new Date()));
                stock.setType(item.getType());
                stock.setTypeId(item.getTypeId());
                stock.setUnit(item.getUnit());
                stock.setPrice(item.getPrice());
                stock.setIsIn(0);
                stock.setStockId(item.getStockId());
                stockInfo = stock;
                this.save(stock);
            }
            // 添加入库记录
            StockGoodsInfo stockInfoPut = new StockGoodsInfo();
            stockInfoPut.setParentId(stockInfo.getId());
            stockInfoPut.setName(item.getName());
            stockInfoPut.setAmount(item.getAmount());
            stockInfoPut.setCreateDate(DateUtil.formatDateTime(new Date()));
            stockInfoPut.setType(item.getType());
            stockInfoPut.setTypeId(item.getTypeId());
            stockInfoPut.setUnit(item.getUnit());
            stockInfoPut.setPrice(item.getPrice());
            stockInfoPut.setIsIn(1);
            this.save(stockInfoPut);

            // 添加所属信息
            GoodsBelong goodsBelong = new GoodsBelong();
            goodsBelong.setNum(stockPut.getNum());
            goodsBelong.setCreateDate(DateUtil.formatDateTime(new Date()));
            goodsBelong.setAmount(item.getAmount());
            goodsBelong.setName(item.getName());
            goodsBelong.setPrice(item.getPrice());
            goodsBelong.setType(item.getType());
            goodsBelong.setTypeId(item.getTypeId());
            goodsBelong.setUnit(item.getUnit());
            goodsBelongService.save(goodsBelong);
        });
        return true;
    }

    @Override
    public IPage<LinkedHashMap<String, Object>> stockInfoDetailPage(Page page, StockGoodsInfo stockGoodsInfo) {
        return baseMapper.stockInfoDetailPage(page, stockGoodsInfo);
    }

    @Override
    public List<LinkedHashMap<String, Object>> stockInfoByList(StockGoodsInfo stockGoodsInfo) {
        return baseMapper.stockInfoByList(stockGoodsInfo);
    }
}
