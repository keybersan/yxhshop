nohup java  -Xms128m -Xmx128m -jar zipkin.jar --STORAGE_TYPE=elasticsearch --DES_HOSTS=http://39.108.68.26:9200  --start  > zipkin.out 2>&1 & 
