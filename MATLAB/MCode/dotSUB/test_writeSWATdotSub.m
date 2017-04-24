clear;clc;close all
%%
filenames = {'../../../SampleFiles/SUB/052680000.sub', ...
             '../../../SampleFiles/SUB/052690000.sub', ...
             '../../../SampleFiles/SUB/052700000.sub', ...
             '../../../SampleFiles/SUB/052710000.sub', ...
             '../../../SampleFiles/SUB/052720000.sub', ...
             '../../../SampleFiles/SUB/052730000.sub'};

%%
inputData = readSWATdotSub(filenames);

for idx = 1:numel(filenames)
    [~,fileBasename, ~]=fileparts(inputData.Filename{idx});
    inputData.Filename{idx} = fullfile('./',[fileBasename '.sub']);
end

%%
clc
status = writeSWATdotSub(inputData);