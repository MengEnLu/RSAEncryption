import java.math.BigDecimal;
import java.util.*;


public class Encryption {

    public static void main (String args[]){
        Scanner sc = new Scanner(System.in);

        System.out.println("================Bank settings==================");
//        System.out.print("Enter the first prime number: ");
//        final int p = sc.nextInt();
//        System.out.print("Enter the second prime number: ");
//        final int q = sc.nextInt();


        //initialize two prime numbers
        final int p = 11;
        final int q = 23;
        final int n = p*q;
        final int phi = (p-1)*(q-1);

        ArrayList<Integer> eOptions= getEOptions(phi);
        printEOptions(eOptions);
        //===========test==============
//            for(int i=0; i< eOptions.size(); i++){
//                System.out.print(eOptions.get(i) +" ");
//            }
//            System.out.println();
        //========================

        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();


        int e = eOptions.get(choice);
        System.out.println();
        System.out.println("PUBLIC PIN GENERATED FOR THE USER IS " + e);
        //=====test: e =========
            //System.out.println("e is " + e);
        //========================

        int d = getD(e,phi);
        System.out.println("PRIVATE PIN GENERATED FOR THE BANK IS " + d);
        System.out.println();
        //=========test: d===========
            //System.out.println("d is " + d);
        //==========================
        System.out.println("Sending the public pin " + e + " to user...");
        System.out.println("=================================================");
        System.out.println();

        sc.nextLine();
        System.out.println("==================User Interface====================");
        System.out.print("Enter your message to encrypt: ");
        String userMsg = sc.nextLine();
        //=========test: userMsg========
            //System.out.println("userMsg: " + userMsg);
        //=============================

        //===========test: encryptedMsg
            //System.out.print(getEncryptMsg(userMsg,7,253));
        //=================================

        ArrayList<Integer>encryptedMsgList = getEncryptedMsg(userMsg,e,n);
        printEncryptedMsg(encryptedMsgList);
        System.out.println("===================================================");
        System.out.println();

//        System.out.println("Enter your selected Pin");
//        int pin = sc.nextInt();
        //=========test: pin========
            //System.out.print(pin);
        //=====================

        //sc.nextLine();
        System.out.println("=================Bank Interface====================");
        System.out.print("Enter the Bank private pin to verify identity: ");
        int pin = sc.nextInt();

        sc. nextLine();
        System.out.print("Enter the encrypted message to decipher: ");
        String userEncryptedMsg = sc.nextLine();
        //==========test============
            //System.out.print(userEncryptedMsg);
        //========================

        ArrayList<Integer> decryptedMsgList = getDecryptedMsg(userEncryptedMsg, pin, n);
        printDecryptedMsg(decryptedMsgList);




    }//end of main


    /**
     * getEOptions
     * getS all the available options e.
     * This method calls getAllPrimeNum to get all the prime number up to phi
     * Then it deletes the prime numbers that are divisible by phi.
     * @param phi
     * @return availableE, which is an ArrayList containing prime numbers that are
     *                      1) smaller than phi
     *                      2) not divisible by phi
     */
    public static ArrayList<Integer> getEOptions(int phi){
        ArrayList<Integer> availabeE = getAllPrimeNum(phi);

        for(int i= 0; i< availabeE.size();i++){
            if(phi % availabeE.get(i) ==0){
                availabeE.remove(i);
            }
        }
        return availabeE;
    }


    /**
     * get AllPrimeNum get all the prime number between 2 and phi(included).
     * Store all values in Array list
     *
     * @param phi
     * @return an arrayList containing all the prime number between 2 and phi
     */
    public static ArrayList <Integer> getAllPrimeNum(int phi){
        ArrayList<Integer> primeArr = new ArrayList<>();
        label:
        for(int i = 2; i <= phi; i++){
            for(int j = 2; j<=i; j++){

                if(i%j == 0) {                   // if i can be divided
                    if (i == j) {                      //if i can only be divided by itself. prime number
                        primeArr.add(i);
                        continue label;
                    } else {                          //if i can be divided by other numbers.
                        continue label;
                    }
                }else {
                    if (j > (i / 2)) {              //2j > i
                        primeArr.add(i);
                        continue label;
                    }

                    if(j> Math.sqrt(phi)){         //121 = 11*11
                        primeArr.add(i);
                        continue label;
                    }

                }
            }
        }

        return primeArr;
    }


    public static void printEOptions(ArrayList<Integer> eOptions){
        System.out.println("Here are all the available pin choices for user:");

        int counter = 0;
        for(int i = 0; i< eOptions.size(); i++){
            System.out.print("("+i+") "+ eOptions.get(i)+"\t\t");
            counter ++;

            if(counter == 8){
                System.out.println();
                counter =0;
            }
        }
        System.out.println();
        System.out.println();
    }



