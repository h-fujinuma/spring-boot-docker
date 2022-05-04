# spring-boot-docker

## Dockerfileからの起動のさせ方

* Dockerfileがあるディレクトリまで移動
* 下記コマンドを実行
    * ```docker build --build-arg JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar -t <任意のタグ名> .```
    * ```docker run -p 8080:8080 <上記コマンドで命名した任意のタグ名>```
    * ```http://localhost:8080/```にアクセスして動作確認
