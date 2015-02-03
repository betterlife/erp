#!/bin/bash
git submodule sync
git submodule update --init
git config url."https://".insteadOf git://
npm install -g bower
ln -s .builder/build.xml build.xml
cd work/WebContent
bower install
cd ../../
ant build.flat
printf "Build Number:\t$CI_BUILD_NUMBER @ $CI_BUILD_URL \nCommit Info :\t $CI_MESSAGE\n"> target/artifact/flat/build.txt
ant build.war junit.run.all
cd work
npm install
npm test
