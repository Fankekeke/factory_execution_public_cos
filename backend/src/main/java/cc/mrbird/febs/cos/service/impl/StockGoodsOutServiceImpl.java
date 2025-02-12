package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.dao.StockGoodsOutMapper;
import cc.mrbird.febs.cos.service.IGoodsBelongService;
import cc.mrbird.febs.cos.service.IStockGoodsInfoService;
import cc.mrbird.febs.cos.service.IStockGoodsOutService;
import cc.mrbird.febs.cos.service.IStockInfoService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StockGoodsOutServiceImpl extends ServiceImpl<StockGoodsOutMapper, StockGoodsOut> implements IStockGoodsOutService {

    private final IStockGoodsInfoService stockInfoService;

    private final IGoodsBelongService goodsBelongService;

    @Override
    public Boolean stockOut(StockGoodsOut stockOut) {
        // 添加出库单
        stockOut.setCreateDate(DateUtil.formatDateTime(new Date()));
        stockOut.setNum("OUT-GOODS-" + System.currentTimeMillis());
        this.save(stockOut);

        // 出库
        JSONArray array = JSONUtil.parseArray(stockOut.getGoods());
        List<GoodsBelong> goodsBelongList = JSONUtil.toList(array, GoodsBelong.class);
        goodsBelongList.forEach(item -> {
            StockGoodsInfo stockInfo = stockInfoService.getOne(Wrappers.<StockGoodsInfo>lambdaQuery().eq(StockGoodsInfo::getName, item.getName()).eq(StockGoodsInfo::getIsIn, 0));
            // 更改库房数据
            stockInfoService.update(Wrappers.<StockGoodsInfo>lambdaUpdate().set(StockGoodsInfo::getAmount, stockInfo.getAmount() - item.getAmount()).eq(StockGoodsInfo::getId, stockInfo.getId()));
            item.setNum(stockOut.getNum());

            // 添加出库记录
            StockGoodsInfo stockInfoOut = new StockGoodsInfo();
            stockInfoOut.setParentId(stockInfo.getId());
            stockInfoOut.setName(item.getName());
            stockInfoOut.setAmount(item.getAmount());
            stockInfoOut.setCreateDate(DateUtil.formatDateTime(new Date()));
            stockInfoOut.setType(item.getType());
            stockInfoOut.setTypeId(item.getTypeId());
            stockInfoOut.setUnit(item.getUnit());
            stockInfoOut.setPrice(item.getPrice());
            stockInfoOut.setIsIn(2);
            stockInfoService.save(stockInfoOut);

            // 添加所属信息
            GoodsBelong goodsBelong = new GoodsBelong();
            goodsBelong.setNum(stockOut.getNum());
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
}
