/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.io;

import SWAT.utilities.common.Convertor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 *
 * @author mabouali
 */
public final class Basins
        implements SWATFormatInput<Basins,Basins.fields> {
    public static enum fields {
        TITLE(String.class,""),
        COMMENT1(String.class,""),
        COMMENT2(String.class,""),
        SFTMP(Double.class,"SFTMP : Snowfall temperature [ºC]"),
        SMTMP(Double.class,"SMTMP : Snow melt base temperature [ºC]"),
        SMFMX(Double.class,"SMFMX : Melt factor for snow on June 21 [mm H2O/ºC-day]"),
        SMFMN(Double.class,"SMFMN : Melt factor for snow on December 21 [mm H2O/ºC-day]"),
        TIMP(Double.class,"TIMP : Snow pack temperature lag factor"),
        SNOCOVMX(Double.class,"SNOCOVMX : Minimum snow water content that corresponds to 100% snow cover [mm]"),
        SNO50COV(Double.class,"SNO50COV : Fraction of snow volume represented by SNOCOVMX that corresponds to 50% snow cover"),
        IPET(Integer.class,"IPET: PET method: 0=priest-t, 1=pen-m, 2=har, 3=read into model"),
        PETFILE(String.class,"PETFILE: name of potential ET input file"),
        ESCO(Double.class,"ESCO: soil evaporation compensation factor"),
        EPCO(Double.class,"EPCO: plant water uptake compensation factor"),
        EVLAI(Double.class,"EVLAI : Leaf area index at which no evaporation occurs from water surface [m2/m2]"),
        FFCB(Double.class,"FFCB : Initial soil water storage expressed as a fraction of field capacity water content"),
        COMMENT3(String.class,""),
        IEVENT(Integer.class,"IEVENT: rainfall/runoff code: 0=daily rainfall/CN"),
        ICRK(Integer.class,"ICRK: crack flow code: 1=model crack flow in soil"),
        SURLAG(Double.class,"SURLAG : Surface runoff lag time [days]"),
        ADJ_PKR(Double.class,"ADJ_PKR : Peak rate adjustment factor for sediment routing in the subbasin (tributary channels)"),
        PRF(Double.class,"PRF : Peak rate adjustment factor for sediment routing in the main channel"),
        SPCON(Double.class,"SPCON : Linear parameter for calculating the maximum amount of sediment that can be reentrained during channel sediment routing"),
        SPEXP(Double.class,"SPEXP : Exponent parameter for calculating sediment reentrained in channel sediment routing"),
        COMMENT4(String.class,""),
        RCN(Double.class,"RCN : Concentration of nitrogen in rainfall [mg N/l]"),
        CMN(Double.class,"CMN : Rate factor for humus mineralization of active organic nitrogen"),
        N_UPDIS(Double.class,"N_UPDIS : Nitrogen uptake distribution parameter"),
        P_UPDIS(Double.class,"P_UPDIS : Phosphorus uptake distribution parameter"),
        NPERCO(Double.class,"NPERCO : Nitrogen percolation coefficient"),
        PPERCO(Double.class,"PPERCO : Phosphorus percolation coefficient"),
        PHOSKD(Double.class,"PHOSKD : Phosphorus soil partitioning coefficient"),
        PSP(Double.class,"PSP : Phosphorus sorption coefficient"),
        RSDCO(Double.class,"RSDCO : Residue decomposition coefficient"),
        COMMENT5(String.class,""),
        PERCOP(Double.class,"PERCOP : Pesticide percolation coefficient"),
        COMMENT6(String.class,""),
        ISUBWQ(Integer.class,"ISUBWQ: subbasin water quality parameter"),
        COMMENT7(String.class,""),
        WDPQ(Double.class,"WDPQ : Die-off factor for persistent bacteria in soil solution. [1/day]"),
        WGPQ(Double.class,"WGPQ : Growth factor for persistent bacteria in soil solution [1/day]"),
        WDLPQ(Double.class,"WDLPQ : Die-off factor for less persistent bacteria in soil solution [1/day]"),
        WGLPQ(Double.class,"WGLPQ : Growth factor for less persistent bacteria in soil solution. [1/day]"),
        WDPS(Double.class,"WDPS : Die-off factor for persistent bacteria adsorbed to soil particles. [1/day]"),
        WGPS(Double.class,"WGPS : Growth factor for persistent bacteria adsorbed to soil particles. [1/day]"),
        WDLPS(Double.class,"WDLPS : Die-off factor for less persistent bacteria adsorbed to soil particles. [1/day]"),
        WGLPS(Double.class,"WGLPS : Growth factor for less persistent bacteria adsorbed to soil particles. [1/day]"),
        BACTKDQ(Double.class,"BACTKDQ : Bacteria partition coefficient"),
        THBACT(Double.class,"THBACT : Temperature adjustment factor for bacteria die-off/growth"),
        WOF_P(Double.class,"WOF_P: wash-off fraction for persistent bacteria on foliage"),
        WOF_LP(Double.class,"WOF_LP: wash-off fraction for less persistent bacteria on foliage"),
        WDPF(Double.class,"WDPF: persistent bacteria die-off factor on foliage"),
        WGPF(Double.class,"WGPF: persistent bacteria growth factor on foliage"),
        WDLPF(Double.class,"WDLPF: less persistent bacteria die-off factor on foliage"),
        WGLPF(Double.class,"WGLPF: less persistent bacteria growth factor on foliage"),
        ISED_DET(Integer.class,"ISED_DET: "),
        COMMENT8(String.class,""),
        IRTE(Integer.class,"IRTE: water routing method 0=variable travel-time 1=Muskingum"),
        MSK_CO1(Double.class,"MSK_CO1 : Calibration coefficient used to control impact of the storage time constant (Km) for normal flow"),
        MSK_CO2(Double.class,"MSK_CO2 : Calibration coefficient used to control impact of the storage time constant (Km) for low flow"),
        MSK_X(Double.class,"MSK_X : Weighting factor controlling relative importance of inflow rate and outflow rate in determining water storage in reach segment"),
        IDEG(Integer.class,"IDEG: channel degradation code"),
        IWQ(Integer.class,"IWQ: in-stream water quality: 1=model in-stream water quality"),
        WWQFILE(String.class,"WWQFILE: name of watershed water quality file"),
        TRNSRCH(Double.class,"TRNSRCH: reach transmission loss partitioning to deep aquifer"),
        EVRCH(Double.class,"EVRCH : Reach evaporation adjustment factor"),
        IRTPEST(Integer.class,"IRTPEST : Number of pesticide to be routed through the watershed channel network"),
        ICN(Integer.class,"ICN  : Daily curve number calculation method"),
        CNCOEF(Double.class,"CNCOEF : Plant ET curve number coefficient"),
        CDN(Double.class,"CDN : Denitrification exponential rate coefficient"),
        SDNCO(Double.class,"SDNCO : Denitrification threshold water content"),
        BACT_SWF(Double.class,"BACT_SWF : Fraction of manure applied to land areas that has active colony forming units"),
        BACTMX(Double.class,"BACTMX : Bacteria percolation coefficient [10 m3/Mg]."),
        BACTMINLP(Double.class,"BACTMINLP : Minimum daily bacteria loss for less persistent bacteria [# cfu/m2]"),
        BACTMINP(Double.class,"BACTMINP : Minimum daily bacteria loss for persistent bacteria [# cfu/m2]"),
        WDLPRCH(Double.class,"WDLPRCH: Die-off factor for less persistent bacteria in streams (moving water) at 20 C [1/day]"),
        WDPRCH(Double.class,"WDPRCH : Die-off factor for persistent bacteria in streams (moving water) at 20 C [1/day]"),
        WDLPRES(Double.class,"WDLPRES : Die-off factor for less persistent bacteria in water bodies (still water) at 20 C [1/day]"),
        WDPRES(Double.class,"WDPRES : Die-off factor for persistent bacteria in water bodies (still water) at 20 C [1/day]"),
        TB_ADJ(Double.class,"TB_ADJ : New variable in testing ...Adjustment factor for subdaily unit hydrograph basetime"),
        DEPIMP_BSN(Double.class,"DEPIMP_BSN : Depth to impervious layer for modeling perched water tables [mm]"),
        DDRAIN_BSN(Double.class,"DDRAIN_BSN : Depth to the sub-surface drain [mm]"),
        TDRAIN_BSN(Double.class,"TDRAIN_BSN : Time to drain soil to field capacity [hours]"),
        GDRAIN_BSN(Double.class,"GDRAIN_BSN : Drain tile lag time [hours]"),
        CN_FROZ(Double.class,"CN_FROZ : Parameter for frozen soil adjustment on infiltration/runoff"),
        DORM_HR(Double.class,"DORM_HR : Time threshold used to define dormancy [hours]"),
        SMXCO(Double.class,"SMXCO : Adjustment factor for maximum curve number S factor"),
        FIXCO(Double.class,"FIXCO : Nitrogen fixation coefficient"),
        NFIXMX(Double.class,"NFIXMX : Maximum daily-n fixation [kg/ha]"),
        ANION_EXCL_BSN(Double.class,"ANION_EXCL_BSN : Fraction of porosity from which anions are excluded"),
        CH_ONCO_BSN(Double.class,"CH_ONCO_BSN : Channel organic nitrogen concentration in basin [ppm]"),
        CH_OPCO_BSN(Double.class,"CH_OPCO_BSN : Channel organic phosphorus concentration in basin [ppm]"),
        HLIFE_NGW_BSN(Double.class,"HLIFE_NGW_BSN : Half-life of nitrogen in groundwater [days]"),
        RCN_SUB_BSN(Double.class,"RCN_SUB_BSN : Concentration of nitrate in precipitation [ppm]"),
        BC1_BSN(Double.class,"BC1_BSN : Rate constant for biological oxidation of NH3 [1/day]"),
        BC2_BSN(Double.class,"BC2_BSN : Rate constant for biological oxidation NO2 to NO3 [1/day]"),
        BC3_BSN(Double.class,"BC3_BSN : Rate constant for hydrolosis of organic nitrogen to ammonia [1/day]"),
        BC4_BSN(Double.class,"BC4_BSN : Rate constant for decay of organic phosphorus to dissolved phosphorus [1/day]"),
        DECR_MIN(Double.class,"DECR_MIN: Minimum daily residue decay"),
        ICFAC(Double.class,"ICFAC : C-factor calculation method"),
        RSD_COVCO(Double.class,"RSD_COVCO : Residue cover factor for computing fraction of cover"),
        VCRIT(Double.class,"VCRIT : Critical velocity"),
        CSWAT(Integer.class,"CSWAT : Code for new carbon routines"),
        RES_STLR_CO(Double.class,"RES_STLR_CO : Reservoir sediment settling coefficient"),
        BFLO_DIST(Double.class,"BFLO_DIST 0-1 (1:profile of baseflow in a day follows rainfall pattern, 0:baseflow evenly distributed to each time step during a day"),
        IUH(Integer.class,"IUH : Unit hydrograph method: 1=triangular UH, 2=gamma function UH"),
        UHALPHA(Double.class,"UHALPHA : alpha coefficient for gamma function unit hydrograph. Required if iuh=2 is selected"),
        COMMENT9(String.class,""),
        SKIP0(String.class,""),
        COMMENT10(String.class,""),
        EROS_SPL(Double.class,"EROS_SPL: The splash erosion coefficient ranges 0.9 - 3.1"),
        RILL_MULT(Double.class,"RILL_MULT: Multiplier to USLE_K for soil susceptible to rill erosion, ranges 0.5 - 2.0"),
        EROS_EXPO(Double.class,"EROS_EXPO: an exponent in the overland flow erosion equation, ranges 1.5 - 3.0"),
        SUBD_CHSED(Double.class,"SUBD_CHSED: 1=Brownlie(1981) model, 2=Yang(1973,1984) model"),
        C_FACTOR(Double.class,"C_FACTOR: Scaling parameter for Cover and management factor in ANSWERS erosion model"),
        CH_D50(Double.class,"CH_D50 : median particle diameter of channel bed [mm]"),
        SIG_G(Double.class,"SIG_G : geometric standard deviation of particle sizes"),
        RE_BSN(Double.class,"RE_BSN: Effective radius of drains"),
        SDRAIN_BSN(Double.class,"SDRAIN_BSN: Distance between two drain or tile tubes"),
        DRAIN_CO_BSN(Double.class,"DRAIN_CO_BSN: Drainage coefficient"),
        PC_BSN(Double.class,"PC_BSN: Pump capacity"),
        LATKSATF_BSN(Double.class,"LATKSATF_BSN: Multiplication factor to determine lateral ksat from SWAT ksat input value for HRU"),
        ITDRN(Integer.class,"ITDRN: Tile drainage equations flag"),
        IWTDN(Integer.class,"IWTDN: Water table depth algorithms flag"),
        SOL_P_MODEL(Double.class,"SOL_P_MODEL: if = 1, use new soil P model"),
        IABSTR(Double.class,"IABSTR: Initial abstraction on impervious cover (mm)"),
        IATMODEP(Integer.class,"IATMODEP: 0 = average annual inputs 1 = monthly inputs");
        
        private final Class classType;
        private final String description;
        
        fields(Class classType, String description) {
            this.classType = classType;
            this.description = description;
        }
        
        public Class getFieldClassType(){
            return classType;            
        }
        public String getFieldClassName(){
            return classType.toString();
        }
        public String getDescription(){
            return this.description;
        }
    }
    @Override
    public Class getFieldClassType(String fieldNameStr) {
        return fields.valueOf(fieldNameStr).getFieldClassType();
    }
    
    private final EnumMap<Basins.fields,Object> values;
    
    @SuppressWarnings("unchecked")
    public Basins() {
        this.values = new EnumMap(fields.class);
    }
    @SuppressWarnings("unchecked")
    public Basins(String filename)
            throws IOException {
        this.values = new EnumMap(fields.class);
        this.readSWATFileFormat(filename);
    }
    
    @Override
    public final Basins set(Basins.fields fieldName, Object value)
            throws IllegalArgumentException {
        if (value.getClass().equals(fieldName.getFieldClassType())) {
            values.put(fieldName,value);
        } else {
            throw new IllegalArgumentException(
                    String.format("Expected %s type for field %s, but got %s",
                            fieldName.getFieldClassType(),
                            fieldName,
                            value.getClass()));
        }
        return this;
    }
    @Override
    public final Basins set(String fieldNameStr, Object value) {
        return set(Basins.fields.valueOf(fieldNameStr), value);
    }
    @Override
    public final Basins set(String fieldNameStr, String value) {
        fields fieldName = fields.valueOf(fieldNameStr);
        return set(fieldName,Convertor.convertStringValueTo(value, fieldName.getFieldClassName()));
    }

    @Override
    public Object get(Basins.fields fieldName)
            throws NullPointerException {
        return values.get(fieldName);
    }
    @Override
    public Object get(String fieldNameStr) {
        return values.get(Basins.fields.valueOf(fieldNameStr));
    }
    
    public Basins getTITLE(String v) {
        return this.set(fields.TITLE,v);
    }
    public Basins setSFTMP(double v) {
        return this.set(fields.SFTMP,v);
    }
    public Basins setSMTMP(double v) {
        return this.set(fields.SMTMP,v);
    }
    public Basins setSMFMX(double v) {
        return this.set(fields.SMFMX,v);
    }
    public Basins setSMFMN(double v) {
        return this.set(fields.SMFMN,v);
    }
    public Basins setTIMP(double v) {
        return this.set(fields.TIMP,v);
    }
    public Basins setSNOCOVMX(double v) {
        return this.set(fields.SNOCOVMX,v);
    }
    public Basins setSNO50COV(double v) {
        return this.set(fields.SNO50COV,v);
    }
    public Basins setIPET(int v) {
        return this.set(fields.IPET,v);
    }
    public Basins setPETFILE(String v) {
        return this.set(fields.PETFILE,v);
    }
    public Basins setESCO(double v) {
        return this.set(fields.ESCO,v);
    }
    public Basins setEPCO(double v) {
        return this.set(fields.EPCO,v);
    }
    public Basins setEVLAI(double v) {
        return this.set(fields.EVLAI,v);
    }
    public Basins setFFCB(double v) {
        return this.set(fields.FFCB,v);
    }
    public Basins setIEVENT(int v) {
        return this.set(fields.IEVENT,v);
    }
    public Basins setICRK(int v) {
        return this.set(fields.ICRK,v);
    }
    public Basins setSURLAG(double v) {
        return this.set(fields.SURLAG,v);
    }
    public Basins setADJ_PKR(double v) {
        return this.set(fields.ADJ_PKR,v);
    }
    public Basins setPRF(double v) {
        return this.set(fields.PRF,v);
    }
    public Basins setSPCON(double v) {
        return this.set(fields.SPCON,v);
    }
    public Basins setSPEXP(double v) {
        return this.set(fields.SPEXP,v);
    }
    public Basins setRCN(double v) {
        return this.set(fields.RCN,v);
    }
    public Basins setCMN(double v) {
        return this.set(fields.CMN,v);
    }
    public Basins setN_UPDIS(double v) {
        return this.set(fields.N_UPDIS,v);
    }
    public Basins setP_UPDIS(double v) {
        return this.set(fields.P_UPDIS,v);
    }
    public Basins setNPERCO(double v) {
        return this.set(fields.NPERCO,v);
    }
    public Basins setPPERCO(double v) {
        return this.set(fields.PPERCO,v);
    }
    public Basins setPHOSKD(double v) {
        return this.set(fields.PHOSKD,v);
    }
    public Basins setPSP(double v) {
        return this.set(fields.PSP,v);
    }
    public Basins setRSDCO(double v) {
        return this.set(fields.RSDCO,v);
    }
    public Basins setPERCOP(double v) {
        return this.set(fields.PERCOP,v);
    }
    public Basins setISUBWQ(int v) {
        return this.set(fields.ISUBWQ,v);
    }
    public Basins setWDPQ(double v) {
        return this.set(fields.WDPQ,v);
    }
    public Basins setWGPQ(double v) {
        return this.set(fields.WGPQ,v);
    }
    public Basins setWDLPQ(double v) {
        return this.set(fields.WDLPQ,v);
    }
    public Basins setWGLPQ(double v) {
        return this.set(fields.WGLPQ,v);
    }
    public Basins setWDPS(double v) {
        return this.set(fields.WDPS,v);
    }
    public Basins setWGPS(double v) {
        return this.set(fields.WGPS,v);
    }
    public Basins setWDLPS(double v) {
        return this.set(fields.WDLPS,v);
    }
    public Basins setWGLPS(double v) {
        return this.set(fields.WGLPS,v);
    }
    public Basins setBACTKDQ(double v) {
        return this.set(fields.BACTKDQ,v);
    }
    public Basins setTHBACT(double v) {
        return this.set(fields.THBACT,v);
    }
    public Basins setWOF_P(double v) {
        return this.set(fields.WOF_P,v);
    }
    public Basins setWOF_LP(double v) {
        return this.set(fields.WOF_LP,v);
    }
    public Basins setWDPF(double v) {
        return this.set(fields.WDPF,v);
    }
    public Basins setWGPF(double v) {
        return this.set(fields.WGPF,v);
    }
    public Basins setWDLPF(double v) {
        return this.set(fields.WDLPF,v);
    }
    public Basins setWGLPF(double v) {
        return this.set(fields.WGLPF,v);
    }
    public Basins setISED_DET(int v) {
        return this.set(fields.ISED_DET,v);
    }
    public Basins setIRTE(int v) {
        return this.set(fields.IRTE,v);
    }
    public Basins setMSK_CO1(double v) {
        return this.set(fields.MSK_CO1,v);
    }
    public Basins setMSK_CO2(double v) {
        return this.set(fields.MSK_CO2,v);
    }
    public Basins setMSK_X(double v) {
        return this.set(fields.MSK_X,v);
    }
    public Basins setIDEG(int v) {
        return this.set(fields.IDEG,v);
    }
    public Basins setIWQ(int v) {
        return this.set(fields.IWQ,v);
    }
    public Basins setWWQFILE(String v) {
        return this.set(fields.WWQFILE,v);
    }
    public Basins setTRNSRCH(double v) {
        return this.set(fields.TRNSRCH,v);
    }
    public Basins setEVRCH(double v) {
        return this.set(fields.EVRCH,v);
    }
    public Basins setIRTPEST(int v) {
        return this.set(fields.IRTPEST,v);
    }
    public Basins setICN(int v) {
        return this.set(fields.ICN,v);
    }
    public Basins setCNCOEF(double v) {
        return this.set(fields.CNCOEF,v);
    }
    public Basins setCDN(double v) {
        return this.set(fields.CDN,v);
    }
    public Basins setSDNCO(double v) {
        return this.set(fields.SDNCO,v);
    }
    public Basins setBACT_SWF(double v) {
        return this.set(fields.BACT_SWF,v);
    }
    public Basins setBACTMX(double v) {
        return this.set(fields.BACTMX,v);
    }
    public Basins setBACTMINLP(double v) {
        return this.set(fields.BACTMINLP,v);
    }
    public Basins setBACTMINP(double v) {
        return this.set(fields.BACTMINP,v);
    }
    public Basins setWDLPRCH(double v) {
        return this.set(fields.WDLPRCH,v);
    }
    public Basins setWDPRCH(double v) {
        return this.set(fields.WDPRCH,v);
    }
    public Basins setWDLPRES(double v) {
        return this.set(fields.WDLPRES,v);
    }
    public Basins setWDPRES(double v) {
        return this.set(fields.WDPRES,v);
    }
    public Basins setTB_ADJ(double v) {
        return this.set(fields.TB_ADJ,v);
    }
    public Basins setDEPIMP_BSN(double v) {
        return this.set(fields.DEPIMP_BSN,v);
    }
    public Basins setDDRAIN_BSN(double v) {
        return this.set(fields.DDRAIN_BSN,v);
    }
    public Basins setTDRAIN_BSN(double v) {
        return this.set(fields.TDRAIN_BSN,v);
    }
    public Basins setGDRAIN_BSN(double v) {
        return this.set(fields.GDRAIN_BSN,v);
    }
    public Basins setCN_FROZ(double v) {
        return this.set(fields.CN_FROZ,v);
    }
    public Basins setDORM_HR(double v) {
        return this.set(fields.DORM_HR,v);
    }
    public Basins setSMXCO(double v) {
        return this.set(fields.SMXCO,v);
    }
    public Basins setFIXCO(double v) {
        return this.set(fields.FIXCO,v);
    }
    public Basins setNFIXMX(double v) {
        return this.set(fields.NFIXMX,v);
    }
    public Basins setANION_EXCL_BSN(double v) {
        return this.set(fields.ANION_EXCL_BSN,v);
    }
    public Basins setCH_ONCO_BSN(double v) {
        return this.set(fields.CH_ONCO_BSN,v);
    }
    public Basins setCH_OPCO_BSN(double v) {
        return this.set(fields.CH_OPCO_BSN,v);
    }
    public Basins setHLIFE_NGW_BSN(double v) {
        return this.set(fields.HLIFE_NGW_BSN,v);
    }
    public Basins setRCN_SUB_BSN(double v) {
        return this.set(fields.RCN_SUB_BSN,v);
    }
    public Basins setBC1_BSN(double v) {
        return this.set(fields.BC1_BSN,v);
    }
    public Basins setBC2_BSN(double v) {
        return this.set(fields.BC2_BSN,v);
    }
    public Basins setBC3_BSN(double v) {
        return this.set(fields.BC3_BSN,v);
    }
    public Basins setBC4_BSN(double v) {
        return this.set(fields.BC4_BSN,v);
    }
    public Basins setDECR_MIN(double v) {
        return this.set(fields.DECR_MIN,v);
    }
    public Basins setICFAC(double v) {
        return this.set(fields.ICFAC,v);
    }
    public Basins setRSD_COVCO(double v) {
        return this.set(fields.RSD_COVCO,v);
    }
    public Basins setVCRIT(double v) {
        return this.set(fields.VCRIT,v);
    }
    public Basins setCSWAT(int v) {
        return this.set(fields.CSWAT,v);
    }
    public Basins setRES_STLR_CO(double v) {
        return this.set(fields.RES_STLR_CO,v);
    }
    public Basins setBFLO_DIST(double v) {
        return this.set(fields.BFLO_DIST,v);
    }
    public Basins setIUH(int v) {
        return this.set(fields.IUH,v);
    }
    public Basins setUHALPHA(double v) {
        return this.set(fields.UHALPHA,v);
    }
    public Basins setEROS_SPL(double v) {
        return this.set(fields.EROS_SPL,v);
    }
    public Basins setRILL_MULT(double v) {
        return this.set(fields.RILL_MULT,v);
    }
    public Basins setEROS_EXPO(double v) {
        return this.set(fields.EROS_EXPO,v);
    }
    public Basins setSUBD_CHSED(double v) {
        return this.set(fields.SUBD_CHSED,v);
    }
    public Basins setC_FACTOR(double v) {
        return this.set(fields.C_FACTOR,v);
    }
    public Basins setCH_D50(double v) {
        return this.set(fields.CH_D50,v);
    }
    public Basins setSIG_G(double v) {
        return this.set(fields.SIG_G,v);
    }
    public Basins setRE_BSN(double v) {
        return this.set(fields.RE_BSN,v);
    }
    public Basins setSDRAIN_BSN(double v) {
        return this.set(fields.SDRAIN_BSN,v);
    }
    public Basins setDRAIN_CO_BSN(double v) {
        return this.set(fields.DRAIN_CO_BSN,v);
    }
    public Basins setPC_BSN(double v) {
        return this.set(fields.PC_BSN,v);
    }
    public Basins setLATKSATF_BSN(double v) {
        return this.set(fields.LATKSATF_BSN,v);
    }
    public Basins setITDRN(int v) {
        return this.set(fields.ITDRN,v);
    }
    public Basins setIWTDN(int v) {
        return this.set(fields.IWTDN,v);
    }
    public Basins setSOL_P_MODEL(int v) {
        return this.set(fields.SOL_P_MODEL,v);
    }
    public Basins setIABSTR(double v) {
        return this.set(fields.IABSTR,v);
    }
    public Basins setIATMODEP(int v) {
        return this.set(fields.IATMODEP,v);
    }
    
    public String getTITLE() {
	return (String) this.get(fields.TITLE);
    }
    public double getSFTMP() {
        return (double) this.get(fields.SFTMP);
    }
    public double getSMTMP() {
        return (double) this.get(fields.SMTMP);
    }
    public double getSMFMX() {
        return (double) this.get(fields.SMFMX);
    }
    public double getSMFMN() {
        return (double) this.get(fields.SMFMN);
    }
    public double getTIMP() {
        return (double) this.get(fields.TIMP);
    }
    public double getSNOCOVMX() {
        return (double) this.get(fields.SNOCOVMX);
    }
    public double getSNO50COV() {
        return (double) this.get(fields.SNO50COV);
    }
    public int getIPET() {
        return (int) this.get(fields.IPET);
    }
    public String getPETFILE() {
        return (String) this.get(fields.PETFILE);
    }
    public double getESCO() {
        return (double) this.get(fields.ESCO);
    }
    public double getEPCO() {
        return (double) this.get(fields.EPCO);
    }
    public double getEVLAI() {
        return (double) this.get(fields.EVLAI);
    }
    public double getFFCB() {
        return (double) this.get(fields.FFCB);
    }
    public int getIEVENT() {
        return (int) this.get(fields.IEVENT);
    }
    public int getICRK() {
        return (int) this.get(fields.ICRK);
    }
    public double getSURLAG() {
        return (double) this.get(fields.SURLAG);
    }
    public double getADJ_PKR() {
        return (double) this.get(fields.ADJ_PKR);
    }
    public double getPRF() {
        return (double) this.get(fields.PRF);
    }
    public double getSPCON() {
        return (double) this.get(fields.SPCON);
    }
    public double getSPEXP() {
        return (double) this.get(fields.SPEXP);
    }
    public double getRCN() {
        return (double) this.get(fields.RCN);
    }
    public double getCMN() {
        return (double) this.get(fields.CMN);
    }
    public double getN_UPDIS() {
        return (double) this.get(fields.N_UPDIS);
    }
    public double getP_UPDIS() {
        return (double) this.get(fields.P_UPDIS);
    }
    public double getNPERCO() {
        return (double) this.get(fields.NPERCO);
    }
    public double getPPERCO() {
        return (double) this.get(fields.PPERCO);
    }
    public double getPHOSKD() {
        return (double) this.get(fields.PHOSKD);
    }
    public double getPSP() {
        return (double) this.get(fields.PSP);
    }
    public double getRSDCO() {
        return (double) this.get(fields.RSDCO);
    }
    public double getPERCOP() {
        return (double) this.get(fields.PERCOP);
    }
    public int getISUBWQ() {
        return (int) this.get(fields.ISUBWQ);
    }
    public double getWDPQ() {
        return (double) this.get(fields.WDPQ);
    }
    public double getWGPQ() {
        return (double) this.get(fields.WGPQ);
    }
    public double getWDLPQ() {
        return (double) this.get(fields.WDLPQ);
    }
    public double getWGLPQ() {
        return (double) this.get(fields.WGLPQ);
    }
    public double getWDPS() {
        return (double) this.get(fields.WDPS);
    }
    public double getWGPS() {
        return (double) this.get(fields.WGPS);
    }
    public double getWDLPS() {
        return (double) this.get(fields.WDLPS);
    }
    public double getWGLPS() {
        return (double) this.get(fields.WGLPS);
    }
    public double getBACTKDQ() {
        return (double) this.get(fields.BACTKDQ);
    }
    public double getTHBACT() {
        return (double) this.get(fields.THBACT);
    }
    public double getWOF_P() {
        return (double) this.get(fields.WOF_P);
    }
    public double getWOF_LP() {
        return (double) this.get(fields.WOF_LP);
    }
    public double getWDPF() {
        return (double) this.get(fields.WDPF);
    }
    public double getWGPF() {
        return (double) this.get(fields.WGPF);
    }
    public double getWDLPF() {
        return (double) this.get(fields.WDLPF);
    }
    public double getWGLPF() {
        return (double) this.get(fields.WGLPF);
    }
    public int getISED_DET() {
        return (int) this.get(fields.ISED_DET);
    }
    public int getIRTE() {
        return (int) this.get(fields.IRTE);
    }
    public double getMSK_CO1() {
        return (double) this.get(fields.MSK_CO1);
    }
    public double getMSK_CO2() {
        return (double) this.get(fields.MSK_CO2);
    }
    public double getMSK_X() {
        return (double) this.get(fields.MSK_X);
    }
    public int getIDEG() {
        return (int) this.get(fields.IDEG);
    }
    public int getIWQ() {
        return (int) this.get(fields.IWQ);
    }
    public String getWWQFILE() {
        return (String) this.get(fields.WWQFILE);
    }
    public double getTRNSRCH() {
        return (double) this.get(fields.TRNSRCH);
    }
    public double getEVRCH() {
        return (double) this.get(fields.EVRCH);
    }
    public int getIRTPEST() {
        return (int) this.get(fields.IRTPEST);
    }
    public int getICN() {
        return (int) this.get(fields.ICN);
    }
    public double getCNCOEF() {
        return (double) this.get(fields.CNCOEF);
    }
    public double getCDN() {
        return (double) this.get(fields.CDN);
    }
    public double getSDNCO() {
        return (double) this.get(fields.SDNCO);
    }
    public double getBACT_SWF() {
        return (double) this.get(fields.BACT_SWF);
    }
    public double getBACTMX() {
        return (double) this.get(fields.BACTMX);
    }
    public double getBACTMINLP() {
        return (double) this.get(fields.BACTMINLP);
    }
    public double getBACTMINP() {
        return (double) this.get(fields.BACTMINP);
    }
    public double getWDLPRCH() {
        return (double) this.get(fields.WDLPRCH);
    }
    public double getWDPRCH() {
        return (double) this.get(fields.WDPRCH);
    }
    public double getWDLPRES() {
        return (double) this.get(fields.WDLPRES);
    }
    public double getWDPRES() {
        return (double) this.get(fields.WDPRES);
    }
    public double getTB_ADJ() {
        return (double) this.get(fields.TB_ADJ);
    }
    public double getDEPIMP_BSN() {
        return (double) this.get(fields.DEPIMP_BSN);
    }
    public double getDDRAIN_BSN() {
        return (double) this.get(fields.DDRAIN_BSN);
    }
    public double getTDRAIN_BSN() {
        return (double) this.get(fields.TDRAIN_BSN);
    }
    public double getGDRAIN_BSN() {
        return (double) this.get(fields.GDRAIN_BSN);
    }
    public double getCN_FROZ() {
        return (double) this.get(fields.CN_FROZ);
    }
    public double getDORM_HR() {
        return (double) this.get(fields.DORM_HR);
    }
    public double getSMXCO() {
        return (double) this.get(fields.SMXCO);
    }
    public double getFIXCO() {
        return (double) this.get(fields.FIXCO);
    }
    public double getNFIXMX() {
        return (double) this.get(fields.NFIXMX);
    }
    public double getANION_EXCL_BSN() {
        return (double) this.get(fields.ANION_EXCL_BSN);
    }
    public double getCH_ONCO_BSN() {
        return (double) this.get(fields.CH_ONCO_BSN);
    }
    public double getCH_OPCO_BSN() {
        return (double) this.get(fields.CH_OPCO_BSN);
    }
    public double getHLIFE_NGW_BSN() {
        return (double) this.get(fields.HLIFE_NGW_BSN);
    }
    public double getRCN_SUB_BSN() {
        return (double) this.get(fields.RCN_SUB_BSN);
    }
    public double getBC1_BSN() {
        return (double) this.get(fields.BC1_BSN);
    }
    public double getBC2_BSN() {
        return (double) this.get(fields.BC2_BSN);
    }
    public double getBC3_BSN() {
        return (double) this.get(fields.BC3_BSN);
    }
    public double getBC4_BSN() {
        return (double) this.get(fields.BC4_BSN);
    }
    public double getDECR_MIN() {
        return (double) this.get(fields.DECR_MIN);
    }
    public double getICFAC() {
        return (double) this.get(fields.ICFAC);
    }
    public double getRSD_COVCO() {
        return (double) this.get(fields.RSD_COVCO);
    }
    public double getVCRIT() {
        return (double) this.get(fields.VCRIT);
    }
    public int getCSWAT() {
        return (int) this.get(fields.CSWAT);
    }
    public double getRES_STLR_CO() {
        return (double) this.get(fields.RES_STLR_CO);
    }
    public double getBFLO_DIST() {
        return (double) this.get(fields.BFLO_DIST);
    }
    public int getIUH() {
        return (int) this.get(fields.IUH);
    }
    public double getUHALPHA() {
        return (double) this.get(fields.UHALPHA);
    }
    public double getEROS_SPL() {
        return (double) this.get(fields.EROS_SPL);
    }
    public double getRILL_MULT() {
        return (double) this.get(fields.RILL_MULT);
    }
    public double getEROS_EXPO() {
        return (double) this.get(fields.EROS_EXPO);
    }
    public double getSUBD_CHSED() {
        return (double) this.get(fields.SUBD_CHSED);
    }
    public double getC_FACTOR() {
        return (double) this.get(fields.C_FACTOR);
    }
    public double getCH_D50() {
        return (double) this.get(fields.CH_D50);
    }
    public double getSIG_G() {
        return (double) this.get(fields.SIG_G);
    }
    public double getRE_BSN() {
        return (double) this.get(fields.RE_BSN);
    }
    public double getSDRAIN_BSN() {
        return (double) this.get(fields.SDRAIN_BSN);
    }
    public double getDRAIN_CO_BSN() {
        return (double) this.get(fields.DRAIN_CO_BSN);
    }
    public double getPC_BSN() {
        return (double) this.get(fields.PC_BSN);
    }
    public double getLATKSATF_BSN() {
        return (double) this.get(fields.LATKSATF_BSN);
    }
    public int getITDRN() {
        return (int) this.get(fields.ITDRN);
    }
    public int getIWTDN() {
        return (int) this.get(fields.IWTDN);
    }
    public int getSOL_P_MODEL() {
        return (int) this.get(fields.SOL_P_MODEL);
    }
    public double getIABSTR() {
        return (double) this.get(fields.IABSTR);
    }
    public int getIATMODEP() {
        return (int) this.get(fields.IATMODEP);
    }

    public boolean contains(Basins.fields fieldName)
            throws IllegalArgumentException {
        return values.containsKey(fieldName);
    }
    public boolean contains(String fieldNameStr)
            throws IllegalArgumentException {
        return values.containsKey(Basins.fields.valueOf(fieldNameStr));
    }
    public boolean containsAllFields() {
        for (Basins.fields fieldName: Basins.fields.values()) {
            if (!values.containsKey(fieldName)) {
                return false;
            }
        }
        return true;
    }
    public boolean containsAllFieldsIgnoring(List<Basins.fields> fieldNamesList) {
        for (Basins.fields fieldName: Basins.fields.values()) {
            if (!fieldNamesList.contains(fieldName) && !values.containsKey(fieldName)) {
                return false;
            }
        }
        return true;
    }
    @SuppressWarnings("unchecked")
    public boolean containsAllFieldsIgnoring(String[] fieldNamesStr) {
        List<Basins.fields> fieldNamesList = new ArrayList(fieldNamesStr.length);
        for (String fieldNameStr: fieldNamesStr)
            fieldNamesList.add(Basins.fields.valueOf(fieldNameStr));
        return containsAllFieldsIgnoring(fieldNamesList);
    }
    
    @Override
    public Basins readSWATFileFormat(String filename)
            throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename),StandardCharsets.ISO_8859_1)) {
            String line;
            for (fields key: fields.values()) {
                if ((line = reader.readLine())== null) {
                    throw new IOException(
                            String.format("Could not read the value for %s", key.toString()));
                } else {
                    if (key.getFieldClassType().equals(String.class)) {
                        switch (key){
                            case PETFILE:
                            case WWQFILE:
                                this.set(key, line.substring(1, 14).trim());
                                break;
                            default:
                                this.set(key, line);
                        }
                    }
                    if (key.getFieldClassType().equals(Integer.class)) {
                        this.set(key, Integer.parseInt(line.substring(0,18).trim()));
                    }
                    if (key.getFieldClassType().equals(Double.class)) {
                        this.set(key, Double.parseDouble(line.substring(0,18).trim()));
                    }
                }
            }
        }
        return this;
    }
    @Override
    public void writeSWATFileFormat(String filename)
            throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename),StandardCharsets.UTF_8)) {
            writer.write(this.toSWATTXTFormat());
        }
    }
    public static Basins newFromSWATFile(String filename)
            throws IOException {
        Basins bsn = new Basins();
        return bsn.readSWATFileFormat(filename);
    }
    @SuppressWarnings("unchecked")
    public static List<Basins> newFromSWATFiles(String[] filenames)
            throws IOException {
        ArrayList<Basins> bsns = new ArrayList();
        for (String filename: filenames){
            bsns.add(newFromSWATFile(filename));
        }
        return bsns;
    }
    
    
    @Override
    public String toString(){
        return this.toJSONString();
    }
    public String toString(String stringOutputType) throws IllegalArgumentException{
        switch (stringOutputType){
            case "JSON":
                return this.toJSONString();
            case "SWAT":
                return this.toSWATTXTFormat();
            default:
                throw new IllegalArgumentException(String.format("String output Type could be: 1) JSON, (2) SWAT; But got %s", stringOutputType));
        }
            
    }
    public String toJSONString() {
        if (this.containsAllFields()) {
            StringBuilder strBldr = new StringBuilder("{");
            for (Basins.fields key: Basins.fields.values()) {
                switch (key) {
                    case TITLE:
                        strBldr.append(String.format("TITLE: \"%s\"", this.get(key)));
                        break;
                    case COMMENT1:
                    case COMMENT2:
                    case COMMENT3:
                    case COMMENT4:
                    case COMMENT5:
                    case COMMENT6:
                    case COMMENT7:
                    case COMMENT8:
                    case COMMENT9:
                    case COMMENT10:
                        strBldr.append(String.format(", %s: \"%s\"", key, this.get(key)));
                        break;
                    case SKIP0:
                        // do nothing
                        break;
                    default:
                        strBldr.append(String.format(", %s: %s", key, this.get(key)));
                        break;
                }
            }
            return strBldr.append("}").toString();
        } else {
            return null;
        }
    }
    public String toSWATTXTFormat() {
        if (this.containsAllFields()) {
            StringBuilder strBldr = new StringBuilder("");
            for (Basins.fields key: Basins.fields.values()) {
                switch (key) {
                    case TITLE:
                    case COMMENT1:
                    case COMMENT2:
                    case COMMENT3:
                    case COMMENT4:
                    case COMMENT5:
                    case COMMENT6:
                    case COMMENT7:
                    case COMMENT8:
                    case COMMENT9:
                    case COMMENT10:
                    case SKIP0:
                        strBldr.append(String.format("%s\n", this.get(key)));
                        break;
                    case PETFILE:
                    case WWQFILE:
                        strBldr.append(String.format(
                                    "%13s       | %s\n",
                                    this.get(key),
                                    key.getDescription()));
                        break;
                    default:
                        if (key.getFieldClassType().equals(Double.class)) {
                            strBldr.append(String.format(
                                    "%16.3f    | %s\n",
                                    this.get(key),
                                    key.getDescription()));
                        }
                        if (key.getFieldClassType().equals(Integer.class)) {
                            strBldr.append(String.format(
                                    "%16d    | %s\n",
                                    this.get(key),
                                    key.getDescription()));
                        }
                }
            }
            return strBldr.toString();
        } else {
            return null;
        }
    }
}
