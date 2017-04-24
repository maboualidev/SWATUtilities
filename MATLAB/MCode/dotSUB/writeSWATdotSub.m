function status = writeSWATdotSub(inputData,verbose)
%% writeSWATdotSub receives inputData TBL containing information for 
%  SWAT .SUB files and writes it to the file.
%  It returns status for each row of the inputData table. If it fails to
%  write the row to the file, the status is false, otherwise it is true.
%  the filename/location is stored in inputData.Filename
%
%  NOTE: This function writes the output files in parallel. However, the
%  user need to start a parallel pool manually. If there is no parallel
%  pool started before calling this function it will work in serial.
%% Disabling the AutoCreate properties of parallel pool
pSettings = parallel.Settings;
AutoCreateOriginalValue = pSettings.Pool.AutoCreate;
pSettings.Pool.AutoCreate = false;

%%
if (nargin<2 || isempty(verbose))
    verbose = false;
end

%%
nFiles = size(inputData,1);
status = false(nFiles,1);

parfor idx = 1:nFiles
    tmpData = table2struct(inputData(idx,:));
    
    fid = fopen(tmpData.Filename,'w');
    if (fid==-1)
        warning('Could not open %s for writing. Skipping the file.',tmpData.Filename);
    else
        try
            % Line 1
            if (isfield(tmpData,'title') && ~isempty(tmpData.title))
                fprintf(fid,'%-80s\n',tmpData.title);
            else
                fprintf(fid,'%-80s\n',['.sub file ' datestr(datetime)]);
            end
            
            % Line 2
            fprintf(fid,'%16f    | SUB_KM : Subbasin area [km2]\n',tmpData.SUB_KM);
            
            fprintf(fid,'\nClimate in subbasin\n'); % Line 3 & 4
            fprintf(fid,'%16f    | LATITUDE : Latitude of subbasin [degrees]\n',tmpData.SUB_LAT);
            fprintf(fid,'%16f    | ELEV : Elevation of subbasin [m]\n',tmpData.SUB_ELEV);
            fprintf(fid,'%16d    | IRGAGE: precip gage data used in subbasin\n',tmpData.IRGAGE);
            fprintf(fid,'%16d    | ITGAGE: temp gage data used in subbasin\n',tmpData.ITGAGE);
            fprintf(fid,'%16d    | ISGAGE: solar radiation gage data used in subbasin\n',tmpData.ISGAGE);
            fprintf(fid,'%16d    | IHGAGE: relative humidity gage data used in subbasin\n',tmpData.IHGAGE);
            fprintf(fid,'%16d    | IWGAGE: wind speed gage data used in subbasin\n',tmpData.IWGAGE);
            
            fprintf(fid,'%-16s    | WGNFILE: name of weather generator data file\n',tmpData.WGNFILE);
            fprintf(fid,'%16d    | FCST_REG: Region number used to assign forecast data to the subbasin\n',tmpData.FCST_REG);
            
            fprintf(fid,'Elevation Bands\n| ELEVB: Elevation at center of elevation bands [m]\n'); % Line 14 & 15
            fprintf(fid,'%8.3f',tmpData.ELEVB);fprintf(fid,'\n');
            fprintf(fid,'| ELEVB_FR: Fraction of subbasin area within elevation band\n');
            fprintf(fid,'%8.3f',tmpData.ELEVB_FR);fprintf(fid,'\n');
            fprintf(fid,'| SNOEB: Initial snow water content in elevation band [mm]\n');
            fprintf(fid,'%8.3f',tmpData.SNOEB);fprintf(fid,'\n');
            fprintf(fid,'%16f    | PLAPS : Precipitation lapse rate [mm/km]\n',tmpData.PLAPS);
            fprintf(fid,'%16f    | TLAPS : Temperature lapse rate [°C/km]\n',tmpData.TLAPS);
            fprintf(fid,'%16f    | SNO_SUB : Initial snow water content [mm]\n',tmpData.SNO_SUB);
            
            fprintf(fid,'Tributary Channels\n'); % Line 24
            fprintf(fid,'%16f    | CH_L1 : Longest tributary channel length [km]\n',tmpData.CH_L1);
            fprintf(fid,'%16f    | CH_S1 : Average slope of tributary channel [m/m]\n',tmpData.CH_S1);
            fprintf(fid,'%16f    | CH_W1 : Average width of tributary channel [m]\n',tmpData.CH_W1);
            fprintf(fid,'%16f    | CH_K1 : Effective hydraulic conductivity in tributary channel [mm/hr]\n',tmpData.CH_K1);
            fprintf(fid,'%16f    | CH_N1 : Manning''s "n" value for the tributary channels\n',tmpData.CH_N1);
            
            fprintf(fid,'Impoundments\n'); %Line 30
            fprintf(fid,'%-16s    | PNDFILE: name of subbasin impoundment file\n',tmpData.PNDFILE);
            
            fprintf(fid,'Consumptive Water Use\n'); %Line 32
            fprintf(fid,'%-16s    | WUSFILE: name of subbasin water use file\n',tmpData.WUSFILE);
            
            fprintf(fid,'Climate Change\n'); % Line 34
            fprintf(fid,'%16f    | CO2 : Carbon dioxide concentration [ppmv]\n',tmpData.CO2);
            fprintf(fid,'| RFINC:  Climate change monthly rainfall adjustment (January - June)\n');
            fprintf(fid,'%8.3f',tmpData.RFINC(1:6));fprintf(fid,'\n');
            fprintf(fid,'| RFINC:  Climate change monthly rainfall adjustment (July - December)\n');
            fprintf(fid,'%8.3f',tmpData.RFINC(7:12));fprintf(fid,'\n');
            fprintf(fid,'| TMPINC: Climate change monthly temperature adjustment (January - June)\n');
            fprintf(fid,'%8.3f',tmpData.TMPINC(1:6));fprintf(fid,'\n');
            fprintf(fid,'| TMPINC: Climate change monthly temperature adjustment (July - December)\n');
            fprintf(fid,'%8.3f',tmpData.TMPINC(7:12));fprintf(fid,'\n');
            fprintf(fid,'| RADINC: Climate change monthly radiation adjustment (January - June)\n');
            fprintf(fid,'%8.3f',tmpData.RADINC(1:6));fprintf(fid,'\n');
            fprintf(fid,'| RADINC: Climate change monthly radiation adjustment (July - December)\n');
            fprintf(fid,'%8.3f',tmpData.RADINC(7:12));fprintf(fid,'\n');
            fprintf(fid,'| HUMINC: Climate change monthly humidity adjustment (January - June)\n');
            fprintf(fid,'%8.3f',tmpData.HUMINC(1:6));fprintf(fid,'\n');
            fprintf(fid,'| HUMINC: Climate change monthly humidity adjustment (July - December)\n');
            fprintf(fid,'%8.3f',tmpData.HUMINC(7:12));fprintf(fid,'\n');
            fprintf(fid,'| HRU data\n');
            fprintf(fid,'%16d    | HRUTOT : Total number of HRUs modeled in subbasin\n',tmpData.HRUTOT);
            
            fprintf(fid,'\n'); % Line 54
            fprintf(fid,'HRU: Depressional Storage/Pothole\n');
            fprintf(fid,'\n');
            
            fprintf(fid,'Floodplain\n'); % Line 57
            fprintf(fid, ...
                    '%13s%13s%13s%13s%13s\n', ...
                    tmpData.FLD_HRUFILE, ...
                    tmpData.FLD_MGTFILE, ...
                    tmpData.FLD_SOLFILE, ...
                    tmpData.FLD_CHMFILE, ...
                    tmpData.FLD_GWFILE);
            
            fprintf(fid,'HRU: Riparian\n'); % Line 59
            fprintf(fid, ...
                    '%13s%13s%13s%13s%13s\n', ...
                    tmpData.RIP_HRUFILE, ...
                    tmpData.RIP_MGTFILE, ...
                    tmpData.RIP_SOLFILE, ...
                    tmpData.RIP_CHMFILE, ...
                    tmpData.RIP_GWFILE);
            
            fprintf(fid,'HRU: General\n'); % Line 61
            fprintf(fid, ...
                    '%13s%13s%13s%13s%13s%13s%13s%13s\n', ...
                    tmpData.HRUFILE, ...
                    tmpData.MGTFILE, ...
                    tmpData.SOLFILE, ...
                    tmpData.CHMFILE, ...
                    tmpData.GWFILE, ...
                    tmpData.OPSFILE, ...
                    tmpData.SEPTFILE, ...
                    tmpData.SDRFILE);
            
            if (verbose)
                fprintf('Writing %s is completed.\n',tmpData.Filename);
            end
            fclose(fid);
            status(idx) = true;
        catch
            warning('%s is not written properly. An error happened while writing this file.',tmpData.Filename);
            fclose(fid);
        end
    end
end

%% Reseting AutoCreate properties of parellel pool to its original state
pSettings.Pool.AutoCreate = AutoCreateOriginalValue;
end













































