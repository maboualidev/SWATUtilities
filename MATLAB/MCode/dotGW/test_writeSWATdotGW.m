clear;clc;close all
%%
inputDIR = '../../../SampleFiles/GW';
outputDIR = './';

%% Getting file list
fileList = struct2table( dir(fullfile(inputDIR,'*.gw')) );
fileList = cellfun(@(c) fullfile(inputDIR,c), ...
                   fileList.name(~fileList.isdir), ...
                   'UniformOutput',false);
%%
GWFileTBL = readSWATdotGW(fileList);

%%
alternateFileList = struct2table( dir(fullfile(inputDIR,'*.gw')) );
alternateFileList = cellfun(@(c) fullfile(outputDIR,c), ...
                            alternateFileList.name(~alternateFileList.isdir), ...
                            'UniformOutput',false);
%%
writeSWATdotGW(GWFileTBL,alternateFileList)
writeSWATdotGW(GWFileTBL)