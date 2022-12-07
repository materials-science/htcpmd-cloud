import util from "@/libs/util";

export default ({
	service,
	request,
	serviceForMock,
	requestForMock,
	mock,
	faker,
	tools
}) => ({
	/**
	 * @description crud
	 * @param {Object} query
	 * @param {Object} data
	 */
	GetList(apiPrefix, query = {}) {
		return request({
			url: apiPrefix,
			method: "get",
			params: query
		});
	},

	AddObj(apiPrefix, obj = {}, user_info = true) {
		return request({
			url: apiPrefix,
			method: "post",
			data: user_info
				? Object.assign(obj, { user: util.cookies.get("uuid") })
				: obj
		});
	},

	UpdateObj(apiPrefix, obj = {}, user_info = true) {
		return request({
			url: apiPrefix,
			method: "put",
			data: user_info
				? Object.assign(obj, { user: util.cookies.get("uuid") })
				: obj
		});
	},

	PatchObj(apiPrefix, obj = {}, user_info = true) {
		return request({
			url: apiPrefix,
			method: "patch",
			data: user_info
				? Object.assign(obj, { user: util.cookies.get("uuid") })
				: obj
		});
	},

	DelObj(apiPrefix, id, user_info = true, appendix = "/") {
		return request({
			url: `${apiPrefix}${id}${appendix}`,
			method: "delete",
			data: {
				user: util.cookies.get("uuid")
			}
		});
	},

	PartialDelObj(apiPrefix, obj = {}, user_info = true) {
		return request({
			url: apiPrefix,
			method: "delete",
			data: user_info
				? Object.assign(obj, { user: util.cookies.get("uuid") })
				: obj
		});
	},

	GetObj(apiPrefix, id, appendix = "/", query = {}, user_info = false) {
		return request({
			url: `${apiPrefix}${id}${appendix}`,
			method: "get",
			params: user_info
				? Object.assign(query, { user: util.cookies.get("uuid") })
				: query
		});
	},

	ApiRequest(apiPrefix, method = "get", obj = {}, user_info = true) {
		return request({
			url: apiPrefix,
			method: method,
			data: user_info
				? Object.assign(obj, { user: util.cookies.get("uuid") })
				: obj
		});
	},

	DownloadJsonFile(url) {
		return request({
			url: ".." + url,
			method: "get"
		});
	}
});
