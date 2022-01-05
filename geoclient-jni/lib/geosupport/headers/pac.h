#ifndef GEOSUPPORT
#define GEOSUPPORT
#ifdef __cplusplus
extern "C" {
#endif
 /*********************************************************************/
 /*                                                                   */
 /*  YNL add 2020 census data: tract, block nta cdta     05/2021 V21.3*/
 /*  Add new 1 byte field 'truck_route_type'   in functions:          */
 /*  1/1E (Extended),1B, 3X, 3CX, 3E, 3CE            YNL 07/2019 V19.3*/
 /*  Add new 1 byte field 'Police Service Area'in functions:          */
 /*  1/1E (Extended),1B.                             YNL 02/2019 V19.2*/
 /*  Add new 4 bytes field 'Police Sector' in functions:              */
 /*  1/1E (Extended),1B,2,3(Extended),3C(Extended).  YNL 02/2019 V19.2*/
 /*  Add new 5 bytes field 'PUMA code' in functions:                  */
 /*  1/1E (Extended),1B,3/3C (Extended).             TLV 12/2017 V18.1*/
 /*  Add new 3 bytes field 'DCP Zoning Map' in       TLV 09/2017 V17.4*/
 /*  functions: 1A,BL,BN; 1A,BL,BN(extended) and 1B.                  */
 /*  Add new 2 bytes field 'Speed Limit' in          TLV 09/2017 V17.4*/
 /*  functions: 1/1E(extended), 1B, 3X, 3CX.                          */
 /*  Add new 2 bytes Bike Traffic Direction          TLV 12/2016 V17.1*/
 /*  Replaced sanit_reserved with sanit_bulk_pick_up TLV  9/2016 V16.4*/
 /*  Added 'unit' fields to WA1                      TLV  9/2016 V16.4*/
 /*  Add new 2 bytes Bike Lane and Max Str Width     TLV  9/2016 V16.4*/
 /*                                                                   */
 /*********************************************************************/
 /*                                                                   */
 /*            GeoSupport System C-Language Header File               */
 /*               for Platform-Independent Work Areas                 */
 /*                                                                   */
 /*                 Last Updated: February 2016                       */
 /*                                                                   */
 /*********************************************************************/

 /*********************************************************************/
 /*                                                                   */
 /*      Group Items Used in Platform-Independent Work Area 1         */
 /*                                                                   */
 /*********************************************************************/
#define UNIT_SIZE     14               //unit type+identifier v16.4
#define UNITT_SIZE     4               //unit type            v16.4
#define UNITI_SIZE    10               //unit identfier       v16.4

typedef struct { char boro;              // Borough Code
                 char SC10[10];          // 10 Digit Street Code
                 char Street_name[32];   // Street Name
               } STREET;

typedef union { char bbl[10];            /* Borough-Block-Lot        */
                struct { char boro;      /* Borough                  */
                         char block[5];  /* Tax Block                */
                         char lot[4];    /* Tax Lot                  */
                       } cas;
              } BBL;

typedef struct {
  char unitt[UNITT_SIZE];                /* Output unit type  V16.4 */
  char uniti[UNITI_SIZE];                /* Output unit identifier  */
} UNIT, *PUNIT;                          /* Output unit       V16.4 */


typedef struct {
                 char func_code[2];      /* Function Code            */
                 char hse_nbr_disp[16];  /* House nbr in Disp form   */
                 char hse_nbr_hns[11];   /* House nbr in Sort form   */
                 char lohse_nbr_disp[16];/* Lo House nbr in Disp form*/
                 char lohse_nbr_hns[11]; /* Lo House nbr in Sort form*/
                 STREET sti[3];          /* Street Information       */
                 BBL bbli;               /* Borough-Block-Lot        */
                 char filler01;          /* Filler-Tax Lot Version # */
                 char bld_id[7];         /* Building Id Number (BIN) */
                 char comp_direction;    /* Compass Direction        */
                 char comp_direction2;   /* Compass Direction-Fn 3S  */
                 char node[7];           /* Node input for Fn2       */
                 char platform_ind;      /* Must be equal to 'C'     */
                 char zipin[5];          /* Input Zip Code           */
                 char unit[UNIT_SIZE];   /* Input unit          V16.4*/
                 char filler03[82];      /* Future Use               */

                        /* Flags that influence processing */

                 char long_WA_flag;      /* Long Work Area 2 Flag     */
                                         /* Next 2 fields not impl    */
                 char hse_nbr_justify;   /* Hse Nbr Justification Flg */
                 char hnl[2];            /* Hse Nbr Normalization len */
                 char hse_nbr_over_flag; /* Reserved for GSS Use      */
                 char snl[2];            /* Street Name Norm Length   */
                 char st_name_norm;      /* Street Name Normalization */
                                         /*   Format Flag             */
                 char expanded_format;   /* Expanded Format Flag      */
                 char roadbedrequest;    /* Roadbed Request Switch    */
                 char res_01;            /* Reserved for Internal Use */
                 char segaux_switch;     /* Request Auxiliary Segment */
                                         /* Information               */
                 char browse_flag;       /* Determines if browse      */
                                         /* displays all or some names*/
                 char real_street_only;  /* Display real streets only */
                 char tpad_switch;       /* TPAD read for PAD process */
                 char mode_switch;       /* Mode Flag                 */
                                         /* X = Extended WA2          */
                 char wto_switch;        /* WTOs Switch N = No WTOs  */
                                         /*   should be issued       */
                 char filler04[29];      /* Future Use                */
                } INWA1;

typedef struct {
                 char boro_name[9];      /* Boro Name of First Street*/
                 char hse_nbr_disp[16];  /* House nbr in Normalized  */
                                         /* Display form             */
                 char hse_nbr_hns[11];   /* House number in Sort Form*/
                 STREET sto[3];          /* Street Information       */
                 BBL bblo;               /* Boro(len=1), Block(len=5)*/
                                         /* and Lot (len=4)-Normalizd*/
                 char filler05;          /* Filler-Tax Lot Version # */
                 char lo_hse_nbr_disp[16]; /* low Hse nbr - display  */
                 char lo_hse_nbr_hns[11]; /* low Hse nbr - sort form */
                 char bin[7];            /* Building Id Number       */
                 char attrbytes[3];      /* NAP Identification Number*/
                 char reason_code_2;     /* 2nd Reason Code          */
                 char reason_code_qual_2;/* 2nd Reason Code Qualifier*/
             //  char filler08_2;        /* Future Use               */
                 char warn_code_2[2];    /* 2nd Warning Return Code  */
                 char ret_code_2[2];     /* 2nd GeoSupport Return Cod*/
                 char msg_2[80];         /* 2nd GeoSupport Message   */
                 char node[7];           /* Node output for Fn 2     */
                 UNIT units;             /* Output unit Sort    V16.4*/
                 char unitd[UNIT_SIZE];  /* Output unit Display V16.4*/
                 char filler07[11];      /* Future Use               */
                 char nap_id_nbr[6];     /* NAP Id Nbr - Not Impl.   */
                 char int_use1;          /* Internal Use Only        */
                 char reason_code;       /* Reason Code              */
                 char reason_code_qual;  /* Reason Code Qualifier    */
             //  char filler08;          /* Future Use               */
                 char warn_code[2];      /* Warning Ret. Code-NotImpl*/
                 char ret_code[2];       /* GeoSupport Return Code   */
                 char msg[80];           /* GeoSupport Message       */
                 char nbr_names[2];      /* Nbr of Sreet Names       */
                 char B_7SC[10][8];      /* 10 Boro+7-digit st codes */
                 char st_names[10][32];  /* Up to 10 Street Names    */
               } OUTWA1;

 /*********************************************************************/
 /*                                                                   */
 /*                 Platform-Independent Work Area 1                  */
 /*                                                                   */
 /*********************************************************************/

typedef struct { INWA1 input;
                 OUTWA1 output;
               } C_WA1;

 /******************************************************************/
 /*                                                                */
 /*     Group Items Used in Platform-Independent Work Area 2's     */
 /*                                                                */
 /******************************************************************/

typedef struct {                         /* LION KEY                 */
                 char lion_boro;         /* LION Borough Code        */
                                         /* Differs from GeoSupport  */
                                         /* Borough Codes            */
                 char face[4];           /* Face Code                */
                 char seq[5];            /* Sequence Number          */
               } LION;

typedef struct {
                 char nbr_sts;           /* Number of streets */
                 char B5SC[5][6];        /* Boro+5 Street Code*/
               } St_list;

typedef struct {
  char  boro;
  char  st_code[7];
} B7SC, *PB7SC;

typedef struct {
  char nbr_sts;                          /* Number of streets */
  B7SC b7sc[5];
} St_listb7, *PSt_listb7;

typedef struct {                    /* used for longwa for TPAD */
  char bin[7];                             /* BIN               */
  char status;                             /* Status of BIN     */
} TPAD_LIST;

typedef struct {
  TPAD_LIST   tpadlist[2187];            /* or BINs + Status Byte  */
  char        fill[4];
} TPADLST;

typedef struct { char lo_hse_nbr[16];    /* Low House Nbr-Disply form*/
                 char hi_hse_nbr[16];    /* Hi House Nbr-Display form*/
                 char B5SC[6];           /* Boro & 5 digit Str Code  */
                 char lgc[2];            /* DCP Preferred Street LGC */
                 char bld_id[7];         /* BIN of address range     */
                 char sos_ind;           /* Side of Street Indicator */
                 char adr_type;          /* Address type - P=NAP,    */
                                         /*    B=NAB, Blank=Normal   */
                 char TPAD_bin_status;   /* Status of Job            */
                 char filler01[3];       /* Future Use               */
               } ADDR_RANGE;

typedef struct {
  char lo_hse_nbr[16];                   /* Low House Nbr-Disply form*/
  char hi_hse_nbr[16];                   /* Hi House Nbr-Display form*/
  char B5SC[6];                          /* Boro & 5 digit Str Code  */
  char lgc[2];                           /* DCP Preferred Street LGC */
  char bld_id[7];                        /* BIN of address range     */
  char sos_ind;                          /* Side of Street Indicator */
  char adr_type;                         /* Address type             */
                                         /* (Blank = Normal)         */
  char TPAD_bin_status;                  /* Status of BIN from TPAD  */
  char st_name[32];                      /* Street Name              */
  char filler01[34];                     /* Future Use               */
} ADDR_RANGE_1AX;

typedef struct { char lo_hse_nbr[16];    /* Low House Nbr-Disply form*/
                 char hi_hse_nbr[16];    /* Hi House Nbr-Display form*/
                 char B5SC[6];           /* Boro & 5 digit Str Code  */
                 char lgc[2];            /* DCP Preferred Street LGC */
                 char bld_id[7];         /* BIN of address range     */
                 char sos_ind;           /* Side of Street Indicator */
                 char adr_type;          /* Address type - V=VANITY  */
                                         /*   Blank=Normal           */
                 char filler02;          /* filler for func AP       */
                 char filler01[3];       /* Future Use               */
               } ADDR_RANGE_AP;

typedef struct { char lo_hse_nbr[16];    /* Low House Nbr-Disply form*/
                 char hi_hse_nbr[16];    /* Hi House Nbr-Display form*/
                 char B5SC[6];           /* Boro & 5 digit Str Code  */
                 char lgc[2];            /* DCP Preferred Street LGC */
                 char bld_id[7];         /* BIN of address range     */
                 char sos_ind;           /* Side of Street Indicator */
                 char adr_type;          /* Address type - V=VANITY  */
                                         /*    Blank=Normal          */
                 char filler02;          /* filler for func APX      */
                 char st_name[32];       /* Street Name              */
                 char filler01[34];      /* Future Use               */
               } ADDR_RANGE_APX;

typedef struct { char sanborn_boro;      /* Sanborn Borough Code     */
                 char sanborn_vol[3];    /* Sanborn Volume           */
                 char sanborn_page[4];   /* Sanborn Page             */
               } SANBORN;


typedef struct {    // for b7sc grid3 v19.3
  char com_dist[3];                      /* Community District       */
  char lo_hse_nbr[16];                   /* Low House Nbr-Disply form*/
  char hi_hse_nbr[16];                   /* Hi House Nbr-Display form*/
  char a_lo_hse_nbr[16];                 /* Alt. Lo Hse 4 3c fill 4 3*/
  char a_hi_hse_nbr[16];                 /* Alt. Hi Hse 4 3c fill 4 3*/
  char iaei;                             /* Interim Ass'tance Elig   */
                                         /* Indicator-CD Eligible    */
  char zip_code[5];                      /* Zip code for Street seg. */
  char health_area[4];                   /* Health Area              */
  char police_boro_com;                  /* Police Patrol Boro Commnd*/
  char police_pre[3];                    /* Police Precinct          */
  char fire_sector[2];                   /* Fire Division            */
  char fire_bat[2];                      /* Fire Battalion           */
  char fire_co_type;                     /* Fire Company Type        */
  char fire_co_nbr[3];                   /* Fire Company Number      */
  char com_schl_dist[2];                 /* Community School District*/
  char dynam_blk[3];                     /* Atomic Polygon           */
  char ED[3];                            /* ED                       */
  char AD[2];                            /* AD                       */
  char police_pat_boro[2];               /* Police Patrol Borough    */
  char boro;                             /* Borough Code             */
  char cen_tract_90[6];                  /* 1990 Census Tract        */
  char cen_tract_10[6];                  /* 2010 Census Tract        */
  char cen_blk_10[4];                    /* 2010 Census Block        */
  char cen_blk_10_sufx;                  /* 2010 Census Block Suffix */
  char cen_tract_2000[6];                /* 2000 Census Tract        */
  char cen_blk_2000[4];                  /* 2000 Census Block        */
  char cen_blk_2000_sufx;                /* 2000 Census Block Suffix */
  char nta[4];                           /* Neighborhood Tabulation  */
                                         /* Area                     */
  char nta_name[75];                     /* NTA name                 */
  char puma_code[5];                     /* PUMA code from NTA       */
  char blockface_id[10];                 /* blockface id             */
  char hcd[2];                           /* Health Center District   */
  char police_sector[4];                 /* Police Sector            */
  char cen_tract_20[6];                  /* 2020 Census Tract   V21.3*/
  char cen_blk_20[4];                    /* 2020 Census Block   V21.3*/
  char cen_blk_20_sufx;                  /* 2020 Census Blk Sfx V21.3*/
  char nta_20[6];                        /* 2020 NTA code       V21.3*/
  char cdta_20[4];                       /* 2020 CDTA           V21.3*/
  char filler04[3];                      /* Future Use          v21.3*/
//char filler04[24];                     /* Future Use               */
} SEGSIDEB7, *PSEGSIDEB7;

typedef struct { char com_dist[3];       /* Community District       */
                 char lo_hse_nbr[16];    /* Low House Nbr-Disply form*/
                 char hi_hse_nbr[16];    /* Hi House Nbr-Display form*/
                 char filler01[32];      /* Future Use               */
                 char iaei;              /* Interim Ass'tance Elig   */
                                         /* Indicator                */
                 char zip_code[5];       /* Zip code for Street seg. */
                 char health_area[4];    /* Health Area        */
                 char police_boro_com;   /* Police Patrl Boro Command*/
                 char police_pre[3];     /* Police Precinct          */
                 char fire_divisn[2];    /* Fire Division            */
                 char fire_bat[2];       /* Fire Battalion           */
                 char fire_co_type;      /* Fire Company Type        */
                 char fire_co_nbr[3];    /* Fire Company Number      */
                 char com_schl_dist[2];  /* Community School District*/
                 char dynam_blk[3];      /* Atomic Polygon           */
                                         /* (was Dynamic Block)      */
                 char ED[3];             /* ED                       */
                 char AD[2];             /* AD                       */
                 char police_pat_boro[2];/* Police Patrol Borough    */
           //    char instruc_div[2];    /* Instructional Division   */
                 char filler02;          /* Future Use               */
                 char boro;              /* Used for the NTA name    */
                 char cen_tract_90[6];   /* 1990 Census Tract        */
                 char cen_tract_10[6];   /* 2010 Census Tract        */
                 char cen_blk_10[4];     /* 2010 Census Block        */
                 char cen_blk_10_sufx;   /* 2010 Census Block Suffix */
                                         /* 2010 Suffix Not Implement*/
                 char cen_tract_2000[6]; /* 2000 Census Tract        */
                 char cen_blk_2000[4];   /* 2000 Census Block        */
                 char cen_blk_2000_sufx; /* 2000 Census Block Suffix */
            //   char blockface_id[7];   /* "Blockface ID" became    */
                 char filler03[7];       /* filler   V16.1           */
                 char nta[4];            /* Neighborhood Tabulation  */
                                         /* Area                     */
                 char filler04[8];       /* Future Use               */
               } SEGSIDE;

typedef struct { char mh_ri_flag;        /* Marble Hill/Rikers Island*/
                                         /* Alternative Boro flag    */
                 char len[5];            /* Len in ft from prev node */
                 char gap_flag;          /* Gap Flag                 */
                 char node_nbr[7];       /* Node Number of Intersect */
                 char nbr_streets;       /* Nbr streets intersecting */
                 char B7SC[5][8];        /* Lowest B7SC at Intersect */
                                         /* is first and 2nd Lowest  */
                                         /* B7SC is next. Remaining  */
                                         /* B7SC's in no particular  */
                                         /* order.                   */
               } CROSS_STRS;

 /********************************************************************/
 /*                                                                  */
 /*        Platform-Independent Work Area 2 for Function 1           */
 /*                                                                  */
 /********************************************************************/

typedef struct { char filler01[21];
                 char cont_parity_ind;   /* Continuous Parity Ind.   */
                                         /* or Duplicate Address Ind.*/
                 char lo_hse_nbr[11];    /* Lo House nbr in Sort form*/
                 char hi_hse_nbr[11];    /* Hi House Nbr in Sort form*/
                 char lgc[2];            /* DCP or BOE Preferred LGC */
                 St_list st[2];          /* 1=Low and 2=High         */
                                         /* Nbr of cross streets at  */
                                         /* low house nbr end of st  */
                                         /* B5SCs of lo end cross st */
                 LION key;               /* LION Key - 10 Characters */
                 char sagr_flag;         /* Special Address Generated*/
                                         /* Record flag              */
                 char sos_ind;           /* Side of Street Indicator */
                 char seg_len[5];        /* Segment Length in Feet   */
                 char coord[3][7];       /* 1 = X coordinate,        */
                                         /* 2 = Y coordinate,        */
                                         /* 3 = Z coordinate, Not Imp*/
                 char iaei;              /* Interim Ass'tance Elig   */
                                         /* Indicator                */
                 char mh_ri_flag;        /* Marble Hill/Rikers Island*/
                                         /* Alternative Borough flag */
                 char DOT_slca;          /* DOT St Lght Contractr Are*/
                 char com_dist[3];       /* Community District       */
                                         /* Position 0 contains the  */
                                         /* CD Boro Code & Pos 1 & 2,*/
                                         /* the district number      */
                 char zip_code[5];       /* Zip code for st seg      */

           /* Following seven fields used for Function 1E only*/

                 char ed[3];             /* Election District        */
                 char ad[2];             /* Assembly District        */
                 char sped_flag;         /* Split Elect District Flag*/
                 char congress_dist[2];  /* Congressional District   */
                 char state_sen_dist[2]; /* State Senatorial District*/
                 char civil_court[2];    /* Civil Court District     */
                 char city_council[2];   /* City Council District    */
                 char health_cent[2];    /* Health Center Dictr*/
                 char health_area[4];    /* Health Area        */
                 char sanit_dist[3];     /* Sanitation District      */
                 char sanit_sub_sect[2]; /* Sanit Collect Scheduling */
                                         /* Section and Subsection   */
                 char sanit_reg_pick_up[5]; /* Regular Pick up       */
                 char sanit_recycle[3];  /* Recycle pick up          */
                 char police_boro_com;   /* Police Patrol Boro Commnd*/
                 char police_pre[3];     /* Police Precinct          */
                 char fire_divisn[2];    /* Fire Division            */
                 char fire_bat[2];       /* Fire Battalion           */
                 char fire_co_type;      /* Fire Company Type        */
                 char fire_co_nbr[3];    /* Fire Company Number      */
                 char filler_scsd;       /* Was Split Com School     */
                                         /* District Flag            */
                 char com_schl_dist[2];  /* Community School District*/
                 char dynam_blk[3];      /* Atomic Polygon           */
                                         /* (was Dynamic Block)      */
                 char police_pat_boro[2];/* Police Patrol Borough    */
         //      char filler_indv[2];    /*                          */
         //      char instruc_div[2];    /* Instructional Division   */
                 char feature_type;      /* Feature Type Code        */
                 char segmenttypecode;   /* Segment Type Code        */
                 char alx;               /* Segment split by Alley(s)*/
                                         /* A=Split by Alley(s)      */
                                         /* X=Cross Streets Modified */
                 char coincident_seg_cnt; /* Coincident Segment      */
                                         /*    Counter               */
                 char filler02[2];       /* Future Use               */
                 char boro_of_cen_tract; /* boro of Census Tract used*/
                 char cen_tract_90[6];   /* 1990 Census Tract        */
                 char cen_tract_10[6];   /* 2010 Census Tract        */
                 char cen_blk_10[4];     /* 2010 Census Block        */
                 char cen_blk_10_sufx;   /* 2010 Census Block Suffix */
                                         /* 2010 Suffix Not Implement*/
                 char cen_tract_2000[6]; /* 2000 Census Tract        */
                 char cen_blk_2000[4];   /* 2000 Census Block        */
                 char cen_blk_2000_sufx; /* 2000 Census Block Suffix */
                 char nta[4];            /* Neighborhood Tabulation  */
                                         /* Area                     */
                 char sanit_snow_priority;/* Sanitation Street Snow  */
                                         /* Priority (P,S,T,V)       */
                 char sanit_org_pick_up[5];/* Organics Pick up       */
                 char sanit_bulk_pick_up[5]; /* Bulk Pick Up V16.4   */
               //char sanit_reserved[5]; /* Reserved for Possible    */
                 char hurricane_zone[2]; /* Hurricane Evacuation Zone*/
                 char filler04[11];      /* Future Use               */
                 char true_hns[11];      /* Underlying HNS           */
                 char true_b7sc[8];      /* True Boro 7 Street Code  */
                 char seg_id[7];         /* Segment Identifier       */
                 char curv_flag;         /* Curve Flag               */
               } C_WA2_F1;

 /********************************************************************/
 /*                                                                  */
 /*      Platform-Independent Work Area 2 for Function 1V/1W         */
 /*                                                                  */
 /********************************************************************/

typedef struct {
  C_WA2_F1 c_wa2_f1;
  char int_use[8];                       /* valid on street lgcs     */
  char boe_lgc;                          /* BOE LGC Pointer          */
  char seg_azm[3];                       /* Segment Azimuth          */
  char seg_orient;                       /* Segment Orientation      */
  char seg_coord[2][3][7];               /* Spatial Coordinates of   */
                                         /* Segment                  */
  char cc_coord[3][7];                   /* Spatial Coordinates of   */
                                         /* Center of Curvature      */
  char radius[7];                        /* Radius of Circle         */
  char cc_sos;                           /* Center of Curvature Side */
                                         /* of Street Flag           */
  char node_angles[2][5];                /* Angle to FROM & TO Nodes */
  char nodes[2][7];                      /* LION Node Numbers of     */
                                         /* FROM and TO nodes        */
  LION LION_key;                         /* LION Key for Vanity      */
                                         /* Addresses                */
  char LION_sos_ind;                     /* LION SoS Indicator       */
  char split_low_hn[11];                 /* Split Low House Number   */
  char traffic_dir;                      /* Traffic Direction        */
  char turn_restricts[10];               /* Turn restrictions        */
  char curve_fraction[3];                /*                          */
  char roadway_type[2];                  /* Roadway Type             */
  char physical_id[7];                   /*                          */
  char generic_id[7];                    /*                          */
  char filler03[7];                      /* DCP internal use         */
  char filler04[7];                      /* DCP internal use         */
//char blockface_id[7];      /** V16.1 **  blockface id 10 bytes long*/
  char bike_lane_2[2];                   /*Bike Lane has 2 bytes     */
                                         /* numeric value moved in   */
                                         /* wa2 of F1EX              */
  char bike_traffic_dir[2];              //V17.1 Bike Traffic Direction
  char filler05[3];                      // V17.1
  char status;                           /*                          */
  char str_width[3];                     /*                          */
  char str_width_irregular;              /* Yes or No                */
  char bike_lane;                        /*                          */
  char fcc[2];                           /* Federal Classification Cd*/
  char row_type;                         /*                          */
  char lgcs_additional[5][2];            /* additional lgcs for on st*/
} C_WA2_F1V;

 /********************************************************************/
 /*                                                                  */
 /*   Platform-Independent Work Area 2 for Function 1E Extended      */
 /*                                                                  */
 /********************************************************************/
typedef struct {                         /* Fn 1E with extra bytes   */
  C_WA2_F1V cwa2f1v;
  char legacy_segid[7];                  /*                          */
  char from_preferred_lgcs[5][2];        /*                          */
  char to_preferred_lgcs[5][2];          /*                          */
  char from_additional_lgcs[5][2];       /*                          */
  char to_additional_lgcs[5][2];         /*                          */
  char no_x_st_calc_flg;                 /* No Cross Street          */
                                         /* Calculation Flag         */
  char indiv_seg_len[5];                 /* Individual Segment Length*/
                                         /* Used with Above Flag     */
  char nta_name[75];                     /* Neighborhood Tabulation  */
                                         /* Area Name                */
  char USPS_city_name[25];               /* USPS Preferred City Name */
  char latitude[9];                      /* Latitude calc from X-Y   */
  char longitude[11];                    /* Longitude calc from X-Y  */
  char seg_from_node[7];                 /* Segment from node        */
  char seg_to_node[7];                   /* Segment to node          */
  char seg_from_xyz[3][7];               /* XYZ coord (segment from) */
  char seg_to_xyz[3][7];                 /* XYZ coord (segment to)   */
  char blockface_id[10];                 /* NEW location V16.1       */
                                         /* because of length changed*/
  char nbr_travel_lanes[2];              /* nbr of traveling lanes   */
  char nbr_park_lanes[2];                /* nbr of parking lanes     */
  char nbr_total_lanes[2];               /* total nbr of lanes       */
  char str_width_max[3];                 /*street width maximum      */
  char speed_limit[2];                   /* Speed Limit              */
  char puma_code[5];                     /* PUMA Code           V18.1*/
  char police_sector[4];                 /* Police Sector       V19.2*/
  char police_service_area;              /* Police Service Area V19.2*/
  char truck_route_type;                 /* Truck Route         V19.3*/
  char cen_tract_20[6];                  /* 2020 Census Tract   V21.3*/
  char cen_blk_20[4];                    /* 2020 Census Block   V21.3*/
  char cen_blk_20_sufx;                  /* 2020 Census Blk Sfx V21.3*/
  char nta_20[6];                        /* 2020 NTA code       V21.3*/
  char cdta_20[4];                       /* 2020 CDTA           V21.3*/
  char filler6[218];                     /* Future Use          V21.3*/
//char filler6[239];                     /* Future Use          V21.3*/
//char filler6[240];                     /*                     V18.3*/
//char filler6[245];                     /*                     V18.1*/
//char filler6[252];                     /* Future Use               */
  char reason_code;                      /* Reason Code              */
  char reason_code_qual;                 /* Reason Code Qualifier    */
  char warn_code[2];                     /* Warning Return Code      */
  char ret_code[2];                      /* GeoSupport Return Code   */
  char nbr_names_lo;                     /* Nbr of St Names Low End  */
  char B7SC_lo[5][8];                    /* 5(Boro+7-digit) st codes */
  char nbr_names_hi;                     /* Nbr of St Names High End */
  char B7SC_hi[5][8];                    /* 5 Boro+7-digit st codes  */
  char st_names_lo[5][32];               /* Up to 5 St Names Low End */
  char st_names_hi[5][32];               /* Up to 5 St Names High End*/
  char BOE_B5SC[6];                      /* BOE Preffered B7SC       */
  char BOE_lgc[2];                       /* BOE Preffered B7SC       */
  char BOE_st_name[32];                  /* BOE Preffered Street Name*/
  char filler7[52];                      /* Future Use               */
} C_WA2_F1EX;                            /* Fn 1EX with filler       */

 /********************************************************************/
 /*                                                                  */
 /*   Platform-Independent Work Area 2 for Function 1A Extended      */
 /*                                                                  */
 /********************************************************************/
typedef struct {                         /* Fn 1A with extra bytes   */
  char filler01[21];
  char cont_parity_ind;                  /* Continuous Parity Ind    */
                                         /* or Duplicate Address Ind */
  char lo_hse_nbr[11];                   /* Low House Number-Sort Frm*/

  BBL  bbl;                              /* Borough-Block-Lot        */
  char filler02;                         /* Reserved for Tax Lot Ver#*/
  char RPAD_scc;                         /* RPAD Self_Check Code(SCC)*/
  char filler03;
  char RPAD_lucc[2];                     /* RPAD Land Use Class. Code*/
  char corner[2];                        /* Corner Code              */
  char nbr_blds[4];                      /* Nbr of buildings on lot  */
  char nbr_str[2];                       /* Nbr Street Frontages     */
  char inter_flag;                       /* Interior Lot Flag        */
  char vacant_flag;                      /* Vacant Lot Flag          */
  char irreg_flag;                       /* Irregularly-Shaped Lot Fl*/
  char mh_ri_flag;                       /* Marble Hill/Rikers Island*/
  char adr_range_overflow;               /* Addr Rnge Lst Ovrflow Flg*/
  char stroll_key[18];                   /* Strolling key Not Implem */
  char filler04;
  char res_internal_use;                 /* Reserved for Internal Use*/
  char bld_id[7];                        /* Building Ident. Number   */
                                         /* (BIN) of Input Address of*/
                                         /* Existing Building, If any*/
  char condo_flag;                       /* Condominium Flag         */
  char filler05;                         /* Future Use               */
  char condo_id[4];                      /* RPAD Condo Id Number     */
  char condo_unit_id[7];                 /* Condo Unit Id Nbr-Not Imp*/
  BBL  condo_bill_bbl;                   /* Condo Billing BBL        */
  char filler06;                         /* Reserved for Tax Lot Ver */
  char condo_scc;                        /* Self-Check Code          */
  BBL  condo_lo_bbl;                     /* Low BBL of Condo         */
  char filler07;                         /* Reserved for Tax Lot Ver */
  BBL  condo_hi_bbl;                     /* High BBL of Condo        */
  char filler08;                         /* Reserved for Tax Lot Ver */
  BBL  condo_base_bbl;                   /* Condo_Base_BBL 21.4      */
  char filler09[5];
  char co_op_nbr[4];                     /* Co-op Number             */
  SANBORN San;                           /* Sanborn Information      */
  char business_area[5];                 /* Business Area            */
  char tax_map_nbr[5];                   /* Tax Map Nbr-Sect and Vol */
  char filler10[4];                      /* Tax Map Nbr Page Not Impl*/
  char filler11[3];
  char latitude[9];                      /* Latitude calc from X-Y   */
  char longitude[11];                    /* Longitude calc from X-Y  */
  char coord[2][7];                      /* 1 = X coordinate-Annotat */
                                         /* 2 = Y coordinate-Annotat */
  char bid_id[6];                        /* Business Improvement     */
                                         /* District ID (BID)        */

  char TPAD_bin_status;                  /* Status of Demolition job */
                                         /* on Existing BIN of Input */
                                         /* Address                  */
  char TPAD_new_bin[7];                  /* BIN for New Building     */
  char TPAD_new_bin_status;              /* Status of New Buildng BIN*/
  char TPAD_conflict_flag;               /* From TPAD                */
  char dcp_zone_map[3];                  /* DCP Zoning Map           */
  char filler12[6];

  char int_use[8];                       /* Internal Use             */
  char reason_code;                      /* Reason Code              */
  char reason_code_qual;                 /* Reason Code Qualifier    */
  char warn_code[2];                     /* Warning Return Code      */
  char ret_code[2];                      /* GeoSupport Return Code   */
  char filler14[108];
  char nbr_addr[4];                      /* Nbr of Addr Ranges or Nbr*/
                                         /* of BINs in List          */
  ADDR_RANGE_1AX addr_range_1ax[21];
} C_WA2_F1AX;                            /* Fn 1AX with filler       */
 /********************************************************************/
 /*                                                                  */
 /*      Platform-Independent Work Area 2 for Function 1B            */
 /*                                                                  */
 /********************************************************************/

typedef struct {                         /* Function 1B              */
  C_WA2_F1EX cwa2f1ex;                   /* 1EX Component            */
  C_WA2_F1AX cwa2f1ax;                   /* 1AX Component            */
} C_WA2_F1B;                             /* Fn 1B                    */

 /********************************************************************/
 /*                                                                  */
 /*      Platform-Independent Work Area 2 for Function 1A            */
 /*                                                                  */
 /********************************************************************/

typedef struct {
   char filler01[21];
   char cont_parity_ind;   /* Continuous Parity Ind    */
                           /* or Duplicate Address Ind */
   char lo_hse_nbr[11];    /* Low House Number-Sort Frm*/

   BBL bbl;                /* Borough-Block-Lot        */
   char filler02;          /* Reserved for Tax Lot Ver#*/
   char RPAD_scc;          /* RPAD Self_Check Code(SCC)*/
   char filler03;
   char RPAD_lucc[2];      /* RPAD Land Use Class. Code*/
   char corner[2];         /* Corner Code              */
   char nbr_blds[4];       /* Nbr of buildings on lot  */
   char nbr_str[2];        /* Nbr Street Frontages     */
   char inter_flag;        /* Interior Lot Flag        */
   char vacant_flag;       /* Vacant Lot Flag          */
   char irreg_flag;        /* Irregularly-Shaped Lot Fl*/
   char mh_ri_flag;        /* Marble Hill/Rikers Island*/
   char adr_range_overflow;/* Addr Rnge Lst Ovrflow Flg*/
   char stroll_key[18];    /* Strolling key            */
   char filler04;
   char res_internal_use;  /* Reserved for Internal Use*/
   char bld_id[7];         /* Building Ident. Number   */
                           /* (BIN) of Input Address of*/
                           /* Existing Building, If any*/
   char condo_flag;        /* Condominium Flag         */
   char filler05;          /* Future Use               */
   char condo_id[4];       /* RPAD Condo Id Number     */
   char condo_unit_id[7];  /* Condo Unit Id Nbr-Not Impl*/
   BBL  condo_bill_bbl;    /* Condo Billing BBL        */
   char filler06;          /* Reserved for Tax Lot Ver */
   char condo_scc;         /* Self-Check Code          */
   BBL  condo_lo_bbl;      /* Low BBL of Condo         */
   char filler07;          /* Reserved for Tax Lot Ver */
   BBL  condo_hi_bbl;      /* High BBL of Condo        */
   char filler08;          /* Reserved for Tax Lot Ver */
   BBL  condo_base_bbl;    /* Condo_Base_BBL 21.4      */
   char filler09[5];
   char co_op_nbr[4];      /* Co-op Number             */
   SANBORN San;            /* Sanborn Information      */
   char business_area[5];  /* Business Area            */
   char tax_map_nbr[5];    /* Tax Map Nbr-Sect and Vol */
   char filler10[4];       /* Tax Map Nbr Page Not Impl*/
   char filler11[3];
   char latitude[9];       /* Latitude calc from X-Y   */
   char longitude[11];     /* Longitude calc from X-Y  */
   char coord[2][7];       /* 1 = X coordinate-Annotat */
                           /* 2 = Y coordinate-Annotat  */
   char bid_id[6];         /* Business Improvement Dist */
                           /* District ID (BID)         */
   char TPAD_bin_status;   /* Existing BIN of Input Addr*/
   char TPAD_new_bin[7];    /* BIN for New Building job */
   char TPAD_new_bin_status;/* Status of New Buildng BIN*/
   char TPAD_conflict_flag; /* From TPAD                */
   char dcp_zone_map[3];    /* DCP Zoning Map           */
   char filler12[6];
   char int_use[8];        /* Internal Use             */
   char nbr_addr[4];       /* Nbr of Addr Ranges or Nbr*/
                           /* of BINs in List          */
   union {
           ADDR_RANGE addr_range[21]; /* List of Addr  */
           TPADLST   tpad_list;       /*or BINs + Status Byte */
           char bin_list[2500][7];    /* or BINs*/
         } bar;
} C_WA2_F1A;
 /********************************************************************/
 /*                                                                  */
 /*      Platform-Independent Work Area 2 for Function AP            */
 /*                                                                  */
 /********************************************************************/

typedef struct {
   char filler01[21];
   char cont_parity_ind;   /* Continuous Parity Ind    */
                           /* or Duplicate Address Ind */
   char lo_hse_nbr[11];    /* Low House Number-Sort Frm*/

   BBL bbl;                /* Borough-Block-Lot        */
   char filler02;          /* Reserved for Tax Lot Ver#*/
   char fil_RPAD_scc;      /* filler for func AP       */
   char filler03;
   char fil_RPAD_lucc[2];  /* fillers for func AP      */
   char fil_corner[2];     /* fillers for func AP      */
   char nbr_blds[4];       /* Nbr of buildings on lot  */
   char fil_nbr_str[2];    /* fillers for func AP      */
   char fil_inter_flag;    /* filler for func AP       */
   char fil_vacant_flag;   /* filler for func AP       */
   char fil_irreg_flag;    /* filler for func AP       */
   char fil_mh_ri_flag;    /* filler for func AP       */
   char fil_adr_range_overflow;/* filler for func AP   */
   char fil_stroll_key[18];/* fillers for func AP      */
   char filler04;
   char res_internal_use;  /* Reserved for Internal Use*/
   char bld_id[7];         /* Building Ident. Number   */
                           /* (BIN) of Input Address of*/
                           /* Existing Building,       */
   char condo_flag;        /* Condominium Flag         */
   char filler05;          /* Future Use               */
   char condo_id[4];       /* RPAD Condo Id Number     */
   char filler_unit_id[7]; /* Condo Unit Id Nbr-Not Impl*/
   BBL  condo_bill_bbl;    /* Condo Billing BBL        */
   char filler06;          /* Reserved for Tax Lot Ver */
   char fil_condo_scc;     /* filler for func AP       */
   BBL  condo_lo_bbl;      /* Low BBL of Condo         */
   char filler07;          /* Reserved for Tax Lot Ver */
   BBL  condo_hi_bbl;      /* High BBL of Condo        */
   char filler08;          /* Reserved for Tax Lot Ver */
   char filler09[15];
   char co_op_nbr[4];      /* Co-op Number             */
   char fil_sanborn[8];    /* fillers for func AP      */
   char fil_business_area[5];  /* fillers for func AP  */
   char fil_tax_map_nbr[5];    /* fillers for func AP  */
   char filler10[4];
   char filler11[3];
   char latitude[9];       /* Latitude calc from X-Y   */
   char longitude[11];     /* Longitude calc from X-Y  */
   char coord[2][7];       /* 1 = X coordinate from AP */
                           /* 2 = Y coordinate from AP */
   char fil_bid_id[6];          /* fillers for func AP */
   char fil_TPAD_bin_status;    /* fillers for func AP */
   char fil_TPAD_new_bin[7];    /* fillers for func AP */
   char fil_TPAD_new_bin_status;/* filler for func AP  */
   char fil_TPAD_conflict_flag; /* filler for func AP  */
   char ap_id[9];               /* Address Point Id    */
   char int_use[8];             /* Internal Use        */
   char nbr_addr[4];            /* Nbr of Addr = 0001  */

   union {
           ADDR_RANGE_AP addr_range_ap[21]; /* List of Addr  */
           char fil_tpad_list[2191];
           char fil_bin_list[2500][7];
         } bar;
} C_WA2_FAP;

 /********************************************************************/
 /*                                                                  */
 /*      Platform-Independent Work Area 2 for Function APX           */
 /*                                                                  */
 /********************************************************************/

typedef struct {
   char filler01[21];      /* Fn AP with extra bytes   */
   char cont_parity_ind;   /* Continuous Parity Ind    */
                           /* or Duplicate Address Ind */
   char lo_hse_nbr[11];    /* Low House Number-Sort Frm*/

   BBL bbl;                /* Borough-Block-Lot        */
   char filler02;          /* Reserved for Tax Lot Ver#*/
   char fil_RPAD_scc;      /* filler for func AP       */
   char filler03;
   char fil_RPAD_lucc[2];  /* fillers for func AP      */
   char fil_corner[2];     /* fillers for func AP      */
   char nbr_blds[4];       /* Nbr of buildings on lot  */
   char fil_nbr_str[2];    /* fillers for func AP      */
   char fil_inter_flag;    /* filler for func AP       */
   char fil_vacant_flag;   /* filler for func AP       */
   char fil_irreg_flag;    /* filler for func AP       */
   char fil_mh_ri_flag;    /* filler for func AP       */
   char fil_adr_range_overflow;/* filler for func AP   */
   char fil_stroll_key[18];/* fillers for func AP      */
   char filler04;
   char res_internal_use;  /* Reserved for Internal Use*/
   char bld_id[7];         /* Building Ident. Number   */
                           /* (BIN) of Input Address of*/
                           /* Existing Building,       */
   char condo_flag;        /* Condominium Flag         */
   char filler05;          /* Future Use               */
   char condo_id[4];       /* RPAD Condo Id Number     */
   char filler_unit_id[7]; /* Condo Unit Id Nbr-Not Impl*/
   BBL  condo_bill_bbl;    /* Condo Billing BBL        */
   char filler06;          /* Reserved for Tax Lot Ver */
   char fil_condo_scc;     /* filler for func AP       */
   BBL  condo_lo_bbl;      /* Low BBL of Condo         */
   char filler07;          /* Reserved for Tax Lot Ver */
   BBL  condo_hi_bbl;      /* High BBL of Condo        */
   char filler08;          /* Reserved for Tax Lot Ver */
   char filler09[15];
   char co_op_nbr[4];      /* Co-op Number             */
   char fil_sanborn[8];    /* fillers for func AP      */
   char fil_business_area[5];  /* fillers for func AP  */
   char fil_tax_map_nbr[5];    /* fillers for func AP  */
   char filler10[4];
   char filler11[3];
   char latitude[9];       /* Latitude calc from X-Y   */
   char longitude[11];     /* Longitude calc from X-Y  */
   char coord[2][7];       /* 1 = X coordinate from AP */
                           /* 2 = Y coordinate from AP */
   char fil_bid_id[6];          /* fillers for func AP */
   char fil_TPAD_bin_status;    /* fillers for func AP */
   char fil_TPAD_new_bin[7];    /* fillers for func AP */
   char fil_TPAD_new_bin_status;/* filler for func AP  */
   char fil_TPAD_conflict_flag; /* filler for func AP  */
   char ap_id[9];               /* Address Point Id    */
   char int_use[8];             /* Internal Use        */
   char reason_code;            /* Reason Code              */
   char reason_code_qual;       /* Reason Code Qualifier    */
   char warn_code[2];           /* Warning Return Code      */
   char ret_code[2];            /* GeoSupport Return Code   */
   char filler14[108];
   char nbr_addr[4];            /* Nbr of Addr = 0001  */

   ADDR_RANGE_APX addr_range_apx[21];
} C_WA2_FAPX;

 /********************************************************************/
 /*                                                                  */
 /*       Platform-Independent Work Area 2 for Function 2            */
 /*                                                                  */
 /********************************************************************/

typedef struct { char filler01[21];
                 char rep_cnt;           /* Intersection Replication */
                                         /* Counter*/
                 char lgc[2][2];         /* Preferred LGCs           */
                 St_list inter;          /* Number of Intersecting St*/
                                         /* B5SCs of Intersection St */
                 char Dup_comp;          /* Duplicate compass Directn*/
                 char atomic_polygon[3]; /* Atomic Polygon added V131*/
                 char filler02[2];
                 char LION_node_nbr[7];  /* LION Node Number         */
                 char coord[3][7];       /* 1 = X coordinate,        */
                                         /* 2 = Y coordinate,        */
                                         /* 3 = Z coordinate, Not Imp*/
                 SANBORN San[2];         /* Sanborn Information      */
                 char mh_ri_flag;        /* Marble Hill/Rikers Island*/
                 char DOT_slca;          /* DOT St Lght Contractr Are*/
                 char com_dist[3];       /* Community District       */
                 char zip_code[5];       /* Zip code for st segment  */
                 char health_area[4];    /* Health Area        */
                 char police_boro_com;   /* Police Patrol Boro Commnd*/
                 char police_pre[3];     /* Police Precinct          */
                 char fire_sector[2];    /* Fire Sector              */
                 char fire_bat[2];       /* Fire Battalion           */
                 char fire_co_type;      /* Fire Company Type        */
                 char fire_co_nbr[3];    /* Fire Company Number      */
                 char com_schl_dist[2];  /* Community School District*/
                 char cen_tract_10[6];   /* 2010 Census Tract        */
                 char cen_tract_90[6];   /* 1990 Census Tract        */
                 char level_codes [10];  /* Level codes              */
                 char police_pat_boro[2];/* Police Patrol Borough    */
         //      char filler_indv[2];    /*                          */
         //      char instruc_div [2];   /* Instructional Division   */
                 char ad[2];             /* Assembly District        */
                 char congress_dist[2];  /* Congressional District   */
                 char state_sen_dist[2]; /* State Senatorial District*/
                 char civil_court[2];    /* Civil Court District     */
                 char city_council[2];   /* City Council District    */
                 char cd_eligible;       /* CD Eligibility           */
                 char dup_intersect_distance[5];  /*Distance in Feet */
                                         /*Betwn Duplicate Intersects*/
                                         /* not implemented */
                 char cen_tract_2000[6]; /* 2000 Census Tract        */
                 char health_cen_dist[2];/* Health Cent Distr*/
                 char sanit_dist[3];     /* Sanitation District      */
                 char sanit_sub_sect[2]; /* Sanit Collect Scheduling */
                                         /* Section and Subsection   */
                 char police_sector[4];  /* Police Sector V19.2      */
                 char cen_tract_20[6];   /* 2020 Census Tract        */
                 char filler03[2];       /* V21.3                    */
         //      char filler03[8];       /* V21.3                    */
         //      char filler03[12];      /*                          */
               } C_WA2_F2;

 /********************************************************************/
 /*                                                                  */
 /*   Platform-Independent Work Area 2 for Function 2W               */
 /********************************************************************/
typedef struct {                         /* Fn 2 - 200 Bytes         */
  C_WA2_F2 cwa2f2;                       /* Start with Fn 2 WA2      */
  char filler04[22];                     /* Fields used for Grid gen */
  char lgcs_first_intersct[4][2];        /* Up to 4 LGC's for 1st    */
                                         /* intersecting street;     */
  char lgcs_second_intersct[4][2];       /* Up to 4 LGC's for 2nd    */
                                         /* intersecting street;     */
  char turn_restricts[10];               /* Up to 10 Turn restrictns */
  char pref_lgc_list[5][2];              /* Preferd LGCs for Str list*/
  char true_rep_cnt[2];                  /* True Int Replication Cntr*/
  char dup_node_list[20][7];       /* 140 *Node list for dup str code*/
  char b7sc_list[20][5][4][8];     /* 3200 *B7SC lists for Node list */
  char reason_code;                      /* Reason Code              */
  char reason_code_qual;                 /* future use               */
  char warn_code[2];                     /* Warning Return Code      */
  char ret_code[2];                      /* GeoSupport Return Code   */
  char latitude[9];                      /* Latitude calc from X-Y   */
  char longitude[11];                    /* Longitude calc from X-Y  */
  char cen_tract_20[6];                  /* 2020 Census Tract   V21.3*/
  char cen_blk_20[4];                    /* 2020 Census Block   V21.3*/
  char cen_blk_20_sufx;                  /* 2020 Census Blk Sfx V21.3*/
  char nta_20[6];                        /* 2020 NTA code       V21.3*/
  char cdta_20[4];                       /* 2020 CDTA           V21.3*/
  char filler8[353];                     /* Future Use          v21.3*/
//char filler8[374];                     /* Future Use          v21.3*/
} C_WA2_F2W, *PC_WA2_F2W;                /* Fn 2W with filler        */
 /********************************************************************/
 /*                                                                  */
 /*        Platform-Independent Work Area 2 for Function 3           */
 /*                                                                  */
 /********************************************************************/

typedef struct { char filler01[21];
                 char dup_key_flag;      /* Duplicate Key Flag or    */
                                         /* Continuous Parity Flag   */
                 char loc_stat_seg;      /* Locational Status of Seg */
                 char cnty_bnd_ind;      /* County Boundary Indicat  */
                 char lgc[3][2];         /* Preferred LGCs           */
                 St_list st[2];          /* 1=Low and 2=High         */
                                         /* Nbr of cross sts at low  */
                                         /* house nbr end of street  */
                                         /* B5SCs of lo end X sts    */
                 char x_street_reversal_flag; /* X St Reversal Flag  */
                 LION key;               /* LION Key                 */
                 char genr_flag;         /* Generated Record Flag    */
                 char seg_len[5];        /* Segment Length in Feet   */
                 char seg_azm[3];        /* Segment Azimuth          */
                 char seg_orient;        /* Segment Orientation      */
                 char mh_ri_flag;        /* Marble Hill/Rikers Island*/
                                         /* Alternative Boro flag    */
                 char from_node[7];      /* From node                */
                 char to_node[7];        /* To node                  */
                 char sanit_snow_priority;/* Sanitation Street Snow  */
                                         /* Priority (P,S,T,V)       */
                 char filler02[4];       /* Future use               */
                 char seg_id[7];         /* Segment Identifier       */
                 char DOT_slca;          /* DOT St Lght Contractr Are*/
                 char curve_flag;        /* Curve Flag               */
                 char dog_leg;           /* Dog leg flag             */
                 char feature_type;      /* Feature Type Code        */
                 char segmenttypecode;   /* Segment Type Code        */
                 char coincident_seg_cnt; /* Coincident Segment      */
                                         /*    Counter               */
                 char filler03[4];
                 SEGSIDE side[2];        /* 1 = Left Side of street  */
                                         /* 2 = Right Side of street */
               } C_WA2_F3;

typedef struct { C_WA2_F3 cwa2f3;
                 char filler1[6];        /* Future use              */
                 char seg_cnt[4];        /* Number of Segments      */
                 char segments[70][7];   /* Segment Ids             */
               } C_WA2_F3_AUXSEG;
 /********************************************************************/
 /*                                                                  */
 /*      Platform-Independent Work Area 2 for Function 3 EXTENDED    */
 /*                                                                  */
 /********************************************************************/

typedef struct {                        /*  Data from CSCL added     */
  C_WA2_F3 cwa2f3;
  char lgc_list[4][2];                   /* List of LGC's            */
  char from_lgcs[4][2];                  /* List of from LGC's       */
  char to_lgcs[4][2];                    /* List of to LGC's         */
  char left_hcd[2];                      /* Left Health Center   */
                                         /* District                 */
  char right_hcd[2];                     /* Right Health Center  */
                                         /* District                 */
  char filler_csd;                       /*                          */
  char traffic_dir;                      /* Traffic Direction        */
  char roadway_type[2];                  /*                          */
  char physical_id[7];                   /*                          */
  char generic_id[7];                    /*                          */
  char filler03[7];                      /* DCP internal use         */
  char filler04[7];                      /* DCP internal use         */
  char street_status;                    /*                          */
  char str_width[3];                     /* Street Width             */
  char str_width_irr;                    /* Irregular Width Y or N   */
  char bike_lane;                        /*                          */
  char fcc[2];                           /* Federal Classification Cd*/
  char row_type;                         /*                          */
  char lgc5[2];                          /*                          */
  char lgc6[2];                          /*                          */
  char lgc7[2];                          /*                          */
  char lgc8[2];                          /*                          */
  char lgc9[2];                          /*                          */
  char legacy_id[7];                     /*                          */
  char nta_name_left[75];                /* Neighborhood Tabulation  */
                                         /* Area Name (Left)         */
  char nta_name_right[75];               /* Neighborhood Tabulation  */
                                         /* Area Name (Right)        */
  char from_coord[2][7];                 /* 1 = X Coordinate         */
                                         /* 2 = Y Coordinate         */
  char to_coord[2][7];                   /* 1 = X Coordinate         */
                                         /* 2 = Y Coordinate         */
  char from_latitude[9];                 /*Latitude of from intersct.*/
  char from_longitude[11];               /*Longitude of from intersct*/
  char to_latitude[9];                   /*Latitude of to intersect. */
  char to_longitude[11];                 /*Longitude of to intersect.*/
  char left_blockface_id[10];            //NEW location of blockface id
  char right_blockface_id[10];
  char nbr_travel_lanes[2];              /* nbr of traveling lanes   */
  char nbr_park_lanes[2];                /* nbr of parking lanes     */
  char nbr_total_lanes[2];               /* total nbr of lanes       */
  char bike_lane_2[2];                   /*Bike Lane has 2 bytes     */
                                         /* numeric value            */
  char str_width_max[3];                 /*Street width maximum      */
  char bike_traffic_dir[2];             //V17.1 Bike Traffic Direction
  char speed_limit[2];                   //V17.4 Speed Limit
  char left_puma_code[5];                // PUMA Code (left) V18.1
  char right_puma_code[5];               // PUMA Code (right) V18.1
  char left_police_sector[4];          // Police Sector(left) V19.2
  char right_police_sector[4];         // Police Sector(right)V19.2
  char truck_route_type;                 /* Truck Route         V19.3*/
  char left_cen_tract_20[6];             /* 2020 Census Tract   V21.3*/
  char left_cen_blk_20[4];               /* 2020 Census Block   V21.3*/
  char left_cen_blk_20_sufx;             /* 2020 Census Blk Sfx V21.3*/
  char left_nta_20[6];                   /* 2020 NTA code       V21.3*/
  char left_cdta_20[4];                  /* 2020 CDTA           V21.3*/
  char right_cen_tract_20[6];            /* 2020 Census Tract   V21.3*/
  char right_cen_blk_20[4];              /* 2020 Census Block   V21.3*/
  char right_cen_blk_20_sufx;            /* 2020 Census Blk Sfx V21.3*/
  char right_nta_20[6];                  /* 2020 NTA code       V21.3*/
  char right_cdta_20[4];                 /* 2020 CDTA           V21.3*/
  char filler05[150];                    /* V21.3                    */
//char filler05[192];                    /* V21.3                    */
//char filler05[193];                    // V18.3
//char filler05[201];                    // V18.1
//char filler05[211];                    // V17.4
//char filler05[213];                    // V17.1
} C_WA2_F3X;

typedef struct {                         /* Fn 3 Extended with       */
  C_WA2_F3X cwa2f3x;                     /* Auxilary Segments        */
  char filler1[6];                        /* Future use              */
  char seg_cnt[4];                        /* Number of Segments      */
  char segments[70][7];                   /* Segment Ids             */
} C_WA2_F3X_AUXSEG;                      /* Fn 3X with AUXSEGID      */


 /********************************************************************/
 /*                                                                  */
 /*      YNL introducing B7SC to Grid3  v19.3                        */
 /*        Platform-Independent Work Area 2 for Function 3E v19.3   */
 /*                                                                  */
 /********************************************************************/

typedef struct {                         /* Data from CSCL added     */
  char rectype[2];                       /* allways '03'             */
  char b5sc1[6];                         /* b5sc on-street           */
  char lgc1[2];                          /* lgc  on-street           */
  char b5sc2[6];                         /* 1st cross street         */
  char lgc2[2];                          /* lgc 1st Cross Street     */
  char b5sc3[6];                         /* 2nd cross street         */
  char lgc3[2];                          /* lgc 2nd Cross Street     */
  char dog_in_key;
  char filler01[20];                     /* VSAM key + key filler    */
  char b5sc_dup_flag;                    /* Dup Key Flag for b5sc    */
  char special_cond_flag;                /* Duplicate Key Flag or    */
                                         /* Continuous Parity Flag   */
  char loc_stat_seg;                     /* Locational Status of Seg */
  char cnty_bnd_ind;                     /* County Boundary Indicat  */
  char lgc[3][2];                        /* Preferred LGCs           */
  St_listb7 st[2];                       /* 1=Low and 2=High         */
                                         /* Nbr of cross sts at low  */
                                         /* house nbr end of street  */
                                         /* B5SCs of lo end X sts    */
  char x_street_reversal_flag;           /* Cross St Reversal Flag   */
  LION key;                              /* LION Key                 */
  char genr_flag;                        /* Generated Record Flag    */
  char seg_len[5];                       /* Segment Length in Feet   */
  char seg_azm[3];                       /* Segment Azimuth          */
  char seg_orient;                       /* Segment Orientation      */
  char mh_ri_flag;                       /* Marble Hill/Rikers Island*/
                                         /* Alternative Boro flag    */
  char nodes[2][7];                      /* From and To Node IDs     */
  char sanit_snow_priority;              /* Sanitation Street Snow   */
                                         /* Priority (P,S,T,V)       */
  char seg_id[7];                        /* Segment Identifier       */
  char DOT_slca;                         /* DOT St Lght Contractr Are*/
  char curve_flag;                       /* Curve Flag               */
  char dog_leg;                          /* Dog leg flag             */
  char feature_type;                     /* Feature Type Code        */
  char segtypecode;                      /* Segment Type Code        */
  char coincident_seg_cnt;               /* Coincident Segment       */
                                         /*    Counter               */
  char lgc_list[4][2];                   /* List of LGC's            */
  char from_lgcs[4][2];                  /* List of from LGC's       */
  char to_lgcs[4][2];                    /* List of to LGC's         */
  char traffic_dir;                      /* Traffic Direction        */
  char roadway_type[2];                  /*                          */
  char physical_id[7];                   /*                          */
  char generic_id[7];                    /*                          */
  char NYPD_id[7];                       /*                          */
  char FDNY_id[7];                       /*                          */
  char street_status;                    /*                          */
  char str_width[3];                     /* Street Width             */
  char str_width_irr;                    /* Irregular Width Y or N   */
  char bike_lane;                        /*                          */
  char fcc[2];                           /* Federal Classification Cd*/
  char row_type;                         /* Right of Way Type        */
  char lgc5[2];                          /*                          */
  char lgc6[2];                          /*                          */
  char lgc7[2];                          /*                          */
  char lgc8[2];                          /*                          */
  char lgc9[2];                          /*                          */
  char legacy_id[7];                     /*                          */
  char from_coord[2][7];                 /* 1 = X Coordinate         */
                                         /* 2 = Y Coordinate         */
  char to_coord[2][7];                   /* 1 = X Coordinate         */
                                         /* 2 = Y Coordinate         */
  char from_latitude[9];                 /*Latitude of from intersct.*/
  char from_longitude[11];               /*Longitude of from intersct*/
  char to_latitude[9];                   /*Latitude of to intersct.  */
  char to_longitude[11];                 /*Longitude of to intersct. */
  char nbr_travel_lanes[2];              /* nbr of traveling lanes   */
  char nbr_park_lanes[2];                /* nbr of parking lanes     */
  char nbr_total_lanes[2];               /* total nbr of lanes       */
  char bike_lane_2[2];                   /* Bike Lane Numerics V16.4 */
  char str_width_max[3];                 /* Str Width Maximum V16.4  */
  char bike_traffic_dir[2];             //V17.1 Bike Traffic Direction
  char speed_limit[2];                   /* Posted Speed Limit  V17.4*/
  char truck_route_type;                 /* Truck Route         V19.3*/
  char filler05[137];                    /* V19.3                    */
  char reserved_for_sos;                 /* V19.3                    */
  SEGSIDEB7 side[2];                     /* 1 = Left Side of street  */

} C_WA2_F3E, *PC_WA2_F3E;                /*                          */

typedef struct                           /* Auxilary Segments      */
  {                                      /* for Functions 3 and 3C */
  char seg_fill[6];                      /* # of Auxilary Segments */
  char seg_cnt[4];                       /* # of Auxilary Segments */
  char segs[70][7];                      /* Auxilary Segments      */
} AUXSEG, *PAUXSEG;

typedef struct {                         /* Fn 3 Wide with Auxiliary */
  C_WA2_F3E cwa2f3e;                     /* segments  v19.3          */
  AUXSEG auxseg;
} C_WA2_F3E_AUXSEG, *PC_WA2_F3E_AUXSEG;  /* Fn 3E with AUXSEGID      */

 /********************************************************************/
 /*                                                                  */
 /*        Platform-Independent Work Area 2 for Function 3C          */
 /*                                                                  */
 /********************************************************************/

typedef struct { char filler01[21];
                 char dup_key_flag;      /* Duplicate Key Flag or    */
                                         /* Continuous Parity Flag   */
                 char loc_stat_seg;      /* Locational Status of Seg */
                 char cnty_bnd_ind;      /* County Boundary Indicat  */
                 char lgc[3][2];         /* Preferred LGCs           */
                 St_list st[2];          /* 1=Low and 2=High         */
                                         /* Nbr of cross sts at low  */
                                         /* house nbr end of street  */
                                         /* B5SCs of lo end Cross sts*/
                 char x_street_reversal_flag; /* X St Reversal Flag  */
                 LION key;               /* LION key                 */
                 char genr_flag;         /* Generated Record Flag    */
                 char seg_len[5];        /* Segment Length in Feet   */
                 char seg_azm[3];        /* Segment Azimuth          */
                 char seg_orient;        /* Segment Orientation      */
                 char mh_ri_flag;        /* Marble Hill/Rikers Island*/
                                         /* Alternative Boro flag    */
                 char from_node[7];      /* From node                */
                 char to_node[7];        /* To Node                  */
                 char sanit_snow_priority;/* Sanitation Street Snow  */
                                          /* Priority (P,S,T,V)      */
                 char filler02[4];       /* Future use               */
                 char seg_id  [7];       /* Segment Identifier       */
                 char DOT_slca;          /* DOT St Lght Contractr Are*/
                 char sos_ind;           /* Side of Street Indicator */
                 char curve_flag;        /* Curve Flag               */
                 char feature_type;      /* Feature Type Code        */
                 char segmenttypecode;   /* Segment Type Code        */
                 char coincident_seg_cnt; /* Coincident Segment      */
                                          /*    Counter              */
                 char filler03[4];
                 SEGSIDE req;           /* Geographic Information for*/
              } C_WA2_F3C;

typedef struct { C_WA2_F3C cwa2f3c;
                 char filler1[6];        /* Future use              */
                 char seg_cnt[4];        /* Number of Segments      */
                 char segments[70][7];   /* Segment ids             */
               } C_WA2_F3C_AUXSEG;

 /********************************************************************/
 /*                                                                  */
 /*      Platform-Independent Work Area 2 for Function 3C EXTENDED   */
 /*                                                                  */
 /********************************************************************/
typedef struct {                         /* Data from CSCL added     */
  C_WA2_F3C cwa2f3c;
  char lgc_list[4][2];                   /* List of LGC's            */
  char from_lgcs[4][2];                  /* List of from LGC's       */
  char to_lgcs[4][2];                    /* List of to LGC's         */
  char left_hcd[2];                      /* Left Health Center Distr */
  char right_hcd[2];                     /* Right Health Center Distr*/
  char fill_csd;                         /* Filler                   */
  char traffic_dir;                      /* Traffic Direction        */
  char roadway_type[2];                  /*                          */
  char physical_id[7];                   /*                          */
  char generic_id[7];                    /*                          */
  char filler03[7];                      /* DCP internal use         */
  char filler04[7];                      /* DCP internal use         */
  char street_status;                    /*                          */
  char str_width[3];                     /* Street Width             */
  char str_width_irr;                    /* Irregular Width Y or N   */
  char bike_lane;                        /*                          */
  char fcc[2];                           /* Federal Classification Cd*/
  char row_type;                         /*                          */
  char lgc5[2];                          /*                          */
  char lgc6[2];                          /*                          */
  char lgc7[2];                          /*                          */
  char lgc8[2];                          /*                          */
  char lgc9[2];                          /*                          */
  char legacy_id[7];                     /*                          */
  char nta_name[75];                     /* Neighborhood Tabulation  */
                                         /* Area Name                */
  char from_coord[2][7];                 /* 1 = X Coordinate         */
                                         /* 2 = Y Coordinate         */
  char to_coord[2][7];                   /* 1 = X Coordinate         */
                                         /* 2 = Y Coordinate         */
  char from_latitude[9];                /* Latitude of from intersct.*/
  char from_longitude[11];              /* Longitude of from intersct*/
  char to_latitude[9];                  /* Latitude of to intersct.  */
  char to_longitude[11];                /* Longitude of to intersct. */
  char blockface_id[10];                /* NEW location of this field*/
                                        /* because of length changed */
  char nbr_travel_lanes[2];              /* nbr of traveling lanes   */
  char nbr_park_lanes[2];                /* nbr of parking lanes     */
  char nbr_total_lanes[2];               /* total nbr of lanes       */
  char bike_lane_2[2];                   /*Bike Lane has 2 bytes     */
                                         /* numeric value            */
  char str_width_max[3];                 /*street width maximum      */
  char bike_traffic_dir[2];              //V17.1 Bike Traffic Direction
  char speed_limit[2];                   //V17.4 Speed Limit
  char puma_code[5];                     // PUMA Code V18.1
  char police_sector[4];                 // Police Sector V19.2
  char truck_route_type;                 /* Truck Route         V19.3*/
  char cen_tract_20[6];                  /* 2020 Census Tract   V21.3*/
  char cen_blk_20[4];                    /* 2020 Census Block   V21.3*/
  char cen_blk_20_sufx;                  /* 2020 Census Blk Sfx V21.3*/
  char nta_20[6];                        /* 2020 NTA code       V21.3*/
  char cdta_20[4];                       /* 2020 CDTA           V21.3*/
  char filler05[265];                    /*                    V21.3 */
//char filler05[286];                    /*                    V21.3 */
//char filler05[287];                    // V18.3
//char filler05[291];                    // V18.1
//char filler05[296];                    // V17.4
//char filler05[298];                    // V17.1
} C_WA2_F3CX;

typedef struct {                         /* Fn 3C Extended with      */
  C_WA2_F3CX cwa2f3cx;                   /* Auxilary Segments        */
  char filler1[6];                       /* Future use              */
  char seg_cnt[4];                       /* Number of Segments      */
  char segments[70][7];                  /* Segment Ids             */
} C_WA2_F3CX_AUXSEG;                     /* Fn 3CX with AUXSEGID     */


 /********************************************************************/
 /*                                                                  */
 /*        Platform-Independent Work Area 2 for Function 3CE v19.3   */
 /*                                                                  */
 /********************************************************************/

typedef struct {                         /* Data from CSCL added     */
  char filler01[47];                     /* VSAM key + key filler    */
  char b5sc_dup_flag;                    /* Dup Key Flag for b5sc    */
  char special_cond_flag;                /* Duplicate Key Flag or    */
                                         /* Continuous Parity Flag   */
  char loc_stat_seg;                     /* Locational Status of Seg */
  char cnty_bnd_ind;                     /* County Boundary Indicat  */
  char lgc[3][2];                        /* Preferred LGCs           */
  St_listb7 st[2];                       /* 1=Low and 2=High         */
                                         /* Nbr of cross sts at low  */
                                         /* house nbr end of street  */
                                         /* B7SCs of lo end Cross sts*/
  char x_street_reversal_flag;           /* Cross St Reversal Flag   */
  LION key;                              /* LION key                 */
  char genr_flag;                        /* Generated Record Flag    */
  char seg_len[5];                       /* Segment Length in Feet   */
  char seg_azm[3];                       /* Segment Azimuth          */
  char seg_orient;                       /* Segment Orientation      */
  char mh_ri_flag;                       /* Marble Hill/Rikers Island*/
                                         /* Alternative Boro flag    */
  char nodes[2][7];                      /* From and To Node IDs     */
  char sanit_snow_priority;              /* Sanitation Street Snow   */
                                         /* Priority (P,S,T,V)       */
  char seg_id[7];                        /* Segment Identifier       */
  char DOT_slca;                         /* DOT St Lght Contractr Are*/
  char curve_flag;                       /* Curve Flag               */
  char dog_leg;                          /* Dog leg flag             */
  char feature_type;                     /* Feature Type Code        */
  char segtypecode;                      /* Segment Type Code        */
  char coincident_seg_cnt;               /* Coincident Segment       */
                                         /*    Counter               */
                                         /* Requested Side of segment*/
  char lgc_list[4][2];                   /* List of LGC's            */
  char from_lgcs[4][2];                  /* List of from LGC's       */
  char to_lgcs[4][2];                    /* List of to LGC's         */
  char traffic_dir;                      /* Traffic Direction        */
  char roadway_type[2];                  /*                          */
  char physical_id[7];                   /*        c                 */
  char generic_id[7];                    /*                          */
  char NYPD_id[7];                       /*                          */
  char FDNY_id[7];                       /*                          */
  char street_status;                    /*                          */
  char str_width[3];                     /* Street Width             */
  char str_width_irr;                    /* Irregular Width Y or N   */
  char bike_lane;                        /*                          */
  char fcc[2];                           /* Federal Classification Cd*/
  char row_type;                         /* Right Of Way Type        */
  char lgc5[2];                          /*                          */
  char lgc6[2];                          /*                          */
  char lgc7[2];                          /*                          */
  char lgc8[2];                          /*                          */
  char lgc9[2];                          /*                          */
  char legacy_id[7];                     /*                          */
                                         /* Area Name                */
  char from_coord[2][7];                 /* 1 = X Coordinate         */
                                         /* 2 = Y Coordinate         */
  char to_coord[2][7];                   /* 1 = X Coordinate         */
                                         /* 2 = Y Coordinate         */
  char from_latitude[9];                 /*Latitude of from intersct.*/
  char from_longitude[11];               /*Longitude of from intersct*/
  char to_latitude[9];                   /*Latitude of to intersct.  */
  char to_longitude[11];                 /*Longitude of to intersct. */
                                         // because of length changed
  char nbr_travel_lanes[2];              /* nbr of traveling lanes   */
  char nbr_park_lanes[2];                /* nbr of parking lanes     */
  char nbr_total_lanes[2];               /* total nbr of lanes       */
  char bike_lane_2[2];                   /* Bike Lane New      V164.4*/
  char str_width_max[3];                 /* Max Str Width      V164.4*/
  char bike_traffic_dir[2];             //V17.1 Bike Traffic Direction
  char speed_limit[2];                   /* Posted Speed Limit  V17.4*/
  char truck_route_type;                 /* Truck Route         V19.3*/
  char filler02[137];                    /* V19.3                    */
  char sos_ind;                          /* Side of Street Indicator */
  SEGSIDEB7 req;                         /*Geographic Information for*/
  char filler03[104];                    /*                    V18.4 */

} C_WA2_F3CE, *PC_WA2_F3CE;              /*                          */

typedef struct {                         /* Fn 3C Extended with      */
  C_WA2_F3CE cwa2f3ce;                   /* Auxilary Segments        */
  AUXSEG auxseg;
} C_WA2_F3CE_AUXSEG, *PC_WA2_F3CE_AUXSEG;/* Fn 3CE with AUXSEGID     */

 /********************************************************************/
 /*                                                                  */
 /*        Platform-Independent Work Area 2 for Function 3S          */
 /*                                                                  */
 /********************************************************************/

typedef struct { char filler01[21];
                 char nbr_x_str[3];      /* Nbr of Cross sts in list */
                 CROSS_STRS cross_strs[350];/* Cross Street structure*/
               } C_WA2_F3S;

#ifdef __cplusplus
       }
#endif
#endif
