package com.imooc.order.repository;

import com.imooc.order.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 廖师兄
 * 2017-12-10 16:12
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
}
