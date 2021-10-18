#!/bin/sh
while true; do
echo -e "HTTP/1.1 200 OK\r\n $(cat /home/monte.ott/index.html)" |
nc -lp 2281
sleep 1
done
