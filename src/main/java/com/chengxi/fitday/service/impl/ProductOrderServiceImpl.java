package com.chengxi.fitday.service.impl;

import com.chengxi.fitday.entity.ProductOrder;
import com.chengxi.fitday.mapper.ProductOrderMapper;
import com.chengxi.fitday.service.IProductOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrder> implements IProductOrderService {

}
