#!/bin/bash

CLAMD_CONF="/etc/clamav/clamd.conf"

echo "start edit clamd.conf"
sed -i "s/LocalSocket \/var\/run\/clamav\/clamd.ctl/# LocalSocket \/var\/run\/clamav\/clamd.ctl/" $CLAMD_CONF
sed -i "s/FixStaleSocket true/# FixStaleSocket true/" $CLAMD_CONF
sed -i "s/LocalSocketGroup clamav/# LocalSocketGroup clamav/" $CLAMD_CONF
sed -i "s/LocalSocketMode 666/# LocalSocketMode 666/" $CLAMD_CONF
if grep -q TCPSocket $CLAMD_CONF; then \
    echo "TCPSocket already exists" ; \
else \
    echo "TCPSocket 3310" >> $CLAMD_CONF ; \
fi
if grep -q TCPAddr $CLAMD_CONF; then \
    echo "TCPAddr already exists" ; \
else \
    echo "TCPAddr 127.0.0.1" >> $CLAMD_CONF ; \
fi
echo "start: freshclam"
freshclam
echo "start: clamd"
clamd
