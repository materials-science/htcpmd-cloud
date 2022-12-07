import { Message } from "element-ui";
import util from "@/libs/util.js";
/* -------------------------------------------------------------------------- */
// TODO: Package this as a class to allow mutilple connections
// TODO: fix bugs: automatic host and protocol(ws, wss)
/* -------------------------------------------------------------------------- */
let websock = null;
let messageCallback = null;
let errorCallback = null;
let wsUrlPrefix = `ws://${window.location.host}/ws`;
let wsUrl = "";

// 接收ws后端返回的数据
function websocketonmessage(e) {
	messageCallback(JSON.parse(e.data));
}

/**
 * 发起websocket连接
 * @param {Object} agentData 需要向后台传递的参数数据
 */
function websocketSend(agentData) {
	// 加延迟是为了尽量让ws连接状态变为OPEN
	setTimeout(() => {
		// 添加状态判断，当为OPEN时，发送消息
		if (websock.readyState === websock.OPEN) {
			// websock.OPEN = 1
			// 发给后端的数据需要字符串化
			websock.send(JSON.stringify(agentData));
		}
		if (websock.readyState === websock.CLOSED) {
			// websock.CLOSED = 3
			console.log("websock.readyState = 3");
			Message.error("ws connection fail, retry latter");
			errorCallback();
		}
	}, 500);
}

// 关闭ws连接
function websocketclose(e) {
	// e.code === 1000  表示正常关闭。 无论为何目的而创建, 该链接都已成功完成任务。
	// e.code !== 1000  表示非正常关闭。
	if (e && e.code !== 1000) {
		// Message.error(e.code);
		console.error(e);
		errorCallback();
	}
}
// 建立ws连接
function websocketOpen(e) {
	console.log("ws连接成功");
	Message.success(`connected`);
}

// 初始化weosocket
function initWebSocket() {
	if (typeof WebSocket === "undefined") {
		Message.error("您的浏览器不支持WebSocket，无法获取数据");
		return false;
	}

	const token = "Token=" + util.cookies.get("token");
	// ws请求完整地址
	// const requstWsUrl = wsUrl + "?" + token;
	const requstWsUrl = wsUrl;
	websock = new WebSocket(requstWsUrl);

	websock.onmessage = function(e) {
		websocketonmessage(e);
	};
	websock.onopen = function() {
		websocketOpen();
	};
	websock.onerror = function(e) {
		Message.error("ws connection fail, retry latter");
		console.error(e);
		errorCallback();
	};
	websock.onclose = function(e) {
		websocketclose(e);
	};
}

/**
 * 发起websocket请求函数
 * @param {string} url ws连接地址
 * @param {function} successCallback 接收到ws数据，对数据进行处理的回调函数
 * @param {function} errCallback ws连接错误的回调函数
 * @param {Object} agentData 传给后台的参数
 */
export function openWebsocket(
	url,
	successCallback,
	errCallback,
	agentData = null
) {
	wsUrl = wsUrlPrefix + url;
	initWebSocket();
	messageCallback = successCallback;
	errorCallback = errCallback;
	if (agentData != null) {
		websocketSend(agentData);
	}
	return websock;
}

/**
 * 发送websocket消息
 * @param {Object} agentData 传给后台的参数
 */
export function sendWebsocket(agentData) {
	websocketSend(agentData);
}

/**
 * 关闭websocket函数
 */
export function closeWebsocket() {
	if (websock) {
		websock.close(); // 关闭websocket
		websock.onclose(); // 关闭websocket
	}
}
