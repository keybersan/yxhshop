package com.rkele.yxhshop.storage.fallback;

import com.rkele.yxhshop.common.enums.CommonResultStatus;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.common.utils.Criteria;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.storage.api.StorageApi;
import com.rkele.yxhshop.storage.po.Storage;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by root on 2020/4/1.
 */
@Component
public class StorageApiFallback extends ApiFallback<Storage>  implements StorageApi {


    @Override
    public Result<String> upload(MultipartFile file) throws IOException {
        return Result.failure(CommonResultStatus.REMOTE_SERVICE_ERROR);
    }

    @Override
    public ResponseEntity<Resource> fetch(String key) {
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Resource> download(String key) {
        return ResponseEntity.notFound().build();
    }
}
