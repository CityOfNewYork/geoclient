#!/usr/bin/python

start=365
length=16
for x in range(1,22):
  print '#################################'
  print '### Geographic identifier {0} ###'.format(x)
  print '#################################'

  print 'giLowHouseNumber{0}={1},{2}'.format(x,start,length)

  length = 16
  start = start + 16
  print 'giHighHouseNumber{0}={1},{2}'.format(x,start,length)

  length = 8
  start = start + 16
  print 'giStreetCode{0}={1},{2},COMP'.format(x,start,length)

  length = 1
  ## Do not adjust start as previous field was composite
  print 'giBoroughCode{0}={1},{2}'.format(x,start,length)

  length = 5
  start = start + 1
  print 'gi5DigitStreetCode{0}={1},{2}'.format(x,start,length)

  length = 2
  start = start + 5
  print 'giDcpPreferredLgc{0}={1},{2}'.format(x,start,length)

  length = 7
  start = start + 2
  print 'giBuildingIdentificationNumber{0}={1},{2}'.format(x,start,length)

  length = 1
  start = start + 7
  print 'giSideOfStreetIndicator{0}={1},{2}'.format(x,start,length)

  length = 1
  start = start + 1
  print 'giGeographicIdentifier{0}={1},{2}'.format(x,start,length)

  length = 1
  start = start + 1
  print 'giTpadBinStatus{0}={1},{2}'.format(x,start,length)

  length =32 
  start = start + 1
  print 'giStreetName{0}={1},{2}'.format(x,start,length)

  length = 34
  start = start + 32
  print 'giFiller{0}={1},{2}'.format(x,start,length)

  length = 16 
  start = start + 34
