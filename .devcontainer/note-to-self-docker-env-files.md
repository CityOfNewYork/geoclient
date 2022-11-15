# Docker Environment Files

Source: https://docs.docker.com/compose/environment-variables/

When you set the same environment variable in multiple files, here’s the priority used by Compose to choose which value to use:

   1. Compose file
   2. Shell environment variables
   3. Environment file
   4. Dockerfile
   5. Variable is not defined

 NOTE:

   Docker does NOT support variable substitution or other shell-like string
   functions in .env files. E.g., use of single or double quotes will be
   part of variable values if used.

 See also: https://docs.docker.com/engine/reference/run/#env-environment-variables
