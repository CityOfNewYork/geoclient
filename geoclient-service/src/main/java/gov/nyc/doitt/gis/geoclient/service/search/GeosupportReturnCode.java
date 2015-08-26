/*
 * Copyright 2013-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nyc.doitt.gis.geoclient.service.search;

import gov.nyc.doitt.gis.geoclient.config.ReturnCodeValue;

public class GeosupportReturnCode
{
	private String returnCode;
	private String reasonCode;
	private String message;

	public boolean isCompassDirectionRequired()
	{
		return ReturnCodeValue.COMPASS_DIRECTION_REQUIRED.is(this.returnCode);
	}
	
	public boolean isRejected()
	{
		if(ReturnCodeValue.SUCCESS.is(this.returnCode) || ReturnCodeValue.WARN.is(this.returnCode))
		{
			return false;
		}
		return true;
	}
	
	public boolean hasSimilarNames()
	{
		return ReturnCodeValue.NOT_RECOGNIZED_WITH_SIMILAR_NAMES.is(this.returnCode);
	}
	
	public boolean hasReasonCode()
	{
		return this.reasonCode != null;
	}
	
	public boolean hasMessage()
	{
		return this.message != null;
	}
	
	public String getReturnCode()
	{
		return returnCode;
	}

	public void setReturnCode(String geosupportReturnCode)
	{
		this.returnCode = geosupportReturnCode;
	}

	public String getReasonCode()
	{
		return reasonCode;
	}

	public void setReasonCode(String reasonCode)
	{
		this.reasonCode = reasonCode;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	@Override
	public String toString()
	{
		return "GeosupportReturnCode [returnCode=" + returnCode + ", reasonCode=" + reasonCode
				+ ", message=" + message + "]";
	}

}
