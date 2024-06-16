package com.swpu.cl.contrallor;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swpu.cl.mapper.CommentMapper;
import com.swpu.cl.mapper.ProductMapper;
import com.swpu.cl.pojo.Images;
import com.swpu.cl.pojo.Product;
import com.swpu.cl.utils.ResponseMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * FileName: ProductController
 * Author: hu_sir
 * Date:   2024/6/6 10:15
 * Description:
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@RestController
@RequestMapping("/front/product")
public class ProductController {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private CommentMapper commentMapper;
    // 模糊分页 http://localhost :9001/front/product/productList
    @GetMapping("/productList")
    public ResponseMessage productList(Integer page, Integer size, String param) {
        // 构建分页对象
        Page<Product> productPage = new Page<>(page,size);
        // 调用方法
        Page<Product> productList =  productMapper.productList(productPage,param);
        //  封装数据
        List<Product> products = productList.getRecords();

        for(Product product : products){
            // 如果images不为空,就取出第一张图片
            List<Images> images = product.getImages();
            if(images.size()>0){
                product.setImagesName(images.get(0).getImagesName());
            }
        }
        // 4. 设置封装好的数据内容
        productList.setRecords(products);
        return new ResponseMessage(200,"模糊分页查询的商品信息",productList);
    }

    // 查询单个商品 ：http://localhost :9001/front/product/findProductByProductName
    @GetMapping("/findProductByProductName")
    public ResponseMessage findProductByProductName(String productName){
        //包括了商品信息、商品图片、商品颜色
        Product findProduct = productMapper.findProductByProductName(productName);
        if(Objects.isNull(findProduct))
            return new ResponseMessage(500,"商品已经下架");
        // 通过商品的id查询商品的评论总条数
        Integer commentCount = commentMapper.countCommentByProductId(findProduct.getProductId());
        // 把评论总条数数据内容设置到findProduct对象属性commentCount中
        findProduct.setCommentCount(commentCount);
        return new ResponseMessage(200,"查询单个商品",findProduct);
    }
}
