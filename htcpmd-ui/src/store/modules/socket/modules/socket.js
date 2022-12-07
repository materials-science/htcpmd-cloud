import Vue from "vue";
import { Message } from "element-ui";
export default {
	state: {
		socket: {
			isConnected: false,
			message: "",
			reconnectError: false
		}
	},
	mutations: {
		SOCKET_ONOPEN(state, event) {
			Vue.prototype.$socket = event.currentTarget;
			state.socket.isConnected = true;
			console.log("ws连接成功");
			Message.success(`connected`);
		},
		SOCKET_ONCLOSE(state, event) {
			state.socket.isConnected = false;
			console.error(event);
		},
		SOCKET_ONERROR(state, event) {
			console.error(state, event);
			// Message.error("ws connection fail, retry latter");
            Message.info("ws closed.")
		},
		// default handler called for all methods
		SOCKET_ONMESSAGE(state, message) {
			state.socket.message = message;
		},
		// mutations for reconnect methods
		SOCKET_RECONNECT(state, count) {
			console.info(state, count);
		},
		SOCKET_RECONNECT_ERROR(state) {
			state.socket.reconnectError = true;
		}
	},
	modules: {}
};
