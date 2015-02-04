#!/bin/sh
if [ "$CI_BRANCH" = "develop" ]
  then
    DEPLOY_USER=$OPEN_SHIFT_DEV_USER
    DEPLOY_HOST=$OPEN_SHIFT_DEV_HOST
elif [ "$CI_BRANCH" = "master" ]
  then
    DEPLOY_USER=$OPEN_SHIFT_PRD_USER
    DEPLOY_HOST=$OPEN_SHIFT_PRD_HOST
fi

cp target/artifact/package/betterlife-erp_0.3.war target/artifact/package/ROOT.war
scp target/artifact/package/ROOT.war $DEPLOY_USER@$DEPLOY_HOST:app-root/dependencies/jbossews/webapps