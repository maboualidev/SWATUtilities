clear;clc;close all;
%%
filename = 'basins2.bsn';
basins = readSWATBasins('../../../SampleFiles/BSN/basins.bsn');

%%
writeSWATBasins(basins,filename);