function output = readSWATdotSub(filenames,verbose)
%%
% readSWATdotSUB receives the path to the .sub files of SWAT and reads all
% of them. The output is provided as MATLAB table.
%
%  NOTE: This function reads the .sub files in parallel. However, the
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
nFiles = numel(filenames);
if (nFiles == 1)
    error('Currently there is a bug in this code that can not handle a single subbasin. The bug actually comes from struct2table of MATLAB not our code. Sorry for the inconvenience.');
end

tmpAllFilesOutput = cell(nFiles,1);
parfor idx = 1:nFiles
    % opening file for reading
    fid = fopen(filenames{idx},'r');
    if (fid==-1)
        error('Cannot open %s to read',filenames{idx});
    end
    
    % Making sure if any error happens the file is closed.
    try
        tmpAllFilesOutput{idx} = struct();
        tmpAllFilesOutput{idx}.Filename = filenames{idx};

        tmpAllFilesOutput{idx}.title = fgetl(fid); % Line 1
        tmpAllFilesOutput{idx}.SUB_KM= fscanf(fid,'%f'); fgetl(fid);

        fgetl(fid);
        fgetl(fid); % Line 4
        tmpAllFilesOutput{idx}.SUB_LAT = fscanf(fid,'%f'); fgetl(fid);
        tmpAllFilesOutput{idx}.SUB_ELEV = fscanf(fid,'%f');  fgetl(fid);
        tmpAllFilesOutput{idx}.IRGAGE = fscanf(fid,'%d');  fgetl(fid);
        tmpAllFilesOutput{idx}.ITGAGE = fscanf(fid,'%d'); fgetl(fid);
        tmpAllFilesOutput{idx}.ISGAGE = fscanf(fid,'%d'); fgetl(fid);
        tmpAllFilesOutput{idx}.IHGAGE = fscanf(fid,'%d'); fgetl(fid);
        tmpAllFilesOutput{idx}.IWGAGE = fscanf(fid,'%d'); fgetl(fid);
        tmpAllFilesOutput{idx}.WGNFILE = strtrim(fscanf(fid,'%c',13)); fgetl(fid);
        tmpAllFilesOutput{idx}.FCST_REG = fscanf(fid,'%d'); fgetl(fid);

        fgetl(fid);
        fgetl(fid); % Line 15
        tmpAllFilesOutput{idx}.ELEVB = cell2mat(textscan(fgetl(fid),'%8.3f',10));
        fgetl(fid);
        tmpAllFilesOutput{idx}.ELEVB_FR = cell2mat(textscan(fgetl(fid),'%8.3f',10));
        fgetl(fid);
        tmpAllFilesOutput{idx}.SNOEB = cell2mat(textscan(fgetl(fid),'%8.3f',10));
        tmpAllFilesOutput{idx}.PLAPS = fscanf(fid,'%f');  fgetl(fid);
        tmpAllFilesOutput{idx}.TLAPS = fscanf(fid,'%f');  fgetl(fid);
        tmpAllFilesOutput{idx}.SNO_SUB = fscanf(fid,'%f');  fgetl(fid);

        fgetl(fid); % Line 24
        tmpAllFilesOutput{idx}.CH_L1 = fscanf(fid,'%f');  fgetl(fid);
        tmpAllFilesOutput{idx}.CH_S1 = fscanf(fid,'%f');  fgetl(fid);
        tmpAllFilesOutput{idx}.CH_W1 = fscanf(fid,'%f');  fgetl(fid);
        tmpAllFilesOutput{idx}.CH_K1 = fscanf(fid,'%f');  fgetl(fid);
        tmpAllFilesOutput{idx}.CH_N1 = fscanf(fid,'%f');  fgetl(fid);

        fgetl(fid); % Line 30
        tmpAllFilesOutput{idx}.PNDFILE = strtrim(fscanf(fid,'%c',13)); fgetl(fid);

        fgetl(fid); % Line 32
        tmpAllFilesOutput{idx}.WUSFILE = strtrim(fscanf(fid,'%c',13)); fgetl(fid);

        fgetl(fid); % Line 34
        tmpAllFilesOutput{idx}.CO2 = fscanf(fid,'%f');  fgetl(fid);

        fgetl(fid); % Line 36
        tmpAllFilesOutput{idx}.RFINC(1:6) = cell2mat(textscan(fgetl(fid),'%8.3f',10));
        fgetl(fid); % Line 38
        tmpAllFilesOutput{idx}.RFINC(7:12) = cell2mat(textscan(fgetl(fid),'%8.3f',10));

        fgetl(fid); % Line 40
        tmpAllFilesOutput{idx}.TMPINC(1:6) = cell2mat(textscan(fgetl(fid),'%8.3f',10));
        fgetl(fid); % Line 42
        tmpAllFilesOutput{idx}.TMPINC(7:12) = cell2mat(textscan(fgetl(fid),'%8.3f',10));

        fgetl(fid); % Line 44
        tmpAllFilesOutput{idx}.RADINC(1:6) = cell2mat(textscan(fgetl(fid),'%8.3f',10));
        fgetl(fid); % Line 46
        tmpAllFilesOutput{idx}.RADINC(7:12) = cell2mat(textscan(fgetl(fid),'%8.3f',10));

        fgetl(fid); % Line 48
        tmpAllFilesOutput{idx}.HUMINC(1:6) = cell2mat(textscan(fgetl(fid),'%8.3f',10));
        fgetl(fid); % Line 50
        tmpAllFilesOutput{idx}.HUMINC(7:12) = cell2mat(textscan(fgetl(fid),'%8.3f',10));

        fgetl(fid); % Line 52
        tmpAllFilesOutput{idx}.HRUTOT = fscanf(fid,'%d');  fgetl(fid);

        fgetl(fid); % Line 54
        fgetl(fid);
        fgetl(fid);

        fgetl(fid); % Line 57
        tmpTXT = reshape(fgetl(fid),13,[])';
        tmpFieldnames={'FLD_HRUFILE', 'FLD_MGTFILE', 'FLD_SOLFILE', 'FLD_CHMFILE', 'FLD_GWFILE'};
        for idx2 = 1:numel(tmpFieldnames)
            if (idx2<=size(tmpTXT,1))
                tmpAllFilesOutput{idx}.(tmpFieldnames{idx2}) = strtrim(tmpTXT(idx2,:));
            else
                tmpAllFilesOutput{idx}.(tmpFieldnames{idx2}) = '';
            end
        end

        fgetl(fid); % Line 59
        tmpTXT = reshape(fgetl(fid),13,[])';
        tmpFieldnames={'RIP_HRUFILE', 'RIP_MGTFILE', 'RIP_SOLFILE', 'RIP_CHMFILE', 'RIP_GWFILE'};
        for idx2 = 1:numel(tmpFieldnames)
            if (idx2<=size(tmpTXT,1))
                tmpAllFilesOutput{idx}.(tmpFieldnames{idx2}) = strtrim(tmpTXT(idx2,:));
            else
                tmpAllFilesOutput{idx}.(tmpFieldnames{idx2}) = '';
            end
        end

        fgetl(fid); % Line 61
        tmpTXT = reshape(fgetl(fid),13,[])';
        tmpFieldnames={'HRUFILE', 'MGTFILE', 'SOLFILE', 'CHMFILE', 'GWFILE', 'OPSFILE', 'SEPTFILE', 'SDRFILE'};
        for idx2 = 1:numel(tmpFieldnames)
            if (idx2<=size(tmpTXT,1))
                tmpAllFilesOutput{idx}.(tmpFieldnames{idx2}) = strtrim(tmpTXT(idx2,:));
            else
                tmpAllFilesOutput{idx}.(tmpFieldnames{idx2}) = '';
            end
        end
    catch ME
        fclose(fid);            
        rethrow(ME);
    end
    % closing the file
    fclose(fid);
    tmpAllFilesOutput{idx} = tmpAllFilesOutput{idx};
    
    if (verbose)
        fprintf('Reading %s is completed.\n',filenames{idx});
    end
end

%% Converting to table
output = [tmpAllFilesOutput{:}];
% save output output
% disp('....')
output = struct2table(output);

%% Reseting AutoCreate properties of parellel pool to its original state
pSettings.Pool.AutoCreate = AutoCreateOriginalValue;
end
