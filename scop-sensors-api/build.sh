#!/usr/bin/bash

DIR=$1
ENV=$2
DIST_MODULE=$3

echo $DIR
echo $ENV

echo "scop-sensors-apiのビルドを開始します"
date

# Javaの設定
export JAVA_HOME=`/usr/libexec/java_home -v 1.8`
java -version

# ディレクトリ移動
cd $DIR

# きれいな状態にするためbuildディレクトリを削除
rm -rf $DIR/build

# ビルド
$DIR/gradlew build

# 設定上書き
cd $DIR/env/$ENV
jar -uvf $DIR/build/libs/$DIST_MODULE ./WEB-INF/
jar -uvf $DIR/build/libs/$DIST_MODULE ./.ebextensions/

# 中身確認のための解凍
cd $DIR/build/libs
jar -xvf $DIST_MODULE WEB-INF/classes/application.yml
jar -xvf $DIST_MODULE .ebextensions/
jar -xvf $DIST_MODULE WEB-INF/classes/public/
# 確認のためのログ出力
echo "----application.yml--------------------" >> build.log
echo "$(cat ./WEB-INF/classes/application.yml)" >> build.log
echo "----.ebextentions----------------------" >> build.log
echo "$(cat .ebextensions/00_options.config)" >> build.log

echo "scop-sensors-apiのビルドが開始しました"
date
