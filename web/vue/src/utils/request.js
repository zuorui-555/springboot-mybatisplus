import axios from 'axios'

const request = axios.create({
    timeout: 5000
})

//request拦截器
//可以自请求发送前对请求做一些处理
//比如统一加token，对请求参数统一加密
request.interceptors.request.use(
    function (config) {
        config.headers[ 'Content-Type' ] = 'application/json;charset=utf-8';

        //config.headers['token'] = user.token; //设置请求头
        return config
    },
    function (error) {
        return Promise.reject(error)
    }
);

//response 拦截器
//可以在接口响应后统一处理结果
request.interceptors.response.use(
    function (response) {
        let res = response.data;
        //如果返回的是文件
        if (response.config.responseType === 'blob') {
            return res
        }
        //兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        return res
    },
    function (error) {
        console.log('err' + error) //for debug
        return Promise.reject(error)
    }
);

export default request