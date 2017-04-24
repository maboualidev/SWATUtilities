clear;clc;close all
%% Description:
% This program recieves a SWAT plant database file and a specially crafted
% csv (or xlsx) file in order to add new crops to the database. The new
% crops is based on the crops that already exists in the database. The
% program loads the property of an already existing crops and changes only 
% those properties that are requested to create the new crop.

%%
% Original SWAT plant database
inputPlantDBFile  = '../../../SampleFiles/PLANT/plant.dat';

% The name of the file that instructs the program to create new crops. the
% file must be a csv (or xlsx) file. Note the following items for this
% file:
%   - The file must contain a column tagged as "newName" (case sensitive).
%     This determines what ID should be used for the newly created crop.
%   - The file must contain a column tagged as "oldName" (case sensitive).
%     This determines the original crop that form the base for the newly
%     created crop. 
%   - One or more crop Properties. The names are case sensitive and they
%     must follow the SWAT manual naming convention.
newCropList = '../../../SampleFiles/PLANT/modifiedNewCrops.csv';

% Updated SWAT plant database
outputPlantDBFile = './plant.dat';

%%
updateSWATPlantDB(inputPlantDBFile,newCropList,outputPlantDBFile)