#!/bin/bash
use_cloudfoundry=yes
ext_url="mcdm.servehttp.com"
base_dir=/home/mcdm
db_dir="$base_dir/db_dir"
tmp_dir="$base_dir/tmp_dir"
download_dir="$base_dir/download_dir"
log_host="127.0.0.1"
log_port=514
app_name="mcdm"
memory=512
instance=1
push_app(){
echo -e "Y\n$app_name\nY\nY\n$memory\n$instance\nN\nN\n" | vmc push --no-start
vmc env-add $app_name "use_cloudfoundry=$use_cloudfoundry"
vmc env-add $app_name "db__dir=$db_dir"
vmc env-add $app_name "tmp_dir=$tmp_dir"
vmc env-add $app_name "download_dir=$download_dir"
vmc env-add $app_name "log_host=$log_host"
vmc env-add $app_name "log_port=$log_port"
vmc map $app_name $ext_url
vmc start $app_name	
}

del_app(){
vmc delete $app_name
}




case $1 in
start)
	push_app
;;
stop)
	del_app
;;
restart)
	del_app
	sleep 1
	push_app
;;
*)
	echo "$0 {start|stop|restart}"
;;
esac
