#include "geoclient.h"

/*
 * Simple proxy for platform-dependent Geosupport function call
 */
void callgeo(char *work_area1, char *work_area2) {
	GEOSUPPORT_FUNC(work_area1, work_area2);
}
