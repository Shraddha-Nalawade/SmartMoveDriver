package com.smartmovetheapp.smartmovedriver.data;

import java.util.HashMap;

public class HelpDataPump {
    public static HashMap<String, String> getData() {
        HashMap<String, String> helpDataMap = new HashMap<String, String>();

        helpDataMap.put("Where are SmartMove services available?", "SmartMove services are available in San Diego.");
        helpDataMap.put("What safety measures has SmartMove put in place", "All drivers have passed a background check, have had their driving records examined, and are at least 18 years of age. Users are able to rate the drivers conduct, SmartMove uses ratings to help us ensure better customer experiences. ");
        helpDataMap.put("What does SmartMove do?", "SmartMove connect willing drivers with pickup trucks, cargo vans, and cars with individuals and businesses that need to transport large, medium, and small items.");
        helpDataMap.put("Does SmartMove offer services on Sundays?", "Yes, in fact, SmartMove offers services 24/7.");
        helpDataMap.put("Will the drivers handle my items with care?", "Each driver will come with two straps and a tarp for protection, but we do suggest our customers wrap or package items as nessessary prior to the driver arriving.  Drivers are encouraged to  inspect items for damage before moving anything.");
        helpDataMap.put("Will drivers help me assemble/disassemble large items?", "In rare circumstances drivers may have to dissasembe and then reassemble items.  This is done purely for transport reasons.  If you have an item that you believe will need this work please mention this in your request description.");
        helpDataMap.put("Can I call the Driver?", "Yes. You will be able to call the driver through the mobile app or message them through the webite.  Phone numbers are anonymous.");
        helpDataMap.put("What is the cancellation fee?", "Any time a service request, either on-demand or a reservation is cancelled within 24 hours of the ordered service a $10 cancellation fee will be applied.");
        helpDataMap.put("Can I tip the driver?", "Tipping is not required but drivers greatly appreciate them.  100% of tips go to the drivers.");
        helpDataMap.put("How do I pay for a service?", "Payments are made through our secure mobile application.");
        helpDataMap.put("Will the estimate quoted be the final price?", "Yes, driver will bid one final amount which wont change.");
        helpDataMap.put("Does SmartMove transport hazardous materials?", "No.  SmartMove will not be moving any hazardous materials including but not limited to batteries, aerosols, mercury and mercury-containing products, light bulbs, automotive fuels and fluids, propane, fireworks, ammunition, paint, paint thinners, solvents, household cleaners, lawn chemicals, certain personal nail products.");




        return helpDataMap;
    }
}
