package com.chengxi.fitday.service.impl;

import com.chengxi.fitday.entity.Product;
import com.chengxi.fitday.mapper.ProductMapper;
import com.chengxi.fitday.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 成溪科技公司开发
 * @since 2023-04-23
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
