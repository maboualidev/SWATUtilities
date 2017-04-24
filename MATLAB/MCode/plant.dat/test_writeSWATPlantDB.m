clear;clc;close all;
%%

inFilename = '../../../SampleFiles/PLANT/plant.dat';
outFilename = './plant.dat';

%%
plantDB = readSWATPlantDB(inFilename);

%%
writeSWATPlantDB(plantDB,outFilename);