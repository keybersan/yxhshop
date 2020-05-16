package com.rkele.yxhshop.storage.service;

import com.rkele.yxhshop.storage.IStorage;
import com.rkele.yxhshop.storage.po.Storage;
import org.springframework.core.io.Resource;
import com.rkele.yxhshop.common.service.IService;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStorageService extends IService<Storage> {

    Storage queryByKey(String key);

    String getActive();

    void setActive(String active);

    IStorage getStorage();

    void setStorage(IStorage storage);

    /**
     * 存储一个文件对象
     *
     * @param inputStream   文件输入流
     * @param contentLength 文件长度
     * @param contentType   文件类型
     * @param fileName      文件索引名
     */
    String store(InputStream inputStream, long contentLength, String contentType, String fileName);

    Stream<Path> loadAll();

    Path load(String keyName);

    Resource loadAsResource(String keyName);

    void delete(String keyName);
}
