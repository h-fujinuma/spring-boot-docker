# spring-boot-docker

## ローカルでの起動させかた
```
set GOOGLE_APPLICATION_CREDENTIALS=C:\Users\fujib\Desktop\program\spring-boot-docker\demo\credential.json
gradlew bootRun
```

```http://localhost:8080/test?test=test```にアクセス

## Dockerfileからの起動のさせ方

* Dockerfileがあるディレクトリまで移動
* 下記コマンドを実行
    * ```gradlew build```にてjavaアプリをビルド
    * ```boot.sh```の改行コードがCRLFになっていると動作しないので、注意(LFに直す必要あり)
    * ```docker build --build-arg JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar --no-cache -t <任意のタグ名> .```
    * ```docker run -p 8080:8080 <上記コマンドで命名した任意のタグ名>```
    * ```http://localhost:8080/```にアクセスして動作確認
