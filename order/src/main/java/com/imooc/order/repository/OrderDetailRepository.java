package com.imooc.order.repository;

import com.imooc.order.model.OrderDetail;
import com.imooc.order.model.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
}
