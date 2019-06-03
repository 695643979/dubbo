package com.pinghua.web.data.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 基础Mapper接口
 *
 * @author Waylon
 * @date 2018/7/12
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
