clear; clc; close all;
%%
recdayData = readSWATRecDay('./inputSample/RECDAY.dat');
writeSWATRecDay('recdayOut.dat',recdayData);