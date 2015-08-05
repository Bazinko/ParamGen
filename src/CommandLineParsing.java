import java.io.*;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Character.isDigit;

/**
 * Created by Jenejkee on 30.07.15.
 */
public class CommandLineParsing extends RandomDate {

    public static List<String> generalList = new LinkedList<String>();

    public static void fetchedRequest(String s) throws ParseException, IOException {
        List<String> list = new LinkedList<String>();
        int count = 0;
        int duration = 0;
        int index;
        int k = 0;
        int j = 0;
        int g = 0;
        String beginDate = "";
        String endDate = "";
        String value = "";
        String relCustValue = "";
        String relTypeValue = "";
        String field = "";

//        String[] tmp = s.split("\\s+");
//
//        for (int i = 0; i<tmp.length; i++) {
//            s += tmp[i];
//        }
            while (s.length() != 0) {
                index = s.indexOf('=');
//                if (index == -1) {
//                    System.out.println("Данные введены неверно");
//                    k++;
//                    break;
//                }
                field = s.substring(0, index);
                while (isDigit(s.charAt(index + 1)) || s.charAt(index + 1) == '.' || s.charAt(index+1) == ':') {
                    value += s.charAt(index + 1);
                    index++;
                    if (index == s.length() - 1)
                        break;
                }

                if (field.toLowerCase().equals("count")) {
                    if (isDigit(s.charAt(s.indexOf('=') + 1))) {
                        count = Integer.parseInt(value);
                    } else {
                        System.out.println("Unable to read the information from the field \"count\"");
                        k++;
                    }
                } else if (field.toLowerCase().equals("duration")) {
                    if (isDigit(s.charAt(s.indexOf('=') + 1))) {
                        duration = Integer.parseInt(value);
                    } else {
                        System.out.println("Unable to read the information from the field \"duration\"");
                        k++;
                    }
                } else if (field.toLowerCase().equals("begin_date")) {
                    if (isValid(value, "dd.MM.yyyy"))
                        beginDate = value;
                    else {
                        if (!isValid(value, "dd.MM.yyyy")) {
                            System.out.println("Unable to read the information from the field \"begin_date\"");
                            k++;
                        }
                    }
                } else if (field.toLowerCase().equals("end_date")) {
                    if (isValid(value, "dd.MM.yyyy"))
                        endDate = value;
                    else {
                        if (!isValid(value, "dd.MM.yyyy")) {
                            System.out.println("Unable to read the information from the field \"end_date\"");
                            k++;
                        }
                    }
                } else if (field.toLowerCase().equals("relation_cust")) {
                    if (checkRelationValue(value)) {
                        relCustValue = value;
                    } else {
                        System.out.println("Unable to read the information from the field \"relation_cust\"");
                        k++;
                    }
                } else if (field.toLowerCase().equals("relation_type")) {
                    if (checkRelationValue(value)) {
                        relTypeValue = value;
                    } else {
                        System.out.println("Unable to read the information from the field \"relation_type\"");
                        k++;
                    }
                } else {
                    System.out.println("The data is entered incorrectly");
                    k++;
                }

                s = new StringBuffer(s).delete(0, index + 1).toString();

                value = "";
                field = "";


            }

        if (count == 0) {
            System.out.println("Field \"count\" is empty");
        }
        if (duration == 0) {
            System.out.println("Field \"duration\" is empty");
        }
        if (beginDate.isEmpty()) {
            System.out.println("Field \"begin_date\" is empty");
        }
        if (endDate.isEmpty()) {
            System.out.println("Field \"end_date\" is empty");
        }
        if (relCustValue.isEmpty()) {
            System.out.println("Field \"relation_cust\" is empty");
        }
        if (relTypeValue.isEmpty()) {
            System.out.println("Field \"relation_type\" is empty");
        }

        //System.out.println("\n" + "Общее количество звонков:" + "\n");
        if (isValid(beginDate, "dd.MM.yyyy") && isValid(endDate, "dd.MM.yyyy")) {
            if (k == 0) {
                for (int i = 1; i <= count; i++) {
                    int a = (int) (Math.random() * duration + 1);
                    int b = (int) (Math.random() * 3 + 1);
                    list.add(randomDOB(a, beginDate, endDate) + "|" + a + "|" + b);
                    //System.out.println(list.get(i-1));
                }
//                System.out.println("----------------------------------------------------------------------");
                generateRelationCust(relCustValue, count, list);
                generateRelationType(relTypeValue, count, list);
                writeToFile();
            }
        }
    }

