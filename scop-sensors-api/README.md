# server-apiプロジェクト

## Eclipseにプロジェクトインポート
1. 任意のディレクトリにソースをgitリポジトリをクローンする
    ```
    git clone https://xxxx.com/xxx.git
    ```
2. eclipseプロジェクト化を行う
   ```
   $ cd ${cloneしたディレクトリ}/server-api
   $ ./gradlew eclipse
   ```
3. eclipseへインポート
    1. Eclipseを開く
    2. File>Import>Existing Projects into Workspace
    3. ${cloneしたディレクトリ}/server-apiを選択しFinish

4. domaの設定  
domaの設定でエラーが発生するので、以下を行い解決する  
    1. server-apiプロジェクトを右クリック＞Build Path>Configuration Build Bath
    2. Java Build Path>Sourceタブを開く>server-api/.apt_generatedのOutput folderをEdit->bin/mainを入力し反映する。

    参考  
    http://doma.seasar.org/faq.html#development-environment-5

## 開発の進め方
1. ソースコードを修正する
2. jp.co.scop.Application.javaを開く
3. 右クリック＞Run As＞Spring Boot Appで実行する
4. ブラウザやcurlで動作確認する
5. ソース再修正を行う（1に戻る）

## Doma2利用方法
Domaプラグインを導入することで、以下のようにDAOを作成することができる。
* 参考  
https://doma.readthedocs.io/en/stable/getting-started-eclipse/#import-template-project

* 手順
    1. Daoインターフェースを作成する
        ```
        @ConfigAutowireable
        @Dao
        public interface XXXXDao {
        }
        ```
    1. @Select・@Insert・@Update・@Deleteのアノテーションをつけたメソッドを作成する
        ```
        @Select
        List<Company> selectAll();
        ```
    2. Eclipseでアノテーション箇所にカーソルを合わせて右クリック＞Doma>Jump to SQL File
    3. ファイル名を入力し(通常はデフォルトのまま)保存する
    4. SQLファイルをカスタマイズする。
