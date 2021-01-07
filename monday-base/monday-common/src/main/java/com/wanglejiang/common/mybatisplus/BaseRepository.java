package com.wanglejiang.common.mybatisplus;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface BaseRepository<T> extends IService<T> {

    QueryChainWrapper<T> queryChain();

    LambdaQueryChainWrapper<T> lambdaQueryChain();

    UpdateChainWrapper<T> updateChain();

    LambdaUpdateChainWrapper<T> lambdaUpdateChain();

    // 以上4个没有支持 CacheRepository 暂时不要使用，后续可能会去掉 --- 以下4个已支持 CacheRepository

    boolean insertBatchSomeColumn(List<T> entityList);

    boolean alwaysUpdateSomeColumnById(T entity);

    boolean deleteByIdWithFill(T entity);
}
