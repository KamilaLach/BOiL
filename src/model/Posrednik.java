package model;

import java.util.*;

public class Posrednik {
    public List<Integer> popyt;  //2
    public List<Integer> podaż;  //4
    public List<Integer> kosztZakupu;
    public List<List<Integer>> kosztTransportu;
    public List<Integer> zyskSprzedaży;
    public List<Integer> alpha;
    public List<Integer> beta;
    public List<ArrayList<Integer>> zysk_jednostkowy;
    public List<ArrayList<Integer>> delty;
    public List<List<Integer>> nullArray;

    public Posrednik() {
        //przyklad rozwiazania optymalnego w pierwszej iteracji  gdzie popyt 2 i podaz 4
        this.podaż = new ArrayList<Integer>(Arrays.asList(15,20,25,30));
        this.popyt = new ArrayList<Integer>(Arrays.asList(10,28));
        this.kosztZakupu = new ArrayList<Integer>(Arrays.asList(6,7,8,9));
        this.zyskSprzedaży = new ArrayList<Integer>(Arrays.asList(22,25));
        this.kosztTransportu = new ArrayList<List<Integer>>(Arrays.asList(new ArrayList<Integer>(Arrays.asList(8,14)),new ArrayList<Integer>(Arrays.asList(17,12)),new ArrayList<Integer>(Arrays.asList(9,19)),new ArrayList<Integer>(Arrays.asList(4,8))));
        this.alpha = new ArrayList<Integer>(Arrays.asList(0,null,null,null,null));
        this.beta = new ArrayList<Integer>(Arrays.asList(null,null,null));
        this.delty = new ArrayList<>(Arrays.asList(new ArrayList<Integer>(Arrays.asList(new Integer[3])),new ArrayList<Integer>(Arrays.asList(new Integer[3])),new ArrayList<Integer>(Arrays.asList(new Integer[3])),new ArrayList<Integer>(Arrays.asList(new Integer[3])),new ArrayList<Integer>(Arrays.asList(new Integer[3]))));

        //przyklad rozwiazania gdzie popyt 3 i podaz 2 - zadanie z cwiczen
//        this.podaż = new ArrayList<Integer>(Arrays.asList(20,30));
//        this.popyt = new ArrayList<Integer>(Arrays.asList(10,28,27));
//        this.kosztZakupu = new ArrayList<Integer>(Arrays.asList(10,12));
//        this.zyskSprzedaży = new ArrayList<Integer>(Arrays.asList(30,25,30));
//        this.kosztTransportu = new ArrayList<List<Integer>>(Arrays.asList(new ArrayList<Integer>(Arrays.asList(8,14,17)),new ArrayList<Integer>(Arrays.asList(12,9,19))));
//        this.alpha = new ArrayList<Integer>(Arrays.asList(0,null,null));
//        this.beta = new ArrayList<Integer>(Arrays.asList(null,null,null,null));
//        this.delty = new ArrayList<>(Arrays.asList(new ArrayList<Integer>(Arrays.asList(new Integer[4])),new ArrayList<Integer>(Arrays.asList(new Integer[4])),new ArrayList<Integer>(Arrays.asList(new Integer[4]))));
    }

    public void calculate_profit_jednostkowy() {
//         do przykladu rozwiazania optymalnego w pierwszej iteracji  gdzie popyt 2 i podaz 4
        zysk_jednostkowy = new ArrayList<>(Arrays.asList(new ArrayList<Integer>(Arrays.asList(new Integer[2])),new ArrayList<Integer>(Arrays.asList(new Integer[2])),new ArrayList<Integer>(Arrays.asList(new Integer[2])),new ArrayList<Integer>(Arrays.asList(new Integer[2]))));

        // do przykladu rozwiazania gdzie popyt 3 i podaz 2 - zadanie z cwiczen
//        zysk_jednostkowy = new ArrayList<>(Arrays.asList(new ArrayList<Integer>(Arrays.asList(new Integer[3])),new ArrayList<Integer>(Arrays.asList(new Integer[3]))));

        for (int i = 0; i < zysk_jednostkowy.size(); i++) {
            for (int j = 0; j < zysk_jednostkowy.get(i).size(); j++) {
                zysk_jednostkowy.get(i).set(j,zyskSprzedaży.get(j)-kosztZakupu.get(i)-kosztTransportu.get(i).get(j));
            }
        }
        System.out.println("Zysk jednostkowy: ");
        System.out.println(zysk_jednostkowy);
    }

