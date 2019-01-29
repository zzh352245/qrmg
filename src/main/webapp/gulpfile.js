/*
	author: kzhang
	data: 2017-7-20
	desc: 自动压缩，合并，替换前端代码
 */
const gulp = require("gulp"),
	conf = require("./gulpconf"),
	del = require("del"),
	cleanCss = require("gulp-clean-css"),
	autoprefixer = require('gulp-autoprefixer'),
	uglify = require("gulp-uglify"),
	runSequence = require("gulp-sequence"),
	concat = require("gulp-concat"),
	rename = require("gulp-rename"),
	rev = require("gulp-rev"),
	revCollector = require("gulp-rev-collector"),
	minifyhtml = require("gulp-minify-html"),
	path = require("path");

gulp.task("clean",() => {
	// return new Promise((resolve, reject)=> {
	// 	setTimeout( ()=> {
	// 		del(conf.clean.src);
	// 		resolve();
	// 	},100)
	// });
	// 
	return del(conf.clean.src);
})

gulp.task("minifycss", () => {
	return gulp.src(conf.css.src)
		.pipe(rev())
		// .pipe(autoprefixer({
		// 	browsers: ['last 2 versions', 'ie >= 8'],
		// 	cascade: false //取消美化属性值
		// }))
		.pipe(cleanCss({compatibility: 'ie8'}))
		// .pipe(rename({suffix: ".min"}))
		.pipe(gulp.dest(conf.css.dest))
		.pipe(rev.manifest())
		.pipe(gulp.dest(conf.rev.css))
})

gulp.task("minifyjs",() => {
	return gulp.src(conf.js.src)
		// .pipe(rename({suffix: ".min"}))
		.pipe(rev())
		.pipe(uglify({
			ie8: true
		}))
		.pipe(gulp.dest(conf.js.dest))
		// .pipe(concat("all.js"))
		.pipe(rev.manifest())
		.pipe(gulp.dest(conf.rev.js))
})


gulp.task("revTpl", () => {
	return gulp.src([conf.rev.src, conf.tpl.src])
		.pipe(revCollector({
			replaceReved: true
		}))
		.pipe(gulp.dest(conf.tpl.dest))
})

gulp.task("revHtml", () => {
	return gulp.src([conf.rev.src, conf.html.src])
		.pipe(revCollector({
			replaceReved: true
		}))
		// .pipe(minifyhtml({
			// minifyJS: true,
			// minifyCSS: true,
			// collapseWhitespace: true
		// }))
		.pipe(gulp.dest(conf.html.dest))
})

gulp.task("revRootHtml", () => {
	return gulp.src([conf.rev.src, conf.rootHtml.src])
		.pipe(revCollector({
			replaceReved: true
		}))
		// .pipe(minifyhtml({
			// minifyJS: true,
			// minifyCSS: true,
			// collapseWhitespace: true
		// }))
		.pipe(gulp.dest(conf.rootHtml.dest))
})

gulp.task("miniLib", () => {
	return gulp.src(path.join(__dirname,"dest/lib.js"))
		.pipe(uglify({
			ie8: true
		}))
		.pipe(rename({suffix: ".min"}))
		.pipe(gulp.dest(path.join(__dirname,"dest/")))
})
gulp.task("default", ["miniLib"]);
// gulp.task("default", runSequence("clean", ["minifycss", "minifyjs"], ["revTpl", "revHtml", "revRootHtml"]) )
