echo "start edit clamd.conf"
sed -i "s/User clamav/User root/" /etc/clamav/clamd.conf
sed -i "s/LocalSocket \/var\/run\/clamav\/clamd.ctl/# LocalSocket \/var\/run\/clamav\/clamd.ctl/" /etc/clamav/clamd.conf
sed -i "s/FixStaleSocket true/# FixStaleSocket true/" /etc/clamav/clamd.conf
sed -i "s/LocalSocketGroup clamav/# LocalSocketGroup clamav/" /etc/clamav/clamd.conf
sed -i "s/LocalSocketMode 666/# LocalSocketMode 666/" /etc/clamav/clamd.conf
if grep -q TCPSocket /etc/clamav/clamd.conf; then \
echo "TCPSocket already exists" ; \
else \
echo "TCPSocket 3310" >> /etc/clamav/clamd.conf ; \
fi
if grep -q TCPAddr /etc/clamav/clamd.conf; then \
echo "TCPAddr already exists" ; \
else \
echo "TCPAddr 127.0.0.1" >> /etc/clamav/clamd.conf ; \
fi
echo "satrt: freshclam"
freshclam
echo "satrt: clamd"
clamd
echo "start: spring"
java -jar app.jar