    /**
     * calculate d using e and phi
     * @param e
     * @param phi
     * @return d
     */
    public static int getD(int e, int phi){
        int d;
        int x=1;
        int temp = phi*x+1;

        while(temp % e != 0){
            x++;
            temp = phi*x+1;
        }

        d = temp/e;
        return d;
    }


    /**
     * getEncryptMsg
     * Takes in a string of characters. Then find each letter's ascii code.
     * Apply the encryption method to each letter's original ascii number. (text)^e mod n
     * Add the encrypted value into an array list
     *
     * @param userMsg
     * @param e
     * @param n
     * @return an array list containing integer values.
     */
    public static  ArrayList<Integer> getEncryptedMsg(String userMsg, int e, int n){
        ArrayList<Integer> encryptedMsg = new ArrayList<>();

        for(int i =0; i< userMsg.length(); i++){
            int ascii = userMsg.charAt(i);
            int encryptedAscii = encryptDecryptMethod(ascii, e, n);
            encryptedMsg.add(encryptedAscii);
        }

        return encryptedMsg;
    }


    /**
     * printEncryptedMsg
     * get the integers from the array list and convert them into one string
     * prints the string in the end.
     * @param encryptedMsg  is an array list containing encrypted Ascii numbers
     */
    public static void printEncryptedMsg(ArrayList<Integer> encryptedMsg){
        String result ="";
        for(int i =0; i< encryptedMsg.size(); i++){
            int num = encryptedMsg.get(i);
            result = result+fillZero(num);
        }
        System.out.print("User encrypted message: ");
        System.out.println(result);
    }


    /**
     * fillZero
     * This method takes an integer and converts it to a string filled with zeros
     * in front of integers if the integer has less than three decimal digits.
     * @param num
     * @return string of an integer filled with zeros in its front.
     */
    public static String fillZero(int num){
        String fillZero= "";

            if (num > 99) {                                 //convert three digit num to string
                fillZero = fillZero + Integer.toString((int) num);
            } else if ((10 <= num) && num <= 99) {    //add a zero in front of two digit num and convert it to string
                fillZero = fillZero + "0" + Integer.toString((int) num);
            } else if (0 <= num && num <= 9) {        //add two zeros and convert it to string;
                fillZero = fillZero + "00" + Integer.toString((int) num);
            }

        return fillZero;
    }


    /**
     * Takes in a string of encrypted ascii code.
     * Then separate each 3 numbers. Convert them back into integers
     * Finally use the decode method and put the deciphered letters
     * into an array list
     * @param encryptedMsg
     * @param d
     * @param n
     * @return a string of deciphered ascii value
     */
    public static ArrayList<Integer> getDecryptedMsg(String encryptedMsg, int d, int n){
        ArrayList<Integer> decipheredArr = new ArrayList<>();

        for(int i= 0; i< encryptedMsg.length(); i+=3){

            String temp ="";
            int value;
            double decipheredVal;
            for(int j = i; j< i+3; j++) {
                temp = temp + encryptedMsg.charAt(j);
            }

            value = Integer.parseInt(temp);
            decipheredVal = encryptDecryptMethod(value,d,n);
            decipheredArr.add((int)decipheredVal);
        }

        return decipheredArr;
    }


    /**
     * decryptMethod takes in the encrypted value and
     * performs decryption based on the equation (value)^power mod n
     * (value)^power mod n is stored using BigDecimal
     * The final result is converted to int type.
     * @param value
     * @param power
     * @param n
     * @return int result, which has a value of (value)^power mod n.
     */
    public static int encryptDecryptMethod(int value, int power, int n){
        //convert value and n to big decimal type
        BigDecimal cVal = new BigDecimal(value);
        BigDecimal nVal = new BigDecimal(n);

        //big decimal result stores c^power and then mod itself with nVal
        BigDecimal val= cVal.pow(power);
        val = val.remainder(nVal);

        //convert big decimal value to int value
        int result = val.intValue();

        return result;
    }

    /**
     * printDecryptedMsg
     * This method acquires the array list which has all the decrypted value
     * The for loop goes through the array and get each elements.
     * The elements are then converted to char type
     * The method finally prints the character.
     * @param decipheredArr
     */
    public static void printDecryptedMsg(ArrayList<Integer> decipheredArr){
        System.out.print("The decrypted message from user: ");

        for(int i = 0; i< decipheredArr.size(); i++){
            int temp = decipheredArr.get(i);
            char c = (char)temp;
            System.out.print(c);
        }
        System.out.println();
    }


}
