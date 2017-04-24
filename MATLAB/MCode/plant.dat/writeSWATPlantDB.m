function writeSWATPlantDB(plantDB,filename)
%% Description:
%  writeSWATPlantDB(PlantDB,filename) writes the plantDB into a file
%  formated for SWAT plant.dat.
%
%  input:
%    - plantDB: an array of structures containing the fields required for
%               SWAT PLant Database.
%    - filename: path the SWAT plant.dat
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

    %% Opening the input file for writing
    fid=fopen(filename,'w');
    if (fid==-1) 
        error('writeSWATPlantDB.m: Could not open %s for writing.',filename);
    end

    %% Writing SWAT plant DataBase
    try 
        nCrops=numel(plantDB);
        for idx1=1:nCrops
            for idx2=1:numel(fieldNames)
                switch (idx2)
                    case 1
                        fprintf(fid,'%u ', plantDB(idx1).(fieldNames{1}{1}));
                        fprintf(fid,'%s ', plantDB(idx1).(fieldNames{1}{2}));
                        fprintf(fid,'%u ', plantDB(idx1).(fieldNames{1}{3}));
                        fprintf(fid,'\n');
                    case {2,3,4}
                        for idx3=1:numel(fieldNames{idx2})
                            if (isempty(plantDB(idx1).(fieldNames{idx2}{idx3})))
                                break;
                            else
                                fprintf(fid,'%0.4f ',plantDB(idx1).(fieldNames{idx2}{idx3}));
                            end
                        end
                        fprintf(fid,'\n');
                    case 5
                        for idx3=1:numel(fieldNames{5})
                            if (isempty(plantDB(idx1).(fieldNames{5}{idx3})))
                                break;
                            else
                                if (idx3==2)
                                    fprintf(fid,'%u ',plantDB(idx1).(fieldNames{idx2}{idx3}));
                                else
                                    fprintf(fid,'%0.4f ',plantDB(idx1).(fieldNames{idx2}{idx3}));
                                end
                            end
                        end
                        fprintf(fid,'\n');
                    otherwise
                        error('writeSWATPlantDB: not supported yet.');
                end
            end
        end

    catch ME
        fclose(fid); % making sure the file is closed before halting the code.
        rethrow(ME);
    end

    %% closing the input file
    fclose(fid);
end
    