    public static boolean checkRelationValue(String value) {
        String[] tmp = value.split("\\:");
        for (int i = 0; i<tmp.length; i++) {
            try {
                Integer.parseInt(tmp[i]);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    static void generateRelationCust(String value, int count, List<String> list) {
        String[] tmp = value.split("\\:");
        int i = 0;
        int usersCount = 15927573;
        File f = new File("Lib.txt");
        int custCount = (int) (Float.parseFloat(tmp[0])/100*usersCount);
        List<Integer> ls = new LinkedList<Integer>();
        List<String> outcomingNumbers = getOutcomingPhoneNumbers(f, count);

        for (int k = 1; k<=usersCount; k++)
            ls.add(k);

        int[] customers = new int[custCount];

        while (custCount != 0) {
            int b = (int)(Math.random()*usersCount+0);
            customers[i] = ls.get(b);
            ls.remove(b);
            custCount--;
            usersCount--;
            i++;
        }


        int callCount = (int) (Float.parseFloat(tmp[1])/100*count);
        int ostatok = callCount %  customers.length;

//        System.out.println("\n" + "Результат распределения:" + "\n");
        int n = 0;
        for (int k = 0; k<customers.length; k++) {
//            System.out.println("Звонки абонента " + customers[k]);
            String phoneNumber = getPhoneNumber(f, customers[k]);
            for (int j = 0; j<callCount / customers.length; j++) {
                if (phoneNumber.equals(outcomingNumbers.get(n)) && n != 0) {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n - 1) +
                            "|" + list.get(n));
                    } else if (phoneNumber.equals(outcomingNumbers.get(n)) && n == 0){
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n + 1) +
                            "|" + list.get(n));
                    } else {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n) +
                            "|" + list.get(n));
                    }
                n++;
            }
            if (ostatok > 0) {
                if (phoneNumber.equals(outcomingNumbers.get(n)) && n != 0) {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n - 1) +
                            "|" + list.get(n));
                } else if (phoneNumber.equals(outcomingNumbers.get(n)) && n == 0){
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n + 1) +
                            "|" + list.get(n));
                } else {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n) +
                            "|" + list.get(n));
                }
                n++;
                ostatok--;
            }
        }

//        System.out.println("----------------------------------------------------------------------");

        callCount = count - callCount;
        ostatok = callCount % ls.size();
