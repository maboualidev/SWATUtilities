function output=readSWATdotFIG(filename, verbose)
if (nargin<2 || isempty(verbose))
    verbose = false;
end
masterTimer = tic;
%%
validateattributes(filename,{'char'},{'row'});
validateattributes(verbose,{'logical'},{'scalar'});

%%

if (verbose)
    fprintf('Parsing %s. This may take some time. Please wait.\n',filename);
end
fid = fopen(filename,'r');
if (fid == -1)
    error('could not open %s for reading.',filename);
end

%% Getting file size
if (verbose)
    fileSize = dir(filename);
    fileSize = fileSize.bytes;
end

%% Reading the contents
commandList=[];
commandDetail={};

if (verbose)
    wbHandle = waitbar(0, 'Reading the configuration file. Please wait.\n');
end
while (~feof(fid))
    try
        if (verbose)
            currentPosition = ftell(fid);
            waitbar(currentPosition/fileSize, wbHandle);
        end
        tmpLine = fgetl(fid);
        
        % Getting Command Code
        tmpCMDDetail = [];
% disp(tmpLine);
        tmpCommand = str2double(tmpLine(11:16));
        switch tmpCommand
            case 0 % finish command
                tmpCMDDetail = [];
            case 1 % subbasin command
                tmpCMDDetail.HYD_STOR = str2double(tmpLine(17:22));
                tmpCMDDetail.SUB_NUM = str2double(tmpLine(23:28));
                tmpCMDDetail.GIS_CODE = str2double(tmpLine(47:end));
                tmpLine = fgetl(fid);
                tmpCMDDetail.SUBFILE = strtrim(tmpLine(11:23));
