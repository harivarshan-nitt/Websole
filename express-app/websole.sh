#!/bin/sh
cd /home/harivarshan
a=1
while [ "$a" -lt 10 ]
do
read MESSAGE<&$NODE_CHANNEL_FD
SUBSTRING=$(echo $MESSAGE| cut -d'"' -f 2)
VAR=$($SUBSTRING)
$SUBSTRING
string=\"$VAR\"
echo $string 1>&$NODE_CHANNEL_FD
done