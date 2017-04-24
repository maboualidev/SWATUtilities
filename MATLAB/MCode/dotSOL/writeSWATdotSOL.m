function writeSWATdotSOL(SWAT_SoilTBL,alternateFileList,verbose)
    %%
    if (nargin<2 || isempty(alternateFileList))
        alternateFileList = SWAT_SoilTBL.fileList;
    end

    if (nargin<3 || isempty(verbose))
        verbose = false;
    end

    %%
    nFiles = size(SWAT_SoilTBL,1);
    if (verbose)
        fprintf('Writing %d files.\n',nFiles);
    end

    %%
    title = SWAT_SoilTBL.title;
    SNAM = SWAT_SoilTBL.SNAM;
    HYDGRP = SWAT_SoilTBL.HYDGRP;
    SOL_ZMX = SWAT_SoilTBL.SOL_ZMX;
    ANION_EXCL = SWAT_SoilTBL.ANION_EXCL;
    SOL_CRK = SWAT_SoilTBL.SOL_CRK;
    CommentL1 = SWAT_SoilTBL.CommentL1;
    SOL_Z = SWAT_SoilTBL.SOL_Z;
    SOL_BD = SWAT_SoilTBL.SOL_BD;
    SOL_AWC = SWAT_SoilTBL.SOL_AWC;
    SOL_K = SWAT_SoilTBL.SOL_K;
    SOL_CBN = SWAT_SoilTBL.SOL_CBN;
    SOL_CLAY = SWAT_SoilTBL.SOL_CLAY;
    SOL_SILT = SWAT_SoilTBL.SOL_SILT;
    SOL_SAND = SWAT_SoilTBL.SOL_SAND;
    SOL_ROCK = SWAT_SoilTBL.SOL_ROCK;
    SOL_ALB = SWAT_SoilTBL.SOL_ALB;
    USLE_K = SWAT_SoilTBL.USLE_K;
    SOL_EC = SWAT_SoilTBL.SOL_EC;
    SOL_CAL = SWAT_SoilTBL.SOL_CAL;
    SOL_PH = SWAT_SoilTBL.SOL_PH;

    %%
    % Disabling the AutoCreate properties of parallel pool
    pSettings = parallel.Settings;
    AutoCreateOriginalValue = pSettings.Pool.AutoCreate;
    pSettings.Pool.AutoCreate = false;
    try
        parfor fileID = 1:nFiles
            fid = fopen(alternateFileList{fileID},'w');
            if (fid ==-1)
                warning('writeSWATdotSOL: could not open %s for reading.',alternateFileList{fileID});
                continue;
            end
            try
                fprintf(fid,'%s\n',title{fileID});

                fprintf(fid,' Soil Name: %s\n', SNAM{fileID});

                fprintf(fid,' Soil Hydrologic Group: %s\n', HYDGRP{fileID});

                fprintf(fid,' Maximum rooting depth(m) : %7.2f\n',SOL_ZMX{fileID});

                fprintf(fid,' Porosity fraction from which anions are excluded: %5.3f\n',ANION_EXCL{fileID});

                fprintf(fid,' Crack volume potential of soil: %5.3f\n',SOL_CRK{fileID});

                fprintf(fid,'%s\n',CommentL1{fileID});

                fprintf(fid,' Depth                [mm]:');
                fprintf(fid,'%12.2f',SOL_Z{fileID});
                fprintf(fid,'\n');

                fprintf(fid,' Bulk Density Moist [g/cc]:');
                fprintf(fid,'%12.2f',SOL_BD{fileID});
                fprintf(fid,'\n');

                fprintf(fid,' Ave. AW Incl. Rock Frag  :');
                fprintf(fid,'%12.2f',SOL_AWC{fileID});
                fprintf(fid,'\n');

                fprintf(fid,' Ksat. (est.)      [mm/hr]:');
                fprintf(fid,'%12.2f',SOL_K{fileID});
                fprintf(fid,'\n');

                fprintf(fid,' Organic Carbon [weight %%]:');
                fprintf(fid,'%12.2f',SOL_CBN{fileID});
                fprintf(fid,'\n');

                fprintf(fid,' Clay           [weight %%]:');
                fprintf(fid,'%12.2f',SOL_CLAY{fileID});
                fprintf(fid,'\n');

                fprintf(fid,' Silt           [weight %%]:');
                fprintf(fid,'%12.2f',SOL_SILT{fileID});
                fprintf(fid,'\n');

                fprintf(fid,' Sand           [weight %%]:');
                fprintf(fid,'%12.2f',SOL_SAND{fileID});
                fprintf(fid,'\n');

                fprintf(fid,' Rock Fragments   [vol. %%]:');
                fprintf(fid,'%12.2f',SOL_ROCK{fileID});
                fprintf(fid,'\n');

                fprintf(fid,' Soil Albedo (Moist)      :');
                fprintf(fid,'%12.2f',SOL_ALB{fileID});
                fprintf(fid,'\n');

                fprintf(fid,' Erosion K                :');
                fprintf(fid,'%12.2f',USLE_K{fileID});
                fprintf(fid,'\n');

                if (~isempty(SOL_EC{fileID}))
                    fprintf(fid,' Salinity (EC, Form 5)    :');
                    fprintf(fid,'%12.2f',SOL_EC{fileID});
                    fprintf(fid,'\n');
                end

                % IMPORTANT NOTE: the SOL_PH and SOL_CAL seems to be interchanged.
                % to be consistent with ArcSWAT we are using the same pattern. but
                % this needs to be further clarified.
                if (~isempty(SOL_CAL{fileID}))
                    fprintf(fid,' Soil pH                  :');
                    fprintf(fid,'%12.2f',SOL_CAL{fileID});
                    fprintf(fid,'\n');
                end

                if (~isempty(SOL_PH{fileID}))
                    fprintf(fid,' Soil CACO3               :');
                    fprintf(fid,'%12.2f',SOL_PH{fileID});
                    fprintf(fid,'\n');
                end

                fclose(fid);
                if (verbose)
                    fprintf('finished writing %s.\n',alternateFileList{fileID});
                end
            catch ME
                warning('writeSWATdotSOL: Reading %s FAILED.',alternateFileList{fileID});
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
end