const util = require('../../utils/util.js');
const api = require('../../config/api.js');
const user = require('../../services/user.js');

//获取应用实例
const app = getApp()
Page({
    data: {
        newGoodsList: [],
        hotGoodsList: [],
        topicList: [],
        brandList: [],
        floorGoods: [],
        bannerList: [],
        channelList: []
    },
    onShareAppMessage: function () {
        return {
            title: 'weshop',
            desc: '严选微信小程序商城',
            path: '/pages/index/index'
        }
    },

    getIndexData: function () {
        let that = this;
		var newData ={"newly":true,"pageNum":1,"pageSize":4};
        util.request(api.goodsList,newData).then(function (res) {
			console.log(res)
            if (res.success) {
                that.setData({
                    newGoodsList: res.data,
                    /* hotGoods: res.data.hotGoodsList,
                    topicList: res.data.topicList,
                    brandList: res.data.brandList,
                    floorGoods: res.data.categoryList,
                    bannerList: res.data.bannerList,
                    channelList: res.data.channelList */
                });
            }
        });
		//
		var hotData={"hot":true,"pageNum":1,"pageSize":4};
		util.request(api.goodsQueryByCriteria,hotData).then(function (res) {
			console.log(res)
		    if (res.success) {
		        that.setData({
		           
		             hotGoods: res.data,
		            /*topicList: res.data.topicList,
		            brandList: res.data.brandList,
		            floorGoods: res.data.categoryList,
		            bannerList: res.data.bannerList,
		            channelList: res.data.channelList */
		        });
		    }
		});
		
		var TopicData={"pageNum":1,"pageSize":10};
		util.request(api.TopicList,TopicData).then(function (res) {
		    if (res.success) {
		        that.setData({
		            topicList: res.data,
		           /* brandList: res.data.brandList,
		            floorGoods: res.data.categoryList,
		            bannerList: res.data.bannerList,
		            channelList: res.data.channelList */
		        });
		    }
		    wx.hideToast();
		});
		
		var brandData={"pageNum":1,"pageSize":10,"newly":true};
		util.request(api.BrandList,brandData).then(function (res) {
		    if (res.success) {
		        that.setData({
		            
		            brandList: res.data,
		            /*floorGoods: res.data.categoryList,
		            bannerList: res.data.bannerList,
		            channelList: res.data.channelList */
		        });
		    }
		   
		});
		util.request(api.CatalogList).then(function (res) {
		    if (res.success) {
		        that.setData({
		            floorGoods: res.data,
		            /*bannerList: res.data.bannerList,
		            channelList: res.data.channelList */
		        });
		    }
		});
		
		var bannerData={"adPositionId":1};
		util.request(api.BannerList,bannerData).then(function (res) {
		    if (res.success) {
		        that.setData({
		            
		           
		            bannerList: res.data,
		            /*channelList: res.data.channelList */
		        });
		    }
		   
		});
		
		util.request(api.ChannelList).then(function (res) {
		    if (res.success) {
		        that.setData({
		            channelList: res.data 
		        });
		    }
		   
		});
		
    },
    onLoad: function (options) {
        this.getIndexData();
    },
    onReady: function () {
        // 页面渲染完成
    },
    onShow: function () {
        // 页面显示
    },
    onHide: function () {
        // 页面隐藏
    },
    onUnload: function () {
        // 页面关闭
    },
})