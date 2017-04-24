function updateSWATPlantDB(inputPlantDBFile,newCropList,outputPlantDBFile)

    %% Reading the plant database
    fprintf('Reading %s ...\n',inputPlantDBFile);
    plantDB = readSWATPlantDB(inputPlantDBFile);

    % Extracting crop Names
    CropNames = {plantDB(:).CPNM}';

    % Finding the maximum Crop ID (New crop IDs starts from maxCropID+1)
    maxCropID = max([plantDB(:).ICNUM]);

    %% Reading newCropList
    fprintf('Reading %s ...\n',newCropList);
    cropList = readtable(newCropList);
    nNewCrops = size(cropList,1);
    fprintf('- %d new crops to be added to the database.\n',nNewCrops);

    %% Checking if field names are properly set and all required fields exists
    if (~all(ismember({'newName','oldName'},cropList.Properties.VariableNames)))
        error('Could not find "newName" and/or "oldName". (Note the field names are case sensitive)')
    end

    validFieldNames = fieldnames(plantDB);
    validFieldNames{end+1} = 'newName';
    validFieldNames{end+1} = 'oldName';
    if (~all(ismember(cropList.Properties.VariableNames,validFieldNames)))
        error('Some of the property tags/names are not valid names. (Note that names are case sensitive')
    end

    %% Getting list of the properties that needs to be changed
    fields2BChanged = fieldnames(plantDB);
    fields2BChanged = fields2BChanged(ismember(fields2BChanged,cropList.Properties.VariableNames));
    nFields2BChanged = numel(fields2BChanged);

    fprintf('- %d fields are going to be modified:\n',nFields2BChanged);
    for idx = 1:nFields2BChanged
        fprintf('- - %s\n',fields2BChanged{idx});
    end

    %% creating the new crops
    for cropIDX = 1:nNewCrops
        fprintf('Creating %s from %s ...\n',cropList.newName{cropIDX},cropList.oldName{cropIDX});
        % finding the original crop
        newCropStructure = plantDB(strcmp(CropNames,cropList.oldName{cropIDX}));
        fprintf('- Setting the new properties ...\n');
        for fieldIDX = 1 :nFields2BChanged
            fprintf('- - Changing %s from %0.5f to %0.5f ...\n', ...
                    fields2BChanged{fieldIDX},...
                    newCropStructure.(fields2BChanged{fieldIDX}), ...
                    cropList.(fields2BChanged{fieldIDX})(cropIDX));
            newCropStructure.(fields2BChanged{fieldIDX}) = cropList.(fields2BChanged{fieldIDX})(cropIDX);
        end

        fprintf('- Merging the new crop to the plant database ...\n');
        newCropStructure.CPNM  = cropList.newName{cropIDX};
        newCropStructure.ICNUM = maxCropID + cropIDX;
        plantDB(end+1) = newCropStructure; %#ok<AGROW>
        fprintf('- - Added %s with ID %d to the database.\n',...
                newCropStructure.CPNM, ...
                newCropStructure.ICNUM);
    end

    %% Writing the new plantDB
    fprintf('Writing %s ...\n',outputPlantDBFile);
    writeSWATPlantDB(plantDB,outputPlantDBFile);
end








































