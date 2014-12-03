#!/bin/bash
git submodule sync
git submodule update --init
git config url."https://".insteadOf git://
ln -s .builder/build.xml build.xml
npm install -g bower
cd work/WebContent
bower install
ant build.war junit.run.all 
