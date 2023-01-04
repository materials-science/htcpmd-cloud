import { request } from "@/api/service";
import setting from "@/setting";
const htcpmd_center = setting.api.htcpmd_center;
const apiPrefix = `${htcpmd_center}/structure/`;
export function GetList(query) {
  return request({
    url: apiPrefix,
    method: "get",
    params: query
  });
}

export function AddObj(obj) {
  return request({
    url: apiPrefix,
    method: "post",
    data: obj
  });
}

export function UpdateObj(obj) {
  return request({
    url: apiPrefix,
    method: "put",
    data: obj
  });
}

export function DelObj(id) {
  return request({
    url: apiPrefix + `${id}/`,
    method: "delete"
  });
}

export function GetObj(id) {
  return request({
    url: apiPrefix + `${id}/`,
    method: "get"
  });
}
/**
 *
 * @param {*} url
 * @todo change API in restful style
 */
export function GetFileStream(url) {
  return request({
    url: htcpmd_center + `/common_file/download`,
    method: "get",
    params: { url }
  });
}

/**
 * Post
 */
export function Post(path, data, params = {}) {
  return request({
    url: `${apiPrefix}${path}`,
    method: "post",
    data: data,
    params
  });
}

/**
 * search
 *
 */
export function Search(query) {
  return request({
    url: apiPrefix + "search/",
    method: "post",
    data: query
  });
}

/**
 * downloadText
 */
export function downloadText(url) {
  return request({
    url: url,
    method: "get"
  });
}
