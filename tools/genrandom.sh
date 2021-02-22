#!/bin/sh
cd ../src
javac cellsociety/test/RandomGridGenerator.java
for i in $(seq -f "%02g" 21 30)
do
    java cellsociety.test.RandomGridGenerator hexagonal 30 25 false 6 rps | sed 's/Optional\[//' | sed 's/\]//' > tmp.xml
    cat ../data/RPS/head.xml tmp.xml ../data/RPS/tail.xml | sed "s/XX/$i/" > ../data/RPS/RPS$i.xml
done
cd -
