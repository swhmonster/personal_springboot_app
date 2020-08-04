module.exports = {
  proxy: {
    '/**': {
      target: 'http://172.23.20.8:18081',
      changeOrigin: false,
      // onProxyReq (proxyReq) {
      //   proxyReq.setHeader('Cookie', 'ajslNew=node01aelbjoui2nls1jsb416pww75u282.node0')
      // },
      // pathRewrite: {
      //   '^/pz-ajbl': 'pz-ajbl'   //重写接口
      // }
    }
  }
}
