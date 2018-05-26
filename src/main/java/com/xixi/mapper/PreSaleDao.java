package com.xixi.mapper;

import com.xixi.pojo.PreSaleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xijiaxiang on 2018/5/22.
 */
public interface PreSaleDao {
    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    PreSaleInfo queryById(String id);

    /**
     * 查询所有
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return
     */
    List<PreSaleInfo> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    int insertOneYSZ(PreSaleInfo preSaleInfo);

    /**
     * 查询所有
     * @return
     */
    int queryCount();
}
