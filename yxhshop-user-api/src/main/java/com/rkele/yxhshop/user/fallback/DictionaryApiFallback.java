package com.rkele.yxhshop.user.fallback;

import com.rkele.yxhshop.user.api.DictionaryApi;
import com.rkele.yxhshop.user.po.Dictionary;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;

/**
 * Created by keybersan on 2020/2/9.
 */
@Component
public class DictionaryApiFallback  extends ApiFallback<Dictionary> implements DictionaryApi {
}
