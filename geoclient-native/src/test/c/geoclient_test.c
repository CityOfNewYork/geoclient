#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "geoclient.h"
#include "pac.h"

#define member_size(type, member) sizeof(((type *)0)->member)

#define LEN_WA1 sizeof(C_WA1)
#define LEN_WA2_F1B sizeof(C_WA2_F1B)
#define LEN_WA2_F1AX sizeof(C_WA2_F1AX)
#define LEN_WA2_F2 sizeof(C_WA2_F2)
#define LEN_WA2_F3 sizeof(C_WA2_F3X)
#define LEN_RET_CODE member_size(OUTWA1, ret_code)
#define LEN_REASON_CODE member_size(OUTWA1, reason_code)
#define LEN_RC (LEN_RET_CODE + LEN_REASON_CODE)
#define LEN_MSG member_size(OUTWA1, msg)
#define ADDRESS_FUNC "1B"

const char PLATFORM_INDICATOR = 'C';

union Request {
  C_WA1 obj;
  char chars[LEN_WA1];
} request;

union Response {
  C_WA2_F1B obj;
  char chars[LEN_WA2_F1B];
} response;

void geocode_address(char **args);
size_t trim_whitespace(char *out, size_t len, const char *str);
void show_args(int argc, char **argv);
void show_call_status(const char *prefix, const char *rc, const char *message);
void show_work_area(const char *prefix, const char *work_area);

int main(int argc, char **argv)
{
  char *function_name = argv[1];
  if(strcmp(ADDRESS_FUNC,function_name) == 0)
  {
    if (argc < 5)
    {
      printf("Usage: geoclient_test '" ADDRESS_FUNC "' '<house number>' '<street name>' '<borough>'\n\n");
      return(1);
    }
    char *args[3] = {argv[2], argv[3], argv[4]};
    geocode_address(args);
  }
  else
  {
    printf("Calling Geosupport function '%s' has not been implemented yet.", function_name);
  }

  return(0);
}

void geocode_address(char **args)
{
  char *house_number = args[0];
  char *street = args[1];
  char *borough = args[2];
  printf("geocoding address[ house_number: '%s', street: '%s', borough: '%s']\n", house_number, street, borough);
  size_t request_length = LEN_WA1;
  size_t response_length = LEN_WA2_F1B;
  printf("\n");
  printf("----\n");
  printf("Size of work area one: %I64d\n", request_length);
  printf("Size of work area two: %I64d\n", response_length);
  printf("\n");
  //memset(request.chars, '', request_length);
  request.chars[LEN_WA1];
  request.chars[LEN_WA1] = '\0';
  request.obj.input.platform_ind = PLATFORM_INDICATOR;
  printf("ADDRESS_FUNC='%s'\n", ADDRESS_FUNC);
  char *function_name = ADDRESS_FUNC;
  memcpy(request.obj.input.func_code, function_name, strlen(function_name));
  memcpy(request.obj.input.hse_nbr_disp, house_number, strlen(house_number));
  memcpy(request.obj.input.sti, borough, 1);
  memcpy(request.obj.input.sti[0].Street_name, street, strlen(street));
  //memset(response.chars, ' ', response_length);
  response.chars[LEN_WA2_F1B];
  response.chars[LEN_WA2_F1B] = '\0';
  callgeo(request.chars, response.chars);
  printf("----\n");
  show_call_status("1E", request.obj.output.ret_code, request.obj.output.msg);
  show_call_status("1A", request.obj.output.ret_code_2, request.obj.output.msg_2);
  printf("\n");
  printf("----\n");
  show_work_area("WA1", request.chars);
  show_work_area("WA2", response.chars);
  printf("\n");
}

// Thank you, Internet! See:
//
//   http://stackoverflow.com/questions/122616/how-do-i-trim-leading-trailing-whitespace-in-a-standard-way
//
// Stores the trimmed input string into the given output buffer, which must be
// large enough to store the result.  If it is too small, the output is
// truncated.
size_t trim_whitespace(char *out, size_t len, const char *str)
{
  if(len == 0)
    return 0;

  const char *end;
  size_t out_size;

  // Trim leading space
  while(isspace(*str)) str++;

  if(*str == 0)  // All spaces?
  {
    *out = 0;
    return 1;
  }

  // Trim trailing space
  end = str + strlen(str) - 1;
  while(end > str && isspace(*end)) end--;
  end++;

  // Set output size to minimum of trimmed string length and buffer size minus 1
  out_size = (end - str) < len-1 ? (end - str) : len-1;

  // Copy trimmed string and add null terminator
  memcpy(out, str, out_size);
  out[out_size] = 0;

  return out_size;
}

void show_work_area(const char *prefix, const char *work_area)
{
  printf("[%s] <%s>\n", prefix, work_area);
}

void show_call_status(const char *prefix, const char *rc, const char *message)
{
  size_t rc_len = LEN_RC;
  size_t msg_len = LEN_MSG;

  //size_t rt_len = LEN_RET_CODE;
  //size_t re_len = LEN_REASON_CODE;
  //printf("rt_len: %u \n", rt_len);
  //printf("re_len: %u \n", re_len);
  //printf("rc_len: %u \n", rc_len);
  //printf("msg_len: %u \n", msg_len);

  char rc_buffer[rc_len];
  char msg_buffer[msg_len];

  trim_whitespace(rc_buffer, rc_len, rc);
  trim_whitespace(msg_buffer, msg_len, message);

  printf("[%s]      rc: <%s>\n", prefix, rc_buffer);
  printf("[%s] message: <%s>\n", prefix, msg_buffer);
}

void show_args(int argc, char **argv)
{
  for (size_t i = 0; i < argc; i++)
  {
    printf("parameter[%I64d] = '%s'\n", i, argv[i]);
  }
}
