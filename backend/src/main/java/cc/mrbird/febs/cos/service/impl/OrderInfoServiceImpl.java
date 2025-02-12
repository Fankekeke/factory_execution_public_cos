package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.dao.OrderInfoMapper;
import cc.mrbird.febs.cos.service.IGoodsBelongService;
import cc.mrbird.febs.cos.service.IOrderInfoService;
import cc.mrbird.febs.cos.service.IStockGoodsOutService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    private final IUserInfoService userInfoService;

    private final IGoodsBelongService goodsBelongService;

    private final IStockGoodsOutService stockGoodsOutService;

    /**
     * 分页获取订单信息
     *
     * @param page      分页对象
     * @param orderInfo 订单信息
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryOrderPage(Page<OrderInfo> page, OrderInfo orderInfo) {
        return baseMapper.queryOrderPage(page, orderInfo);
    }

    /**
     * 新增订单信息
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrder(OrderInfo orderInfo) throws FebsException {
        if (StrUtil.isEmpty(orderInfo.getGoods())) {
            throw new FebsException("请选择商品信息");
        }

        JSONArray array = JSONUtil.parseArray(orderInfo.getGoods());
        List<GoodsBelong> goodsBelongList = JSONUtil.toList(array, GoodsBelong.class);

        // 添加订单信息
        orderInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        orderInfo.setCode("OR-" + System.currentTimeMillis());
        orderInfo.setStatus("0");
        // 获取用户信息
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, orderInfo.getUserId()));
        if (userInfo != null) {
            orderInfo.setUserId(userInfo.getId());
        }

        goodsBelongList.forEach(item -> {
            item.setNum(orderInfo.getCode());
            // 添加所属信息
            GoodsBelong goodsBelong = new GoodsBelong();
            goodsBelong.setNum(orderInfo.getCode());
            goodsBelong.setCreateDate(DateUtil.formatDateTime(new Date()));
            goodsBelong.setAmount(item.getAmount());
            goodsBelong.setName(item.getName());
            goodsBelong.setPrice(item.getPrice());
            goodsBelong.setType(item.getType());
            goodsBelong.setTypeId(item.getTypeId());
            goodsBelong.setUnit(item.getUnit());
            goodsBelongService.save(goodsBelong);
            // 计算总订单金额
            orderInfo.setOrderPrice(orderInfo.getOrderPrice().add(item.getPrice().multiply(BigDecimal.valueOf(item.getAmount()))));

        });
        return this.save(orderInfo);
    }

    /**
     * 订单支付后回调-更新订单状态
     *
     * @param orderCode 订单编号
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean payment(String orderCode) {
        // 获取订单信息
        OrderInfo orderInfo = this.getOne(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getCode, orderCode));
        // 库房库存减少
        StockGoodsOut stockGoodsOut = new StockGoodsOut();
        stockGoodsOut.setUserId(orderInfo.getUserId());
        stockGoodsOut.setPrice(orderInfo.getOrderPrice());
        stockGoodsOut.setCustodian("管理员");
        stockGoodsOut.setHandler("管理员");
        stockGoodsOut.setCreateDate(DateUtil.formatDateTime(new Date()));
        stockGoodsOut.setOrderId(orderInfo.getId());
        stockGoodsOut.setGoods(orderInfo.getGoods());

        stockGoodsOutService.stockOut(stockGoodsOut);
        return this.update(Wrappers.<OrderInfo>lambdaUpdate().set(OrderInfo::getStatus, 1).eq(OrderInfo::getCode, orderCode));
    }
}
