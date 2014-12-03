#!/bin/bash
git submodule sync
git submodule update --init
git config url."https://".insteadOf git://
npm install -g bower
ln -s .builder/build.xml build.xml
cd work/WebContent
bower install
cd ../../
ant build.war junit.run.all 
