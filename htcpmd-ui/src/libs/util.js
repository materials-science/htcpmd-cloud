import { errorCreate } from "@/api/tools";
import cookies from "./util.cookies";
import db from "./util.db";
import log from "./util.log";
import validate from "./util.validate";

const util = {
	cookies,
	db,
	log,
	validate
};

/**
 * @description 更新标题
 * @param {String} title 标题
 */
util.title = function(titleText) {
	const processTitle = process.env.VUE_APP_TITLE || "HTCPMD";
	window.document.title = `${processTitle}${
		titleText ? ` | ${titleText}` : ""
	}`;
};

/**
 * @description 打开新页面
 * @param {String} url 地址
 */
util.open = function(url) {
	var a = document.createElement("a");
	a.setAttribute("href", url);
	a.setAttribute("target", "_blank");
	a.setAttribute("id", "d2admin-link-temp");
	document.body.appendChild(a);
	a.click();
	document.body.removeChild(document.getElementById("d2admin-link-temp"));
};

util.deepcopy = function(obj) {
	try {
		if (obj.constructor === Object) {
			return JSON.parse(JSON.stringify(obj));
		}
	} catch (e) {
		return errorCreate("Not a Object!");
	}
};

util.getWebsocketUrl = url => {
	let _url;
	// Use wss:// if running on https://
	const scheme = window.location.protocol === "https:" ? "wss" : "ws";
	const base_url = `${scheme}://${window.location.host}/ws`;
	if (url === undefined) {
		_url = base_url;
	} else {
		// Support relative URLs
		if (url[0] == "/") {
			_url = `${base_url}${url}`;
		} else {
			_url = url;
		}
	}
	return _url;
};

export default util;
