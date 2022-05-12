#!/bin/bash

# 設定ファイルの上書き
mv -f /clamd.conf /etc/clamav
mv -f /freshclam.conf /etc/clamav
chown -R clamav:clamav /etc/clamav

echo "start: freshclam"
freshclam
echo "start: clamd"
clamd
# javaアプリの実行
sh ./run.sh