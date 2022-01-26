/*
 * Copyright 2013-2022 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.cli;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.config.GeosupportConfig;
import gov.nyc.doitt.gis.geoclient.function.Field;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.function.WorkArea;
import gov.nyc.doitt.gis.geoclient.jni.GeoclientJni;
import gov.nyc.doitt.gis.geoclient.util.OperatingSystemUtils;

public class GeosupportClient
{
    private static final Logger log = LoggerFactory.getLogger(GeosupportClient.class);
    private static final String CONFIG_CHOICE = "c";
    private static final String SORT_BY_NAME = "order by name";
    private static final String SORT_BY_START = "order by position";
    private static final String QUIT_CHOICE = "q";
    private static final Map<String, Integer> FUNCTIONS = new HashMap<String, Integer>();
    private static final StringBuffer FUNCTION_MENU = new StringBuffer();
    static
    {
        FUNCTIONS.put(Function.F1, 1);
        FUNCTIONS.put(Function.F1E, 2);
        FUNCTIONS.put(Function.F1B, 3);
        FUNCTIONS.put(Function.F1A, 4);
        FUNCTIONS.put(Function.F1AX, 5);
        FUNCTIONS.put(Function.FBL, 6);
        FUNCTIONS.put(Function.FBN, 7);
        FUNCTIONS.put(Function.F2, 8);
        FUNCTIONS.put(Function.F2W, 9);
        FUNCTIONS.put(Function.F3, 10);
        FUNCTIONS.put(Function.FDG, 11);
        FUNCTIONS.put(Function.FHR, 12);
        // TODO: Add functions N*, D, DN
        SortedSet<String> functionNames = new TreeSet<String>();
        functionNames.addAll(FUNCTIONS.keySet());
        for (Iterator<String> iterator = functionNames.iterator(); iterator.hasNext();)
        {
            String functionName = iterator.next();
            FUNCTION_MENU.append(functionName);
            if (iterator.hasNext())
            {
                FUNCTION_MENU.append(", ");
            }

        }
    }

    private GeosupportConfig geosupportConfiguration;
    private Scanner scanner;
    private boolean displayEmptyFieldValues = false;
    private boolean combineWorkAreaResults = true;
    private Comparator<Field> fieldComparator = Field.DEFAULT_SORT;

    public GeosupportClient() throws IOException
    {
        super();
        log.info("Platform is {}", OperatingSystemUtils.uname());
        this.geosupportConfiguration = new GeosupportConfig(new GeoclientJni());
        this.scanner = new Scanner(System.in);
    }

    public void callFunction1()
    {
        doCallFunction1(Function.F1, false);
    }

    public void callFunction1E()
    {
        doCallFunction1(Function.F1E, false);
    }

    public void callFunction1B()
    {
        doCallFunction1(Function.F1B, false);
    }

    public void callFunction1A()
    {
        doCallFunction1(Function.F1A, false);
    }

    public void callFunction1AX()
    {
        doCallFunction1(Function.F1AX, true);
    }

    public void callFunctionBL()
    {
        showFunctionHeader(Function.FBL);
        String boroCode = promptAndReadValue("borough code");
        String block = promptAndReadValue("block");
        String lot = promptAndReadValue("lot");
        doCall(Function.FBL,
                buildParams("geosupportFunctionCode", Function.FBL, "bblBoroughCodeIn", boroCode, "bblTaxBlockIn",
                        block, "bblTaxLotIn", lot), true,this.displayEmptyFieldValues);
    }

    public void callFunctionBN()
    {
        showFunctionHeader(Function.FBN);
        String bin = promptAndReadValue("BIN");
        doCall(Function.FBN,
                buildParams("geosupportFunctionCode", Function.FBN, "buildingIdentificationNumberIn", bin), true, this.displayEmptyFieldValues);
    }

    public void callFunction2()
    {
        showFunctionHeader(Function.F2);
        String crossStreet1 = promptAndReadValue("cross street 1");
        String crossStreet2 = promptAndReadValue("cross street 2");
        String compassDirection = promptAndReadValue("compass direction (if necessary)");
        String boroCode = promptAndReadValue("borough code");
        doCall(Function.F2,
                buildParams("geosupportFunctionCode", Function.F2, "streetName1In", crossStreet1, "streetName2In",
                        crossStreet2, "compassDirection", compassDirection, "boroughCode1In", boroCode), false, this.displayEmptyFieldValues);
    }

    public void callFunction2W()
    {
        showFunctionHeader(Function.F2W);
        String crossStreet1 = promptAndReadValue("cross street 1");
        String crossStreet2 = promptAndReadValue("cross street 2");
        String compassDirection = promptAndReadValue("compass direction (if necessary)");
        String boroCode = promptAndReadValue("borough code");
        doCall(Function.F2W,
                buildParams("geosupportFunctionCode", Function.F2W, "streetName1In", crossStreet1, "streetName2In",
                        crossStreet2, "compassDirection", compassDirection, "boroughCode1In", boroCode), false, this.displayEmptyFieldValues);
    }

    public void callFunction3()
    {
        showFunctionHeader(Function.F3);
        String onStreet = promptAndReadValue("on street");
        String crossStreet1 = promptAndReadValue("cross street 1");
        String crossStreet2 = promptAndReadValue("cross street 2");
        String compassDirection = promptAndReadValue("compass direction (if necessary)");
        String boroCode = promptAndReadValue("borough code");
        doCall(Function.F3,
                buildParams("geosupportFunctionCode", Function.F3, "streetName1In", onStreet, "streetName2In",
                        crossStreet1, "streetName3In", crossStreet2, "compassDirection", compassDirection,
                        "boroughCode1In", boroCode), true, this.displayEmptyFieldValues);
    }

    public void callFunctionDG()
    {
        showFunctionHeader(Function.FDG);
        String boroCode = promptAndReadValue("borough code");
        String streetCode = promptAndReadValue("streetCode (5 digits + 2 digit LGC)");
        doCall(Function.FDG,
                buildParams("geosupportFunctionCode", Function.FDG, "boroughCode1In", boroCode, "streetCode1In", streetCode),
                false, this.displayEmptyFieldValues);
    }

    public void callFunctionHR()
    {
        showFunctionHeader(Function.FHR);
        doCall(Function.FHR,
                buildParams("geosupportFunctionCode", Function.FHR),
                false, this.displayEmptyFieldValues);
    }

    public String chooseFunction()
    {
        System.out.println();
        System.out.println();
        System.out.print(String.format("Enter a function(%s), '%s' to configure display or '%s' to quit: ", FUNCTION_MENU.toString(), CONFIG_CHOICE, QUIT_CHOICE));
        return this.scanner.nextLine();
    }

    public void showUnknownFunctionErrorMessage(String unknownFunction)
    {
        System.out.println();
        System.out.println(String.format("[ERROR] Unknown function %s", unknownFunction));
        System.out.println();
    }

    public void quit()
    {
        System.out.println();
        System.out.println();
        System.out.println("Bye, bye...");
        this.scanner.close();
    }

    public static void main(String[] args) throws Exception
    {
        GeosupportClient client = null;
        try
        {
            client = new GeosupportClient();
            String action = null;
            while (!(action = client.chooseFunction()).equalsIgnoreCase(QUIT_CHOICE))
            {
                action = action.toUpperCase();
                if(CONFIG_CHOICE.equalsIgnoreCase(action))
                {
                    client.doConfigure();
                    continue;
                }

                if (!FUNCTIONS.containsKey(action))
                {
                    client.showUnknownFunctionErrorMessage(action);
                    continue;
                }

                switch (FUNCTIONS.get(action))
                {
                case 1:
                    client.callFunction1();
                    break;
                case 2:
                    client.callFunction1E();
                    break;
                case 3:
                    client.callFunction1B();
                    break;
                case 4:
                    client.callFunction1A();
                    break;
                case 5:
                    client.callFunction1AX();
                    break;
                case 6:
                    client.callFunctionBL();
                    break;
                case 7:
                    client.callFunctionBN();
                    break;
                case 8:
                    client.callFunction2();
                    break;
                case 9:
                    client.callFunction2W();
                    break;
                case 10:
                    client.callFunction3();
                    break;
                case 11:
                    client.callFunctionDG();
                    break;
                case 12:
                    client.callFunctionHR();
                    break;
                }
            }
        } finally
        {
            if (client != null)
            {
                client.quit();
            }
        }
    }

    private void doConfigure()
    {
        showConfiguration();
        doConfigureDisplayEmptyFieldValues();
        doConfigureSort();
        doConfigureCombineWorkAreas();
    }


    private void doConfigureDisplayEmptyFieldValues()
    {
        boolean beforeConfig = this.displayEmptyFieldValues;
        System.out.println("Toggle display empty field value option?");
        this.displayEmptyFieldValues = promptAndReadBoolean();
        if(this.displayEmptyFieldValues != beforeConfig)
        {
            showDisplayEmptyConfiguration();
        }
    }

    private void doConfigureSort()
    {
        String currentSort = null;
        String otherSort = null;
        if(isDefaultSort())
        {
            currentSort = SORT_BY_START;
            otherSort = SORT_BY_NAME;
        }
        else
        {
            currentSort = SORT_BY_NAME;
            otherSort = SORT_BY_START;
        }
        System.out.println(String.format("Change from current field sorting: '%s' to: '%s'?", currentSort, otherSort));
        boolean change = promptAndReadBoolean();
        if(change)
        {
            switchSort();
            showSortConfiguration();
        }
    }

    private void doConfigureCombineWorkAreas()
    {
        String message = null;
        if(this.combineWorkAreaResults)
        {
            message = "Show work areas separately?";
        }else
        {
            message = "Show combined work areas?";
        }
        System.out.println(message);
        boolean change = promptAndReadBoolean();
        if(change)
        {
            this.combineWorkAreaResults = !this.combineWorkAreaResults;
            showCombineWorkAreasConfiguration();
        }
    }

    private void switchSort()
    {
        if(isDefaultSort())
        {
            this.fieldComparator = Field.NAME_SORT;
        }else
        {
            this.fieldComparator = Field.DEFAULT_SORT;
        }
    }
    private boolean isDefaultSort()
    {
        return Field.DEFAULT_SORT == this.fieldComparator;
    }

    private void doCall(String functionName, Map<String, Object> parameters, boolean doExtended, boolean displayEmptyFields)
    {
        Function function = getFunction(functionName);
        parameters.put("workAreaFormatIndicatorIn", "C");
        if (doExtended)
        {
            parameters.put("modeSwitchIn", "X");
        } else
        {
            parameters.put("crossStreetNamesFlagIn", "E");
        }
        log.debug("Making call with params: {}", parameters);
        Map<String, Object> results = function.call(parameters);
        showResults(function ,results);
    }

    private void doCallFunction1(String functionName, boolean doExtended)
    {
        showFunctionHeader(functionName);
        String houseNumber = promptAndReadValue("house number");
        String street = promptAndReadValue("street name or 7 digit street code");
        String boroCode = promptAndReadValue("borough code");
        String zipCode = promptAndReadValue("zip code");
        if (isB7SC(street))
        {
            doCall(functionName,
                    buildParams("geosupportFunctionCode", functionName, "houseNumberIn", houseNumber,
                            "streetCode1In", street, "boroughCode1In", boroCode, "zipCodeIn", zipCode), doExtended, this.displayEmptyFieldValues);
        } else
        {

            doCall(functionName,
                    buildParams("geosupportFunctionCode", functionName, "houseNumberIn", houseNumber,
                            "streetName1In", street, "boroughCode1In", boroCode, "zipCodeIn", zipCode), doExtended, this.displayEmptyFieldValues);
        }
    }

    private boolean isB7SC(String string)
    {
        return string.matches("\\d{7}");
    }

    private void showConfiguration()
    {
        System.out.println("Current Config");
        System.out.println("-------------------------------------------------");
        showDisplayEmptyConfiguration();
        System.out.println();
        showSortConfiguration();
        System.out.println();
        showCombineWorkAreasConfiguration();
        System.out.println();
    }

    private void showDisplayEmptyConfiguration()
    {
        System.out.println(String.format("'Display empty?'='%s'", String.valueOf(this.displayEmptyFieldValues)));
    }

    private void showSortConfiguration()
    {
        String currentSort = isDefaultSort() ? SORT_BY_START : SORT_BY_NAME;
        System.out.println(String.format("'Field Sort Order'='%s'", currentSort));
    }

    private void showCombineWorkAreasConfiguration()
    {
        System.out.println(String.format("'Combine work areas in results?'='%s'", String.valueOf(this.combineWorkAreaResults)));
    }

    private void showFunctionHeader(String functionName)
    {
        System.out.println(String.format("Function %s", functionName));
        System.out.println("-------------------------------------------------");
        System.out.println();
    }

    private void showWorkAreaHeader(String... workAreaName)
    {
        System.out.println();
        System.out.println();
        if(workAreaName.length == 2)
        {
            System.out.println(String.format("Work Areas %s & %s", workAreaName[0], workAreaName[1]));
        }else
        {
            System.out.println(String.format("Work Area %s", workAreaName[0]));
        }
        System.out.println("-------------------------------------------------");
        System.out.println();
    }

    private String promptAndReadValue(String valueName)
    {
        System.out.print(String.format("Enter %s: ", valueName));
        return this.scanner.nextLine();
    }

    private boolean promptAndReadBoolean()
    {
        String choice = promptAndReadValue("'[y]es' or '[n]o'");
        if(choice == null || choice.isEmpty())
        {
            return false;
        }
        return choice.toLowerCase().startsWith("y");
    }

    private void showResults(Function function, Map<String, Object> results)
    {
        WorkArea wa1 = function.getWorkAreaOne();
        WorkArea wa2 = function.getWorkAreaTwo();
        if(this.combineWorkAreaResults)
        {
            showWorkAreaHeader(wa1.getId(),wa2.getId());
            List<String> fieldNames = wa1.getFieldIds(fieldComparator, true, true);
            fieldNames.addAll(wa2.getFieldIds(fieldComparator, true, true));
            if(!isDefaultSort())
            {
                // Name sort all results together
                Collections.sort(fieldNames);
            }
            showResults(fieldNames, results);
        }else
        {
            showWorkAreaHeader(wa1.getId());
            showResults(wa1.getFieldIds(fieldComparator,true, true), results);
            showWorkAreaHeader(wa2.getId());
            showResults(wa2.getFieldIds(fieldComparator,true, true), results);
        }
    }

    private void showResults(List<String> fieldIds, Map<String, Object> results)
    {
        for (String field: fieldIds)
        {
            Object value = results.get(field);
            value = value != null ? value : "";
            if(!this.displayEmptyFieldValues && value == "")
            {
                continue;
            }
            System.out.println(String.format("%s='%s'", field, value));
        }
    }

    private Function getFunction(String functionName)
    {
        return this.geosupportConfiguration.getFunction(functionName);
    }

    private Map<String, Object> buildParams(Object... params)
    {
        if (params.length % 2 != 0)
        {
            throw new IllegalArgumentException("Even number of parameters is required");
        }

        Map<String, Object> result = new HashMap<String, Object>();
        String paramName = null;
        for (int i = 0; i < params.length; i++)
        {
            if (i % 2 == 0)
            {
                paramName = params[i].toString();
            } else
            {
                result.put(paramName, params[i]);
            }
        }
        return result;
    }

}
