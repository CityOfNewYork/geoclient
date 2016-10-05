var a = function(json) {
  if (json && json.address) {
    return json.address;
  }
  return {};
};

var getFormInput = function() {
  var input = {};
  input.app_id=$("form :input[name='app_id']").val();
  input.app_key=$("form :input[name='app_key']").val();
  input.houseNumber=$("form :input[name='houseNumber']").val();
  input.street=$("form :input[name='street']").val();
  input.borough=$("form :input[name='borough']").val();
  return input;
  // return $("#addressform1").serialize();
};

var setFormInput = function(input) {
  $("form :input[name='houseNumber']").val(input.houseNumber);
  $("form :input[name='street']").val(input.street);
  $("form :input[name='borough']").val(getBoroughCode(input.borough));
};

var clearFormErrors = function()
{
  $("#streetLabel").removeClass("missingFormInput");
  $("#boroughLabel").removeClass("missingFormInput");
  $("#formErrorMessage").empty();
}

var showFormErrors = function(error)
{
  var boroughLi = "<li>Borough is required</li>",
      streetLi = "<li>Street Name is required</li>";
  var errorMsg = "Please provide valid values for the following form fields:<br/><br/>";

  errorMsg += "<ul>";

  if(error.street && error.borough)
  {
    // Both are wrong
    errorMsg += streetLi;
    errorMsg += boroughLi;
  } else if (error.street)
  {
    // Only street
    errorMsg += streetLi;
    $("#streetLabel").addClass("missingFormInput");
  } else
  {
    // Only borough
    errorMsg += boroughLi;
    $("#boroughLabel").addClass("missingFormInput");
  }
    errorMsg += "</ul>";
    $("#formErrorMessage").html(errorMsg).addClass("missingFormInput");
}

var validateForm = function(input) {

  clearFormErrors();

  var errorSpec = {};
  if(!input.street)
  {
    errorSpec.street = true;
  }

  if(!input.borough || input.borough == "0")
  {
      errorSpec.borough = true;
  }

  if(errorSpec.street || errorSpec.borough)
  {
    showFormErrors(errorSpec);
    return false;
  }

  return true;
};

var getQueryString = function()
{
  var input = {};
  var sn = getParameterByName("sn");
  input.houseNumber = sn ? sn : getParameterByName("houseNumber");
  input.street = getParameterByName("street");
  input.borough = getParameterByName("borough");
  return input;
};

var getBoroughCode = function(value) {
  if(value)
  {
    if(value.match(/^[1-5]$/)) {
    // Already a valid borough number
      return value;
    } else if (value.match(/manhattan/i)) {
      return "1";
    } else if (value.match(/bronx/i)) {
      return "2";
    } else if (value.match(/brooklyn/i)) {
      return "3";
    } else if (value.match(/queens/i)) {
      return "4";
    } else if (value.match(/staten is/i)) {
      return "5";
    }
  }
  // Invalid
  return "0";
};

var parse = function(json) {
  var result = {};
  var rc = a(json).geosupportReturnCode;
  result.rc = rc ? rc : "";
  result.success = result.rc && (result.rc.match(/^00/) || result.rc.match(/^01/));
  result.message = a(json).message;
  result.houseNumber = a(json).houseNumber;
  result.street = a(json).firstStreetNameNormalized;
  result.borough = a(json).firstBoroughName;
  if(result.houseNumber) {
    // Address
    result.address = result.houseNumber + " " + result.street + "(" + result.borough + ")";
  } else {
    // Place name
    result.address = result.street + "(" + result.borough + ")";
  }
  if (!result.success) {
    // error
    result.hasSchedule = false;
    var numString = a(json).numberOfStreetCodesAndNamesInList;
    var num = numString ? Number(numString) : 0;
    result.possibleStreets = [];
    result.hasPossibleStreets = num > 0;
    if (result.hasPossibleStreets) {
      for ( var i = 1; i <= num; i++) {
        result.possibleStreets.push(a(json)["streetName" + i]);
      }
    }
  } else {
    // success
    result.trashPickup = parseSchedule(a(json).sanitationRegularCollectionSchedule);
    result.recyclePickup = parseSchedule(a(json).sanitationRecyclingCollectionSchedule);
  }
  return result;
}

