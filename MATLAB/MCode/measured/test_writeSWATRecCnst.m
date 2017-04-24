clear; clc; close all;
%%
reccnstData = readSWATRecCnst('./inputSample/RECCNST.dat');
writeSWATRecCnst('reccnstOutput.dat',reccnstData);