#!/usr/bin/python

start=251
length=16
for x in range(1,22):
  #print('#################################')
  #print('### Geographic identifier {0} ###'.format(x))
  #print('#################################')

  print('<field id="giLowHouseNumber{0}" start="{1}" length="{2}"></field><!-- geographic identifier {0} -->'.format(x,start,length))

  length = 16
  start = start + 16
  print('<field id="giHighHouseNumber{0}" start="{1}" length="{2}"></field>'.format(x,start,length))

  length = 8
  start = start + 16
  print('<field id="giStreetCode{0}" start="{1}" length="{2}" type="COMP"></field>'.format(x,start,length))

  length = 1
  ## Do not adjust start as previous field was composite
  print('<field id="giBoroughCode{0}" start="{1}" length="{2}"></field>'.format(x,start,length))

  length = 5
  start = start + 1
  print('<field id="gi5DigitStreetCode{0}" start="{1}" length="{2}"></field>'.format(x,start,length))

  length = 2
  start = start + 5
  print('<field id="giDcpPreferredLgc{0}" start="{1}" length="{2}"></field>'.format(x,start,length))

  length = 7
  start = start + 2
  print('<field id="giBuildingIdentificationNumber{0}" start="{1}" length="{2}"></field>'.format(x,start,length))

  length = 1
  start = start + 7
  print('<field id="giSideOfStreetIndicator{0}" start="{1}" length="{2}"></field>'.format(x,start,length))

  length = 1
  start = start + 1
  print('<field id="giGeographicIdentifier{0}" start="{1}" length="{2}"></field>'.format(x,start,length))

  length = 4
  start = start + 1
  print('<field id="giFiller{0}" start="{1}" length="{2}"></field>'.format(x,start,length))

  length = 16
  start = start + 4
