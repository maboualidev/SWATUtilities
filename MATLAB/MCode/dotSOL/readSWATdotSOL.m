function output = readSWATdotSOL(fileList,verbose)
    if (nargin<2 || isempty(verbose))
        verbose = false;
    end

    %%
    nFiles = numel(fileList);
    if (verbose)
        fprintf('Reading %d files.\n',nFiles);
    end

    %%
    title       = cell(nFiles,1);
    SNAM        = cell(nFiles,1);
    HYDGRP      = cell(nFiles,1);
    SOL_ZMX     = cell(nFiles,1);
    ANION_EXCL  = cell(nFiles,1);
    SOL_CRK     = cell(nFiles,1);
    CommentL1   = cell(nFiles,1);
    SOL_Z       = cell(nFiles,1);
    SOL_BD      = cell(nFiles,1);
    SOL_AWC     = cell(nFiles,1);
    SOL_K       = cell(nFiles,1);
    SOL_CBN     = cell(nFiles,1);
    SOL_CLAY    = cell(nFiles,1);
    SOL_SILT    = cell(nFiles,1);
    SOL_SAND    = cell(nFiles,1);
    SOL_ROCK    = cell(nFiles,1);
    SOL_ALB     = cell(nFiles,1);
    USLE_K      = cell(nFiles,1);
    SOL_EC      = cell(nFiles,1);
    SOL_CAL     = cell(nFiles,1);
    SOL_PH      = cell(nFiles,1);

    %%
    % Disabling the AutoCreate properties of parallel pool
    pSettings = parallel.Settings;
    AutoCreateOriginalValue = pSettings.Pool.AutoCreate;
    pSettings.Pool.AutoCreate = false;
    try
        for fileID = 1:nFiles
            fid = fopen(fileList{fileID},'r');
            if (fid == -1)
                warning('readSWATdotSOL: Could not open %s for reading.',fileList{fileID});
                continue;
            end
            try
                title{fileID} = fgetl(fid);

                tmpLine = fgetl(fid);
                SNAM{fileID} = strtrim(tmpLine(13:end));

                tmpLine = fgetl(fid);
                HYDGRP{fileID} = tmpLine(25);

                tmpLine = fgetl(fid);
                SOL_ZMX{fileID} = str2num(tmpLine(29:35)); %#ok<*ST2NM>

                tmpLine = fgetl(fid);
                ANION_EXCL{fileID} = str2num(tmpLine(52:56));

                tmpLine = fgetl(fid);
                SOL_CRK{fileID} = str2num(tmpLine(34:38));

                CommentL1{fileID} = fgetl(fid);

                tmpLine = fgetl(fid);
                SOL_Z{fileID} = str2num(tmpLine(28:end));

                tmpLine = fgetl(fid);
                SOL_BD{fileID} = str2num(tmpLine(28:end));

                tmpLine = fgetl(fid);
                SOL_AWC{fileID} = str2num(tmpLine(28:end));

                tmpLine = fgetl(fid);
                SOL_K{fileID} = str2num(tmpLine(28:end));

                tmpLine = fgetl(fid);
                SOL_CBN{fileID} = str2num(tmpLine(28:end));

                tmpLine = fgetl(fid);
                SOL_CLAY{fileID} = str2num(tmpLine(28:end));

                tmpLine = fgetl(fid);
                SOL_SILT{fileID} = str2num(tmpLine(28:end));

                tmpLine = fgetl(fid);
                SOL_SAND{fileID} = str2num(tmpLine(28:end));

                tmpLine = fgetl(fid);
                SOL_ROCK{fileID} = str2num(tmpLine(28:end));

                tmpLine = fgetl(fid);
                SOL_ALB{fileID} = str2num(tmpLine(28:end));

                tmpLine = fgetl(fid);
                USLE_K{fileID} = str2num(tmpLine(28:end));

                if (~feof(fid))
                    tmpLine = fgetl(fid);
                    SOL_EC{fileID} = str2num(tmpLine(28:end));
                else
                    fclose(fid);
                    continue;
                end

                if (~feof(fid))
                    tmpLine = fgetl(fid);
                    SOL_CAL{fileID} = str2num(tmpLine(28:end));
                else
                    fclose(fid);
                    continue;
                end

                if (~feof(fid))
                    tmpLine = fgetl(fid);
                    SOL_PH{fileID} = str2num(tmpLine(28:end));
                end % Reason for not having ELSE-CLAUSE: 
                    % this is the last parameter anyway.
                    % Therefore, we need to close after this anyway.

                fclose(fid);
                if (verbose)
                    fprintf('Finished reading %s.\n',fileList{fileID});
                end
            catch ME
                % making sure the file is closed in case any error occured.
                warning('readSWATdotSOL: reading %s was not successfull.', fileList{fileID});
                fclose(fid);
                rethrow(ME);
            end
        end
    catch ME
        % making sure that the AutoCreate property of parallel pool is set to
        % its original status in case of any error.
        pSettings.Pool.AutoCreate = AutoCreateOriginalValue;
        rethrow(ME);
    end

    % Setting back the original status of AutoCreat upon successfully reading
    % all files.
    pSettings.Pool.AutoCreate = AutoCreateOriginalValue;

    %%
    output = table();
    output.fileList     = fileList;
    output.title        = title;
    output.SNAM         = SNAM;
    output.HYDGRP       = HYDGRP;
    output.SOL_ZMX      = SOL_ZMX;
    output.ANION_EXCL   = ANION_EXCL;
    output.SOL_CRK      = SOL_CRK;
    output.CommentL1    = CommentL1;
    output.SOL_Z        = SOL_Z;
    output.SOL_BD       = SOL_BD;
    output.SOL_AWC      = SOL_AWC;
    output.SOL_K        = SOL_K;
    output.SOL_CBN      = SOL_CBN;
    output.SOL_CLAY     = SOL_CLAY;
    output.SOL_SILT     = SOL_SILT;
    output.SOL_SAND     = SOL_SAND;
    output.SOL_ROCK     = SOL_ROCK;
    output.SOL_ALB      = SOL_ALB;
    output.USLE_K       = USLE_K;
    output.SOL_EC       = SOL_EC;
    output.SOL_CAL      = SOL_CAL;
    output.SOL_PH       = SOL_PH;
end










































