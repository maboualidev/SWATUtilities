function [output, Opt] = SWAT_WATOUT_Summarize(filename,Opt)
    %% Assigning default Options
    fullYearMinRecordCount = 365;
    if (nargin<2 || isempty(Opt))
        Opt.FileType = 'text';
        Opt.HeaderLines = 6;
        Opt.ReadVariablesNames = false;
        Opt.wYearEndMonth = 9;
        Opt.wYearEndDay = 30;
        Opt.PhosphorusColumn = [7, 11];
        Opt.ConversionFactor = 0.0864;
        Opt.FlowColumn = 4;
        Opt.YearColumn = 1;
        Opt.DaysOfYearColumn = 2;
        Opt.NYears2Skip = 1;
    else
        if ( ~isfield(Opt,'FileType') || isempty(Opt.FileType))
            Opt.FileType = 'text';
        end
        if ( ~isfield(Opt,'HeaderLines') || isempty(Opt.HeaderLines))
            Opt.HeaderLines = 6;
        end
        if ( ~isfield(Opt,'ReadVariablesNames') || isempty(Opt.ReadVariablesNames))
            Opt.ReadVariablesNames = false;
        end
        if ( ~isfield(Opt,'wYearEndMonth') || isempty(Opt.wYearEndMonth))
            Opt.wYearEndMonth = 9;
        end
        if ( ~isfield(Opt,'wYearEndDay') || isempty(Opt.wYearEndDay))
            Opt.wYearEndDay = 30;
        end
        if ( ~isfield(Opt,'PhosphorusColumn') || isempty(Opt.PhosphorusColumn))
            Opt.PhosphorusColumn = [7, 11];
        end
        if ( ~isfield(Opt,'ConversionFactor') || isempty(Opt.ConversionFactor))
            Opt.ConversionFactor = 0.0864;
        end
        if ( ~isfield(Opt,'FlowColumn') || isempty(Opt.FlowColumn))
            Opt.FlowColumn = 4;
        end
        if ( ~isfield(Opt,'YearColumn') || isempty(Opt.YearColumn))
            Opt.YearColumn = 1;
        end
        if ( ~isfield(Opt,'DaysOfYearColumn') || isempty(Opt.DaysOfYearColumn))
            Opt.DaysOfYearColumn = 2;
        end
        if ( ~isfield(Opt,'NYears2Skip') || isempty(Opt.NYears2Skip))
            Opt.NYears2Skip = 1;
        end
    end
    
    %% Reading the watout file
    data = readtable(filename, ...
                     'FileType',Opt.FileType, ...
                     'HeaderLines',Opt.HeaderLines, ...
                     'ReadVariableNames',Opt.ReadVariablesNames);
    data = table2array(data);
    
    waterYearCutOff = datenum(data(:,Opt.YearColumn),Opt.wYearEndMonth,Opt.wYearEndDay) - datenum(data(:,Opt.YearColumn),1,1) + 1;
    wYear = data(:,Opt.YearColumn) + double(data(:,Opt.DaysOfYearColumn)>waterYearCutOff);
    
    X = sum(data(:,Opt.PhosphorusColumn),2)*Opt.ConversionFactor .* data(:,Opt.FlowColumn);
    [totalSum,count,wYear] = grpstats(X,wYear,{'sum','numel','gname'});
    mask = count>=fullYearMinRecordCount;
    totalSum = totalSum(mask); % removing incomplete years;
    totalSum = totalSum(Opt.NYears2Skip+1:end); % skipping requested number of years from begining
	wYear = wYear(mask);
    wYear = wYear(Opt.NYears2Skip+1:end);
    wYear = cellfun(@(c) str2double(c),wYear);
    output = table(totalSum,wYear);
end
