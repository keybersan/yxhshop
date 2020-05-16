package com.rkele.yxhshop.common.fallback;

import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.common.enums.CommonResultStatus;
import com.rkele.yxhshop.common.utils.Criteria;
import com.rkele.yxhshop.common.utils.Result;

import java.util.List;

public abstract class ApiFallback<T> implements Api<T> {

    @Override
    public Result<List<T>> queryAll() {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }

    @Override
    public Result<List<T>> queryList(T entity) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }

    @Override
    public Result<List<T>> queryByCriteria(Criteria<T, Object> criteria) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }

    @Override
    public Result<T> queryOneByCriteria(Criteria<T, Object> criteria) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }

    @Override
    public Result<Integer> countByCriteria(Criteria<T, Object> criteria) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }

    @Override
    public Result<T> queryOne(T entity) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }

    @Override
    public Result<T> queryById(Object id) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }

    @Override
    public Result<Integer> create(T entity) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }

    @Override
    public Result<Integer> createBatch(List<T> list) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }

    @Override
    public Result<Integer> updateAll(T entity) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }

    @Override
    public Result<Integer> updateNotNull(T entity) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }

    @Override
    public Result<Integer> delete(T entity) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }

    @Override
    public Result<Integer> deleteById(Object id) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }

    @Override
    public Result<Integer> count(T entity) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }

    @Override
    public Result<String> sayHello(String name) {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }
}
