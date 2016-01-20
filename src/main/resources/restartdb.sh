!#bin/bash
source /home/oracle/.bash_profile
lsnrctl stop
sqlplus / as sysdba <<EOF
shutdown immediate;
startup;
exit
EOF
lsnrctl start