var parseSchedule = function(str) {
  if (!str || "" == str) {
    return null;
  }
  var sched = "";
  for ( var i = 0; i < str.length; i++) {
    var c = str.charAt(i);
    switch (c) {
    case 'M':
      sched += "Monday ";
      break;
    case 'T':
      if ((i + 1 <= str.length) && str.substr(i, i + 1) == "TH") {
        // Thursday
        sched += "Thursday ";
        // increment i over the 'H' character
        i++;
      } else {
        // Tuesday
        sched += "Tuesday ";
      }
      break;
    case 'W':
      sched += "Wednesday ";
      break;
    case 'F':
      sched += "Friday ";
      break;
    case 'S':
      sched += "Saturday ";
      break;
    case '6':
      if ((i + 1 <= str.length) && str.substr(i, i + 1) == "6X") {
        // Overwrite 'sched' variable instead of appending to it
        sched = "Six days a week(not Sunday)";
        // increment i over the 'X' character
        i++;
      }
      break;
    case 'A':
      // Applies only to recycling schedule:
      // sched += " - A week"
      break;
    case 'B':
      // Applies only to recycling schedule:
      // sched += " - B week"
      break;
    case 'E':
      // Applies only to recycling schedule:
      // sched = "Every " + sched // (prepend)
      break;
    default:
      // NO-OP
      break;
    }
  }
  return sched.trim();
}

var successCallback = function(json) {
  var result = parse(json);
  if (result.success) {
    // success
    showSuccessResults(result);
  } else {
    // error
    showErrorResults(result);
  }
};

var showSuccessResults = function(result) {
  var msg = "<p>";
  if (result.trashPickup) {
    // show schedule info
    msg = "<strong>The collection schedule for " + result.address
        + " is:</strong><br/><br/>";
    msg += "Refuse: " + result.trashPickup + "<br/>";
    msg += "Recycling: " + result.recyclePickup + "<br/><br />";
    msg += "Place refuse and recyclables at the curb the <em>night before</em> your collection day.<br /><br />";
    msg += "There is no garbage or recycling collection on <a href=\"http://www.nyc.gov/html/dsny/html/collection/schedule.shtml\">Department of Sanitation holidays</a>.<br />";

  } else {
    // possible commercial building (call succeeded but no schedule data)
    msg = "The address: "
        + result.address
        + " may be a commercial building, therefore there is no Refuse/Recycling Pickup schedule available.";
  }
  msg += "</p>";
  showMessage(msg);
};

var showErrorResults = function(result) {

  var msg = "<h3 class=\"resultHeading\">Location "
      + result.address
      + " Not Found</h3>"
      + "<p>Unable to identify any locations that were similar to the information you entered. Hint:</p>"
      + "<ul><li>Include street type in the Street Name field. E.g., Avenue, Street, Road, Place, etc.</li>"
      + "<li>Include geographic identifiers (North, South, East, or West) when they are part of the street name. E.g., \"West 52 Street\"</li>"
      + "<li>Try other common names for the street. For example, enter \"Avenue of the Americas\" instead of \"6th Avenue\"</li>"
      + "Click the \"New Search\" button below to search again.</p>";
  if(result.hasPossibleStreets)
  {
    msg += "<p>Possible candidates:";
    msg += "<ul>";
    for ( var i = 0; i < result.possibleStreets.length; i++) {
      var street = result.possibleStreets[i];
      msg += "<li><a href=\"index.html?houseNumber=" + result.houseNumber + "&street=" + street + "&borough=" + result.borough + "\">" +
          result.houseNumber + " " + street + " (" + result.borough + ")</a></li>";
    }
    msg += "</ul></p>";
  }
  showMessage(msg);
};

var handleCallError = function(xhr, status) {
  showMessage("<div class=\"serviceError\">[ERROR] - Call to geoclient service failed. Unable to retrieve DSNY collection schedules.</div>");
}

var showMessage = function(msg) {
  $("#results").html(msg);
};

var getParameterByName = function (name) {
  name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
  var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
      results = regex.exec(location.search);
  return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
};

// JQuery event handler for when the document is loaded
$(document).ready(function() {

  // Register event handler for form submission
  $("#addressform1").submit(function (event) {
    var formInput = getFormInput();
    if(validateForm(formInput))
    {
      // Required form values are present; call the service
      $.ajax({
          url : "@protocol@://@host@:@port@/@endpoint@",
          data : formInput,
          type : "GET",
          dataType : "jsonp",
          success : successCallback,
          error : handleCallError,
          complete : function(xhr, status) {}
      });
    }
    // Prevent default form submit behavior since Javascript/Ajax is
    // taking care of retrieving the requested schedlue data
    event.preventDefault();
  });

  // Handle request from query string (if any parameters are set) by
  // setting form values and submitting the form. This way, validation
  // is run.
  var queryStringInput = getQueryString();
  if( queryStringInput.houseNumber || queryStringInput.street || queryStringInput.borough )
  {
    setFormInput(queryStringInput);
    $("#addressform1").submit();
  }
});
