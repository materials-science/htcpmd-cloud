module.exports = {
	root: true,
	env: {
		node: true,
		jquery: true
	},
	extends: ["plugin:vue/essential", "@vue/standard"],
	rules: {
		"no-console": process.env.NODE_ENV === "production" ? "error" : "off",
		"no-debugger": process.env.NODE_ENV === "production" ? "error" : "off",
		"no-tabs": "off",
		semi: "off",
		indent: "off",
		quotes: "off",
		"space-before-function-paren": ["error", "never"],
		eqeqeq: "off",
		"no-prototype-builtins": "off",
		"no-unused-vars": "off",
		camelcase: "off",
		"prefer-const": "off"
	},
	parserOptions: {
		parser: "babel-eslint"
	},
	overrides: [
		{
			files: [
				"**/__tests__/*.{j,t}s?(x)",
				"**/tests/unit/**/*.spec.{j,t}s?(x)"
			],
			env: {
				jest: true
			}
		}
	]
};