    public void max_matrix() {
        // do przykladu rozwiazania optymalnego w pierwszej iteracji  gdzie popyt 2 i podaz 4
        nullArray = new ArrayList<>(Arrays.asList(new ArrayList<Integer>(Arrays.asList(new Integer[3])),new ArrayList<Integer>(Arrays.asList(new Integer[3])),new ArrayList<Integer>(Arrays.asList(new Integer[3])),new ArrayList<Integer>(Arrays.asList(new Integer[3])),new ArrayList<Integer>(Arrays.asList(new Integer[3]))));

        //do przykladu rozwiazania gdzie popyt 3 i podaz 2 - zadanie z cwiczen
//        nullArray = new ArrayList<>(Arrays.asList(new ArrayList<Integer>(Arrays.asList(new Integer[4])),new ArrayList<Integer>(Arrays.asList(new Integer[4])),new ArrayList<Integer>(Arrays.asList(new Integer[4]))));

        Set<Integer> readyX = new HashSet<>();
        Set<Integer> readyY = new HashSet<>();

        int sumpod = podaż.stream().mapToInt(Integer::intValue).sum();
        int sumpop = popyt.stream().mapToInt(Integer::intValue).sum();
        boolean onceFOD = true;
        do {
            Integer maxPrice = null;
            int x = 0, y = 0;
            for (int i = 0; i < podaż.size(); i++) {
                if (readyX.contains(i))
                    continue;

                for (int j = 0; j < popyt.size(); j++) {
                    if (readyY.contains(j))
                        continue;

                    if (maxPrice == null) {
                        maxPrice = zysk_jednostkowy.get(i).get(j);
                        x = i;
                        y = j;
                    } else if (maxPrice < zysk_jednostkowy.get(i).get(j) && zysk_jednostkowy.get(i).get(j) > 0) {
                        maxPrice = zysk_jednostkowy.get(i).get(j);
                        x = i;
                        y = j;
                    }

                }
            }
            if (podaż.get(x) > popyt.get(y)) {
                nullArray.get(x).set(y, popyt.get(y));
                podaż.set(x, podaż.get(x) - popyt.get(y));
                popyt.set(y, 0);
            } else if (podaż.get(x) < popyt.get(y)) {
                nullArray.get(x).set(y, podaż.get(x));
                popyt.set(y, popyt.get(y) - podaż.get(x));
                podaż.set(x, 0);
            } else {
                nullArray.get(x).set(y, podaż.get(x));
                popyt.set(y, 0);
                podaż.set(x, 0);
            }

            if (podaż.get(x) == 0)
                readyX.add(x);

            if (popyt.get(y) == 0)
                readyY.add(y);

            if ((podaż.stream().mapToInt(Integer::intValue).sum() == 0 || popyt.stream().mapToInt(Integer::intValue).sum() == 0) && onceFOD) {
                zysk_jednostkowy.add(new ArrayList<>(Arrays.asList(new Integer[]{0, 0, 0})));
                for (int i = 0; i < zysk_jednostkowy.size(); i++) {
                    zysk_jednostkowy.get(i).add(0);
                }
                popyt.add(sumpod);
                podaż.add(sumpop);
                onceFOD = false;
            }

        } while (readyX.size() < podaż.size() || readyY.size() < popyt.size());

        System.out.println("Tabela kosztów: ");
        System.out.println(nullArray);

        for (int i = 0; i < podaż.size(); i++) {
            for (int j = 0; j < popyt.size(); j++) {
                if(nullArray.get(i).get(j) == null){
                    nullArray.get(i).set(j, 0);
                }
            }
        }

        System.out.println("Tabela kosztów z zamienionymi wartosciami null na zero: " + nullArray);

    }

    public void calculate_alpha_beta() {
        alpha.set(0, 0); //alpha1 = 0
        int temp = 0;
        for (int i = 0; i < podaż.size(); i++) {
            for (int j = 0; j < popyt.size(); j++) {
                if(i == 0){
                    alpha.set(0, 0); //alpha1 = 0
                }
                else {
                    alpha.set(i, null);
                    beta.set(j, null);
                }
            }
        }

        do{
            for (int i = 0; i < podaż.size(); i++) {
                for (int j = 0; j < popyt.size(); j++) {
                    if(nullArray.get(i).get(j) != 0 && alpha.get(i) == null && beta.get(j) != null ) { //jezeli obliczone wartosci nie wynosza zero, to liczymy węzły bazowe i przyjmujemy ze alpha1 = 0
                        alpha.set(i, zysk_jednostkowy.get(i).get(j) - beta.get(j));
                    }
                    else if(nullArray.get(i).get(j) != 0 && alpha.get(i) != null && beta.get(j) == null ) { //jezeli obliczone wartosci nie wynosza zero, to liczymy węzły bazowe i przyjmujemy ze alpha1 = 0
                        beta.set(j, zysk_jednostkowy.get(i).get(j) - alpha.get(i));
                    }
                }
            }
            temp = temp + 1;
        } while (temp < 100);

        for (int i = 0; i < podaż.size(); i++) {
            for (int j = 0; j < popyt.size(); j++) {
                if (alpha.get(i) == null || beta.get(j) == null){
                    System.out.println("Nie można policzyć bet/alf - popraw dane");
                    System.exit(1);
                }
            }
        }

        System.out.println("Alfy: ");
        System.out.println(alpha);
        System.out.println("Bety: ");
        System.out.println(beta);
    }

