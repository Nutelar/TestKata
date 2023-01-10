import java.util.Arrays;
import java.util.Scanner;

public class Calc {
    public static void main(String[] args) throws ActionExeption, ArrayIndexOutOfBoundsException {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите операцию:");
        String inputString = in.nextLine();
        String[] action = {"+", "-", "*", "/"}; //массив для определения операции
        String[] splitAction = {"\\+", "-", "\\*", "/"}; // экранирование для сплита
        String[] number; //массив для хранения чисел после сплита
        int finalsum =0;
        int arop = -1;
        int countarop = 0;
        int countroman = 0;
        int a,b=4000; // Римские числа до 3999

        if(inputString.length()==0){
            throw new ActionExeption("Введена пустая строка");
        }
        for (int i = 0; i < action.length; i++) {
            if (inputString.contains(action[i])) {    //определяем операцию
                countarop++;
                if (countarop <= 1) {
                    arop = i;
                }
            }
        }
        try {
            number = inputString.split(splitAction[arop]);    //разделение строки на числа
            //System.out.println(Arrays.toString(number));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Входные данные не являются математической операцией - неизвестная операция");
        }
        if(number.length==0){
            throw new ArrayIndexOutOfBoundsException("Входные данные не являются математической операцией - не введены числа");
        }

        if (arop == -1) {                                       //проверка на наличие операции
            throw new ActionExeption("Неверная операция 2");
        } else if (countarop > 1 | number.length > 2) {               //проверка нескольких операций
            throw new ArrayIndexOutOfBoundsException("Неверный формат математической операции  - несколько операций (+, -, /, *)");
        } //else {
            //System.out.println("Операция " + action[arop]); }



        try {
            if (isRoman(number[0].toUpperCase()) == true & isRoman(number[1].toUpperCase()) == true) { //Проверяем оба ли римские
                // System.out.println("Оба римских");
                a = ConvRomeToArab(number[0]);
                b = ConvRomeToArab(number[1]);
                countroman = 1;
                //System.out.println("Roman a="+a+" b="+b);
            } else if (isArab(number) == true) {
                // System.out.println("Оба арабских");
                a = Integer.parseInt(number[0]);
                b = Integer.parseInt(number[1]);
                // System.out.println("arab a="+a+" b="+b);
            } else
                throw new ActionExeption("Неверный формат операции: разные системы исчисления, нецелые числа или неизвестная операция");

            if (0<a&a<=10&0<b&b<=10){               //Сравниваем размер чисел и выполняем операцию
                switch (arop){
                     case 0:
                    finalsum=a+b; break;
                     case 1:
                    finalsum=a-b; break;
                     case 2:
                    finalsum=a*b; break;
                    case 3:
                    finalsum=a/b; break;
                     default:
                    System.out.println("Нет математической операции"); break;
                }
            } else {
            throw new ActionExeption("Числа должны быть больше 0 и меньше или равны 10(первое число: "+a+"; второе число: "+ b+")");
            }

        } catch (ArrayIndexOutOfBoundsException e){
            throw new ActionExeption("Неверный формат операции: не введено второе число");
        }

        //Проверка на отрицательное при римских
        if (finalsum<0&countroman==1){
            throw new ActionExeption("Результат с римскими числами не может быть меньше 0");
        }

        //Вывод результата в зависимости от типа исчиления
        if(countroman>0){
            System.out.println(ConverToRome(finalsum));
        }else System.out.println("Результат: "+finalsum);



        //main end
    }

    public static boolean isRoman(String s) {
        char[] s1 = s.toCharArray();
        for (int i = 0; i < s1.length; i++) {
            try {
                Roman.valueOf(String.valueOf(s1[i])).getValue();
                //посимвольно сравниваем все ли буквы являются римскими числами
               // System.out.println(s1[i]);
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return true;
    }


    public static boolean isArab(String s[]) {
        int[] s1 = new int[2];
        for(int i=0;i<s1.length;i++){
            try {
                s1[i]=Integer.parseInt(s[i]);
            } catch (NumberFormatException e){
                return false;
            }
        }
        return true;
    }

    public static int ConvRomeToArab(String s) {
        char[] c1 = s.toUpperCase().toCharArray();
        int l = c1.length - 1;
        int arab = 0;
        int sum=Roman.valueOf(String.valueOf(c1[c1.length-1])).getValue();//последняя или же единственная буква
        for (int i = l-1; i >= 0; i--) {
            arab = Roman.valueOf(String.valueOf(c1[i])).getValue();   //для преобразования идем справа налево
            // System.out.println("arab before if=" + arab +";sum = "+sum);
            try {
                if (arab < Roman.valueOf(String.valueOf(c1[i+1])).getValue()) {      //сравниваем правый с левым
                    sum -= arab;
                } else {
                    sum += arab;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // System.out.println("arab in catch="+arab+"; sum="+sum);
                if (arab < Roman.valueOf(String.valueOf(c1[i + 1])).getValue()) {   //для 1 буквы
                    sum = Math.abs(sum) - arab;
                } else {
                    sum = Math.abs(sum) + arab;
                }
            }
        }

        return sum;
    }

    public static String ConverToRome(int res){
        String romanstring="";
        int l = RomanConv.values().length;
        RomanConv j;
        if(res==0){
            return "0";
        }
        while (l>0){
            j=RomanConv.values()[l-1];
            if(res>=j.getValue()){
                romanstring=romanstring.concat(j.name());
                res = res- j.getValue();
            }else {
                l--;
            }
        }
        return romanstring;
    }

}
