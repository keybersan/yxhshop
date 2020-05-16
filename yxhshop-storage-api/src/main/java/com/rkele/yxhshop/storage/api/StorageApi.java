package com.rkele.yxhshop.storage.api;


import com.rkele.yxhshop.storage.fallback.StorageApiFallback;
import com.rkele.yxhshop.storage.po.Storage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.common.utils.Result;

import java.io.IOException;

@FeignClient(value = "yxhshop-storage", path = "storage", fallback = StorageApiFallback.class)
public interface StorageApi extends Api<Storage> {

    @PostMapping("/upload")
    Result<String> upload(MultipartFile file) throws IOException;

    @GetMapping("/fetch/{key:.+}")
    ResponseEntity<Resource> fetch(@PathVariable String key);

    @GetMapping("/download/{key:.+}")
    ResponseEntity<Resource> download(@PathVariable String key);
}
