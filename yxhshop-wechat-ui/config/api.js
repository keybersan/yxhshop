


const base = 'https://xxxxxx/';
module.exports = {
	AdList: base + 'yxhshop-goods/brand/list',  //品牌列表
	goodsList: base + 'yxhshop-goods/goods/list', //首页数据接口
	BannerList: base + 'yxhshop-user/ad/list',  //品牌列表
	ChannelList: base + 'yxhshop-goods/channel/list',  //品牌列表
	CatalogList: base + 'yxhshop-goods/catalog/index',  //分类目录全部分类数据接口
	CatalogCurrent: base + 'yxhshop-goods/category/current',  //分类目录当前分类数据接口
    CatalogList: base + 'yxhshop-goods/category/list',  //品牌列表
	AuthLoginByWeixin: base + 'yxhshop-user/user/login', //微信登录

	GoodsCount: base + 'yxhshop-goods/goods/count',  //统计商品总数
	GoodsList: base + 'yxhshop-goods/goods/list',  //获得商品列表
	GoodsCategory: base + 'yxhshop-goods/goods/category',  //获得分类数据
	GoodsDetail: base + 'yxhshop-goods/goods/detail',  //获得商品的详情
	GoodsNew: base + 'yxhshop-goods/goods/new',  //新品
	GoodsHot: base + 'yxhshop-goods/goods/hot',  //热门
	GoodsRelated: base + 'yxhshop-goods/goods/related',  //商品详情页的关联商品（大家都在看）

	BrandList: base + 'yxhshop-goods/brand/list',  //品牌列表
	BrandDetail: base + 'yxhshop-goods/brand/queryById',  //品牌详情

	CartList: base + 'yxhshop-order/cart/index', //获取购物车的数据
	CartAdd: base + 'yxhshop-order/cart/add', // 添加商品到购物车
	CartUpdate: base + 'yxhshop-order/cart/update', // 更新购物车的商品
	CartDelete: base + 'yxhshop-order/cart/deleteCartGoods', // 删除购物车的商品
	CartChecked: base + 'yxhshop-order/cart/checked', // 选择或取消选择商品
	CartGoodsCount: base + 'yxhshop-order/cart/goods-count', // 获取购物车商品件数
	CartCheckout: base + 'yxhshop-order/cart/checkout', // 下单前信息确认

	OrderSubmit: base + 'yxhshop-order/order/submit', // 提交订单
	PayPrepayId: base + 'yxhshop-order/pay/prepay', //获取微信统一下单prepay_id

	CollectList: base + 'yxhshop-user/collect/list',  //收藏列表
	CollectAddOrDelete: base + 'yxhshop-user/collect/add-or-delete',  //添加或取消收藏

	CommentList: base + 'yxhshop-user/comment/list',  //评论列表
	CommentCount: base + 'yxhshop-user/comment/count',  //评论总数
	CommentPost: base + 'yxhshop-user/comment/post',   //发表评论

	TopicList: base + 'yxhshop-user/topic/list',  //专题列表
	TopicDetail: base + 'yxhshop-user/topic',  //专题详情
	TopicRelated: base + 'yxhshop-user/topic/related',  //相关专题

	SearchIndex: base + 'yxhshop-user/searchHistory/index',  //搜索页面数据
	//SearchResult: BaseUrl + 'search/result',  //搜索数据
	SearchHelper: base + 'yxhshop-user/searchHistory/helper',  //搜索帮助
	SearchClearHistory: base + 'yxhshop-user/searchHistory/clear-history',  //搜索帮助

	AddressList: base + 'yxhshop-user/address/list',  //收货地址列表
	AddressDetail: base + 'yxhshop-user/address/detail',  //收货地址详情
	AddressSave: base + 'yxhshop-user/address/save',  //保存收货地址
	AddressDelete: base + 'yxhshop-user/address/delete',  //保存收货地址

	RegionList: base + 'yxhshop-user/region/list',  //获取区域列表

	OrderList: base + 'yxhshop-order/order/list',  //订单列表
	OrderDetail: base + 'yxhshop-order/order/detail',  //订单详情
	OrderCancel: base + 'yxhshop-order/order/cancel',  //取消订单
	OrderExpress: base + 'yxhshop-order/order/express', //物流详情

	FootprintList: base + 'yxhshop-user/footprint/list',  //足迹列表
	FootprintDelete: base + 'yxhshop-user/footprint/deleteById',  //删除足迹
};
