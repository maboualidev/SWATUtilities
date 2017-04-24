clear;clc;close all
%%
inputFilename = '../../../SampleFiles/FIG/fig.fig';
outputFilename = './fig.fig';
%%
configurationData=readSWATdotFIG(inputFilename);
clc

%% 
writeSWATdotFIG(configurationData, outputFilename)