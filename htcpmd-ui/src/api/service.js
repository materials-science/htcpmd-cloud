import axios from "axios";
import Adapter from "axios-mock-adapter";
import { get, set } from "lodash";
import util from "@/libs/util";
import { errorLog, errorCreate } from "./tools";

/**
 * @description 创建请求实例
 */
function createService() {
  // 创建一个 axios 实例
  const service = axios.create();
  // 请求拦截
  service.interceptors.request.use(
    config => {
      // 是否需要设置 token
      const isToken = (config.headers || {}).isToken === false;
      const token = util.cookies.get("token");
      if (token && !isToken) {
        set(
          config,
          "headers.Authorization",
          `${process.env.VUE_APP_TOKEN_PREFIX} ${token}`
        ); // 让每个请求携带自定义token 请根据实际情况自行修改
      }
      return config;
    },
    error => {
      // 发送失败
      console.log(error);
      return Promise.reject(error);
    }
  );
  // 响应拦截
  service.interceptors.response.use(
    response => {
      // dataAxios 是 axios 返回数据中的 data
      const dataAxios = response.data;
      // 这个状态码是和后端约定的
      const { code } = dataAxios;
      // 根据 code 进行判断
      if (code === undefined) {
        // 如果没有 code 代表这不是项目后端开发的接口 比如可能是 D2Admin 请求最新版本
        return dataAxios;
      } else {
        // 有 code 代表这是一个后端接口 可以进行进一步的判断
        switch (code) {
          case 0:
          case 200:
          case 201:
          case 202:
          case 404:
            console.log(dataAxios);
            // return dataAxios.data
            return dataAxios;
          default:
            // 不是正确的 code
            const msg = dataAxios.msg;
            let msg_str = "";
            if (typeof dataAxios.msg == "object") {
              for (let i in msg) {
                try {
                  msg_str += msg[i].toString() + " | ";
                } catch (e) {
                  console.warn("Parse error...");
                  continue;
                }
              }
            } else {
              msg_str = msg;
            }
            // errorCreate(`${msg_str}: ${response.config.url}`)
            errorCreate(`Error ${code}: ${msg_str}`);
            break;
        }
      }
    },
    error => {
      const status = get(error, "response.status");
      switch (status) {
        case 400:
          error.message = "请求错误";
          break;
        case 401:
          error.message = "登录失效";
          // 删除cookie
          util.cookies.remove("token");
          util.cookies.remove("uuid");
          setTimeout(() => {
            // 跳转路由
            router.push({
              name: "login"
            });
          }, 1500);
          break;
        case 403:
          error.message = "拒绝访问";
          break;
        case 404:
          error.message = `请求内容不存在: ${error.response.config.url}`;
          break;
        case 408:
          error.message = "请求超时";
          break;
        case 500:
          error.message = "服务器内部错误";
          break;
        case 501:
          error.message = "服务未实现";
          break;
        case 502:
          error.message = "网关错误";
          break;
        case 503:
          error.message = "服务不可用";
          break;
        case 504:
          error.message = "网关超时";
          break;
        case 505:
          error.message = "HTTP版本不受支持";
          break;
        default:
          break;
      }
      errorLog(error);
      return Promise.reject(error);
    }
  );
  return service;
}

/**
 * @description 创建请求方法
 * @param {Object} service axios 实例
 */
function createRequestFunction(service) {
  return function(config) {
    const token = util.cookies.get("token");
    const configDefault = {
      headers: {
        "Content-Type": get(config, "headers.Content-Type", "application/json")
      },
      timeout: 5000 * 3600,
      baseURL: process.env.VUE_APP_API,
      data: {}
    };
    return service(Object.assign(configDefault, config));
  };
}

// 用于真实网络请求的实例和请求方法
export const service = createService();
export const request = createRequestFunction(service);

// 用于模拟网络请求的实例和请求方法
export const serviceForMock = createService();
export const requestForMock = createRequestFunction(serviceForMock);

// 网络请求数据模拟工具
export const mock = new Adapter(serviceForMock);
