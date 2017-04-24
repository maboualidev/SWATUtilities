clear;clc;close all
%%
inputDIR = '../../../SampleFiles/SOL';
verbose = true;

%% Getting file list
fileList = struct2table( dir(fullfile(inputDIR,'*.sol')) );
fileList = cellfun(@(c) fullfile(inputDIR,c), ...
                   fileList.name(~fileList.isdir), ...
                   'UniformOutput',false);

%%
output = readSWATdotSOL(fileList,true);