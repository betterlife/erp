#!/bin/bash
# git submodule sync
# git submodule update --init
# git config url."https://".insteadOf git://
npm install -g bower
npm install -g codeclimate-test-reporter
ln -s .builder/build.xml build.xml
cd work/WebContent
bower install
cd ../../
ant build.flat
printf "Branch  Name:\t$CI_BRANCH\nBuild Number:\t$CI_BUILD_NUMBER @ $CI_BUILD_URL \nCommit  Info:\t$CI_MESSAGE\n"> target/artifact/flat/build.txt
# ant build.war junit.run.all
ant build.war
# cd work
# npm install
# npm test
# codeclimate < ../target/data/coverage/javascript/lcov.info