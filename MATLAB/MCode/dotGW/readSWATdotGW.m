function output = readSWATdotGW(fileList,verbose)
    if (nargin<2 || isempty(verbose))
        verbose = false;
    end
    %% Validating the inputs
    validateattributes(fileList,{'cell'},{'vector'});
    validateattributes(verbose,{'logical'},{'scalar'});

    %% getting some info
    nFiles = numel(fileList);

    %% Initializing the variables
    if (verbose)
        disp('Initializing the variables ...');
    end
    title = cell(nFiles,1);
    SHALLST = NaN(nFiles,1);
    DEEPST = NaN(nFiles,1);
    GW_DELAY = NaN(nFiles,1);
    ALPHA_BF = NaN(nFiles,1);
    GWQMN = NaN(nFiles,1);
    GW_REVAP = NaN(nFiles,1);
    REVAPMN = NaN(nFiles,1);
    RCHRG_DP = NaN(nFiles,1);
    GWHT = NaN(nFiles,1);
    GW_SPYLD = NaN(nFiles,1);
    SHALLST_N = NaN(nFiles,1);
    GWSOLP = NaN(nFiles,1);
    HLIFE_NGW = NaN(nFiles,1);
    LAT_ORGN = NaN(nFiles,1);
    LAT_ORGP = NaN(nFiles,1);
    ALPHA_BF_D = NaN(nFiles,1);

    %% 
    % Disabling the AutoCreate properties of parallel pool
    pSettings = parallel.Settings;
    AutoCreateOriginalValue = pSettings.Pool.AutoCreate;
    pSettings.Pool.AutoCreate = false;
    try
        parfor fileID = 1:nFiles
            fid = fopen(fileList{fileID},'r');
            if (fid == -1)
                warning('%s file could not be opened for reading',fileList{fileID});
                continue
            end

            try
                title{fileID} = fgetl(fid);
                SHALLST(fileID) = fscanf(fid,'%f');fgetl(fid);
                DEEPST(fileID) = fscanf(fid,'%f');fgetl(fid);
                GW_DELAY(fileID) = fscanf(fid,'%f');fgetl(fid);
                ALPHA_BF(fileID) = fscanf(fid,'%f');fgetl(fid);
                GWQMN(fileID) = fscanf(fid,'%f');fgetl(fid);
                GW_REVAP(fileID) = fscanf(fid,'%f');fgetl(fid);
                REVAPMN(fileID) = fscanf(fid,'%f');fgetl(fid);
                RCHRG_DP(fileID) = fscanf(fid,'%f');fgetl(fid);
                GWHT(fileID) = fscanf(fid,'%f');fgetl(fid);
                GW_SPYLD(fileID) = fscanf(fid,'%f');fgetl(fid);
                SHALLST_N(fileID) = fscanf(fid,'%f');fgetl(fid);
                GWSOLP(fileID) = fscanf(fid,'%f');fgetl(fid);
                HLIFE_NGW(fileID) = fscanf(fid,'%f');fgetl(fid);
                LAT_ORGN(fileID) = fscanf(fid,'%f');fgetl(fid);
                LAT_ORGP(fileID) = fscanf(fid,'%f');fgetl(fid);
                ALPHA_BF_D(fileID) = fscanf(fid,'%f');

                fclose(fid);
            catch ME
                fclose(fid);
                rethrow(ME);
            end


            if (verbose)
                fprintf('Processing %s completed.\n', fileList{fileID});
            end
        end
    catch ME
        pSettings.Pool.AutoCreate = AutoCreateOriginalValue;
        rethrow(ME);
    end
    pSettings.Pool.AutoCreate = AutoCreateOriginalValue;
%%
    output = table();
    output.fileList = fileList;
    output.title = title;
    output.SHALLST = SHALLST;
    output.DEEPST = DEEPST;
    output.GW_DELAY = GW_DELAY;
    output.ALPHA_BF = ALPHA_BF;
    output.GWQMN = GWQMN;
    output.GW_REVAP = GW_REVAP;
    output.REVAPMN = REVAPMN;
    output.RCHRG_DP = RCHRG_DP;
    output.GWHT = GWHT;
    output.GW_SPYLD = GW_SPYLD;
    output.SHALLST_N = SHALLST_N;
    output.GWSOLP = GWSOLP;
    output.HLIFE_NGW = HLIFE_NGW;
    output.LAT_ORGN = LAT_ORGN;
    output.LAT_ORGP = LAT_ORGP;
    output.ALPHA_BF_D = ALPHA_BF_D;
end