    public void calculate_tabela_wskaznikow() {
        for (int i = 0; i < podaż.size(); i++) {
            for (int j = 0; j < popyt.size(); j++) {
                if(nullArray.get(i).get(j) == 0 ){ //jezeli obliczone wartosci wynosza 0, to nie sa to wezly bazowe i liczymy delte, w przeciwnym razie delta = 0
                    delty.get(i).set(j, zysk_jednostkowy.get(i).get(j) - alpha.get(i) - beta.get(j));
                }
                else {
                    delty.get(i).set(j,0);
                }
            }
        }

        System.out.println("Tabela wskaźników optymalności: ");
        System.out.println(delty);
    }

    public void calculate_if_optimal() {
        int count = 0;
        int min = 0;
        int temp = 0;
        int newMax = 0;
        int newi = 0;
        int newj = 0;
        //sprawdzenie czy rozwiazanie jest optymalne, czyli czy wartosci w tabeli optymalnosci są ujemne lub równe zero
        for (int i = 0; i < podaż.size(); i++) {
            for (int j = 0; j < popyt.size(); j++) {
                if( delty.get(i).get(j) > 0 ){ //jezeli wartosci w tabeli optymalnosci są ujemne lub równe zero rozwiazanie jest optymalne
                    count = count + 1;
                    if(newMax < delty.get(i).get(j)){
                        newMax = delty.get(i).get(j);
                        newi = i;
                        newj = j;
                    }
                }
            }
        }
        System.out.println("W tabeli wskaźnikow jest: " + count + " wartość/-ci większa/-e od zera");

        if(count == 0){
            System.out.println("rozwiązanie optymalne");
            System.exit(1);
        }
        else System.out.println("rozwiązanie nieoptymalne");

//        System.out.println("NEW MAX " + newMax);
//        System.out.println("NEW I " + newi);
//        System.out.println("NEW j " + newj);
        System.out.println("-------------------------------------------------------------------------------------------");


        do {
            temp = 0;
//            count = temp;
            for (int i = 0; i < podaż.size(); i++) {
                for (int j = 0; j < popyt.size(); j++) {
                    if (i == newi && j == newj) { //jeśli ta wartosc jest wieksza od zera, to w nullArray bedzie zerem - szukamy innej minimalnej
                        System.out.println("Tabela kosztów przed zmianą: " + nullArray);
                        if(i <= podaż.size() - 1 && j == popyt.size() - 1) {
                            if(nullArray.get(i).get(j) == 0) {
                                if (nullArray.get(i).get(j) == 0 && nullArray.get(i).get(j - 1) != 0) {
                                    min = nullArray.get(i).get(j - 1);
                                    if (nullArray.get(i + 1).get(j - 1) < min && nullArray.get(i + 1).get(j - 1) != 0) {
                                        min = nullArray.get(i + 1).get(j - 1);
                                        if (nullArray.get(i + 1).get(j) < min && nullArray.get(i + 1).get(j) != 0) {
                                            min = nullArray.get(i + 1).get(j);
                                        }
                                    } else if (nullArray.get(i + 1).get(j) < min && nullArray.get(i + 1).get(j) != 0) {
                                        min = nullArray.get(i + 1).get(j);
                                        if (nullArray.get(i + 1).get(j - 1) < min && nullArray.get(i + 1).get(j - 1) != 0) {
                                            min = nullArray.get(i + 1).get(j - 1);
                                        }
                                    }
                                } else if (nullArray.get(i).get(j) == 0 && nullArray.get(i + 1).get(j - 1) != 0) {
                                    min = nullArray.get(i + 1).get(j - 1);
                                    if (nullArray.get(i).get(j - 1) < min && nullArray.get(i).get(j - 1) != 0) {
                                        min = nullArray.get(i).get(j - 1);
                                        if (nullArray.get(i + 1).get(j) < min && nullArray.get(i + 1).get(j) != 0) {
                                            min = nullArray.get(i + 1).get(j);
                                        }
                                    } else if (nullArray.get(i + 1).get(j) < min && nullArray.get(i + 1).get(j) != 0) {
                                        min = nullArray.get(i + 1).get(j);
                                        if (nullArray.get(i).get(j - 1) < min && nullArray.get(i).get(j - 1) != 0) {
                                            min = nullArray.get(i).get(j - 1);
                                        }
                                    }
                                } else if(nullArray.get(i).get(j) == 0 && nullArray.get(i + 1).get(j) != 0){
                                    min = nullArray.get(i + 1).get(j);
                                    if (nullArray.get(i).get(j - 1) < min && nullArray.get(i).get(j - 1) != 0) {
                                        min = nullArray.get(i).get(j - 1);
                                        if (nullArray.get(i + 1).get(j - 1) < min && nullArray.get(i + 1).get(j - 1) != 0) {
                                            min = nullArray.get(i + 1).get(j - 1);
                                        }
                                    } else if (nullArray.get(i + 1).get(j - 1) < min && nullArray.get(i + 1).get(j - 1) != 0) {
                                        min = nullArray.get(i + 1).get(j - 1);
                                        if (nullArray.get(i).get(j - 1) < min && nullArray.get(i).get(j - 1) != 0) {
                                            min = nullArray.get(i).get(j - 1);
                                        }
                                    }
                                }
                            }
                            else {
                                min = nullArray.get(i).get(j);
                                if (nullArray.get(i + 1).get(j - 1) < min && nullArray.get(i + 1).get(j - 1) != 0) {
                                    min = nullArray.get(i + 1).get(j - 1);
                                    if (nullArray.get(i + 1).get(j) < min && nullArray.get(i + 1).get(j) != 0) {
                                        min = nullArray.get(i + 1).get(j);
                                        if (nullArray.get(i).get(j - 1) < min && nullArray.get(i).get(j - 1) != 0) {
                                            min = nullArray.get(i).get(j - 1);
                                        }
                                    } else if (nullArray.get(i).get(j - 1) < min && nullArray.get(i).get(j - 1) != 0) {
                                        min = nullArray.get(i).get(j - 1);
                                        if (nullArray.get(i + 1).get(j) < min && nullArray.get(i + 1).get(j) != 0) {
                                            min = nullArray.get(i + 1).get(j);
                                        }
                                    }
                                } else if (nullArray.get(i + 1).get(j) < min && nullArray.get(i + 1).get(j) != 0) {
                                    min = nullArray.get(i + 1).get(j);
                                    if (nullArray.get(i).get(j - 1) < min && nullArray.get(i).get(j - 1) != 0) {
                                        min = nullArray.get(i).get(j - 1);
                                        if (nullArray.get(i + 1).get(j - 1) < min && nullArray.get(i + 1).get(j - 1) != 0) {
                                            min = nullArray.get(i + 1).get(j - 1);
                                        }
                                    }else if(nullArray.get(i + 1).get(j - 1) < min && nullArray.get(i + 1).get(j - 1) != 0){
                                        min = nullArray.get(i + 1).get(j - 1);
                                        if(nullArray.get(i).get(j - 1) < min && nullArray.get(i).get(j - 1) != 0){
                                            min = nullArray.get(i).get(j - 1);
                                        }
                                    }
                                } else if (nullArray.get(i).get(j - 1) < min && nullArray.get(i).get(j - 1) != 0) {
                                    min = nullArray.get(i).get(j - 1);
                                    if (nullArray.get(i + 1).get(j - 1) < min && nullArray.get(i + 1).get(j - 1) != 0) {
                                        min = nullArray.get(i + 1).get(j - 1);
                                        if (nullArray.get(i + 1).get(j) < min && nullArray.get(i + 1).get(j) != 0) {
                                            min = nullArray.get(i + 1).get(j);
                                        }
                                    }else if(nullArray.get(i + 1).get(j - 1) < min && nullArray.get(i + 1).get(j - 1) != 0){
                                        min = nullArray.get(i + 1).get(j - 1);
                                        if(nullArray.get(i + 1).get(j) < min && nullArray.get(i + 1).get(j) != 0){
                                            min = nullArray.get(i + 1).get(j);
                                        }
                                    }
                                }
                            }

                            System.out.println("Minimum w cyklu wynosi: " + min);
                            nullArray.get(i).set(j, nullArray.get(i).get(j) + min);
                            nullArray.get(i).set(j - 1, nullArray.get(i).get(j - 1) - min);
                            nullArray.get(i + 1).set(j, nullArray.get(i + 1).get(j) - min);
                            nullArray.get(i + 1).set(j - 1, nullArray.get(i + 1).get(j - 1) + min);
                        }


                        else if(i == podaż.size() - 1 && j <= popyt.size() - 1){
                            if(nullArray.get(i).get(j) == 0) {
                                if (nullArray.get(i).get(j) == 0 && nullArray.get(i).get(j + 1) != 0) {
                                    min = nullArray.get(i).get(j + 1);
                                    if (nullArray.get(i - 1).get(j + 1) < min && nullArray.get(i - 1).get(j + 1) != 0) {
                                        min = nullArray.get(i - 1).get(j + 1);
                                        if (nullArray.get(i - 1).get(j) < min && nullArray.get(i - 1).get(j) != 0) {
                                            min = nullArray.get(i - 1).get(j);
                                        }
                                    } else if (nullArray.get(i - 1).get(j) < min && nullArray.get(i - 1).get(j) != 0) {
                                        min = nullArray.get(i - 1).get(j);
                                        if (nullArray.get(i - 1).get(j + 1) < min && nullArray.get(i - 1).get(j + 1) != 0) {
                                            min = nullArray.get(i - 1).get(j + 1);
                                        }
                                    }
                                } else if (nullArray.get(i).get(j) == 0 && nullArray.get(i - 1).get(j + 1) != 0) {
                                    min = nullArray.get(i - 1).get(j + 1);
                                    if (nullArray.get(i).get(j + 1) < min && nullArray.get(i).get(j + 1) != 0) {
                                        min = nullArray.get(i).get(j + 1);
                                        if (nullArray.get(i - 1).get(j) < min && nullArray.get(i - 1).get(j) != 0) {
                                            min = nullArray.get(i - 1).get(j);
                                        }
                                    } else if (nullArray.get(i - 1).get(j) < min && nullArray.get(i - 1).get(j) != 0) {
                                        min = nullArray.get(i - 1).get(j);
                                        if (nullArray.get(i).get(j + 1) < min && nullArray.get(i).get(j + 1) != 0) {
                                            min = nullArray.get(i).get(j + 1);
                                        }
                                    }
                                } else if (nullArray.get(i).get(j) == 0 && nullArray.get(i - 1).get(j) != 0) {
                                    min = nullArray.get(i - 1).get(j);
                                    if (nullArray.get(i).get(j + 1) < min && nullArray.get(i).get(j + 1) != 0) {
                                        min = nullArray.get(i).get(j + 1);
                                        if (nullArray.get(i - 1).get(j + 1) < min && nullArray.get(i - 1).get(j + 1) != 0) {
                                            min = nullArray.get(i - 1).get(j + 1);
                                        }
                                    } else if(nullArray.get(i - 1).get(j + 1) < min && nullArray.get(i - 1).get(j + 1) != 0){
                                        min = nullArray.get(i - 1).get(j + 1);
                                        if (nullArray.get(i).get(j + 1) < min && nullArray.get(i).get(j + 1) != 0) {
                                            min = nullArray.get(i).get(j + 1);
                                        }
                                    }
                                }
                            }
                            else {
                                min = nullArray.get(i).get(j);
                                if (nullArray.get(i - 1).get(j + 1) < min && nullArray.get(i - 1).get(j + 1) != 0) {
                                    min = nullArray.get(i - 1).get(j + 1);
                                    if (nullArray.get(i - 1).get(j) < min && nullArray.get(i - 1).get(j) != 0) {
                                        min = nullArray.get(i - 1).get(j);
                                        if (nullArray.get(i).get(j + 1) < min && nullArray.get(i).get(j + 1) != 0) {
                                            min = nullArray.get(i).get(j + 1);
                                        }
                                    } else if (nullArray.get(i).get(j + 1) < min && nullArray.get(i).get(j + 1) != 0) {
                                        min = nullArray.get(i).get(j + 1);
                                        if (nullArray.get(i - 1).get(j) < min && nullArray.get(i - 1).get(j) != 0) {
                                            min = nullArray.get(i - 1).get(j);
                                        }
                                    }
                                } else if (nullArray.get(i - 1).get(j) < min && nullArray.get(i - 1).get(j) != 0) {
                                    min = nullArray.get(i - 1).get(j);
                                    if (nullArray.get(i).get(j + 1) < min && nullArray.get(i).get(j + 1) != 0) {
                                        min = nullArray.get(i).get(j + 1);
                                        if (nullArray.get(i - 1).get(j + 1) < min && nullArray.get(i - 1).get(j + 1) != 0) {
                                            min = nullArray.get(i - 1).get(j + 1);
                                        }
                                    }else if(nullArray.get(i - 1).get(j + 1) < min && nullArray.get(i - 1).get(j + 1) != 0){
                                        min = nullArray.get(i - 1).get(j + 1);
                                        if(nullArray.get(i).get(j + 1) < min && nullArray.get(i).get(j + 1) != 0){
                                            min = nullArray.get(i).get(j + 1);
                                        }
                                    }
                                } else if (nullArray.get(i).get(j + 1) < min && nullArray.get(i).get(j + 1) != 0) {
                                    min = nullArray.get(i).get(j + 1);
                                    if (nullArray.get(i - 1).get(j + 1) < min && nullArray.get(i - 1).get(j + 1) != 0) {
                                        min = nullArray.get(i - 1).get(j + 1);
                                        if (nullArray.get(i - 1).get(j) < min && nullArray.get(i - 1).get(j) != 0) {
                                            min = nullArray.get(i - 1).get(j);
                                        }
                                    }else if(nullArray.get(i - 1).get(j) < min && nullArray.get(i - 1).get(j) != 0){
                                        min = nullArray.get(i - 1).get(j);
                                        if(nullArray.get(i - 1).get(j + 1) < min && nullArray.get(i - 1).get(j + 1) != 0){
                                            min = nullArray.get(i - 1).get(j + 1);
                                        }
                                    }
                                }
                            }
                            System.out.println("Minimum w cyklu wynosi: " + min);
                            nullArray.get(i).set(j, nullArray.get(i).get(j) + min);
                            nullArray.get(i).set(j + 1, nullArray.get(i).get(j + 1) - min);
                            nullArray.get(i - 1).set(j, nullArray.get(i - 1).get(j) - min);
                            nullArray.get(i - 1).set(j + 1, nullArray.get(i - 1).get(j + 1) + min);
                        }


                        else if(i == podaż.size() - 1 && j == popyt.size() - 1){
                            if(nullArray.get(i).get(j) == 0) {
                                if (nullArray.get(i).get(j) == 0 && nullArray.get(i).get(j - 1) != 0) {
                                    min = nullArray.get(i).get(j - 1);
                                    if (nullArray.get(i - 1).get(j - 1) < min && nullArray.get(i - 1).get(j - 1) != 0) {
                                        min = nullArray.get(i - 1).get(j - 1);
                                        if (nullArray.get(i - 1).get(j) < min && nullArray.get(i - 1).get(j) != 0) {
                                            min = nullArray.get(i - 1).get(j);
                                        }
                                    } else if (nullArray.get(i - 1).get(j) < min && nullArray.get(i - 1).get(j) != 0) {
                                        min = nullArray.get(i - 1).get(j);
                                        if (nullArray.get(i - 1).get(j - 1) < min && nullArray.get(i - 1).get(j - 1) != 0) {
                                            min = nullArray.get(i - 1).get(j - 1);
                                        }
                                    }
                                } else if (nullArray.get(i).get(j) == 0 && nullArray.get(i - 1).get(j - 1) != 0) {
                                    min = nullArray.get(i - 1).get(j - 1);
                                    if (nullArray.get(i).get(j - 1) < min && nullArray.get(i).get(j - 1) != 0) {
                                        min = nullArray.get(i).get(j - 1);
                                        if (nullArray.get(i - 1).get(j) < min && nullArray.get(i - 1).get(j) != 0) {
                                            min = nullArray.get(i - 1).get(j);
                                        }
                                    } else if (nullArray.get(i - 1).get(j) < min && nullArray.get(i - 1).get(j) != 0) {
                                        min = nullArray.get(i - 1).get(j);
                                        if (nullArray.get(i).get(j - 1) < min && nullArray.get(i).get(j - 1) != 0) {
                                            min = nullArray.get(i).get(j - 1);
                                        }
                                    }
                                } else if (nullArray.get(i).get(j) == 0 && nullArray.get(i - 1).get(j) != 0) {
                                    min = nullArray.get(i - 1).get(j);
                                    if (nullArray.get(i).get(j - 1) < min && nullArray.get(i).get(j - 1) != 0) {
                                        min = nullArray.get(i).get(j - 1);
                                        if (nullArray.get(i - 1).get(j - 1) < min && nullArray.get(i - 1).get(j - 1) != 0) {
                                            min = nullArray.get(i - 1).get(j - 1);
                                        }
                                    } else if(nullArray.get(i - 1).get(j - 1) < min && nullArray.get(i - 1).get(j - 1) != 0){
                                        min = nullArray.get(i - 1).get(j - 1);
                                        if (nullArray.get(i).get(j - 1) < min && nullArray.get(i).get(j - 1) != 0) {
                                            min = nullArray.get(i).get(j - 1);
                                        }
                                    }
                                }
                            }
                            else {
                                min = nullArray.get(i).get(j);
                                if (nullArray.get(i - 1).get(j - 1) < min && nullArray.get(i - 1).get(j - 1) != 0) {
                                    min = nullArray.get(i - 1).get(j - 1);
                                    if (nullArray.get(i - 1).get(j) < min && nullArray.get(i - 1).get(j) != 0) {
                                        min = nullArray.get(i - 1).get(j);
                                        if (nullArray.get(i).get(j - 1) < min && nullArray.get(i).get(j - 1) != 0) {
                                            min = nullArray.get(i).get(j - 1);
                                        }
                                    } else if (nullArray.get(i).get(j - 1) < min && nullArray.get(i).get(j - 1) != 0) {
                                        min = nullArray.get(i).get(j - 1);
                                        if (nullArray.get(i - 1).get(j) < min && nullArray.get(i - 1).get(j) != 0) {
                                            min = nullArray.get(i - 1).get(j);
                                        }
                                    }
                                } else if (nullArray.get(i - 1).get(j) < min && nullArray.get(i - 1).get(j) != 0) {
                                    min = nullArray.get(i - 1).get(j);
                                    if (nullArray.get(i).get(j - 1) < min && nullArray.get(i).get(j - 1) != 0) {
                                        min = nullArray.get(i).get(j - 1);
                                        if (nullArray.get(i - 1).get(j - 1) < min && nullArray.get(i - 1).get(j - 1) != 0) {
                                            min = nullArray.get(i - 1).get(j - 1);
                                        }
                                    }else if(nullArray.get(i - 1).get(j - 1) < min && nullArray.get(i - 1).get(j - 1) != 0){
                                        min = nullArray.get(i - 1).get(j - 1);
                                        if(nullArray.get(i).get(j - 1) < min && nullArray.get(i).get(j - 1) != 0){
                                            min = nullArray.get(i).get(j - 1);
                                        }
                                    }
                                } else if (nullArray.get(i).get(j - 1) < min && nullArray.get(i).get(j - 1) != 0) {
                                    min = nullArray.get(i).get(j - 1);
                                    if (nullArray.get(i - 1).get(j - 1) < min && nullArray.get(i - 1).get(j - 1) != 0) {
                                        min = nullArray.get(i - 1).get(j - 1);
                                        if (nullArray.get(i - 1).get(j) < min && nullArray.get(i - 1).get(j) != 0) {
                                            min = nullArray.get(i - 1).get(j);
                                        }
                                    }else if(nullArray.get(i - 1).get(j) < min && nullArray.get(i - 1).get(j) != 0){
                                        min = nullArray.get(i - 1).get(j);
                                        if(nullArray.get(i - 1).get(j - 1) < min && nullArray.get(i - 1).get(j - 1) != 0){
                                            min = nullArray.get(i - 1).get(j - 1);
                                        }
                                    }
                                }
                            }

                            System.out.println("Minimum w cyklu wynosi: " + min);
                            nullArray.get(i).set(j, nullArray.get(i).get(j) + min);
                            nullArray.get(i).set(j - 1, nullArray.get(i).get(j - 1) - min);
                            nullArray.get(i - 1).set(j, nullArray.get(i - 1).get(j) - min);
                            nullArray.get(i - 1).set(j - 1, nullArray.get(i - 1).get(j - 1) + min);
                        }


                        else{
                            if(nullArray.get(i).get(j) == 0) {
                                if (nullArray.get(i).get(j) == 0 && nullArray.get(i).get(j + 1) != 0) {
                                    min = nullArray.get(i).get(j + 1);
                                    if (nullArray.get(i + 1).get(j + 1) < min && nullArray.get(i + 1).get(j + 1) != 0) {
                                        min = nullArray.get(i + 1).get(j + 1);
                                        if (nullArray.get(i + 1).get(j) < min && nullArray.get(i + 1).get(j) != 0) {
                                            min = nullArray.get(i + 1).get(j);
                                        }
                                    } else if (nullArray.get(i + 1).get(j) < min && nullArray.get(i + 1).get(j) != 0) {
                                        min = nullArray.get(i + 1).get(j);
                                        if (nullArray.get(i + 1).get(j + 1) < min && nullArray.get(i + 1).get(j + 1) != 0) {
                                            min = nullArray.get(i + 1).get(j + 1);
                                        }
                                    }
                                } else if (nullArray.get(i).get(j) == 0 && nullArray.get(i + 1).get(j + 1) != 0) {
                                    min = nullArray.get(i + 1).get(j + 1);
                                    if (nullArray.get(i).get(j + 1) < min && nullArray.get(i).get(j + 1) != 0) {
                                        min = nullArray.get(i).get(j + 1);
                                        if (nullArray.get(i + 1).get(j) < min && nullArray.get(i + 1).get(j) != 0) {
                                            min = nullArray.get(i + 1).get(j);
                                        }
                                    } else if (nullArray.get(i + 1).get(j) < min && nullArray.get(i + 1).get(j) != 0) {
                                        min = nullArray.get(i + 1).get(j);
                                        if (nullArray.get(i).get(j + 1) < min && nullArray.get(i).get(j + 1) != 0) {
                                            min = nullArray.get(i).get(j + 1);
                                        }
                                    }
                                } else if (nullArray.get(i).get(j) == 0 && nullArray.get(i + 1).get(j) != 0) {
                                    min = nullArray.get(i + 1).get(j);
                                    if (nullArray.get(i).get(j + 1) < min && nullArray.get(i).get(j + 1) != 0) {
                                        min = nullArray.get(i).get(j + 1);
                                        if (nullArray.get(i + 1).get(j + 1) < min && nullArray.get(i + 1).get(j + 1) != 0) {
                                            min = nullArray.get(i + 1).get(j + 1);
                                        }
                                    } else if(nullArray.get(i + 1).get(j + 1) < min && nullArray.get(i + 1).get(j + 1) != 0){
                                        min = nullArray.get(i + 1).get(j + 1);
                                        if (nullArray.get(i).get(j + 1) < min && nullArray.get(i).get(j + 1) != 0) {
                                            min = nullArray.get(i).get(j + 1);
                                        }
                                    }
                                }
                            }
                            else {
                                min = nullArray.get(i).get(j);
                                if (nullArray.get(i + 1).get(j + 1) < min && nullArray.get(i + 1).get(j + 1) != 0) {
                                    min = nullArray.get(i + 1).get(j + 1);
                                    if (nullArray.get(i + 1).get(j) < min && nullArray.get(i + 1).get(j) != 0) {
                                        min = nullArray.get(i + 1).get(j);
                                        if (nullArray.get(i).get(j + 1) < min && nullArray.get(i).get(j + 1) != 0) {
                                            min = nullArray.get(i).get(j + 1);
                                        }
                                    } else if (nullArray.get(i).get(j + 1) < min && nullArray.get(i).get(j + 1) != 0) {
                                        min = nullArray.get(i).get(j + 1);
                                        if (nullArray.get(i + 1).get(j) < min && nullArray.get(i + 1).get(j) != 0) {
                                            min = nullArray.get(i + 1).get(j);
                                        }
                                    }
                                } else if (nullArray.get(i + 1).get(j) < min && nullArray.get(i + 1).get(j) != 0) {
                                    min = nullArray.get(i + 1).get(j);
                                    if (nullArray.get(i).get(j + 1) < min && nullArray.get(i).get(j + 1) != 0) {
                                        min = nullArray.get(i).get(j + 1);
                                        if (nullArray.get(i + 1).get(j + 1) < min && nullArray.get(i + 1).get(j + 1) != 0) {
                                            min = nullArray.get(i + 1).get(j + 1);
                                        }
                                    }else if(nullArray.get(i + 1).get(j + 1) < min && nullArray.get(i + 1).get(j + 1) != 0){
                                        min = nullArray.get(i + 1).get(j + 1);
                                        if(nullArray.get(i).get(j + 1) < min && nullArray.get(i).get(j + 1) != 0){
                                            min = nullArray.get(i).get(j + 1);
                                        }
                                    }
                                } else if (nullArray.get(i).get(j + 1) < min && nullArray.get(i).get(j + 1) != 0) {
                                    min = nullArray.get(i).get(j + 1);
                                    if (nullArray.get(i + 1).get(j + 1) < min && nullArray.get(i + 1).get(j + 1) != 0) {
                                        min = nullArray.get(i + 1).get(j + 1);
                                        if (nullArray.get(i + 1).get(j) < min && nullArray.get(i + 1).get(j) != 0) {
                                            min = nullArray.get(i + 1).get(j);
                                        }
                                    }else if(nullArray.get(i + 1).get(j) < min && nullArray.get(i + 1).get(j) != 0){
                                        min = nullArray.get(i + 1).get(j);
                                        if(nullArray.get(i + 1).get(j + 1) < min && nullArray.get(i + 1).get(j + 1) != 0){
                                            min = nullArray.get(i + 1).get(j + 1);
                                        }
                                    }
                                }
                            }

                            System.out.println("Minimum w cyklu wynosi: " + min);
                            nullArray.get(i).set(j, nullArray.get(i).get(j) + min);
                            nullArray.get(i).set(j + 1, nullArray.get(i).get(j + 1) - min);
                            nullArray.get(i + 1).set(j, nullArray.get(i + 1).get(j) - min);
                            nullArray.get(i + 1).set(j + 1, nullArray.get(i + 1).get(j + 1) + min);
                        }
                    }
                }
            }
            System.out.println("Tabela kosztów po zmianie: " + nullArray);
            calculate_alpha_beta();
            calculate_tabela_wskaznikow();
            for (int i = 0; i < podaż.size(); i++) {
                for (int j = 0; j < popyt.size(); j++) {
                    if( delty.get(i).get(j) > 0 ){ //jezeli wartosci w tabeli optymalnosci są ujemne lub równe zero rozwiazanie jest optymalne
                        count = count + 1;
                        temp = count;
                    }
                }
            }

            if(temp == 0){
                System.out.println("rozwiązanie optymalne");
            }
            else System.out.println("rozwiązanie nieoptymalne");
            System.out.println("-------------------------------------------------------------------------------------------");


        } while (count == 0);

    }


    //public void

    @Override
    public String toString() {

        return "Zysk jednostkowy:\n" +Arrays.toString(zysk_jednostkowy.get(0).toArray()) +"\n"+Arrays.toString(zysk_jednostkowy.get(1).toArray())+"\n"+Arrays.toString(zysk_jednostkowy.get(2).toArray());
    }


}