//        System.out.println("\n" + "Оставшиеся звонки:" + "\n");
        for (int k = 0; k<ls.size(); k++) {
//            System.out.println("Звонки абонента " + ls.get(k));
            String phoneNumber = getPhoneNumber(f, ls.get(k));
            for (int j = 0; j<callCount / ls.size(); j++) {
                if (phoneNumber.equals(outcomingNumbers.get(n)) && n != 0) {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n-1) +
                            "|" + list.get(n));
                } else if (phoneNumber.equals(outcomingNumbers.get(n)) && n == 0){
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n+1) +
                            "|" + list.get(n));
                } else {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n) +
                            "|" + list.get(n));
                }
                n++;
            }
            if (ostatok > 0) {
                if (phoneNumber.equals(outcomingNumbers.get(n)) && n != 0) {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n - 1) +
                            "|" + list.get(n));
                } else if (phoneNumber.equals(outcomingNumbers.get(n)) && n == 0){
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n + 1) +
                            "|" + list.get(n));
                } else {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n) +
                            "|" + list.get(n));
                }
                n++;
                ostatok--;
            }
        }
    }

    static void generateRelationType(String reltypeValue, int count, List<String> list) {
        String[] tmp = reltypeValue.split("\\:");
        int i = 0;
        int usersCount = 15927573;
        File f = new File("Lib.txt");
        int voiceUsersCount = (int) (Float.parseFloat(tmp[0])/100*usersCount);
        int smsUsersCount = (int) (Float.parseFloat(tmp[1])/100*usersCount);
        List<Integer> ls = new LinkedList<Integer>();
        List<String> outcomingNumbers = getOutcomingPhoneNumbers(f, count);
        List<String> voiceList = new LinkedList<String>();
        List<String> smslist = new LinkedList<String>();
        List<String> mmslist = new LinkedList<String>();

        for (int k = 1; k<=usersCount; k++)
            ls.add(k);

        int[] voice = new int[voiceUsersCount];
        int[] sms = new int[smsUsersCount];

        while (voiceUsersCount != 0) {
            int b = (int)(Math.random()*usersCount+0);
            voice[i] = ls.get(b);
            ls.remove(b);
            voiceUsersCount--;
            usersCount--;
            i++;
        }

        i = 0;

        while (smsUsersCount != 0) {
            int b = (int)(Math.random()*usersCount+0);
            sms[i] = ls.get(b);
            ls.remove(b);
            smsUsersCount--;
            usersCount--;
            i++;
        }


        for (int k = 0; k<list.size(); k++) {
            if (getType(list.get(k)).equals("1"))
                voiceList.add(list.get(k));
            if (getType(list.get(k)).equals("2"))
                smslist.add(list.get(k));
            if (getType(list.get(k)).equals("3"))
                mmslist.add(list.get(k));
        }

        int residueOfVoices = voiceList.size() %  voice.length;
        int residueOfSms = smslist.size() % sms.length;
        int residueOfMms = mmslist.size() % ls.size();

//        System.out.println("\n" + "RELATION_TYPE" + "\n");
        int n = 0;
        for (int k = 0; k<voice.length; k++) {
//            System.out.println("Звонки пользователя " + voice[k]);
            String phoneNumber = getPhoneNumber(f, voice[k]);
            for (int j = 0; j<voiceList.size() / voice.length; j++) {
                if (phoneNumber.equals(outcomingNumbers.get(n)) && n != 0) {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n - 1) +
                            "|" + voiceList.get(n));
                } else if (phoneNumber.equals(outcomingNumbers.get(n)) && n == 0){
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n + 1) +
                            "|" + voiceList.get(n));
                } else {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n) +
                            "|" + voiceList.get(n));
                }
                n++;
            }
            if (residueOfVoices > 0) {
                if (phoneNumber.equals(outcomingNumbers.get(n)) && n != 0) {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n - 1) +
                            "|" + voiceList.get(n));
                } else if (phoneNumber.equals(outcomingNumbers.get(n)) && n == 0){
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n + 1) +
                            "|" + voiceList.get(n));
                } else {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n) +
                            "|" + voiceList.get(n));
                }
                n++;
                residueOfVoices--;
            }
        }

        n = 0;

        for (int k = 0; k<sms.length; k++) {
//            System.out.println("SMS пользователя " + sms[k]);
            String phoneNumber = getPhoneNumber(f, sms[k]);
            for (int j = 0; j<smslist.size() / sms.length; j++) {
                if (phoneNumber.equals(outcomingNumbers.get(n)) && n != 0) {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n-1) +
                            "|" + smslist.get(n));
                } else if (phoneNumber.equals(outcomingNumbers.get(n)) && n == 0){
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n+1) +
                            "|" + smslist.get(n));
                } else {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n) +
                            "|" + smslist.get(n));
                }
                n++;
            }
            if (residueOfSms > 0) {
                if (phoneNumber.equals(outcomingNumbers.get(n)) && n != 0) {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n - 1) +
                            "|" + smslist.get(n));
                } else if (phoneNumber.equals(outcomingNumbers.get(n)) && n == 0){
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n + 1) +
                            "|" + smslist.get(n));
                } else {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n) +
                            "|" + smslist.get(n));
                }
                n++;
                residueOfSms--;
            }
        }

        n = 0;

        for (int k = 0; k<ls.size(); k++) {
//            System.out.println("MMS пользователя " + ls.get(k));
            String phoneNumber = getPhoneNumber(f, ls.get(k));
            for (int j = 0; j<mmslist.size() / ls.size(); j++) {
                if (phoneNumber.equals(outcomingNumbers.get(n)) && n != 0) {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n-1) +
                            "|" + mmslist.get(n));
                } else if (phoneNumber.equals(outcomingNumbers.get(n)) && n == 0){
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n+1) +
                            "|" + mmslist.get(n));
                } else {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n) +
                            "|" + mmslist.get(n));
                }
                n++;
            }
            if (residueOfMms > 0) {
                if (phoneNumber.equals(outcomingNumbers.get(n)) && n != 0) {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n - 1) +
                            "|" + mmslist.get(n));
                } else if (phoneNumber.equals(outcomingNumbers.get(n)) && n == 0){
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n + 1) +
                            "|" + mmslist.get(n));
                } else {
                    generalList.add(phoneNumber + "|" + outcomingNumbers.get(n) +
                            "|" + mmslist.get(n));
                }
                n++;
                residueOfMms--;
            }
        }
    }

    public static String getType(String value) {
        String[] tmp = value.split("\\|");
        String s = tmp[tmp.length-1];

        return s;
    }

    public static String getPhoneNumber(File f, int userNumber) {
        int i = 0;
        String s = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(f));
            String line;
            while ((line = reader.readLine()) != null) {
                if (i == userNumber) {
                    String[] phoneNumber = line.split("\\|");
                    s = phoneNumber[1];
                    break;
                }
                i++;
            }
            reader.close();

        } catch(IOException e) {}

        return s;
    }

    public static List<String> getOutcomingPhoneNumbers(File f, int count) {
        int i = 0;
        BufferedReader reader = null;
        List<String> list = new LinkedList<String>();
        String[] phoneNumber;
        try {
            reader = new BufferedReader(new FileReader(f));
            String line;
            while ((line = reader.readLine()) != null && count+1 != i) {
                phoneNumber = line.split("\\|");
                list.add(phoneNumber[1]);
                i++;
            }
            reader.close();

        } catch(IOException e) {}

        return list;
    }

    static void writeToFile() throws IOException {
        int filesCount = generalList.size() / 100000;
        int presidue = generalList.size() % 100000;
        int predel = 100000;
        int n = 0;
        PrintWriter writer = null;

        if (filesCount == 0) {
            writer = new PrintWriter(new FileWriter("CDR1.cdr"));
            for (int i = 0; i<generalList.size(); i++) {
                writer.println((i+1) + "|" + generalList.get(i));
                //System.out.println(i + "|" + generalList.get(i));
            }
        } else {
            for (int k = 0; k<filesCount; k++) {
                writer = new PrintWriter(new FileWriter("CDR" + (k+1) + ".cdr"));
                for (int i = n; i<predel; i++) {
                    writer.println((i+1)+"|"+generalList.get(i));
                    //System.out.println(i + "|" + generalList.get(i));
                }
                predel += 100000;
                n += 100000;
            }
        }

        if (filesCount != 0) {
            writer = new PrintWriter(new FileWriter("CDR" + (filesCount + 1) + ".cdr"));
            for (int i = n; i < presidue; i++) {
                writer.println((i+1) + "|" + generalList.get(i));
                //System.out.println(i + "|" + generalList.get(i));
            }
        }
    }

}
