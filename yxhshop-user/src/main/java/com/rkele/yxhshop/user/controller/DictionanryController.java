package com.rkele.yxhshop.user.controller;

import com.rkele.yxhshop.user.po.Dictionary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.user.api.DictionaryApi;

/**
 * Created by keybersan on 2020/2/9.
 */
@RestController
@RequestMapping("/dictionary")
public class DictionanryController extends BaseApi<Dictionary> implements DictionaryApi{

}
