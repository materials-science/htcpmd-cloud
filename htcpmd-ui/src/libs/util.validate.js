const validate = {};

/**
 * @description twice password confirmation
 * @param {String} pass1
 * @param {String} pass2
 */
validate.password_confirmation = function(rule, pass1, pass2) {
	return pass1 == pass2;
};

/**
 * @description
 * @param {String} value
 */
validate.is_clean_string = function(rule, value, callback) {
	const reg = /^[_a-zA-Z0-9]+$/;
	if (value == "" || value == undefined || value == null) {
		callback();
	} else {
		if (!reg.test(value)) {
			callback(new Error("仅由英文字母，数字以及下划线组成"));
		} else {
			callback();
		}
	}
};

validate.is_clean_string_2 = function(rule, value, callback) {
	const reg = /^[\.\-_a-zA-Z0-9]+$/;
	if (value == "" || value == undefined || value == null) {
		callback();
	} else {
		if (!reg.test(value)) {
			callback(new Error("仅由英文字母，数字以及_-.组成"));
		} else {
			callback();
		}
	}
};

validate.elements_of_array_are_not_null = function(rule, arr, callback) {
	if (Array.isArray(arr)) {
		for (let val of arr) {
			if (val == "") {
				callback(new Error("Null elements of array"));
			}
		}
		callback();
	}
	callback(new Error("Validated object must be an array"));
};

export default validate;
