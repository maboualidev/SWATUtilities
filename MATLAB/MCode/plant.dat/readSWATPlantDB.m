function output=readSWATPlantDB(filename)
%% Description:
%  readSWATPlantDB(filename) receives the path to the SWAT plant database 
%  and parses its contents into an array of structures.
%
%  input:
%    - filename: path the SWAT plant.dat
%
%  output:
%    - output: an array of structures containing the information for each
%              plant.
%
%  NOTE(S):
%    - The fields are based on Chapter 14: SWAT INPUT - CROP.DAT, Version
%      2012.
%
    %% Initializing
    fieldNames{1} = {'ICNUM', 'CPNM', 'IDC'};
    fieldNames{2} = {'BIO_E', 'HVSTI', 'BLAI', 'FRGRW1', 'LAIMX1', 'FRGRW2', 'LAIMX2', 'DLAI', 'CHTMX', 'RDMX'};
    fieldNames{3} = {'T_OPT', 'T_BASE', 'CNYLD', 'CPYLD', 'PLTNFR1', 'PLTNFR2', 'PLTNFR3', 'PLTPFR1', 'PLTPFR2', 'PLTPFR3'};
    fieldNames{4} = {'WSYF', 'USLE_C', 'GSI', 'VPDFR', 'FRGMAX', 'WAVP', 'CO2HI', 'BIOEHI', 'RSDCO_PL', 'ALAI_MIN'};
    fieldNames{5} = {'BIO_LEAF', 'MAT_YRS', 'BMX_TREES', 'EXT_COEF', 'BMDIEOFF', 'RSR1C', 'RSR2C'};

    %% Opening the input file for reading
    fid=fopen(filename,'r');
    if (fid==-1) 
        error('readSWATPlantDB.m: Could not open %s for reading.',filename);
    end

    %% Parsing the content of the input file.
    cropCounter=0;
    try 
        tmpTXT=cell(5,1);
        while (~feof(fid))
            % reading five lines
            for idx=1:5
                tmpTXT{idx}=fgetl(fid);
            end

            % parsing
            tmpStruct=struct();
            for idx1=1:5
                tmpTXT2=strsplit(strtrim(tmpTXT{idx1}));
                nValues=numel(tmpTXT2);
                for idx2=1:numel(fieldNames{idx1})
                    if (idx2<=nValues)
                        if ((idx1==1) && (idx2)==2)
                            tmpStruct.(fieldNames{idx1}{idx2})=tmpTXT2{idx2};
                        else
                            tmpStruct.(fieldNames{idx1}{idx2})=str2double( tmpTXT2{idx2} );
                        end
                    else
                        tmpStruct.(fieldNames{idx1}{idx2})=[];
                    end
                end
            end
            cropCounter = cropCounter + 1;
            output(cropCounter) = tmpStruct; %#ok<AGROW>
        end
    catch ME
        fclose(fid); % making sure the file is closed before halting the code.
        rethrow(ME);
    end

    %% closing the input file
    fclose(fid);
end