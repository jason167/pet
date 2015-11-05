echo %classpath%
java -Dlogback.configurationFile=logback.xml -Djava.ext.dirs=libs -Dsf.conn.timeout=10 -Dsf.read.timeout=20 -Dsf.saveto=./images/ -Dsf.url=http://localhost:8081/fsp-loan/id/q ImageUtil emps.txt
pause