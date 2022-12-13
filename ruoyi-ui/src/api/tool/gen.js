import request from "@/utils/request";

// 查询生成表数据
export function listTable(query, dataSource = null) {
  return request({
    url: "/code/gen/list",
    method: "get",
    params: query,
    headers: { dataSource },
  });
}
// 查询db数据库列表
export function listDbTable(query, dataSource = null) {
  return request({
    url: "/code/gen/db/list",
    method: "get",
    params: query,
    headers: { dataSource },
  });
}

// 查询表详细信息
export function getGenTable(tableId, dataSource = null) {
  return request({
    url: "/code/gen/" + tableId,
    method: "get",
    headers: { dataSource },
  });
}

// 修改代码生成信息
export function updateGenTable(data, dataSource = null) {
  return request({
    url: "/code/gen",
    method: "put",
    data: data,
    headers: { dataSource },
  });
}

// 导入表
export function importTable(data, dataSource = null) {
  return request({
    url: "/code/gen/importTable",
    method: "post",
    params: data,
    headers: { dataSource },
  });
}

// 预览生成代码
export function previewTable(tableId, dataSource = null) {
  return request({
    url: "/code/gen/preview/" + tableId,
    method: "get",
    headers: { dataSource },
  });
}

// 删除表数据
export function delTable(tableId, dataSource = null) {
  return request({
    url: "/code/gen/" + tableId,
    method: "delete",
    headers: { dataSource },
  });
}

// 生成代码（自定义路径）
export function genCode(tableName, dataSource = null) {
  return request({
    url: "/code/gen/genCode/" + tableName,
    method: "get",
    headers: { dataSource },
  });
}

// 同步数据库
export function synchDb(tableName, dataSource = null) {
  return request({
    url: "/code/gen/synchDb/" + tableName,
    method: "get",
    headers: { dataSource },
  });
}
