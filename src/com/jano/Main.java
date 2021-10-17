package com.jano;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        greetings();
        while (true) {
            long num;
            System.out.print("\nEnter a request: ");
            String input = scanner.nextLine();

            if (isRequest(input) > 3) {
                String[] inputs;
                int occurrence;
                String[] requests;

                inputs = input.split("\\W+");
                num = Long.parseLong(inputs[0]);
                occurrence = Integer.parseInt(inputs[1]);
                String sub = input.substring(Integer.parseInt(inputs[1]));
                requests = sub.split("\\W+");


                System.out.println(Arrays.toString(requests).toUpperCase());
                System.out.println(num);
                System.out.println(occurrence);


            } else if (isRequest(input) == 3) {
                String[] inputs;
                int occurrence;
                String requestOne;
                String requestTwo;

                inputs = input.split("\\W+");
                num = Long.parseLong(inputs[0]);
                occurrence = Integer.parseInt(inputs[1]);
                requestOne = inputs[2];
                requestTwo = input.substring(input.lastIndexOf(" ") + 1);

                if (!checkRequest(requestOne) && checkRequest(requestTwo)) {
                    System.out.println("\nThe property [" + requestOne.toUpperCase() + "] is wrong.\n" +
                            "Available properties: " + Arrays.toString(Requests.values()));
                } else if (checkRequest(requestOne) && !checkRequest(requestTwo)) {
                    System.out.println("\nThe property [" + requestTwo.toUpperCase() + "] is wrong.\n" +
                            "Available properties: " + Arrays.toString(Requests.values()));
                } else if (!checkRequest(requestOne) && !checkRequest(requestTwo)) {
                    System.out.println("\nThe properties [" + requestOne.toUpperCase() + "," + requestTwo.toUpperCase() + "] are wrong.\n" +
                            "Available properties: " + Arrays.toString(Requests.values()));
                } else if (checkMultipleRequestPossibility(requestOne, requestTwo)) {
                    System.out.println("\nThe request contains mutually exclusive properties: [" + requestOne.toUpperCase() + "," + requestTwo.toUpperCase() + "]\n" +
                            "There are no numbers with these properties.");
                } else {
                    printDoubleRequestProperties(num, occurrence, requestOne, requestTwo);
                }
            } else if (isRequest(input) == 2) {
                String[] inputs;
                int occurrence;
                String request;

                inputs = input.split("\\W+");
                num = Long.parseLong(inputs[0]);
                occurrence = Integer.parseInt(inputs[1]);
                request = input.substring(input.lastIndexOf(" ") + 1);

                if (!checkRequest(request)) {
                    System.out.println("\nThe property [" + request.toUpperCase() + "] is wrong.\n" +
                            "Available properties: " + Arrays.toString(Requests.values()));
                } else {
                    printRequestProperties(num, occurrence, request);
                }
            } else if (isRequest(input) == 1) {
                int times;

                String sub1 = input.substring(0, input.indexOf(" "));
                num = Long.parseLong(sub1);
                String sub2 = input.substring(input.indexOf(" ") + 1);

                if (isNumber(sub2)) {
                    times = Integer.parseInt(sub2);
                    if (times > 0) {
                        printFollowingProperties(num, times);
                    } else {
                        System.out.println("\nThe second parameter should be a natural number or zero.");
                    }
                } else {
                    System.out.println("\nThe second parameter should be a natural number or zero.");
                }
            } else if (isNumber(input)) {
                num = Long.parseLong(input);

                if (num < 0 || !isNumber(input)) {
                    System.out.println("\nThe first parameter should be a natural number or zero.");
                } else if (num == 0) {
                    System.out.println("\nGoodbye!");
                    break;
                } else {
                    printProperties(num);
                }
            }
        }
    }

    public static void greetings() {
        System.out.println("Welcome to Amazing Numbers!\n");
        System.out.println("Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameter shows how many consecutive numbers are to be printed;\n" +
                "- two natural numbers and properties to search for;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit."
        );
    }

    public static boolean checkEven(long num) {
        long even = num % 2;
        return even == 0;
    }

    public static boolean checkOdd(long num) {
        long even = num % 2;
        return even != 0;
    }

    public static boolean checkBuzz(long num) {
        long divisor = num % 7;
        String s = Long.toString(num);
        if (divisor == 0 && s.endsWith("7")) {
            return true;
        } else if (divisor == 0) {
            return true;
        } else return s.endsWith("7");
    }

    public static boolean checkDuck(long num) {
        String s = Long.toString(num);
        return s.contains("0") && !s.startsWith("0");
    }

    public static boolean checkPalindromic(long num) {
        String s = Long.toString(num);
        return s.equals(reverseString(s));
    }

    public static boolean checkGapful(long num) {
        String s = Long.toString(num);
        if (s.length() < 3) {
            return false;
        } else {
            char start = s.charAt(0);
            char end = s.charAt(s.length() - 1);
            int divisor = combineChars(start, end);
            return num % divisor == 0;
        }
    }

    public static boolean checkSpy(long num) {
        long r, mul = 1, sum = 0;
        while (num > 0) {
            r = num % 10;
            sum = sum + r;
            mul = mul * r;
            num = num / 10;
        }
        return mul == sum;
    }

    public static boolean checkSquare(long num) {
        double square = Math.sqrt(num);
        return ((square - Math.floor(square)) == 0);
    }

    public  static boolean checkSunny(long num) {
        return Math.sqrt(num + 1) % 1 == 0;
    }

    public static boolean checkJumping(long num) {
        boolean flag = true;
        while (num != 0) {
            long d1 = num % 10;
            num /= 10;
            if (num != 0) {
                long d2 = num % 10;
                if (Math.abs(d1 - d2) != 1) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    private static String reverseString(String s) {
        StringBuilder stringBuilder = new StringBuilder(s);
        stringBuilder.reverse();
        return stringBuilder.toString();
    }

    private static int combineChars(char start, char end) {
        String s = String.valueOf(start) + end;
        return Integer.parseInt(s);
    }

    public static boolean isNumber(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int isRequest(String input) {
        int counter = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                counter++;
            }
        }
        if (counter == 1) {
            return 1;
        } else if (counter == 2) {
            return 2;
        } else if (counter == 3) {
            return 3;
        } else if (counter > 3) {
            return 4;
        } return 0;
    }

    public enum Requests {
        BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, EVEN, ODD
    }

    public static boolean checkRequest(String request) {
        boolean flag = false;
        for (Requests requests : Requests.values()) {
            if (request.equalsIgnoreCase(requests.toString())) {
                flag = true;
            }
        }
        return flag;
    }

    public static boolean checkMultipleRequestPossibility(String requestOne, String requestTwo) {
        return requestOne.equalsIgnoreCase("even") && requestTwo.equalsIgnoreCase("odd")
                || requestOne.equalsIgnoreCase("odd") && requestTwo.equalsIgnoreCase("even")
                || requestOne.equalsIgnoreCase("duck") && requestTwo.equalsIgnoreCase("spy")
                || requestOne.equalsIgnoreCase("spy") && requestTwo.equalsIgnoreCase("duck")
                || requestOne.equalsIgnoreCase("sunny") && requestTwo.equalsIgnoreCase("square")
                || requestOne.equalsIgnoreCase("square") && requestTwo.equalsIgnoreCase("sunny");
    }

    public static void printProperties(long num) {
        System.out.println("\nProperties of " + num + "\n" +
                "        buzz: " + checkBuzz(num) + "\n" +
                "        duck: " + checkDuck(num) + "\n" +
                " palindromic: " + checkPalindromic(num) + "\n" +
                "      gapful: " + checkGapful(num) + "\n" +
                "         spy: " + checkSpy(num) + "\n" +
                "      square: " + checkSquare(num) + "\n" +
                "       sunny: " + checkSunny(num) +"\n" +
                "     jumping: " + checkJumping(num) + "\n" +
                "        even: " + checkEven(num) + "\n" +
                "         odd: " + checkOdd(num));
    }

    public static void printFollowingProperties(long num, int times) {
        System.out.println();
        for (int i = 0; i < times; i++) {
            String output = " is";
            if (checkBuzz(num)) {
                output += " buzz,";
            }
            if (checkDuck(num)) {
                output += " duck,";
            }
            if (checkPalindromic(num)) {
                output += " palindromic,";
            }
            if (checkGapful(num)) {
                output += " gapful,";
            }
            if (checkSpy(num)) {
                output += " spy,";
            }
            if (checkSquare(num)) {
                output += " square,";
            }
            if (checkSunny(num)) {
                output += " sunny,";
            }
            if (checkJumping(num)) {
                output += " jumping,";
            }
            if (checkEven(num)) {
                output += " even";
            }
            if (checkOdd(num)) {
                output += " odd";
            }
            System.out.println(num + output);
            num++;
        }
    }

    private static void printOutput(long num) {
        String output = " is";
        if (checkBuzz(num)) {
            output += " buzz,";
        }
        if (checkDuck(num)) {
            output += " duck,";
        }
        if (checkPalindromic(num)) {
            output += " palindromic,";
        }
        if (checkGapful(num)) {
            output += " gapful,";
        }
        if (checkSpy(num)) {
            output += " spy,";
        }
        if (checkSquare(num)) {
            output += " square,";
        }
        if (checkSunny(num)) {
            output += " sunny,";
        }
        if (checkJumping(num)) {
            output += " jumping,";
        }
        if (checkEven(num)) {
            output += " even";
        }
        if (checkOdd(num)) {
            output += " odd";
        }
        System.out.println(num + output);
    }

    public static void printRequestProperties(long num, int occurrence, String request) {
        System.out.println();
        int counter = 0;
        while (counter < occurrence) {
            if (request.equalsIgnoreCase("buzz")) {
                if (checkBuzz(num)) {
                    printOutput(num);
                    counter++;
                }
            } else if (request.equalsIgnoreCase("duck")) {
                if (checkDuck(num)) {
                    printOutput(num);
                    counter++;
                }
            } else if (request.equalsIgnoreCase("palindromic")) {
                if (checkPalindromic(num)) {
                    printOutput(num);
                    counter++;
                }
            } else if (request.equalsIgnoreCase("gapful")) {
                if (checkGapful(num)) {
                    printOutput(num);
                    counter++;
                }
            } else if (request.equalsIgnoreCase("spy")) {
                if (checkSpy(num)) {
                    printOutput(num);
                    counter++;
                }
            }
            if (request.equalsIgnoreCase("square")) {
                if (checkSquare(num)) {
                    printOutput(num);
                    counter++;
                }
            }
            if (request.equalsIgnoreCase("sunny")) {
                if (checkSunny(num)) {
                    printOutput(num);
                    counter++;
                }
            }
            if (request.equalsIgnoreCase("jumping")) {
                if (checkJumping(num)) {
                    printOutput(num);
                    counter++;
                }
            }
            if (request.equalsIgnoreCase("even")) {
                if (checkEven(num)) {
                    printOutput(num);
                    counter++;
                }
            }
            if (request.equalsIgnoreCase("odd")) {
                if (checkOdd(num)) {
                    printOutput(num);
                    counter++;
                }
            }
            num++;
        }
    }

    public static void printDoubleRequestProperties(long num, int occurrence, String requestOne, String requestTwo) {
        System.out.println();
        int counter = 0;
        boolean flagOne = false, flagTwo = false;
        while (counter < occurrence) {

            if (requestOne.equalsIgnoreCase("buzz")) {
                if (checkBuzz(num)) {
                    flagOne = true;
                }
            } else if (requestTwo.equalsIgnoreCase("buzz")) {
                if (checkBuzz(num)) {
                    flagTwo = true;
                }
            }

            if (requestOne.equalsIgnoreCase("duck")) {
                if (checkDuck(num)) {
                    flagOne = true;
                }
            } else if (requestTwo.equalsIgnoreCase("duck")) {
                if (checkDuck(num)) {
                    flagTwo = true;
                }
            }


            if (requestOne.equalsIgnoreCase("palindromic")) {
                if (checkPalindromic(num)) {
                    flagOne = true;
                }
            } else if (requestTwo.equalsIgnoreCase("palindromic")) {
                if (checkPalindromic(num)) {
                    flagTwo = true;
                }
            }

            if (requestOne.equalsIgnoreCase("gapful")) {
                if (checkGapful(num)) {
                    flagOne = true;
                }
            } else if (requestTwo.equalsIgnoreCase("gapful")) {
                if (checkGapful(num)) {
                    flagTwo = true;
                }
            }

            if (requestOne.equalsIgnoreCase("spy")) {
                if (checkSpy(num)) {
                    flagOne = true;
                }
            } else if (requestTwo.equalsIgnoreCase("spy")) {
                if (checkSpy(num)) {
                    flagTwo = true;
                }
            }

            if (requestOne.equalsIgnoreCase("square")) {
                if (checkSquare(num)) {
                    flagOne = true;
                }
            } else if (requestTwo.equalsIgnoreCase("square")) {
                if (checkSquare(num)) {
                    flagTwo = true;
                }
            }

            if (requestOne.equalsIgnoreCase("sunny")) {
                if (checkSunny(num)) {
                    flagOne = true;
                }
            } else if (requestTwo.equalsIgnoreCase("sunny")) {
                if (checkSunny(num)) {
                    flagTwo = true;
                }
            }

            if (requestOne.equalsIgnoreCase("jumping")) {
                if (checkJumping(num)) {
                    flagOne = true;
                }
            } else if (requestTwo.equalsIgnoreCase("jumping")) {
                if (checkJumping(num)) {
                    flagTwo = true;
                }
            }

            if (requestOne.equalsIgnoreCase("even")) {
                if (checkEven(num)) {
                    flagOne = true;
                }
            } else if (requestTwo.equalsIgnoreCase("even")) {
                if (checkEven(num)) {
                    flagTwo = true;
                }
            }

            if (requestOne.equalsIgnoreCase("odd")) {
                if(checkOdd(num)) {
                    flagOne = true;
                }
            } else if (requestTwo.equalsIgnoreCase("odd")) {
                if(checkOdd(num)) {
                    flagTwo = true;
                }
            }

            if (flagOne && flagTwo) {
                printOutput(num);
                counter++;
            }
            num++;
            flagOne = false;
            flagTwo = false;
        }
    }

}
