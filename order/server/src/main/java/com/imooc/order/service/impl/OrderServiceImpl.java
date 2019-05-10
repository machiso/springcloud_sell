package com.imooc.order.service.impl;

import com.imooc.order.VO.ResponseUtil;
import com.imooc.order.enums.OrderStatusEnum;
import com.imooc.order.enums.ResponseEnum;
import com.imooc.order.exception.OrderException;
import com.imooc.order.model.OrderMaster;
import com.imooc.order.utils.KeyGenUnique;
import com.imooc.product.client.ProductClient;
import common.DecreaseStockInput;
import common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import com.imooc.order.DTO.OrderDTO;
import com.imooc.order.enums.PayStatusEnum;
import com.imooc.order.model.OrderDetail;
import com.imooc.order.repository.OrderDetailRepository;
import com.imooc.order.repository.OrderMasterRepository;
import com.imooc.order.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired private OrderMasterRepository orderMasterRepository;
    @Autowired private OrderDetailRepository orderDetailRepository;
    @Resource private ProductClient productClient;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyGenUnique.genereteKeyUnique();

         // 2 查询商品信息（调用商品服务）
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(orderDetail -> orderDetail.getProductId())
                .collect(Collectors.toList());
        List<ProductInfoOutput> productList = productClient.getProductList(productIdList);
        // 3 计算总价
//        List<CartDto> cartDtoList = orderDTO.getOrderDetailList().stream()
//                .map(orderDetail -> new CartDto(orderDetail.getProductId(), orderDetail.getProductQuantity()))
//                .collect(Collectors.toList());
        BigDecimal count = new BigDecimal(BigInteger.ZERO);
        for (ProductInfoOutput productInfo:productList){
            for (OrderDetail orderDetail:orderDTO.getOrderDetailList()){
                if (productInfo.getProductId().equals(orderDetail.getProductId())){
                    count=productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(count);
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyGenUnique.genereteKeyUnique());
                    orderDetailRepository.save(orderDetail);
                }
            }
        }
        // 4 扣库存（调用商品服务）
        List<DecreaseStockInput> cartDtoList = orderDTO.getOrderDetailList().stream()
                .map(e -> new DecreaseStockInput(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(cartDtoList);
        // 5 订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(count);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.UNPAY.getCode());
        orderMasterRepository.save(orderMaster);

        return orderDTO;
    }

    @Override
    public OrderDTO finish(String orderId) {
        //查询订单状态
        Optional<OrderMaster> orderMasterOptional = orderMasterRepository.findById(orderId);
        if (!orderMasterOptional.isPresent()){
            throw new OrderException(ResponseEnum.ORDER_NOT_EXIST);
        }
        OrderMaster orderMaster = orderMasterOptional.get();
        if (orderMaster.getOrderStatus()!=OrderStatusEnum.NEW.getCode()){
            throw new OrderException(ResponseEnum.ORDER_STATUS_ERROR);
        }
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetails)){
            throw new OrderException(ResponseEnum.ORDER_DETAIL_ERROR);
        }
        //修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMasterRepository.save(orderMaster);

        //返回dto对象
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetails);
        return orderDTO;
    }

    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = df.format(new Date());
        System.out.println(format);
    }
}
