package com.rkele.yxhshop.goods.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rkele.yxhshop.common.enums.CommonResultStatus;
import com.rkele.yxhshop.common.exception.YxhshopException;
import com.rkele.yxhshop.common.rocketmq.FootPrintSendCallback;
import com.rkele.yxhshop.common.utils.Criteria;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.goods.api.CategoryApi;
import com.rkele.yxhshop.goods.api.GoodsAttributeApi;
import com.rkele.yxhshop.goods.api.GoodsGalleryApi;
import com.rkele.yxhshop.goods.dto.GoodsAttributeDTO;
import com.rkele.yxhshop.goods.dto.GoodsSpecificationDTO;
import com.rkele.yxhshop.goods.po.*;
import com.rkele.yxhshop.goods.query.GoodsSearchQuery;
import com.rkele.yxhshop.goods.service.*;
import com.rkele.yxhshop.goods.vo.*;
import com.rkele.yxhshop.user.api.CollectApi;
import com.rkele.yxhshop.user.api.CommentApi;
import com.rkele.yxhshop.user.api.CommentPictureApi;
import com.rkele.yxhshop.user.api.UserApi;
import com.rkele.yxhshop.user.po.*;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rkele.yxhshop.common.service.BaseService;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
public class GoodsServiceImpl extends BaseService<Goods> implements GoodsService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GoodsGalleryService goodsGalleryService;
    @Autowired
    private GoodsAttributeService goodsAttributeService;

    @Autowired
    GoodsIssueService goodsIssueService;
    @Autowired
    BrandService brandService;
    @Autowired
    GoodsSpecificationService goodsSpecificationService;
    @Autowired
    ProductService productService;
    @Autowired
    RelatedGoodsService relatedGoodsService;


    @Autowired
    CommentApi commentApi;
    @Autowired
    UserApi userApi;
    @Autowired
    CommentPictureApi commentPictureApi;
    @Autowired
    CollectApi collectApi;
    @Autowired
    private DefaultMQProducer defaultProducer;


    @Override
    public List<Goods> queryListByCategoryIdIn(List<Integer> categoryIdList) {
        Criteria<Goods, Object> criteria = Criteria.of(Goods.class)
            .fields(Goods::getId, Goods::getName, Goods::getListPicUrl, Goods::getRetailPrice)
            .andIn(Goods::getCategoryId, categoryIdList)
            .page(1, 7);
        return this.queryByCriteria(criteria);
    }

    @Override
    public GoodsResultVO queryList(GoodsSearchQuery goodsSearchQuery) {
        //没传分类id就查全部
        Criteria<Goods, Object> criteria = Criteria.of(Goods.class);
        if (goodsSearchQuery.getCategoryId() == null) {
            goodsSearchQuery.setCategoryId(0);
        }
        if (goodsSearchQuery.getBrandId() != null) {
            criteria.andEqualTo(Goods::getBrandId, goodsSearchQuery.getBrandId());
        }
        if (goodsSearchQuery.getKeyword() != null) {
            criteria.andLike(Goods::getName, "%" + goodsSearchQuery.getKeyword() + "%");
        }
        if (goodsSearchQuery.getNewly() != null) {
            criteria.andEqualTo(Goods::getNewly, goodsSearchQuery.getNewly());
        }
        if (goodsSearchQuery.getHot() != null) {
            criteria.andEqualTo(Goods::getHot, goodsSearchQuery.getHot());
        }
        criteria.fields(Goods::getCategoryId);
        List<Integer> categoryIds = this.queryByCriteria(criteria).stream()
            .map(Goods::getCategoryId)
            .collect(Collectors.toList());

        if (categoryIds.isEmpty()) {
            return GoodsResultVO.EMPTY_GOODS_RESULT;
        }
        //查询二级分类的parentIds
        List<Integer> parentIds = categoryService.queryParentIdsByIdIn(categoryIds);
        //一级分类
        List<CategoryFilterVO> categoryFilter = new LinkedList<CategoryFilterVO>() {{
            add(new CategoryFilterVO(0, "全部", false));
            addAll(categoryService.queryByIdIn(parentIds).stream()
                .map(CategoryFilterVO::new)
                .collect(Collectors.toList()));
        }};

        categoryFilter.forEach(categoryFilterDTO -> categoryFilterDTO.setChecked(categoryFilterDTO.getId().equals(goodsSearchQuery.getCategoryId())));

        if (goodsSearchQuery.getCategoryId() != null && goodsSearchQuery.getCategoryId() > 0) {
            //根据一级分类ID查询二级分类ID
            List<Integer> idList = new LinkedList<>();
            idList.add(goodsSearchQuery.getCategoryId());
            idList.addAll(Optional.ofNullable(categoryService.queryIdsByParentId(goodsSearchQuery.getCategoryId())).orElse(Collections.EMPTY_LIST));
            criteria.andIn(Goods::getCategoryId, idList);
        }
        if (goodsSearchQuery.getSort() != null) {
            String orderBy = null;
            switch (goodsSearchQuery.getSort()) {
                case "price":
//                    orderBy = "retail_price";
                    if ("desc".equals(goodsSearchQuery.getOrder())) {
                        criteria.sortDesc(Goods::getRetailPrice);
                    } else {
                        criteria.sort(Goods::getRetailPrice);
                    }
                    break;
                default:
                    orderBy = "id";
                    if ("desc".equals(goodsSearchQuery.getOrder())) {
                        criteria.sortDesc(Goods::getId);
                    } else {
                        criteria.sort(Goods::getId);
                    }
            }
        } else {
            //默认按照添加时间排序
            criteria.sortDesc(Goods::getId);
        }
        criteria.fields(
            Goods::getId,
            Goods::getName,
            Goods::getListPicUrl,
            Goods::getRetailPrice);
         criteria. page(goodsSearchQuery.getPageNum(), goodsSearchQuery.getPageSize());
        List<Goods> goodsList = this.queryByCriteria(criteria);
        return new GoodsResultVO(goodsList, categoryFilter);
    }

    @Override
    public GoodsDetailVO queryGoodsDetail(Integer goodsId,User userInfo) {

        GoodsDetailVO goodsDetailDTO = new GoodsDetailVO();

        Goods goods = this.queryById(goodsId);
        if(goods == null){
            throw  new YxhshopException(CommonResultStatus.RECORD_NOT_EXIST);
        }

        List<GoodsGallery> goodsGalleryVOList = goodsGalleryService.queryList(new GoodsGallery().setGoodsId(goodsId));
        List<GoodsAttributeDTO> goodsAttributeVOList = goodsAttributeService.queryGoodsDetailAttributeByGoodsId(goodsId);
        List<GoodsIssue> goodsIssueList = goodsIssueService.queryAll();
        Brand brand = brandService.queryById(goods.getBrandId());

        //商品评价
        int commentCount = commentApi.countByCriteria(Criteria.of(Comment.class).andEqualTo(Comment::getValueId, goodsId).andEqualTo(Comment::getTypeId, 0)).getData();
        if (commentCount > 0) {
            Comment hotComment = commentApi.queryOneByCriteria(Criteria.of(Comment.class).andEqualTo(Comment::getValueId, goodsId).andEqualTo(Comment::getTypeId, 0).page(1, 1)).getData();
            GoodsDetailVO.CommentVO.CommentDataVO commentData = new GoodsDetailVO.CommentVO.CommentDataVO();
            String content = new String(Base64.getDecoder().decode(hotComment.getContent()));
            User user = userApi.queryById(hotComment.getUserId()).getData();
            List<String> picList = commentPictureApi.queryList(new CommentPicture().setCommentId(hotComment.getId())).getData().stream()
                .map(CommentPicture::getPicUrl)
                .collect(Collectors.toList());

            commentData.setContent(content);
            commentData.setNickname(user.getNickname());
            commentData.setAvatar(user.getAvatar());
            commentData.setPicList(picList);
            commentData.setCreateTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(hotComment.getCreateTime()));
            goodsDetailDTO.setComment(new GoodsDetailVO.CommentVO(commentCount, commentData));
        }

        List<GoodsDetailVO.GoodsSpecificationVO> goodsSpecificationVOList = this.queryGoodsDetailSpecificationByGoodsId(goodsId);
        List<Product> productList = productService.queryList(new Product().setGoodsId(goodsId));

        goodsDetailDTO.setGoods(goods);

        goodsDetailDTO.setBrand(brand);
        goodsDetailDTO.setGoodsGalleryList(goodsGalleryVOList);
        goodsDetailDTO.setGoodsAttributeList(goodsAttributeVOList);
        goodsDetailDTO.setGoodsIssueList(goodsIssueList);
        goodsDetailDTO.setGoodsSpecificationList(goodsSpecificationVOList);
        goodsDetailDTO.setProductList(productList);

        //用户是否收藏
        List<Collect> userCollect =null;
        Result userConlectRest = collectApi.queryByCriteria(Criteria.of(Collect.class).andEqualTo(Collect::getUserId, userInfo.getId()).andEqualTo(Collect::getValueId, goodsId).page(1, 1));
        if(userConlectRest !=null){
            userCollect = (List<Collect>)userConlectRest.getData();
            goodsDetailDTO.setUserHasCollect(userCollect.size() > 0 ? true : false);
        }



        //记录用户足迹 此处使用异步处理
        Footprint footprint = new Footprint()
            .setUserId(userInfo.getId())
            .setGoodsId(goodsId);
        //amqpTemplate.convertAndSend("weshop.topic.footprint", footprint);

        Message msg = new Message("footprintReceiver", // topic
            "footprint", // tag
            "footprint"+userInfo.getId()+goodsId , // key
            JSONObject.toJSONString(footprint).getBytes());// body
        try {
            defaultProducer.send(msg, new FootPrintSendCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goodsDetailDTO;
    }

    private List<GoodsDetailVO.GoodsSpecificationVO> queryGoodsDetailSpecificationByGoodsId(Integer goodsId) {
        List<GoodsDetailVO.GoodsSpecificationVO> goodsSpecificationVOList = new LinkedList<>();
        List<GoodsSpecificationDTO> goodsSpecificationBOList = goodsSpecificationService.queryGoodsDetailSpecificationByGoodsId(goodsId);

        goodsSpecificationBOList.stream()
            .collect(Collectors.toMap(GoodsSpecificationDTO::getSpecificationId, g -> g, (g1, g2) -> g2))
            .forEach((k, v) -> {
                GoodsDetailVO.GoodsSpecificationVO goodsSpecificationVO = new GoodsDetailVO.GoodsSpecificationVO();
                goodsSpecificationVO.setSpecificationId(k);
                goodsSpecificationVO.setName(v.getName());
                goodsSpecificationVO.setValueList(
                    goodsSpecificationBOList.stream()
                        .filter(g -> g.getSpecificationId().equals(v.getSpecificationId()))
                        .collect(Collectors.toList())
                );
                goodsSpecificationVOList.add(goodsSpecificationVO);
            });

        return goodsSpecificationVOList;
    }

    @Override
    public List<GoodsListVO> queryRelatedGoods(Integer goodsId) {
        List<RelatedGoods> relatedGoodsList = relatedGoodsService.queryList(new RelatedGoods().setGoodsId(goodsId));
        List<GoodsListVO> goodsList = null;
        if (relatedGoodsList==null||relatedGoodsList.isEmpty()) {
            //查找同分类下的商品
            Goods goods = this.queryById(goodsId);

            goodsList = this.queryByCriteria(Criteria.of(Goods.class).andEqualTo(Goods::getCategoryId, goods.getCategoryId()).page(1, 8)).stream()
                .map(GoodsListVO::new)
                .collect(Collectors.toList());
        } else {
            List<Integer> goodsIdList = relatedGoodsList.stream()
                .map(RelatedGoods::getGoodsId)
                .collect(Collectors.toList());
            goodsList = this.queryByCriteria(Criteria.of(Goods.class).andIn(Goods::getId, goodsIdList).page(1, 8)).stream()
                .map(GoodsListVO::new)
                .collect(Collectors.toList());
        }
        return goodsList;
    }

    @Override
    public GoodsCategoryVO queryGoodsCategory(Integer categoryId) {
        Category currentCategory = categoryService.queryById(categoryId);
        if(categoryService==null){
            throw  new YxhshopException(CommonResultStatus.RECORD_NOT_EXIST);
        }
        Category parentCategory = categoryService.queryById(currentCategory.getParentId());
        List<Category> brotherCategory = categoryService.queryList(new Category().setParentId(currentCategory.getParentId()));
        return new GoodsCategoryVO(currentCategory, parentCategory, brotherCategory);
    }
}
