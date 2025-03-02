package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.dao.ProductionProcessInfoMapper;
import cc.mrbird.febs.cos.service.*;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductionProcessInfoServiceImpl extends ServiceImpl<ProductionProcessInfoMapper, ProductionProcessInfo> implements IProductionProcessInfoService {

    private final IProcessDetailService processDetailService;

    private final IDeviceInfoService deviceInfoService;

    private final IGoodsBelongService goodsBelongService;

    private final IGoodsRequestService goodsRequestService;

    private final IStudentInfoService studentInfoService;

    private final IStockGoodsInfoService stockGoodsInfoService;

    /**
     * 分页获取生产流程信息
     *
     * @param page                  分页对象
     * @param productionProcessInfo 生产流程信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryProductionProcessPage(Page<ProductionProcessInfo> page, ProductionProcessInfo productionProcessInfo) {
        return baseMapper.queryProductionProcessPage(page, productionProcessInfo);
    }

    /**
     * 更新流程状态
     *
     * @param productionProcessInfo 生产流程信息
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateProcessStatus(ProductionProcessInfo productionProcessInfo) throws FebsException {
        // 是否废弃
        if (productionProcessInfo.getCurrentStep() == -1) {
            productionProcessInfo.setStatus("1");
            return this.updateById(productionProcessInfo);
        }

        // 返工
        if ("2".equals(productionProcessInfo.getStatus())) {
            productionProcessInfo.setCurrentStep(productionProcessInfo.getCurrentStep() - 1);
            productionProcessInfo.setStatus("0");
            return this.updateById(productionProcessInfo);
        }
        // 获取详细流程
        List<ProcessDetail> processDetailList = processDetailService.list(Wrappers.<ProcessDetail>lambdaQuery().eq(ProcessDetail::getProcessCode, productionProcessInfo.getCode()).orderByAsc(ProcessDetail::getStepIndex));
        // 提交申请单
        if ("1".equals(productionProcessInfo.getStatus())) {
            // 校验当前流程申请是否审批通过
            ProcessDetail currentProcessDetail = processDetailList.get(productionProcessInfo.getCurrentStep() - 1);
            if (currentProcessDetail == null) {
                throw new FebsException("当前流程不存在！");
            } else {
                String requestNum = currentProcessDetail.getReqNum();
                if (StrUtil.isEmpty(requestNum)) {
                    throw new FebsException("此流程未申请原材料！");
                }
                // 获取申请单信息
                GoodsRequest goodsRequest = goodsRequestService.getOne(Wrappers.<GoodsRequest>lambdaQuery().eq(GoodsRequest::getNum, requestNum));
                if (goodsRequest == null || (goodsRequest.getStep() == 0 || goodsRequest.getStep() == 2)) {
                    throw new FebsException("此申请单未审核成功！");
                }
                // 进入下一阶段
                productionProcessInfo.setCurrentStep(productionProcessInfo.getCurrentStep() + 1);
                productionProcessInfo.setStatus("0");
                return this.updateById(productionProcessInfo);
            }
        }

        return false;
    }

    /**
     * 流程状态申请原材料
     *
     * @param productionProcessInfo 生产流程信息
     * @return 结果
     */
    @Override
    public boolean requestProcessStatus(ProductionProcessInfo productionProcessInfo) throws FebsException {
        if (StrUtil.isEmpty(productionProcessInfo.getGoods())) {
            throw new FebsException("申请原材料不能为空！");
        }
        // 获取详细流程
        List<ProcessDetail> processDetailList = processDetailService.list(Wrappers.<ProcessDetail>lambdaQuery().eq(ProcessDetail::getProcessCode, productionProcessInfo.getCode()).orderByAsc(ProcessDetail::getStepIndex));
        Map<Integer, ProcessDetail> processDetailMap = processDetailList.stream().collect(Collectors.toMap(ProcessDetail::getStepIndex, processDetail -> processDetail));

        // 申请单号
        String requestNum = "REQ-" + System.currentTimeMillis();

        // 设置当前流程申请单号
        ProcessDetail currentProcessDetail = processDetailMap.get(productionProcessInfo.getCurrentStep());
        if (currentProcessDetail != null) {
            currentProcessDetail.setReqNum(requestNum);
            currentProcessDetail.setDeviceId(productionProcessInfo.getDeviceId());
            processDetailService.updateById(currentProcessDetail);
        }

        // 提交申请单
        return this.goodsRequestAdd(productionProcessInfo, requestNum);
    }

    /**
     * 流程完成商品入库
     *
     * @param productionProcessInfo 生产流程信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addWarehouse(ProductionProcessInfo productionProcessInfo) throws FebsException {
        if (StrUtil.isEmpty(productionProcessInfo.getGoods())) {
            throw new FebsException("入库商品不能为空！");
        }

        JSONArray array = JSONUtil.parseArray(productionProcessInfo.getGoods());
        List<GoodsBelong> goodsBelongList = JSONUtil.toList(array, GoodsBelong.class);
        // 计算总价格
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (GoodsBelong item : goodsBelongList) {
            totalPrice = totalPrice.add(item.getPrice().multiply(new BigDecimal(item.getAmount())));
        }

        // 员工信息
        StudentInfo studentInfo = studentInfoService.getOne(Wrappers.<StudentInfo>lambdaQuery().eq(StudentInfo::getUserId, productionProcessInfo.getLaseOperator()));

        // 添加商品入库到GOODS STOCK
        stockGoodsInfoService.stockPut(productionProcessInfo.getGoods(), studentInfo.getName(), studentInfo.getName(), productionProcessInfo.getContent(), totalPrice);

        // 更新流程信息
        productionProcessInfo.setStatus("1");
        productionProcessInfo.setCurrentStep(productionProcessInfo.getCurrentStep() + 1);
        return this.updateById(productionProcessInfo);
    }

    /**
     * 获取生产流程信息详情
     *
     * @param id 主键
     * @return 结果
     */
    @Override
    public List<ProcessDetail> queryProcessDetail(Integer id) {
        // 获取流程信息
        ProductionProcessInfo productionProcess = this.getById(id);
        List<ProcessDetail> processDetailList = processDetailService.list(Wrappers.<ProcessDetail>lambdaQuery().eq(ProcessDetail::getProcessCode, productionProcess.getCode()).orderByAsc(ProcessDetail::getStepIndex));
        if (CollectionUtil.isEmpty(processDetailList)) {
            return Collections.emptyList();
        }
        // 获取原材料申请单号
        List<String> reqNumList = processDetailList.stream().map(ProcessDetail::getReqNum).collect(Collectors.toList());
        Map<String, Integer> goodsRequestMap = new HashMap<>(16);
        if (CollectionUtil.isNotEmpty(reqNumList)) {
            List<GoodsRequest> goodsRequestList = goodsRequestService.list(Wrappers.<GoodsRequest>lambdaQuery().in(GoodsRequest::getNum, reqNumList));
            goodsRequestMap = goodsRequestList.stream().collect(Collectors.toMap(GoodsRequest::getNum, GoodsRequest::getStep));
        }

        // 获取设备信息
        List<DeviceInfo> deviceInfoList = deviceInfoService.list();
        Map<Integer, String> deviceMap = deviceInfoList.stream().collect(Collectors.toMap(DeviceInfo::getId, DeviceInfo::getDeviceName));
        for (ProcessDetail processDetail : processDetailList) {
            // 获取原材料出库详情
            if (StrUtil.isNotEmpty(processDetail.getReqNum())) {
                List<LinkedHashMap<String, Object>> itemList = goodsBelongService.getGoodsByNum(processDetail.getReqNum());
                processDetail.setItemList(itemList);
                // 是否审批通过
                if (CollectionUtil.isNotEmpty(goodsRequestMap)) {
                    processDetail.setPassFlag(goodsRequestMap.get(processDetail.getReqNum()));
                } else {
                    processDetail.setPassFlag(0);
                }
            }
            if (processDetail.getDeviceId() == null || CollUtil.isEmpty(deviceMap)) {
                continue;
            }
            processDetail.setDeviceName(deviceMap.get(processDetail.getDeviceId()));
        }
        return processDetailList;
    }

    /**
     * 生产阶段提交申请
     *
     * @param productionProcessInfo 生产信息
     * @return 结果
     */
    private boolean goodsRequestAdd(ProductionProcessInfo productionProcessInfo, String requestNum) {
        GoodsRequest goodsRequest = new GoodsRequest();
        goodsRequest.setContent(productionProcessInfo.getContent() + "阶段采购申请");
        goodsRequest.setNum(requestNum);
        goodsRequest.setStep(0);
        goodsRequest.setUserId(productionProcessInfo.getUserId());
        goodsRequest.setCreateDate(DateUtil.formatDateTime(new Date()));
        goodsRequest.setGoods(productionProcessInfo.getGoods());
        StudentInfo studentInfo = studentInfoService.getOne(Wrappers.<StudentInfo>lambdaQuery().eq(StudentInfo::getUserId, goodsRequest.getUserId()));
        goodsRequest.setUserId(studentInfo.getId());

        JSONArray array = JSONUtil.parseArray(goodsRequest.getGoods());
        List<GoodsBelong> goodsBelongList = JSONUtil.toList(array, GoodsBelong.class);
        goodsBelongList.forEach(item -> {
            // 添加所属信息
            GoodsBelong goodsBelong = new GoodsBelong();
            goodsBelong.setNum(goodsRequest.getNum());
            goodsBelong.setCreateDate(DateUtil.formatDateTime(new Date()));
            goodsBelong.setAmount(item.getAmount());
            goodsBelong.setName(item.getName());
            goodsBelong.setPrice(item.getPrice());
            goodsBelong.setType(item.getType());
            goodsBelong.setTypeId(item.getTypeId());
            goodsBelong.setUnit(item.getUnit());
            goodsBelongService.save(goodsBelong);
        });
        return goodsRequestService.save(goodsRequest);
    }
}
