var baseUrl = `https://xxxxxx`;
App({

  url: {


  },
	onLaunch: function () {
		try {
			this.globalData.userInfo = JSON.parse(wx.getStorageSync('userInfo'));
			this.globalData.token = wx.getStorageSync('token');
		} catch (e) {
			console.log(e);
		}
	},

	globalData: {
		userInfo: {
			nickname: '点击登录',
			avatar: 'http://yanxuan.nosdn.127.net/8945ae63d940cc42406c3f67019c5cb6.png'
		},
		token: '',
	},
  post: function (url, data) {
    var promise = new Promise((resolve, reject) => {
      //init
      var that = this;
      var postData = data;
      //网络请求
      wx.request({
        url: url,
        // type: "POST",
        method: 'POST',
        contentType: 'application/json:encoding:utf-8',
        data: postData,
        success: function (res) {
          //console.log(res.data)
          if (res.data.code === 0) {
            resolve(res.data);
          } else {
            reject({
              code: res.data.code,
              msg: res.data.msg
            }); //返回错误提示信息
          }
        },
        error: function (e) {
          reject({
            code: res.data.code,
            msg: "网络出错啦"
          });
        }
      })
    });
    return promise;
  },
})
