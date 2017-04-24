clear;clc;close all
%%
inputDIR = '../../../SampleFiles/SOL';
outputDIR = './';
verbose = true;

%% Getting file list
fileList = struct2table( dir(fullfile(inputDIR,'*.sol')) );
fileList = cellfun(@(c) fullfile(inputDIR,c), ...
                   fileList.name(~fileList.isdir), ...
                   'UniformOutput',false);

%% Getting outputfile list
alternateFileList = struct2table( dir(fullfile(inputDIR,'*.sol')) );
alternateFileList = cellfun(@(c) fullfile(outputDIR,c), ...
                            alternateFileList.name(~alternateFileList.isdir), ...
                            'UniformOutput',false);
                        
%%
SWAT_SoilTBL = readSWATdotSOL(fileList,true);

%%
writeSWATdotSOL(SWAT_SoilTBL,alternateFileList,true)