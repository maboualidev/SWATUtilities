function writeSWATdotGW(GWFileTBL,alternateFileList)
    %%
    if (nargin<2 || isempty(alternateFileList))
        alternateFileList = GWFileTBL.fileList;
    end
    
    %% Validating the inputs
    if (~istable(GWFileTBL))
        error('writeSWATdotGW: GWFileTBL (first input) must be a table.');
    end
    validateattributes(alternateFileList,{'cell'},{'vector'});
    
    %%
    nFiles = size(GWFileTBL,1);

    %%
    title = GWFileTBL.title;
    SHALLST = GWFileTBL.SHALLST;
    DEEPST = GWFileTBL.DEEPST;
    GW_DELAY = GWFileTBL.GW_DELAY;
    ALPHA_BF = GWFileTBL.ALPHA_BF;
    GWQMN = GWFileTBL.GWQMN;
    GW_REVAP = GWFileTBL.GW_REVAP;
    REVAPMN = GWFileTBL.REVAPMN;
    RCHRG_DP = GWFileTBL.RCHRG_DP;
    GWHT = GWFileTBL.GWHT;
    GW_SPYLD = GWFileTBL.GW_SPYLD;
    SHALLST_N = GWFileTBL.SHALLST_N;
    GWSOLP = GWFileTBL.GWSOLP;
    HLIFE_NGW = GWFileTBL.HLIFE_NGW;
    LAT_ORGN = GWFileTBL.LAT_ORGN;
    LAT_ORGP = GWFileTBL.LAT_ORGP;
    ALPHA_BF_D = GWFileTBL.ALPHA_BF_D;

    %%
    % Disabling the AutoCreate properties of parallel pool
    pSettings = parallel.Settings;
    AutoCreateOriginalValue = pSettings.Pool.AutoCreate;
    pSettings.Pool.AutoCreate = false;
    try
        for fileID = 1:nFiles
            fid = fopen(alternateFileList{fileID},'w');
            if (fid == -1)
                warning('Could not open %s for writing.',alternateFileList{fileID});
                continue;
            end
            try
                fprintf(fid,'%s\n',title{fileID});
                fprintf(fid,'%16.4f%s\n',SHALLST(fileID),'    | SHALLST : Initial depth of water in the shallow aquifer [mm]');
                fprintf(fid,'%16.4f%s\n',DEEPST(fileID),'    | DEEPST : Initial depth of water in the deep aquifer [mm]');
                fprintf(fid,'%16.4f%s\n',GW_DELAY(fileID),'    | GW_DELAY : Groundwater delay [days]');
                fprintf(fid,'%16.4f%s\n',ALPHA_BF(fileID),'    | ALPHA_BF : Baseflow alpha factor [days]');
                fprintf(fid,'%16.4f%s\n',GWQMN(fileID),'    | GWQMN : Threshold depth of water in the shallow aquifer required for return flow to occur [mm]');
                fprintf(fid,'%16.4f%s\n',GW_REVAP(fileID),'    | GW_REVAP : Groundwater "revap" coefficient');
                fprintf(fid,'%16.4f%s\n',REVAPMN(fileID),'    | REVAPMN: Threshold depth of water in the shallow aquifer for "revap" to occur [mm]');
                fprintf(fid,'%16.4f%s\n',RCHRG_DP(fileID),'    | RCHRG_DP : Deep aquifer percolation fraction');
                fprintf(fid,'%16.4f%s\n',GWHT(fileID),'    | GWHT : Initial groundwater height [m]');
                fprintf(fid,'%16.4f%s\n',GW_SPYLD(fileID),'    | GW_SPYLD : Specific yield of the shallow aquifer [m3/m3]');
                fprintf(fid,'%16.4f%s\n',SHALLST_N(fileID),'    | SHALLST_N : Initial concentration of nitrate in shallow aquifer [mg N/l]');
                fprintf(fid,'%16.4f%s\n',GWSOLP(fileID),'    | GWSOLP : Concentration of soluble phosphorus in groundwater contribution to streamflow from subbasin [mg P/l]');
                fprintf(fid,'%16.4f%s\n',HLIFE_NGW(fileID),'    | HLIFE_NGW : Half-life of nitrate in the shallow aquifer [days]');
                fprintf(fid,'%16.4f%s\n',LAT_ORGN(fileID),'    | LAT_ORGN : Organic N in the base flow [mg/L]');
                fprintf(fid,'%16.4f%s\n',LAT_ORGP(fileID),'    | LAT_ORGP : Organic P in the base flow [mg/L]');
                fprintf(fid,'%16.4f%s\n',ALPHA_BF_D(fileID),'    | ALPHA_BF_D : Baseflow alpha factor for deep aquifer [days]');
                fclose(fid);
            catch ME
                fclose(fid);
                rethrow(ME);
            end
        end
    catch ME
        pSettings.Pool.AutoCreate = AutoCreateOriginalValue;
        rethrow(ME);
    end
    pSettings.Pool.AutoCreate = AutoCreateOriginalValue;
end
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    