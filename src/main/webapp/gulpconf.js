/*
	author: kzhang
	data: 2017-7-21
	desc: 自动化构建路径配置
 */
var path = require("path");
const srcDir = "src/";

function getAbsoluteDir(parame1, parame2) {
	return parame1 instanceof Array ? 
		parame1.map( (data, i) => {
			return path.join(__dirname, data);
		})
		: path.join(__dirname, arguments[1] ? (parame1 + parame2) : parame1);
}

module.exports = exports = {
	clean: {
		src: getAbsoluteDir(["css/**/*.css","dest/**/*.js","tpl"])
	},
	js: {
		src: getAbsoluteDir(srcDir, "dest/**/*.js"),
		dest: getAbsoluteDir("dest")
	},
	css: {
		src: getAbsoluteDir(srcDir,"css/**/*.css"),
		dest: getAbsoluteDir( "css")
	},
	rev: {
		src: getAbsoluteDir(srcDir, "rev/**/*.json"),
		js: getAbsoluteDir(srcDir, "rev/js"),
		css: getAbsoluteDir(srcDir, "rev/css")
	},
	tpl: {
		src: getAbsoluteDir(srcDir, "tpl/**/*.tpl"),
		dest: getAbsoluteDir("tpl")
	},
	html: {
		src: getAbsoluteDir("module/**/*.html"),
		dest: getAbsoluteDir("module")
	},
	rootHtml: {
		src: getAbsoluteDir("*.html"),
		dest: getAbsoluteDir("")
	}
}