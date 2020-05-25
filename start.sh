#!/usr/bin/env bash
#git pull
#
#if [ $? -ne 0 ]; then
#    echo "pull master失败"
#    exit;
#fi
userName=$(echo $USER)
projectPath="/Users/tilenmac/treading/investment/target/investment-0.0.1-SNAPSHOT.jar"
if [ "$userName" == "wangchangdong" ]; then
  projectPath="/Users/wangchangdong/tcoding/read/investment/target/investment-0.0.1-SNAPSHOT.jar"
fi
preBranch=`git rev-parse --abbrev-ref HEAD`
echo "pull $preBranch 代码成功"

if [ "$preBranch" != "master" ]; then
  echo "自动合并代码到master分支"
  git checkout master
  git pull
  git merge $preBranch

  if [ $? -ne 0 ]; then
      echo "自动合并失败，请手动处理"
      exit;
  fi
  git push
fi

mvn clean package  -DskipTests=true
if [ $? -ne 0 ]; then
    echo "打包失败"
    exit;
fi


echo "自动merge成功"

git push

echo "打包成功"



#curl -H "Content-Type:application/json" -X POST --data '{ "msgtype": "text", "text": { "content": "7马上重启，重启大概需要30秒" } }' https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=863f4b91-b03a-48d3-bdf8-2cf89f0ddb6f

#rsync --progress  -e 'ssh -p 10022' florence-starter/target/florence-starter-1.0-SNAPSHOT.jar work@172.17.0.7:/home/work/dev/florence-main/

ps -ef|grep investment|grep -v grep|grep -v guard|awk '{print $2}'|xargs kill -9

java -Xms1024m -Xmx1024m -jar $projectPath

if [ "$preBranch" != "master" ]; then
  git checkout -
  echo "切换回原分支：$preBranch"
fi