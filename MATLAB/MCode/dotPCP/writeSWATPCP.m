function output=writeSWATPCP(SWATPCPData, outputDir,nrgfil,options)
%%
if (nargin<4 || isempty(options))
    options.verbose = false;
    options.pcpFilePrefix='pcp';
    if (nargin==0)
        output = options;
        return;
    end
else
    if (~isfield(options,'verbose') || isempty(options.verbose))
        options.verbose = false;
    end
    if (~isfield(options,'pcpFilePrefix') || isempty(options.pcpFilePrefix))
        options.pcpFilePrefix = 'pcp';
    end
end

nrtot = size(SWATPCPData.values,2)-2;

%% Creating list of files and splitting data for parallel writing
nFiles = ceil(nrtot/nrgfil);

% fileList = cell(nFiles,1);
tmpStationInfo = cell(nFiles,1);
tmpPCPTBLs = cell(nFiles,1);
titleLines = SWATPCPData.titleLines;
%% Preparing input data for each file for parallel processing
lastRecord = 0;
for idx = 1:nFiles
    if (options.verbose)
        fprintf('Preparing the input data for file #%d ...\n',idx);
    end
    % Checking howmany stations should written to this file.
    if (idx == nFiles)
        nStations = nrtot - (nFiles-1)*nrgfil;
    else
        nStations = nrgfil;
    end
    
    recordList = lastRecord+1:lastRecord+nStations;
    tmpStationInfo{idx} = SWATPCPData.stationInfo(recordList,:);
    tmpPCPTBLs{idx} = table2array(SWATPCPData.values(:,[1,2 (recordList+2)]))';
    lastRecord = recordList(end);
end

%% Writing the files
parfor idx = 1:nFiles
    filename = fullfile(outputDir,sprintf('%s_%0.4d.pcp',options.pcpFilePrefix,idx)); %#ok<PFBNS>
	fid = fopen(filename,'w');
    if (fid==-1)
        error('Cannot open %s for writing.',filename);
    end
    
    try
        % Writing title
        fprintf(fid,'%s\n',titleLines{idx});
        
        % writing latitudes
        fprintf(fid,'Lat    ');
        fprintf(fid,'%5.2f',tmpStationInfo{idx}.latitude);
        fprintf(fid,'\n');

        % writing longitudes
        fprintf(fid,'Lon    ');
        fprintf(fid,'%5.1f',tmpStationInfo{idx}.longitude);
        fprintf(fid,'\n');
        
        % writing elevations
        fprintf(fid,'elv    ');
        fprintf(fid,'%5.0f',tmpStationInfo{idx}.elevation);
        fprintf(fid,'\n');
        
        % Checking howmany stations should written to this file.
        if (idx == nFiles)
            nStations = nrtot - (nFiles-1)*nrgfil;
        else
            nStations = nrgfil;
        end
        
        % writing the values
        formatSpec = '';
        for idx2=1:nStations
            formatSpec=strcat(formatSpec,'%05.1f');
        end
        formatSpec = strcat('%4d%3d',formatSpec,'\n');
        fprintf(fid,formatSpec, tmpPCPTBLs{idx});
        
        fclose(fid);
        if (options.verbose)
            fprintf('Writing %s is completed.\n',filename);
        end
    catch ME
        fclose(fid);
        rethrow(ME);
    end
end

output = options;

end












































