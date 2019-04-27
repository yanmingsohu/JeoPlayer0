// linear2alaw() - Convert a 16-bit linear PCM value to 8-bit A-law 
unsigned char linear2alaw( int pcm_val );

 // linear2ulaw()   -   Convert   a   linear   PCM   value   to   u-law   
unsigned char linear2ulaw( int pcm_val );

// alaw2linear()   -   Convert   an   A-law   value   to   16-bit   linear   PCM   
int  alaw2linear( unsigned   char a_val );

// ulaw2linear()   -   Convert   a   u-law   value   to   16-bit   linear   PCM   
int  ulaw2linear( unsigned   char u_val );

//   A-law   to   u-law   conversion   
unsigned   char  alaw2ulaw( unsigned   char aval );

//   u-law   to   A-law   conversion   
unsigned   char ulaw2alaw( unsigned   char uval );