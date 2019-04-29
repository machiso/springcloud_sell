package com.imooc.order.service.impl;

import com.imooc.order.DTO.CartDto;
import com.imooc.order.DTO.OrderDTO;
import com.imooc.order.client.ProductClient;
import com.imooc.order.enums.OrderStatusEnum;
import com.imooc.order.enums.PayStatusEnum;
import com.imooc.order.model.OrderDetail;
import com.imooc.order.model.OrderMaster;
import com.imooc.order.model.ProductInfo;
import com.imooc.order.repository.OrderDetailRepository;
import com.imooc.order.repository.OrderMasterRepository;
import com.imooc.order.service.OrderService;
import com.imooc.order.utils.KeyGenUnique;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired private OrderMasterRepository orderMasterRepository;
    @Autowired private OrderDetailRepository orderDetailRepository;
    @Autowired private ProductClient productClient;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyGenUnique.genereteKeyUnique();

         // 2 查询商品信息（调用商品服务）
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(orderDetail -> orderDetail.getProductId())
                .collect(Collectors.toList());
        List<ProductInfo> productList = productClient.getProductList(productIdList);
        // 3 计算总价
//        List<CartDto> cartDtoList = orderDTO.getOrderDetailList().stream()
//                .map(orderDetail -> new CartDto(orderDetail.getProductId(), orderDetail.getProductQuantity()))
//                .collect(Collectors.toList());
        BigDecimal count = new BigDecimal(BigInteger.ZERO);
        for (ProductInfo productInfo:productList){
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
        List<CartDto> cartDtoList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDto(e.getProductId(), e.getProductQuantity()))
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

    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = df.format(new Date());
        System.out.println(format);
    }
}
