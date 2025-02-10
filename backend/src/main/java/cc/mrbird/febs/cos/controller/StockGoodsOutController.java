package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.GoodsRequest;
import cc.mrbird.febs.cos.entity.StockGoodsOut;
import cc.mrbird.febs.cos.entity.StockOut;
import cc.mrbird.febs.cos.service.IGoodsRequestService;
import cc.mrbird.febs.cos.service.IStockGoodsOutService;
import cc.mrbird.febs.cos.service.IStockOutService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/stock-goods-out")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StockGoodsOutController {

    private final IGoodsRequestService goodsRequestService;

    private final IStockGoodsOutService stockOutService;

    /**
     * 库房出库
     *
     * @param stockOut
     * @return
     */
    @PostMapping("/stockOut")
    public R stockOut(StockGoodsOut stockOut) {
        return R.ok(stockOutService.stockOut(stockOut));
    }
}
