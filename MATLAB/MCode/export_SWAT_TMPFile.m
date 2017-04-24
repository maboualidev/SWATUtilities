function [Options]=export_IFSM_Weather(filename, Data,Station)
%% Checking inputs
validateattributes(filename,{'char'},{'row'});

% checking the inputs are structure
if (~isstruct(Data) || ~isstruct(Station))
  error('Data and Station must be both structure.');
end

% making sure all the fields exists
if any((~isfield(Data,{'Year','Month','Day','Tmax','Tmin'})))
  error('When using new format, Data must contain all the following fields (case sensitive): Year, Month, Day, Tmax, Tmin.')
end

if any((~isfield(Station,{'Name','Lat','Lon','Elevation'})))
  error('Station must contain all the following fields (case sensitive): Name, Lat, Lon, Elevation');
end
nStations=size(Station);

% checking the size of the fields
validateattributes(Data.Year,{'numeric'},{'vector'})
nData=numel(Data.Year);
validateattributes(Data.Month,{'numeric'},{'vector','numel',nData});
validateattributes(Data.Day,{'numeric'},{'vector','numel',nData});
validateattributes(Data.Tmax,{'numeric'},{'vector','numel',nData});
validateattributes(Data.Tmin,{'numeric'},{'vector','numel',nData});

%% Preparing date column
dateNumber=datenum(Data.Year,Data.Month,Data.Day);
SWATDateNumber=(min(dateNumber):max(dateNumber))';

tmpDateVec=datevec(SWATDateNumber);
DayOfYear=SWATDateNumber-datenum(tmpDateVec(:,1),1,1)+1;
SWATData.Date=tmpDateVec(:,1)*1000+DayOfYear;
SWATData.TMax=NaN(numel(SWATDateNumber),1);
SWATData.TMin=NaN(numel(SWATDateNumber),1);

%% Preparint SWATData with missing values set to -99
FillValue=-99;
SWATData.TMax(dateNumber-min(dateNumber)+1)=Data.Tmax;
SWATData.TMin(dateNumber-min(dateNumber)+1)=Data.Tmin;

SWATData.TMax(isnan(SWATData.TMax))=FillValue;
SWATData.TMin(isnan(SWATData.TMin))=FillValue;


%% Opening the output file
fid=fopen(filename,'w');
if (fid==-1)
  error('Cannot open the output file.')
end

%% writing the file
try
  % Writing the headers
% writing header
    fprintf(fid,'Station  ');
    fprintf(fid,'%s,',Station.Name);
    fprintf(fid,'\n');
    
    fprintf(fid,'Lati   ');
    fprintf(fid,'%10.1f',Station.Lat);
    fprintf(fid,'\n');
    
    fprintf(fid,'Long   ');
    fprintf(fid,'%10.1f',Station.Lon);
    fprintf(fid,'\n');
    
    fprintf(fid,'Elev   ');
    fprintf(fid,'%10d',Station.Elevation);
    fprintf(fid,'\n');

    % Preparing data format
    formatStr='%d%0.3d';
    for stID=1:(2*nStatoins)
      formatStr=strcat(formatStr,'%05.1f');
    end
    formatStr=strcat(formatStr,'\n');
    
    tmpData=[];
%     tmpData(:,1:2:2*nStatoins)=tasmin(:,tmpStationMask);
%     tmpData(:,2:2:2*nStatoins)=tasmax(:,tmpStationMask);
    tmpData(:,1:2:2*nStatoins)=tasmax(:,tmpStationMask);
    tmpData(:,2:2:2*nStatoins)=tasmin(:,tmpStationMask);
    
    % Writing data
    fprintf(fid,formatStr,[StationData.(validProjections{projID}).year, StationData.(validProjections{projID}).doy, tmpData]');
 
catch ME
  fclose(fid);
  rethrow(ME);
end

%% closing the file
fclose(fid);

end