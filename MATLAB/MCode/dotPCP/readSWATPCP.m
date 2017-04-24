function output = readSWATPCP(pcpFilenames,pcpFileFormat,nrgfil,nrtot,options)
%%
if (nargin<5 || isempty(options))
    options.verbose = false;
    if (nargin==0)
        output = options;
        return;
    end
else
    if (~isfield(options,'verbose') || isempty(options.verbose))
        options.verbose = false;
    end
end

%%
validateattributes(pcpFilenames,{'cell'},{'vector'});
validateattributes(pcpFileFormat,{'char'},{'row'});
if (~any(ismember(pcpFileFormat,{'daily','subdaily'})))
    error('pcpFileFormat can be either "daily" or "subdaily".');
end
validateattributes(nrgfil,{'numeric'},{'integer','positive','scalar'});
validateattributes(nrtot,{'numeric'},{'integer','nonnegative','scalar'});
%%
nFiles = numel(pcpFilenames); % this is the same as nrgage in SWAT code
if (nrtot<=0)
    nrtot = nrgfil * nFiles;
end

titleLines = cell(nFiles,1);
tmpPCPTBLs = cell(nFiles,1);
tmpStationInfo = cell(nFiles,1);
parfor idx = 1:nFiles
    % Opening the files
    fid = fopen(pcpFilenames{idx},'r');
    if (fid==-1)
        error('Cannot open %s for reading.',pcpFilenames{idx});
    end

    % Checking howmany stations should read from this file.
    if (idx == nFiles)
        nStations = nrtot - (nFiles-1)*nrgfil;
    else
        nStations = nrgfil;
    end

    % Initializing the variables
    tmpStationInfo{idx} = table();

    % Reading the file
    try 
        titleLines{idx} = fgetl(fid); 

        tmpDummy = fgetl(fid);
        tmpStationInfo{idx}.latitude = cell2mat(textscan(tmpDummy(8:end),'%5f',nStations));
        tmpDummy = fgetl(fid);
        tmpStationInfo{idx}.longitude = cell2mat(textscan(tmpDummy(8:end),'%5f',nStations));
        tmpDummy = fgetl(fid);
        tmpStationInfo{idx}.elevation = cell2mat(textscan(tmpDummy(8:end),'%5f',nStations));
        fclose(fid);

        switch pcpFileFormat
            case 'daily'
                formatSpec = '';
                for idx2=1:nStations
                    formatSpec=strcat(formatSpec,'%5.1f');
                end
                formatSpec = strcat('%4f%3f',formatSpec);

                tmpPCPTBLs{idx} = readtable(pcpFilenames{idx}, ...
                                            'FileType','text', ...
                                            'HeaderLines',4, ...
                                            'ReadVariableNames',false, ...
                                            'Format',formatSpec);
                tmpPCPTBLs{idx}.Properties.VariableNames{1} = 'year';
                tmpPCPTBLs{idx}.Properties.VariableNames{2} = 'date';
                for idx2=1:nStations
                    tmpPCPTBLs{idx}.Properties.VariableNames{2+idx2} = sprintf('Station_%d',idx2+ (idx-1)*nrgfil);
                end
            case 'subdaily'
                error('"subdaily" is not yet implemented. Sorry for the inconvenience')
            otherwise
                error('unknown pcpFileFormat'); %This line should never be exuected; due to earlier check on the variable
        end
    if (options.verbose)
        fprintf('Reading %s completed.\n', pcpFilenames{idx});
    end
    catch ME
        fclose(fid);
        rethrow(ME);
    end
end

% nowCombining all data into one variable
if (options.verbose)
        fprintf('Combining data and preparing the output.\nProcessing %s ...\n',pcpFilenames{1});
end
output.titleLines = titleLines;
output.stationInfo = tmpStationInfo{1};
output.values = tmpPCPTBLs{1};
for idx = 2:nFiles
    if (options.verbose)
        fprintf('Processing %s ...\n',pcpFilenames{idx});
    end
    if (idx == nFiles)
        nStations = nrtot - (nFiles-1)*nrgfil;
    else
        nStations = nrgfil;
    end
    output.stationInfo(end+1:end+nStations,:)=tmpStationInfo{idx};

    output.values = join(output.values,tmpPCPTBLs{idx});
end

end

