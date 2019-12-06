package com.secoo.mall.price.dao.mysql.mapper;

import com.secoo.mall.price.dao.mysql.po.Demo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 模版上线前，请删除
 *
 * @author zhanghao
 * @date 2019-10-2613:02
 */
@Mapper
public interface DemoMapper {

    Demo selectById(Long id);

}