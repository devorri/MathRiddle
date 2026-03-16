package com.example.trial.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RiddleBank {

    public static List<Riddle> getRiddles(String category, String difficulty) {
        List<Riddle> all = new ArrayList<>();

        switch (category) {
            case "Arithmetic":
                addArithmeticRiddles(all, difficulty);
                break;
            case "Logic":
                addLogicRiddles(all, difficulty);
                break;
            case "Patterns":
                addPatternRiddles(all, difficulty);
                break;
            case "Word Problems":
                addWordProblemRiddles(all, difficulty);
                break;
        }

        Collections.shuffle(all);
        return all.subList(0, Math.min(5, all.size()));
    }

    public static List<Riddle> getRandomRiddles() {
        List<Riddle> all = new ArrayList<>();
        addArithmeticRiddles(all, "easy");
        addArithmeticRiddles(all, "medium");
        addLogicRiddles(all, "easy");
        addLogicRiddles(all, "medium");
        addPatternRiddles(all, "easy");
        addPatternRiddles(all, "medium");
        addWordProblemRiddles(all, "easy");
        addWordProblemRiddles(all, "medium");
        Collections.shuffle(all);
        return all.subList(0, Math.min(5, all.size()));
    }

    private static void addArithmeticRiddles(List<Riddle> list, String difficulty) {
        switch (difficulty) {
            case "easy":
                list.add(new Riddle("What is 15 + 27?", new String[]{"40", "42", "44", "38"}, 1, "Arithmetic", "easy"));
                list.add(new Riddle("What is 8 × 7?", new String[]{"54", "56", "58", "48"}, 1, "Arithmetic", "easy"));
                list.add(new Riddle("What is 100 - 37?", new String[]{"67", "63", "73", "57"}, 1, "Arithmetic", "easy"));
                list.add(new Riddle("What is 144 ÷ 12?", new String[]{"11", "12", "13", "14"}, 1, "Arithmetic", "easy"));
                list.add(new Riddle("What is 25 + 38?", new String[]{"63", "53", "73", "65"}, 0, "Arithmetic", "easy"));
                break;
            case "medium":
                list.add(new Riddle("What is 17 × 13?", new String[]{"201", "221", "211", "231"}, 1, "Arithmetic", "medium"));
                list.add(new Riddle("What is 256 ÷ 16?", new String[]{"14", "15", "16", "18"}, 2, "Arithmetic", "medium"));
                list.add(new Riddle("What is 345 + 678?", new String[]{"1013", "1023", "1033", "1003"}, 1, "Arithmetic", "medium"));
                list.add(new Riddle("What is 1000 - 387?", new String[]{"623", "617", "613", "603"}, 2, "Arithmetic", "medium"));
                list.add(new Riddle("What is 15²?", new String[]{"215", "225", "235", "245"}, 1, "Arithmetic", "medium"));
                break;
            case "hard":
                list.add(new Riddle("What is 23 × 47?", new String[]{"1071", "1081", "1091", "1061"}, 1, "Arithmetic", "hard"));
                list.add(new Riddle("What is √2025?", new String[]{"43", "44", "45", "46"}, 2, "Arithmetic", "hard"));
                list.add(new Riddle("What is 7³?", new String[]{"333", "343", "353", "363"}, 1, "Arithmetic", "hard"));
                list.add(new Riddle("What is 999 × 3?", new String[]{"2977", "2987", "2997", "3007"}, 2, "Arithmetic", "hard"));
                list.add(new Riddle("What is 12! ÷ 11!?", new String[]{"10", "11", "12", "13"}, 2, "Arithmetic", "hard"));
                break;
        }
    }

    private static void addLogicRiddles(List<Riddle> list, String difficulty) {
        switch (difficulty) {
            case "easy":
                list.add(new Riddle("If all roses are flowers and some flowers fade quickly, can we conclude all roses fade quickly?", new String[]{"Yes", "No", "Maybe", "Not enough info"}, 1, "Logic", "easy"));
                list.add(new Riddle("What comes next: 2, 4, 6, 8, ?", new String[]{"9", "10", "12", "11"}, 1, "Logic", "easy"));
                list.add(new Riddle("If A > B and B > C, which is smallest?", new String[]{"A", "B", "C", "Cannot tell"}, 2, "Logic", "easy"));
                list.add(new Riddle("How many months have 28 days?", new String[]{"1", "6", "12", "None"}, 2, "Logic", "easy"));
                list.add(new Riddle("If you have 3 apples and take away 2, how many do YOU have?", new String[]{"1", "2", "3", "0"}, 1, "Logic", "easy"));
                break;
            case "medium":
                list.add(new Riddle("A bat and ball cost $1.10. The bat costs $1 more than the ball. What does the ball cost?", new String[]{"$0.10", "$0.05", "$0.15", "$0.20"}, 1, "Logic", "medium"));
                list.add(new Riddle("If 5 machines make 5 widgets in 5 min, how long for 100 machines to make 100 widgets?", new String[]{"100 min", "5 min", "20 min", "50 min"}, 1, "Logic", "medium"));
                list.add(new Riddle("Which number doesn't belong: 2, 5, 11, 17, 23, 29?", new String[]{"2", "5", "11", "All prime"}, 3, "Logic", "medium"));
                list.add(new Riddle("I am an odd number. Take away one letter and I become even. What am I?", new String[]{"3", "5", "7 (Seven)", "9"}, 2, "Logic", "medium"));
                list.add(new Riddle("A farmer has 17 sheep. All but 9 run away. How many are left?", new String[]{"8", "9", "17", "0"}, 1, "Logic", "medium"));
                break;
            case "hard":
                list.add(new Riddle("Three people check into a hotel room that costs $30. They each pay $10. The clerk realizes it should be $25 and gives $5 back to the bellboy. The bellboy keeps $2 and gives $1 to each person. Each person paid $9 (×3 = $27) + $2 tip = $29. Where's the extra $1?", new String[]{"With the clerk", "With the bellboy", "Nowhere, the math is flawed", "Lost"}, 2, "Logic", "hard"));
                list.add(new Riddle("If you rearrange 'CIFAIPC', you get:", new String[]{"SPECIFIC", "PACIFIC", "TYPICAL", "TOPICAL"}, 1, "Logic", "hard"));
                list.add(new Riddle("What is the next number: 1, 11, 21, 1211, 111221, ?", new String[]{"312211", "122111", "212211", "111222"}, 0, "Logic", "hard"));
                list.add(new Riddle("A clock shows 3:15. What is the angle between the hour and minute hands?", new String[]{"0°", "7.5°", "15°", "22.5°"}, 1, "Logic", "hard"));
                list.add(new Riddle("You have 12 balls. One is different weight. You have a balance scale. Minimum weighings to find the odd ball?", new String[]{"2", "3", "4", "5"}, 1, "Logic", "hard"));
                break;
        }
    }

    private static void addPatternRiddles(List<Riddle> list, String difficulty) {
        switch (difficulty) {
            case "easy":
                list.add(new Riddle("What comes next: 1, 4, 9, 16, ?", new String[]{"20", "24", "25", "36"}, 2, "Patterns", "easy"));
                list.add(new Riddle("What comes next: 3, 6, 12, 24, ?", new String[]{"36", "48", "30", "42"}, 1, "Patterns", "easy"));
                list.add(new Riddle("What comes next: 1, 1, 2, 3, 5, ?", new String[]{"7", "8", "6", "9"}, 1, "Patterns", "easy"));
                list.add(new Riddle("Complete: 10, 20, 30, 40, ?", new String[]{"45", "50", "55", "60"}, 1, "Patterns", "easy"));
                list.add(new Riddle("What comes next: A1, B2, C3, D?", new String[]{"4", "5", "3", "6"}, 0, "Patterns", "easy"));
                break;
            case "medium":
                list.add(new Riddle("What comes next: 2, 6, 18, 54, ?", new String[]{"108", "162", "72", "216"}, 1, "Patterns", "medium"));
                list.add(new Riddle("What comes next: 1, 8, 27, 64, ?", new String[]{"100", "125", "216", "81"}, 1, "Patterns", "medium"));
                list.add(new Riddle("What comes next: 0, 1, 1, 2, 3, 5, 8, ?", new String[]{"11", "12", "13", "10"}, 2, "Patterns", "medium"));
                list.add(new Riddle("What's the pattern? 2, 3, 5, 7, 11, ?", new String[]{"12", "13", "14", "15"}, 1, "Patterns", "medium"));
                list.add(new Riddle("What comes next: 1, 4, 27, 256, ?", new String[]{"625", "3125", "1024", "3025"}, 1, "Patterns", "medium"));
                break;
            case "hard":
                list.add(new Riddle("What comes next: 1, 2, 6, 24, 120, ?", new String[]{"240", "600", "720", "840"}, 2, "Patterns", "hard"));
                list.add(new Riddle("What comes next: 2, 5, 10, 17, 26, ?", new String[]{"35", "37", "36", "38"}, 1, "Patterns", "hard"));
                list.add(new Riddle("Find the missing: 3, 5, 9, 17, ?, 65", new String[]{"29", "31", "33", "35"}, 2, "Patterns", "hard"));
                list.add(new Riddle("What comes next: 1, 3, 7, 15, 31, ?", new String[]{"47", "55", "63", "61"}, 2, "Patterns", "hard"));
                list.add(new Riddle("Next in sequence: 2, 12, 36, 80, 150, ?", new String[]{"252", "240", "220", "280"}, 0, "Patterns", "hard"));
                break;
        }
    }

    private static void addWordProblemRiddles(List<Riddle> list, String difficulty) {
        switch (difficulty) {
            case "easy":
                list.add(new Riddle("Sarah has 15 candies. She gives 7 to Tom. How many does she have left?", new String[]{"7", "8", "9", "6"}, 1, "Word Problems", "easy"));
                list.add(new Riddle("A bus has 23 passengers. 8 get off and 5 get on. How many now?", new String[]{"18", "19", "20", "21"}, 2, "Word Problems", "easy"));
                list.add(new Riddle("If a pizza is cut into 8 slices and you eat 3, what fraction is left?", new String[]{"3/8", "5/8", "1/2", "3/4"}, 1, "Word Problems", "easy"));
                list.add(new Riddle("A toy costs $5. If you have $20, how many toys can you buy?", new String[]{"3", "4", "5", "6"}, 1, "Word Problems", "easy"));
                list.add(new Riddle("You read 12 pages a day. How many days to finish a 60-page book?", new String[]{"4", "5", "6", "7"}, 1, "Word Problems", "easy"));
                break;
            case "medium":
                list.add(new Riddle("A train travels 60 km/h for 2.5 hours. How far does it go?", new String[]{"120 km", "140 km", "150 km", "160 km"}, 2, "Word Problems", "medium"));
                list.add(new Riddle("If 3 workers paint a house in 6 days, how long for 6 workers?", new String[]{"2 days", "3 days", "4 days", "12 days"}, 1, "Word Problems", "medium"));
                list.add(new Riddle("A shirt is 20% off from $45. What's the sale price?", new String[]{"$32", "$34", "$36", "$38"}, 2, "Word Problems", "medium"));
                list.add(new Riddle("The sum of three consecutive numbers is 72. What is the largest?", new String[]{"23", "24", "25", "26"}, 2, "Word Problems", "medium"));
                list.add(new Riddle("A rectangle has area 48 cm² and width 6 cm. What is the perimeter?", new String[]{"22 cm", "24 cm", "28 cm", "32 cm"}, 2, "Word Problems", "medium"));
                break;
            case "hard":
                list.add(new Riddle("Two trains are 300 km apart, heading toward each other at 70 and 80 km/h. When do they meet?", new String[]{"1 hr", "1.5 hr", "2 hr", "2.5 hr"}, 2, "Word Problems", "hard"));
                list.add(new Riddle("A tank fills in 6 hours by pipe A and empties in 8 hours by pipe B. If both are open, how long to fill?", new String[]{"12 hr", "18 hr", "24 hr", "48 hr"}, 2, "Word Problems", "hard"));
                list.add(new Riddle("Compound interest: $1000 at 10% for 2 years compounded annually. Total amount?", new String[]{"$1200", "$1210", "$1100", "$1220"}, 1, "Word Problems", "hard"));
                list.add(new Riddle("The average of 5 numbers is 20. If one number is removed, the average becomes 18. What was removed?", new String[]{"24", "26", "28", "30"}, 2, "Word Problems", "hard"));
                list.add(new Riddle("A ladder 10m long leans against a wall. Its foot is 6m from the wall. How high does it reach?", new String[]{"6m", "7m", "8m", "9m"}, 2, "Word Problems", "hard"));
                break;
        }
    }
}
