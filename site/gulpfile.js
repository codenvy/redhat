var gulp = require('gulp'),
    shell = require('gulp-shell'),      //to run Jekyll
    rename = require("gulp-rename");    //Rename files;

var buildConfig = {
        jekyllStageConfig : "_config.stage.yml", //stage
        jekyllProdConfig : "_config.prod.yml" //prod
    };

var paths = {
        src: 'app/',
         // assembly folders
        prod: './target/prod/',
        stage: './target/stage/',
        // ------------------
        temp: 'target/temp/',
        dist: 'target/dist/', //dist folder
        config: 'assembly/build/' //jekill configurations
};

// --------------------------- Building On-premises SE (standart edition version) ----------------------------- path.prod
//----------------
//----------
gulp.task('prod',
  ['copy_src', //copy src to /target/temp
  'prod_cfg', //copy _config.yml to /target/temp
  'jekyll_prod', //building pages
  'copy_branding',//copy branding folder
  'copy_prod'], //copy prod site to /dist/prod
  function(){

})

// Copies src to temp folder
gulp.task('copy_src', [], function(){
  return gulp.src(paths.src + '**/*.*')
  .pipe(gulp.dest(paths.temp))
})

gulp.task('prod_cfg', function(){
  return gulp.src(paths.config + buildConfig.jekyllProdConfig)
  .pipe(rename('_config.yml'))
  .pipe(gulp.dest(paths.temp))
});

// Ensure waiting for Jekill job finishing
gulp.task('jekyll_prod',['copy_src','prod_cfg'], function () {
         console.log('Jekyll prod......... ');
   return gulp.src(paths.temp+'_config.yml', {read: false})
    .pipe(shell([
      'jekyll build'
    ], {
      cwd: 'target/temp',
      templateData: {
        f: function (s) {
          return s.replace(/$/, '.bak')
        }
      }
    }))
});


// copy branding folder
gulp.task('copy_branding', ['copy_prod'], function(){
  gulp.src([paths.prod+'site/branding/**/*.* '])
  .pipe(gulp.dest(paths.dist+'prod/site/branding/'));
});

gulp.task('copy_prod',
  ['copy_src',
  'prod_cfg',
  'jekyll_prod',
  ], function(){
  gulp.src([paths.prod+'**/*.html', // all HTML
    paths.prod+'**/*.jpg',
    paths.prod+'**/*.ico',
    paths.prod+'**/*.png',
    paths.prod+'**/*.svg',
    paths.prod+'**/*.woff',
    paths.prod+'**/*.woff2',
    paths.prod+'**/*.ttf',
    paths.prod+'**/*.eot',
    paths.prod+'**/*.otf'
    ])
  .pipe(gulp.dest(paths.dist+'prod'));
});

//***************************************************************** On-premises (base version)

// --------------------------- Dev config for LiveReload localhost:8080) -----------------------------
//----------------
//----------
//  Set Up LiveReload (port 35729 which LiveReload uses by default)

// This task creates local server
gulp.task('connect', ['stage'], function() {
  connect.server({
    root: paths.dist+"/stage",
    livereload: true,
    port: 9000
  });
});

gulp.task('watch', function() {
  gulp.watch(paths.src+'/**/*.*', ['stage']);
  watch(paths.dist+"/stage").pipe(connect.reload());
});
// 
gulp.task('lr',['connect', 'watch'],function(){

});
// -------------------- Utils ------------------------
