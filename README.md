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

## curlでtat計測
```curl http://localhost:8080/clamav/instream?filePath=sample.txt -o nul -w "%{time_total}" 2> nul```


## GCP上で動かす
* ```docker build --build-arg JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar --no-cache -t us-central1-docker.pkg.dev/kinetic-hydra-353700/fujinuma-repo/clamav-spring-boot:<任意のタグ> .```でGARと名前を合わせてビルド
* 上記でビルドしたイメージをGARにプッシュ```docker push us-central1-docker.pkg.dev/kinetic-hydra-353700/fujinuma-repo/clamav-spring-boot:<任意のタグ>```
* クラスタの作成```gcloud container clusters create <クラスタ名> --num-nodes=1```
* クラスタ認証情報取得```gcloud container clusters get-credentials <クラスタ名>```
* デプロイの作成```kubectl create deployment <デプロイ名> --image=us-central1-docker.pkg.dev/kinetic-hydra-353700/fujinuma-repo/clamav-spring-boot:<任意のタグ>```
* デプロイの公開```kubectl expose deployment <デプロイ名> --type LoadBalancer --port 80 --target-port 8080```
* 外部IPの確認```kubectl get service <デプロイ名>```
* 接続確認 http://{external IP}/test?test=test