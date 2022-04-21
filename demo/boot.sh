echo "start: boot.sh"
su root << EOF
echo "start: apt -y update"
apt -y update
echo "start: apt -y install clamav-daemon"
apt -y install clamav-daemon
echo "start edit clamd.conf"
sed -e "s/User clamav/User root/g" /etc/clamav/clamd.conf
if grep -q TCPSocket /etc/clamav/clamd.conf; then
echo "TCPSocket already exists"
else
echo "TCPSocket 3310" >> /etc/clamav/clamd.conf
if grep -q TCPAddr /etc/clamav/clamd.conf; then
echo "TCPAddr already exists"
else
echo "TCPAddr 127.0.0.1" >> /etc/clamav/clamd.conf
echo "satrt: freshclam"
echo freshclam
echo "satrt: clamd"
clamd
echo "start: bootRun"
sh gradlew bootRun
EOF