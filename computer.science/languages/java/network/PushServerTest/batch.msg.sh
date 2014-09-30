#!/bin/bash

for ((i=0;i<10000;i++))
do
curl 'http://localhost:8081/v1/sendMessage?device_id=device_uuid_100&message=xxxxxx123&admin_token=12345'
done
