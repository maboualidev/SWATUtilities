clear;clc;close all
%%
filenames = {'../../../SampleFiles/SUB/052680000.sub', ...
             '../../../SampleFiles/SUB/052690000.sub', ...
             '../../../SampleFiles/SUB/052700000.sub', ...
             '../../../SampleFiles/SUB/052710000.sub', ...
             '../../../SampleFiles/SUB/052720000.sub', ...
             '../../../SampleFiles/SUB/052730000.sub'};
output = readSWATdotSub(filenames);

