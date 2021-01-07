package com.wanglejiang.common.mybatisplus;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.wanglejiang.common.exception.InvalidParameterException;

import javax.annotation.Resource;
import java.util.List;

public abstract class BaseRepositoryImpl<M extends MyBaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseRepository<T> {

    @Resource
    protected MyBaseMapper<T> myBaseMapper;

    @Override
    public QueryChainWrapper<T> queryChain() {
        return myBaseMapper.queryChain();
    }

    @Override
    public LambdaQueryChainWrapper<T> lambdaQueryChain() {
        return myBaseMapper.lambdaQueryChain();
    }

    @Override
    public UpdateChainWrapper<T> updateChain() {
        return myBaseMapper.updateChain();
    }

    @Override
    public LambdaUpdateChainWrapper<T> lambdaUpdateChain() {
        return myBaseMapper.lambdaUpdateChain();
    }

    @Override
    public boolean insertBatchSomeColumn(List<T> entityList) {
        return SqlHelper.retBool(myBaseMapper.insertBatchSomeColumn(entityList));
    }

    @Override
    public boolean alwaysUpdateSomeColumnById(T entity) {
        T t = myBaseMapper.selectById(entity.getId());
        InvalidParameterException.checkIsEmpty(t, "ID【{0}】不存在，不能修改", entity.getId());
        entity.setCreatedBy(t.getCreatedBy());
        entity.setCreatedTime(t.getCreatedTime());
        entity.setIsDeleted(t.getIsDeleted());
        return SqlHelper.retBool(myBaseMapper.alwaysUpdateSomeColumnById(entity));
    }

    @Override
    public boolean deleteByIdWithFill(T entity) {
        return SqlHelper.retBool(myBaseMapper.deleteByIdWithFill(entity));
    }

}
