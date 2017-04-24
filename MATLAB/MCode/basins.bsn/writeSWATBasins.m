function writeSWATBasins(basins,filename)

    %%
    fieldNames =   {'CommentOrTitle1','Basin data .bsn file 12/18/2015 12:00:00 AM ArcSWAT 2012.10_0.14','char'; ...
                    'CommentOrTitle2','Modeling Options: Land Area','char'; ...
                    'CommentOrTitle3','Water Balance:','char'; ...
                    'SFTMP','SFTMP : Snowfall temperature [�C]','double'; ...
                    'SMTMP','SMTMP : Snow melt base temperature [�C]','double'; ...
                    'SMFMX','SMFMX : Melt factor for snow on June 21 [mm H2O/�C-day]','double'; ...
                    'SMFMN','SMFMN : Melt factor for snow on December 21 [mm H2O/�C-day]','double'; ...
                    'TIMP','TIMP : Snow pack temperature lag factor','double'; ...
                    'SNOCOVMX','SNOCOVMX : Minimum snow water content that corresponds to 100% snow cover [mm]','double'; ...
                    'SNO50COV','SNO50COV : Fraction of snow volume represented by SNOCOVMX that corresponds to 50% snow cover','double'; ...
                    'IPET','IPET: PET method: 0=priest-t, 1=pen-m, 2=har, 3=read into model','integer'; ...
                    'PETFILE','PETFILE: name of potential ET input file','char'; ...
                    'ESCO','ESCO: soil evaporation compensation factor','double'; ...
                    'EPCO','EPCO: plant water uptake compensation factor','double'; ...
                    'EVLAI','EVLAI : Leaf area index at which no evaporation occurs from water surface [m2/m2]','double'; ...
                    'FFCB','FFCB : Initial soil water storage expressed as a fraction of field capacity water content','double'; ...
                    'CommentOrTitle4','Surface Runoff:','char'; ...
                    'IEVENT','IEVENT: rainfall/runoff code: 0=daily rainfall/CN','integer'; ...
                    'ICRK','ICRK: crack flow code: 1=model crack flow in soil','integer'; ...
                    'SURLAG','SURLAG : Surface runoff lag time [days]','double'; ...
                    'ADJ_PKR','ADJ_PKR : Peak rate adjustment factor for sediment routing in the subbasin (tributary channels)','double'; ...
                    'PRF','PRF : Peak rate adjustment factor for sediment routing in the main channel','double'; ...
                    'SPCON','SPCON : Linear parameter for calculating the maximum amount of sediment that can be reentrained during channel sediment routing','double'; ...
                    'SPEXP','SPEXP : Exponent parameter for calculating sediment reentrained in channel sediment routing','double'; ...
                    'CommentOrTitle5','Nutrient Cycling:','char'; ...
                    'RCN','RCN : Concentration of nitrogen in rainfall [mg N/l]','double'; ...
                    'CMN','CMN : Rate factor for humus mineralization of active organic nitrogen','double'; ...
                    'N_UPDIS','N_UPDIS : Nitrogen uptake distribution parameter','double'; ...
                    'P_UPDIS','P_UPDIS : Phosphorus uptake distribution parameter','double'; ...
                    'NPERCO','NPERCO : Nitrogen percolation coefficient','double'; ...
                    'PPERCO','PPERCO : Phosphorus percolation coefficient','double'; ...
                    'PHOSKD','PHOSKD : Phosphorus soil partitioning coefficient','double'; ...
                    'PSP','PSP : Phosphorus sorption coefficient','double'; ...
                    'RSDCO','RSDCO : Residue decomposition coefficient','double'; ...
                    'CommentOrTitle6','Pesticide Cycling:','char'; ...
                    'PERCOP','PERCOP : Pesticide percolation coefficient','double'; ...
                    'CommentOrTitle7','Algae/CBOD/Dissolved Oxygen:','char'; ...
                    'ISUBWQ','ISUBWQ: subbasin water quality parameter','integer'; ...
                    'CommentOrTitle8','Bacteria:','char'; ...
                    'WDPQ','WDPQ : Die-off factor for persistent bacteria in soil solution. [1/day]','double'; ...
                    'WGPQ','WGPQ : Growth factor for persistent bacteria in soil solution [1/day]','double'; ...
                    'WDLPQ','WDLPQ : Die-off factor for less persistent bacteria in soil solution [1/day]','double'; ...
                    'WGLPQ','WGLPQ : Growth factor for less persistent bacteria in soil solution. [1/day]','double'; ...
                    'WDPS','WDPS : Die-off factor for persistent bacteria adsorbed to soil particles. [1/day]','double'; ...
                    'WGPS','WGPS : Growth factor for persistent bacteria adsorbed to soil particles. [1/day]','double'; ...
                    'WDLPS','WDLPS : Die-off factor for less persistent bacteria adsorbed to soil particles. [1/day]','double'; ...
                    'WGLPS','WGLPS : Growth factor for less persistent bacteria adsorbed to soil particles. [1/day]','double'; ...
                    'BACTKDQ','BACTKDQ : Bacteria partition coefficient','double'; ...
                    'THBACT','THBACT : Temperature adjustment factor for bacteria die-off/growth','double'; ...
                    'WOF_P','WOF_P: wash-off fraction for persistent bacteria on foliage','double'; ...
                    'WOF_LP','WOF_LP: wash-off fraction for less persistent bacteria on foliage','double'; ...
                    'WDPF','WDPF: persistent bacteria die-off factor on foliage','double'; ...
                    'WGPF','WGPF: persistent bacteria growth factor on foliage','double'; ...
                    'WDLPF','WDLPF: less persistent bacteria die-off factor on foliage','double'; ...
                    'WGLPF','WGLPF: less persistent bacteria growth factor on foliage','double'; ...
                    'ISED_DET','ISED_DET:','integer'; ...
                    'CommentOrTitle9','Modeling Options: Reaches','char'; ...
                    'IRTE','IRTE: water routing method 0=variable travel-time 1=Muskingum','integer'; ...
                    'MSK_CO1','MSK_CO1 : Calibration coefficient used to control impact of the storage time constant (Km) for normal flow','double'; ...
                    'MSK_CO2','MSK_CO2 : Calibration coefficient used to control impact of the storage time constant (Km) for low flow','double'; ...
                    'MSK_X','MSK_X : Weighting factor controlling relative importance of inflow rate and outflow rate in determining water storage in reach segment','double'; ...
                    'IDEG','IDEG: channel degradation code','integer'; ...
                    'IWQ','IWQ: in-stream water quality: 1=model in-stream water quality','integer'; ...
                    'WWQFILE','WWQFILE: name of watershed water quality file','char'; ...
                    'TRNSRCH','TRNSRCH: reach transmission loss partitioning to deep aquifer','double'; ...
                    'EVRCH','EVRCH : Reach evaporation adjustment factor','double'; ...
                    'IRTPEST','IRTPEST : Number of pesticide to be routed through the watershed channel network','integer'; ...
                    'ICN','ICN : Daily curve number calculation method','double'; ...
                    'CNCOEF','CNCOEF : Plant ET curve number coefficient','double'; ...
                    'CDN','CDN : Denitrification exponential rate coefficient','double'; ...
                    'SDNCO','SDNCO : Denitrification threshold water content','double'; ...
                    'BACT_SWF','BACT_SWF : Fraction of manure applied to land areas that has active colony forming units','double'; ...
                    'BACTMX','BACTMX : Bacteria percolation coefficient [10 m3/Mg].','double'; ...
                    'BACTMINLP','BACTMINLP : Minimum daily bacteria loss for less persistent bacteria [# cfu/m2]','double'; ...
                    'BACTMINP','BACTMINP : Minimum daily bacteria loss for persistent bacteria [# cfu/m2]','double'; ...
                    'WDLPRCH','WDLPRCH: Die-off factor for less persistent bacteria in streams (moving water) at 20 C [1/day]','double'; ...
                    'WDPRCH','WDPRCH : Die-off factor for persistent bacteria in streams (moving water) at 20 C [1/day]','double'; ...
                    'WDLPRES','WDLPRES : Die-off factor for less persistent bacteria in water bodies (still water) at 20 C [1/day]','double'; ...
                    'WDPRES','WDPRES : Die-off factor for persistent bacteria in water bodies (still water) at 20 C [1/day]','double'; ...
                    'TB_ADJ','TB_ADJ : New variable in testing ...Adjustment factor for subdaily unit hydrograph basetime','double'; ...
                    'DEPIMP_BSN','DEPIMP_BSN : Depth to impervious layer for modeling perched water tables [mm]','double'; ...
                    'DDRAIN_BSN','DDRAIN_BSN : Depth to the sub-surface drain [mm]','double'; ...
                    'TDRAIN_BSN','TDRAIN_BSN : Time to drain soil to field capacity [hours]','double'; ...
                    'GDRAIN_BSN','GDRAIN_BSN : Drain tile lag time [hours]','double'; ...
                    'CN_FROZ','CN_FROZ : Parameter for frozen soil adjustment on infiltration/runoff','double'; ...
                    'DORM_HR','DORM_HR : Time threshold used to define dormancy [hours]','double'; ...
                    'SMXCO','SMXCO : Adjustment factor for maximum curve number S factor','double'; ...
                    'FIXCO','FIXCO : Nitrogen fixation coefficient','double'; ...
                    'NFIXMX','NFIXMX : Maximum daily-n fixation [kg/ha]','double'; ...
                    'ANION_EXCL_BSN','ANION_EXCL_BSN : Fraction of porosity from which anions are excluded','double'; ...
                    'CH_ONCO_BSN','CH_ONCO_BSN : Channel organic nitrogen concentration in basin [ppm]','double'; ...
                    'CH_OPCO_BSN','CH_OPCO_BSN : Channel organic phosphorus concentration in basin [ppm]','double'; ...
                    'HLIFE_NGW_BSN','HLIFE_NGW_BSN : Half-life of nitrogen in groundwater [days]','double'; ...
                    'RCN_SUB_BSN','RCN_SUB_BSN : Concentration of nitrate in precipitation [ppm]','double'; ...
                    'BC1_BSN','BC1_BSN : Rate constant for biological oxidation of NH3 [1/day]','double'; ...
                    'BC2_BSN','BC2_BSN : Rate constant for biological oxidation NO2 to NO3 [1/day]','double'; ...
                    'BC3_BSN','BC3_BSN : Rate constant for hydrolosis of organic nitrogen to ammonia [1/day]','double'; ...
                    'BC4_BSN','BC4_BSN : Rate constant for decay of organic phosphorus to dissolved phosphorus [1/day]','double'; ...
                    'DECR_MIN','DECR_MIN: Minimum daily residue decay','double'; ...
                    'ICFAC','ICFAC : C-factor calculation method','double'; ...
                    'RSD_COVCO','RSD_COVCO : Residue cover factor for computing fraction of cover','double'; ...
                    'VCRIT','VCRIT : Critical velocity','double'; ...
                    'CSWAT','CSWAT : Code for new carbon routines','integer'; ...
                    'RES_STLR_CO','RES_STLR_CO : Reservoir sediment settling coefficient','double'; ...
                    'BFLO_DIST','BFLO_DIST 0-1 (1:profile of baseflow in a day follows rainfall pattern, 0:baseflow evenly distributed to each time step during a day','double'; ...
                    'IUH','IUH : Unit hydrograph method: 1=triangular UH, 2=gamma function UH','integer'; ...
                    'UHALPHA','UHALPHA : alpha coefficient for gamma function unit hydrograph. Required if iuh=2 is selected','double'; ...
                    'CommentOrTitle10','Land Use types in urban.dat that do not make runoff to urban BMPs:','char'; ...
                    'LU_NODRAIN','','doubleArray'; ...
                    'CommentOrTitle11','Subdaily Erosion:','char'; ...
                    'EROS_SPL','EROS_SPL: The splash erosion coefficient ranges 0.9 - 3.1','double'; ...
                    'RILL_MULT','RILL_MULT: Multiplier to USLE_K for soil susceptible to rill erosion, ranges 0.5 - 2.0','double'; ...
                    'EROS_EXPO','EROS_EXPO: an exponent in the overland flow erosion equation, ranges 1.5 - 3.0','double'; ...
                    'SUBD_CHSED','SUBD_CHSED: 1=Brownlie(1981) model, 2=Yang(1973,1984) model','integer'; ...
                    'C_FACTOR','C_FACTOR: Scaling parameter for Cover and management factor in ANSWERS erosion model','double'; ...
                    'CH_D50','CH_D50 : median particle diameter of channel bed [mm]','double'; ...
                    'SIG_G','SIG_G : geometric standard deviation of particle sizes','double'; ...
                    'RE_BSN','RE_BSN: Effective radius of drains','double'; ...
                    'SDRAIN_BSN','SDRAIN_BSN: Distance between two drain or tile tubes','double'; ...
                    'DRAIN_CO_BSN','DRAIN_CO_BSN: Drainage coefficient','double'; ...
                    'PC_BSN','PC_BSN: Pump capacity','double'; ...
                    'LATKSATF_BSN','LATKSATF_BSN: Multiplication factor to determine lateral ksat from SWAT ksat input value for HRU','double'; ...
                    'ITDRN','ITDRN: Tile drainage equations flag','integer'; ...
                    'IWTDN','IWTDN: Water table depth algorithms flag','integer'; ...
                    'SOL_P_MODEL','SOL_P_MODEL: if = 1, use new soil P model','integer'; ...
                    'IABSTR','IABSTR: Initial abstraction on impervious cover (mm)','double'; ...
                    'IATMODEP','IATMODEP: 0 = average annual inputs 1 = monthly inputs','integer'; ...
                    'R2ADJ_BSN','R2ADJ_BSN: basinwide retention parameter adjustment factor (greater than 1)','double'; ...
                    'SSTMAXD_BSN','SSTMAXD_BSN: static maximum depressional storage','double'; ...
                    'ISMAX','ISMAX: maximum depressional storage selection flag/code','integer'; ...
                    'IROUTUNIT','IROUTUNIT: drainmod tile equations','integer'};
    lastMandatoryField = 72;
    %%
    fid = fopen(filename,'w');
    if (fid==-1)
        error('readSWATBasins: Could not open %s for writing.',filename);
    end

    try
        for idx=1:size(fieldNames,1)
            if (idx<=lastMandatoryField)
                if (numel(fieldNames{idx,1})>=14 && ...
                    strcmpi(fieldNames{idx,1}(1:14),'CommentOrTitle'))
                    fprintf(fid,'%s ',basins.(fieldNames{idx,1}));
                else
                    switch fieldNames{idx,3}
                        case 'char'
                            fprintf(fid,'%16s    | %s ',basins.(fieldNames{idx,1}),fieldNames{idx,2});
                        case 'double'
                            fprintf(fid,'%16.5f    | %s ',basins.(fieldNames{idx,1}),fieldNames{idx,2});
                        case 'integer'
                            fprintf(fid,'%16u    | %s ',basins.(fieldNames{idx,1}),fieldNames{idx,2});
                        case 'doubleArray'
                            tmpVAR=num2str(basins.(fieldNames{idx,1}));
                            fprintf(fid,'%0.5u    | %s ',strjoin(strsplit(tmpVAR),','),fieldNames{idx,2});
                        otherwise
                            error('writeSWATBasins.m: doesnot recognize the type.');
                    end
                end
            else
                if (isfield(basins,fieldNames{idx,1}))
                    if (numel(fieldNames{idx,1})>=14 && ...
                        strcmpi(fieldNames{idx,1}(1:14),'CommentOrTitle'))
                        fprintf(fid,'%s ',basins.(fieldNames{idx,1}));
                    else
                        switch fieldNames{idx,3}
                            case 'char'
                                fprintf(fid,'%16s    | %s ',basins.(fieldNames{idx,1}),fieldNames{idx,2});
                            case 'double'
                                fprintf(fid,'%16.5f    | %s ',basins.(fieldNames{idx,1}),fieldNames{idx,2});
                            case 'integer'
                                fprintf(fid,'%16u    | %s ',basins.(fieldNames{idx,1}),fieldNames{idx,2});
                            case 'doubleArray'
                                if (~isempty(basins.(fieldNames{idx,1})))
                                    tmpVAR=num2str(basins.(fieldNames{idx,1}));
                                    fprintf(fid,'%0.5u    | %s ',strjoin(strsplit(tmpVAR),','),fieldNames{idx,2});
                                else
                                    fprintf(fid,'                                  ');
                                end
                            otherwise
                                error('writeSWATBasins.m: doesnot recognize the type.');
                        end
                    end
                end
            end
            fprintf(fid,'\n');
        end
    catch ME
        fclose(fid); % making sure the file is closed before halting the code.
        rethrow(ME);
    end

    %% closing the input file
    fclose(fid);
end
