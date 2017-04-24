clear;clc;close all
%%
pcpFilenames = {'../../../SampleFiles/PCP/pcp1.pcp', ...
                '../../../SampleFiles/PCP/pcp2.pcp', ...
                '../../../SampleFiles/PCP/pcp3.pcp', ...
                '../../../SampleFiles/PCP/pcp4.pcp', ...
                '../../../SampleFiles/PCP/pcp5.pcp', ...
                '../../../SampleFiles/PCP/pcp6.pcp', ...
                '../../../SampleFiles/PCP/pcp7.pcp', ...
                '../../../SampleFiles/PCP/pcp8.pcp', ...
                '../../../SampleFiles/PCP/pcp9.pcp', ...
                '../../../SampleFiles/PCP/pcp10.pcp', ...
                '../../../SampleFiles/PCP/pcp11.pcp', ...
                '../../../SampleFiles/PCP/pcp12.pcp', ...
                '../../../SampleFiles/PCP/pcp13.pcp', ...
                '../../../SampleFiles/PCP/pcp14.pcp'};
pcpFileFormat = 'daily';
nrgfil = 300; % number of gage records in each pcp file
nrtot = 3920; % number of precip gage records used in simulation.
outputDir = './';
pcpFilePrefix = 'pcp';
options.verbose = true;
%%
SWATPCPData = readSWATPCP(pcpFilenames,pcpFileFormat,nrgfil,nrtot,options);

nrgfil=1800;

%%
output=writeSWATPCP(SWATPCPData, outputDir,nrgfil,options);












































