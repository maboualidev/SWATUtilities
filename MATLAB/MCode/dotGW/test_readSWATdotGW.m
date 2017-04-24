clear;clc;close all
%%
inputDIR = '../../../SampleFiles/GW';
verbose = true;

%% Getting file list
fileList = struct2table( dir(fullfile(inputDIR,'*.gw')) );
fileList = cellfun(@(c) fullfile(inputDIR,c), ...
                   fileList.name(~fileList.isdir), ...
                   'UniformOutput',false);

%%
output = readSWATdotGW(fileList,verbose)