% stop
            case 2 % route command
                tmpCMDDetail.HYD_STOR = str2double(tmpLine(17:22));
                tmpCMDDetail.RCH_NUM = str2double(tmpLine(23:28));
                tmpCMDDetail.HYD_NUM = str2double(tmpLine(29:34));
                if (length(tmpLine)>=46)
                    tmpCMDDetail.FLOW_OVN = str2double(tmpLine(41:46));
                end
                tmpLine = fgetl(fid);
                tmpCMDDetail.RTEFILE = strtrim(tmpLine(11:23));
                tmpCMDDetail.SWQFILE = strtrim(tmpLine(24:36));
            case 3 % routres command
                tmpCMDDetail.HYD_STOR = str2double(tmpLine(17:22));
                tmpCMDDetail.RES_NUM = str2double(tmpLine(23:28));
                tmpCMDDetail.HYD_NUM = str2double(tmpLine(29:34));
                tmpLine = fgetl(fid);
                tmpCMDDetail.RESFILE = strtrim(tmpLine(11:23));
                tmpCMDDetail.LWQFILE = strtrim(tmpLine(24:36));
            case 4 % transfer command
                tmpCMDDetail.DEP_TYPE=str2double(tmpLine(17:22));
                tmpCMDDetail.DEP_NUM=str2double(tmpLine(23:28));
                tmpCMDDetail.DEST_TYPE=str2double(tmpLine(29:34));
                tmpCMDDetail.DEST_NUM=str2double(tmpLine(35:40));
                tmpCMDDetail.TRANS_AMT=str2double(tmpLine(41:46));
                tmpCMDDetail.TRANS_CODE=str2double(tmpLine(47:55));
                tmpCMDDetail.TRANS_SE=str2double(tmpLine(56:58));
                tmpLine = fgetl(fid);
                tmpCMDDetail.MO_TRANSB=str2double(tmpLine(11:14));
                tmpCMDDetail.MO_TRANSE=str2double(tmpLine(15:18));
                tmpCMDDetail.IH_TRANS=str2double(tmpLine(19:22));
            case 5 %add command
                tmpCMDDetail.HYD_STOR=str2double(tmpLine(17:22));
                tmpCMDDetail.HYD_NUM1=str2double(tmpLine(23:28));
                tmpCMDDetail.HYD_NUM2=str2double(tmpLine(29:34));
            case 6 % rechour command
                tmpCMDDetail.HYD_STOR=str2double(tmpLine(17:22));
                tmpCMDDetail.FILEHR_NUM=str2double(tmpLine(23:28));
                if (length(tmpLine)>=46)
                    tmpCMDDetail.DRAINAGE_AREA=str2double(tmpLine(41:46));
                end
                tmpLine = fgetl(fid);
                tmpCMDDetail.FILE_HR=strtrim(tmpLine(11:23));
            case 7 % recmon command
                tmpCMDDetail.HYD_STOR=str2double(tmpLine(17:22));
                tmpCMDDetail.FILEMIN_NUM=str2double(tmpLine(23:28));
                if (length(tmpLine)>=46)
                    tmpCMDDetail.DRAINAGE_AREA=str2double(tmpLine(41:46));
                end
                tmpLine = fgetl(fid);
                tmpCMDDetail.FILE_MON=strtrim(tmpLine(11:23));
            case 8 % recyear command
                tmpCMDDetail.HYD_STOR=str2double(tmpLine(17:22));
                tmpCMDDetail.FILEYR_NUM=str2double(tmpLine(23:28));
                if (length(tmpLine)>=46)
                    tmpCMDDetail.DRAINAGE_AREA=str2double(tmpLine(41:46));
                end
                tmpLine = fgetl(fid);
                tmpCMDDetail.FILE_YR=strtrim(tmpLine(11:23));
            case 9 % save command
                tmpCMDDetail.HYD_NUM=str2double(tmpLine(17:22));
                tmpCMDDetail.FILESAVE_NUM=str2double(tmpLine(23:28));
                tmpCMDDetail.PRINT_FREQ=str2double(tmpLine(29:34));
                tmpCMDDetail.PRINT_FMT=str2double(tmpLine(35:40));
                tmpLine = fgetl(fid);
                tmpCMDDetail.FILE_MASS=strtrim(tmpLine(11:23));
             case 10 % recday command
                tmpCMDDetail.HYD_STOR=str2double(tmpLine(17:22));
                tmpCMDDetail.FILEDAY_NUM=str2double(tmpLine(23:28));
                if (length(tmpLine)>=46)
                    tmpCMDDetail.DRAINAGE_AREA=str2double(tmpLine(41:46));
                end
                tmpLine = fgetl(fid);
                tmpCMDDetail.FILE_DAY=strtrim(tmpLine(11:23));
            case 11 % reccnst command
                tmpCMDDetail.HYD_STOR=str2double(tmpLine(17:22));
                tmpCMDDetail.FILECNST_NUM=str2double(tmpLine(23:28));
                if (length(tmpLine)>=46)
                    tmpCMDDetail.DRAINAGE_AREA=str2double(tmpLine(41:46));
                end
                tmpLine = fgetl(fid);
                tmpCMDDetail.FILE_CNST=strtrim(tmpLine(11:23));
            case 12 % structure command
                tmpCMDDetail.HYD_STOR=str2double(tmpLine(17:22));
                tmpCMDDetail.HYD_NUM=str2double(tmpLine(23:28));
                tmpCMDDetail.AERATION_COEF=str2double(tmpLine(41:46));
            case 13 % apex command
                tmpCMDDetail.HYD_STOR=str2double(tmpLine(17:22));
                tmpCMDDetail.FILECONC_NUM=str2double(tmpLine(23:28));
                tmpLine = fgetl(fid);
                tmpCMDDetail.APEX_IN=strtrim(tmpLine(11:23));
            case 14 % saveconc command
                tmpCMDDetail.HYD_NUM=str2double(tmpLine(17:22));
                tmpCMDDetail.FILECONC_NUM=str2double(tmpLine(23:28));
                tmpCMDDetail.PRINT_FREQ=str2double(tmpLine(29:34));
                tmpLine = fgetl(fid);
                tmpCMDDetail.FILE_CONC=strtrim(tmpLine(11:end));
            case 16 % autocal command
                tmpCMDDetail.HYD_NUM=str2double(tmpLine(17:22));
                tmpCMDDetail.FILECAL_NUM=str2double(tmpLine(23:28));
                tmpCMDDetail.PRINT_FREQ=str2double(tmpLine(29:34));
                tmpLine = fgetl(fid);
                tmpCMDDetail.FILE_ACAL=strtrim(tmpLine(11:23));
            case 17 % route unit command
                tmpCMDDetail.HYD_STOR=str2double(tmpLine(17:22));
                tmpCMDDetail.RU_NUM=str2double(tmpLine(23:28));
                tmpCMDDetail.SUB_NUM=str2double(tmpLine(29:34));
                tmpLine = fgetl(fid);
                tmpCMDDetail.RU_FILE=strtrim(tmpLine(11:23));
            case 18 % route landscape command
                tmpCMDDetail.HYD_STOR=str2double(tmpLine(17:22));
                tmpCMDDetail.LU_NUM=str2double(tmpLine(23:28));
                tmpCMDDetail.HYD_NUM=str2double(tmpLine(29:34));
                tmpCMDDetail.SUB_NUM=str2double(tmpLine(35:40));
                tmpCMDDetail.SURQ_RCO=str2double(tmpLine(41:46));
                tmpCMDDetail.LATQ_RCO=str2double(tmpLine(46:51));
                tmpCMDDetail.GWQ_RCO=str2double(tmpLine(52:57));
                tmpCMDDetail.TILEQ_RCO=str2double(tmpLine(58:63));
            otherwise
                error('command code: %d is not recognies.',tmpCommand);
        end
        commandList(end+1,1) = tmpCommand; %#ok<*AGROW>
        commandDetail{end+1,1} = tmpCMDDetail;
    catch ME
        fclose(fid);
        if (verbose)
            close(wbHandle);
        end
        rethrow(ME);
    end
end

%%
output = table();
output.commandCode = commandList;
output.commandDetail = commandDetail;
fclose(fid);
if (verbose)
    close(wbHandle);
    fprintf('Finished in %0.2f [s].\n',toc(masterTimer));
end

end
