#if !defined(WA2FIELDS) 
#define WA2FIELDS 

typedef struct _FIELDENTRY { 
  char fld_name[32]; 
  int  fld_len; 
  int  fld_start; 
  int  arr_entries; 
  int  arr_len; 
  int  start_structure; 
  int  end_structure; 
  char data_type; 
  char entry_type; 
} FIELDENTRY, *PFIELDENTRY;

//spo(2012-12-17):  I don't think I need extern if not using dll.
extern PFIELDENTRY wa1_flds; 
extern int n_wa1_field_recs;

extern PFIELDENTRY wa2_f1_flds; 
extern int n_wa2_f1_field_recs;

extern PFIELDENTRY wa2_f1a_flds; 
extern int n_wa2_f1a_field_recs;

extern PFIELDENTRY wa2_f1ax_flds; 
extern int n_wa2_f1ax_field_recs;

extern PFIELDENTRY wa2_f1b_flds; 
extern int n_wa2_f1b_field_recs;

extern PFIELDENTRY wa2_f1e_flds; 
extern int n_wa2_f1e_field_recs;

extern PFIELDENTRY wa2_f1ex_flds; 
extern int n_wa2_f1ex_field_recs;

extern PFIELDENTRY wa2_f2_flds; 
extern int n_wa2_f2_field_recs;

extern PFIELDENTRY wa2_f2w_flds; 
extern int n_wa2_f2w_field_recs;

extern PFIELDENTRY wa2_f3_flds; 
extern int n_wa2_f3_field_recs;

extern PFIELDENTRY wa2_f3as_flds; 
extern int n_wa2_f3as_field_recs;

extern PFIELDENTRY wa2_f3c_flds; 
extern int n_wa2_f3c_field_recs;

extern PFIELDENTRY wa2_f3cas_flds; 
extern int n_wa2_f3cas_field_recs;

extern PFIELDENTRY wa2_f3cx_flds; 
extern int n_wa2_f3cx_field_recs;

//extern PFIELDENTRY wa2_f3cxas_flds; 
//extern int n_wa2_f3xas_field_recs;

extern PFIELDENTRY wa2_f3x_flds; 
extern int n_wa2_f3x_field_recs;

extern PFIELDENTRY wa2_f3s_flds; 
extern int n_wa2_f3s_field_recs;

extern PFIELDENTRY wa2_fbl_flds; 
extern int n_wa2_fbl_field_recs;

extern PFIELDENTRY wa2_fblx_flds; 
extern int n_wa2_fblx_field_recs;

extern PFIELDENTRY wa2_fbn_flds; 
extern int n_wa2_fbn_field_recs;

extern PFIELDENTRY wa2_fbnx_flds; 
extern int n_wa2_fbnx_field_recs;

extern PFIELDENTRY wa2_fhr_flds; 
extern int n_wa2_fhr_field_recs;

extern PFIELDENTRY wa2_fap_flds; 
extern int n_wa2_fap_field_recs;

extern PFIELDENTRY wa2_fapx_flds; 
extern int n_wa2_fapx_field_recs;

#endif 
